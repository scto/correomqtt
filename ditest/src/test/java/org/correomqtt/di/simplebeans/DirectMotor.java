package org.correomqtt.di.simplebeans;

import lombok.Getter;
import lombok.Setter;
import org.correomqtt.di.DefaultBean;
import org.correomqtt.di.Inject;

@DefaultBean
@Getter
@Setter
public class DirectMotor {

    private int speed;

    @Inject
    public DirectMotor() {
        this.speed = 100;
    }
}
