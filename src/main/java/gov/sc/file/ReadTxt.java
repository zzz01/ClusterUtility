package gov.sc.file;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class ReadTxt {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ReadTxt.class);

	public String readTxt() throws Exception {
		StringBuilder sb = new StringBuilder();
		String line = "";
		String encoding = "utf-8";
		File file = new File("library/stopwords.dic");
		if (file.isFile() && file.exists()) { // 判断文件是否存在
			InputStreamReader read = new InputStreamReader(new FileInputStream(
					file), encoding);// 考虑到编码格式
			BufferedReader bufferedReader = new BufferedReader(read);
			while ((line = bufferedReader.readLine()) != null) {
				sb.append(line);
				sb.append("\n");
			}
			read.close();
		} else {
			logger.info("stopwords file not found");
		}
		return sb.toString();
	}
}
