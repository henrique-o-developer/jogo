package Events;

import Events.Interfaces.CancelInterface;

public class InitEvent extends Event {
    public InitEvent(String what, CancelInterface cancel) {
        super(what, cancel, "InitEvent");
    }
}
