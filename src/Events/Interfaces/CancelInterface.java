package Events.Interfaces;

import Errors.Success;
import Errors.Undifferentiable;

public interface CancelInterface {
    default Success cancel() {
        return new Undifferentiable();
    };
    default int maxTimeToCancelInMills() { return 0; };

    default boolean hasMaxTimeToCancel() {
        return true;
    }

    default boolean isCancelable() {
        return true;
    }

    default void run() {};
}
