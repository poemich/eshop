package id.ac.ui.cs.advprog.eshop.service;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.repository.CarRepository;
import java.lang.reflect.Field;
import java.util.List;

public class CarServiceImplTest {

    private CarServiceImpl getServiceWithRepo() throws Exception {
        CarServiceImpl service = new CarServiceImpl();
        CarRepository repo = new CarRepository();
        Field field = service.getClass().getDeclaredField("carRepository");
        field.setAccessible(true);
        field.set(service, repo);
        return service;
    }

    @Test
    public void testCreateAndFindById() throws Exception {
        CarServiceImpl service = getServiceWithRepo();
        Car car = new Car();
        car.setCarName("TestCar");
        Car created = service.create(car);
        assertNotNull(created.getCarId());
        Car found = service.findById(created.getCarId());
        assertEquals("TestCar", found.getCarName());
    }

    @Test
    public void testFindAll() throws Exception {
        CarServiceImpl service = getServiceWithRepo();
        Car car1 = new Car();
        car1.setCarName("Car1");
        Car car2 = new Car();
        car2.setCarName("Car2");
        service.create(car1);
        service.create(car2);
        List<Car> list = service.findAll();
        assertEquals(2, list.size());
    }

    @Test
    public void testUpdate() throws Exception {
        CarServiceImpl service = getServiceWithRepo();
        Car car = new Car();
        car.setCarName("OldName");
        car.setCarColor("Blue");
        car.setCarQuantity(1);
        Car created = service.create(car);
        Car updateCar = new Car();
        updateCar.setCarName("NewName");
        updateCar.setCarColor("Green");
        updateCar.setCarQuantity(2);
        service.update(created.getCarId(), updateCar);
        Car updated = service.findById(created.getCarId());
        assertEquals("NewName", updated.getCarName());
    }

    @Test
    public void testDeleteCarById() throws Exception {
        CarServiceImpl service = getServiceWithRepo();
        Car car = new Car();
        car.setCarName("ToDelete");
        Car created = service.create(car);
        service.deleteCarById(created.getCarId());
        assertNull(service.findById(created.getCarId()));
    }
}
