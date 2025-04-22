package com.muchirikennedy.app.user.control;

import java.util.HashMap;
import java.util.Map;
import com.muchirikennedy.app.user.entity.User;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.JsonObject;
import jakarta.validation.Validator;

@ApplicationScoped
public class UserControl {
    @Inject
    private Validator validator;

    public User mapToUser(JsonObject json) {
        var name = json.getString("name", null);
        var occupation = json.getString("occupation", null);
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
