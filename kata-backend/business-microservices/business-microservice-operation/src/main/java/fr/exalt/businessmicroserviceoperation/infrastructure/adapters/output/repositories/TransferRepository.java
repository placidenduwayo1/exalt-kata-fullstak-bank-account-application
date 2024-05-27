package fr.exalt.businessmicroserviceoperation.infrastructure.adapters.output.repositories;

import fr.exalt.businessmicroserviceoperation.infrastructure.adapters.output.models.TransferModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransferRepository extends JpaRepository<TransferModel, String> {
}
