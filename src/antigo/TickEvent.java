package antigo;

public class TickEvent extends Event {
    public TickEvent(String what, CancelInterface cancel) {
        super(what, cancel, "TickEvent");
    }
}
