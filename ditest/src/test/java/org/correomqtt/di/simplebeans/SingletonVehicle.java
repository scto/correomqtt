package org.correomqtt.di.simplebeans;

import lombok.Getter;
import lombok.Setter;
import org.correomqtt.di.Inject;
import org.correomqtt.di.SingletonBean;

@SingletonBean
@Getter
@Setter
public class SingletonVehicle {

    SingletonMotor motor;

    @Inject
    public SingletonVehicle(SingletonMotor motor) {
        this.motor = motor;
    }

    public void setSpeed(int speed) {
        motor.setSpeed(speed);
    }
}
