package hy.ea.finance.dao;

import hy.plat.bo.BaseBean;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public abstract interface CommissionDao
{
  public abstract Map<String,Object> GetDesignPageList(String paramString);
}