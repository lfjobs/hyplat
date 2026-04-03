package hy.ea.bo.invoicing.voucher;

import hy.plat.bo.BaseBean;

/**
 * 资产负债损益表内容设定表
 * 内容设定档
 * @author lou
 *
 */
public class DtInvCcbsgl  implements BaseBean,java.io.Serializable {


    // Fields    

     private String ccKey;
     private String ccId;
     private String comId;//公司id
     private String sequence;//序号
     private String tabSymbol;//报表代号
     private String tabSCaption;//报表代号说明
     private String tabType;//报表类别（A:资产负债表，B：损益表）
     private String bsAtion;//资产负债损益表内容注记( A:资产,B:负债及股东权益,C:营业收入净值,D:本期纯益,E:其他)
     private String cglAtion;//本期损益注记（Y/N Y:损益，N:为默认值）
     private String tabPSymbol;//上层报表代号


    // Constructors

    /** default constructor */
    public DtInvCcbsgl() {
    }
   
    public DtInvCcbsgl(String ccKey, String ccId, String comId,
			String sequence, String tabSymbol, String tabSCaption,
			String tabType, String bsAtion, String cglAtion, String tabPSymbol) {
		super();
		this.ccKey = ccKey;
		this.ccId = ccId;
		this.comId = comId;
		this.sequence = sequence;
		this.tabSymbol = tabSymbol;
		this.tabSCaption = tabSCaption;
		this.tabType = tabType;
		this.bsAtion = bsAtion;
		this.cglAtion = cglAtion;
		this.tabPSymbol = tabPSymbol;
	}
    
	// Property accessors
    @Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DtInvCcbsgl other = (DtInvCcbsgl) obj;
		if (bsAtion == null) {
			if (other.bsAtion != null)
				return false;
		} else if (!bsAtion.equals(other.bsAtion))
			return false;
		if (ccId == null) {
			if (other.ccId != null)
				return false;
		} else if (!ccId.equals(other.ccId))
			return false;
		if (ccKey == null) {
			if (other.ccKey != null)
				return false;
		} else if (!ccKey.equals(other.ccKey))
			return false;
		if (cglAtion == null) {
			if (other.cglAtion != null)
				return false;
		} else if (!cglAtion.equals(other.cglAtion))
			return false;
		if (comId == null) {
			if (other.comId != null)
				return false;
		} else if (!comId.equals(other.comId))
			return false;
		if (sequence == null) {
			if (other.sequence != null)
				return false;
		} else if (!sequence.equals(other.sequence))
			return false;
		if (tabPSymbol == null) {
			if (other.tabPSymbol != null)
				return false;
		} else if (!tabPSymbol.equals(other.tabPSymbol))
			return false;
		if (tabSCaption == null) {
			if (other.tabSCaption != null)
				return false;
		} else if (!tabSCaption.equals(other.tabSCaption))
			return false;
		if (tabSymbol == null) {
			if (other.tabSymbol != null)
				return false;
		} else if (!tabSymbol.equals(other.tabSymbol))
			return false;
		if (tabType == null) {
			if (other.tabType != null)
				return false;
		} else if (!tabType.equals(other.tabType))
			return false;
		return true;
	}





	public String getCcKey() {
        return this.ccKey;
    }
    
    public void setCcKey(String ccKey) {
        this.ccKey = ccKey;
    }

    public String getCcId() {
        return this.ccId;
    }
    
    public void setCcId(String ccId) {
        this.ccId = ccId;
    }

    public String getComId() {
        return this.comId;
    }
    
    public void setComId(String comId) {
        this.comId = comId;
    }

    public String getTabSymbol() {
        return this.tabSymbol;
    }
    
    public void setTabSymbol(String tabSymbol) {
        this.tabSymbol = tabSymbol;
    }

    public String getTabSCaption() {
        return this.tabSCaption;
    }
    
    public void setTabSCaption(String tabSCaption) {
        this.tabSCaption = tabSCaption;
    }

    public String getTabType() {
        return this.tabType;
    }
    
    public void setTabType(String tabType) {
        this.tabType = tabType;
    }

    public String getBsAtion() {
        return this.bsAtion;
    }
    
    public void setBsAtion(String bsAtion) {
        this.bsAtion = bsAtion;
    }

    public String getCglAtion() {
        return this.cglAtion;
    }
    
    public void setCglAtion(String cglAtion) {
        this.cglAtion = cglAtion;
    }

    public String getTabPSymbol() {
        return this.tabPSymbol;
    }
    
    public void setTabPSymbol(String tabPSymbol) {
        this.tabPSymbol = tabPSymbol;
    }


	public String getSequence() {
		return sequence;
	}


	public void setSequence(String sequence) {
		this.sequence = sequence;
	}
    
}