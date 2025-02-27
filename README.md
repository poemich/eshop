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

**Implemented:**
- Created an `ICarRepository` interface that defines how to work with cars.
- The `CarRepository` now implements this interface.
- `CarServiceImpl` depends on the `ICarRepository` abstraction instead of a concrete class.
