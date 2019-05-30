package com.fonsview.soapserver.util;

import javassist.Modifier;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectUtil {

    /**
     * 用于重写JAVA BEAN的to String方法
     * @param javaBean
     * @return
     * @throws Exception
     */
    public static String toString(Object javaBean) throws Exception{

        StringBuilder sb = new StringBuilder();
        int i = 0;
        for (Class<?> cls = javaBean.getClass(); cls != Object.class; cls = cls.getSuperclass()) {
            Field[] fields = cls.getDeclaredFields();
            if (null == fields || fields.length == 0) {
                continue;
            }
            for (Field f : fields) {
                if(i!=0) {
                    sb.append(",");
                }
                if(Modifier.isStatic(f.getModifiers())) {
                    continue; //排除static
                }
                if(Modifier.isFinal(f.getModifiers())) {
                    continue; //排除final
                }

                PropertyDescriptor descriptor = new PropertyDescriptor(f.getName(), cls);
                Method rMethod = descriptor.getReadMethod();
                Object value = rMethod.invoke(javaBean);

                sb.append(f.getName()).append(":<").append(value).append(">");
                i++;
            }
        }
        return sb.toString();
    }
}
