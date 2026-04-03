package hy.ea.finance.service.impl;

import hy.ea.bo.finance.CSubjectsRule;
import hy.ea.finance.service.CSubjectsRuleService;
import hy.plat.service.BaseBeanService;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

@Service
public class CSubjectsRuleServiceImpl implements CSubjectsRuleService {
	@Resource
	private BaseBeanService baseBeanService;

	@Override
	public void save(CSubjectsRule subRule) {

		String hql = "delete from CSubjectsRule where companyID = ? ";
		Object[] params = { subRule.getCompanyID() };
		baseBeanService.saveBeansListAndexecuteHqlsByParams(null, new String[]{hql}, params);
		baseBeanService.update(subRule);
	}

}
