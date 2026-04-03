package hy.ea.service.impl;

import hy.ea.bo.DictData;
import hy.ea.bo.ExamineProcessData;
import hy.ea.dao.DictDataDao;
import hy.ea.dao.ExamineProcessDao;
import hy.ea.service.DictDataService;
import hy.ea.service.ExamineProcessService;
import hy.plat.bo.BaseBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ExamineProcessServiceImpl implements ExamineProcessService {
    @Resource
    private ExamineProcessDao examineProcessDao;


    @Override
    public ExamineProcessData getExamineProcessDataById(String examineProcessId) {
        return examineProcessDao.getExamineProcessDataById(examineProcessId);
    }
}
