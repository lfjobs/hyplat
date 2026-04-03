package hy.ea.dao.impl;

import hy.ea.bo.ExamineProcessData;
import hy.ea.dao.ExamineProcessDao;
import hy.plat.dao.impl.BaseBeanDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class ExamineProcessDaoImpl implements ExamineProcessDao {
    @Resource
    private BaseBeanDao baseBeanDao;

    @Override
    public ExamineProcessData getExamineProcessDataById(String examineProcessId) {
        StringBuffer sql = new StringBuffer(" from ExamineProcessData o where o.examineProcessId? ");
        return (ExamineProcessData) baseBeanDao.getBeanByHqlAndParams(sql.toString(), new Object[]{examineProcessId});
    }
}
