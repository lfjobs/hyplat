package hy.ea.finance.billTimeClass;

import hy.plat.bo.BaseBean;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface BillDayService {
    void convertFromModel(BaseBean bean, int type, List<BaseBean> beanList);

    /**
     *
     * @param parameter 参数值
     * @param type 参数分类
     *             1:id
     *             2:单据号
     * @return Map
     *         falg 返回值状态:
     *                  00：正常
     *                  01：参数值为空
     *                  02：参数值格式不正确
     *                  03: 参数日期不在查询范围内
     *         c_s:sql单据表名称
     *         c_h:hql单据表名称
     *         g_s:sql单据产品表名称
     *         g_h:hql单据产品表名称
     */
    Map gatTableNameByPar(String parameter, int type) throws ParseException;

    /**
     *
     * @param tableType 表明类型
     *               1：c_s-sql单据表名称
     *               2：c_h-hql单据表名称
     *               3：g_s-sql单据产品表名称
     *               4：g_h-hql单据产品表名称
     * @param parameter 参数值
     * @param type 参数分类
     *             1:id
     *             2:单据号
     * @return
     * @throws ParseException
     */
    String getTableNeame(int tableType,String parameter,int type) throws ParseException;
}
