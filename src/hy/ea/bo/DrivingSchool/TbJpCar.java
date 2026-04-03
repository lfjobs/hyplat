package hy.ea.bo.DrivingSchool;

import hy.plat.bo.BaseBean;

import java.io.Serializable;
import java.util.Date;

/**
 * 驾校车辆信息
 * Created by Administrator on 2017/8/11 0011.
 */

public class TbJpCar implements BaseBean,Serializable {
    private String carKey ; //主键
    private String carzjid ; // 逻辑id
    private String carId;  //车辆ID
    private String carnum;  //教练车全国唯一编号
    private String companyId; //驾校ID
    private String franum;  //车架号
    private String engnum;  //发动机号
    private String plateNumber; //车牌号码
    private String plateColor; //车牌颜色  1蓝色 2黄色 3 黑色 4白色 5 绿色 9其他
    private String photo;  //车辆照片文件ID
    private String manufacture; //生产厂家
    private String brand; //车辆品牌
    private String model;  //厂牌型号
    private String perdritype;  //培训车型 A1 A2 A3 B1 B2 C1 C2 C3 C4 C5 D E F M N P
    private Date buydate;  //购买时间
    private Date linceDispathDate;  //行驶证发证日期
    private String vehicleState;  //车辆状态
    private String skillLevel;  //技术等级 1 无 2一级 3二级 4三级
    private String fuel;  //燃油 1无 2汽油 3柴油 4其他 5 双燃油
    private String zkl;  //载客量
    private String jcqk;  //检测情况
    private String equipStatus;  //设备状况
    private String syncType;  //同步类型
    private String syncStatus;  //同步状态
    private String delFlag;  //删除标志
    private String checkStatus;  //审核状态
    private String remark;  //备注
    private String updateperson;  //更新人
    private Date createdate;  //创建时间
    private Date updatedate;  //更新时间
    private String createperson;  //创建人
    private String syncXlycStatus;  //同步状态（0待同步1同步成功2同步失败）
    private String dizhi; //地址


    public TbJpCar() {
    }

    public TbJpCar(String carKey, String carzjid, String carId, String carnum, String companyId, String franum, String engnum, String plateNumber, String plateColor, String photo, String manufacture, String brand, String model, String perdritype, Date buydate, Date linceDispathDate, String vehicleState, String skillLevel, String fuel, String zkl, String jcqk, String equipStatus, String syncType, String syncStatus, String delFlag, String checkStatus, String remark, String updateperson, Date createdate, Date updatedate, String createperson, String syncXlycStatus, String dizhi) {
        this.carKey = carKey;
        this.carzjid = carzjid;
        this.carId = carId;
        this.carnum = carnum;
        this.companyId = companyId;
        this.franum = franum;
        this.engnum = engnum;
        this.plateNumber = plateNumber;
        this.plateColor = plateColor;
        this.photo = photo;
        this.manufacture = manufacture;
        this.brand = brand;
        this.model = model;
        this.perdritype = perdritype;
        this.buydate = buydate;
        this.linceDispathDate = linceDispathDate;
        this.vehicleState = vehicleState;
        this.skillLevel = skillLevel;
        this.fuel = fuel;
        this.zkl = zkl;
        this.jcqk = jcqk;
        this.equipStatus = equipStatus;
        this.syncType = syncType;
        this.syncStatus = syncStatus;
        this.delFlag = delFlag;
        this.checkStatus = checkStatus;
        this.remark = remark;
        this.updateperson = updateperson;
        this.createdate = createdate;
        this.updatedate = updatedate;
        this.createperson = createperson;
        this.syncXlycStatus = syncXlycStatus;
        this.dizhi = dizhi;
    }

    public String getCarKey() {
        return carKey;
    }

    public void setCarKey(String carKey) {
        this.carKey = carKey;
    }

    public String getDizhi() {
        return dizhi;
    }

    public void setDizhi(String dizhi) {
        this.dizhi = dizhi;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public String getCarnum() {
        return carnum;
    }

    public void setCarnum(String carnum) {
        this.carnum = carnum;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getFranum() {
        return franum;
    }

    public void setFranum(String franum) {
        this.franum = franum;
    }

    public String getEngnum() {
        return engnum;
    }

    public void setEngnum(String engnum) {
        this.engnum = engnum;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public String getPlateColor() {
        return plateColor;
    }

    public void setPlateColor(String plateColor) {
        this.plateColor = plateColor;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getManufacture() {
        return manufacture;
    }

    public void setManufacture(String manufacture) {
        this.manufacture = manufacture;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getPerdritype() {
        return perdritype;
    }

    public void setPerdritype(String perdritype) {
        this.perdritype = perdritype;
    }

    public Date getBuydate() {
        return buydate;
    }

    public void setBuydate(Date buydate) {
        this.buydate = buydate;
    }

    public Date getLinceDispathDate() {
        return linceDispathDate;
    }

    public void setLinceDispathDate(Date linceDispathDate) {
        this.linceDispathDate = linceDispathDate;
    }

    public String getVehicleState() {
        return vehicleState;
    }

    public void setVehicleState(String vehicleState) {
        this.vehicleState = vehicleState;
    }

    public String getSkillLevel() {
        return skillLevel;
    }

    public void setSkillLevel(String skillLevel) {
        this.skillLevel = skillLevel;
    }

    public String getFuel() {
        return fuel;
    }

    public void setFuel(String fuel) {
        this.fuel = fuel;
    }

    public String getZkl() {
        return zkl;
    }

    public void setZkl(String zkl) {
        this.zkl = zkl;
    }

    public String getJcqk() {
        return jcqk;
    }

    public void setJcqk(String jcqk) {
        this.jcqk = jcqk;
    }

    public String getEquipStatus() {
        return equipStatus;
    }

    public void setEquipStatus(String equipStatus) {
        this.equipStatus = equipStatus;
    }

    public String getSyncType() {
        return syncType;
    }

    public void setSyncType(String syncType) {
        this.syncType = syncType;
    }

    public String getSyncStatus() {
        return syncStatus;
    }

    public void setSyncStatus(String syncStatus) {
        this.syncStatus = syncStatus;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(String checkStatus) {
        this.checkStatus = checkStatus;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUpdateperson() {
        return updateperson;
    }

    public void setUpdateperson(String updateperson) {
        this.updateperson = updateperson;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public Date getUpdatedate() {
        return updatedate;
    }

    public void setUpdatedate(Date updatedate) {
        this.updatedate = updatedate;
    }

    public String getCreateperson() {
        return createperson;
    }

    public void setCreateperson(String createperson) {
        this.createperson = createperson;
    }

    public String getSyncXlycStatus() {
        return syncXlycStatus;
    }

    public void setSyncXlycStatus(String syncXlycStatus) {
        this.syncXlycStatus = syncXlycStatus;
    }

    public String getCarzjid() {
        return carzjid;
    }

    public void setCarzjid(String carzjid) {
        this.carzjid = carzjid;
    }
}
