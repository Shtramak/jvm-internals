package com.shtramak.glporcamp.classloader;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CustomClassLoader extends ClassLoader {

    public CustomClassLoader() {
        super(null);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] bytes = bytesFromClassFile(name);
        return defineClass(name, bytes, 0, bytes.length);
    }

    private byte[] bytesFromClassFile(String className) {
        Path path = pathFromClassName(className);
        try {
            return Files.readAllBytes(path);
        } catch (IOException e) {
            throw new RuntimeException("an I/O error occurs reading from the stream", e);
        }
    }

    private Path pathFromClassName(String className) {
        String resourseName = className.replace(".", "/") + ".class";
        URL url = getClass().getClassLoader().getResource(resourseName);
        if (url == null) {
                String message = String.format("Class %s not found", className);
                throw new RuntimeException(message);
        }

        try {
            return Paths.get(url.toURI());
        } catch (
                URISyntaxException e) {
            String message = String.format("URL %s is not formatted strictly according to to RFC2396" +
                    " and cannot be converted to a URI", url);
            throw new RuntimeException(message, e);
        }

    }
}
