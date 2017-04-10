package com.oriental.manage.core.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Created by lupf on 2016/4/25.
 */
@Slf4j
public class BigDecimalUtils {
    /**
     * 格式化金额格式
     */
    private static final DecimalFormat fmt = new DecimalFormat("##,###,###,###,##0.00");

    /**
     * 加法运算
     *
     * @param v1 被加数
     * @param v2 加数
     * @return 两个参数的和
     */
    public static String add(String v1, String v2) {
        if (StringUtils.isEmpty(v1)) {
            v1 = "0";
        }
        if (StringUtils.isEmpty(v2)) {
            v2 = "0";
        }
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.add(b2).toString();
    }

    /**
     * 加法运算
     *
     * @param v1    被加数
     * @param v2    加数
     * @param scale 精确的小数位
     * @return 两个参数的和
     */
    public static String add(String v1, String v2, int scale) {
        if (StringUtils.isEmpty(v1)) {
            v1 = "0";
        }
        if (StringUtils.isEmpty(v2)) {
            v2 = "0";
        }
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        b1 = b1.add(b2);
        return String.valueOf(round(b1.doubleValue(), scale));
    }

    /**
     * 浮点数 加 小数位四舍五入精确计算 a+b
     *
     * @param a     被加数
     * @param b     加数
     * @param scale 精确的小数位
     * @return 两个参数的和
     */
    public static double preciseAdd(Double a, Double b, int scale) {
        if (null == b) {
            b = 0D;
        }
        if (null == a) {
            a = 0D;
        }
        BigDecimal r = new BigDecimal(Double.toString(a));
        r = r.add(new BigDecimal(Double.toString(b)));
        return round(r.doubleValue(), scale);
    }

    /**
     * 减法运算
     *
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差
     */
    public static String substract(String v1, String v2) {
        if (StringUtils.isEmpty(v1)) {
            v1 = "0";
        }
        if (StringUtils.isEmpty(v2)) {
            v2 = "0";
        }
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.subtract(b2).toString();
    }

    /**
     * 浮点数 减 小数位四舍五入精确计算 a-b
     *
     * @param a     被减数
     * @param b     减数
     * @param scale 精确的小数位
     * @return 两个参数的差
     */
    public static double preciseSub(Double a, Double b, int scale) {
        if (null == b) {
            b = 0D;
        }
        if (null == a) {
            a = 0D;
        }
        BigDecimal r = new BigDecimal(Double.toString(a));
        r = r.subtract(new BigDecimal(Double.toString(b)));
        return round(r.doubleValue(), scale);
    }

    public static String preciseSub(String a, String b, int scale) {
        if (StringUtils.isBlank(a)) {
            a = "0";
        }
        if (StringUtils.isBlank(b)) {
            b = "0";
        }
        BigDecimal r = new BigDecimal(a);
        r = r.subtract(new BigDecimal(b));
        return r.setScale(0).toString();
    }

    /**
     * 浮点数 乘 精确计算 a*b
     *
     * @param a 乘数1
     * @param b 乘数2
     * @return 两个参数乘积
     */
    public static double preciseMul(Double a, Double b) {
        if (null == b || null == a) {
            return 0D;
        }
        BigDecimal r = new BigDecimal(Double.toString(a));
        r = r.multiply(new BigDecimal(Double.toString(b)));
        return r.doubleValue();
    }

    /**
     * 浮点数 乘 精确计算 a*b
     *
     * @param a 乘数1
     * @param b 乘数2
     * @return 两个参数乘积
     */
    public static String preciseMul(String a, String b) {
        if (StringUtils.isEmpty(a)) {
            a = "0";
        }
        if (StringUtils.isEmpty(b)) {
            b = "0";
        }
        BigDecimal r = new BigDecimal(a);
        r = r.multiply(new BigDecimal(b));
        return r.toString();
    }

    public static String preciseMul(String a, String b, int num) {
        if (StringUtils.isEmpty(a)) {
            a = "0";
        }
        if (StringUtils.isEmpty(b)) {
            b = "0";
        }
        BigDecimal r = new BigDecimal(a);
        r = r.multiply(new BigDecimal(b));
        return r.setScale(num).toString();
    }

    /**
     * 浮点数 乘 小数位四舍五入精确计算 a*b
     *
     * @param a     乘数1
     * @param b     乘数2
     * @param scale 精确的小数位
     * @return 两个参数乘积
     */
    public static double preciseMul(Double a, Double b, int scale) {
        if (null == b || null == a) {
            return 0D;
        }
        return round(new BigDecimal(Double.toString(a)).multiply(
                new BigDecimal(Double.toString(b))).doubleValue(), scale);
    }

    /**
     * 字符串 乘 小数位四舍五入精确计算 a*b
     *
     * @param a     乘数1
     * @param b     乘数2
     * @param scale 精确的小数位
     * @return 两个参数乘积
     */
    public static String preciseMul(String a, Double b, int scale) {
        if (null == b || null == a || Double.doubleToRawLongBits(b) == 0) {
            return "0";
        }
        double d = round(new BigDecimal(a).multiply(
                new BigDecimal(Double.toString(b))).doubleValue(), scale);
        return Double.toString(d);
    }

