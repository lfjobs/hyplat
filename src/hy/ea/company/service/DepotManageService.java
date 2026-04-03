package hy.ea.company.service;

import hy.plat.bo.BaseBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


public interface DepotManageService {

    /**
     * 根据仓库编码获取仓库信息
     *
     * @param depotParam code or id
     * @param companyId  公司id
     * @param t          0:全部 1：智能货柜  2：秤盘
     * @return
     */
    BaseBean getDepotToCoding(String depotParam, String companyId, int t);

    /**
     * 根据仓库父id获取仓库信息
     *
     * @param depotpid  父id
     * @param companyId 公司id
     * @param t         0:全部 1：智能货柜  2：秤盘
     * @return
     */
    List<BaseBean> getListDepotToPid(String depotpid, String companyId, int t);

    /**
     * 根据posNum获取pos设备信息
     * @param posNum 设备编号
     * @return
     */
    BaseBean getPosDeviceByPosNum(String posNum);

    /**
     * 根据公司id查询成品库房
     *
     * @param companyId
     * @return
     */
    Map<String, Object> getListDepotToComid(String companyId);

    /**
     * 查询符合规格的仓库
     *
     * @param companyId 公司id
     * @return
     */
    Map<String, Object> getParenByDepotid(String companyId);

    /**
     * 仓库数据升级
     *
     * @param companyId 公司id
     */
    String dataUpgrade(String companyId);

    /**
     * 根据名称查询数据条数（验证重名）
     *
     * @param companyId 公司id
     * @param name      仓库名称
     * @return
     */
    Map<String, Object> getCountByName(String companyId, String name);

    /**
     * 查询秤盘总数量
     *
     * @return
     */
    Integer getCountByItemId();

    /**
     * 查询符合规格的仓库
     *
     * @param depotid    仓库上级id
     * @param companyId  公司id
     * @param selectType 0：向上查询 1：向下查询 默认向下查询
     * @return
     */
    List<Object> ParenByDepotid(String depotid, String companyId, int selectType);

}
