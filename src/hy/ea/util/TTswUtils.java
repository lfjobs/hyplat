package hy.ea.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * PropertiesUtils
 * 
 * @author Lynch 2014-09-15
 *
 */
public class TTswUtils {

	public static Properties getProperties() {

		Properties p = new Properties();

		try {
			InputStream inputStream = TTswUtils.class.getResourceAsStream(
					"/ttsw.properties");

			p.load(inputStream);

		} catch (IOException e1) {
			e1.printStackTrace();
		}

		return p;
	}
	
	
	public static String getPath(){
		
		String path = getProperties().getProperty("FFMPEG_PATH");
		
		return path;
	}
	
}
