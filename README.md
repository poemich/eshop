# eshop | Advance Programming repository by Muhammad Fadhlan Karimuddin (2306245011)

### [🚀 Deployment link](https://tory-madge-poemich-3f32ba73.koyeb.app/product/)
### [🔐 SonarQube analysis](https://sonarcloud.io/project/overview?id=poemich_eshop)

## Reflection

### 1. List the code quality issue(s) that you fixed during the exercise and explain your strategy on fixing them.


Using SonarCloud, several major code quality issues were identified and fixed. One of the issues was the grouping of dependencies by their destination in the build file. This was addressed by organizing the dependencies into logical groups, making the build file more readable and maintainable. Another significant issue was the use of field injection, which was replaced with constructor injection to improve the testability and reliability of the code. Constructor injection is generally preferred over field injection as it makes the dependencies explicit and helps in writing unit tests more easily.


The strategy to address these issues involved running tests with JaCoCo to identify uncovered methods and adding unit tests to those methods. This ensured that the code had sufficient test coverage and that any changes made did not introduce new bugs. Additionally, SonarCloud was used to identify code quality issues, which were then systematically fixed. By following the recommendations from SonarCloud, the codebase was made more maintainable and easier to understand. The use of constructor injection, for example, not only improved the code quality but also made the code more robust and easier to test.

### 2. Look at your CI/CD workflows (GitHub)/pipelines (GitLab). Do you think the current implementation has met the definition of Continuous Integration and Continuous Deployment? Explain the reasons (minimum 3 sentences)!

The current CI/CD implementation meets the definition of Continuous Integration and Continuous Deployment. The workflow is designed to automatically run tests and analyze code quality on every push and pull request, ensuring that the codebase remains stable and maintainable. This continuous feedback loop helps in catching issues early in the development process, reducing the chances of bugs making it to production. Additionally, the use of caching mechanisms for SonarQube and Gradle packages improves the efficiency of the build process, making the CI/CD pipeline faster and more reliable. The integration with SonarCloud ensures that code quality is continuously monitored and maintained, providing developers with actionable insights to improve their code.
