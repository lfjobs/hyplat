package hy.plat;

import java.io.Serializable;

import hy.plat.common.hibernate3.BaseDaoImpl;

public class CoreDaoImpl<T extends Serializable> extends BaseDaoImpl<T>
		implements CoreDao<T> {

}
