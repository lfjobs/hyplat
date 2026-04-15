package hy.ea.bo.office;

import hy.plat.bo.BaseBean;

import java.util.List;

public class DoubaoParam implements BaseBean ,java.io.Serializable{

	private String id;
	private String text;
	private String file;
	private List<String> flist;
	private String imgurl;
	private List<String> imglist;
	private String formateFile;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public List<String> getFlist() {
		return flist;
	}

	public void setFlist(List<String> flist) {
		this.flist = flist;
	}

	public String getImgurl() {
		return imgurl;
	}

	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}

	public List<String> getImglist() {
		return imglist;
	}

	public void setImglist(List<String> imglist) {
		this.imglist = imglist;
	}

	public String getFormateFile() {
		return formateFile;
	}

	public void setFormateFile(String formateFile) {
		this.formateFile = formateFile;
	}
}
