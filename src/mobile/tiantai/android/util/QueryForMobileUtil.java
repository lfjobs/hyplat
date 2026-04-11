package mobile.tiantai.android.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import hy.plat.service.BaseBeanService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

/**
 * 公共查询页面
 * Created by Administrator on 2019-10-18.
 */
@Controller
@Scope("prototype")
public class QueryForMobileUtil {
	private static final Logger logger = LoggerFactory.getLogger(QueryForMobileUtil.class);
    @Resource
    private BaseBeanService baseBeanService;

    //跳转类型（YSD_LB:预算单列表）
    private String jumpType;
    private StringBuffer innerHtml;

    /**
     * 根据登录帐号查询展示组织机构名称
     *
     * @return
     */
    public String toQuery() {
        try {
            logger.info("跳转查询: {}", jumpType);
            innerHtml = new StringBuffer();
            switch (jumpType) {
                case "YSD_LB"://预算单列表
                    innerHtml.append("<li class=\"ttsw_li\" onclick=\"toCheck(this,2);\">项目名称</li>");
                    innerHtml.append("<li class=\"ttsw_li\" onclick=\"toCheck(this,3);\">凭证号</li>");
                    innerHtml.append("<li class=\"ttsw_li\" onclick=\"toCheck(this,4);\">责任人</li>");
                    innerHtml.append("<li class=\"ttsw_li\" onclick=\"toCheck(this,11);\">制单人</li>");
                    break;
                default://查询其他
                    innerHtml.append("<li class=\"ttsw_li\" onclick=\"toCheck(this,1);\">商品名称</li>");
                    innerHtml.append("<li class=\"ttsw_li\" onclick=\"toCheck(this,2);\">项目名称</li>");
                    innerHtml.append("<li class=\"ttsw_li\" onclick=\"toCheck(this,3);\">凭证号</li>");
                    innerHtml.append("<li class=\"ttsw_li\" onclick=\"toCheck(this,4);\">责任人</li>");
                    innerHtml.append("<li class=\"ttsw_li\" onclick=\"toCheck(this,5);\">制单时间</li>");
                    innerHtml.append("<li class=\"ttsw_li\" onclick=\"toCheck(this,6);\">规格</li>");
                    innerHtml.append("<li class=\"ttsw_li\" onclick=\"toCheck(this,7);\">已申</li>");
                    innerHtml.append("<li class=\"ttsw_li\" onclick=\"toCheck(this,8);\">未审</li>");
                    innerHtml.append("<li class=\"ttsw_li\" onclick=\"toCheck(this,9);\">机构</li>");
                    innerHtml.append("<li class=\"ttsw_li\" onclick=\"toCheck(this,10);\">单据状态</li>");
                    innerHtml.append("<li class=\"ttsw_li\" onclick=\"toCheck(this,11);\">制单人</li>");
                    innerHtml.append("<li class=\"ttsw_li\" onclick=\"toCheck(this,12);\">物品类型</li>");
            }
        } catch (Exception e) {
            logger.error("操作异常", e);
        }
        return "toQuery";
    }

