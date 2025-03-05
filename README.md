# eshop | Advance Programming repository by Muhammad Fadhlan Karimuddin (2306245011)

### [🚀 Deployment link](https://tory-madge-poemich-3f32ba73.koyeb.app/product/)
### [🔐 SonarQube analysis](https://sonarcloud.io/project/overview?id=poemich_eshop)

## Reflection

### 1. Reflect based on Percival (2017) proposed self-reflective questions (in “Principles and Best Practice of Testing” submodule, chapter “Evaluating Your Testing Objectives”), whether this TDD flow is useful enough for you or not. If not, explain things that you need to do next time you make more tests.

Yes, this **TDD workflow** is useful:

#### **1. Correctness**  
The TDD flow helped ensure correctness in my application.  
- Functional tests confirmed that the main features worked from a user's perspective.  
- Edge cases were covered in unit tests, but I could add more negative test cases, such as invalid inputs.  
- Integration between components was tested, but more integration tests might be needed to ensure all parts work together correctly.  

**What can be improved?**  
- Add more integration tests to verify that different modules interact correctly.  
- Test more edge cases to prevent unexpected errors.  

#### **2. Maintainability**  
The tests provided confidence when refactoring because they:  
- Caught unexpected side effects when code was modified.  
- Helped enforce a cleaner and modular code structure by focusing on testability.  

However, there were some challenges:  
- Some tests were too tightly coupled to implementation details, making them break too easily when refactoring.  
- There was a heavier reliance on integration tests rather than unit tests, which sometimes made debugging harder.  

**What can be improved?**  
- Write more unit tests to verify individual functions and classes.  
- Reduce overly detailed tests that fail due to small code changes.  

#### **3. Productive Workflow**  
TDD improved productivity by providing early feedback and reducing debugging time.  
- Running a subset of tests helped focus on specific issues without waiting for the entire test suite.  

However, there were some inefficiencies:  
- Some tests, especially integration tests, took too long to run.  
- Running all tests every time slowed down development.  

**What can be improved?**  
- Optimize slow tests by mocking database or external dependencies.  
- Use parallel testing or selective test execution for faster feedback.  

### **Conclusion**  
TDD has been useful for correctness, maintainability, and productivity. In future implementations, improving test coverage for edge cases, refining test design, and optimizing test execution speed will further enhance the workflow.

### 2. You have created unit tests in Tutorial. Now reflect whether your tests have successfully followed F.I.R.S.T. principle or not. If not, explain things that you need to do the next time you create more tests.

Yes, the test have followed the **F.I.R.S.T.** principle:
- **Fast** – My tests run quickly, but I could optimize some database-related tests by mocking them.  
- **Independent** – Each test is separate, so one failing test does not affect others.  
- **Repeatable** – The tests run consistently across different environments.  
- **Self-Validating** – Each test clearly states pass/fail conditions.  
- **Thorough** – I covered both positive and negative cases, but I could improve by adding more edge cases.  

**What to improve?**  
🔹 I will **ensure more negative test cases** (invalid inputs, edge conditions).  
🔹 I will **mock database calls more efficiently** to avoid slow test execution.  
