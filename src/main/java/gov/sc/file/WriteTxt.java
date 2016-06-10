package gov.sc.file;

import gov.sc.form.ConfForm;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JTextArea;

public class WriteTxt {
	JTextArea jTextArea;

	public WriteTxt(ConfForm con) {
		this.jTextArea = con.jTextArea;
	}

	public void write() {
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(new File(
					"././library/stopwords.dic")));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			writer.write(jTextArea.getText());
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
