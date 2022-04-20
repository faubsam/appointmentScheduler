Title: Appointment Scheduling Application
    This application can be used to schedule appointments using a GUI and can also generate reports about the appointments,
    the contacts, and the customers. The application allows a user to view appointments, view customers, and modify both as well.
    The program also keeps a log file of all login attempts to the application.


Author: Samuel Faubert
faubertsamuel@gmail.com
11 April 2022
Version: 1.0


IDE: IntelliJ Community Edition 2021.3.3
JDK: 17.0.2
JavaFX-SDK-17
mysql-connector-java 8.0.25


The additional report in the reports section gives a count of appointments grouped by country.
As the business has offices in multiple countries, the report will display the count of appointments for each country.


Running the application
--------------------------------------------------------
The application can be executed by opening the src folder in a Java IDE such as IntelliJ or Eclipse.
The Java SDK and Java FX SDK are required, as is a MySQL database with an administrative user.
The following settings need to be applied to the project in the IDE
- add the path to the JavaFX lib directory in the path variables
- add the JavaFX/lib directory and the MySQL connector jar file to the project's libraries 
- select the correct SDK for Java
It is required to add the following command in the Application configuration's VM options:
--module-path ${PATH_TO_FX} --add-modules javafx.fxml,javafx.controls,javafx.graphics
Once the pre-requisites have been installed, the run button in the IDE can be clicked to execute the program.
The appointments view displays all appointments, appointments by week starting on the previous Sunday at midnight until the next Saturday at 23:59 in the user's local time.
Appointments view by month show from the first day of the month at midnight until the last day of the month at 23:59 in the user's local time.
The application can be tested with the user test:test.


