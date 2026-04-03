package hy.ea.human.service.impl;

import com.mysl.bo.administrative.DtMytravel;
import com.tiantai.wfj.bo.TEshopCusCom;
import hy.ea.bo.CAccount;
import hy.ea.bo.finance.BenDis.Sign;
import hy.ea.human.service.OutworkingMobileService;
import hy.plat.bo.BaseBean;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class OutworkingMobileServiceImpl implements OutworkingMobileService {
    @Resource
    private BaseBeanService baseBeanService;
    @Resource
    private ServerService serverService;
    @Override
    public void working(String local, String img, String type, CAccount account, TEshopCusCom shopCusCom, String id) {

        //打卡操作
        Sign sign=new Sign();
        String signId = serverService.getServerID("signId");
        sign.setSignId(signId);
        sign.setAccount(shopCusCom.getAccount());
        sign.setSccid(shopCusCom.getSccId());
        sign.setCompanyId(account.getCompanyID());
        sign.setSignDate(new Date());
        sign.setSignSite(local); //地址
        sign.setSignImagePath(img);
        sign.setSignType(type);
        DtMytravel dtMytravel = queryMYTRAVEL(id);
        if (dtMytravel.getStatus().equals("02"))
        {
            sign.setStatus("T");
        }else {
            sign.setStatus("F");
        }

        baseBeanService.saveOrUpdate(sign);
    }

    private DtMytravel queryMYTRAVEL(String id) {
      return (DtMytravel) baseBeanService.getBeanByKey(DtMytravel.class,id);
    }

    @Override
    public List<BaseBean> workingList(String staffID, String companyID) {

        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = sdf.format(now);
        //查询外勤表申请表中时间在可打卡范围之内的
        List<BaseBean> beans = baseBeanService.getListBeanByHqlAndParams("FROM DtMytravel WHERE " +
                "STAFFID=?" +
                "AND COMPANYID=?" +
                "AND TRAVELSTARTDATE<=? " + "AND TRAVELENDDATE>=? ", new Object[]{staffID, companyID, formattedDate, formattedDate});
        return beans;
    }

    @Override
    public void queryWorkingListDetail(String sccid) {
        //查看该外勤打卡记录

        List<BaseBean> beans = baseBeanService.getListBeanByHqlAndParams("FROM DTWFJSIGN WHERE " +
                "STAFFID=?" +
                "AND COMPANYID=?" +
                "AND TRAVELSTARTDATE<=? " + "AND TRAVELENDDATE>=? ", new Object[]{sccid});



    }
}
