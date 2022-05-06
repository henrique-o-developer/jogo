package Events.Inline;

import Events.Interfaces.RunInt;

public class TickInlineEvent extends EventInline {
    public TickInlineEvent(String what, RunInt cancel, int mttc, boolean hmttc, boolean ic) {
        super(what, cancel, "TickInlineEvent", mttc, hmttc, ic);
    }
}
