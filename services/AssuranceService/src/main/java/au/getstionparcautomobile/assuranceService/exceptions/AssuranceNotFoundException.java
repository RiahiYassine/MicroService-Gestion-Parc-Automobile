package au.getstionparcautomobile.assuranceService.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper=true)
@Data
public class AssuranceNotFoundException extends RuntimeException { private final String msg;}