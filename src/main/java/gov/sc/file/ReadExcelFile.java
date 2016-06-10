package gov.sc.file;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 该类继承自File类
 * 
 * @author Kevin
 *
 */
public class ReadExcelFile {

	private String file;
	private List<String[]> cells;
	private static ReadExcelFile ref = null;

	/**
	 * @param file
	 */
	public ReadExcelFile(String file) {
		this.file = file;
	}

	public static ReadExcelFile getInstance(String file) throws FileNotFoundException, IOException {
		if (ref == null) {
			ref = new ReadExcelFile(file);
		}else if(ref.file!=file){
			ref.file=file;
			ref.readCells();
		}
		return ref;
	}

	/**
	 * 该方法返回从file中读取得到的目标列数据,每一个cell的数据都将保存为一个TargetCell对象，并保存在一个List中。
	 * 
	 * @param column_num
	 *            目标列的列号
	 * @return 返回得到的list
	 */
	public List<String> readTarCells(String column_num) {
		return null;
	}

	/**
	 * 该方法返回从file中读取得到的全部数据,每一行数据保存在一个String数组中，并将所有数组保存到一个list中
	 * 
	 * @return 返回得到的list
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public void readCells() throws FileNotFoundException, IOException {
		cells = new ArrayList<String[]>();
		Workbook workbook;
		if (file.endsWith(".xls")) {
			workbook = new HSSFWorkbook(new FileInputStream(file));
		} else {
			workbook = new XSSFWorkbook(new FileInputStream(file));
		}
		Sheet sheet = workbook.getSheetAt(0);
		int rowNum = sheet.getLastRowNum();
		int colNum = sheet.getRow(0).getLastCellNum();
		for (int i = 0; i <= rowNum; i++) {
			String[] rowStr = new String[colNum];
			for (int j = 0; j < colNum; j++) {
				try {
					rowStr[j] = sheet.getRow(i).getCell(j).toString();
				} catch (Exception e) {
					rowStr[j] = "";
				}
			}
			cells.add(rowStr);
		}
		workbook.close();
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public List<String[]> getCells() throws FileNotFoundException, IOException {
		if (cells == null) {
			readCells();
		}
		return cells;
	}

}
