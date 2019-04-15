package com.bitedu.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.bitedu.common.RedisUtil;
import com.bitedu.common.Result;
import com.bitedu.common.SnowFlake;
import com.bitedu.common.StatusCode;
import com.bitedu.dao.*;
import com.bitedu.dto.ServiceInfoFabric;
import com.bitedu.dto.ServiceInfoWithSchedule;
import com.bitedu.dto.UserConsumeService;
import com.bitedu.pojo.*;
import com.bitedu.service.ConsumptionService;
import com.bitedu.service.UserService;
import com.bitedu.service.fabric.FabricServiceClient;
import com.bitedu.service.redis.RedisLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class ConsumptionServiceImpl implements ConsumptionService {

    @Autowired
    private FabricServiceClient fabricServiceClient;

    @Autowired
    private ServiceInfoMapper serviceInfoMapper;

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private CompanyInfoMapper companyInfoMapper;

    @Autowired
    private UserConsumeMapper userConsumeMapper;

    @Autowired
    private ServiceScheduleMapper serviceScheduleMapper;

    @Autowired
    private RedisLock redisLock;

    @Autowired
    private BalanceMapper balanceMapper;

    @Autowired
    private SnowFlake snowFlake;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private UserService userService;

    @Override
    public ServiceInfo getServiceByServiceId(String serviceId) {

        return this.serviceInfoMapper.selectByPrimaryKey(serviceId);

    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object userConsumeService(String email, String serviceId) {


        UserInfo userInfo = this.userInfoMapper.selectByEmail(email);


        try{
            redisLock.lock(serviceId);

            ServiceInfo serviceInfo = this.serviceInfoMapper.selectByPrimaryKey(serviceId);


            if(serviceInfo.getCapacity()==0){
                return new Result(false,StatusCode.CAPACITYERRPR,"课程已经售完");
            }else if(userInfo.getBalance()<serviceInfo.getPrice()){

                return new Result(false,StatusCode.BALANCEINADEQUATE,"余额不足");

            }else {

                double price = serviceInfo.getPrice();

                CompanyInfo companyInfo = this.companyInfoMapper.selectByEmail(serviceInfo.getCompanyEmail());

                HashMap<String,Object> m = new HashMap<String,Object>();
                m.put("userId", userInfo.getId());
                m.put("companyId", companyInfo.getId());
                m.put("userDecBalance", price);
                //权益分配
                m.put("companyAddBalance", (double)(price*0.997));
                System.out.println(m.get("companyAddBalance"));
                int res = this.balanceMapper.transactionBalanceU2C(m);
                if(res==0){
                   return new Result(false, StatusCode.ERROR, "购买失败，请重新操作");
                }

                userInfo.setBalance(userInfo.getBalance()-price);
                this.userService.updateUser(userInfo);

                serviceInfo.setCapacity(serviceInfo.getCapacity() - 1);
                this.serviceInfoMapper.updateByPrimaryKey(serviceInfo);


                UserConsume userConsume = new UserConsume();

                userConsume.setId(String.valueOf(snowFlake.nextId()));
                userConsume.setEmail(userInfo.getEmail());
                userConsume.setServiceId(serviceId);
                this.userConsumeMapper.insertSelective(userConsume);


                UserConsumeService userConsumeService = new UserConsumeService(serviceId, userInfo.getEmail());
                JSONObject jsonObject;

                try {

                    jsonObject = JSONObject.parseObject((fabricServiceClient.userConsumeService(userConsumeService)));

                }catch (Exception e){

                    //    public Result(boolean flag, int code, String message) {
                    return new Result(false,StatusCode.FABRICERROR,e.toString());

                }


         }

            String key = "ConsumptionTimes::"+serviceId;
            String val;
            if((val=(String)redisTemplate.opsForValue().get(key))!=null){
                val = (Integer.parseInt(val)+1)+"";
            }else {
                val = "1";
            }
            redisTemplate.opsForValue().set(key, val);


            ZSetOperations<String, Object> opsForZSet = redisTemplate.opsForZSet();
            TopService topService = new TopService();
            topService.setName(serviceInfo.getName());
            topService.setServiceId(serviceId);
            opsForZSet.add("TopService", topService, Float.parseFloat(val));

        }catch (Exception e) {
            e.printStackTrace();
        }finally{

            redisLock.unlock(serviceId);
        }



        return new Result(true,StatusCode.SUCCESS,"购买成功");


        //service002   ,userk@email.com  , company1@email.com
    }

    @Override
    public List<ServiceInfo> selectServiceSelective(ServiceInfo record) {
        return this.serviceInfoMapper.selectServiceSelective(record);
    }

    @Override
    public List<ServiceInfo> selectCompanyService(String email) {
        return this.serviceInfoMapper.selectCompanyService(email);
    }

    @Override
    public List<ServiceInfo> selectUserService(String email) {
        return this.serviceInfoMapper.selectUserService(email);
    }

    @Override
    public ServiceInfo selectByPrimaryKey(String id) {
        return this.serviceInfoMapper.selectByPrimaryKey(id);
    }




    @Override
    public Object insertServiceWithSchedule(ServiceInfoWithSchedule serviceInfoWithSchedule){
        ServiceInfo serviceInfo = serviceInfoWithSchedule.getServiceInfo();
        serviceInfo.setId("C"+String.valueOf(snowFlake.nextId()));
        this.serviceInfoMapper.insertSelective(serviceInfo);

        ServiceInfoFabric serviceInfoFabric = new ServiceInfoFabric();
        serviceInfoFabric.setClassName("token.Service");
        serviceInfoFabric.setCompany(serviceInfo.getCompanyEmail());
        serviceInfoFabric.setServiceID(serviceInfo.getId());
        serviceInfoFabric.setServiceName(serviceInfo.getName());
        serviceInfoFabric.setServicePrice(serviceInfo.getPrice());
        serviceInfoFabric.setServiceType(serviceInfo.getType());


        this.fabricServiceClient.insertService(serviceInfoFabric);

        List<ServiceSchedule> serviceSchedules = serviceInfoWithSchedule.getSchedules();

        for(int i=0; i<serviceSchedules.size(); i++){

            ServiceSchedule serviceSchedule = serviceSchedules.get(i);
            serviceSchedule.setServiceId(serviceInfo.getId());
            serviceSchedule.setId(String.valueOf(snowFlake.nextId()));
            this.serviceScheduleMapper.insertSelective(serviceSchedule);
        }


        return new Result(true, StatusCode.SUCCESS,"发布课程成功" );
    }

    @Override
    public Object getTopService(int nums) {


        ZSetOperations<String, Object> opsForZSet = redisTemplate.opsForZSet();
        Set<Object> res = opsForZSet.reverseRange("TopService", 0, nums-1);


        return new Result(true, StatusCode.SUCCESS,"查询成功",res );
    }
}