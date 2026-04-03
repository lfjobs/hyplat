package hy.ea.bo.office;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

/**
 * Dtcarassemblytable entity. @author MyEclipse Persistence Tools
 *车辆组件信息详情表
 */

public class Dtcarassemblytable implements BaseBean,ExcelBean {

	//评分标准
	// 00   优     +0.5
	// 01   良    
	// 02   差    -0.5
	private String assemblykey;    
	private String assemblyid;
	private String safetyid;
	private String companyid;
	private String organizationid;
	private String carid;    
	private String boxcar;    //车厢 00 01 02
	private String cartrunk;   //车辆后备箱
	private String floormats;  //脚垫
	private String cushion;  //坐垫
	private String carbody;    //车身
	private String glass;   //玻璃
	private String doorwindows; //门窗
	private String engine;   //发动机
	private String braking;   //刹车
	private String biglight;//大灯
	private String soslight; //急救灯
	private String fogprooflight;  //防雾灯
	private String turnofflight;  //转弯灯
	private String createpeople;  //创建人
	private String createtime;  //创建时间
	private String lastupdatatime;  //最后修改时间
	private String lastupname;  //最后修改人

	// Constructors

	/** default constructor */
	public Dtcarassemblytable() {
	}

	/** full constructor */
	public Dtcarassemblytable(String assemblyid, String safetyid,
			String companyid, String organizationid, String carid,
			String boxcar, String cartrunk, String floormats, String cushion,
			String carbody, String glass, String doorwindows, String engine,
			String braking, String biglight, String soslight,
			String fogprooflight, String turnofflight, String createpeople,
			String createtime, String lastupdatatime, String lastupname) {
		this.assemblyid = assemblyid;
		this.safetyid = safetyid;
		this.companyid = companyid;
		this.organizationid = organizationid;
		this.carid = carid;
		this.boxcar = boxcar;
		this.cartrunk = cartrunk;
		this.floormats = floormats;
		this.cushion = cushion;
		this.carbody = carbody;
		this.glass = glass;
		this.doorwindows = doorwindows;
		this.engine = engine;
		this.braking = braking;
		this.biglight = biglight;
		this.soslight = soslight;
		this.fogprooflight = fogprooflight;
		this.turnofflight = turnofflight;
		this.createpeople = createpeople;
		this.createtime = createtime;
		this.lastupdatatime = lastupdatatime;
		this.lastupname = lastupname;
	}

	// Property accessors

	public String getAssemblykey() {
		return this.assemblykey;
	}

	public void setAssemblykey(String assemblykey) {
		this.assemblykey = assemblykey;
	}

	public String getAssemblyid() {
		return this.assemblyid;
	}

	public void setAssemblyid(String assemblyid) {
		this.assemblyid = assemblyid;
	}

	public String getSafetyid() {
		return this.safetyid;
	}

	public void setSafetyid(String safetyid) {
		this.safetyid = safetyid;
	}

	public String getCompanyid() {
		return this.companyid;
	}

	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}

	public String getOrganizationid() {
		return this.organizationid;
	}

	public void setOrganizationid(String organizationid) {
		this.organizationid = organizationid;
	}

	public String getCarid() {
		return this.carid;
	}

	public void setCarid(String carid) {
		this.carid = carid;
	}

	public String getBoxcar() {
		return this.boxcar;
	}

	public void setBoxcar(String boxcar) {
		this.boxcar = boxcar;
	}

	public String getCartrunk() {
		return this.cartrunk;
	}

	public void setCartrunk(String cartrunk) {
		this.cartrunk = cartrunk;
	}

	public String getFloormats() {
		return this.floormats;
	}

	public void setFloormats(String floormats) {
		this.floormats = floormats;
	}

	public String getCushion() {
		return this.cushion;
	}

	public void setCushion(String cushion) {
		this.cushion = cushion;
	}

	public String getCarbody() {
		return this.carbody;
	}

	public void setCarbody(String carbody) {
		this.carbody = carbody;
	}

	public String getGlass() {
		return this.glass;
	}

	public void setGlass(String glass) {
		this.glass = glass;
	}

	public String getDoorwindows() {
		return this.doorwindows;
	}

	public void setDoorwindows(String doorwindows) {
		this.doorwindows = doorwindows;
	}

	public String getEngine() {
		return this.engine;
	}

	public void setEngine(String engine) {
		this.engine = engine;
	}

	public String getBraking() {
		return this.braking;
	}

	public void setBraking(String braking) {
		this.braking = braking;
	}

	public String getBiglight() {
		return this.biglight;
	}

	public void setBiglight(String biglight) {
		this.biglight = biglight;
	}

	public String getSoslight() {
		return this.soslight;
	}

	public void setSoslight(String soslight) {
		this.soslight = soslight;
	}

	public String getFogprooflight() {
		return this.fogprooflight;
	}

	public void setFogprooflight(String fogprooflight) {
		this.fogprooflight = fogprooflight;
	}

	public String getTurnofflight() {
		return this.turnofflight;
	}

	public void setTurnofflight(String turnofflight) {
		this.turnofflight = turnofflight;
	}

	public String getCreatepeople() {
		return this.createpeople;
	}

	public void setCreatepeople(String createpeople) {
		this.createpeople = createpeople;
	}

	public String getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public String getLastupdatatime() {
		return this.lastupdatatime;
	}

	public void setLastupdatatime(String lastupdatatime) {
		this.lastupdatatime = lastupdatatime;
	}

	public String getLastupname() {
		return this.lastupname;
	}

	public void setLastupname(String lastupname) {
		this.lastupname = lastupname;
	}

	@Override
	public String[] properties() {
		// TODO Auto-generated method stub
		return null;
	}

}