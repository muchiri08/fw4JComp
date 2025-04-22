package com.muchirikennedy.app.user.boundary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import com.muchirikennedy.app.user.control.UserManager;
import com.muchirikennedy.app.user.entity.User;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserManager userManager;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveUser(@RequestBody User user, UriComponentsBuilder urilBuilder) {
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
}
