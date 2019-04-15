package com.bitedu.service;

import com.bitedu.pojo.QualificationApply;

public interface QualificationApplyService {

    Object insertNewApply(QualificationApply record);

    Object selectAllApply();

    QualificationApply selectByPrimaryKey(String id);

    Object approveQualification(String adminId, int state,String qualificationApplyId, String info);

}
