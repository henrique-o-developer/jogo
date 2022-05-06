package Events.Inline;

import Events.Interfaces.RunInt;

public class InitInlineEvent extends EventInline {
    public InitInlineEvent(String what, RunInt cancel, int mttc, boolean hmttc, boolean ic) {
        super(what, cancel, "InitInlineEvent", mttc, hmttc, ic);
    }
}
