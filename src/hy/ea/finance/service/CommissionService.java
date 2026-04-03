package hy.ea.finance.service;

import hy.plat.bo.BaseBean;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public abstract interface CommissionService
{
  public abstract Map<String,Object> GetDesignHomePageList(String paramString);
}