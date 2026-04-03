package hy.ea.util;

import org.hibernate.dialect.Oracle10gDialect;
/**
 * 重写方言
 * @author zg
 *
 */
public class OracletDialect_ttsw extends Oracle10gDialect {

	public OracletDialect_ttsw() {
		super();
	}
	/**
	 * 由于原来此方法会导致分页排序后无法查到预期分页结果。
	 */
	public String getLimitString(String sql, boolean hasOffset) {
		
		sql = sql.trim();
		boolean isForUpdate = false;
		if ( sql.toLowerCase().endsWith(" for update") ) {
			sql = sql.substring( 0, sql.length()-11 );
			isForUpdate = true;
		}
		
		StringBuffer pagingSelect = new StringBuffer( sql.length()+100 );
		pagingSelect.append("select * from ( select row_.*, rownum rownum_ from ( ");
		pagingSelect.append(sql);
		if (hasOffset) {
			pagingSelect.append(" ) row_ ) where rownum_ <= ? and rownum_ > ?");
		}
		else {
			pagingSelect.append(" ) row_ ) where rownum_ <= ? and rownum_ > 0");
		}

		if ( isForUpdate ) {
			pagingSelect.append( " for update" );
		}
		return pagingSelect.toString();
	}
}
