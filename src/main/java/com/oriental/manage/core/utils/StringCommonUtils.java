package com.oriental.manage.core.utils;

import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 字符串通用工具类
 * @author 黄军 2013-09-04
 * @author 蒯越 2014-12-09 优化
 */
public class StringCommonUtils extends StringUtils {
	
	/**
	 * 把数组组合成字符串，使用 splitChar 分割。
	 * @param value 需要组合的数组
	 * @return 组合后的字符串
	 */
	public static String assemble(String[] value, char splitChar) {
		if (value == null || value.length == 0) {
			return "";
		}
        StringBuilder temp = new StringBuilder();
        for (String s : value) {
            temp.append(s);
            temp.append(splitChar);
        }
		temp.deleteCharAt(temp.length() - 1);
		return temp.toString();
	}
    /**
     * 把List组合成字符串，使用 splitChar 分割。
     * @param value 需要组合的数组
     * @return 组合后的字符串
     */
    public static String assemble(List<String> value, char splitChar) {
        if (value == null || value.size() == 0) {
            return "";
        }
        String [] strings = new String[value.size()];
        return assemble(value.toArray(strings),splitChar);
    }

	/**
	 * 对字符串 - 在左边填充指定符号
	 * @param s 需要填充的字符串
	 * @param fullLength 填充长度
	 * @param addSymbol 填充字符
	 * @return 填充完后的字符串
	 */
	public static String addSymbolAtLeft(String s, int fullLength, char addSymbol) {
		if (s == null) {
			return null;
		}
		String result = s;
		int length = s.length();
        int distance = fullLength - length;

		if (distance > 0) {
			char[] newChars = new char[fullLength];
			for (int i = 0; i < length; i++) {
				newChars[i + distance] = s.charAt(i);
			}
			for (int j = 0; j < distance; j++) {
				newChars[j] = addSymbol;
			}
			result = new String(newChars);
		}
		return result;
	}
	/**
	 * 字符串分割
	 * @param str  字符串
	 * @param separatorChar 分隔符
	 * @return 分割后的字符串数组
	 */
	public static String [] splitExtends(String str,String separatorChar){
		if(str == null){
			return null;
		} else if("".equals(str)){
			return new String[]{""};
		} else {
			return StringCommonUtils.splitPreserveAllTokens(str, separatorChar);
		}
	}
	
	/**
	 * 过滤空格
	 * @param o 要过滤的对象
	 * @return 返回过滤后的结果
	 */
	public static String filter(Object o) {
		if (o == null){
			return "";
        }
		if ("null".equals(o)){
			return "";
        }
		return o.toString().trim();
	}

	public static String getRsrcOperation(String rsrcUrl){
		String rsrcOperation = "0";
		if(!"common.jsp".equals(rsrcUrl)){
			if(rsrcUrl.contains(".action")){
				rsrcOperation = rsrcUrl.substring(0, rsrcUrl.indexOf(".action")).replace("!", "_");
			}else{
				if(rsrcUrl.contains("!")){
					rsrcOperation = rsrcUrl.substring(0, rsrcUrl.indexOf('!'));
				}else{
					if(rsrcUrl.contains("?")){
						rsrcOperation = rsrcUrl.substring(0, rsrcUrl.indexOf('?'));
					} 
				}
			}
		}
		return rsrcOperation;
	}
	
	/**
	 * 判断是否符合用户名格式标准
	 * @param str 用户名
	 * @return 是否符合标准
	 * xiabin add 2013-04-08 14:31:1
	 */
	public static boolean isUserName(String str){
        String regex = "[^\\u4e00-\\u9fa5&&^\\w\\S]{6,29}$";
        return !StringUtils.isEmpty(str) && str.matches(regex);
    }
	
	/**
	 * 判断是否是数字
	 * @param num 数值字符串
	 * @param length 长度
	 * @return 是否是数字
	 */
	public static boolean isNumber(String num, int length){
		String regex ="[0-9]{0, " + length + "}";
        return !StringUtils.isEmpty(num) && num.matches(regex);
    }

	/**
	 * 在流水号等字符串前加制表符，以避免该串在csv中显示为科学计数法
	 * @param o 要转换的对象
	 * @return 返回转换后的结果
	 */
	public static String toTextFormat(String o) {
        return "\t" + filter(o);
	}
	
	/**
	 * set
	 * @param column 字符串
	 * @return 为null或"" 返回 "" 否则返回原字符串
	 */
	public static String setString(String column){
		if(StringUtils.isEmpty(column)){
			return "";
		}
		return column;
	}

    /**
     * @param str 传入科学计数法
     * @return 转换后的文本格式返回
     */
    public static String transCharByBigDecimal(String str){
        BigDecimal bd ;
        if(StringUtils.isEmpty(str)){
            return "";
        }else{
            bd = new BigDecimal(str);
        }
        return  bd.toPlainString();
    }
    
    /***
     * 字符串分割成数组
     * @param str 字符串
     * @param reg 分隔符
     * @return 字符串数组
     */
    public static String[] splits(String str,String reg){
        if(StringUtils.isEmpty(str)){
            return new String []{};
        }else if(!str.contains(reg)){
            return new String[]{str};
        }
		// 去除最后有个
		if (str.lastIndexOf(reg) > 0 && (str.length() - 1 == str.lastIndexOf(reg))) {
			str = str.substring(0, str.lastIndexOf(reg)); 
		}
		return  StringUtils.split(str, reg);
	 }
    
