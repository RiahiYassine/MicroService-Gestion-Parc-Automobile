package au.gestionparcautomobile.vehiculeSpecificationService.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class AssuranceNotFoundException extends RuntimeException {
    private final String msg;
}