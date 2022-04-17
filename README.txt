# Title: Appointment Scheduling Application
    This application can be used to schedule appointments using a GUI and can also generate reports about the appointments,
    the contacts, and the customers. The application allows a user to view appointments, view customers, and modify both as well.
    The program also keeps a log file of all login attempts to the application.

Author: Samuel Faubert
faubertsamuel@gmail.com
11 April 2022
Version: 1.0

IDE: IntelliJ Community Edition 2021.3.3
JDK: 17.0.2
JavaFX-SDK-18
mysql-connector-java 8.0.28

The additional report in the reports section gives a count of appointments grouped by country.
As the business has offices in multiple countries, the report will display the count of appointments for each country.

Running the application: The application can be executed by opening the src folder in a Java IDE such as IntelliJ or Eclipse.
The Java SDK and Java FX SDK are required, as is a MySQL database with an administrative user.
It is required to add the following command in the Application configuration's VM options:
--module-path ${PATH_TO_FX} --add-modules javafx.fxml,javafx.controls,javafx.graphics
Once the pre-requisites have been installed, the run button in the IDE can be clicked to execute the program.
The application can be tested with the user test:test.


