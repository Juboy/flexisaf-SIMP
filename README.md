## flexisaf-SIMP
Student Information Management portal

Use `mwnw spring-boot:run` to start the application

If you have problems starting the application, you might need to change the port in which the application runs on. The application.properties file is located on *src/main/resources* folder.
You can change project configuration like server port and mail client configurations and database properties.

After successfully starting the service, you can view the endpoints with url `localhost:{port}/swagger-ui.html`
it contains endpoints to:
- Add students
- Update students record
- Find All Students
- Search for students using all criterias
- Delete Students by Id etc

The service also send mails to students on their birthdays.
The service does not include Authentication as it wasn't specified in the scope.

The service also bootstraps the department database and add two records on first startup.

Unit tests can be found in the *src/test* folder. The test properties which contains configuration for H2 in-memory database for repository layer tests.

The service relies heavily on lombok. So Annotation processing needs to be enabled on your IDE.