    /**
     * 浮点数 除 精确计算 a/b
     *
     * @param a 被除数
     * @param b 除数
     * @return a/b
     */
    public static double preciseDev(Double a, Double b) {
        if (null == b || Double.doubleToRawLongBits(b) == 0 || null == a) {
            return 0D;
        }
        BigDecimal r = new BigDecimal(Double.toString(a));
        r = r.divide(new BigDecimal(Double.toString(b)), 6, BigDecimal.ROUND_HALF_UP);
        return r.doubleValue();
    }

    /**
     * 浮点数 除 小数位四舍五入精确计算 a/b
     *
     * @param a     被除数
     * @param b     除数
     * @param scale 精确的小数位
     * @return a/b
     */
    public static String preciseDev(Double a, Double b, int scale) {
        if (null == b || Double.doubleToRawLongBits(b) == 0 || null == a) {
            return "0";
        }
        BigDecimal r = new BigDecimal(Double.toString(a));
        r = r.divide(new BigDecimal(Double.toString(b)), scale, BigDecimal.ROUND_HALF_UP).setScale(scale);
        return r.toPlainString();
    }

    /**
     * 浮点数 除 小数位四舍五入精确计算 a/b
     *
     * @param a     被除数
     * @param b     除数
     * @param scale 精确的小数位
     * @return 计算结果
     */
    public static String preciseDev(String a, Double b, int scale) {
        if (null == b || Double.doubleToRawLongBits(b) == 0 || null == a) {
            return "0";
        }
        BigDecimal r = new BigDecimal(a);
        r = r.divide(new BigDecimal(Double.toString(b)), scale, BigDecimal.ROUND_HALF_UP).setScale(scale);
        return r.toPlainString();
    }

    /**
     * 比较两个值的大小
     *
     * @param num1 第一个数
     * @param num2 第二个数
     * @return -1 小于  num1 < num2
     * 0  相等  num1 = num2
     * 1  大于  num1 > num2
     */
    public static int isCompareTo(String num1, String num2) {
        if (StringUtils.isBlank(num1)) {
            num1 = "0";
        }
        if (StringUtils.isBlank(num2)) {
            num2 = "0";
        }
        BigDecimal b1 = new BigDecimal(num1);
        BigDecimal b2 = new BigDecimal(num2);
        return b1.compareTo(b2);
    }

