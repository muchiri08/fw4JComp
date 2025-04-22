package com.muchirikennedy.app.user.boundary;

import com.muchirikennedy.app.user.control.UserControl;
import com.muchirikennedy.app.user.control.UserManager;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.JsonObject;
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
import jakarta.ws.rs.core.Response.Status;

@Path("users")
@ApplicationScoped
public class UserResource {

    @Inject
    UserManager userManager;
    @Inject
    UserControl userControl;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveUser(JsonObject userJson, @Context UriInfo uriInfo) {
        var user = userControl.mapToUser(userJson);
        var errors = userControl.validateUser(user);
        if (!errors.isEmpty()) {
            return Response.status(Status.BAD_REQUEST).entity(errors).build();
        }
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
