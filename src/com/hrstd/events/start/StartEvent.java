package com.hrstd.events.start;

import com.hrstd.events.Event;

public class StartEvent extends Event {
    public StartEvent(String name, Runnable when_censelled) {
        super(name, when_censelled);
    }
}
