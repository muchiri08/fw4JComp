package com.muchirikennedy.app.user.boundary;

import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.media.SchemaProperty;

import com.muchirikennedy.app.user.control.UserControl;
import com.muchirikennedy.app.user.control.UserManager;
import com.muchirikennedy.app.user.entity.User;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.JsonObject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
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

    @Operation(summary = "Create new user", description = "create a new user with the given data")
    @APIResponse(responseCode = "201", description = "Success")
    @APIResponse(responseCode = "400", description = "Invalid user inputs", content = @Content(mediaType = "application/json"))
    @RequestBody(content = {
            @Content(mediaType = "application/json", schema = @Schema(properties = {
                    @SchemaProperty(name = "name", nullable = false, defaultValue = "John Doe"),
                    @SchemaProperty(name = "occupation", nullable = false, defaultValue = "Software Developer") }))
    })
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

    @APIResponse(
        responseCode = "200", 
        description = "Success", 
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))
    )
    @APIResponse(responseCode = "404", description = "User Not Found")
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findUser(@PathParam("id") int id) {
        var user = userManager.findUser(id)
                .orElseThrow(
                        () -> new WebApplicationException(404));

        return Response.ok(user).build();
    }

    @APIResponse(responseCode = "204", description = "Success")
    @APIResponse(responseCode = "404", description = "User Not Found")
    @DELETE
    @Path("{id}")
    public Response deleteUser(@PathParam("id") int id) {
        var success = userManager.deleteUser(id);
        if (success) {
            return Response.noContent().build();
        } else {
            return Response.status(Status.NOT_FOUND).build();
        }
    }
}
