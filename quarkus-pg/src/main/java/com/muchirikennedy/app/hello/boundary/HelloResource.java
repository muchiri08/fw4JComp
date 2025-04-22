package com.muchirikennedy.app.hello.boundary;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import com.muchirikennedy.app.hello.control.HelloControl;

@Path("hello")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
public class HelloResource {

    @Inject
    HelloControl helloControl;

    @GET
    public Response hello() {
        return Response.ok(helloControl.hello()).build();
    }
}
