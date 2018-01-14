package nl.ingenico.services.restfultransferservice.service;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import nl.ingenico.services.restfultransferservice.entity.Account;
import nl.ingenico.services.restfultransferservice.entity.Transfer;
import nl.ingenico.services.restfultransferservice.exception.DestinationNotValidException;
import nl.ingenico.services.restfultransferservice.exception.SourceNotSufficientException;
import nl.ingenico.services.restfultransferservice.exception.SourceNotValidException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TransactionServiceTest {

	@Autowired
	private TransactionService service;
	
	@Test
	public void makeTransfer() {
		Transfer transfer = new Transfer();
		transfer.setSource(new Account("account1", null));
		transfer.setDestination(new Account("account2", null));
		transfer.setAmount(new BigDecimal(300));
		
		Transfer result = service.makeTransfer(transfer);
		
		assertEquals(transfer.getAmount(), result.getAmount());
		assertEquals(transfer.getSource().getName(), result.getSource().getName());
		assertEquals(transfer.getDestination().getName(), result.getDestination().getName());
		
	}
	
	@Test(expected = SourceNotValidException.class)
	public void makeTransferSourceNotValidException() {
		Transfer transfer = new Transfer();
		transfer.setSource(new Account("", null));
		transfer.setDestination(new Account("account2", null));
		transfer.setAmount(new BigDecimal(300));
		
		service.makeTransfer(transfer);
	}
	
	@Test(expected = DestinationNotValidException.class)
	public void makeTransferDestinationNotValidException() {
		Transfer transfer = new Transfer();
		transfer.setSource(new Account("account1", null));
		transfer.setDestination(new Account("", null));
		transfer.setAmount(new BigDecimal(300));
		
		service.makeTransfer(transfer);
	}
	
	@Test(expected = SourceNotSufficientException.class)
	public void makeTransferSourceNotSufficientException() {
		Transfer transfer = new Transfer();
		transfer.setSource(new Account("account1", null));
		transfer.setDestination(new Account("account2", null));
		transfer.setAmount(new BigDecimal(300000));
		
		service.makeTransfer(transfer);
	}
}
