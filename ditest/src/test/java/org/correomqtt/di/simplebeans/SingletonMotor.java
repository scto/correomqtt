package org.correomqtt.di.simplebeans;

import lombok.Getter;
import lombok.Setter;
import org.correomqtt.di.Inject;
import org.correomqtt.di.SingletonBean;

@SingletonBean
@Getter
@Setter
public class SingletonMotor {

    int speed;

    @Inject
    public SingletonMotor() {
        speed = 100;
    }
}
