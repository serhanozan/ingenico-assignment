package nl.ingenico.services.restfultransferservice.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
public class Transfer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
    @JoinColumn(name = "src_acc")
	private Account source;
	
	@ManyToOne
    @JoinColumn(name = "dest_acc")	
	private Account destination;

	@Column(name = "process_timestamp")
	private LocalDateTime processTimeStamp;

	@Min(value = 1 , message = "Enter an amount to transfer")
	@NotNull(message = "Enter an amount to transfer")
	private BigDecimal amount;
	
	/**
	 * 
	 */
	public Transfer() {

	}

	/**
	 * @param id
	 * @param source
	 * @param destination
	 * @param processTimeStamp
	 * @param amount
	 */
	public Transfer(Integer id, Account source, Account destination, LocalDateTime processTimeStamp,
			BigDecimal amount) {
		this.id = id;
		this.source = source;
		this.destination = destination;
		this.processTimeStamp = processTimeStamp;
		this.amount = amount;
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	
	/**
	 * @return the source
	 */
	public Account getSource() {
		return source;
	}

	/**
	 * @param source the source to set
	 */
	public void setSource(Account source) {
		this.source = source;
	}

	/**
	 * @return the destination
	 */
	public Account getDestination() {
		return destination;
	}

	/**
	 * @param destination the destination to set
	 */
	public void setDestination(Account destination) {
		this.destination = destination;
	}

	/**
	 * @return the processTimeStamp
	 */
	public LocalDateTime getProcessTimeStamp() {
		return processTimeStamp;
	}

	/**
	 * @param processTimeStamp the processTimeStamp to set
	 */
	public void setProcessTimeStamp(LocalDateTime processTimeStamp) {
		this.processTimeStamp = processTimeStamp;
	}

	/**
	 * @return the balance
	 */
	public BigDecimal getAmount() {
		return amount;
	}

	/**
	 * @param balance the balance to set
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Transfer [source=" + source.toString() + ", destination=" + destination.toString()
				+ ", processTimeStamp=" + processTimeStamp + "]";
	}

	
}
