package gov.sc.form.listener;

import gov.sc.form.ConfForm;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConfMiHandler implements ActionListener {
	public void actionPerformed(ActionEvent e) {
		ConfForm con = new ConfForm();
		con.setForm();
	}
}
