package gov.sc.utils;

import java.util.HashMap;
import java.util.regex.Pattern;

/**
 * 
 * @author chan
 *
 */

public class Time
{
	/**
	 * 
	 * @param String
	 * @return
	 */
	public static String convert(String date)
	{
		if(isContainAlphabet(date))
			return convertEngDate(date);
		
		return convertChnDate(date);
	}
	
	/**
	 * 对两个时间进行比较：d1>d2则返回1,d1==d2则返回0,d1<d2则返回-1。
	 * 例如 d1 = 2016/5/30, d2 = 2016/5/29, 则d1 > d2. 
	 * 
	 * @param String d1, String d1
	 * @return
	 */
	public static int compare(String d1, String d2)
	{
		if(d1.compareTo(d2) > 0)
			return 1;
		else if(d1.compareTo(d2) < 0)
			return -1;
		return 0;
	}
	
	/**
	 * 判断字符串中是否含有	阿拉伯数字：0-9
	 * @param String
	 * @return
	 */
	private static boolean isContainArbNum(String str)
	{
		
        return Pattern.compile("[0-9]").matcher(str).find()? true:false;
	}
	
	/**
	 * 判断字符串中是否含有	中文数字	：零...
	 * 
	 * @param String
	 * @return
	 */
	private static boolean isContainChnNum(String str)
	{
		
        return Pattern.compile("[一二三四五六七八九十〇零壹贰叁肆伍陆柒捌玖拾]").matcher(str).find()? true:false;
	}

	/**
	 * 判断字符串中是否含有	字母		：a-z/A-Z(除去am pm)
	 * @param String
	 * @return
	 */
	private static boolean isContainAlphabet(String str)
	{
		
        return Pattern.compile("[b-lB-L]|[no]|[NO]|[q-z]|[Q-Z]").matcher(str).find()? true:false;
	}
	
	/**
	 * 判断字符串中是否含有	关键字符	：年月日...,:/-....
	 * 
	 * @param String
	 * @return
	 */
	private static boolean isContainKeyword(String str)
	{
			
	    return Pattern.compile("[年月日号点时分秒早凌晨晚上中下午,:：/-]").matcher(str).find()? true:false;
	}
	
	/**
	 * 判断字符串是否包含 英文星期。
	 * 
	 * @param String
	 * @return
	 */
	private static boolean isContainWeek(String str)
	{
		
	    return Pattern.compile("(monday|mon.|mon|tuesday|tue.|tue|tue|tues.|tues|"
	    		+ "wednesday|wed.|wed|thursday|thur.|thurs.|thur|thurs|friday|fri.|fri"
	    		+ "saturday|sat.|sat|sunday|sun.|sun)").matcher(str).find()? true:false;
	}
	
	/**
	 *判断字符串是否含有 am与pm
	 * 
	 * @param String
	 * @return
	 */
	private static boolean isContainAmOrPm(String str)
	{
		
	    return Pattern.compile("(am|a.m.|am.|a.m|"
	    		+ "pm|p.m.|pm.|p.m)").matcher(str).find()? true:false;
	}
	
	/**
	 * 判断'.'前面是否是数字，是则返回false
	 * 
	 * @param String, int
	 * @return
	 */
	private static boolean judgePot(String date, int i)
	{		
		return date.charAt(i)=='.' && isContainArbNum(String.valueOf(date.charAt(i-1)))?false:true;
	}
	
	/**
	 * 
	 * @param String
	 * @return
	 */
	private static int convertAmOrPm(String str)
	{
		HashMap<String, Integer>numMap = new HashMap<String, Integer>();
		numMap.put("am", 0);		numMap.put("a.m.", 0);
		numMap.put("am.", 0);		numMap.put("a.m", 0);
		numMap.put("pm", 12);		numMap.put("p.m.", 12);
		numMap.put("pm.", 12);		numMap.put("p.m", 12);
		
		return numMap.get(str);
	}
	
