package fr.exalt.businessmicroserviceaccount.infrastructure.adapters.output.repository;

import fr.exalt.businessmicroserviceaccount.infrastructure.adapters.output.models.AccountModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface AccountRepository extends JpaRepository<AccountModel, String> {
    Collection<AccountModel> findByCustomerId(String customerId);
}
