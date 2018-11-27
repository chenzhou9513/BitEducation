package com.bitedu.service.fabric;


import com.bitedu.pojo.fabric.Company;
import com.bitedu.pojo.fabric.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Component
@FeignClient(value = "fabricUserClient",url="${fabric.url}")
@RequestMapping("/api")
public interface FabricCompanyClient {

    @RequestMapping(value = "/Company",method = RequestMethod.GET)
    public String getCompany();

    @RequestMapping(value = "/Company",method = RequestMethod.POST)
    public String insertCompany(@RequestBody Company company);

}
