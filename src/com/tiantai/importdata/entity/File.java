package com.tiantai.importdata.entity;

/**
 *  对应每个导入meta xml文件
 * @author zhb
 *
 */
public class File {
	
	private String type;//导入数据的简单缩写
	private String name;//导入数据的中文名称
	private String desc;//导入数据的中文描述
	private String xmlMetaFile;//导入数据的元数据xml文件(包括路径)
	private Entity entity;//元数据xml文件转换成的Entity对象
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public Entity getEntity() {
		return entity;
	}
	public void setEntity(Entity entity) {
		this.entity = entity;
	}
	public String getXmlMetaFile() {
		return xmlMetaFile;
	}
	public void setXmlMetaFile(String xmlMetaFile) {
		this.xmlMetaFile = xmlMetaFile;
	}
	

}
