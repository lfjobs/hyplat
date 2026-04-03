package hy.ea.human.service;

import hy.ea.bo.human.SocialSecuritySetup;
import hy.plat.bo.PageForm;

public interface SocialSecuritySetupService {
	String findSocialSecuritySetupList(PageForm pageForm);
	void addSocialSecuritySetup(SocialSecuritySetup socialSecuritySetup);
	void deleteSocialSecuritySetupById(String id);
	String findSocialSecuritySetupById(String id);
	void updateSocialSecuritySetup(SocialSecuritySetup socialSecuritySetup);
	String findCurrentUser();
	SocialSecuritySetup findSocialSecuritySetupByCompanyId(String companyId);
}
