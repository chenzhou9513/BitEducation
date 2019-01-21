package com.bitedu.controller;


import com.bitedu.common.Result;
import com.bitedu.common.StatusCode;
import com.bitedu.dto.ServiceInfoWithSchedule;
import com.bitedu.pojo.ServiceInfo;
import com.bitedu.pojo.UserConsume;
import com.bitedu.service.ConsumptionService;
import com.bitedu.service.redis.RedisLock;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

@RestController
public class ConsumptionController {


    @Autowired
    private ConsumptionService consumptionService;

    @RequestMapping(value = "/consumption",method = RequestMethod.POST)
    public Result getService(@RequestBody UserConsume userConsume){

        return (Result) consumptionService.userConsumeService(userConsume.getEmail(), userConsume.getServiceId());
    }




    @RequestMapping(value = "/service",method = RequestMethod.POST)
    public Result testService(@RequestBody ServiceInfoWithSchedule serviceInfoWithSchedule){


        return (Result) this.consumptionService.insertServiceWithSchedule(serviceInfoWithSchedule);
    }

    @RequestMapping(value = "/service",method = RequestMethod.GET)
    public Result getAllService(@RequestParam("index")String index,@RequestParam("pageSize")String pageSize){


        PageHelper.startPage(Integer.parseInt(index),Integer.parseInt(pageSize));
        List<ServiceInfo> data = this.consumptionService.selectServiceSelective(new ServiceInfo());
        return new Result(true, StatusCode.SUCCESS, "查询成功",data);

    }




    @RequestMapping(value = "/service/{serviceId}",method = RequestMethod.GET)
    public Result getUserService(@PathVariable("serviceId")String serviceId){

        ServiceInfo data = this.consumptionService.selectByPrimaryKey(serviceId);
        return new Result(true, StatusCode.SUCCESS, "查询成功",data);

    }








}
