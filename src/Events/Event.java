package Events;

import Errors.CancelError;
import Errors.Success;
import Events.Interfaces.CancelInterface;
import Main.Main;

public class Event extends EventHandler {
    public String what;
    public String ext;
    public CancelInterface cancel;
    public long timeOfCall;
    public boolean canceled = false;
    public String getEventName() {
        return this.getClass().getSimpleName();
    }

    public Event(String what, CancelInterface cancel, String ext) {
        this.what = what;
        this.cancel = cancel;
        this.timeOfCall = System.currentTimeMillis();
        this.ext = ext;

        if (!cancel.isCancelable() || !cancel.hasMaxTimeToCancel()) cancel.run();
        else Main.setTimeout(() -> {
            if (!canceled) cancel.run();
        }, cancel.maxTimeToCancelInMills()-1);

        addEvent(this);
    }

    public Success cancel() {
        canceled = true;
        if (!cancel.isCancelable()) {
            return new CancelError("this event is not cancelable");
        } else if (cancel.hasMaxTimeToCancel()) {
            if (System.currentTimeMillis() - timeOfCall > cancel.maxTimeToCancelInMills()) {
                return new CancelError("cannot cancel after maxTimeToCancel (mills: "+cancel.maxTimeToCancelInMills()+")");
            }
        }

        return cancel.cancel();
    }
}
