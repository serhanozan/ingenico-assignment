package nl.ingenico.services.restfultransferservice.exception;

public class AccountAlreadyExistsException extends RestfulTransferServiceException {

	private static final long serialVersionUID = 2973871728969207173L;
	
	@Override
	public String getMessage() {
		return "Account already exists!";
	}
}
