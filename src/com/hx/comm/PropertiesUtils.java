package com.hx.comm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * PropertiesUtils
 * 
 * @author Lynch 2014-09-15
 *
 */
public class PropertiesUtils {
	private static final Logger logger = LoggerFactory.getLogger(PropertiesUtils.class);

	public static Properties getProperties() {

		Properties p = new Properties();

		try {
			InputStream inputStream = PropertiesUtils.class.getResourceAsStream(
					"/com/hx/resources/RestAPIConfig.properties");

			p.load(inputStream);

		} catch (IOException e1) {
			logger.error("操作异常", e1);
		}

		return p;
	}

}
