package hy.ea.office.service.impl;

import hy.ea.office.service.IArchiveService;
import hy.plat.bo.BaseBean;
import hy.plat.dao.impl.BaseBeanDao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ArchiveServiceImpl implements IArchiveService {

	@Autowired
	private BaseBeanDao baseBeanDao; 
	
	
	/**
	 * 
	 * 获取编号
	 * 
	 * @param companyID mz
	 * @return
	 */
	public String getCode(String companyID) {
		String hql = "from DtArchivesArchives where companyID = ?";
		String sql = "select max(to_number(archiveCode)) from DT_ARCHIVES_ARCHIVES where companyID= ?";
		int nextnum = 0;
		List<BaseBean> list = (List<BaseBean>) baseBeanDao
				.getListBeanByHqlAndParams(hql, new Object[] { companyID });

		if (list.size() == 0) {
			return "1";
		} else {
			int a = baseBeanDao.getConutByBySqlAndParams(sql,
					new Object[] { companyID });
			nextnum = a + 1;

		}
		return nextnum + "";

	}

	/**
	 * 
	 * 获取类别
	 */
	@Transactional(readOnly=true)
	public List<BaseBean> getCatalogueList(String companyID) {

		String hql = "from DtArchivesCataloguearchives where comanyid = ? order by modifytime desc";
		
		List<BaseBean> cataloguelist = baseBeanDao.getListBeanByHqlAndParams(
				hql, new Object[] { companyID });		

		return cataloguelist;
	}

	/**
	 * 
	 * 获取位置列表
	 */
	@Transactional(readOnly=true)
	public List<BaseBean> getLocation(String staffID) {
		String hql = "from DtArchivesInventorylocation where userid = ?";
		List<BaseBean> locationlist = baseBeanDao.getListBeanByHqlAndParams(
				hql, new Object[] { staffID });

		return locationlist;
	}
}

/*	 *//**
	  * 
	  * 根据人，类别，位置，入库查询档案列表
	  *//*
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<BaseBean> getArchiveList(String userId, String catalogueId,
			String locationid) {
		StringBuilder buffer = new StringBuilder();
		buffer.append("from DtArchivesArchives a where a.archiveskey ");
		buffer.append("in (select h.dtArchivesArchives from DtArchivesArchiveshistory h where inuser = ? ");
		buffer.append("and state = ? and h.dtArchivesInventorylocation ");
		buffer.append("in(select locationkey from DtArchivesInventorylocation d where d.locationid = ?))");
		buffer.append("and a.dtArchivesCataloguearchives ");
		buffer.append("in(select archivekey from DtArchivesCataloguearchives c where c.archiveid = ?)");
		List<BaseBean> archivelist = baseBeanDao.getListBeanByHqlAndParams(buffer.toString(),
				new Object[] { userId, "00", locationid, catalogueId });

		return archivelist;
	}*/
	
	/**
	 * 根据条件查询用户在库档案,
	 * @see IArchiveService#getArchiveList(String userId, String catalogueId,String locationid) 
	 * @param chipsIds 芯片号列表 用","分隔 eg:EDASDF1564541411,E0SF45812ASDFSFE5
	 * 返回盘点结果：完全符合："0" 档案有差："档案的编号:名字,"列表
	 */
//	@Transactional(readOnly=true)
	/*public String archivesCheckvouchs(String chipsIds,String userId, String catalogueId,
			String locationid) {
		List<BaseBean> archives = getArchiveList(userId,catalogueId,locationid);		
		
		String result = "";
		
		String[] chipsId = chipsIds.split(",");
		for(BaseBean item : archives){
			for(String chip : chipsId){
				if(chip.equals(item)){
					archives.remove(item);
				}
			}
		}
		
		if(null == archives || archives.size() < 1){
			result = "0";
		}else{
			StringBuilder buffer = new StringBuilder();
			for(DtArchivesArchives item : archives){
				buffer.append(item.getArchivesid());
				buffer.append(":");
				buffer.append(item.getName());
				buffer.append(",");
			}
			result = buffer.toString();
			buffer = null;
		}
		
		return result;
	}
}*/