    /**
     * 招标投标查询页面跳转
     *
     * @return
     */
    public String toCostBudgetQuery() {
        try {
            logger.info("跳转查询: {}", jumpType);
            innerHtml = new StringBuffer();
            switch (jumpType) {
                case "DJFB_LB"://招标投标列表
                    innerHtml.append("<li class=\"ttsw_li\" onclick=\"toCheck(this,2);\">项目名称</li>");
                    innerHtml.append("<li class=\"ttsw_li\" onclick=\"toCheck(this,3);\">凭证号</li>");
                    innerHtml.append("<li class=\"ttsw_li\" onclick=\"toCheck(this,4);\">责任人</li>");
                    innerHtml.append("<li class=\"ttsw_li\" onclick=\"toCheck(this,11);\">制单人</li>");
                    break;
                default://查询其他
                    innerHtml.append("<li class=\"ttsw_li\" onclick=\"toCheck(this,1);\">商品名称</li>");
                    innerHtml.append("<li class=\"ttsw_li\" onclick=\"toCheck(this,2);\">项目名称</li>");
                    innerHtml.append("<li class=\"ttsw_li\" onclick=\"toCheck(this,3);\">凭证号</li>");
                    innerHtml.append("<li class=\"ttsw_li\" onclick=\"toCheck(this,4);\">责任人</li>");
                    innerHtml.append("<li class=\"ttsw_li\" onclick=\"toCheck(this,5);\">制单时间</li>");
                    innerHtml.append("<li class=\"ttsw_li\" onclick=\"toCheck(this,6);\">规格</li>");
                    innerHtml.append("<li class=\"ttsw_li\" onclick=\"toCheck(this,7);\">已申</li>");
                    innerHtml.append("<li class=\"ttsw_li\" onclick=\"toCheck(this,8);\">未审</li>");
                    innerHtml.append("<li class=\"ttsw_li\" onclick=\"toCheck(this,9);\">机构</li>");
                    innerHtml.append("<li class=\"ttsw_li\" onclick=\"toCheck(this,10);\">单据状态</li>");
                    innerHtml.append("<li class=\"ttsw_li\" onclick=\"toCheck(this,11);\">制单人</li>");
                    innerHtml.append("<li class=\"ttsw_li\" onclick=\"toCheck(this,12);\">物品类型</li>");
            }
        } catch (Exception e) {
            logger.error("操作异常", e);
        }
        return "toCostBudgetQuery";
    }

    /**
     * 项目计划查询页面跳转
     *
     * @return
     */
    public String toPlanCostBudgetQuery() {
        try {
            logger.info("跳转查询: {}", jumpType);
            innerHtml = new StringBuffer();
            switch (jumpType) {
                case "XMJH_LB"://项目计划列表
                    innerHtml.append("<li class=\"ttsw_li\" onclick=\"toCheck(this,2);\">项目名称</li>");
                    innerHtml.append("<li class=\"ttsw_li\" onclick=\"toCheck(this,3);\">凭证号</li>");
                    innerHtml.append("<li class=\"ttsw_li\" onclick=\"toCheck(this,4);\">责任人</li>");
                    innerHtml.append("<li class=\"ttsw_li\" onclick=\"toCheck(this,11);\">制单人</li>");
                    break;
                default://查询其他
                    innerHtml.append("<li class=\"ttsw_li\" onclick=\"toCheck(this,1);\">商品名称</li>");
                    innerHtml.append("<li class=\"ttsw_li\" onclick=\"toCheck(this,2);\">项目名称</li>");
                    innerHtml.append("<li class=\"ttsw_li\" onclick=\"toCheck(this,3);\">凭证号</li>");
                    innerHtml.append("<li class=\"ttsw_li\" onclick=\"toCheck(this,4);\">责任人</li>");
                    innerHtml.append("<li class=\"ttsw_li\" onclick=\"toCheck(this,5);\">制单时间</li>");
                    innerHtml.append("<li class=\"ttsw_li\" onclick=\"toCheck(this,6);\">规格</li>");
                    innerHtml.append("<li class=\"ttsw_li\" onclick=\"toCheck(this,7);\">已申</li>");
                    innerHtml.append("<li class=\"ttsw_li\" onclick=\"toCheck(this,8);\">未审</li>");
                    innerHtml.append("<li class=\"ttsw_li\" onclick=\"toCheck(this,9);\">机构</li>");
                    innerHtml.append("<li class=\"ttsw_li\" onclick=\"toCheck(this,10);\">单据状态</li>");
                    innerHtml.append("<li class=\"ttsw_li\" onclick=\"toCheck(this,11);\">制单人</li>");
                    innerHtml.append("<li class=\"ttsw_li\" onclick=\"toCheck(this,12);\">物品类型</li>");
            }
        } catch (Exception e) {
            logger.error("操作异常", e);
        }
        return "toPlanCostBudgetQuery";
    }

    /**
     * GET AND SET METHOD
     */
    public String getJumpType() {
        return jumpType;
    }

    public void setJumpType(String jumpType) {
        this.jumpType = jumpType;
    }

    public StringBuffer getInnerHtml() {
        return innerHtml;
    }

    public void setInnerHtml(StringBuffer innerHtml) {
        this.innerHtml = innerHtml;
    }
}
