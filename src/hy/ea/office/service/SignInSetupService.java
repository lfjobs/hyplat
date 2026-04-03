package hy.ea.office.service;

import hy.ea.office.bo.SignInSetup;
import hy.plat.bo.PageForm;

public interface SignInSetupService {
	String findSignInSetupList(PageForm pageForm);
	void addSignInSetup(SignInSetup signInSetup);
	void deleteSignInSetupById(String id);
	String findSignInSetupById(String id);
	void updateSignInSetup(SignInSetup signInSetup);
	String findSignInType();
	String findCurrentUser();
}
