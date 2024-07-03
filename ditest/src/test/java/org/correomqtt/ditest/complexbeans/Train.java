package org.correomqtt.ditest.complexbeans;

import lombok.Getter;
import lombok.Setter;
import org.correomqtt.di.Assisted;
import org.correomqtt.di.DefaultBean;
import org.correomqtt.di.Inject;

@DefaultBean
@Getter
@Setter
public class Train {

    int seats;
    TrainDoor door;

    @Inject
    public Train(TrainDoor door, @Assisted int seats) {
        this.seats = seats;
        this.door = door;
    }
}
