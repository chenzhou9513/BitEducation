package com.bitedu.controller;
import com.bitedu.common.Result;
import com.bitedu.common.StatusCode;
import com.bitedu.dao.ServiceScheduleMapper;
import com.bitedu.dto.ServiceInfoWithSchedule;
import com.bitedu.pojo.ServiceInfo;
import com.bitedu.pojo.ServiceSchedule;
import com.bitedu.pojo.UserConsume;
import com.bitedu.service.ConsumptionService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class ConsumptionController {
    @Autowired
    private ConsumptionService consumptionService;
    @Autowired
    private ServiceScheduleMapper serviceScheduleMapper;
    @RequestMapping(value = "/consumption", method = RequestMethod.POST)
    public Result getService(@RequestBody UserConsume userConsume) {
        return (Result) consumptionService.userConsumeService(userConsume.getEmail(), userConsume.getServiceId());
    }
    @RequestMapping(value = "/service", method = RequestMethod.POST)
    public Result testService(@RequestBody ServiceInfoWithSchedule serviceInfoWithSchedule) {
        return (Result) this.consumptionService.insertServiceWithSchedule(serviceInfoWithSchedule);
    }
    @RequestMapping(value = "/service", method = RequestMethod.GET)
    public Result getAllService(@RequestParam("index") String index, @RequestParam("pageSize") String pageSize) {
        PageHelper.startPage(Integer.parseInt(index), Integer.parseInt(pageSize));
        List<ServiceInfo> data = this.consumptionService.selectServiceSelective(new ServiceInfo());
        return new Result(true, StatusCode.SUCCESS, "查询成功", data);
    }
    @RequestMapping(value = "/topservice", method = RequestMethod.GET)
    public Result getTopService(@RequestParam("nums") int nums) {
        return (Result) this.consumptionService.getTopService(nums);
    }
    @RequestMapping(value = "/service/{serviceId}", method = RequestMethod.GET)
    public Result getUserService(@PathVariable("serviceId") String serviceId) {
        ServiceInfo data = this.consumptionService.selectByPrimaryKey(serviceId);
        return new Result(true, StatusCode.SUCCESS, "查询成功", data);
    }
    @RequestMapping(value = "/schedual/{serviceId}", method = RequestMethod.GET)
    public Result getServiceschedual(@PathVariable("serviceId") String serviceId) {
        List<ServiceSchedule> data = this.serviceScheduleMapper.selectByServiceId(serviceId);
        return new Result(true, StatusCode.SUCCESS, "查询成功", data);
    }
}
