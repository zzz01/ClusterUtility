package gov.sc.file;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.swing.JProgressBar;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 该类继承自File类
 * 
 * @author Kevin
 *
 */
public class WriteExcelFile {

	private String file;

	/**
	 * @param file
	 */
	public WriteExcelFile(String file) {
		this.file = file;
	}

	/**
	 * 
	 */
	public WriteExcelFile() {
		// TODO Auto-generated constructor stub
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	/**
	 * 该方法用于将目标list写入targetFile中
	 * 
	 * @param list
	 *            list中的每一个元素都是String数组，表示excel中的一行数据
	 * @return 如果写入成功返回true，如果写入失败，返回false
	 * @throws IOException
	 */
	@SuppressWarnings("resource")
	public void write(List<String[]> list, JProgressBar proBar)
			throws IOException {
		Workbook workbook;
		if (file.endsWith(".xls")) {
			workbook = new HSSFWorkbook();
		} else {
			workbook = new XSSFWorkbook();
		}
		Sheet sheet = workbook.createSheet("sheet1");
		int proBarValue = proBar.getValue();
		for (int i = 0; i < list.size(); i++) {
			String[] rowList = list.get(i);
			Row row = sheet.createRow(i);
			for (int j = 0; j < rowList.length; j++) {
				Cell cell = row.createCell(j);
				cell.setCellValue(rowList[j]);
			}
			proBar.setValue(proBarValue + i);
		}
		FileOutputStream fout = new FileOutputStream(file);
		workbook.write(fout);
		fout.flush();
		fout.close();
	}
}
