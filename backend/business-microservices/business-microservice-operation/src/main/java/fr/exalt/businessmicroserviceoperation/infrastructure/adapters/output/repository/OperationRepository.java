package fr.exalt.businessmicroserviceoperation.infrastructure.adapters.output.repository;

import fr.exalt.businessmicroserviceoperation.infrastructure.adapters.output.models.OperationModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationRepository extends JpaRepository<OperationModel, String> {
}
