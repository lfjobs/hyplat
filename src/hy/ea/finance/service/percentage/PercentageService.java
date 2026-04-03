package hy.ea.finance.service.percentage;

import hy.ea.bo.finance.percentage.BPercentage;
import hy.ea.bo.finance.percentage.PPercentage;
import hy.plat.bo.PageForm;

/**
 * Created by Administrator on 2018-11-7.
 */
public interface PercentageService {
    PageForm selectPPercentage(int pageNumber, int pageSize, String companyID);

    boolean PPercentageAdd(PPercentage ppercentage);

    PPercentage getPPercentagebyId(String percentageId);

    boolean PPercentageuUpdate(PPercentage ppercentage);

    PageForm selectBPercentage(int pageNumber, int pageSize, String companyID);

    boolean BPercentageAdd(BPercentage bpercentage);

    BPercentage getBPercentagebyId(String brokerageId);

    boolean BPercentageuUpdate(BPercentage bPercentage);

    PPercentage getPPercentage(PPercentage ppercentage);
}
