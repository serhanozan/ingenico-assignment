package nl.ingenico.services.restfultransferservice.exception;

public class RestfulTransferServiceException extends RuntimeException {

	private static final long serialVersionUID = -7099721395937318147L;
	
	@Override
	public String getMessage() {
		return "An unknown exception occured!";
	}	
}