	/**
	 * 中文数字转换为阿拉伯数字		： 二零一六  - 2016
	 * 
	 * @param String
	 * @return
	 */
	private static String chnNumToArbNum(String hanzi)
	{
		HashMap<String, Integer>numMap = new HashMap<String, Integer>();
		numMap.put("〇", 0);		numMap.put("零", 0);
		numMap.put("一", 1);		numMap.put("壹", 1);
		numMap.put("二", 2);		numMap.put("贰", 2);
		numMap.put("三", 3);		numMap.put("叁", 3);
		numMap.put("四", 4);		numMap.put("肆", 4);
		numMap.put("五", 5);		numMap.put("伍", 5);
		numMap.put("六", 6);		numMap.put("陆", 6);
		numMap.put("七", 7);		numMap.put("柒", 7);
		numMap.put("八", 8);		numMap.put("捌", 8);
		numMap.put("九", 9);		numMap.put("玖", 9);
		numMap.put("十", 10);	numMap.put("拾", 10);
		
		int res = 0;
		boolean flag = true;
		for(int i=0; i<hanzi.length(); i++)
		{
			int tmp = numMap.get(String.valueOf(hanzi.charAt(i)));
			
			if(flag)
			{
				res = res * 10;
				if(tmp != 10 || i == 0)
					res += tmp;
			}
			else
			{
				flag = true;
				res += tmp;
			}
			
			if(tmp == 10)
				flag = false;
		}
		
		return String.valueOf(res);
	}	
	
	/**
	 * 英语月份或日转换为相应阿拉伯数字
	 * 
	 * @param String
	 * @return
	 */
	public static String engToArbNum(String date)
	{
		HashMap<String, Integer>numMap = new HashMap<String, Integer>();
		numMap.put("jan.",1);		numMap.put("may",5);		numMap.put("sept.",9);
		numMap.put("feb.",2);		numMap.put("jun.",6);		numMap.put("oct.",10);
		numMap.put("mar.",3);		numMap.put("jul.",7);		numMap.put("nov.",11);
		numMap.put("apr.",4);		numMap.put("aug.",8);		numMap.put("dec.",12);
		
		numMap.put("jan.",1);		numMap.put("may",5);		numMap.put("sept.",9);
		numMap.put("feb.",2);		numMap.put("jun.",6);		numMap.put("oct.",10);
		numMap.put("mar.",3);		numMap.put("jul.",7);		numMap.put("nov.",11);
		numMap.put("apr.",4);		numMap.put("aug.",8);		numMap.put("dec.",12);		
		
		numMap.put("january",1);								numMap.put("september",9);
		numMap.put("february",2);	numMap.put("june",6);		numMap.put("october",10);
		numMap.put("march",3);		numMap.put("july",7);		numMap.put("november",11);
		numMap.put("april",4);		numMap.put("august",8);		numMap.put("december",12);
		
		numMap.put("jy.",7);		numMap.put("sep.",9);
		
		numMap.put("1st", 1);		numMap.put("11st", 11);		numMap.put("21st", 21);
		numMap.put("2nd", 2);		numMap.put("12nd", 12);		numMap.put("22nd", 22);
		numMap.put("3rd", 3);		numMap.put("13rd", 13);		numMap.put("23rd", 23);
		numMap.put("4th", 4);		numMap.put("14th", 14);		numMap.put("24th", 24);
		numMap.put("5th", 5);		numMap.put("15th", 15);		numMap.put("25th", 25);
		numMap.put("6th", 6);		numMap.put("16th", 16);		numMap.put("26th", 26);
		numMap.put("7th", 7);		numMap.put("17th", 17);		numMap.put("27th", 27);
		numMap.put("8th", 8);		numMap.put("18th", 18);		numMap.put("28th", 28);
		numMap.put("9th", 9);		numMap.put("19th", 19);		numMap.put("29th", 29);
		numMap.put("10th", 10);		numMap.put("20th", 20);		numMap.put("30th", 30);
		numMap.put("31st", 31);	
		
		return String.valueOf(numMap.get(date));
	}

