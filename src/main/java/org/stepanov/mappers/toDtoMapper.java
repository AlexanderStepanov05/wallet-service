package org.stepanov.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.stepanov.dto.AuditDto;
import org.stepanov.dto.PlayerDto;
import org.stepanov.dto.TransactionDto;
import org.stepanov.entity.Audit;
import org.stepanov.entity.Player;
import org.stepanov.entity.Transaction;

@Mapper
public interface toDtoMapper {
    toDtoMapper INSTANCE = Mappers.getMapper(toDtoMapper.class);

    PlayerDto toDTO(Player player);

    AuditDto toDTO(Audit audit);

    TransactionDto toDTO(Transaction transaction);
}
