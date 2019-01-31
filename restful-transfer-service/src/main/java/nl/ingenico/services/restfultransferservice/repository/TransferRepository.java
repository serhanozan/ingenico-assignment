package nl.ingenico.services.restfultransferservice.repository;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import nl.ingenico.services.restfultransferservice.entity.Transfer;

@Repository
public interface TransferRepository extends JpaRepository<Transfer, Integer>{

}