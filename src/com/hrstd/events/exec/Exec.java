package com.hrstd.events.exec;

import com.hrstd.events.EventListener;
import com.hrstd.events.end.EndEvent;
import com.hrstd.events.start.DrawEvent;
import com.hrstd.events.start.StartEvent;

public class Exec {
    protected boolean canceled = false, dwdwc;
    protected String name;
    protected Runnable when_do, when_cans;

    public Exec(String name, Runnable when_do, Runnable when_cans, boolean dwdwc) {
        this.name = name;
        this.when_do = when_do;
        this.when_cans = when_cans == null ? () -> {} : when_cans;
        this.dwdwc = dwdwc;
    }

    public void execute() {
        if (!canceled || dwdwc) when_do.run();

        if (canceled) when_cans.run();

        EventListener.def.addEvent(new EndEvent(name));
    }
}
