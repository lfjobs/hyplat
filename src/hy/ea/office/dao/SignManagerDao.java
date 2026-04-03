package hy.ea.office.dao;

import hy.ea.bo.office.SignManager;

import java.util.Date;
import java.util.List;

public interface SignManagerDao {

	public void InsertSignManager(SignManager signManager);

	public boolean UpdatePosition(String position, String signmanagerkey);

	public boolean DeleteSignManager(String signmanagerkey);

	@SuppressWarnings("rawtypes")
	public List getSignManager(String signmanagerkey);
	
	public boolean UpdateSignstat(String signstat, String signmanagerkey);
	
	public boolean UpdateRelationtable(String signstat, String relationid);
	
	@SuppressWarnings("rawtypes")
	public List QuerySignManager(String accountid, int rp, int pages, String signstat, String signid, String relationtable, Date starttime, Date endtime);
	
	public int CountSignManager(String accountid, int rp, int pages, String signstat, String signid, String relationtable, Date starttime, Date endtime);
	
	@SuppressWarnings("rawtypes")
	public List QueryRelationTable(String accountid, int rp, int pages, String relationid, String signstat, String signid, Date starttime, Date endtime);
	
	public int CountRelationTable(String accountid, String relationid, String signstat, String signid, Date starttime, Date endtime);
}
