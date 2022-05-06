package Events.Inline;

import Events.Interfaces.RunInt;

public class RenderInlineEvent extends EventInline {
    public RenderInlineEvent(String what, RunInt cancel, int mttc, boolean hmttc, boolean ic) {
        super(what, cancel, "RenderInlineEvent", mttc, hmttc, ic);
    }
}
