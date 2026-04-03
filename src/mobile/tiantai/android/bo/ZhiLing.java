package mobile.tiantai.android.bo;

import hy.plat.bo.BaseBean;

import java.io.File;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
/**
 * 
 * 服务器接受 单车平台的指令应答信息
 * @author mz
 *
 */

public class ZhiLing implements BaseBean,java.io.Serializable{
	
	private String key;
	private String id;
	
	private String userid;   //用户唯一标识，十六位数字或者字符
	private int cmd;       //0表示查询设备信息,1表示下发开锁指令，2表示寻车，3 表示设置设备位置回传间隔
	private String deviceid;   //设备的编号，和用户发送过来的一样
	private String result;         //ok 表示成功，fail 表示失败查询则放设备的信息（空字
	private String info;   /*符串表示没有车辆，用分号隔开每辆车的信息，逗号隔开每个字段的信息,格式：[设备编号,0/1=离线/在线]），
	                          其他指令则是针对 result 的描述*/
	private int serialnum;  //用户下发指令的流水号
	private String sign;          //以上字段值+userkey 组成的字符串后进行 uft-8 编码的字节流进行 md5 计算

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public int getCmd() {
		return cmd;
	}

	public void setCmd(int cmd) {
		this.cmd = cmd;
	}

	public String getDeviceid() {
		return deviceid;
	}

	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public int getSerialnum() {
		return serialnum;
	}

	public void setSerialnum(int serialnum) {
		this.serialnum = serialnum;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}
}