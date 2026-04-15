package hy.ea.util;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
/**
 * 分页转换器
 * @author zg
 *
 */
public class Converter {
	/**
	 * 匹配指定class中数据,并返回包含get和set方法的object
	 * 
	 * @author zg
	 * @param clazz
	 *            实体类
	 * @param beanProperty
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private Object[] beanMatch(Class clazz, String beanProperty) {
		Object[] result = new Object[2];
		if(beanProperty.contains("_")){
			beanProperty=beanProperty.replace("_", "");
		}
		char beanPropertyChars[] = beanProperty.toCharArray();
		beanPropertyChars[0] = Character.toUpperCase(beanPropertyChars[0]);
		String s = new String(beanPropertyChars);
		String names[] = { ("set" + s).intern(), ("get" + s).intern(),
				("is" + s).intern(), ("write" + s).intern(),
				("read" + s).intern() };
		Method getter = null;
		Method setter = null;
		Method methods[] = clazz.getMethods();
		for (int i = 0; i < methods.length; i++) {
			Method method = methods[i];
			// 只取公共字段
			if (!Modifier.isPublic(method.getModifiers()))
				continue;
			String methodName = method.getName().intern();
			for (int j = 0; j < names.length; j++) {
				String name = names[j];
				if (!name.equalsIgnoreCase(methodName))
					continue;
				if (methodName.startsWith("set")
						|| methodName.startsWith("read"))
					setter = method;
				else
					getter = method;
			}
		}
		result[0] = getter;
		result[1] = setter;
		return result;
	}

	/**
	 * 为bean自动注入数据
	 * 
	 * @author zg
	 * @param object
	 * @param beanProperty
	 */
	private void beanRegister(Object object, String beanProperty, String value) {
		Object[] beanObject = beanMatch(object.getClass(), beanProperty);
		Object[] cache = new Object[1];
		Method getter = (Method) beanObject[0];
		Method setter = (Method) beanObject[1];
		try {
			// 通过get获得方法类型
			String methodType = getter.getReturnType().getName();
			if (methodType.equalsIgnoreCase("java.lang.String")) {
				cache[0] = value;
				setter.invoke(object, cache);
			} else if (methodType.equalsIgnoreCase("long")) {
				cache[0] = new Long(value);
				setter.invoke(object, cache);
			} else if (methodType.equalsIgnoreCase("int")
					|| methodType.equalsIgnoreCase("integer")) {
				cache[0] = new Integer(value);
				setter.invoke(object, cache);
			} else if (methodType.equalsIgnoreCase("java.util.Date")) {
				cache[0] = Utilities.getDateFromString(value, "yyyy-MM-dd hh:mm:ss");
				setter.invoke(object, cache);
			} else if (methodType.equalsIgnoreCase("short")) {
				cache[0] = new Short(value);
				setter.invoke(object, cache);
			} else if (methodType.equalsIgnoreCase("float")) {
				cache[0] = new Float(value);
				setter.invoke(object, cache);
			} else if (methodType.equalsIgnoreCase("double")) {
				cache[0] = new Double(value);
				setter.invoke(object, cache);
			} else if (methodType.equalsIgnoreCase("boolean")) {
				cache[0] = new Boolean(value);
				setter.invoke(object, cache);
			}else if (methodType.equalsIgnoreCase("java.math.BigDecimal")) {
				cache[0] = new BigDecimal(value);
				setter.invoke(object, cache);
			} else if (methodType.equalsIgnoreCase("java.io.InputStream")) {
			} else if (methodType.equalsIgnoreCase("char")) {
				cache[0] = (Character.valueOf(value.charAt(0)));
				setter.invoke(object, cache);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 自己封装的集合 
	 * 将resultset结果转换成实体
	 * @param result 
	 * @param clazz 需要转换的class类
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Collection get(final ResultSet result, final Class clazz) {
		// 创建collection
		Collection collection = null;
		try {
			ResultSetMetaData rsmd = result.getMetaData();
			// 获得数据列数
			int cols = rsmd.getColumnCount() - 1;
			// 创建等同数据列数的arraylist类型collection实例
			collection = new ArrayList(cols);
			Object object = null;
			// 遍历结果集
			while (result.next()) {
				// 创建对象
				try {
					// 从class获得对象实体
					object = clazz.newInstance();
				} catch (Exception e) {
				}
				// 循环每条记录
				for (int i = 1; i <= cols; i++) {
					if (result.getString(i) != null)
						beanRegister(object, rsmd.getColumnName(i),
								result.getString(i));
				}
				// 将数据插入collection
				collection.add(object);
				object=null;
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		} finally {

		}
		return collection;
	}
}
