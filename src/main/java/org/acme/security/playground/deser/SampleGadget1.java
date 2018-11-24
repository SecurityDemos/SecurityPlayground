package org.acme.security.playground.deser;

import java.io.IOException;
import java.io.Serializable;

public class SampleGadget1 implements Runnable, Serializable {
    private final String command;

    public SampleGadget1(String command) {
        this.command = command;
    }

    @Override
    public void run() {
        try {
            Runtime.getRuntime().exec(command);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
