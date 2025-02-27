# eshop | Advance Programming repository by Muhammad Fadhlan Karimuddin (2306245011)

### [🚀 Deployment link](https://tory-madge-poemich-3f32ba73.koyeb.app/product/)
### [🔐 SonarQube analysis](https://sonarcloud.io/project/overview?id=poemich_eshop)

## Reflection

## 1. Reflection on SOLID Principles

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
