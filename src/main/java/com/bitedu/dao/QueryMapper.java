package com.bitedu.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface QueryMapper {

    //管理员查看： 时间段内所有用户消费数据 (x:日, y:消费金额)
    //beginDate, endDate
    public List<Map<String,Object>> allUserConsumeData(Map<String,String> m);

    public List<Map<String,Object>> userConsumeData(Map<String,String> m);

    public List<Map<String,Object>> companyConsumeData(Map<String,String> m);

    public List<Map<String,Object>> allUserChargeData(Map<String,String> m);

    public List<Map<String,Object>> userChargeData(Map<String,String> m);
    public List<Map<String,Object>> allCompanyWithdrawalData(Map<String,String> m);
    public List<Map<String,Object>> companyWithdrawalData(Map<String,String> m);

    public int companyWithdrawalCount(Map<String,String> m);


}
