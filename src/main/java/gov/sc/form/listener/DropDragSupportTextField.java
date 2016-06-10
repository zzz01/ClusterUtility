package gov.sc.form.listener;

import gov.sc.file.ReadExcelFile;
import gov.sc.form.MainForm;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

@SuppressWarnings("serial")
public class DropDragSupportTextField extends JTextField implements
		DropTargetListener {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(DropDragSupportTextField.class);
	private MainForm form;
	private DropThread dropThread = null;
	//判断是否正在解析。
	public static boolean begPress = false;
	private JFrame jFrame;
	public DropDragSupportTextField(MainForm form) {
		this.form = form;
	    this.jFrame = form.jFrame;
		new DropTarget(jFrame, DnDConstants.ACTION_COPY_OR_MOVE, this,
				true);
	}
	
	private void addItem() throws FileNotFoundException, IOException {
		JComboBox<String> selectTarCol = form.selectTarCol;
		JComboBox<String> selectTarTim = form.selectTarTim;
		JProgressBar proBar = form.progressbar;
		List<String[]> cells = ReadExcelFile.getInstance(
				form.srcPthTxtFiled.getText()).getCells();
		selectTarCol.removeAllItems();
		selectTarTim.removeAllItems();
		String[] items = cells.get(0);
		for (int i = 0; i < items.length; i++) {
			selectTarCol.addItem(items[i]);
			selectTarTim.addItem(items[i]);
			if (items[i].matches("标题")) {
				selectTarCol.setSelectedIndex(i);
				continue;
			}
			if (items[i].matches(".*时间|.*日期")) {
				selectTarTim.setSelectedIndex(i);
			}
		}
		proBar.setString("获取文件成功");
		form.begBut.setEnabled(true);
		dropThread = null;
	}

	public void dragEnter(DropTargetDragEvent dtde) {

	}

	public void dragOver(DropTargetDragEvent dtde) {
		// TODO Auto-generated method stub
	}

	public void dropActionChanged(DropTargetDragEvent dtde) {
		// TODO Auto-generated method stub
	}

	public void dragExit(DropTargetEvent dte) {
		// TODO Auto-generated method stub
	}

	@SuppressWarnings("deprecation")
	public void drop(final DropTargetDropEvent dtde) {
		
		//判断是否存在已经存在的拖入线程，如果存在停止该线程。
		if(begPress)
		{
			JOptionPane.showMessageDialog(jFrame, "正在解析，请稍后...");
			return;
		}
		if(dropThread != null)
			dropThread.stop();
		
		JProgressBar proBar = form.progressbar;
		form.begBut.setEnabled(false);
		proBar.setString("正在解析，请稍后...");
		try {
			Transferable tr = dtde.getTransferable(); // 得到传递来的数据对象
			// 处理数据对象，得到其中的文本信息
			if (dtde.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
				dtde.acceptDrop(dtde.getDropAction());
				@SuppressWarnings("unchecked")
				List<File> s = (List<File>) tr.getTransferData(DataFlavor.javaFileListFlavor);			
				String addr = s.get(s.size()-1).getAbsolutePath();			
				if(addr.endsWith(".xls") || addr.endsWith(".xlsx"))
				{
					 // 在放置目标上显示从拖拽源传递来的文本信息
					form.srcPthTxtFiled.setText(addr);
					dtde.dropComplete(true);
					dropThread = new DropThread();
					dropThread.start();
				} else {
					form.progressbar.setString("请选择excel文件格式...");
				}

			} else {
				dtde.rejectDrop();
			}
		} catch (Exception err) {
			err.printStackTrace();
			form.progressbar.setString("文件获取失败.....");
			logger.info("initialization failed!" + err.toString());
		}
	}
	
	public class DropThread extends Thread
	{
		@Override
		public void run()
		{
			try
			{
				addItem();
			} catch (FileNotFoundException e)
			{
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			} catch (IOException e)
			{
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
	}
}
