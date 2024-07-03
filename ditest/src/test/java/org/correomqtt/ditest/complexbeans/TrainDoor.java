package org.correomqtt.ditest.complexbeans;

import lombok.Getter;
import lombok.Setter;
import org.correomqtt.di.DefaultBean;
import org.correomqtt.di.Inject;

@DefaultBean
@Getter
@Setter
public class TrainDoor {

    boolean isOpen;

    @Inject
    public TrainDoor() {
        isOpen = false;
    }

    public void toggle() {
        isOpen = !isOpen;
    }
}
