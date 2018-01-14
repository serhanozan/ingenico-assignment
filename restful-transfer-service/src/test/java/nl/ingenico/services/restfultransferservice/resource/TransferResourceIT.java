package nl.ingenico.services.restfultransferservice.resource;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import nl.ingenico.services.restfultransferservice.RestfulTransferServiceApplication;
import nl.ingenico.services.restfultransferservice.entity.Account;
import nl.ingenico.services.restfultransferservice.entity.Transfer;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RestfulTransferServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TransferResourceIT {

	@LocalServerPort
	private int port;
	
	TestRestTemplate restTemplate = new TestRestTemplate();
	
	HttpHeaders headers = new HttpHeaders();
	
	@Test
	public void createAccount() throws JSONException {

		HttpEntity<Account> request = new HttpEntity<>(new Account("account7", new BigDecimal(300)));
		Account response = restTemplate.postForObject(getFullRequestURL("/account/create"), request, Account.class);

		assertThat(response, notNullValue());
		assertTrue(response.getName().equals("account7"));
	}	
	
	@Test
	public void createAccountWithoutInitialBalance() throws Exception {
		HttpEntity<Account> request = new HttpEntity<>(new Account("account7", null));

		ResponseEntity<?> response = restTemplate
		  .exchange(getFullRequestURL("/account/create"), HttpMethod.POST, request, Object.class);
		  
		assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
	}
	
	@Test
	public void createAccountWithNegativeInitialBalance() throws Exception {
		HttpEntity<Account> request = new HttpEntity<>(new Account("account7", new BigDecimal(-5)));

		ResponseEntity<?> response = restTemplate
		  .exchange(getFullRequestURL("/account/create"), HttpMethod.POST, request, Object.class);
		  
		assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
	}
	
	@Test
	public void createAccountWithoutName() throws Exception {
		HttpEntity<Account> request = new HttpEntity<>(new Account(null, new BigDecimal(300)));

		ResponseEntity<?> response = restTemplate
		  .exchange(getFullRequestURL("/account/create"), HttpMethod.POST, request, Object.class);
		  
		assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
	}
	
	@Test
	public void makeTransfer() throws JSONException {

		Transfer transfer = new Transfer();
		transfer.setSource(new Account("account1", null));
		transfer.setDestination(new Account("account2", null));
		transfer.setAmount(new BigDecimal(300));
		
		HttpEntity<Transfer> request = new HttpEntity<>(transfer);
		Transfer response = restTemplate.postForObject(getFullRequestURL("/transfer/make"), request, Transfer.class);

		assertThat(response, notNullValue());
		assertTrue(response.getDestination().getName().equals("account2"));
	}	
	
	@Test
	public void makeTransferWithoutAmount() throws Exception {
		Transfer transfer = new Transfer();
		transfer.setSource(new Account("account1", null));
		transfer.setDestination(new Account("account2", null));
		transfer.setAmount(null);
		
		HttpEntity<Transfer> request = new HttpEntity<>(transfer);
		ResponseEntity<?> response = restTemplate
		  .exchange(getFullRequestURL("/transfer/make"), HttpMethod.POST, request, Object.class);
		  
		assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
	}
	
	@Test
	public void makeTransferWithInvalidAmount() throws Exception {
		Transfer transfer = new Transfer();
		transfer.setSource(new Account("account1", null));
		transfer.setDestination(new Account("account2", null));
		transfer.setAmount(new BigDecimal(-3));
		
		HttpEntity<Transfer> request = new HttpEntity<>(transfer);
		ResponseEntity<?> response = restTemplate
		  .exchange(getFullRequestURL("/transfer/make"), HttpMethod.POST, request, Object.class);
		  
		assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
	}
	
	@Test
	public void makeTransferWithInvalidSource() throws Exception {
		Transfer transfer = new Transfer();
		transfer.setSource(new Account("nonExistingAccount", null));
		transfer.setDestination(new Account("account2", null));
		transfer.setAmount(new BigDecimal(300));
		
		HttpEntity<Transfer> request = new HttpEntity<>(transfer);
		ResponseEntity<String> response = restTemplate
		  .exchange(getFullRequestURL("/transfer/make"), HttpMethod.POST, request, String.class);
		  
		assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
	}
	
	@Test
	public void makeTransferWithInvalidDestination() throws Exception {
		Transfer transfer = new Transfer();
		transfer.setSource(new Account("account2", null));
		transfer.setDestination(new Account("nonExistingAccount", null));
		transfer.setAmount(new BigDecimal(300));
		
		HttpEntity<Transfer> request = new HttpEntity<>(transfer);
		ResponseEntity<String> response = restTemplate
		  .exchange(getFullRequestURL("/transfer/make"), HttpMethod.POST, request, String.class);
		  
		assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
	}
	
	@Test
	public void makeTransferWithInsufficientSource() throws Exception {
		Transfer transfer = new Transfer();
		transfer.setSource(new Account("account1", null));
		transfer.setDestination(new Account("account2", null));
		transfer.setAmount(new BigDecimal(30000000));
		
		HttpEntity<Transfer> request = new HttpEntity<>(transfer);
		ResponseEntity<String> response = restTemplate
		  .exchange(getFullRequestURL("/transfer/make"), HttpMethod.POST, request, String.class);
		  
		assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
	}
	
	private String getFullRequestURL(String uri) {
		return "http://localhost:" + port + uri;
	}
}
