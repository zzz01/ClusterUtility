package gov.sc.cluster;

import gov.sc.seg.Analysis;
import gov.sc.utils.Time;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JProgressBar;

/**
 * 该类是用于对数据进行聚类，聚类的完整过程包括：对目标数据进行分词、对分词结果进行聚类；在实现过程中为了减少内存的消耗，在聚类过程中
 * 用数据的index代替该条数据，则可以不用保存所有的数据；但需要在聚类实现后，将结果中的index替换为完整的数据；并对每个簇中的数据以
 * 目标列为目标，进行排序，按照Unicode编码进行排序。 该类中还实现了获取聚类结果中每个簇中时间最早的一条数据
 * 该类中计算两条数据的方式为交叉对比取平均，即假设有A和B两条数据，分词的结果为a[]和b[]； 计算公式为： sim=(sima+simb)/2;
 * sima=na/lengtha;其中na表示a数组中的元素在b中出现的个数，lengtha表示数组a的长度
 * simb=nb/lengthb;其中nb表示b数组中的元素在a中出现的个数，lengthb表示数组b的长度
 * 该类中计算一条数据与一个类的相似度方式为求该条数据与类中每条数据的相似度的平均数，计算公式为：
 * sim=∑(i∈[1,n])sim(L,Li)/n;其中L表示目标数据，Li表示类中的第i条数据。
 * 
 * @author Kevin
 * @ClassName: Cluster
 * @date 2016年6月4日 上午10:08:15
 */
public class Cluster {
	private List<String[]> cells; // 存放文件中的所有数据，每一个元素都是一个string数组，数组的每一个元素都对应一个列
	private int tarLine; // 目标列的index
	private int timeLine; // 时间列的index
	private List<String[]> list_seg; // 对目标列中的文本句子进行分词，然后将词存入string数组
	private Analysis analysis = Analysis.getInstance(); // 用单例模式建立分词类的对象
	private List<List<Integer>> result_int; // 表示聚类结果的index集合（Integer对应每一条数据的index）
	private List<List<String[]>> result_set_all; // 表示聚类结果的原始数据集合
	private List<String[]> result_all; // 表示聚类结果的原始数据(非集合)
	private List<String[]> result_original; // 聚类结果中时间最早的数据的集合

	/**
	 * 构造函数
	 * 
	 * @param cells
	 *            文件中的原始数据
	 * @param tarLine
	 *            目标列的index
	 * @param timeLine
	 *            时间列的index
	 */
	public Cluster(List<String[]> cells, int tarLine, int timeLine) {
		this.cells = cells;
		this.tarLine = tarLine;
		this.timeLine = timeLine;
		// list_seg = getSegmentList();
	}

	public Cluster() {
	}

	/**
	 * @Title: getSegmentList
	 * @Description: 得到目标列中句子的分词结果，并保存起来
	 * @param
	 * @return void
	 * @throws
	 */
	private void getSegmentList(JProgressBar proBar) {
		if (cells == null || tarLine < 0 || cells.size() == 0) {
			throw new IllegalArgumentException("原始的list和目标列数据不合法");
		}
		int proBarValue = proBar.getValue();
		list_seg = new ArrayList<String[]>();
		int i = 0;
		for (String[] row : cells) {
			i++;
			list_seg.add(analysis.parse(row[tarLine]));
			proBar.setValue(proBarValue + i);
		}
	}

