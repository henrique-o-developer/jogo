package com.hrstd.events.exec.noDo;


import com.hrstd.events.EventListener;
import com.hrstd.events.exec.ExecNoDo;
import com.hrstd.events.start.DrawEvent;

import java.awt.*;

public class ExecGraphic extends ExecNoDo {
    public ExecGraphic(String name, Runnable when_do, Runnable when_cans, Graphics g, DrawEvent.Drawable t) {
        super(name, when_do, when_cans);

        EventListener.def.addEvent(new DrawEvent(name, () -> canceled = true, g, t));

        execute();
    }
}
