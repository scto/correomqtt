package org.correomqtt.ditest.complexbeans;

import lombok.Getter;
import lombok.Setter;
import org.correomqtt.di.DefaultBean;
import org.correomqtt.di.Inject;

@DefaultBean
@Getter
@Setter
public class Thermometer {

    int temperature;

    HeatCoil coil;

    @Inject
    public Thermometer(HeatCoil coil) {
        this.coil = coil;
    }
}
