package com.hx.httpclient.bi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.hx.comm.Constants;
import com.hx.comm.HTTPMethod;
import com.hx.comm.Roles;
import com.hx.httpclient.utils.HTTPClientUtils;
import com.hx.httpclient.vo.ClientSecretCredential;
import com.hx.httpclient.vo.Credential;

/**
 * REST API Demo :用户体系集成 REST API HttpClient4.3实现
 * 
 * Doc URL: https://docs.easemob.com/doku.php?id=start:100serverintegration:20users
 * 
 * @author Lynch 2014-09-15
 * 
 */
public class EasemobIMUsers {
	private static final Logger logger = LoggerFactory.getLogger(EasemobIMUsers.class);

	private static final JsonNodeFactory factory = new JsonNodeFactory(false);

    // 通过app的client_id和client_secret来获取app管理员token
    private static Credential credential = new ClientSecretCredential(Constants.APP_CLIENT_ID,
            Constants.APP_CLIENT_SECRET, Roles.USER_ROLE_APPADMIN);

    public static void main(String[] args) {
        /**
         * 重置IM用户密码 提供管理员token
         */
		String username = "15210819620";
        ObjectNode modifyIMUserPasswordWithAdminTokenNode = modifyIMUserPasswordWithAdminToken(username, "123456");
        if (null != modifyIMUserPasswordWithAdminTokenNode) {
            logger.info("调试信息");
        }

    }


	/**
	 * 重置IM用户密码 提供管理员token
	 * 
	 * @param userName手机号
	 * @param dataObjectNode
	 * @return
	 */
	public static ObjectNode modifyIMUserPasswordWithAdminToken(String userName, String password) {
		
		ObjectNode dataObjectNode = JsonNodeFactory.instance.objectNode();
		if(password==null||password.length()==0){
			return null;
		}
		dataObjectNode.put("newpassword", password);
		ObjectNode objectNode = factory.objectNode();

		// check Constants.APPKEY format
		if (!HTTPClientUtils.match("^(?!-)[0-9a-zA-Z\\-]+#[0-9a-zA-Z]+", Constants.APPKEY)) {
			logger.info("调试信息");

			objectNode.put("message", "Bad format of Constants.APPKEY");

			return objectNode;
		}

		if (StringUtils.isEmpty(userName)) {
			logger.info("Property that named userName must be provided，the value is username of imuser.");

			objectNode.put("message",
					"Property that named userName must be provided，the value is username or imuser.");

			return objectNode;
		}

		if (null != dataObjectNode && !dataObjectNode.has("newpassword")) {
			logger.info("Property that named newpassword must be provided .");

			objectNode.put("message", "Property that named newpassword must be provided .");

			return objectNode;
		}

		try {
			URL modifyIMUserPasswordWithAdminTokenUrl = HTTPClientUtils.getURL(Constants.APPKEY.replace("#", "/")
					+ "/users/" + userName + "/password");
			objectNode = HTTPClientUtils.sendHTTPRequest(modifyIMUserPasswordWithAdminTokenUrl, credential,
					dataObjectNode, HTTPMethod.METHOD_PUT);

		} catch (Exception e) {
			logger.error("操作异常", e);
		}

		return objectNode;
	}

}
