package Events.Inline;

import Events.Interfaces.RunInt;

public class RunInlineEvent extends EventInline {
    public RunInlineEvent(String what, RunInt cancel, int mttc, boolean hmttc, boolean ic) {
        super(what, cancel, "RunInlineEvent", mttc, hmttc, ic);
    }
}
