package org.stepanov.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.stepanov.entity.types.ActionType;
import org.stepanov.entity.types.AuditType;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuditDto {
    private AuditType auditType;
    private ActionType actionType;
    private String playerFullName;
}
