package nl.ingenico.services.restfultransferservice.entity;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class Account {

	@Id	
	@NotEmpty(message = "Enter an account name")
	private String name;

	@Min(value = 0, message = "Enter an initial balance")
	@NotNull(message = "Enter an initial balance")
	private BigDecimal balance;

	/**
	 * The default constructor
	 */
	public Account() {
		
	}

	/**
	 * @param id
	 * @param name
	 * @param balance
	 */
	public Account(String name, BigDecimal balance) {
		this.name = name;
		this.balance = balance;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the balance
	 */
	public BigDecimal getBalance() {
		return balance;
	}

	/**
	 * @param balance the balance to set
	 */
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Account [ name=" + name + ", balance=" + balance + "]";
	}
	
}
