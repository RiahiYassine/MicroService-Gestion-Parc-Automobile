package au.gestionparcautomobile.vehiculeSpecificationService.handler;


import java.util.Map;

public record ErrorResponse(Map<String, String> errors) { }