package hy.ea.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

/**
 * PropertiesUtils
 * 
 * @author
 *
 */
public class AutoTaskUtils {
	private static final Logger logger = LoggerFactory.getLogger(AutoTaskUtils.class);
	private static Properties getProperties() {
		Properties properties = new Properties();
		try {
			InputStream inputStream = AutoTaskUtils.class.getResourceAsStream("/autoTask.properties");
			properties.load(inputStream);
		} catch (IOException ioException) {
			logger.error("操作异常", e);
		}
		return properties;
	}
	
	public static String getExecute(){
		String execute = getProperties().getProperty("runMonthTaskExecute");
		return execute;
	}

	public static String[] getSettlementYear(){
		String[] settlementYear = getProperties().getProperty("settlementYear").split(",");
		return settlementYear;
	}

	public static String[] getMonth(){
		String[] month = null;
		if(Objects.nonNull(getProperties().getProperty("month"))){
			month = getProperties().getProperty("month").split(",");
		}
		return month;
	}

	public static String[] getCompanyId(){
		String[] companyId = null;
		if(Objects.nonNull(getProperties().getProperty("companyId"))){
			companyId =	getProperties().getProperty("companyId").split(",");
		}
		return companyId;
	}
}