    /**
     * 字符串分割成List
     * @param str 需要分割的字符串
     * @param reg 分隔符
     * @return 分割后List
     */
    public static List<String> splitList(String str, String reg){
        String[] strs;
        if(StringUtils.isEmpty(str)){
            strs = new String[]{};
        }else if(!str.contains(reg)){
            strs = new String[]{str};
        }else{
            if (str.lastIndexOf(reg) > 0 && (str.length() - 1 == str.lastIndexOf(reg))) {
                // 去除最后有个 ","
                str = str.substring(0, str.lastIndexOf(reg));
            }
            strs = StringUtils.split(str, reg);
        }
        List<String> list = new ArrayList<String>();
        Collections.addAll(list, strs);
        return list;
    }
    
    /***
     * Object类型转换成String类型, 为null时返回""
     * @param obj 对象
     * @return 字符窜
     */
    public static String getString(Object obj){
    	return obj == null ? "" : obj.toString();
    }

    /**
     * Object类型转换成String类型
     * @param obj 需要转换的对象
     * @param obj1 若参数1对象为空,则返回对象2的字符串形式
     * @return 转换后的字符串
     */
    public static String getString(Object obj, Object obj1){
    	if(obj == null){
    		obj = obj1;
    	}
    	return obj == null ? "" : String.valueOf(obj);
    }
    
    /***
     * 构建下载功能时的HTML头部
     * @param list 表头集合
     * @param title html title
     * @return 表头html
     */
    public static String getHeadHtmlBegin(List<String> list, String title){
    	if(list == null || list.isEmpty()){
    		return "";
    	}
    	StringBuilder builder = new StringBuilder();
        builder.append("<html>");
        builder.append("<head>");
        builder.append("<meta http-equiv='Content-Type' content='text/html; charset=GB18030'>");
        builder.append("<title>").append(title).append("</title>");
        builder.append("<style>.tr1{word-wrap: break-word; word-break: break-all;");
        builder.append("height: 22px;line-height: 22px;");
        builder.append("text-align: center;font-weight: bold;background-color: #16A2C1;}");
        builder.append("table,tr,td, th{border:1px solid #c0c0c0;border-collapse: collapse;}</style>");
        builder.append("</head>");
        builder.append("<body>");
        builder.append("<table border='1'><tr>");
    	for(String str:list){
            builder.append("<td class='tr1'>").append(str).append("</td>");
    	}
        builder.append("</tr>");
    	return builder.toString();
    }

    /**
     * 构建下载功能时的HTML尾部
     * @return String
     */
    public static String getHtmlEnd(){
    	return "</table></body></html>";
    }

    /**
     * List转换String类型
     * @param list 需要转换的list
     * @param reg 分隔符
     * @return 转换后的字符串
     */
    public static String getString(List<String> list, String reg){
    	if(list == null || list.isEmpty()){
    		return "";
    	}
    	StringBuilder sb = new StringBuilder();
    	for(String str : list){
            sb.append(reg).append(str);
    	}
    	return sb.substring(1);
    }

    /**
     * 每次导出文件的时候保证文件名格式都一样
     * @param name 文件名
     * @param fileType 文件类型
     * @return 文件名 + 年月日时分秒 + 文件后缀名
     */
    public static String getExportFileName(String name, String fileType){
    	return name + "_" + DateUtils.getToDay(DateUtils.DATETIMESFORMAT) + "." + fileType;
    }
    
    /**
     * 获取异常信息
     * @param e 异常
     * @return 格式化后的异常信息
     */
    public static String getExceptionInfo(Exception e){
        return "className: " + e.getStackTrace()[0].getClassName() + " ; methodName: " + e.getStackTrace()[0].getMethodName();
    }

    /**
     * 手机验证码日志脱敏
     * @param str 字符串
     * @return 脱敏后字符串
     */
    public static String logDes(String str){
        return StringCommonUtils.stringDes(str, 1);
    }

    /**
     * 脱敏通用类
     * @param str 字符串
     * @param holdDigit 前后保留位数(不替换为*)
     * @return 脱敏后字符串
     */
    public static String stringDes(String str, int holdDigit){
        String returnStr;
        if(str == null){
            return "";
        }
        int len = str.length();
        if(len > holdDigit * 2){
            int len2 = len - holdDigit * 2;
            StringBuilder sb = new StringBuilder();
            sb.append(str.substring(0, holdDigit));
            for(int i = 0; i < len2; i++){
                sb.append("*");
            }
            sb.append(str.substring(len - holdDigit, len));
            returnStr = sb.toString();
        }else{
            returnStr = str;
        }
        return returnStr;
    }

	/**
	 * 字符串插入指定字符
	 * @param str 字符串
	 * @param con 指定的字符
	 * @param index 字符串的位置
	 * @param index1 字符串的位置
     * @return 插入后的字符串
     */
	public static 	String insertStr(String str,String con,int index,int index1){
		StringBuilder  sb = new StringBuilder (str);
		sb.insert(index,con);
		sb.insert(index1,con);
		return sb.toString();
	}

}
