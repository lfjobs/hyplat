package hy.ea.marketing.service;


import com.tiantai.wfj.bo.PosDevice;


public interface PosDeviceManageSerivce
{
    //添加Pos终端
    public  void  addOrUpdate(PosDevice pos, String companyID, String staffID);

    //删除Pos终端
    public  void  delete(String posKey);

    /**
     * 设备号全网唯一
     * @param posNum
     * @return
     */
    public String checkRepPosNum(String posNum,String posID);






}
