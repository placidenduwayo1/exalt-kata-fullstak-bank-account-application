package fr.exalt.businessmicroserviceoperation.infrastructure.adapters.output.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Setter
@Getter
@ToString
public class OperationDto {
    private String type;
    private double mount;
    private String accountId;
}
