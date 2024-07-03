package org.correomqtt.ditest.simplebeans;

import lombok.Getter;
import lombok.Setter;
import org.correomqtt.di.DefaultBean;

@DefaultBean
@Getter
@Setter
public class FactoryMotor {

    private int speed;

    public FactoryMotor() {
        speed = 100;
    }
}
