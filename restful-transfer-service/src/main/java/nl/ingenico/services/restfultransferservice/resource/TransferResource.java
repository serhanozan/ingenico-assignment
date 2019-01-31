package nl.ingenico.services.restfultransferservice.resource;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import nl.ingenico.services.restfultransferservice.entity.Account;
import nl.ingenico.services.restfultransferservice.entity.Transfer;
import nl.ingenico.services.restfultransferservice.exception.RestfulTransferServiceException;
import nl.ingenico.services.restfultransferservice.service.TransactionService;

@RestController
public class TransferResource {
	
	@Autowired
	private TransactionService service;

	@PostMapping("/account/create")
	public ResponseEntity<?> createAccount(@RequestBody @Valid Account account, BindingResult result) {
		if (result.hasErrors()) {
			List<String> errors = new ArrayList<>();
			for (ObjectError error : result.getAllErrors()) {
				errors.add(error.getDefaultMessage());
			}
			return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
		} else {
			Account createdAccount = null;
			try{
				createdAccount = service.createAccount(account); 
			}catch (RestfulTransferServiceException e) {
				return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
			}
				return new ResponseEntity<>(createdAccount, HttpStatus.CREATED);
		}
	}
	
	@PostMapping("/transfer/make")
	public ResponseEntity<?> makeTransfer(@RequestBody @Valid Transfer transfer, BindingResult result) {
		if (result.hasErrors()) {
			List<String> errors = new ArrayList<>();
			for (ObjectError error : result.getAllErrors()) {
				errors.add(error.getDefaultMessage());
			}
			return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
		} else {
			Transfer createdTransfer = null;
			try{
				createdTransfer = service.makeTransfer(transfer);
			}catch (RestfulTransferServiceException e) {
				return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
			}
				return new ResponseEntity<>(createdTransfer, HttpStatus.CREATED);
		}
	}

	@GetMapping("/transfer/all")
	public List<Transfer> getAllTransfers() {
		return service.getAllTransfers();
	}
	
	@GetMapping("/account/all")
	public List<Account> getAllAccounts() {
		return service.getAllAccounts();
	}

	@GetMapping("/account/created/{date}")
	public List<Account> getAllAccounts(@PathVariable("date") @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate date ) {
		return service.getAccountsByDate(date);
	}
	
}
