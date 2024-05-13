package fr.exalt.businessmicroserviceoperation.infrastructure.adapters.output.repository;

import fr.exalt.businessmicroserviceoperation.infrastructure.adapters.output.models.OperationModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface OperationRepository extends JpaRepository<OperationModel, String> {
    Collection<OperationModel> findByAccountId(String accountId);
}
