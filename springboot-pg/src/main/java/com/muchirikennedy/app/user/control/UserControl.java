package com.muchirikennedy.app.user.control;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.JsonNode;
import com.muchirikennedy.app.user.entity.User;

import jakarta.validation.Validator;

@Component
public class UserControl {
    @Autowired
    private Validator validator;

    public User mapToUser(JsonNode json) {
        var name = json.has("name") ? json.get("name").asText() : null;
        var occupation = json.has("occupation") ? json.get("occupation").asText() : null;
        return new User(name, occupation);
    }

    public Map<String, String> validateUser(User user) {
        var constraintViolations = this.validator.validate(user);
        var errors = new HashMap<String, String>(constraintViolations.size());
        for (var violation : constraintViolations) {
            errors.put(violation.getPropertyPath().toString(), violation.getMessage());
        }
        return errors;
    }
}
