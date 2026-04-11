package hy.ea.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.InputStreamReader;
import java.util.Vector;
/**
 * 获取网卡地址工具类
 * @author Administrator
 *
 */
public class GetNetMACShell {
	private static final Logger logger = LoggerFactory.getLogger(GetNetMACShell.class);
	private Process process = null;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Vector execute(String shellCommand) {
		try {
			Start(shellCommand);
			Vector vResult = new Vector();
			DataInputStream in = new DataInputStream(process.getInputStream());
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(in));

			String line;
			do {
				line = reader.readLine();
				if (line == null) {
					break;
				} else {
					vResult.addElement(line);
				}
			} while (true);
			reader.close();
			return vResult;

		} catch (Exception e) {
			// error
			return null;
		}
	}

	public void Start(String shellCommand) {
		try {
			if (process != null) {
				kill();
			}
			Runtime sys = Runtime.getRuntime();
			process = sys.exec(shellCommand);
		} catch (Exception e) {
			logger.info("调试信息");
		}
	}

	public void kill() {
		if (process != null) {
			process.destroy();
			process = null;
		}
	}
}