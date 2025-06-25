package io.molenaar.config;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path.Node;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.List;
import java.util.Map;

@Provider
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException exception) {
        List<Map<String, String>> errors = exception
                .getConstraintViolations()
                .stream()
                .map(cv -> Map.of("field", extractFieldName(cv), "message", cv.getMessage()))
                .toList();

        return Response.status(Response.Status.BAD_REQUEST).entity(Map.of("error", "Validation failed", "violations", errors)).build();
    }

    private String extractFieldName(ConstraintViolation<?> cv) {
        String name = "";
        for (Node node : cv.getPropertyPath()) {
            name = node.getName();
        }
        return name;
    }
}