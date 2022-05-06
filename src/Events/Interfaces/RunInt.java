package Events.Interfaces;

import Errors.Success;
import Errors.Undifferentiable;

public class RunInt {
    protected InterfaceRunnable ir = null;
    protected Runnable run = null;

    public RunInt(InterfaceRunnable ir) {
        this.ir = ir;
    }

    public RunInt(Runnable run) {
        this.run = run;
    }

    public Success run() {
        if (ir != null) {
            return ir.run();
        } else if (run != null) {
            run.run();
            return new Undifferentiable();
        }

        return new Undifferentiable();
    }
}
