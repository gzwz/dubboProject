package cn.qumiandan.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Sha1Util {
	
    /**
     * @param signature 签名        
     */
    public static String signature(String content){
        
        //启动sha1加密法的工具
        MessageDigest md=null;
        String tmpStr=null;
        try {
            md=MessageDigest.getInstance("SHA-1");
            //md.digest()方法必须作用于字节数组
            byte[] digest=md.digest(content.toString().getBytes());
            //将字节数组弄成字符串
            tmpStr=byteToStr(digest);
        } catch (NoSuchAlgorithmException e) {
            log.error(e.getMessage());
        }
       return tmpStr;
    }
    
    
    /**
     * 将字节加工然后转化成字符串
     * @param digest
     * @return
     */
    private static String byteToStr(byte[] digest){
        String strDigest="";
        for(int i=0;i<digest.length;i++){
            //将取得字符的二进制码转化为16进制码的的码数字符串
            strDigest+=byteToHexStr(digest[i]);
        }
        return strDigest;
    }
    
    /**
     * 把每个字节加工成一个16位的字符串
     * @param b
     * @return
     */
    public static String byteToHexStr(byte b){
        //转位数参照表
        char[] Digit= {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        
        
        //位操作把2进制转化为16进制
        char[] tempArr=new char[2];
        tempArr[0]=Digit[(b>>>4)&0X0F];//XXXX&1111那么得到的还是XXXX
        tempArr[1]=Digit[b&0X0F];//XXXX&1111那么得到的还是XXXX
        
        //得到进制码的字符串
        String s=new String(tempArr);
        return s;
    }
}
