package com.muchirikennedy.app.hello.control;

import org.eclipse.microprofile.config.inject.ConfigProperties;

import jakarta.enterprise.context.ApplicationScoped;

@ConfigProperties(prefix = "hello")
@ApplicationScoped
public class Hello {
    public String greeting;
    public String message;
    public String quote;
}
