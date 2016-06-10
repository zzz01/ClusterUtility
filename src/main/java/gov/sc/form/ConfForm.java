package gov.sc.form;

import gov.sc.file.ReadTxt;
import gov.sc.form.listener.ExitHandler;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.Frame;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ConfForm {
	public JFrame jFrame;
	public JTextArea jTextArea;
	JScrollPane jScrollPane;
	public void setForm(){
		if (jFrame == null) {
			jFrame = new JFrame("停用词配置");
			jTextArea = new JTextArea();
			jFrame.addWindowListener(new ExitHandler(this));
			jFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		}
		jScrollPane = new JScrollPane(jTextArea);
		Container contentPane = jFrame.getContentPane();
		contentPane.setLayout(new BorderLayout());
		contentPane.add(jScrollPane, BorderLayout.CENTER);
		jFrame.setEnabled(true);
		jFrame.setState(Frame.NORMAL);
		jFrame.setSize(520, 430);
		jFrame.setLocation(400, 200);
		jFrame.setVisible(true);
		jFrame.requestFocus();
		ImageIcon icon = new ImageIcon("images/filter.jpg");
		jFrame.setIconImage(icon.getImage());
		jTextArea.setTabSize(4);
		jTextArea.setFont(new Font("宋体", Font.BOLD, 13));
		jTextArea.setLineWrap(true);
		jTextArea.setWrapStyleWord(true);
		jTextArea.setEditable(true);
		String content = null;
		ReadTxt read = new ReadTxt();
		try {
			content = read.readTxt();
		} catch (Exception e) {
			e.printStackTrace();
		}
		jTextArea.setText(content);
	}
	
}
