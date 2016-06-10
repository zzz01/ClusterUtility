package gov.sc.form;

//添加注释 测试
import gov.sc.form.listener.BegButHandler;
import gov.sc.form.listener.ConfMiHandler;
import gov.sc.form.listener.DropDragSupportTextField;
import gov.sc.form.listener.ExitMiHandler;
import gov.sc.form.listener.HelpMiHandler;
import gov.sc.form.listener.JFrameExitHandler;
import gov.sc.form.listener.OpenMiHandler;
import gov.sc.form.listener.ScanButHandler;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class MainForm {
	public final JFrame jFrame = new JFrame();
	public final JTextField srcPthTxtFiled = new DropDragSupportTextField(this);
	public final JComboBox<String> selectTarCol = new JComboBox<String>();
	public final JComboBox<String> selectTarTim = new JComboBox<String>();
	public final JButton scanBut = new JButton("浏览");
	public final JButton begBut = new JButton("开始");
	public final JProgressBar progressbar = new JProgressBar(0, 1000);
	public final JMenu fileMenu = new JMenu("文件");
	public final JMenu helpMenu = new JMenu("帮助");
	public final JMenuItem helpMI = new JMenuItem("使用帮助");
	public final JMenuItem openMI = new JMenuItem("打开");
	public final JMenuItem exitMI = new JMenuItem("退出");
	public final JMenuItem confMI = new JMenuItem("配置");
	public final JLabel selectFile = new JLabel("选择文本:");
	public final JLabel selectCol = new JLabel("目标列:");
	public final JLabel selectTim = new JLabel("时间列:");

	public void createForm() {

		JPanel jpanela = new JPanel();
		JPanel jpanelb = new JPanel();
		JPanel jpanelc = new JPanel();
		JPanel jpaneld = new JPanel();
		JPanel jpanele = new JPanel();
		JPanel jpanelf = new JPanel();
		JMenuBar menuBar = new JMenuBar();
		jpanela.setPreferredSize(new Dimension(150, 60));
		jpanelb.setPreferredSize(new Dimension(150, 55));
		jFrame.add(jpanela, BorderLayout.NORTH);
		jFrame.add(jpanelb, BorderLayout.CENTER);// 三个嵌套
		jFrame.add(jpanelc, BorderLayout.SOUTH);
		jpanelb.add(jpaneld, BorderLayout.WEST);
		jpanelb.add(jpanele, BorderLayout.EAST);
		jpanelb.add(jpanelf, BorderLayout.SOUTH);
		jpanela.add(selectFile);
		jpanela.add(srcPthTxtFiled);
		jpanela.add(scanBut);
		jpaneld.add(selectCol);
		jpaneld.add(selectTarCol);
		jpanele.add(selectTim);
		jpanele.add(selectTarTim);
		jpanelf.add(begBut);
		jpanelc.add(progressbar);
		jFrame.setJMenuBar(menuBar);
		menuBar.add(fileMenu);
		menuBar.add(helpMenu);
		fileMenu.add(openMI);
		fileMenu.add(confMI);
		fileMenu.add(exitMI);
		helpMenu.add(helpMI);
		// 窗体
		jFrame.setTitle("舆情监测器");
		// srcPthTxtFiled
		srcPthTxtFiled.setColumns(21);
		srcPthTxtFiled.setFont(new Font("宋体", Font.BOLD, 24));
		srcPthTxtFiled.setPreferredSize(new Dimension(446, 33));
		srcPthTxtFiled.setEditable(false);
		// selectTarCol
		selectTarCol.setFont(new Font("宋体", Font.BOLD, 25));
		selectTarCol.addItem("请选择文件");
		selectTarCol.setPreferredSize(new Dimension(130, 30));
		selectTarCol.setLayout(null);
		// selectTarTim
		selectTarTim.setFont(new Font("宋体", Font.BOLD, 25));
		selectTarTim.addItem("请选择文件");
		selectTarTim.setPreferredSize(new Dimension(130, 30));
		selectTarTim.setFont(new Font("宋体", Font.BOLD, 20));
		// scanBut
		scanBut.setFont(new Font("宋体", Font.BOLD, 25));
		// begBut
		begBut.setFont(new Font("宋体", Font.BOLD, 45));
		// progressbar
		progressbar.setFont(new Font("宋体", Font.BOLD, 22));// 进度条大小
		progressbar.setStringPainted(true);
		progressbar.setForeground(Color.GREEN);
		progressbar.setString("选择Excel文件");
		progressbar.setPreferredSize(new Dimension(606, 33));
		// fileMenu
		fileMenu.setFont(new Font("宋体", Font.BOLD, 15));
		// helpMenu
		helpMenu.setFont(new Font("宋体", Font.BOLD, 15));
		// fileMenu
		fileMenu.setPreferredSize(new Dimension(50, 25));
		// helpMI
		helpMI.setFont(new Font("宋体", Font.BOLD, 13));
		helpMI.setIcon(new ImageIcon("images/help.jpg"));
		// openMI
		openMI.setFont(new Font("宋体", Font.BOLD, 13));
		openMI.setIcon(new ImageIcon("images/open.jpg"));// xiugai
		// exitMI
		exitMI.setFont(new Font("宋体", Font.BOLD, 13));
		exitMI.setIcon(new ImageIcon("images/exit.jpg"));
		// confMI
		confMI.setFont(new Font("宋体", Font.BOLD, 13));
		confMI.setIcon(new ImageIcon("images/config.png"));
		// selectFile
		selectFile.setPreferredSize(new Dimension(130, 63));
		selectFile.setFont(new Font("宋体", Font.BOLD, 25));
		// selectCol
		selectTarCol.setFont(new Font("宋体", Font.BOLD, 20));
		selectCol.setFont(new Font("宋体", Font.BOLD, 25));
		selectCol.setPreferredSize(new Dimension(110, 45));
		// selectTim
		selectTim.setPreferredSize(new Dimension(110, 45));
		selectTim.setFont(new Font("宋体", Font.BOLD, 25));
		ImageIcon icon = new ImageIcon("images/filter.jpg");
		jFrame.setIconImage(icon.getImage());
		jFrame.pack();
		jFrame.setVisible(true);
		jFrame.setBounds(new Rectangle(320, 290, 606, 315));
		jFrame.setResizable(false);// 大小不变
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void setListener() {
		jFrame.addWindowListener(new JFrameExitHandler(this));
		helpMI.addActionListener(new HelpMiHandler());// 类名
		confMI.addActionListener(new ConfMiHandler());
		exitMI.addActionListener(new ExitMiHandler());
		openMI.addActionListener(new OpenMiHandler(this));
		scanBut.addActionListener(new ScanButHandler(this));
		begBut.addActionListener(new BegButHandler(this));
	}

	public static void main(String[] args) {
		JFrame.setDefaultLookAndFeelDecorated(true);
		try {
			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		MainForm ui = new MainForm();
		ui.createForm();
		ui.setListener();
	}
}
