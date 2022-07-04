package com.hrstd.events.exec;

public class ExecDo extends Exec {
    public ExecDo(String name, Runnable when_do, Runnable when_cans) {
        super(name, when_do, when_cans, true);
    }
}
