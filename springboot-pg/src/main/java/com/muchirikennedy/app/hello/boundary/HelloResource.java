package com.muchirikennedy.app.hello.boundary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.muchirikennedy.app.hello.control.Hello;
import com.muchirikennedy.app.hello.control.HelloControl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("hello")
public class HelloResource {

    @Autowired
    HelloControl helloControl;

    @Operation(summary = "Says Hello", description = "Simply reads from property file the greeting, message and quote")
    @ApiResponse(responseCode = "200", description = "The Greeting", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Hello.class)))
    @GetMapping
    public ResponseEntity<Hello> hello() {
        return ResponseEntity.ok(helloControl.hello());
    }
}
