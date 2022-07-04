package com.hrstd.events;

public class Event {
    private boolean canselled = false;
    private String name;
    private final Runnable when_canselled;

    public Event(String name, Runnable when_censelled) {
        this.name = name;
        this.when_canselled = when_censelled == null ? () -> {} : when_censelled;
    }

    public void setCanselled() {
        this.canselled = false;
        when_canselled.run();
    }

    public boolean getCanselled() {
        return canselled;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Event {" +
                "canselled=" + canselled +
                ", name='" + name + '\'' +
                ", when_canselled=" + when_canselled +
                ", currentClass=" + this.getClass().getSimpleName() +
                '}';
    }
}
