package org.correomqtt.ditest;

import org.correomqtt.di.SoyDi;
import org.correomqtt.di.SoyDiException;
import org.correomqtt.ditest.complexbeans.ComplexOven;
import org.correomqtt.ditest.complexbeans.SingletonOven;
import org.correomqtt.ditest.simplebeans.FactoryMotor;
import org.correomqtt.ditest.simplebeans.FactoryVehicle;
import org.correomqtt.ditest.simplebeans.SingletonVehicle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FactoryBeanInjectionTest {

    @BeforeEach
    public void setUp() {
        SoyDi.scan("org.correomqtt.ditest");

        // Soy has no reset after test feature currently
        var vehicle = SoyDi.inject(SingletonVehicle.class);
        vehicle.setSpeed(100); // reset
    }

    @Test
    void injectDiBean_injectBean_shouldInject() {

        // arrange

        // act
        var bean = SoyDi.inject(FactoryVehicle.class);

        // assert
        assertNotNull(bean);
        assertInstanceOf(FactoryVehicle.class, bean);
        assertNotNull(bean.getMotor());
        assertInstanceOf(FactoryMotor.class, bean.getMotor());
        assertEquals(100, bean.getMotor().getSpeed());
    }

    @Test
    void injectDiBean_callMethod_shouldUpdate() {

        // arrange
        var bean = SoyDi.inject(FactoryVehicle.class);

        // act
        int speedBefore = bean.getMotor().getSpeed();
        bean.getMotor().setSpeed(130);
        int speedAfter = bean.getMotor().getSpeed();

        // assert
        assertNotNull(bean);
        assertEquals(100, speedBefore);
        assertEquals(130, speedAfter);
    }

    @Test
    void injectDiBean_twice_shouldBeDifferent() {

        // arrange
        var bean1 = SoyDi.inject(FactoryVehicle.class);
        var bean2 = SoyDi.inject(FactoryVehicle.class);

        // act
        var speedBefore1 = bean1.getMotor().getSpeed();
        var speedBefore2 = bean2.getMotor().getSpeed();

        bean1.getMotor().setSpeed(130);
        bean2.getMotor().setSpeed(140);

        var speedAfter1 = bean1.getMotor().getSpeed();
        var speedAfter2 = bean2.getMotor().getSpeed();

        // assert
        assertNotNull(bean1);
        assertInstanceOf(FactoryVehicle.class, bean1);

        assertNotNull(bean2);
        assertInstanceOf(FactoryVehicle.class, bean2);

        assertEquals(100, speedBefore1);
        assertEquals(100, speedBefore2);
        assertEquals(130, speedAfter1);
        assertEquals(140, speedAfter2);
        assertNotEquals(bean1, bean2);
    }

    @Test
    void injectDiBean_unscannedBean_shouldThrow() {

        // arrange

        // act
        var expected = assertThrows(SoyDiException.class, () -> SoyDi.inject(Math.class));

        // assert
        assertNotNull(expected);
        assertEquals("Can not inject class java.lang.Math, cause it was not scanned. ", expected.getMessage());
    }

    // complex injection tests
    @Test
    void injectComplexBean_circularReferences_shouldNotInject() {

        // arrange

        // act
        var expected = assertThrows(SoyDiException.class, () -> SoyDi.inject(ComplexOven.class));

        // assert
        assertNotNull(expected);
        // constructor injection does not allow for circular dependencies
        assertTrue(expected.getMessage().contains("Can not inject class "));
    }

    @Test
    void injectComplexSingletonBean_circularReferences_shouldNotInject() {

        // arrange

        // act
        var expected = assertThrows(SoyDiException.class, () -> SoyDi.inject(SingletonOven.class));

        // assert
        assertNotNull(expected);
        // constructor injection does not allow for circular dependencies
        assertTrue(expected.getMessage().contains("Can not inject class "));
    }

    // singleton bean tests
    @Test
    void injectSingletonBean_injectBean_shouldInject() {

        // arrange

        // act
        var bean = SoyDi.inject(SingletonVehicle.class);

        // assert
        assertNotNull(bean);
        assertNotNull(bean.getMotor());
        assertEquals(100, bean.getMotor().getSpeed());
    }

    @Test
    void injectSingletonBean_callMethod_shouldUpdate() {

        // arrange
        var bean = SoyDi.inject(SingletonVehicle.class);

        // act
        int speedBefore = bean.getMotor().getSpeed();
        bean.getMotor().setSpeed(130);
        int speedAfter = bean.getMotor().getSpeed();

        // assert
        assertNotNull(bean);
        assertEquals(100, speedBefore);
        assertEquals(130, speedAfter);
    }

    @Test
    void injectSingletonBean_twice_shouldBeSame() {

        // arrange
        var bean1 = SoyDi.inject(SingletonVehicle.class);
        var bean2 = SoyDi.inject(SingletonVehicle.class);

        // act
        int speedBefore = bean1.getMotor().getSpeed();
        bean1.getMotor().setSpeed(130);
        bean2.getMotor().setSpeed(140);
        int speedAfter = bean1.getMotor().getSpeed();

        // assert
        assertNotNull(bean1);
        assertNotNull(bean2);
        assertEquals(bean1, bean2);
        assertEquals(bean1.getMotor(), bean2.getMotor());
        assertEquals(100, speedBefore);
        assertEquals(140, speedAfter);
    }
}
