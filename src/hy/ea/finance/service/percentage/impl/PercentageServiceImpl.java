package hy.ea.finance.service.percentage.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hy.ea.bo.finance.percentage.BPercentage;
import hy.ea.bo.finance.percentage.PPercentage;
import hy.ea.finance.service.percentage.PercentageService;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2018-11-7.
 */
@Service
@Transactional
public class PercentageServiceImpl implements PercentageService {

    @Resource
    private BaseBeanService baseBeanService;


    /**
     * 获取各种价格百分比[零售价,活动价...等]
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public PageForm selectPPercentage(int pageNumber, int pageSize, String companyID) {
        StringBuilder sql = new StringBuilder();
        sql.append("select p.percentageId,p.retail,p.activity,p.vip,p.wholesale,p.special,p.principal,p.times,pp.goodsname,dc.codevalue");
        sql.append(" from p_percentage p left join dt_productpackaging pp on pp.ppid = p.ppid");
        sql.append(" left join dtccode dc on dc.codeid = p.codeid and dc.companyid = ? where p.companyid = ?");
        PageForm pageForm = this.baseBeanService.getPageFormBySQL(pageNumber, pageSize, sql.toString(), "select count(*) from(" + sql + ")", new Object[]{companyID, companyID});
        return pageForm;
    }

    /**
     * 价格、佣金百分比添加
     */
    @Override
    public boolean PPercentageAdd(PPercentage ppercentage) {
        boolean flag = true;
        try {
            this.baseBeanService.save(ppercentage);
        } catch (Exception e) {
            logger.error("操作异常", e);
            flag = false;
        }
        return flag;
    }

    /**
     * 价格百分比修改回显
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public PPercentage getPPercentagebyId(String percentageId) {
        String hql = "from PPercentage where percentageId = ?";
        return (PPercentage) this.baseBeanService.getBeanByHqlAndParams(hql, new Object[]{percentageId});
    }

    /**
     * 价格百分比修改
     */
    @Override
    public boolean PPercentageuUpdate(PPercentage ppercentage) {
        boolean flag = true;
        try {
            this.baseBeanService.update(ppercentage);
        } catch (Exception e) {
            logger.error("操作异常", e);
            flag = false;
        }
        return flag;
    }

    /**
     * 获取各种佣金百分比[村级代理,县级代理...等]
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public PageForm selectBPercentage(int pageNumber, int pageSize, String companyID) {
        PageForm pageForm = this.baseBeanService.getPageForm(pageNumber, pageSize, "from BPercentage where companyId = ?", new Object[]{companyID});
        return pageForm;
    }

    /**
     * 佣金百分比添加
     */
    @Override
    public boolean BPercentageAdd(BPercentage bpercentage) {
        boolean flag = true;
        try {
            this.baseBeanService.save(bpercentage);
        } catch (Exception e) {
            logger.error("操作异常", e);
            flag = false;
        }
        return flag;
    }

    /**
     * 佣金百分比修改回显
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public BPercentage getBPercentagebyId(String brokerageId) {
        String hql = "from BPercentage where brokerageId = ?";
        return (BPercentage) this.baseBeanService.getBeanByHqlAndParams(hql, new Object[]{brokerageId});
    }

    /**
     * 佣金百分比修改
     *
     * @param bPercentage
     * @return
     */
    @Override
    public boolean BPercentageuUpdate(BPercentage bPercentage) {
        boolean flag = true;
        try {
            this.baseBeanService.update(bPercentage);
        } catch (Exception e) {
            logger.error("操作异常", e);
            flag = false;
        }
        return flag;
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public PPercentage getPPercentage(PPercentage ppercentage) {
        PPercentage pPercentage = null;
        if (ppercentage != null && (ppercentage.getCodeId() == null || ppercentage.getCodeId().equals("")) && (ppercentage.getPpId() == null || ppercentage.getPpId().equals(""))) {
            String hql1 = "from PPercentage where companyId = ? and codeId is null and ppId is null ";
            pPercentage = (PPercentage) this.baseBeanService.getBeanByHqlAndParams(hql1, new Object[]{ppercentage.getCompanyId()});
        } else {//参数校验
            String hql2 = "from PPercentage where companyId = ? and (codeId =? or ppId =?) ";
            pPercentage = (PPercentage) this.baseBeanService.
                    getBeanByHqlAndParams(hql2, new Object[]{ppercentage.getCompanyId(), ppercentage.getCodeId(), ppercentage.getPpId()});
        }
        return pPercentage;
    }

}
