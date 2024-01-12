package vn.com.dsk.authservice.base.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class EntityNotFoundException extends RuntimeException {

    private String entityName;

    private String entityId;

    public EntityNotFoundException() {
        super("error.api.entity-not-found", null);
    }

    public EntityNotFoundException(String entityName, String entityId) {
        super("error.api.entity-not-found", null);
        this.entityId = entityId;
        this.entityName = entityName;
    }

}
