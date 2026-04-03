package hy.ea.bo.finance.BenDis;

import hy.plat.bo.BaseBean;

public class transferPay implements BaseBean,java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private String cbatpkey;
    private String cbatpid;
    private String cashierbillsid; //订单id
    private String journalnum; //订单编号
    private String state; //类型  01：供应商帮采购商下单  02：采购人员下单  03：个人财务支付
    private String mapkey; //人员信息
    private String mapval;//人员信息
    private String cgscomid;//采购商公司id
    private String cgscomname;//采购商公司名称


   // Constructors

   /** default constructor */
   public transferPay() {
   }

   
   /** full constructor */
   public transferPay(String cbatpid, String cashierbillsid, String journalnum, String state, String mapkey, String mapval,
                      String cgscomid, String cgscomname) {
       this.cbatpid = cbatpid;
       this.cashierbillsid = cashierbillsid;
       this.journalnum = journalnum;
       this.state = state;
       this.mapkey = mapkey;
       this.mapval = mapval;
       this.cgscomid=cgscomid;
       this.cgscomname=cgscomname;
   }

  
   // Property accessors

   public String getCbatpkey() {
       return this.cbatpkey;
   }
   
   public void setCbatpkey(String cbatpkey) {
       this.cbatpkey = cbatpkey;
   }

   public String getCbatpid() {
       return this.cbatpid;
   }
   
   public void setCbatpid(String cbatpid) {
       this.cbatpid = cbatpid;
   }

   public String getCashierbillsid() {
       return this.cashierbillsid;
   }
   
   public void setCashierbillsid(String cashierbillsid) {
       this.cashierbillsid = cashierbillsid;
   }

   public String getJournalnum() {
       return this.journalnum;
   }
   
   public void setJournalnum(String journalnum) {
       this.journalnum = journalnum;
   }

   public String getState() {
       return this.state;
   }
   
   public void setState(String state) {
       this.state = state;
   }

   public String getMapkey() {
       return this.mapkey;
   }
   
   public void setMapkey(String mapkey) {
       this.mapkey = mapkey;
   }

   public String getMapval() {
       return this.mapval;
   }
   
   public void setMapval(String mapval) {
       this.mapval = mapval;
   }

    public String getCgscomid() {
        return cgscomid;
    }

    public void setCgscomid(String cgscomid) {
        this.cgscomid = cgscomid;
    }

    public String getCgscomname() {
        return cgscomname;
    }

    public void setCgscomname(String cgscomname) {
        this.cgscomname = cgscomname;
    }
}
