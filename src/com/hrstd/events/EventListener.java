package com.hrstd.events;

import java.util.ArrayList;
import java.util.List;

public class EventListener {
    private final List<Event> events = new ArrayList<>();
    private final List<EventListenerInterface> listeners = new ArrayList<>();

    public static EventListener def = new EventListener();

    public List<Event> getEvents() {
        return events;
    }

    public List<EventListenerInterface> getListeners() {
        return listeners;
    }

    public void addEvent(Event e) {
        events.add(e);

        while (events.size() > 500) {
            events.remove(0);
        }

        for (int i = 0; i < getListeners().size(); i++) {
            EventListenerInterface r = getListeners().get(i);

            r.run(e, () -> getListeners().remove(r));
        }
    }

    public void addEvents(Event[] es) {
        for (Event e : es) {
            addEvent(e);
        }
    }

    public void addListener(EventListenerInterface r) {
        listeners.add(r);
    }

}
