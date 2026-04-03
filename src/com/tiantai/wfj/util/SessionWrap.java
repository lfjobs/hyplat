package com.tiantai.wfj.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.tiantai.wfj.vo.CartItem;

/**
 * session的操作类，用于微分金前台客户
 * @author ttst_zhb
 * 一是单例模式的类只提供私有的构造函数，
 * 二是类定义中含有一个该类的静态私有对象，
 * 三是该类提供了一个静态的共有的函数用于创建或获取它本身的静态私有对象。
 */
public class SessionWrap {
	/***start 实体类都需要序列化********/
	//保存Staff的key
	public static final String KEY_STAFF = "key_staff";
	//保存Company的key
	public static final String KEY_COMPANY = "key_company";
	//保存Customer的key
	public static final String KEY_CUSTOMER = "key_customer";
	//保存当前登陆客户的登陆角色
	public static final String KEY_SHOPCUSCOM = "key_shop_cus_com";
	//保存缺省地址的key
	public static final String KEY_ADDRESS = "key_address";
	//保存caccount
	public static final String KEY_CACCOUNT = "key_caccount";
	//保存金币
	public static final String KEY_JIFEN="key_jifen";
	/***end 实体类都需要序列化********/
	//保存用户名的key
	public static final String KEY_USERNAME = "key_user_name";
	//保存用户电话的key
	public static final String KEY_USERPHONE = "key_user_phone";
	//保存购物车中商品的key（说明：购物车是内存中的，临时性的，每件商品都是一个对象CartItem，将这些CartItem放入map中。）
	public static final String KEY_CART = "key_cart";
	//session内部保存购物车商品map的key
	private static final String KEY_CART_MAP = "key_cart_map";
	//保存当前商店的key
	public static final String KEY_SHOPNAME = "key_shop_name";
	//保存当前商店的key
	public static final String KEY_SMS = "key_sms";
	//保存临时用户
	public static final String KEY_TEMPLATE_WX_RESULT = "key_wx_result";
	//单态实例
	private static SessionWrap sessionWrap = null;
	
	//定义私有构造器的作用是防止在外部使用new关键字生成SessionWrap的实例
	private SessionWrap(){
		
	}
	/**
	 * 获得实例
	 * @return
	 */
	public static SessionWrap getInstance()
	{
		if (null == sessionWrap) {
			synchronized (SessionWrap.class) { //这里不能使用 synchronized (this)
				if (sessionWrap == null){
					sessionWrap = new SessionWrap();
				}
			}			
		}
		return sessionWrap;
	}
	/**
	 * 获得用户名
	 * @param session
	 * @return
	 */
	public String getUserName(Map<String,Object> session){
		return (String)session.get(KEY_USERNAME);
	}
	/**
	 * 设置用户名
	 * @param session
	 * @param userName
	 */
	public void setUserName(Map<String,Object> session, String  userName){
		session.put(KEY_SHOPCUSCOM, userName);
	}
	//
	public String getscs(Map<String,Object> session){
		return (String)session.get(KEY_SHOPCUSCOM);
	}
	public void setscs(Map<String,Object> session, String  cus){
		session.put(KEY_USERNAME, cus);
	}
	/**
	 * 获得用户电话
	 * @param session
	 * @return
	 */
	public String getUserPhone(Map<String,Object> session){
		return (String)session.get(KEY_USERPHONE);
	}
	/**
	 * 设置用户电话
	 * @param session
	 * @param userPhone
	 */
	public void setUserPhone(Map<String,Object> session, String userPhone){
		session.put(KEY_USERPHONE, userPhone);
	}
	
	/**
	 * 获得用户名
	 * @param session                                                                                    
	 * @return
	 */
	public String getUserName(HttpSession session){
		return (String)session.getAttribute(KEY_USERNAME);
	}
	/**
	 * 设置用户名
	 * @param session
	 * @param userName
	 */
	public void setUserName(HttpSession session, String  userName){
		session.setAttribute(KEY_USERNAME, userName);
	}
	
	/**
	 * 获得用户电话
	 * @param session
	 * @return
	 */
	public String getUserPhone(HttpSession session){
		return (String)session.getAttribute(KEY_USERPHONE);
	}
	/**
	 * 设置用户电话
	 * @param session
	 * @param userPhone
	 */
	public void setUserPhone(HttpSession session, String userPhone){
		session.setAttribute(KEY_USERPHONE, userPhone);
	}
	
	/**
	 * 获得缺省地址
	 * @param session
	 * @return
	 */
	public String getAddress(Map<String,Object> session){
		return (String)session.get(KEY_ADDRESS);
	}
	/**
	 * 设置缺省地址
	 * @param session
	 * @param Address
	 */
	public void setAddress(Map<String,Object> session, String  Address){
		session.put(KEY_ADDRESS, Address);
	}
	
