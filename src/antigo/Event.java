package antigo;

import antigo.Main.Main;

import java.util.TimerTask;

public class Event extends EventHandler {
    public final String what;
    public final String ext;
    public final CancelInterface cancel;
    public final long timeOfCall;
    protected boolean canceled = false;
    public String getEventName() {
        return this.getClass().getSimpleName();
    }

    public Event(String what, CancelInterface cancel, String ext) {
        if (cancel == null) cancel = new CancelInterface() {};

        this.what = what;
        this.cancel = cancel;
        this.timeOfCall = System.currentTimeMillis();
        this.ext = ext;

        if (!cancel.isCancelable() ||
                !cancel.hasMaxTimeToCancel())
            cancel.run();
        else {
            CancelInterface finalCancel = cancel;
            Main.setTimeout(new TimerTask() {
                @Override
                public void run() {
                    if (!canceled) finalCancel.run();
                }
            }, cancel.maxTimeToCancelInMills() - 1);
        }

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

    public boolean getCanceled() {
        return canceled;
    }
}
