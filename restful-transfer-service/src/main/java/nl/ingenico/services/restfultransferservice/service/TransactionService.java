package nl.ingenico.services.restfultransferservice.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nl.ingenico.services.restfultransferservice.entity.Account;
import nl.ingenico.services.restfultransferservice.entity.Transfer;
import nl.ingenico.services.restfultransferservice.exception.AccountAlreadyExistsException;
import nl.ingenico.services.restfultransferservice.exception.DestinationNotValidException;
import nl.ingenico.services.restfultransferservice.exception.SourceNotSufficientException;
import nl.ingenico.services.restfultransferservice.exception.SourceNotValidException;
import nl.ingenico.services.restfultransferservice.repository.AccountRepository;
import nl.ingenico.services.restfultransferservice.repository.TransferRepository;

@Service
public class TransactionService{

	@Autowired
	private TransferRepository transferRepository;

	@Autowired
	private AccountRepository accountRepository;

	public Account createAccount(Account account) {
		Optional<Account> result = accountRepository.findById(account.getName());
		if (result.isPresent()) {
			throw new AccountAlreadyExistsException();
		} else {
			return accountRepository.save(account);
		}
	}

	@Transactional
	public Transfer makeTransfer(Transfer transfer) {
 
		Account source = null;
		Account destination = null;

		Optional<Account> sourceOptional = accountRepository.findById(transfer.getSource().getName());
		if (sourceOptional.isPresent()) {
			source = sourceOptional.get();
		} else {
			throw new SourceNotValidException();
		}

		Optional<Account> destinationOptional = accountRepository.findById(transfer.getDestination().getName());
		if (destinationOptional.isPresent()) {
			destination = destinationOptional.get();
		} else {
			throw new DestinationNotValidException();
		}

		if (source.getBalance().compareTo(transfer.getAmount()) >= 0) {
			source.setBalance(source.getBalance().subtract(transfer.getAmount()));
			transfer.setSource(accountRepository.saveAndFlush(source));
			destination.setBalance(destination.getBalance().add(transfer.getAmount()));
			transfer.setDestination(accountRepository.saveAndFlush(destination));
			transfer.setProcessTimeStamp(LocalDateTime.now());
			return transferRepository.save(transfer);
		} else {
			throw new SourceNotSufficientException();
		}
	}

	public List<Transfer> getAllTransfers() {
		return transferRepository.findAll();
	}

	public List<Account> getAllAccounts() {
		return accountRepository.findAll();
	}
	
	public List<Account> getAccountsByDate(LocalDate created) {
		return accountRepository.findAllByCreateDate(created);
	}
}
