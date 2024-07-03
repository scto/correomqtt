package org.correomqtt.di;

import org.correomqtt.di.complexbeans.ComplexOven;
import org.correomqtt.di.complexbeans.SingletonOven;
import org.correomqtt.di.simplebeans.DirectVehicle;
import org.correomqtt.di.simplebeans.SingletonVehicle;
import org.correomqtt.ditest.simplebeans.FactoryVehicle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DirectBeanInjectionTest {

    @BeforeEach
    public void setUp() {
        SoyDi.scan("org.correomqtt.di");
        SoyDi.scan("org.correomqtt.ditest");

        // reset, soydi has currently no way to remove beans
        var vehicle = SoyDi.inject(SingletonVehicle.class);
        vehicle.setSpeed(100);
    }

    @Test
    void injectDiBean_injectBean_shouldInject() {

        // arrange

        // act
        var bean = SoyDi.inject(DirectVehicle.class);

        // assert
        assertNotNull(bean);
        assertNotNull(bean.getMotor());
        assertEquals(100, bean.getMotor().getSpeed());
    }

    @Test
    void injectDiBean_callMethod_shouldUpdate() {

        // arrange
        var bean = SoyDi.inject(DirectVehicle.class);

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
    void injectDiBean_unscannedBean_shouldThrow() {

        // arrange

        // act
        var expected = assertThrows(SoyDiException.class, () -> SoyDi.inject(Math.class));

        // assert
        assertNotNull(expected);
        assertEquals("Can not inject class java.lang.Math, cause it was not scanned. ", expected.getMessage());
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

    // complex direct bean injection
    @Test
    void injectComplexBean_circularReferences_shouldNotInject() {

        // arrange

        // act
        var expected = assertThrows(SoyDiException.class, () -> SoyDi.inject(ComplexOven.class));

        // assert
        assertNotNull(expected);
        // constructor injection does not allow for circular dependencies
        assertTrue(expected.getMessage().contains("Detected dependency cycle: "));
    }

    @Test
    void injectComplexSingletonBean_circularReferences_shouldNotInject() {

        // arrange

        // act
        var expected = assertThrows(SoyDiException.class, () -> SoyDi.inject(SingletonOven.class));

        // assert
        assertNotNull(expected);
        // constructor injection does not allow for circular dependencies
        assertTrue(expected.getMessage().contains("Detected dependency cycle: "));
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
