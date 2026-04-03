package hy.ea.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;
/**
 * 重写json返回值为null方法
 * 调用方法 jsonObject.putAll(map,JsonConfigFactory.getInstance());
 * @author zg
 *
 */
public class JsonConfigFactory {
	private static JsonConfig instance = null;

	public static synchronized JsonConfig getInstance() {
		if (instance == null) {
			instance = new JsonConfig();
			register(instance);
		}
		return instance;
	}

	private static void register(JsonConfig jsonConfig) {

		jsonConfig.registerJsonValueProcessor(Double.class,
				new JsonValueProcessor() {

					@Override
					public Object processObjectValue(String arg0, Object arg1,
							JsonConfig arg2) {
						if (arg1 == null) {
							return "";
						}
						return arg1;
					}

					@Override
					public Object processArrayValue(Object arg0, JsonConfig arg1) {
						return null;
					}
				});
		jsonConfig.registerJsonValueProcessor(Integer.class,
				new JsonValueProcessor() {

					@Override
					public Object processObjectValue(String arg0, Object arg1,
							JsonConfig arg2) {
						if (arg1 == null) {
							return "";
						}
						return arg1;
					}

					@Override
					public Object processArrayValue(Object arg0, JsonConfig arg1) {
						return null;
					}
				});
		jsonConfig.registerJsonValueProcessor(String.class,
				new JsonValueProcessor() {

					@Override
					public Object processObjectValue(String arg0, Object arg1,
							JsonConfig arg2) {
						if (arg1 == null) {
							return "";
						}
						return arg1;
					}

					@Override
					public Object processArrayValue(Object arg0, JsonConfig arg1) {
						return null;
					}
				});
		jsonConfig.registerJsonValueProcessor(Date.class,
				new JsonValueProcessor() {
			private String datePattern = "yyyy-MM-dd";
					@Override
					public Object processObjectValue(String arg0, Object arg1,
							JsonConfig arg2) {
						if (arg1 == null) {
							return "";
						}
						try {
							if (arg1 instanceof Date) {
								SimpleDateFormat sdf = new SimpleDateFormat(
										datePattern, Locale.CHINESE);
								return sdf.format((Date) arg1);
							}
							return arg1 == null ? "" : arg1.toString();
						} catch (Exception e) {
							return "";
						}
					}

					@Override
					public Object processArrayValue(Object arg0, JsonConfig arg1) {
						return null;
					}
				});
		jsonConfig.registerJsonValueProcessor(String.class,
				new JsonValueProcessor() {

					@Override
					public Object processObjectValue(String arg0, Object arg1,
							JsonConfig arg2) {
						if (arg1 == null) {
							return "";
						}
						return arg1;
					}

					@Override
					public Object processArrayValue(Object arg0, JsonConfig arg1) {
						return null;
					}
				});
		jsonConfig.registerJsonValueProcessor(Object.class,
				new JsonValueProcessor() {

					@Override
					public Object processObjectValue(String arg0, Object arg1,
							JsonConfig arg2) {
						if (arg1 == null) {
							return "";
						}
						return arg1;
					}

					@Override
					public Object processArrayValue(Object arg0, JsonConfig arg1) {
						return null;
					}
				});
	}

}
