package nl.ingenico.services.restfultransferservice.resource;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import nl.ingenico.services.restfultransferservice.entity.Account;
import nl.ingenico.services.restfultransferservice.entity.Transfer;
import nl.ingenico.services.restfultransferservice.service.TransactionService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = TransferResource.class, secure = false)
public class TransferResourceTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private TransactionService transactionService;
	
	@Test
	public void createAccount() throws Exception {
		Account mockAccount = new Account("mockAccount", BigDecimal.TEN);
		String mockAccountJson = "{\"name\":\"mockAccount\",\"balance\":10}";
		
		when(transactionService.createAccount(Mockito.any(Account.class))).thenReturn(mockAccount);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/account/create")
				.accept(MediaType.APPLICATION_JSON).content(mockAccountJson)
				.contentType(MediaType.APPLICATION_JSON);
	
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.CREATED.value(), response.getStatus());
		
		JSONAssert.assertEquals(mockAccountJson, result.getResponse().getContentAsString(), false);
	}
	
	@Test
	public void createAccountWithoutInitialBalance() throws Exception {
		String mockAccountJson = "{\"name\":\"mockAccount\"}";
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/account/create")
				.accept(MediaType.APPLICATION_JSON).content(mockAccountJson)
				.contentType(MediaType.APPLICATION_JSON);
	
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
		
	}
	
	@Test
	public void createAccountWithNegativeInitialBalance() throws Exception {
		String mockAccountJson = "{\"name\":\"mockAccount\",\"balance\":-10}";
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/account/create")
				.accept(MediaType.APPLICATION_JSON).content(mockAccountJson)
				.contentType(MediaType.APPLICATION_JSON);
	
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
		
	}
	
	@Test
	public void createAccountWithoutName() throws Exception {
		String mockAccountJson = "{\"balance\":10}";
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/account/create")
				.accept(MediaType.APPLICATION_JSON).content(mockAccountJson)
				.contentType(MediaType.APPLICATION_JSON);
	
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
		
	}
	
	@Test
	public void makeTransfer() throws Exception {
		Transfer mockTransfer = new Transfer();
		mockTransfer.setSource(new Account("account1", null));
		mockTransfer.setDestination(new Account("account2", null));
		mockTransfer.setAmount(new BigDecimal(300));
		String mockTransferJson = "{\"source\":{\"name\":\"account1\"},\"destination\":{\"name\":\"account2\"},\"amount\":300}";
		
		when(transactionService.makeTransfer(Mockito.any(Transfer.class))).thenReturn(mockTransfer);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/transfer/make")
				.accept(MediaType.APPLICATION_JSON).content(mockTransferJson)
				.contentType(MediaType.APPLICATION_JSON);
	
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.CREATED.value(), response.getStatus());
		
		JSONAssert.assertEquals(mockTransferJson, result.getResponse().getContentAsString(), false);
	}
	
	@Test
	public void makeTransferWithoutAmount() throws Exception {
		String mockTransferJson = "{\"source\":{\"name\":\"account1\"},\"destination\":{\"name\":\"account2\"}}";
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/transfer/make")
				.accept(MediaType.APPLICATION_JSON).content(mockTransferJson)
				.contentType(MediaType.APPLICATION_JSON);
	
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
		
	}
	
	@Test
	public void makeTransferWithInvalidAmount() throws Exception {
		String mockTransferJson =  "{\"source\":{\"name\":\"account1\"},\"destination\":{\"name\":\"account2\"},\"amount\":0}";
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/transfer/make")
				.accept(MediaType.APPLICATION_JSON).content(mockTransferJson)
				.contentType(MediaType.APPLICATION_JSON);
	
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
		
	}
	
	@Test
	public void makeTransferWithInvalidSource() throws Exception {
		String mockTransferJson =  "{\"source\":{\"name\":\"\"},\"destination\":{\"name\":\"account2\"},\"amount\":0}";
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/transfer/make")
				.accept(MediaType.APPLICATION_JSON).content(mockTransferJson)
				.contentType(MediaType.APPLICATION_JSON);
	
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
		
	}
	
	@Test
	public void makeTransferWithInvalidDestination() throws Exception {
		String mockTransferJson =  "{\"source\":{\"name\":\"account1\"},\"destination\":{\"name\":\"\"},\"amount\":0}";
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/transfer/make")
				.accept(MediaType.APPLICATION_JSON).content(mockTransferJson)
				.contentType(MediaType.APPLICATION_JSON);
	
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
		
	}
		
}
