package com.hrstd.events.exec.noDo;

import com.hrstd.events.EventListener;
import com.hrstd.events.exec.ExecNoDo;
import com.hrstd.events.start.StandardEvent;

public class StandardExec extends ExecNoDo {
    public StandardExec(String name) {
        super(name, () -> {}, () -> {});

        EventListener.def.addEvent(new StandardEvent(name));

        execute();
    }
}
