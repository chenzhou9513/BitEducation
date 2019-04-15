package com.bitedu.service.fabric;


import com.bitedu.dto.QualificationApplyFabric;
import com.bitedu.dto.QualificationInfoFabric;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Component
@FeignClient(value = "fabricUserClient",url="${fabric.url}")
@RequestMapping("/api")
public interface FabricQualification {


    /*
    {
      "$class": "token.QualificationApply",
      "qualificationApplyId": "ssddewweqweqwe",
      "auditState": 0,
      "Administrator": "none",
      "company": "213@qq.com"
    }
     */
    @RequestMapping(value = "/QualificationApply",method = RequestMethod.POST)
    public String insertQualificationApply(@RequestBody QualificationApplyFabric qualificationApplyFabric);

    @RequestMapping(value = "/QualificationApply/{applyId}",method = RequestMethod.PUT)
    public String updateQualificationApply(@PathVariable("applyId")String applyId,@RequestBody QualificationApplyFabric qualificationApplyFabric);

    @RequestMapping(value = "/Qualification",method = RequestMethod.POST)
    public String insertQualification(@RequestBody QualificationInfoFabric qualificationInfoFabric);





}
