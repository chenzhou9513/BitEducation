package com.bitedu.controller;

import com.alibaba.fastjson.JSONObject;
import com.bitedu.common.DateUtil;
import com.bitedu.common.Result;
import com.bitedu.common.StatusCode;
import com.bitedu.dao.QueryMapper;
import org.apache.shiro.crypto.hash.Hash;
import org.omg.PortableInterceptor.INACTIVE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.sql.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/query")
public class QueryController {

    @Autowired
    private QueryMapper queryMapper;

    @Autowired
    private  DateUtil dateUtil;

    @RequestMapping(value = "/allUserCom",method = RequestMethod.GET)
    public Result allUserCom(@RequestParam("beginDate") String beginDate, @RequestParam("endDate") String endDate) throws ParseException {


        Map<String,String> m = new HashMap<String,String>();



        m.put("beginDate",beginDate );
        m.put("endDate", endDate);
        List<Map<String,Object>> querys = this.queryMapper.allUserConsumeData(m);



        Map<String,List> data = new HashMap<String,List>();


        List<String> allDate = dateUtil.getAllDate(beginDate, endDate);


        List<Double> allNums = new ArrayList<Double>();
        for(int i=0; i<allDate.size(); i++){
            allNums.add(0.0);
        }


        for(int i=0; i<querys.size(); i++){

            String d = (String)querys.get(i).get("days");
            double nums = (Double)querys.get(i).get("nums");
           int index = this.dateUtil.getDifferDays(beginDate, d);
            allNums.set(index, nums);
        }

        data.put("date", allDate);
        data.put("nums",allNums);

        return new Result(true, StatusCode.SUCCESS, "查询成功",
                data);
    }

    @RequestMapping(value = "/userCom",method = RequestMethod.POST)
    public Result allUserCom(
            @RequestBody JSONObject jsonObject

    ) throws ParseException {


        Map<String,String> m = new HashMap<String,String>();

        String beginDate = jsonObject.getString("beginDate");
        String endDate = jsonObject.getString("endDate");
        String email = jsonObject.getString("email");


        m.put("beginDate", beginDate);
        m.put("endDate", endDate);
        m.put("email", email);
        List<Map<String,Object>> querys = this.queryMapper.userConsumeData(m);



        Map<String,List> data = new HashMap<String,List>();


        List<String> allDate = dateUtil.getAllDate(beginDate, endDate);


        List<Double> allNums = new ArrayList<Double>();
        for(int i=0; i<allDate.size(); i++){
            allNums.add(0.0);
        }


        for(int i=0; i<querys.size(); i++){

            String d = (String)querys.get(i).get("days");
            double nums = (Double)querys.get(i).get("nums");
            int index = this.dateUtil.getDifferDays(beginDate, d);
            allNums.set(index, nums);
        }

        data.put("date", allDate);
        data.put("nums",allNums);

        return new Result(true, StatusCode.SUCCESS, "查询成功",
                data);
    }


    @RequestMapping(value = "/companyCom",method = RequestMethod.POST)
    public Result companyUserCom(
            @RequestBody JSONObject jsonObject

    ) throws ParseException {


        Map<String,String> m = new HashMap<String,String>();

        String beginDate = jsonObject.getString("beginDate");
        String endDate = jsonObject.getString("endDate");
        String email = jsonObject.getString("email");


        m.put("beginDate", beginDate);
        m.put("endDate", endDate);
        m.put("email", email);
        List<Map<String,Object>> querys = this.queryMapper.companyConsumeData(m);



        Map<String,List> data = new HashMap<String,List>();


        List<String> allDate = dateUtil.getAllDate(beginDate, endDate);


        List<Double> allNums = new ArrayList<Double>();
        for(int i=0; i<allDate.size(); i++){
            allNums.add(0.0);
        }


        for(int i=0; i<querys.size(); i++){

            String d = (String)querys.get(i).get("days");
            double nums = (Double)querys.get(i).get("nums");
            int index = this.dateUtil.getDifferDays(beginDate, d);
            allNums.set(index, nums);
        }

        data.put("date", allDate);
        data.put("nums",allNums);

        return new Result(true, StatusCode.SUCCESS, "查询成功",
                data);
    }

    /*
        public List<Map<String,Object>> allUserChargeData(Map<String,String> m);
        public List<Map<String,Object>> userChargeData(Map<String,String> m);
        public List<Map<String,Object>> allCompanyWithdrawalData(Map<String,String> m);
        public List<Map<String,Object>> companyWithdrawalData(Map<String,String> m);

    */


