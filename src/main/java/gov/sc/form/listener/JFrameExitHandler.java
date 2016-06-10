package gov.sc.form.listener;


import gov.sc.form.MainForm;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class JFrameExitHandler implements WindowListener{
	private JFrame jFrame;
	public JFrameExitHandler(MainForm form){
		this.jFrame = form.jFrame;
	}
	
	private void sureExit() {
		int result = JOptionPane.showConfirmDialog(jFrame, "是否退出程序");
		if (result == JOptionPane.YES_OPTION) {
			System.exit(0);
		}else{
			return;
		}
	}
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		sureExit();
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
