package com.bitedu.service.fabric;


import com.bitedu.dto.UserCharge;
import com.bitedu.pojo.fabric.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Component
@FeignClient(value = "fabricUserClient",url="${fabric.url}")
@RequestMapping("/api")
public interface FabricUserClient {

    @RequestMapping(value = "/user",method = RequestMethod.GET)
    public String getUser();

    @RequestMapping(value = "/user",method = RequestMethod.POST)
    public String insertUser(@RequestBody User user);


    @RequestMapping(value = "/UserRecharge",method = RequestMethod.POST)
    public String chargeApply(@RequestBody UserCharge userCharge);




}
