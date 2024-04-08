package org.stepanov.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.stepanov.entity.types.ActionType;
import org.stepanov.entity.types.AuditType;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Audit {
    private Integer id;
    private AuditType auditType;
    private ActionType actionType;
    private String playerFullName;
}
