package com.bitedu.service.fabric;


import com.bitedu.dto.ServiceInfoFabric;
import com.bitedu.dto.UserConsumeService;
import com.bitedu.pojo.ServiceInfo;
import com.bitedu.pojo.fabric.Company;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Component
@FeignClient(value = "fabricServiceClient",url="${fabric.url}")
@RequestMapping("/api")
public interface FabricServiceClient {


    @RequestMapping(value = "/Service",method = RequestMethod.GET)
    public String getService();

    @RequestMapping(value = "/Service",method = RequestMethod.POST)
    public String insertService(@RequestBody ServiceInfoFabric serviceInfoFabric);

    @RequestMapping(value="UserConsumeService",method = RequestMethod.POST)
    public String userConsumeService(@RequestBody UserConsumeService userConsumeService);

}
