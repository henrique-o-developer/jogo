package Errors;

public class Error extends Success {
    protected String msg;
    public Error(String msg, String cause) {
        super(cause);

        this.msg = msg;
        this.success = false;
    }

    public String getMsg() {
        return msg;
    }
}
