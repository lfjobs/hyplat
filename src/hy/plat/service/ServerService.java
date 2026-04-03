package hy.plat.service;

import hy.ea.bo.CAccount;
import hy.ea.bo.CDetail;
import hy.ea.bo.Company;
import hy.ea.bo.company.ContactCompany;


public interface ServerService {
	/**
	 * 根据type取得SID
	 * @param type
	 * @return
	 */
	String getServerID(String type);

	String getBillID(String companyID);

	Boolean getCBooleanByCompanyIdentifier(String companyIdentifier);

	boolean register(Company company, ContactCompany contactCompany,
			CDetail companyDetail, CAccount account);
	/**
	 * 根据单据类型取得SerialNumber
	 * @param typeNumber
	 * @return
	 */
	String getSerialNumber(String typeNumber);

}
