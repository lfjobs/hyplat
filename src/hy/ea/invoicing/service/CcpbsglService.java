package hy.ea.invoicing.service;

import java.io.InputStream;
import java.util.Map;
/**
 * 资产负载表、损益表excel导出
 * @author lf
 *
 */
public interface CcpbsglService {
	/**
	 * 资产负债表
	 */
	InputStream zfShowExcel( Object[][] titlea,Object[][] titlea2,Map<String,Object[]> titlea3,Object[] objs);
	/**
	 * 损益表
	 */
	InputStream syShowExcel( Object[][] titlea,Object[] objs,Map<String, Object[]> syval);
	/**
	 * 资产负债表及损益表
	 */
	InputStream syzfExcel(Object[][] titlea,Object[][] titlea2,Object[][] titlea3,Map<String, Object[]> titlea4,Map<String, Object[]> syval,Object[] objs);
	
	InputStream syVoucherExcel(String json);
	InputStream syAccountExcel(String json1,String json2,String type);
}
