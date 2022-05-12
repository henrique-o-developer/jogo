package Events;

import Events.Interfaces.CancelInterface;

public class RenderEvent extends Event {

    public RenderEvent(String what, CancelInterface cancel) {
        super(what, cancel, "RenderEvent");
    }
}
