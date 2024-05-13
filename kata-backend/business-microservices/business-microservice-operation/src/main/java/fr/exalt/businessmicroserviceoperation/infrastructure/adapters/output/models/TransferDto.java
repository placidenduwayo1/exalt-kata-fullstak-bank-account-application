package fr.exalt.businessmicroserviceoperation.infrastructure.adapters.output.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Setter
@Getter
@ToString
public class TransferDto {
    private String origin;
    private String destination;
    private double mount;
}
