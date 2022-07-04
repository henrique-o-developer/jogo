package com.hrstd.events.exec;

public class ExecNoDo extends Exec {
    public ExecNoDo(String name, Runnable when_do, Runnable when_cans) {
        super(name, when_do, when_cans, false);
    }
}
