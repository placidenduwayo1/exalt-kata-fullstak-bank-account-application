package fr.exalt.businessmicroserviceaccount.infrastructure.adapters.output.repository;

import fr.exalt.businessmicroserviceaccount.infrastructure.adapters.output.models.entities.BankAccountModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface AccountRepository extends JpaRepository<BankAccountModel, String> {
    Collection<BankAccountModel> findByCustomerId(String customerId);
}
