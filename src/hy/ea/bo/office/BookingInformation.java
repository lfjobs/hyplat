package hy.ea.bo.office;

import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * 预约信息
 * @author xgb
 *
 */
public class BookingInformation implements BaseBean {

    private String bifKey;
    private String bifId;
    private String journalNum;//订单凭证号
    private String cashierBillsID;//同时保存ID 避免重复查询订单表
    private String ppID;//预约产品ID
    private String sccId;//账号Id
    private String erId;//考场id
    private String coachStaffId;//教练id
    private String directorStaffId;//主管id
    private String qrCode;//二维码url
    private String signInState;//签到状态00:未签到,01:已签到,02:已签退,03:签退失败  04 金币不足等待客户直接支付
    private String reasonForFailure;//失败原因
    private Date checkInTime;//签到时间
    private Date signBackTime;//签退时间
    private String howMuchTime;//时长
    private String money;//金额
    private Date bifTime;//生成时间

    public String getBifKey() {
        return bifKey;
    }

    public void setBifKey(String bifKey) {
        this.bifKey = bifKey;
    }

    public String getBifId() {
        return bifId;
    }

    public void setBifId(String bifId) {
        this.bifId = bifId;
    }

    public String getPpID() {
        return ppID;
    }

    public void setPpID(String ppID) {
        this.ppID = ppID;
    }

    public String getJournalNum() {
        return journalNum;
    }

    public void setJournalNum(String journalNum) {
        this.journalNum = journalNum;
    }

    public String getSccId() {
        return sccId;
    }

    public void setSccId(String sccId) {
        this.sccId = sccId;
    }

    public String getErId() {
        return erId;
    }

    public void setErId(String erId) {
        this.erId = erId;
    }

    public String getCoachStaffId() {
        return coachStaffId;
    }

    public void setCoachStaffId(String coachStaffId) {
        this.coachStaffId = coachStaffId;
    }

    public String getDirectorStaffId() {
        return directorStaffId;
    }

    public void setDirectorStaffId(String directorStaffId) {
        this.directorStaffId = directorStaffId;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public String getSignInState() {
        return signInState;
    }

    public void setSignInState(String signInState) {
        this.signInState = signInState;
    }

    public Date getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(Date checkInTime) {
        this.checkInTime = checkInTime;
    }

    public Date getSignBackTime() {
        return signBackTime;
    }

    public void setSignBackTime(Date signBackTime) {
        this.signBackTime = signBackTime;
    }

    public Date getBifTime() {
        return bifTime;
    }

    public void setBifTime(Date bifTime) {
        this.bifTime = bifTime;
    }

    public String getHowMuchTime() {
        return howMuchTime;
    }

    public void setHowMuchTime(String howMuchTime) {
        this.howMuchTime = howMuchTime;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getReasonForFailure() {
        return reasonForFailure;
    }

    public void setReasonForFailure(String reasonForFailure) {
        this.reasonForFailure = reasonForFailure;
    }

    public String getCashierBillsID() {
        return cashierBillsID;
    }

    public void setCashierBillsID(String cashierBillsID) {
        this.cashierBillsID = cashierBillsID;
    }
}
