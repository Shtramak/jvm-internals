package com.shtramak.glporcamp.service;

public class TextServiceImpl implements TextService {
    public String staticText() {
        return "Hello!";
    }

    @Override
    public String variable(String variable) {
        return variable;
    }
}
