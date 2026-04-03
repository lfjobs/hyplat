package hy.ea.util;
/**
 * 加密
 */
import java.util.Properties;

import org.springframework.beans.factory.FactoryBean;


public class MyBasicDataSource implements FactoryBean{

	private Properties properties;   
    
    public Object getObject() throws Exception {   
        return getProperties();   
    }
    @SuppressWarnings("rawtypes")
	public Class getObjectType() {   
        return java.util.Properties.class;   
    }   
  
    public boolean isSingleton() {   
        return true;   
    }   
  
    public Properties getProperties() {   
        return properties;   
    }   


    public void setProperties(Properties inProperties) {   
        this.properties = inProperties;   
        String originalUsername = properties.getProperty("user");   
        String originalPassword = properties.getProperty("password");  
        if (originalUsername != null){   
            String newUsername ="hyplat";   
            properties.put("user", newUsername);   
        }   
        if (originalPassword != null){   
            String newPassword ="hyplatttsw";   
            properties.put("password", newPassword);   
        }   
        
    }   
    

	
}
