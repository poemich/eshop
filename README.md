# eshop | Advance Programming repository by Muhammad Fadhlan Karimuddin (2306245011)

### [🚀 Deployment link](https://tory-madge-poemich-3f32ba73.koyeb.app/product/)
### [🔐 SonarQube analysis](https://sonarcloud.io/project/overview?id=poemich_eshop)

## Reflection

## 1. Explain what principles you apply to your project!

### 1. Single Responsibility Principle (SRP)

SRP states that a class should have only one reason to change – in other words, it should encapsulate only one aspect of the software's functionality.

**Implemented:**
- **Car Model**: The `Car` class is a simple data holder containing properties such as `carId`, `carName`, `carColor`, and `carQuantity`. It has no additional responsibilities beyond data encapsulation.
- **CarRepository**: This class is dedicated solely to the persistence logic for Car objects. Its responsibilities include creating, reading, updating, and deleting Car records.
- **CarServiceImpl**: It encapsulates the business logic related to Car entities, for example, aggregating data from the repository and exposing these operations to the controllers.
- **Controllers**: The `CarController` and `ProductController` handle web requests and responses, ensuring that HTTP routing and interaction with the service layer are kept separate from business logic and persistence.

### 2. Open/Closed Principle (OCP)

OCP means a class or module should be easy to extend without changing its existing code.

**Modified Code to implement OCP:**
- Created an `ICarRepository` interface that defines how to work with cars.
- The `CarRepository` now implements this interface.
- `CarServiceImpl` depends on the `ICarRepository` abstraction instead of a concrete class.

### 3. Liskov Substitution Principle (LSP)

LSP means that objects of a subclass should be able to replace objects of the parent class without altering the correctness of the program.

**Modified Code to implement LSP:**
- We previously had `CarController` extend `ProductController`, which sometimes led to inconsistent behavior (like different view handling).
- We refactored the design by removing this inheritance and creating an independent `CarController`. This controller now manages car-specific actions on its own.
- If any shared logic is needed between controllers, it can be provided via separate helper services rather than through inheritance.

This change ensures that a `CarController` is not forced to follow a contract that might not suit its needs, making our codebase more robust and compliant with LSP.

### 4. Interface Segregation Principle (ISP)

ISP means that clients should not be forced to depend on methods they do not use. Instead, interfaces should be small and focused so that a client only needs to know about methods that are of interest.

**Implemented:**
- We have defined separate interfaces for our services. For example, the `CarService` interface contains only the car-related methods that the controllers need, while `ProductService` focuses on product operations.
- This approach ensures that each client interacts only with the specific methods it requires.

### 5. Dependency Inversion Principle (DIP)

DIP states that high-level modules should not depend on low-level modules; both should depend on abstractions. In our project, the CarServiceImpl originally depended directly on the concrete CarRepository. This created tight coupling and made it hard to swap persistence strategies.

**Modified Code to implement DIP:**
- Created an `ICarRepository` interface to define the required methods for working with Car objects.
- The `CarRepository` now implements `ICarRepository`.
- The `CarServiceImpl` class now depends on the `ICarRepository` abstraction rather than a concrete repository class.

## 2. Explain the advantages of applying SOLID principles to your project with examples.

Applying SOLID principles brings several benefits to the project. For example:

- **Easier Maintenance (SRP):**  
  Each class having a single responsibility allows you to modify one part without impacting others.  
  _Example:_ The `CarRepository` only handles persistence:
  ```java
  public class CarRepository {
      // Responsible solely for CRUD operations on Car objects.
      public Car create(Car car) { /* ... */ }
      // ...other CRUD methods...
  }
  ```
  
- **Better Flexibility and Extensibility (OCP & DIP):**  
  By using abstractions, high-level modules can be extended without altering existing code.  
  _Example:_ `CarServiceImpl` depends on the `ICarRepository` interface:
  ```java
  public class CarServiceImpl implements CarService {
      @Autowired
      private ICarRepository carRepository; // Flexible dependency

      public Car create(Car car) {
          return carRepository.create(car);
      }
      // ...other methods...
  }
  ```
  This design allows swapping `CarRepository` with a different implementation without changing `CarServiceImpl`.

- **Improved Testability (DIP & ISP):**  
  Decoupled components enable easier unit testing by allowing mocks of specific interfaces.  
  _Example:_ In tests, you can mock `ICarRepository`:
  ```java
  when(carRepository.create(any(Car.class))).thenReturn(mockCar);
  ```
  
- **Robust and Predictable Design (LSP & ISP):**  
  With clear contracts defined by focused interfaces, substituting different implementations causes fewer surprises.  
  _Example:_ A controller expects a service interface:
  ```java
  @Autowired
  private CarService carService; // Uses only defined methods of the interface
  ```
  This ensures any compliant implementation works correctly with the controller.

### 3. Explain the disadvantages of not applying SOLID principles to your project with examples.

Not applying SOLID principles can lead to several issues in a project. For example:

- **Tight Coupling (DIP Violation):**  
  When high-level modules depend on concrete implementations, changes in low-level modules force changes throughout the code.  
  _Example:_
  ```java
  // Without DIP, CarServiceImpl depends directly on a concrete CarRepository.
  public class CarServiceImpl implements CarService {
      @Autowired
      private CarRepository carRepository; // Tightly coupled

      public Car create(Car car) {
          return carRepository.create(car);
      }
      // ...
  }
  // This design makes it hard to swap out or extend the persistence layer.
  ```

- **Difficult to Extend (OCP Violation):**  
  Without using abstractions or interfaces, adding new behavior may require altering existing classes rather than simply extending them.  
  _Example:_
  ```java
  // A concrete CarRepository with no extension points.
  public class CarRepository {
      public Car create(Car car) { /* ... */ }
      // If new persistence logic is needed, you must change this class directly.
  }
  ```

- **Reduced Testability (ISP/DIP Issues):**  
  When classes are tightly coupled or have broad interfaces, unit testing becomes more complex because it is difficult to isolate modules.  
  _Example:_
  ```java
  // Without proper interface segregation, a test must handle unnecessary methods.
  public interface GeneralService {
      void processCar(Car car);
      void processProduct(Product product);
  }
  // Testing Car functionality forces you to stub or implement product methods.
  ```

- **Unreliable Substitutability (LSP Violation):**  
  If subclasses do not strictly follow the contracts set by parent classes, substituting one for the other can result in unpredictable behavior.  
  _Example:_
  ```java
  // If CarController improperly extends ProductController
  public class CarController extends ProductController {
      @Override
      public String create() {
          // Returns a view name that does not match the ProductController's contract,
          // causing failures in parts of the system expecting a consistent behavior.
          return "unexpectedView";
      }
  }
  ```
  
These issues make the codebase rigid, difficult to maintain, and challenging to test, ultimately slowing development and increasing the risk of bugs.