package hy.ea.bo.invoicing;

import hy.plat.bo.BaseBean;

/**
 * 项目中车辆
 * @author mz
 *
 */
public class ProjectMCar implements BaseBean,java.io.Serializable{
	
	private String mcKey;			        //主键
	private String mcID;         			//业务主键
    private String carID;//车辆ID
    private String carCode;//车辆编号
    private String carNum;//车牌号
    private String engineNum;//发动机号
    private String carFrameNum;//车架号
	private String projectID;//项目ID
	public String getMcKey() {
		return mcKey;
	}
	public void setMcKey(String mcKey) {
		this.mcKey = mcKey;
	}
	public String getMcID() {
		return mcID;
	}
	public void setMcID(String mcID) {
		this.mcID = mcID;
	}
	public String getCarID() {
		return carID;
	}
	public void setCarID(String carID) {
		this.carID = carID;
	}
	public String getCarCode() {
		return carCode;
	}
	public void setCarCode(String carCode) {
		this.carCode = carCode;
	}
	public String getCarNum() {
		return carNum;
	}
	public void setCarNum(String carNum) {
		this.carNum = carNum;
	}
	public String getEngineNum() {
		return engineNum;
	}
	public void setEngineNum(String engineNum) {
		this.engineNum = engineNum;
	}
	public String getCarFrameNum() {
		return carFrameNum;
	}
	public void setCarFrameNum(String carFrameNum) {
		this.carFrameNum = carFrameNum;
	}
	public String getProjectID() {
		return projectID;
	}
	public void setProjectID(String projectID) {
		this.projectID = projectID;
	}
	
	
	
   
	
	
}
