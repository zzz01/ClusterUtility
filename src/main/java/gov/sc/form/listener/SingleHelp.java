package gov.sc.form.listener;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.Frame;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


@SuppressWarnings("serial")
public class SingleHelp extends JDialog {
	private static JFrame jFrame;
	private static SingleHelp single;
	private SingleHelp() {
	}
	public static SingleHelp getInstance(){
		if(single == null){
			single = new SingleHelp();
			jFrame = new JFrame("HELP");
			}
		Jframe();
		return single;
	}
	public static void Jframe() {
		JTextArea jTextArea = null;
		JScrollPane jScrollPane;
		Container contentPane = jFrame.getContentPane();
		contentPane.setLayout(new BorderLayout());
		ImageIcon icon = new ImageIcon("./image/filter.png");
		jFrame.setIconImage(icon.getImage());
		jTextArea = new JTextArea();
		jTextArea.setTabSize(4);
		jTextArea.setFont(new Font("宋体", Font.BOLD, 13));
		jTextArea.setLineWrap(true);
		jTextArea.setWrapStyleWord(true);
		jTextArea.setEditable(false);
		jTextArea
				.setText("  过滤器（FilterUtil.exe）1.0版本能够实现根据目标文件（excel文件）中指定列的内容对目标文件中的数据进行重新整理排序，整理排序后的结果为：指定列内容相同或者相似度达到指定数值的数据的行号是连续的。\n本程序为绿色版本，无需安装，但要求所有文件必须保持在同一目录下，请勿对其中的任何文件进行修改，否则将对处理结果产生影响。\n  1.双击FilterUtil.exe可执行程序，可进入程序主界面，开始按钮此时不可以使用，当你根据要求输入合法信息后，开始按钮变为可用（见第6步）。\n  2.点击浏览按钮，出现文件选择对话框。根据目标文件所在的目录选取目标文件，该过滤器只针对excel文件(.xls，.xlsx)。\n  3.选定目标文件后，点击选择按钮，此时该对话框消失，并可看到程序主界面中“请选择文件”标签后的文本框内出现当前选择的文件的路径.\n  4.请在“请输入目标列”标签后的文本框中输入你想要过滤的目标列的列符，如：我们使用excel程序打开刚才选择的文件，其中B列为我们想要过滤的目标列，那么在文本框中输入“B”（或者“b”，不区分大小写，包含双引号）。注意，在该文本框中可以输入多个字符，结果则会定位到第一个字符。\n  5.在”请输入时间列”标签后的文本框中，你根据实际情况输入正确的时间列。\n  6.根据要求输入相关信息后，此时开始按钮变为可用。点击开始按钮，程序开始执行。此时，程序最底部的进度条显示当前处理进度。在程序执行期间，开始和浏览按钮变为不可用，两个文本框也变为只读状态，程序执行完毕后，恢复执行前状态。");
		jScrollPane = new JScrollPane(jTextArea);
		contentPane.add(jScrollPane, BorderLayout.CENTER);
		jFrame.setState(Frame.NORMAL);
		jFrame.setSize(520, 430);
		jFrame.setLocation(400, 200);
		jFrame.setVisible(true);
		jFrame.requestFocus();
	}
}
