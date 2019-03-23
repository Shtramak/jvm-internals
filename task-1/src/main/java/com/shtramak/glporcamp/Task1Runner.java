package com.shtramak.glporcamp;

import com.shtramak.glporcamp.classloader.CustomClassLoader;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@SuppressWarnings("ALL")
public class Task1Runner {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException,
            IllegalAccessException, InvocationTargetException, InstantiationException {

        String className = "com.shtramak.glporcamp.service.TextService";
        while (true) {
            CustomClassLoader loader = new CustomClassLoader();
            Class<?> clazz = null;
            try {
                clazz = loader.loadClass(className);
            } catch (Exception e) {
                sleep(500);
                loader = new CustomClassLoader();
                clazz = loader.loadClass(className);
            }
//            Object instance = clazz.newInstance();
            Constructor<?> constructor = clazz.getConstructor();
            Object instance = constructor.newInstance();
            Method method = instance.getClass().getMethod("staticText");
            Object message = method.invoke(instance);
            sleep(1500);
            System.out.println(message);
        }
    }

    private static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