    @RequestMapping(value = "/allUserCharge",method = RequestMethod.GET)
    public Result allUserCharge(@RequestParam("beginDate") String beginDate, @RequestParam("endDate") String endDate) throws ParseException {


        Map<String,String> m = new HashMap<String,String>();



        m.put("beginDate",beginDate );
        m.put("endDate", endDate);
        List<Map<String,Object>> querys = this.queryMapper.allUserChargeData(m);



        Map<String,List> data = new HashMap<String,List>();


        List<String> allDate = dateUtil.getAllDate(beginDate, endDate);


        List<Integer> allNums = new ArrayList<Integer>();
        for(int i=0; i<allDate.size(); i++){
            allNums.add(0);
        }


        for(int i=0; querys!=null&&i<querys.size(); i++){

            String d = (String)querys.get(i).get("days");
            int nums = ((BigDecimal) querys.get(i).get("nums")).intValue();
            int index = this.dateUtil.getDifferDays(beginDate, d);
            allNums.set(index, nums);
        }

        data.put("date", allDate);
        data.put("nums",allNums);

        return new Result(true, StatusCode.SUCCESS, "查询成功",
                data);
    }


    @RequestMapping(value = "/allCompanyWithdrawal",method = RequestMethod.GET)
    public Result allCompanyWithdrawal(@RequestParam("beginDate") String beginDate, @RequestParam("endDate") String endDate) throws ParseException {


        Map<String,String> m = new HashMap<String,String>();



        m.put("beginDate",beginDate );
        m.put("endDate", endDate);
        List<Map<String,Object>> querys = this.queryMapper.allCompanyWithdrawalData(m);



        Map<String,List> data = new HashMap<String,List>();


        List<String> allDate = dateUtil.getAllDate(beginDate, endDate);


        List<Double> allNums = new ArrayList<Double>();
        for(int i=0; i<allDate.size(); i++){
            allNums.add(0.0);
        }


        for(int i=0; i<querys.size(); i++){

            String d = (String)querys.get(i).get("days");
            double nums = (Double)querys.get(i).get("nums");
            int index = this.dateUtil.getDifferDays(beginDate, d);
            allNums.set(index, nums);
        }

        data.put("date", allDate);
        data.put("nums",allNums);

        return new Result(true, StatusCode.SUCCESS, "查询成功",
                data);
    }



    @RequestMapping(value = "/userCharge",method = RequestMethod.POST)
    public Result userChargeData(
            @RequestBody JSONObject jsonObject

    ) throws ParseException {


        Map<String,String> m = new HashMap<String,String>();

        String beginDate = jsonObject.getString("beginDate");
        String endDate = jsonObject.getString("endDate");
        String email = jsonObject.getString("email");


        m.put("beginDate", beginDate);
        m.put("endDate", endDate);
        m.put("email", email);
        List<Map<String,Object>> querys = this.queryMapper.userChargeData(m);



        Map<String,List> data = new HashMap<String,List>();


        List<String> allDate = dateUtil.getAllDate(beginDate, endDate);


        List<Integer> allNums = new ArrayList<Integer>();
        for(int i=0; i<allDate.size(); i++){
            allNums.add(0);
        }


        for(int i=0; i<querys.size(); i++){

            String d = (String)querys.get(i).get("days");
            int nums = ((BigDecimal) querys.get(i).get("nums")).intValue();
            int index = this.dateUtil.getDifferDays(beginDate, d);
            allNums.set(index, nums);
        }

        data.put("date", allDate);
        data.put("nums",allNums);

        return new Result(true, StatusCode.SUCCESS, "查询成功",
                data);
    }

    @RequestMapping(value = "/companyWithdrawal",method = RequestMethod.POST)
    public Result companyWithdrawal(
            @RequestBody JSONObject jsonObject

    ) throws ParseException {


        Map<String,String> m = new HashMap<String,String>();

        String beginDate = jsonObject.getString("beginDate");
        String endDate = jsonObject.getString("endDate");
        String email = jsonObject.getString("email");


        m.put("beginDate", beginDate);
        m.put("endDate", endDate);
        m.put("email", email);
        List<Map<String,Object>> querys = this.queryMapper.companyWithdrawalData(m);



        Map<String,List> data = new HashMap<String,List>();


        List<String> allDate = dateUtil.getAllDate(beginDate, endDate);


        List<Double> allNums = new ArrayList<Double>();
        for(int i=0; i<allDate.size(); i++){
            allNums.add(0.0);
        }


        for(int i=0; i<querys.size(); i++){

            String d = (String)querys.get(i).get("days");
            double nums = (Double)querys.get(i).get("nums");
            int index = this.dateUtil.getDifferDays(beginDate, d);
            allNums.set(index, nums);
        }

        data.put("date", allDate);
        data.put("nums",allNums);

        return new Result(true, StatusCode.SUCCESS, "查询成功",
                data);
    }

}
