package finalproject.application.repository;

import finalproject.application.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Account getAccountByEmail(String email);
    Account getAccountById(int accountId);
}