	/**
	 * 将英语日期（美式、英式）转换为标准 
	 * 
	 * @param String
	 * @return
	 */
	public static String convertEngDate(String date)
	{
		String[] setDate = new String[]{"2000", "01", "01", "00", "00", "00"};
		String month = "01";
		String day = "01";
		int apm = 0;
		try{
			for(int i=0,index=0; i<date.length(); i++)
			{
				while(date.charAt(i) == ' '  || isContainKeyword(String.valueOf(date.charAt(i))))
					i++;
					
				int begin;
				for(begin=i; i<date.length() && date.charAt(i)!=' ' &&
						!isContainKeyword(String.valueOf(date.charAt(i))); i++);
					
				String res = date.substring(begin, i);
				if(!isContainAmOrPm(res.toLowerCase()))
				{
					if(!isContainWeek(res.toLowerCase()))
					{
						if(isContainAlphabet(res))
							if(isContainArbNum(res))
								res = day = engToArbNum(res.toLowerCase());
							else
								res = month = engToArbNum(res.toLowerCase());
							else if(index < 2)
								day = res;
							
						
						res = String.valueOf(Integer.valueOf(res));
						
						if(Integer.valueOf(res) < 10)
							setDate[index++] = "0"+res;
						else
							setDate[index++] = res;
					}
				}
				else
					apm = convertAmOrPm(res.toLowerCase());
			}
			
			if(apm == 12)
			{
				int hour = Integer.valueOf(setDate[3])+apm;
				if(hour >= 24)
					setDate[3] = "00";
				else
					setDate[3] = String.valueOf(hour);
			}
			
			if(Integer.valueOf(day) < 10)
				day = "0"+day;
			if(Integer.valueOf(month) < 10)
				month = "0"+month;
		} catch(Exception e)
		{
			return date;
		}
		if(setDate[2].length()!=4 || month.length()!=2 && day.length()!=2)
		{
			return date;
		}
		
		return setDate[2]+"/"+month+"/"+day+" "+setDate[3]+":"+setDate[4]+":"+setDate[5];
	}
	
	/**
	 * 转换纯阿拉伯数字、纯中文、中文和阿拉伯数字混合为标准格式
	 * 
	 * @param String
	 * @return
	 */
	public static String convertChnDate(String date)
	{
		String[] setDate = new String[]{"2000", "01", "01", "00", "00", "00"};
		int apm = 0;
		try
		{
			for(int i=0,index=0; i<date.length(); i++)
			{
				while(date.charAt(i) == ' '  || isContainKeyword(String.valueOf(date.charAt(i))))
					i++;
					
				int begin;
				for(begin=i; i<date.length() && date.charAt(i)!=' ' && judgePot(date, i)
					&& !isContainKeyword(String.valueOf(date.charAt(i))); i++);
				
				String res = date.substring(begin, i);
				if(res.equals(""))	continue;
				if(!isContainAmOrPm(res.toLowerCase()))
				{
						//判断res是否是中文数字。
					if(isContainChnNum(res))
						res = chnNumToArbNum(res);
					else
						res = String.valueOf(Integer.valueOf(res));
						
					if(Integer.valueOf(res) < 10)
						setDate[index++] = "0"+res;
					else
						setDate[index++] = res;
				}
				else
					apm = convertAmOrPm(res.toLowerCase());
			}
				
			if(setDate[0].length() < 4)
			{
				String tmp = setDate[0];
				setDate[0] = setDate[2];
				setDate[2] = tmp;
			}
			
			if(apm == 12)
			{
				int hour = Integer.valueOf(setDate[3])+apm;
				if(hour >= 24)
					setDate[3] = "00";
				else
					setDate[3] = String.valueOf(hour);
			}
		} catch(Exception e)
		{
			return date;
		}
		if(setDate[0].length()!=4 || setDate[1].length()!=2 && setDate[2].length()!=2)
		{
			return date;
		}
		
		return setDate[0]+"/"+setDate[1]+"/"+setDate[2]+" "+setDate[3]+":"+setDate[4]+":"+setDate[5];
	}
}