    /**
     * 提供精确的小数位四舍五入处理。
     *
     * @param v     需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static double round(String v, int scale) {
        if (null == v) {
            v = "0";
        }
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(v);
        BigDecimal one = BigDecimal.ONE;
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 提供精确的小数位四舍五入处理。
     *
     * @param v     需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果 String类型
     */
    public static String roundToString(String v, int scale) {
        if (null == v) {
            v = "0";
        }
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(v);
        BigDecimal one = BigDecimal.ONE;
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).toPlainString();
    }

    /**
     * 提供精确的小数位四舍五入处理。
     *
     * @param v     需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static double round(Double v, int scale) {
        if (null == v) {
            v = 0D;
        }
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(Double.toString(v));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 将数字格式化，如：传入：1000000.11,输入效果为：1,000,000.11
     *
     * @param moeny 金额
     * @param b     除数
     * @return 格式化后的金额
     */
    public static String formattingMoeny(String moeny, Double b) {
        long moenyLong = Long.parseLong(moeny);
        return fmt.format(moenyLong / b);
    }

    /***
     * 将数字格式化，如：传入：1000000.11,输入效果为：1,000,000.11
     *
     * @param moeny 金额
     * @param b     除数
     * @return 格式化后的金额
     */
    public static String formattingMoeny(Double moeny, Double b) {
        return fmt.format(moeny / b);
    }

    /**
     * 将数字格式化，如：传入：1000000.11,输入效果为：1,000,000.11
     *
     * @param moeny 金额
     * @return 格式化后的金额
     */
    public static String formattingMoeny(Double moeny) {
        return fmt.format(moeny);
    }

    /**
     * 将数字格式化，如：传入：1000000.11,输入效果为：1,000,000.11
     *
     * @param moeny 金额
     * @return 格式化后的金额
     */
    public static String formattingMoeny(String moeny) {
        Double formattingMoeny = Double.parseDouble(moeny);
        return fmt.format(formattingMoeny);
    }

    /**
     * 使用参数及运算符号进行计算
     * 除法运算时默认四舍五入保留2位
     * 此方法只支持顺序执行, 即乘除法必须写在加减之前, 否则也不会根据运算符优先级, 优先计算
     * 例: calculate("1", "+", "2", "-", "3")
     *  或 calculate("1", "*", "2", "/", "3")
     *  或 calculate("1", "*", "2", "/", "3", "+", "4", "-", "5")
     * 错误符号传递: calculate("1", "+", "2", "*", "3")
     * @param elements 需要计算的数字与运算符
     * @return 计算后的结果
     * @throws Exception 没有传递参数, 有参数为空, 符号传递有误, 乘除出现在加减之后 会抛出异常
     */
    /*public static String calculate(String... elements) throws Exception {
        return BigDecimalUtils.calculate(2, elements);
    }*/

    /**
     * 使用参数及运算符号进行计算, 可指定除法精度
     * 除法运算时默认四舍五入
     * 此方法只支持顺序执行, 即乘除法必须写在加减之前, 否则也不会根据运算符优先级, 优先计算
     * 例: calculate(4, "1", "+", "2", "-", "3")
     *  或 calculate(4, "1", "*", "2", "/", "3")
     *  或 calculate(4, "1", "*", "2", "/", "3", "+", "4", "-", "5")
     * 错误符号传递: calculate("1", "+", "2", "*", "3")
     * @param scale 除法保留精度
     * @param elements 需要计算的数字与运算符
     * @return 计算后的结果
     * @throws Exception 没有传递参数, 有参数为空, 符号传递有误, 乘除出现在加减之后 会抛出异常
     */
    /*public static String calculate(int scale, String... elements) throws Exception {
        BigDecimal prevNum = null;
        String operator = null;
        int length = elements.length;
        for(int i = 0; i < length; i++){
            String element = elements[i];
            if(element == null){
                throw new NullArgumentException("第 " + i + " 个参数");
            }
            if(i % 2 == 0){
                BigDecimal currentNum = new BigDecimal(element);
                if(prevNum != null && operator != null){
                    char[] chars = operator.toCharArray();
                    if(chars.length != 1){
                        throw new Exception("未知符号: " + operator);
                    }
                    switch (chars[0]){
                        case '+':
                            prevNum = prevNum.add(currentNum);
                            break;
                        case '-':
                            prevNum = prevNum.subtract(currentNum);
                            break;
                        case '*':
                            prevNum = prevNum.multiply(currentNum);
                            break;
                        case '/':
                            prevNum = prevNum.divide(currentNum, scale, BigDecimal.ROUND_HALF_UP);
                            break;
                        default:
                            throw new Exception("未知符号: " + operator);
                    }
                }else{
                    prevNum = currentNum;
                }
            }else{
                // 若乘除出现在加减之后会抛出异常
                if(("*".equals(element) || "/".equals(element)) && ("+".equals(operator) || "-".equals(operator))){
                    throw new Exception("乘除运算不可出现在加减之后!");
                }
                operator = element;
            }
        }
        if(prevNum == null){
            throw new Exception("必须传递参数!");
        }
        return prevNum.toPlainString();
    }*/

    /**
     * 将元为单位的转换为分 (乘100)
     *
     * @param amount
     * @return
     */
    public static String changeY2F(String amount) throws Exception {

        if (StringUtils.isBlank(amount)) {
            return "";
        }
        String temp = amount.trim();
        int postion = -100;
        postion = temp.indexOf(".");
        if (postion != 0) {
            BigDecimal inhead = new BigDecimal(0);
            if (postion != -1) {
                BigDecimal head = new BigDecimal(temp.substring(0, postion));
                String foots = temp.substring(postion + 1, temp.length());
                if (foots.length() >= 3) {
                    inhead = new BigDecimal(foots.substring(0, 2));
                    temp = (head.multiply(new BigDecimal(100)).add(inhead))
                            .toString();
                    return temp;
                } else if (foots.length() == 2) {
                    temp = (head.multiply(new BigDecimal(100))
                            .add(new BigDecimal(foots))).toString();
                    return temp;
                } else {
                    temp = (head.multiply(new BigDecimal(100))
                            .add(new BigDecimal(foots).multiply(new BigDecimal(
                                    10)))).toString();
                    return temp;
                }
            } else {
                BigDecimal head = new BigDecimal(temp);
                temp = head.multiply(new BigDecimal(100)).toString();
                return temp;
            }
        } else {
            BigDecimal head = new BigDecimal(temp);
            temp = head.multiply(new BigDecimal(100)).toString();
            return temp;
        }
    }

    /**
     * 将分为单位的转换为元 (除100)
     *
     * @param amount
     * @return
     */
    public static String changeF2Y(String amount) {
        try {
            amount = amount.trim();
            String temp = new BigDecimal(amount).divide(new BigDecimal(100)).toString();

            if (temp.indexOf(".") != -1) {
                if (StringUtils.split(temp, ".")[1].length() == 1) {
                    temp += "0";
                }
            } else {
                temp += ".00";
            }
            return temp;
        } catch (Exception e) {
            log.error("金额:{},分转换成元异常:{}", amount, e);
        }
        return null;
    }

    public static BigDecimal changeF2Y(BigDecimal decimal) {
        return new BigDecimal(changeF2Y(decimal.toString()));
    }
}
