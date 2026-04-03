package hy.ea.service;

import hy.ea.bo.ExamineProcessData;

public interface ExamineProcessService {
    /**
     * 根据id获取审核数据
     * @param examineProcessId
     * @return
     */
    ExamineProcessData getExamineProcessDataById(String examineProcessId);

}
