package hy.ea.finance.service.impl;

import hy.ea.finance.dao.CommissionDao;
import hy.ea.finance.service.CommissionService;
import hy.plat.bo.BaseBean;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CommissionServiceImpl
  implements CommissionService
{
  @Resource
  private CommissionDao comDao;

  public Map<String,Object> GetDesignHomePageList(String comId)
  {
    return this.comDao.GetDesignPageList(comId);
  }
}