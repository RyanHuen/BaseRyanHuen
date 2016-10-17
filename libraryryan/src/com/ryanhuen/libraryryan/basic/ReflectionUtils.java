
package com.ryanhuen.libraryryan.basic;

import java.lang.reflect.Field;
import java.lang.reflect.Method;


import android.util.Log;

import com.ryanhuen.libraryryan.config.LibConfig;

/**
 * Created by ryanhuen
 */
@SuppressWarnings({
        "rawtypes", "unchecked"
})
public class ReflectionUtils {
    public static final String TAG = ReflectionUtils.class.getName();

    /**
     * 得到某个对象的属性
     *
     * @param owner
     * @param fieldName
     * @return
     * @throws Exception
     */
    public static Object getProperty(Object owner, String fieldName) {
        Class ownerClass = owner.getClass();

        Field field = null;
        Object property = null;
        try {
            field = ownerClass.getField(fieldName);
            property = field.get(owner);
        } catch (Exception e) {
            if (LibConfig.getInstance().isBASE_DEBUG()) {
                Log.w(TAG, "error reflecting property", e);
            }
        }
        return property;
    }

    /**
     * 获取某个类的静态属性
     *
     * @param className
     * @param fieldName
     * @return
     * @throws Exception
     */
    public static Object getStaticProperty(String className, String fieldName) {
        Class ownerClass = null;
        Object property = null;
        try {
            ownerClass = Class.forName(className);
            Field field = ownerClass.getField(fieldName);
            property = field.get(ownerClass);
        } catch (Exception e) {
            if (LibConfig.getInstance().isBASE_DEBUG()) {
                Log.w(TAG, "error reflecting static property", e);
            }
        }
        return property;
    }

    /**
     * 执行某对象的方法
     *
     * @param owner
     * @param methodName
     * @param args
     * @return
     * @throws Exception
     */

    public static Object invokeMethod(Object owner, String methodName, Object[] args)
            throws Exception {

        Class ownerClass = owner.getClass();

        Class[] argsClass = new Class[args.length];

        for (int i = 0, j = args.length; i < j; i++) {
            argsClass[i] = args[i].getClass();
        }

        Method method = ownerClass.getMethod(methodName, argsClass);

        return method.invoke(owner, args);
    }

    /**
     * 执行某对象的静态方法
     *
     * @param className
     * @param methodName
     * @param args
     * @return
     * @throws Exception
     */
    public static Object invokeStaticMethod(String className, String methodName,
            Object[] args) throws Exception {
        Class ownerClass = Class.forName(className);
        Class[] argsClass = new Class[args.length];
        for (int i = 0, j = args.length; i < j; i++) {
            argsClass[i] = args[i].getClass();
        }
        Method method = ownerClass.getMethod(methodName, argsClass);
        return method.invoke(null, args);
    }

}
