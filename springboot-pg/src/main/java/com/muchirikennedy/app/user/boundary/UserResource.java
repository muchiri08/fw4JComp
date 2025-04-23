package com.muchirikennedy.app.user.boundary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.JsonNode;
import com.muchirikennedy.app.user.control.UserControl;
import com.muchirikennedy.app.user.control.UserManager;
import com.muchirikennedy.app.user.entity.User;

@RestController
@RequestMapping("/users")
public class UserResource {

    @Autowired
    UserManager userManager;
    @Autowired
    UserControl userControl;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity saveUser(@RequestBody JsonNode userJson, UriComponentsBuilder urilBuilder) {
        var user = userControl.mapToUser(userJson);
        var errors = userControl.validateUser(user);
        if (!errors.isEmpty()) {
            return ResponseEntity.badRequest().body(errors);
        }
        userManager.saveUser(user);
        var location = urilBuilder.path("/users/{id}")
                .buildAndExpand(user.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> findUser(@PathVariable("id") int id) {
        var user = userManager.findUser(id)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatusCode.valueOf(404)));

        return ResponseEntity.ok(user);
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteUser(@PathVariable("id") int id) {
        var success = userManager.deleteUser(id);
        if (success) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
