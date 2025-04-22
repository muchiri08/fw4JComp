package com.muchirikennedy.app.hello.control;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperties;

@ApplicationScoped
public class HelloControl {
    @Inject
    @ConfigProperties
    private Hello hello;

    public Hello hello() {
        return this.hello;
    }
}
