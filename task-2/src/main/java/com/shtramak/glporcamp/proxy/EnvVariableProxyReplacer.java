package com.shtramak.glporcamp.proxy;

import com.shtramak.glporcamp.service.TextService;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Properties;
import java.util.Set;

public class EnvVariableProxyReplacer implements InvocationHandler {
    private TextService textService;

    public EnvVariableProxyReplacer(TextService textService) {
        this.textService = textService;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getName().equals("variable")) {
            Properties allProperties = System.getProperties();
            Set<String> propertyNames = allProperties.stringPropertyNames();
            String argVariable = (String) args[0];
            String propertyName = propertyNameFromArg(argVariable);
            if (propertyNames.contains(propertyName)) {
                String key = argVariable.split("\\$")[0].trim();
                String value = allProperties.getProperty(propertyName);
                return method.invoke(textService,key+value);
            }
        }
        return null;
    }

    private String propertyNameFromArg(String arg) {
        String variable = arg.split("=")[1].trim();
        int[] codePoints = variable.chars()
                .filter(Character::isLetter)
                .toArray();
        return new String(codePoints,0,codePoints.length);
    }


}
