package com.muchirikennedy.app.user.boundary;

import com.muchirikennedy.app.user.control.UserManager;
import com.muchirikennedy.app.user.entity.User;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

@Path("users")
@ApplicationScoped
public class UserResource {

    @Inject
    UserManager userManager;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response saveUser(User user, @Context UriInfo uriInfo) {
        userManager.saveUser(user);
        var location = uriInfo.getAbsolutePathBuilder()
                .path("{id}")
                .resolveTemplate("id", user.getId())
                .build();
        return Response.created(location).build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findUser(@PathParam("id") int id) {
        var user = userManager.findUser(id)
                .orElseThrow(
                        () -> new WebApplicationException(404));

        return Response.ok(user).build();
    }
}