	/**
	 * 
	 * @Title: cluster
	 * @Description: 聚类函数，对分词结果保存的容器进行聚类，然后将对应的index保存起来，这样可以减少内存的消耗
	 * @param
	 * @return void
	 * @throws
	 */
	private void cluster(JProgressBar proBar) {
		if (list_seg == null || list_seg.size() == 0) {
			throw new IllegalArgumentException("分词列表是空的");
		}
		int proBarValue = proBar.getValue();
		result_int = new ArrayList<List<Integer>>();
		for (int i = 1; i < list_seg.size(); i++) {
			int max_sim_set_index = -1;
			float max_sim = -1.0f;
			if (i == 0) {
				List<Integer> set = new ArrayList<Integer>();
				set.add(0);
				result_int.add(set);
				continue;
			}
			for (int j = 0; j < result_int.size(); j++) {
				float sim = getSim(list_seg.get(i), result_int.get(j));
				if (max_sim < sim) {
					max_sim = sim;
					max_sim_set_index = j;
				}
			}
			if (max_sim <= 0.40f) {
				List<Integer> set = new ArrayList<Integer>();
				set.add(i);
				result_int.add(set);
			} else {
				result_int.get(max_sim_set_index).add(i);
			}
			proBar.setValue(proBarValue + i);
		}
		Collections.sort(result_int, new Comparator<List<Integer>>() {
			public int compare(List<Integer> o1, List<Integer> o2) {
				return o2.size() - o1.size();
			}

		});
	}

	/**
	 * 
	 * @Title: getSim
	 * @Description: 计算一条数据与一个类的相似度：分别计算这条数据与类中每一条数据的距离，然后取平均数
	 * @param @param seg 一条数据的分词结果
	 * @param @param set 一个类
	 * @param @return
	 * @return float 相似度
	 * @throws
	 */
	private float getSim(String[] seg, List<Integer> set) {
		if (seg.length == 0 || set.size() == 0) {
			return 0.0f;
		}
		float sum = 0.0f;
		for (int i : set) {
			sum += getSim(seg, list_seg.get(i));
		}
		return sum / set.size();
	}

	/**
	 * 
	 * @Title: getSim
	 * @Description: 计算两条数据分词结果的相似度，计算方式：交叉计算，即计算A与B的相似度和B与A的相似度，然后取平均
	 * @param @param seg_1 第一条数据的分词结果
	 * @param @param seg_2 第二条数据的分词结果
	 * @param @return
	 * @return float 两条数据的相似度
	 * @throws
	 */
	private float getSim(String[] seg_1, String[] seg_2) {
		if (seg_1.length == 0 || seg_2.length == 0) {
			return 0.0f;
		}
		// int length = seg_1.length > seg_2.length ? seg_2.length :
		// seg_1.length;
		int count = 0;
		for (String word_1 : seg_1) {
			for (String word_2 : seg_2) {
				if (word_1.equals(word_2)) {
					count++;
					break;
				}
			}
		}
		float sim_1 = (float) count / (float) seg_1.length;
		count = 0;
		for (String word_2 : seg_2) {
			for (String word_1 : seg_1) {
				if (word_2.equals(word_1)) {
					count++;
					break;
				}
			}
		}
		float sim_2 = (float) count / (float) seg_2.length;

		return (sim_1 + sim_2) / 2.0f;
	}

	/**
	 * 将用index表示的聚类结果转化为实际index对应的数据
	 * 
	 * @Title: changeIntSetToStringSet
	 * @Description: 将用index表示的聚类结果转化为实际index对应的数据
	 * @param
	 * @return void
	 * @throws
	 */
	private void changeIntSetToStringSet() {
		if (result_int == null) {
			return;
		}
		result_set_all = new ArrayList<List<String[]>>();
		List<String[]> singleList = new ArrayList<String[]>();
		for (List<Integer> set : result_int) {
			if(set.size()==1){
				singleList.add(cells.get(set.get(0)));
				continue;
			}
			List<String[]> list_result_set = new ArrayList<String[]>();
			for (int i : set) {
				list_result_set.add(cells.get(i));
			}
			Collections.sort(list_result_set, new Comparator<String[]>() {

				public int compare(String[] o1, String[] o2) {
					return o1[tarLine].compareTo(o2[tarLine]);
				}
			});
			result_set_all.add(list_result_set);
		}
		
		Collections.sort(singleList, new Comparator<String[]>() {

			public int compare(String[] o1, String[] o2) {
				return o1[tarLine].compareTo(o2[tarLine]);
			}
		});
		result_set_all.add(singleList);
	}

