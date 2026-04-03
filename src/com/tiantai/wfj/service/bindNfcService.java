package com.tiantai.wfj.service;

import com.tiantai.wfj.bo.NfcChip;
import org.hibernate.criterion.DetachedCriteria;

public interface bindNfcService {

    /**
     * 读取NFC芯片
     * @param sn 序列号
     * @return
     */
    boolean readNfc(String sn);

    /**
     * 绑定nfc
     * @param nfc
     * @return
     */
    boolean saveNfc(NfcChip nfc);

    /**
     * 解绑
     * @param id
     * @return
     */
    boolean updateNfc(String id);

    /**
     * 根据序列号查询nfc记录
     *
     * @param sn 序列号
     * @return
     */
    NfcChip getNfcBySn(String sn);

    /**
     * nfc列表
     * @param comid 公司id
     * @param bindState 绑定状态
     * @param staffname 责任人
     * @param ln 绑定状态
     * @param oaskName 安全类别
     * @return
     */
    DetachedCriteria getNfcList(String comid, String bindState, String staffname, String ln, String oaskName);

    NfcChip getNfcById(String id);
}
