package fr.exalt.businessmicroserviceoperation.infrastructure.adapters.output.repositories;

import fr.exalt.businessmicroserviceoperation.infrastructure.adapters.output.models.OperationModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface OperationRepository extends JpaRepository<OperationModel, String> {
    Collection<OperationModel> findByAccountId(String accountId);
}
