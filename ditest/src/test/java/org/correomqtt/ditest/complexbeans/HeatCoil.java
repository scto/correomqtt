package org.correomqtt.ditest.complexbeans;

import lombok.Getter;
import lombok.Setter;
import org.correomqtt.di.DefaultBean;
import org.correomqtt.di.Inject;

@DefaultBean
@Getter
@Setter
public class HeatCoil {

    Thermometer thermometer;

    @Inject
    public HeatCoil(Thermometer thermometer) {
        this.thermometer = thermometer;
    }
}
