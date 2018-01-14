package nl.ingenico.services.restfultransferservice.exception;

public class SourceNotValidException extends RestfulTransferServiceException {

	private static final long serialVersionUID = -7294749635013648906L;
	
	@Override
	public String getMessage() {
		return "Source account is not valid!";
	}
}
