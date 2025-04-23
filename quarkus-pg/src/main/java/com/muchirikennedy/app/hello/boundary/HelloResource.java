package com.muchirikennedy.app.hello.boundary;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import com.muchirikennedy.app.hello.control.Hello;
import com.muchirikennedy.app.hello.control.HelloControl;

@Path("hello")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
public class HelloResource {

    @Inject
    HelloControl helloControl;

    @Operation(summary = "Says Hello", description = "Simply reads from property file the greeting, message and quote")
    @APIResponse(responseCode = "200", description = "The Greeting", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Hello.class)))
    @GET
    public Response hello() {
        return Response.ok(helloControl.hello()).build();
    }
}
