# Bank Account Kata

We need to develop a java based application with some features could be done into our bank account like deposit, withdrawal, printing balance and also showing transactions history.

## Requirements

The expected result is a Spring Boot application, and its underlying implementation, that meets the expressed needs. Some enhancements have been done dealing with bonus features like the following :

- REST API
- Customers & Accounts
- Persistence
- Swagger UI to interact with API resources

## User Stories

##### US 1:
As a bank, deposit money from a customer to his account, is allowed when superior to €0.01

##### US 2:
As a bank, withdraw money from a customer account, is allowed when no overdraft used

##### US 3:
As a bank, a customer can display its account balance

##### US 4:
As a bank, a customer can display its account transactions history

## Technical environment
- Spring Boot 2.2.6 (Data, MVC)
- Java 11.
- H2 embedded database
- Lombok
- Swagger UI
- Junit5/ AssertJ 3.13.2

## Demonstration 

The application is introduced using java 11 and Spring Boot 2.2. And the project has been built initially with Maven. 
So first, you need to be sure that maven is already installed and fully configured (with java 11 as java home). 
Second, be sure also that the plugin Lombok is installed properly in your IDE environment. You could follow the official website for lombok for further information about installation: https://projectlombok.org/setup/

Finally, you could run the application via BankAccountKataApplication.java class or you generate the jar with _'mvn clean package'_ command and you can build it and run it from the command line under target. 

By default, the application is defined with context path bankAcount and port 8081. You could change the configuration in the application.yaml file in the resources package. And following typed configuration, the URL of the application (main URL) is: http:// < address >:< port >/bankAccount
You could after that access to: 

- the H2 database using the following URL:
MainURL/h2-console

- Swagger UI that help you to interact and visualize the API resources using the following URL:
MainURL/swagger-ui.html



