package hy.ea.bo.finance;

import hy.plat.bo.BaseBean;

import java.util.Date;


/**
 * 产品评论表主表
 *
 */
public class ProductCommentMain implements BaseBean{
	private String pcmKey;  		//主键
	private String pcmID;  		//业务主键
	private String ppid;		//物品表主键
	private int praise; 		//被赞数
	private int plcount;			//评论数
	private int readcount;//阅读量

	public String getPcmKey() {
		return pcmKey;
	}

	public void setPcmKey(String pcmKey) {
		this.pcmKey = pcmKey;
	}

	public String getPcmID() {
		return pcmID;
	}

	public void setPcmID(String pcmID) {
		this.pcmID = pcmID;
	}

	public String getPpid() {
		return ppid;
	}

	public void setPpid(String ppid) {
		this.ppid = ppid;
	}

	public int getPraise() {
		return praise;
	}

	public void setPraise(int praise) {
		this.praise = praise;
	}

	public int getPlcount() {
		return plcount;
	}

	public void setPlcount(int plcount) {
		this.plcount = plcount;
	}

	public int getReadcount() {
		return readcount;
	}

	public void setReadcount(int readcount) {
		this.readcount = readcount;
	}


}