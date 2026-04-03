package hy.base.action;

import java.lang.reflect.ParameterizedType;

public class GenericSuperClassUtils {
	
	//范类转换
	@SuppressWarnings("rawtypes")
	public static Class getGenericSuperClass(Class entity) {
		ParameterizedType type = (ParameterizedType) entity.getGenericSuperclass();
		Class entityClass = (Class) type.getActualTypeArguments()[0];
		return entityClass;
	}

}
