package hy.ea.service.impl;

import hy.ea.bo.invoicing.CostSheetBill;
import hy.ea.service.InvoicingService;
import hy.plat.bo.BaseBean;
import hy.plat.dao.impl.BaseBeanDao;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

@Service
public class InvoicingServiceImpl implements InvoicingService{
	 // 整数部分
	  private String integerPart;
	  // 小数部分
	  private String floatPart;
	  
	  // 将数字转化为汉字的数组,因为各个实例都要使用所以设为静态
	  private static final char[] cnNumbers={'零','壹','贰','叁','肆','伍','陆','柒','捌','玖'};
	  
	  // 供分级转化的数组,因为各个实例都要使用所以设为静态
	  private static final char[] series={'元','拾','百','仟','万','拾','百','仟','亿'};

	  
	  @Resource
	  private BaseBeanDao baseBeanDao;
	  
	  
	@Override
	public String cnUpperCaser(String original) {
		   // 成员变量初始化
	    integerPart="";
	    floatPart="";
	    
	    if(original.contains(".")){
	      // 如果包含小数点
	      int dotIndex=original.indexOf(".");
	      integerPart=original.substring(0,dotIndex);
	      floatPart=original.substring(dotIndex+1);
	    }
	    else{
	      // 不包含小数点
	      integerPart=original;
	    }
		  
	    // 因为是累加所以用StringBuffer
	    StringBuffer sb=new StringBuffer();
	    
	    // 整数部分处理
	    for(int i=0;i<integerPart.length();i++){
	      int number=getNumber(integerPart.charAt(i));
	      
	      sb.append(cnNumbers[number]);
	      sb.append(series[integerPart.length()-1-i]);
	    }
	    
	    // 小数部分处理
	    if(floatPart.length()>0){
	      sb.append("点");
	      for(int i=0;i<floatPart.length();i++){
	        int number=getNumber(floatPart.charAt(i));
	        
	        sb.append(cnNumbers[number]);
	      }
	    }
	    
	    // 返回拼接好的字符串
	    return sb.toString();
	  }

	
	
	
	  /**
	   * 将字符形式的数字转化为整形数字
	   * 因为所有实例都要用到所以用静态修饰
	   * @param c
	   * @return
	   */
	  private static int getNumber(char c){
	    String str=String.valueOf(c);   
	    return Integer.parseInt(str);
	  }
	  
	  
	  
	//递归获取子项目
		public List<BaseBean> getSubProject(List<BaseBean> sublist,String pJournalNum){
			String hql = "from CostSheetBill where  pJournalNum = ? and pJournalNum!=journalNum";
			List<BaseBean> list1 = baseBeanDao.getListBeanByHqlAndParams(hql,new Object[]{pJournalNum});
			sublist.addAll(list1);
			if(list1.size()!=0){
			for(BaseBean b:list1){
				CostSheetBill csb = (CostSheetBill) b;
				getSubProject(sublist,csb.getJournalNum());
			}
			}
			return sublist;
		}
	

}

