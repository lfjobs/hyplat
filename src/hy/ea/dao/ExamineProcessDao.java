package hy.ea.dao;


import hy.ea.bo.ExamineProcessData;

public interface ExamineProcessDao {
    /**
     * 根据id获取审核数据
     * @param examineProcessId
     * @return
     */
    ExamineProcessData getExamineProcessDataById(String examineProcessId);

}
