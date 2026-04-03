package hy.ea.office.service;

import hy.ea.bo.office.SignManager;

import java.util.Date;
import java.util.List;

public interface SignManagerService {

	public void InsertSignManager(SignManager signmanager);

	public boolean UpdatePosition(String position, String signmanagerkey);

	public boolean DeleteSignManager(String signmanagerkey);

	@SuppressWarnings("rawtypes")
	public List getSignManager(String signmanagerkey);
	
	public boolean SignManager(SignManager SignManager, String signstat, String signid, String relationid);
	
	public boolean UpdateSignstat(String signstat, String signmanagerkey);
	
	public boolean UpdateRelationtable(String signstat, String relationid);
	
	@SuppressWarnings("rawtypes")
	public List QuerySignManager(String accountid, int rp, int pages, String signstat, String signid, String relationtable, Date starttime, Date endtime);
	
	public int CountSignManager(String accountid, int rp, int pages, String signstat, String signid, String relationtable, Date starttime, Date endtime);
	
	@SuppressWarnings("rawtypes")
	public List QueryRelationTable(String accountid, int rp, int pages, String relationid, String signstat, String signid, Date starttime, Date endtime);
	
	public int CountRelationTable(String accountid, String relationid, String signstat, String signid, Date starttime, Date endtime);
}
