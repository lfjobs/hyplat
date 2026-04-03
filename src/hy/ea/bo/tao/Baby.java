package hy.ea.bo.tao;


/**
 * 
 * 商品（宝贝）表
 */


import hy.plat.bo.BaseBean;

import java.util.Date;

public class Baby implements BaseBean{
	private String babyKey;
	private String babyID;
	private String category;//类目
	//1.宝贝基本信息
	private String babytype;//宝贝类型：全新:0  二手 1
    private String babytitle;//宝贝标题 限制：30字
    private String sellpoints;//宝贝卖点
    private Float aprice;//一口价
    private String presaletype;//预售设置：预售类型：0:费预售；1:普通预售;3:定时预售
    private Integer babynum;//宝贝总数量
    private String purchaseland;//采购地：国内：0;海外及港澳台 :1
    private String sellercode;//商家编码
    private String barcode;//商品条形码
    private String descriptxt;//宝贝描述txt文本地址
    //2.宝贝物流及安装服务
    private String freightcostid;//运费Id外键
    private Float volume;//物流体积(m3)
    private Float weight;//物流重量(kg)
    //3.售后保障信息
    private String invoice;//发票  0：无 ；1:有 ；默认：无
    private String guarantee;//保修 0：无;1:有 ；默认：无
    private String expromise;//退换货承诺 0:不承诺 1：承诺 ；默认：不承诺 凡使用支付宝服务付款购买本店商品，若存在质量问题或与描述不符，本店将主动提供退换货服务并承担来回邮费!
    private String serguarantee;//服务保障：0：不保障 ；1：保障（默认保障）该商品品类须支持“七天退货”服务；承诺更好服务可通过交易合约设置
    private String threegug;//三包  0:不三包 1：三包 ；默认:不三包(支持7天包退,15天包换,30天至120天保修)
    //4.其他信息
    private String discount;//会员打折:0:不参与会员打折；1:参与会员打折;默认：不参与
    private String inventorycount;//库存计数：0：拍下减库存 1：付款减库存  ；默认：0
    private String validityperiod;//有效期 7天 ；0 默认选中即日起全网一口价宝贝的有效期统一为7天
    private String starttype;//开始时间： 0：立刻；1：设计：3：放入仓库
    private String seckill;//秒杀商品： 0：电脑用户 1：手机用户 复选框  默认都不选
    private String window;//橱窗推荐; 0：推荐 1：不推荐 默认 推荐
    
     //自动生成
    
    private Date  createtime;//创建时间 
    private String companyid;//公司ID
    private String companyname;//公司Name
    private String creatorid;//创建
    private String creatorname;//创建人Name；
    private String updateid;//修改人
    private String updatename;//修改人姓名
    private Date updatetime;//修改时间
	public String getBabyKey() {
		return babyKey;
	}
	public void setBabyKey(String babyKey) {
		this.babyKey = babyKey;
	}
	public String getBabyID() {
		return babyID;
	}
	public void setBabyID(String babyID) {
		this.babyID = babyID;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getBabytype() {
		return babytype;
	}
	public void setBabytype(String babytype) {
		this.babytype = babytype;
	}
	public String getBabytitle() {
		return babytitle;
	}
	public void setBabytitle(String babytitle) {
		this.babytitle = babytitle;
	}
	public String getSellpoints() {
		return sellpoints;
	}
	public void setSellpoints(String sellpoints) {
		this.sellpoints = sellpoints;
	}
	
	public Float getAprice() {
		return aprice;
	}
	public void setAprice(Float aprice) {
		this.aprice = aprice;
	}
	public String getPresaletype() {
		return presaletype;
	}
	public void setPresaletype(String presaletype) {
		this.presaletype = presaletype;
	}
  
	public String getPurchaseland() {
		return purchaseland;
	}
	public void setPurchaseland(String purchaseland) {
		this.purchaseland = purchaseland;
	}
	public String getSellercode() {
		return sellercode;
	}
	public void setSellercode(String sellercode) {
		this.sellercode = sellercode;
	}
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	public String getDescriptxt() {
		return descriptxt;
	}
	public void setDescriptxt(String descriptxt) {
		this.descriptxt = descriptxt;
	}
	public String getFreightcostid() {
		return freightcostid;
	}
	public void setFreightcostid(String freightcostid) {
		this.freightcostid = freightcostid;
	}
	
	public Integer getBabynum() {
		return babynum;
	}
	public void setBabynum(Integer babynum) {
		this.babynum = babynum;
	}
	public Float getVolume() {
		return volume;
	}
	public void setVolume(Float volume) {
		this.volume = volume;
	}
	public Float getWeight() {
		return weight;
	}
	public void setWeight(Float weight) {
		this.weight = weight;
	}
	public String getInvoice() {
		return invoice;
	}
	public void setInvoice(String invoice) {
		this.invoice = invoice;
	}
	public String getGuarantee() {
		return guarantee;
	}
	public void setGuarantee(String guarantee) {
		this.guarantee = guarantee;
	}
	public String getExpromise() {
		return expromise;
	}
	public void setExpromise(String expromise) {
		this.expromise = expromise;
	}
	
	public String getSerguarantee() {
		return serguarantee;
	}
	public void setSerguarantee(String serguarantee) {
		this.serguarantee = serguarantee;
	}
	public String getThreegug() {
		return threegug;
	}
	public void setThreegug(String threegug) {
		this.threegug = threegug;
	}
	public String getDiscount() {
		return discount;
	}
	public void setDiscount(String discount) {
		this.discount = discount;
	}
	public String getInventorycount() {
		return inventorycount;
	}
	public void setInventorycount(String inventorycount) {
		this.inventorycount = inventorycount;
	}
	public String getValidityperiod() {
		return validityperiod;
	}
	public void setValidityperiod(String validityperiod) {
		this.validityperiod = validityperiod;
	}
	public String getStarttype() {
		return starttype;
	}
	public void setStarttype(String starttype) {
		this.starttype = starttype;
	}
	public String getSeckill() {
		return seckill;
	}
	public void setSeckill(String seckill) {
		this.seckill = seckill;
	}
	public String getWindow() {
		return window;
	}
	public void setWindow(String window) {
		this.window = window;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

    
	public String getCreatorname() {
		return creatorname;
	}
	public void setCreatorname(String creatorname) {
		this.creatorname = creatorname;
	}
	public String getCompanyid() {
		return companyid;
	}
	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}
	public String getCompanyname() {
		return companyname;
	}
	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}
	public String getCreatorid() {
		return creatorid;
	}
	public void setCreatorid(String creatorid) {
		this.creatorid = creatorid;
	}
	public String getUpdateid() {
		return updateid;
	}
	public void setUpdateid(String updateid) {
		this.updateid = updateid;
	}
	public String getUpdatename() {
		return updatename;
	}
	public void setUpdatename(String updatename) {
		this.updatename = updatename;
	}
	public Date getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
    
    
    
    
}
