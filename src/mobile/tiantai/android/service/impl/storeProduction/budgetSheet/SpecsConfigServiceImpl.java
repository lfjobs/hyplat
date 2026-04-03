package mobile.tiantai.android.service.impl.storeProduction.budgetSheet;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.RandomUtil;
import hy.ea.bo.finance.PerformanceManagement;
import hy.ea.bo.finance.vo.SpecsConfigAddDTO;
import hy.ea.bo.finance.vo.SpecsConfigListVO;
import hy.plat.bo.BaseBean;
import hy.plat.dao.impl.BaseBeanDao;
import mobile.tiantai.android.bo.DtSpecsConfig;
import mobile.tiantai.android.service.storeProduction.budgetSheet.SpecsConfigService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SpecsConfigServiceImpl implements SpecsConfigService {

    @Resource
    private BaseBeanDao baseBeanDao;


    @Override
    public List<SpecsConfigListVO> getSpecsList() throws Exception {
        List<SpecsConfigListVO> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT        a.SPECS, \n" +
                "                 REPLACE ( wm_concat ( b.SPECS ), ',', 'x' ) AS SPECS_TYPE, \n" +
                "                 b.SPECS_CODE  ,\n" +
                "                     a.ID \n" +
                "                FROM \n" +
                "                 DT_SPECS_CONFIG a \n" +
                "                 LEFT JOIN ( SELECT * FROM DT_SPECS_CONFIG WHERE STATE = '0' AND SPECS_LEVEL = 2 ORDER BY SPECS_ORDER ) b ON a.ID = b.PARENT_ID  \n" +
                "                 AND b.SPECS_LEVEL = 2  \n" +
                "                WHERE \n" +
                "                 a.SPECS_LEVEL = 1  and b.SPECS_CODE is not NULL \n" +
                "               GROUP BY \n" +
                "                 a.SPECS, \n" +
                "                 a.ID ,\n" +
                "                 b.SPECS_CODE order by specs");
        List<Object> result = baseBeanDao.getListObjectBySqlAndParams(sql.toString(), new Object[]{});

        if (CollectionUtil.isNotEmpty(result)) {
            for (Object obj : result) {
                Object[] o = (Object[]) obj;
                SpecsConfigListVO recent = new SpecsConfigListVO();
                recent.setSpecs(o[0] + "");
                recent.setSpecsType(o[1] + "");
                recent.setSpecsCode(o[2] + "");
                recent.setId(o[3] + "");
                list.add(recent);
            }
        }
        return list;
    }

    @Override
    public DtSpecsConfig findConfigByType(String specsType) {
        List<SpecsConfigListVO> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM DT_SPECS_CONFIG WHERE SPECS_LEVEL = '1' and SPECS = ? ");
        Object result = baseBeanDao.getObjectBySqlAndParams(sql.toString(), new Object[]{specsType});
        if (!Optional.ofNullable(result).isPresent()) {
            return null;
        }
        Object[] o = (Object[]) result;
        DtSpecsConfig dtSpecsConfig = new DtSpecsConfig();
        dtSpecsConfig.setId(o[0] + "");
        dtSpecsConfig.setSpecs(o[1] + "");
        dtSpecsConfig.setSpecsLevel(o[2] + "");
        dtSpecsConfig.setSpecsCode(o[3] + "");
        dtSpecsConfig.setSpecsOrder(null != o[4] ? Integer.parseInt(String.valueOf(o[4])) : null);
        dtSpecsConfig.setParentId(o[5] + "");
        return dtSpecsConfig;
    }

    @Override
    public DtSpecsConfig findConfigByType1(String specsType) {
        {
            List<SpecsConfigListVO> list = new ArrayList<>();
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT * FROM DT_SPECS_CONFIG WHERE SPECS_LEVEL = '1' and id = ? ");
            Object result = baseBeanDao.getObjectBySqlAndParams(sql.toString(), new Object[]{specsType});
            if (!Optional.ofNullable(result).isPresent()) {
                return null;
            }
            Object[] o = (Object[]) result;
            DtSpecsConfig dtSpecsConfig = new DtSpecsConfig();
            dtSpecsConfig.setId(o[0] + "");
            dtSpecsConfig.setSpecs(o[1] + "");
            dtSpecsConfig.setSpecsLevel(o[2] + "");
            dtSpecsConfig.setSpecsCode(o[3] + "");
            dtSpecsConfig.setSpecsOrder(null != o[4] ? Integer.parseInt(String.valueOf(o[4])) : null);
            dtSpecsConfig.setParentId(o[5] + "");
            return dtSpecsConfig;
        }
    }



    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addSpecsConfig(SpecsConfigAddDTO specsConfigAddDTO) throws Exception {
        //查询单位是否存在
        DtSpecsConfig dtSpecsConfig = this.findConfigByType(specsConfigAddDTO.getSpecsType());
        //生成分组
        String specsCode = RandomUtil.randomStringUpper(5);

        //保存或者获取单位信息
        String parentId = UUID.randomUUID().toString().replaceAll("-", "");
        if (Optional.ofNullable(dtSpecsConfig).isPresent()) {
            parentId = dtSpecsConfig.getId();
        } else {
            //保存单位信息
            DtSpecsConfig specs = new DtSpecsConfig();
            specs.setSpecs(specsConfigAddDTO.getSpecsType());
            specs.setId(parentId);
            specs.setSpecsLevel("1");
            specs.setParentId("0");
            specs.setState("0");
            baseBeanDao.save(specs);
        }
        //保存规格信息 === 如果字段多可以用反射取值
        List<String> specsTypes = new ArrayList<>();
        if (StringUtils.isNotEmpty(specsConfigAddDTO.getSpecsA())) {
            specsTypes.add(specsConfigAddDTO.getSpecsA());
        }
        if (StringUtils.isNotEmpty(specsConfigAddDTO.getSpecsB())) {
            specsTypes.add(specsConfigAddDTO.getSpecsB());
        }
        if (StringUtils.isNotEmpty(specsConfigAddDTO.getSpecsC())) {
            specsTypes.add(specsConfigAddDTO.getSpecsC());
        }
        if (StringUtils.isNotEmpty(specsConfigAddDTO.getSpecsD())) {
            specsTypes.add(specsConfigAddDTO.getSpecsD());
        }
        for (int i = 0; i < specsTypes.size(); i++) {
            DtSpecsConfig specsType = new DtSpecsConfig();
            specsType.setId(UUID.randomUUID().toString().replaceAll("-", ""));
            specsType.setSpecs(specsTypes.get(i));
            specsType.setSpecsLevel("2");
            specsType.setParentId(parentId);
            specsType.setSpecsCode(specsCode);
            specsType.setState("0");
            specsType.setSpecsOrder(i);
            baseBeanDao.save(specsType);
        }
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delSpecsConfig(SpecsConfigAddDTO specsConfigAddDTO) {
        //删除只能根据分组来删除
        //查询单位是否存在
        DtSpecsConfig dtSpecsConfig = this.findConfigByType(specsConfigAddDTO.getSpecsType());
        if (!Optional.ofNullable(dtSpecsConfig).isPresent()) {
            return;
        }
        // String hql = "DELETE FROM DtSpecsConfig WHERE SPECS_CODE = ? and PARENT_ID = ? ";
        String hql = "UPDATE DtSpecsConfig SET STATE = '1'  WHERE SPECS_CODE = ? and PARENT_ID = ? ";
        List<Object[]> parmsList = new ArrayList<Object[]>();
        Object[] param = new Object[]{specsConfigAddDTO.getSpecsCode(), dtSpecsConfig.getId()};
        parmsList.add(param);
        baseBeanDao.executeHqlsByParmsList(null, new String[]{hql}, parmsList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delSpecsConfig1(SpecsConfigAddDTO specsConfigAddDTO) {
        //删除只能根据分组来删除
        //查询单位是否存在
        DtSpecsConfig dtSpecsConfig = this.findConfigByType1(specsConfigAddDTO.getId());
        if (!Optional.ofNullable(dtSpecsConfig).isPresent()) {
            return;
        }
//         String hql = "DELETE FROM DtSpecsConfig WHERE SPECS_CODE = ? and PARENT_ID = ? ";
        String hql = "DELETE FROM DtSpecsConfig WHERE SPECS_CODE = ? and PARENT_ID = ? ";
        List<Object[]> parmsList = new ArrayList<Object[]>();
        Object[] param = new Object[]{specsConfigAddDTO.getSpecsCode(), dtSpecsConfig.getId()};
        parmsList.add(param);
        baseBeanDao.executeHqlsByParmsList(null, new String[]{hql}, parmsList);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateSpecsConfig(SpecsConfigAddDTO specsConfigAddDTO) {
//        1修改单位，是根据单位的id进行修改,2修改规格，根据单位的分组全部删除再进行添加
//        单位修改
        String hql = "UPDATE DtSpecsConfig SET SPECS = ? WHERE id  = ? ";
        List<Object[]> parmsList = new ArrayList<Object[]>();
        Object[] param = new Object[]{specsConfigAddDTO.getSpecsType(), specsConfigAddDTO.getId()};
        parmsList.add(param);
        baseBeanDao.executeHqlsByParmsList(null, new String[]{hql}, parmsList);
        //根据分组删除数据
        this.delSpecsConfig1(specsConfigAddDTO);

        //保存规格信息 === 如果字段多可以用反射取值
        List<String> specsTypes = new ArrayList<>();
        if (StringUtils.isNotEmpty(specsConfigAddDTO.getSpecsA())) {
            specsTypes.add(specsConfigAddDTO.getSpecsA());
        }
        if (StringUtils.isNotEmpty(specsConfigAddDTO.getSpecsB())) {
            specsTypes.add(specsConfigAddDTO.getSpecsB());
        }
        if (StringUtils.isNotEmpty(specsConfigAddDTO.getSpecsC())) {
            specsTypes.add(specsConfigAddDTO.getSpecsC());
        }
        if (StringUtils.isNotEmpty(specsConfigAddDTO.getSpecsD())) {
            specsTypes.add(specsConfigAddDTO.getSpecsD());
        }
        for (int i = 0; i < specsTypes.size(); i++) {
            DtSpecsConfig specsType = new DtSpecsConfig();
            specsType.setId(UUID.randomUUID().toString().replaceAll("-", ""));
            specsType.setSpecs(specsTypes.get(i));
            specsType.setState("0");
            specsType.setSpecsLevel("2");
            specsType.setParentId(specsConfigAddDTO.getId());
            specsType.setSpecsCode(specsConfigAddDTO.getSpecsCode());
            specsType.setSpecsOrder(i);
            baseBeanDao.save(specsType);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SpecsConfigAddDTO updateSpecsInfo(SpecsConfigAddDTO specsConfigAddDTO) {

        String sql = "SELECT * FROM DT_SPECS_CONFIG   WHERE SPECS_CODE = ? OR  SPECS  = ? ";
        List<Object[]> parmsList = new ArrayList<Object[]>();
        Object[] param = new Object[]{specsConfigAddDTO.getSpecsCode(), specsConfigAddDTO.getSpecsType()};
        parmsList.add(param);
        List<Object> result = baseBeanDao.getListObjectBySqlAndParams(sql.toString(), new Object[]{specsConfigAddDTO.getSpecsCode(), specsConfigAddDTO.getSpecsType()});
        if (!Optional.ofNullable(result).isPresent()) {
            return null;
        }
        SpecsConfigAddDTO specsConfigAddDTO1 = new SpecsConfigAddDTO();
        for (Object obj : result) {
            Object[] o = (Object[]) obj;
            if (o[2].equals("1")) {
                //规格单位
                specsConfigAddDTO1.setSpecsType(o[1] + "");
                specsConfigAddDTO1.setId(o[0] + "");
            }
            String b = o[4] + "";
            if (o[2].equals("2")) {
                if (o[4] != null && (o[4] + "").equals("0")) {
                    //A
                    specsConfigAddDTO1.setSpecsA(o[1] + "");
                }
                if (o[4] != null && (o[4] + "").equals("1")) {
                    //B
                    specsConfigAddDTO1.setSpecsB(o[1] + "");
                }
                if (o[4] != null && (o[4] + "").equals("2")) {
                    //C
                    specsConfigAddDTO1.setSpecsC(o[1] + "");
                }
                if (o[4] != null && (o[4] + "").equals("3")) {
                    //D
                    specsConfigAddDTO1.setSpecsD(o[1] + "");
                }
                if (o[4] != null && (o[4] + "").equals("0")) {

                    specsConfigAddDTO1.setSpecsCode(o[3] + "");
                }
//                if (o[4] != null && (o[4] + "").equals("0")) {
//
//                    specsConfigAddDTO1.setSpecsType(o[0] + "");
//                }

            }

        }


        //  Object[] o = (Object[]) result;

        //  specsConfigAddDTO1.setId(o[0] + "");
//        specsConfigAddDTO1.setSpecs(o[1] + "");
//        specsConfigAddDTO1.setSpecsLevel(o[2] + "");
//        specsConfigAddDTO1.setSpecsCode(o[3] + "");
//        specsConfigAddDTO1.setSpecsOrder(null != o[4] ? Integer.parseInt(String.valueOf(o[4])) : null);
//        specsConfigAddDTO1.setParentId(o[5] + "");
        return specsConfigAddDTO1;
    }


    public List<BaseBean> getPerformanceManagementByParentId(String parentId) {
        StringBuffer hql = new StringBuffer(" from PerformanceManagement o where o.parentId=? and o.delFlag='1' order by o.createTime");
        List<Object> params = new ArrayList<>();
        params.add(parentId);
        List<BaseBean> list = baseBeanDao.getListBeanByHqlAndParams(hql.toString(), params.toArray());
        return list;
    }

    @Override
    public boolean isExistName(String name, String parentId) {
        StringBuffer hql = new StringBuffer(" from PerformanceManagement o where o.name=? and o.delFlag='1'");
        List<Object> params = new ArrayList<>();
        params.add(name);
        if (StringUtils.isNotEmpty(parentId)) {
            hql.append(" and o.parentId=? ");
            params.add(parentId);
        } else {
            hql.append(" and o.parentId is null ");
        }
        hql.append("  order by o.createTime ");
        List<BaseBean> list = baseBeanDao.getListBeanByHqlAndParams(hql.toString(), params.toArray());
        return CollectionUtil.isNotEmpty(list);
    }

    @Transactional
    @Override
    public void createPerformanceManagement(PerformanceManagement info) {
        baseBeanDao.save(info);
    }

    @Transactional
    @Override
    public void updatePerformanceManagement(PerformanceManagement info) {
        baseBeanDao.update(info);
    }

    @Override
    public PerformanceManagement getPerformanceManagementByKey(String key) {
        return (PerformanceManagement)baseBeanDao.getBeanByKey(PerformanceManagement.class, key);
    }

    @Override
    public PerformanceManagement getPerformanceManagementById(String id) {
        String hql = " from PerformanceManagement o where o.id=? ";
        return (PerformanceManagement)baseBeanDao.getBeanByHqlAndParams(hql,new Object[]{id});
    }

    @Override
    public List<BaseBean> getPerformanceManagementRootList() {
        String hql = " from PerformanceManagement o where o.parentId is null and o.delFlag='1'  order by o.createTime";
        List<BaseBean> list = baseBeanDao.getListBeanByHqlAndParams(hql, null);
        return list;
    }
}
