package com.greenbee.se.start;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectionLib {

    public static Class<?> loadClass(ClassLoader cl, String name) throws ClassNotFoundException {
        ClassLoader ccl = Thread.currentThread().getContextClassLoader();
        if (ccl == cl)
            cl = null;//avoid unnecessary duplicate load
        try {
            return Class.forName(name, true, ccl);
        } catch (ClassNotFoundException cne) {
            if (cl != null)
                return Class.forName(name, true, cl);
            throw cne;
        }
    }

    public static Object createInstance(String clazzName) throws Exception {
        return createInstance(null, clazzName, new Class<?>[0], new Object[0]);
    }

    public static Object createInstance(ClassLoader cl, String clazzName) throws Exception {
        return createInstance(cl, clazzName, new Class<?>[0], new Object[0]);
    }

    public static Object createInstance(ClassLoader cl, String clazzName, Class<?>[] types,
            Object[] params) throws Exception {
        Class<?> clazz = loadClass(cl, clazzName);
        Constructor<?> cst = clazz.getConstructor(types);
        try {
            return cst.newInstance(params);
        } catch (Exception e) {
            throw wrap(e, cst.getExceptionTypes());
        }
    }

    public static Object invokeStatic(ClassLoader cl, String className, String methodName)
            throws Exception {
        return invokeStatic(cl, className, methodName, new Class<?>[0], new Object[0]);
    }

    public static Object invokeStatic(ClassLoader cl, String className, String methodName,
            Class<?>[] types, Object[] params) throws Exception {
        Class<?> clazz = loadClass(cl, className);
        Method method = clazz.getMethod(methodName, types);
        try {
            return method.invoke(null, params);
        } catch (Exception e) {
            throw wrap(e, method.getExceptionTypes());
        }
    }

    public static void setBeanProperty(Object bean, String name, Object value) throws Exception {
        Class<?> beanClazz = bean.getClass();
        String methodName = "set" + name;
        Method method;
        Class<? extends Object> argClazz = value.getClass();
        try {
            Class<?>[] types = new Class<?>[] { argClazz };
            method = beanClazz.getMethod(methodName, types);
        } catch (NoSuchMethodException nme) {
            Class<?> pmvClazz = getPrimitiveClass(argClazz);
            if (pmvClazz != null) {
                method = beanClazz.getMethod(methodName, new Class<?>[] { pmvClazz });
            } else {
                throw nme;
            }
        }
        try {
            method.invoke(bean, value);
        } catch (Exception e) {
            throw wrap(e, method.getExceptionTypes());
        }
    }

    protected static Class<?> getPrimitiveClass(Class<?> argClazz) {
        Class<?> pmvClazz;
        if (Boolean.class == argClazz) {
            pmvClazz = Boolean.TYPE;
        } else if (Character.class == argClazz) {
            pmvClazz = Character.TYPE;
        } else if (Byte.class == argClazz) {
            pmvClazz = Byte.TYPE;
        } else if (Short.class == argClazz) {
            pmvClazz = Short.TYPE;
        } else if (Integer.class == argClazz) {
            pmvClazz = Integer.TYPE;
        } else if (Long.class == argClazz) {
            pmvClazz = Long.TYPE;
        } else if (Float.class == argClazz) {
            pmvClazz = Float.TYPE;
        } else if (Double.class == argClazz) {
            pmvClazz = Double.TYPE;
        } else {
            pmvClazz = null;
        }
        return pmvClazz;
    }

    //get real exception and wrap exception into runtime exception if it is not declared
    protected static Exception wrap(Exception ex, Class<?>[] exceptions) {
        Throwable cause = ex;
        if (ex instanceof InvocationTargetException) {//get target exception
            cause = ((InvocationTargetException) ex).getTargetException();
        }
        for (Class<?> exception : exceptions) {
            if (exception.isAssignableFrom(cause.getClass()))
                return ex;
        }
        if (ex instanceof RuntimeException)
            return ex;
        return new RuntimeException(ex);
    }

}
