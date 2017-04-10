package com.oriental.manage.core.utils;

import org.apache.commons.lang3.ObjectUtils;

import java.text.DecimalFormat;

public class FormatUtil {
	//设置数值精度
	public static String toMoneyFormat(Double digit){
		DecimalFormat df = new DecimalFormat("0.00");
		return df.format(digit);
	}

	//非数值字段的转化
	public static String tranferObjectToString(Object o){
		return ObjectUtils.toString(o);
		//return o!=null?o.toString():"";
	}
		
	//将对象转换成数值字符串
	public static String tranferObjectToDoubleString(Object o){
		return o!=null? FormatUtil.tranferDoubleNew(Double.parseDouble(o.toString())):"0.00";
	}
	
	//将对象转化为Double类型
	public static Double tranferObjectToDouble(Object o){
		return o!=null?Double.parseDouble(o.toString()):0.00;
	}
	
	//字符串转double
	public static Double tranferStringToDouble(String s){
		return s.equals("")?0.00:Double.parseDouble(s);
	}
    public static Double switchDouble(Object obj){
        if(obj==null){
            return 0.00;
        }
        return BigDecimalUtils.round(obj.toString(), 2);
    }


    private static final DecimalFormat  fmt   = new DecimalFormat("##,###,###,###,##0.00");
    //将Double对象转化为String类型,传入：1000000.11,输入效果为：1,000,000.11
    public static String tranferDoubleNew(Object o){
        Double d =  o!=null?Double.parseDouble(o.toString()):0.00;
        return fmt.format(d);
    }
    //将Object对象转化String类型，金额以万元为单位，保留四位有效数字
    public static String tranferStringNew(Object o){
        Double d =  o!=null?Double.parseDouble(o.toString()):0.0000;
        double dd = Math.round(d)/10000.0;
        DecimalFormat df = new DecimalFormat("0.0000");
        return df.format(dd);
    }
    public  static void main(String args[]){
        Object j=null;
        System.out.print(tranferStringNew(j));
    }


}
