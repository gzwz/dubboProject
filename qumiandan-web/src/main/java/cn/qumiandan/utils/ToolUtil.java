package cn.qumiandan.utils;

/**
 * @description：工具类
 * @author：zhuyangyong
 * @date: 2018/11/13 14:00
 */
public class ToolUtil {
    /**
     * byte转int
     * @param byteNum
     * @return
     */
    public static int byteToInt(byte byteNum) {
        return byteNum > 0 ? byteNum : (128 + (128 + byteNum));
    }

    /**
     * int转byte
     * @param num
     * @return
     */
    public static byte intToByte(int num) {
        return (byte) (num & 0x000000ff);
    }
}
