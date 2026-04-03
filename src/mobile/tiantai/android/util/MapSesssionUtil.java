package mobile.tiantai.android.util;

import cn.jpush.api.utils.StringUtils;
import com.opensymphony.xwork2.ActionContext;
import hy.ea.bo.CAccount;
import hy.plat.service.BaseBeanService;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 以Map形式保存session公共方法
 * Created by Administrator on 2019-10-28.
 */
public class MapSesssionUtil {

    private static ActionContext actionContext;//获取session用
    private static Map<String, Object> paramMap;//接收session用

    /**
     * 获取Map<String, Object> 形式保存的session
     *
     * @param parameter session key值
     * @return Map<String, Object>
     */
    public static Map<String, Object> getSessionForMap(String parameter) {
        return (Map<String, Object>) actionContext.getContext().getSession().get(parameter);
    }

    /**
     * 获取List<Object> 形式保存的session
     *
     * @param parameter session key值
     * @return List<Object>
     */
    public static List<Object> getSessionForList(String parameter) {
        return (List<Object>) actionContext.getContext().getSession().get(parameter);
    }

    /**
     * 保存session
     *
     * @param key   key
     * @param value 参数值
     * @return
     */
    public static Map<String, Object> saveSessionForMap(String key, Object value) {
        actionContext.getContext().getSession().put(key, value);
        return getSessionForMap(key);
    }

    /**
     * 讲list保存到session
     *
     * @param key   key
     * @param value 参数值
     * @return
     */
    public static List<Object> saveSessionForList(String key, Object value) {
        actionContext.getContext().getSession().put(key, value);
        return getSessionForList(key);
    }

    /**
     * 保存session
     *
     * @return
     */
    public static void removeSession(String parameter) {
        actionContext.getContext().getSession().remove(parameter);
    }

    /**
     * 以Map形式保存手机端传过来的预算单所需参数
     *
     * @param companyId 公司id
     * @param staffId   登录人id
     */
    public static Map<String, Object> saveYsdSessionForMap(String companyId, String staffId) {
        //将手机端传入的参数放入session中
        paramMap = new HashMap<String, Object>();
        paramMap.put("companyId", companyId);//公司id
        paramMap.put("staffId", staffId);//staff表员工id手机端传过来的
        return paramMap;
    }

    /**
     * 以Map形式保存批发商城后退所需参数
     *
     * @param companyId   公司id
     * @param companyName 公司名称
     */
    public static Map<String, Object> savePfsc_back_SessionForMap(String companyId, String companyName) {
        //将手机端传入的参数放入session中
        paramMap = new HashMap<String, Object>();
        paramMap.put("ccompanyId", companyId);//公司id
        paramMap.put("companyName", companyName);//公司名称
        return paramMap;
    }

    public static Map<String, Object> saveYsdSessionForMapNew(String companyId, String staffId) {
        //将手机端传入的参数放入session中
        if (StringUtils.isNotEmpty(companyId) && StringUtils.isNotEmpty(staffId)) {
            paramMap = new HashMap<String, Object>();
            paramMap.put("companyId", companyId);//公司id
            paramMap.put("staffId", staffId);//staff表员工id手机端传过来的
        }
        return paramMap;
    }

    public static Object getSessionForObject(String parameter) {
        return  (Object)actionContext.getContext().getSession().get(parameter);
    }

    /**
     * 保存session
     *
     * @param key   key
     * @param value 参数值
     * @return
     */
    public static Object saveSessionForObject(String key, Object value) {
        actionContext.getContext().getSession().put(key, value);
        return getSessionForObject(key);
    }
}
