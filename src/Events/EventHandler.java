package Events;

import Events.Interfaces.NewEventInterface;

import java.util.ArrayList;
import java.util.List;

public class EventHandler {
    protected static List<Event> events = new ArrayList<>();
    protected static List<NewEventInterface> NotifyOnNewEvent = new ArrayList();

    protected static void addEvent(Event e) {
        events.add(0, e);

        if (events.size() > 500) {
            events.remove(500);
        }

        notifyHandlers(e);
    }

    protected static void notifyHandlers(Event e) {
        for (NewEventInterface newEventInterface : NotifyOnNewEvent) {
            newEventInterface.newEvent(e);
        }
    }

    public static void addHandler(NewEventInterface h) {
        NotifyOnNewEvent.add(h);
    }

    public static List<Event> getEvents() {
        return events;
    }
}