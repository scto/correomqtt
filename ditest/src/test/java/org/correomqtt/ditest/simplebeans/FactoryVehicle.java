package org.correomqtt.ditest.simplebeans;

import lombok.Getter;
import lombok.Setter;
import org.correomqtt.di.DefaultBean;
import org.correomqtt.di.Inject;

@DefaultBean
@Getter
@Setter
public class FactoryVehicle {

    FactoryMotor motor;

    @Inject
    public FactoryVehicle(FactoryMotor dummyBean) {
        this.motor = dummyBean;
    }

    public void setSpeed(int speed) {
        motor.setSpeed(speed);
    }
}
