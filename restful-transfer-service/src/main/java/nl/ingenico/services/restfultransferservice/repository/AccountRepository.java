package nl.ingenico.services.restfultransferservice.repository;
import java.util.Optional;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import nl.ingenico.services.restfultransferservice.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, String>{

	@Override
	@Lock(LockModeType.PESSIMISTIC_WRITE) // in order to achieve same functionality as SELECT FOR UPDATE
	Optional<Account> findById(String id);
}