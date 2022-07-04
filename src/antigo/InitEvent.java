package antigo;

public class InitEvent extends Event {
    public InitEvent(String what, CancelInterface cancel) {
        super(what, cancel, "InitEvent");
    }
}
