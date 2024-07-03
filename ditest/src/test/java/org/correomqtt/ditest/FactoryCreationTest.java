package org.correomqtt.ditest;

import org.correomqtt.di.SoyDi;
import org.correomqtt.ditest.complexbeans.TrainFactory;
import org.correomqtt.ditest.simplebeans.FactoryVehicleFactory;
import org.correomqtt.ditest.simplebeans.SingletonVehicle;
import org.correomqtt.ditest.simplebeans.SingletonVehicleFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FactoryCreationTest {

    @BeforeEach
    public void setUp() {
        SoyDi.scan("org.correomqtt.ditest");

        // Soy has no reset after test feature currently
        var vehicle = SoyDi.inject(SingletonVehicle.class);
        vehicle.setSpeed(100); // reset
    }

    @Test
    public void createBean_withFactory_shouldCreateBean() {

        // arrange
        var factory = new FactoryVehicleFactory();

        // act
        var bean = factory.create();

        // assert
        assertNotNull(bean);
        assertNotNull(bean.getMotor());
        assertEquals(100, bean.getMotor().getSpeed());
    }

    @Test
    public void createBean_withFactoryTwice_shouldCreateTwoBeans() {

        // arrange
        var factory = new FactoryVehicleFactory();

        // act
        var bean1 = factory.create();
        var bean2 = factory.create();

        bean1.setSpeed(130);
        bean2.setSpeed(140);

        // assert
        assertNotNull(bean1);
        assertNotNull(bean2);

        assertNotNull(bean1.getMotor());
        assertNotNull(bean2.getMotor());

        assertEquals(130, bean1.getMotor().getSpeed());
        assertEquals(140, bean2.getMotor().getSpeed());

        // should be new instance
        assertNotEquals(bean1, bean2);
    }


    @Test
    public void createBean_withAssistedFactory_shouldCreateBean() {

        // arrange
        var factory = new TrainFactory();

        // act
        var bean = factory.create(200);
        bean.getDoor().toggle();

        // assert
        assertNotNull(bean);
        assertNotNull(bean.getDoor());
        assertNotEquals(0, bean.getSeats());
        assertEquals(200, bean.getSeats());
        assertTrue(bean.getDoor().isOpen());
    }

    // singleton bean factory
    @Test
    public void createSingletonBean_withFactory_shouldCreateBean() {

        // arrange
        var factory = new SingletonVehicleFactory();

        // act
        var bean = factory.create();

        // assert
        assertNotNull(bean);
        assertNotNull(bean.getMotor());
        assertEquals(100, bean.getMotor().getSpeed());
    }

    @Test
    @Disabled("Currently, singletons can be instantiated twice via factory. This is not exactly intended.")
    public void createSingletonBean_withFactoryTwice_shouldCreateIdenticalBean() {

        // arrange
        var factory = new SingletonVehicleFactory();

        // act
        var bean1 = factory.create();
        var bean2 = factory.create();

        bean1.setSpeed(130);
        bean2.setSpeed(140);

        // assert
        assertEquals(bean1, bean2);
        assertEquals(bean1.getMotor(), bean2.getMotor());
        assertEquals(140, bean1.getMotor().getSpeed());
    }
}
