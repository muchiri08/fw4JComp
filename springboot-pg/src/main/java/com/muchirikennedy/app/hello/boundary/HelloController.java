package com.muchirikennedy.app.hello.boundary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.muchirikennedy.app.hello.control.Hello;
import com.muchirikennedy.app.hello.control.HelloControl;

@RestController
@RequestMapping("hello")
public class HelloController {

    @Autowired
    HelloControl helloControl;

    @GetMapping
    public ResponseEntity<Hello> hello() {
        return ResponseEntity.ok(helloControl.hello());
    }
}
