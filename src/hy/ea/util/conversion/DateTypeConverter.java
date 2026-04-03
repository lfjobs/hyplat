package hy.ea.util.conversion;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import com.opensymphony.xwork2.conversion.impl.DefaultTypeConverter;

public class DateTypeConverter extends DefaultTypeConverter {
	/**
	 * 时间类型的转换
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public Object convertValue(Map<String, Object> context, Object value,
			Class toType) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String rDate = format.format(new Date());
		try {
			if (toType == Date.class) {
				String[] param = (String[]) value;
				return format.parseObject(param[0]);
			} else if (toType == String.class) {
				Date date = (Date) value;
				rDate = format.format(date);
			}
		} catch (Exception e) {
		}
		return rDate;
	}

}
