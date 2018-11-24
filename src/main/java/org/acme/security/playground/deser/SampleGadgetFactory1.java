package org.acme.security.playground.deser;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

public class SampleGadgetFactory1 implements Serializable {

    private final Runnable initHook;

    public SampleGadgetFactory1(Runnable initHook) {
        this.initHook = initHook;
    }

    /**
     * Java serialization allows to customize object deserialization
     *
     */
    public void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        initHook.run();
    }
}
