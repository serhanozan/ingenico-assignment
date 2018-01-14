package nl.ingenico.services.restfultransferservice.exception;

public class DestinationNotValidException extends RestfulTransferServiceException {

	private static final long serialVersionUID = -8934764200827995635L;

	@Override
	public String getMessage() {
		return "Destination account is not valid!";
	}
}
