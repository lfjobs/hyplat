package hy.ea.util;

import hy.ea.bo.CAccount;
import hy.ea.bo.Company;

import java.util.HashMap;

/**
 * <pre>
 * 
 *  freeapis
 *  File: UserSession.java
 * 
 *  freeapis, Inc.
 *  Copyright (C): 2015
 * 
 *  Description:
 *  TODO
 * 
 *  Notes:
 *  $Id: UserSession.java 31101200-9 2014-10-14 16:43:51Z freeapis\baijunyan $ 
 * 
 *  Revision History
 *  &lt;Date&gt;,			&lt;Who&gt;,			&lt;What&gt;
 *  2015年8月7日		baijunyan		Initial.
 *
 * </pre>
 */
public class UserSession implements java.io.Serializable {


	public static final String CACHE_KEY_PREFIX_SESSION="SESSION";

	public static final String PRODUCT="WFJ";

	public static final Long CASHE_TIME=900L;// 秒

    private static final long serialVersionUID = 1701924515983788810L;

    private String signToken;

	private HashMap<String, String> coa;

	private HashMap<String, String> cir;

	private Company currentcompany;

	private CAccount account;

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public HashMap<String, String> getCoa() {
		return coa;
	}

	public void setCoa(HashMap<String, String> coa) {
		this.coa = coa;
	}

	public HashMap<String, String> getCir() {
		return cir;
	}

	public void setCir(HashMap<String, String> cir) {
		this.cir = cir;
	}

	public Company getCurrentcompany() {
		return currentcompany;
	}

	public void setCurrentcompany(Company currentcompany) {
		this.currentcompany = currentcompany;
	}

	public CAccount getAccount() {
		return account;
	}

	public void setAccount(CAccount account) {
		this.account = account;
	}

    public String getSignToken() {
        return signToken;
    }

    public void setSignToken(String signToken) {
        this.signToken = signToken;
    }
}