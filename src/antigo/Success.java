package antigo;

public class Success {
    protected boolean success;
    protected String cause;
    public Success(String cause) {
        success = true;
        this.cause = cause;
    }

    public boolean getSuccess() {
        return success;
    }

    public String getCause() {
        return cause;
    }

    public String getFullMessage() {
        return "success guaranteed in "+cause;
    }

    @Override
    public String toString() {
        return "Success {" +
                "success=" + success +
                ", cause='" + cause + '\'' +
                '}';
    }
}
