package hy.plat;

import java.io.Serializable;

import hy.plat.common.hibernate3.BaseManagerImpl;

public class CoreManagerImpl<T extends Serializable> extends
		BaseManagerImpl<T> implements CoreManager<T> {
}
