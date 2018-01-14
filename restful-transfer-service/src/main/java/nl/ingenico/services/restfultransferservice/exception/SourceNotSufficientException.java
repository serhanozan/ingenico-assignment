package nl.ingenico.services.restfultransferservice.exception;

public class SourceNotSufficientException extends RestfulTransferServiceException {

	private static final long serialVersionUID = 4083206932636682121L;
	
	@Override
	public String getMessage() {
		return "Source account balance is not sufficient!";
	}
}
