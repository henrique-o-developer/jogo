package antigo;

public class CancelError extends Error {

    public CancelError(String msg) {
        super(msg, "CancelError");
    }
}