	private void changeSetToCells() {
		if (result_set_all == null)
			return;
		result_all = new ArrayList<String[]>();
		result_all.add(cells.get(0));
		int rowLength = cells.get(0).length;
		for (List<String[]> list : result_set_all) {
			for (String[] row : list) {
				result_all.add(row);
			}
			/**
			 * 新加一行空行，以区分类间的区别
			 */
			result_all.add(new String[rowLength]);
		}
	}

	/**
	 * 
	 * @Title: process_all
	 * @Description: 执行聚类过程，先分词，再聚类，最后转化数据
	 * @param
	 * @return void
	 * @throws
	 */
	private void process_all(JProgressBar proBar) {
		proBar.setMaximum(cells.size() * 3);
		getSegmentList(proBar);
		cluster(proBar);
		proBar.setMaximum(proBar.getMaximum()+result_int.size());
		changeIntSetToStringSet();
		changeSetToCells();
	}

	/**
	 * 对外提供的获取聚类结果的接口
	 * 
	 * @Title: getResult_all
	 * @Description: 对外提供的获取聚类结果的接口
	 * @param @return
	 * @return List<List<String[]>>
	 * @throws
	 */
	public List<String[]> getResult_all(JProgressBar proBar) {
		if (result_all == null) {
			process_all(proBar);
		}
		return result_all;
	}

	/**
	 * 获取每个类中时间最早的一条数据
	 * 
	 * @Title: process_original
	 * @Description: 获取每个类中时间最早的一条数据
	 * @param
	 * @return void
	 * @throws
	 */
	private void process_original(JProgressBar proBar) {
		if (result_int == null) {
			process_all(proBar);
		}
		result_original = new ArrayList<String[]>();
		/**
		 * 添加第一行（列名），并在最开始插入"总计"列
		 */
		int proBarValue = proBar.getValue();
		String[] firstRow = cells.get(0);
		String[] newFirstRow = new String[firstRow.length + 1];
		newFirstRow[0] = "总计";
		for (int i = 0; i < firstRow.length; i++) {
			newFirstRow[i + 1] = firstRow[i];
		}
		result_original.add(newFirstRow);
		int count = 0;
		for (List<Integer> set : result_int) {
			if (set.size() == 1) {
				continue;
			}
			int originalIndex = -1;
			String originalTime = Time.convert("9000-01-01");
			String maxNumContent = "";
			Map<String, Integer> map = new HashMap<String, Integer>();
			for (int index : set) {
				String time = Time.convert(cells.get(index)[timeLine]);
				if (Time.compare(time, originalTime) < 0) {
					originalIndex = index;
					originalTime = time;
				}
				String content = cells.get(index)[tarLine];
				if (map.containsKey(content)) {
					map.put(content, map.get(content) + 1);
				} else {
					map.put(content, 1);
				}
				proBar.setValue(proBarValue + count);
			}
			if (originalIndex == -1) {
				originalIndex = set.get(0);
				maxNumContent = cells.get(set.get(0))[tarLine];
			} else {
				int max = -1;
				for (String key : map.keySet()) {
					if (map.get(key) > max) {
						max = map.get(key);
						maxNumContent = key;
					}
				}
			}

			String[] row = cells.get(originalIndex);
			row[tarLine] = maxNumContent;
			String[] newRow = new String[row.length + 1];
			for (int i = 0; i < row.length; i++) {
				newRow[i + 1] = row[i];
			}
			newRow[0] = set.size() + "";
			result_original.add(newRow);
		}
	}

	/**
	 * 对外提供的获取类中时间最早数据的接口
	 * 
	 * @Title: getResult_original
	 * @Description: TODO
	 * @param @return
	 * @return List<String[]>
	 * @throws
	 */
	public List<String[]> getResult_original(JProgressBar proBar) {
		if (result_original == null) {
			process_original(proBar);
		}
		return result_original;
	}

	public static void main(String[] args) {
		Cluster cluster = new Cluster();
		System.out.println(cluster.getSim(cluster.analysis.parse("必有路也许也就只"),
				cluster.analysis.parse("行约时候了他也许就能")));
	}
}
