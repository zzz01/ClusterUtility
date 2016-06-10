package gov.sc.seg;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.ToAnalysis;
import org.ansj.util.FilterModifWord;

public class Analysis {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(Analysis.class);

	private static Analysis als;

	public Analysis() {
		try {
			loadStopWord();
		} catch (FileNotFoundException e) {
			logger.info("load stopwords dictionary fails");
		} catch (IOException e) {
			logger.info("load stopwords dictionary fails");
		}
	}

	private void loadStopWord() throws IOException {
		File stopwords = new File("library\\stopwords.dic");
		FileReader fr = new FileReader(stopwords);
		BufferedReader br = new BufferedReader(fr);
		String line = br.readLine();
		while (line != null) {
			FilterModifWord.insertStopWord(line);
			line = br.readLine();
		}
		br.close();
		fr.close();
	}

	public String[] parse(String str) {
		List<Term> res = ToAnalysis.parse(str);
		res = FilterModifWord.modifResult(res);
		String[] words = new String[res.size()];
		for (int i = 0; i < res.size(); i++) {
			words[i] = res.get(i).getName();
		}
		return words;
	}

	public static Analysis getInstance() {
		if (als == null) {
			als = new Analysis();
		}
		return als;
	}

	public static void main(String[] args) {
		Analysis ana = new Analysis();
		String str = "尊敬的律师。:本人原是一名普通警察，2011年个人受贿3.04万元";
		System.out.println(ana.parse(str).toString());
	}
}
