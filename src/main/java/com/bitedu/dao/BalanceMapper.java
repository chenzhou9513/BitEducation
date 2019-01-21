package com.bitedu.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

import java.util.Map;

public interface BalanceMapper {

    Integer transactionBalanceU2C(Map<String,Object> param);

}
