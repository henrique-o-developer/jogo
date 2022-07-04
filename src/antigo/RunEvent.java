package antigo;

public class RunEvent extends Event {
    public RunEvent(String what, CancelInterface cancel) {
        super(what, cancel, "RunEvent");
    }
}
