package org.correomqtt.ditest.complexbeans;

import org.correomqtt.di.Inject;
import org.correomqtt.di.SingletonBean;

@SingletonBean
public class SingletonOven {

    HeatCoil coil;

    @Inject
    public SingletonOven(HeatCoil coil) {
        this.coil = coil;
    }
}