	/**
	 * 获得缺省地址
	 * @param session
	 * @return
	 */
	public String getAddress(HttpSession session){
		return (String)session.getAttribute(KEY_ADDRESS);
	}
	/**
	 * 设置缺省地址
	 * @param session
	 * @param Address
	 */
	public void setAddress(HttpSession session, String  Address){
		session.setAttribute(KEY_ADDRESS, Address);
	}
	
	/**
	 * 获得商店名称
	 * @param session
	 * @return
	 */
	public String getShopName(HttpSession session){
		return (String)session.getAttribute(KEY_SHOPNAME);
	}
	/**
	 * 设置商店名称
	 * @param session
	 * @param Address
	 */
	public void setShopName(HttpSession session, String  shopName){
		session.setAttribute(KEY_SHOPNAME, shopName);
	}
	
	/**
	 * 获得商店名称
	 * @param session
	 * @return
	 */
	public String getShopName(Map<String,Object> session){
		return (String)session.get(KEY_SHOPNAME);
	}
	/**
	 * 设置商店名称
	 * @param session
	 * @param Address
	 */
	public void setShopName(Map<String,Object> session, String  shopName){
		session.put(KEY_SHOPNAME, shopName);
	}
	
	/**
	 * 获取一个对象
	 * @param session
	 * @param key
	 * @return
	 */
	public Object getObject(Map<String,Object> session, String key){
		return session.get(key);
	}
	/**
	 * 保存一个对象
	 * @param session
	 * @param key
	 * @param object
	 */
	public void setObject(Map<String,Object> session, String key, Object object){
		session.put(key, object);
	}
	
	/**
	 * 获取一个对象
	 * @param session
	 * @param key
	 * @return
	 */
	public Object getObject(HttpSession session, String key){
		return session.getAttribute(key);
	}
	/**
	 * 保存一个对象
	 * @param session
	 * @param key
	 * @param object
	 */
	public void setObject(HttpSession session, String key, Object object){
		session.setAttribute(key, object);
	}
	
	/**
	 * 内部方法，获得购物车map
	 * @param session
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Map<String,CartItem> getCartMap(HttpSession session){
		if (session.getAttribute(KEY_CART_MAP)==null){
			session.setAttribute(KEY_CART_MAP, new HashMap<String,CartItem>());			
		}
		return (Map<String,CartItem>)session.getAttribute(KEY_CART_MAP);		
	}
	
	/**
	 * 内部方法，获得购物车map
	 * @param session
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Map<String,CartItem> getCartMap(Map<String,Object> session){
		if (session.get(KEY_CART_MAP)==null){
			session.put(KEY_CART_MAP, new HashMap<String,CartItem>());			
		}
		return (Map<String,CartItem>)session.get(KEY_CART_MAP);		
	}
	
	/**
	 * 存入一个产品
	 * @param session
	 * @param cartItem
	 */
	public void setCartItem(HttpSession session, CartItem cartItem){
		Map<String,CartItem> cartMap = getCartMap(session);
		cartMap.put(cartItem.getPid(), cartItem);
	}
	
	/**
	 * 存入一个产品
	 * @param session
	 * @param cartItem
	 */
	public void setCartItem(Map<String,Object> session, CartItem cartItem){
		Map<String,CartItem> cartMap = getCartMap(session);
		cartMap.put(cartItem.getPid(), cartItem);
	}
	
	/**
	 * 获得一个购物车项
	 * @param session
	 * @param pid
	 * @return
	 */
	private CartItem getCartItem(HttpSession session, String pid){
		Map<String,CartItem> cartMap = getCartMap(session);
		if (cartMap==null || cartMap.isEmpty()){
			return null;
		}
		return cartMap.get(pid);
	}
	
	/**
	 * 获得一个购物车项
	 * @param session
	 * @param pid
	 * @return
	 */
	private CartItem getCartItem(Map<String,Object> session, String pid){
		Map<String,CartItem> cartMap = getCartMap(session);
		if (cartMap==null || cartMap.isEmpty()){
			return null;
		}
		return cartMap.get(pid);
	}
	
	
	/**
	 * 获得全部产品
	 * @param session
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String,CartItem> getAllCartIteme(HttpSession session){
		return (Map<String,CartItem>)session.getAttribute(KEY_CART_MAP);		
	}
	
	/**
	 * 获得全部产品
	 * @param session
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String,CartItem> getAllCartIteme(Map<String,Object> session){
		return (Map<String,CartItem>)session.get(KEY_CART_MAP);		
	}
	
	/**
	 * 清除一个产品
	 * @param session
	 * @param cartItem
	 */
	@SuppressWarnings("unchecked")
	public void clearCartItem(HttpSession session, CartItem cartItem){
		if (session.getAttribute(KEY_CART_MAP)!=null){
			Map<String,CartItem> cartMap = (Map<String,CartItem>)session.getAttribute(KEY_CART_MAP);
			if (cartMap.containsKey(cartItem.getPid())) cartMap.remove(cartItem.getPid());
		}		
	}
	
