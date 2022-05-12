package Errors;

public class InputError extends Error {
    public InputError(String msg) {
        super(msg, "InputError");
    }
}
