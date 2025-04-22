package com.muchirikennedy.app.hello.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HelloControl {
    @Autowired
    private Hello hello;

    public Hello hello() {
        return this.hello;
    }
}
