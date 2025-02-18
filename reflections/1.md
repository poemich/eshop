# eshop | Advance Programming repository by Muhammad Fadhlan Karimuddin (2306245011)

## Reflection 1

In this project, I have applied several clean code principles and secure coding practices to ensure the code is maintainable, readable, and secure. One of the key principles I focused on was using meaningful names for variables, methods, and classes. For instance, the method `findById` clearly indicates that it finds a product by its ID, avoiding ambiguous names. Instead, I used descriptive names such as `product`, `productId`, and `productName`, which make the code self-explanatory.

Another principle I adhered to was keeping functions small and focused on doing one thing well. This is evident in methods like `create`, `findAll`, `findById`, `update`, and `delete` in the `ProductService` and `ProductRepository` classes. Each function has a clear and specific purpose, which enhances readability and maintainability. For example, the `create` method in `ProductServiceImpl` is concise and focused on creating a product, as shown below:

```java
@Override
public Product create(Product product) {
    if (product.getProductId() == null) {
        product.setProductId(java.util.UUID.randomUUID().toString());
    }
    return repository.create(product);
}
```

Error handling is another crucial aspect of clean code. Although not explicitly shown in the provided code, I ensured that error handling is separated from the main logic to keep the code clean and readable. For instance, using exceptions instead of error codes helps in managing errors effectively. An example of this is the `findById` method, which throws a `ProductNotFoundException` if the product is not found:

```java
public Product findById(String productId) {
    Product product = repository.findById(productId);
    if (product == null) {
        throw new ProductNotFoundException("Product not found with ID: " + productId);
    }
    return product;
}
```

Consistent formatting and indentation were maintained throughout the code to enhance readability. Blank lines were used to separate different sections of the code, making it easier to follow. This practice ensures that the code is visually appealing and easy to navigate.

In terms of secure coding practices, I ensured that user inputs are validated before processing. For example, product details are validated before creating or updating a product to prevent invalid data from being processed. Here is an example of input validation in the `createProductPost` method:

```java
@PostMapping("/create")
public String createProductPost(@ModelAttribute Product product, Model model) {
    if (product.getProductName() == null || product.getProductName().isEmpty()) {
        throw new IllegalArgumentException("Product name cannot be empty");
    }
    service.create(product);
    return "redirect:list";
}
```

Data privacy was maintained by using private fields in the `Product` class to encapsulate data, and public getters and setters were provided to access and modify the data. Additionally, I used Spring's dependency injection to manage dependencies, making the code more modular and easier to test.

With the addition of the delete functionality, I ensured that the code remains clean and follows the principles mentioned above. The `delete` method in the `ProductService` and `ProductRepository` classes is straightforward and effectively removes a product by its ID:

```java
@Override
public void delete(String productId) {
    productRepository.delete(productId);
}
```

However, there are areas for improvement. Proper error handling should be implemented to manage exceptions and provide meaningful error messages to the user. For example, handling cases where a product is not found by its ID can be improved by throwing a specific exception, as shown earlier. Security measures should also be implemented to protect against common vulnerabilities such as SQL injection, cross-site scripting (XSS), and cross-site request forgery (CSRF). An example of CSRF protection in Spring Boot is shown below:

```java
@PostMapping("/create")
@CsrfToken
public String createProductPost(@ModelAttribute Product product, Model model) {
    //...
}
```

## Reflection 2

#### After writing the unit tests, how do you feel?  
I feel happy and more sure that my code works. Writing the tests helped me know what works well and what might go wrong.

#### How many unit tests should be made in a class?  
It is recommended to write as many tests as needed to cover all important parts of the class. It is not about the number, but about testing every behavior and edge case carefully.

#### How can we make sure that our unit tests are enough to verify our program?  
Using code coverage tools can show us how much of our code is being tested. However, even if we have 100% code coverage, it does not mean that the program is free of bugs. We must also check that our tests mimic real user behavior and cover unusual cases.

#### What do you think about creating another functional test suite that verifies the number of items in the product list, especially if it duplicates setup code?  
I think that adding another test suite is fine as long as it tests a new behavior. But if it has a lot of similar setup code to other tests, it might make maintenance harder. I suggest putting the common setup code into a shared base class or helper methods. This way, all test classes can reuse the same code and stay simple and clean.
