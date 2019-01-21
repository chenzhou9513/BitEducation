package com.bitedu.service.fabric;


import com.alibaba.fastjson.JSONObject;
import com.bitedu.dto.ApproveChargeFabric;
import com.bitedu.pojo.fabric.Administrator;
import com.bitedu.pojo.fabric.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@Component
@FeignClient(value = "fabricUserClient",url="${fabric.url}")
@RequestMapping("/api")
public interface FabricAdminClient {


    @RequestMapping(value = "/Administrator",method = RequestMethod.GET)
    public String getAdmin();

    @RequestMapping(value = "/Administrator",method = RequestMethod.POST)
    public String insertAdmin(@RequestBody Administrator administrator);

    /*
        {
          "$class": "token.CheckUserRecharge",
          "rechargeID": "recharge011"
        }
    */
    @RequestMapping(value = "/CheckUserRecharge",method = RequestMethod.POST)
    public String checkUserRecharge(@RequestBody ApproveChargeFabric approveChargeFabric);

    /*
        {
          "$class": "token.CheckUserRecharge",
          "rechargeID": "recharge011"
        }


    */
    @RequestMapping(value = "/RejectUserRecharge",method = RequestMethod.POST)
    public String rejectUserRecharge(@RequestBody ApproveChargeFabric approveChargeFabric);

}
