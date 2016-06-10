package gov.sc.form.listener;

import gov.sc.file.ReadExcelFile;
import gov.sc.file.WriteExcelFile;
import gov.sc.cluster.Cluster;
import gov.sc.form.MainForm;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;

import org.apache.log4j.Logger;

public class BegButHandler implements ActionListener {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(BegButHandler.class);
	private MainForm form;
	private JFrame jFrame;
	public BegButHandler(MainForm form) {
		this.form = form;
		this.jFrame = form.jFrame;
	}

	public void actionPerformed(ActionEvent e) {
		
		DropDragSupportTextField.begPress = true;
		
		String reFile = form.srcPthTxtFiled.getText().trim();
		if (reFile == null || reFile.equals("")) {
			JOptionPane.showMessageDialog(jFrame, "请选择Excel文件");
			return;
		}
		HandleThread ht = new HandleThread(form);
		ht.start();
	}

	static class HandleThread extends Thread {
		private JProgressBar proBar;
		private JButton begBut;
		private JButton scanBut;
		private JComboBox<String> selectTarCol;
		private JComboBox<String> selectTarTim;
		private String reFile;
		private List<String[]> cells;
		private JFrame jFrame;
		public HandleThread(MainForm form) {
			this.proBar = form.progressbar;
			this.begBut = form.begBut;
			this.scanBut = form.scanBut;
			this.selectTarCol = form.selectTarCol;
			this.selectTarTim = form.selectTarTim;
			this.reFile = form.srcPthTxtFiled.getText();
			this.jFrame = form.jFrame;
		}

		private void showErrorMessage() {
			JOptionPane.showMessageDialog(jFrame, "文件解析失败");
			proBar.setValue(0);
			proBar.setString("请选择正确目标列和时间列");
			begBut.setEnabled(true);
			scanBut.setEnabled(true);
		}

		@Override
		public void run() {
			File fileAll = new File(reFile.replace(".xls",
					"(过滤后所有数据" + selectTarCol.getSelectedItem() + "+"
							+ selectTarTim.getSelectedItem() + ").xls"));
			File fileSta = new File(reFile.replace(".xls",
					"(过滤后统计数据" + selectTarCol.getSelectedItem() + "+"
							+ selectTarTim.getSelectedItem() + ").xls"));
			proBar.setString("判断是否解析过此文件，请稍后...");
			if (fileAll.exists()) {
				int Yes = JOptionPane.showConfirmDialog(jFrame,
						"文件已经解析过，是否删除已存在的文件，并生成新文件");
				if (Yes == JOptionPane.YES_OPTION) {
					fileAll.delete();
					fileSta.delete();
				} else {
					return;
				}
			}
			proBar.setString("数据解析中，请稍后...");
			begBut.setEnabled(false);
			scanBut.setEnabled(false);
			try {
				ReadExcelFile ref = ReadExcelFile.getInstance(reFile);
				cells = ref.getCells();
			} catch (Exception e) {
				logger.info("read Excel file error---->" + e.toString());
				return;
			}
			int tarline = selectTarCol.getSelectedIndex();
			int timeline = selectTarTim.getSelectedIndex();
			Cluster cluster = new Cluster(cells, tarline, timeline);
			List<String[]> reList = cluster.getResult_all(proBar);
			WriteExcelFile write = new WriteExcelFile(reFile.replace(".xls",
					"(过滤后所有数据" + selectTarCol.getSelectedItem() + "+"
							+ selectTarTim.getSelectedItem() + ").xls"));
			try {
				proBar.setString("将结果写入文件，请稍后...");
				write.write(reList,proBar);
			} catch (Exception e) {
				logger.info("write all result file error--->" + e.toString());
				showErrorMessage();
				return;
			}
			try {
				write.setFile(reFile.replace(".xls",
						"(过滤后统计数据" + selectTarCol.getSelectedItem() + "+"
								+ selectTarTim.getSelectedItem() + ").xls"));
				reList = cluster.getResult_original(proBar);// 统计结果
				write.write(reList,proBar);
			} catch (Exception e) {
				logger.info("write statistics result file error--->"
						+ e.toString());
				showErrorMessage();
				return;
			}
			proBar.setString("文件解析成功！！！");
			begBut.setEnabled(true);
			scanBut.setEnabled(true);
			proBar.setValue(0);
			DropDragSupportTextField.begPress = false;
		}
	}

}
