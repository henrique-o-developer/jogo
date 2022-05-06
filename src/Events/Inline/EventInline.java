package Events.Inline;

import Errors.Success;
import Events.Event;
import Events.Interfaces.CancelInterface;
import Events.Interfaces.RunInt;

public class EventInline extends Event {
    public EventInline(String what, RunInt cancel, String ext, int mttc, boolean hmttc, boolean ic) {
        super(what, new CancelInterface() {
            @Override
            public Success cancel() {
                return cancel.run();
            }

            @Override
            public int maxTimeToCancelInMills() {
                return mttc;
            }

            @Override
            public boolean hasMaxTimeToCancel() {
                return hmttc;
            }

            @Override
            public boolean isCancelable() {
                return ic;
            }

            @Override
            public void run() {}
        }, ext);
    }
}
