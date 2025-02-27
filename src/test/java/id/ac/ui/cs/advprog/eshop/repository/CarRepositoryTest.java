package id.ac.ui.cs.advprog.eshop.repository;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import id.ac.ui.cs.advprog.eshop.model.Car;
import java.util.Iterator;

public class CarRepositoryTest {
    @Test
    public void testCreateAndFindById() {
        CarRepository repo = new CarRepository();
        Car car = new Car();
        car.setCarName("TestCar");
        car.setCarColor("Red");
        car.setCarQuantity(1);
        Car created = repo.create(car);
        assertNotNull(created.getCarId());
        Car found = repo.findById(created.getCarId());
        assertEquals("TestCar", found.getCarName());
    }

    @Test
    public void testFindAll() {
        CarRepository repo = new CarRepository();
        Car car1 = new Car();
        car1.setCarName("Car1");
        Car car2 = new Car();
        car2.setCarName("Car2");
        repo.create(car1);
        repo.create(car2);
        Iterator<Car> it = repo.findAll();
        int count = 0;
        while(it.hasNext()){
            it.next();
            count++;
        }
        assertEquals(2, count);
    }

    @Test
    public void testUpdate() {
        CarRepository repo = new CarRepository();
        Car car = new Car();
        car.setCarName("OldName");
        car.setCarColor("Blue");
        car.setCarQuantity(5);
        Car created = repo.create(car);
        Car updateCar = new Car();
        updateCar.setCarName("NewName");
        updateCar.setCarColor("Green");
        updateCar.setCarQuantity(10);
        Car updated = repo.update(created.getCarId(), updateCar);
        assertEquals("NewName", updated.getCarName());
        assertEquals("Green", updated.getCarColor());
        assertEquals(10, updated.getCarQuantity());
    }

    @Test
    public void testDelete() {
        CarRepository repo = new CarRepository();
        Car car = new Car();
        car.setCarName("ToDelete");
        Car created = repo.create(car);
        repo.delete(created.getCarId());
        assertNull(repo.findById(created.getCarId()));
    }

    @Test
    public void testUpdateNotFound() {
        // Test update branch when the car is not found.
        CarRepository repo = new CarRepository();
        Car updateCar = new Car();
        updateCar.setCarName("NonExisting");
        assertNull(repo.update("non-existent", updateCar));
    }

    @Test
    public void testFindByIdNotFound() {
        // Test findById returns null when no car is found.
        CarRepository repo = new CarRepository();
        assertNull(repo.findById("non-existent"));
    }

    @Test
    public void testDeleteNonExisting() {
        // Test delete does not throw exception when the car is not found.
        CarRepository repo = new CarRepository();
        repo.delete("non-existent");
        Iterator<Car> it = repo.findAll();
        int count = 0;
        while (it.hasNext()) {
            it.next();
            count++;
        }
        assertEquals(0, count);
    }

    // New tests to increase branch coverage

    @Test
    public void testUpdateMultipleCars() {
        CarRepository repo = new CarRepository();
        Car car1 = new Car();
        car1.setCarName("Car1");
        Car car2 = new Car();
        car2.setCarName("Car2");
        repo.create(car1);
        Car created2 = repo.create(car2); // second car created
        // Update only the second car after a failed check on the first one.
        Car updateCar = new Car();
        updateCar.setCarName("UpdatedCar2");
        updateCar.setCarColor("Yellow");
        updateCar.setCarQuantity(3);
        Car updated = repo.update(created2.getCarId(), updateCar);
        assertNotNull(updated);
        assertEquals("UpdatedCar2", updated.getCarName());
    }

    @Test
    public void testCreateWithExistingId() {
        CarRepository repo = new CarRepository();
        Car car = new Car();
        // Manually set an id so that the "if null" branch is skipped.
        car.setCarId("manual-id");
        car.setCarName("ManualCar");
        repo.create(car);
        Car found = repo.findById("manual-id");
        assertNotNull(found);
        assertEquals("manual-id", found.getCarId());
    }

    @Test
    public void testFindByIdMultiple() {
        CarRepository repo = new CarRepository();
        Car car1 = new Car();
        car1.setCarName("Car1");
        Car created1 = repo.create(car1);
        
        Car car2 = new Car();
        car2.setCarName("Car2");
        Car created2 = repo.create(car2);
        
        // Attempt to find the second car (skipping the first one)
        Car found = repo.findById(created2.getCarId());
        assertNotNull(found);
        assertEquals(created2.getCarId(), found.getCarId());
    }
}
