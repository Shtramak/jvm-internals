package com.shtramak.glporcamp;

import com.shtramak.glporcamp.proxy.EnvVariableProxyReplacer;
import com.shtramak.glporcamp.service.TextService;
import com.shtramak.glporcamp.service.TextServiceImpl;

import java.lang.reflect.Proxy;

public class Task2Runner {
    public static void main(String[] args) {
        TextService textService = new TextServiceImpl();
        TextService proxyService = textServiceProxy(textService);
        String result = proxyService.variable("server.port=${port}");
        System.out.println(result);
    }

    private static TextService textServiceProxy(TextService textService) {
        ClassLoader classLoader = textService.getClass().getClassLoader();
        Class<?>[] interfaces = textService.getClass().getInterfaces();
        EnvVariableProxyReplacer proxyReplacer = new EnvVariableProxyReplacer(textService);
        return (TextService) Proxy.newProxyInstance(classLoader, interfaces, proxyReplacer);
    }
}
