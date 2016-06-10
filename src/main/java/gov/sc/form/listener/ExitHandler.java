package gov.sc.form.listener;

import gov.sc.file.WriteTxt;
import gov.sc.form.ConfForm;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class ExitHandler implements WindowListener {
	JFrame jFrame;
	JTextArea jTextArea;
	ConfForm con;

	public ExitHandler(ConfForm con) {
		this.jTextArea = con.jTextArea;
		this.jFrame = con.jFrame;
		this.con = con;

	}

	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		int result = JOptionPane.showConfirmDialog(jFrame, "是否保存文件");
		if (result == JOptionPane.YES_OPTION) {
			WriteTxt writeTxt = new WriteTxt(con);
			writeTxt.write();
			jFrame.dispose();
		} else if (result == JOptionPane.NO_OPTION) {
			jFrame.dispose();
		}
	}

	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

}
