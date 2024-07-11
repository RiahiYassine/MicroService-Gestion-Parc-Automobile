package au.getstionparcautomobile.assuranceService.handler;


import java.util.Map;

public record ErrorResponse(Map<String, String> errors) { }