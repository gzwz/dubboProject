package cn.qumiandan.utils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;

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

    /**
     * map 转 object
     * @param map
     * @param beanClass
     * @return
     * @throws Exception
     */
    public static Object mapToObject(Map<String, Object> map, Class<?> beanClass) throws Exception {
        if (map == null)
            return null;

        Object obj = beanClass.newInstance();

        BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor property : propertyDescriptors) {
            Method setter = property.getWriteMethod();
            if (setter != null) {
                setter.invoke(obj, map.get(property.getName()));
            }
        }
        return obj;
    }
}
