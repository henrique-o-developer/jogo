package Events.Interfaces;

import Errors.Success;

public interface CancelInterface {
    Success cancel();
    int maxTimeToCancelInMills();
    boolean hasMaxTimeToCancel();
    boolean isCancelable();
    void run();
}
