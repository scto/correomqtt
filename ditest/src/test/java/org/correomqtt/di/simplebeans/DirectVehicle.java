package org.correomqtt.di.simplebeans;

import lombok.Getter;
import lombok.Setter;
import org.correomqtt.di.DefaultBean;
import org.correomqtt.di.Inject;

@DefaultBean
@Getter
@Setter
public class DirectVehicle {

    DirectMotor motor;

    @Inject
    public DirectVehicle(DirectMotor motor) {
        this.motor = motor;
    }

    public void setSpeed(int speed) {
        motor.setSpeed(speed);
    }
}
