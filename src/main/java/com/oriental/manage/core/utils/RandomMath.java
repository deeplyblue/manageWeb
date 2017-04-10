package com.oriental.manage.core.utils;
import java.util.Random;


public class RandomMath {

    /**
     * get random math
     * @param len
     * @return
     */
	public static String getMath(int len)
	{
		StringBuffer buffer=new StringBuffer("");
		Random rd=new Random();
		//24+10
		 char[] str = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
				     'm', 'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
				    'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
        for(int i=0;i<len;i++)
        {
        	int value=rd.nextInt(34);
        	char ch=str[value];
        	buffer.append(String.valueOf(ch));
        }
        return buffer.toString();
	}
    /**
     * get random num
     * @param len
     * @return
     */
    public static String getNum(int len)
    {
        StringBuffer buffer=new StringBuffer("");
        Random rd=new Random();
        //24+10
        char[] str = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
        for(int i=0;i<len;i++)
        {
            int value=rd.nextInt(10);
            char ch=str[value];
            buffer.append(String.valueOf(ch));
        }
        return buffer.toString();
    }
    public static void main(String[] args)throws Exception {

        System.out.println( getNum(3) );
    }


}