	/**
	 * 清除一个产品
	 * @param session
	 * @param pid
	 */
	@SuppressWarnings("unchecked")
	public void clearCartItem(HttpSession session, String pid){
		if (session.getAttribute(KEY_CART_MAP)!=null){
			Map<String,CartItem> cartMap = (Map<String,CartItem>)session.getAttribute(KEY_CART_MAP);
			if (cartMap.containsKey(pid)) cartMap.remove(pid);
		}		
	}
	
	/**
	 * 清除一个产品
	 * @param session
	 * @param cartItem
	 */
	@SuppressWarnings("unchecked")
	public void clearCartItem(Map<String,Object> session, CartItem cartItem){
		if (session.get(KEY_CART_MAP)!=null){
			Map<String,CartItem> cartMap = (Map<String,CartItem>)session.get(KEY_CART_MAP);
			if (cartMap.containsKey(cartItem.getPid())) cartMap.remove(cartItem.getPid());
		}		
	}
	
	/**
	 * 清除一个产品
	 * @param session
	 * @param pid
	 */
	@SuppressWarnings("unchecked")
	public void clearCartItem(Map<String,Object> session, String pid){
		if (session.get(KEY_CART_MAP)!=null){
			Map<String,CartItem> cartMap = (Map<String,CartItem>)session.get(KEY_CART_MAP);
			if (cartMap.containsKey(pid)) cartMap.remove(pid);
		}		
	}
	
	/**
	 * 清除部分产品
	 * @param session
	 * @param items
	 */
	public void clearAllCartItemes(HttpSession session,CartItem[] items){
		if (session.getAttribute(KEY_CART_MAP)!=null && items!=null){
			for (CartItem cartItem : items) {
				clearCartItem(session,cartItem);
			}	
		}		
	}
	/**
	 * 清除部分产品
	 * @param session
	 * @param items
	 */
	public void clearAllCartItemes(HttpSession session,String... items){
		if (session.getAttribute(KEY_CART_MAP)!=null && items!=null){
			for (String pid : items) {
				clearCartItem(session,pid);
			}	
		}		
	}	
	
	/**
	 * 清除部分产品
	 * @param session
	 * @param items
	 */
	public void clearAllCartItemes(Map<String,Object> session,CartItem[] items){
		if (session.get(KEY_CART_MAP)!=null && items!=null){
			for (CartItem cartItem : items) {
				clearCartItem(session,cartItem);
			}	
		}		
	}
	
	/**
	 * 清除部分产品
	 * @param session
	 * @param items
	 */
	public void clearAllCartItemes(Map<String,Object> session,String... items){
		if (session.get(KEY_CART_MAP)!=null && items!=null){
			for (String pid : items) {
				clearCartItem(session,pid);
			}	
		}		
	}
	
	/**
	 * 清除全部产品
	 * @param session
	 */
	public void clearAllCartItem(HttpSession session){
		if (session.getAttribute(KEY_CART_MAP)!=null){
			session.removeAttribute(KEY_CART_MAP);			
		}		
	}
	
	/**
	 * 清除全部产品
	 * @param session
	 */
	public void clearAllCartItem(Map<String,Object> session){
		if (session.get(KEY_CART_MAP)!=null){
			session.remove(KEY_CART_MAP);			
		}		
	}
	
	/**
	 * 更新一个产品的数量
	 * @param session
	 * @param pid
	 * @param itemNum
	 */
	public void updateCartItemNum(HttpSession session, String pid, String itemNum){
		if (pid==null || "".equals(pid.trim()) || itemNum==null || "".equals(itemNum.trim()))
		{
			return;
		}
		Map<String,CartItem> map = getCartMap(session);
		if (map==null || map.isEmpty()){
			return;
		}
		CartItem item = getCartItem(session,pid);
		if (item!=null){
			item.setItemNum(itemNum);
			setCartItem(session, item);//更新
		}		
	}
	
	/**
	 * 更新一个产品的数量
	 * @param session
	 * @param pid
	 * @param itemNum
	 */
	public void updateCartItemNum(Map<String, Object> session, String pid, String itemNum){
		if (pid==null || "".equals(pid.trim()) || itemNum==null || "".equals(itemNum.trim()))
		{
			return;
		}
		Map<String,CartItem> map = getCartMap(session);
		if (map==null || map.isEmpty()){
			return;
		}
		CartItem item = getCartItem(session,pid);
		if (item!=null){
			item.setItemNum(itemNum);
			setCartItem(session, item);//更新
		}		
	}
	
}
