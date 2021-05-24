Title: "Appointment System I" 

***********************************************Purpose***************************************************************************************** 

A GUI-based desktop JavaFXML application that allows users to manage the creation and deletion of customers and appointments.
Each customer's appointment is assigned to a contact. The contacts are responsible to manage all appointments that are assigned to them. 
The application will prevent users from creating appointments that are outside of business hour and prevent any appointment time overlaps.
The logged in user will get a notification when there are upcoming appointments within 15 minutes. Customers can come from other countries.
The login page changes display languages between English/French based on the user computer's locale setting. Various reports are also available.

***********************************************************************************************************************************************

Author: Yong Liang
Contact: yongliang24@gmail.com
Application Version: 1.0.0
Date: 5/23/2021


IDE Version: Apache NetBeans IDE 11.0 
JDK Version: Java SE jdk-11.0.10
JavaFX Version: javafx-sdk-11.0.2
MySQL Connector driver version: mysql-connector-java-8.0.22


**************************************How to Run the Program**********************************************************************************
1.Login Page - Available username/password for testing: test/test, admin/admin.

2.Customer Page:
	Create- Creates a new customer.(Selecting a country will enable division selection)
	Update- Updates a customer.(Select a customer from tableview to be updated)
	Delete- Deletes a selected customer. (All appointments that belong to this customer will also be deleted)
	Appointment- Opens up the appointment page.
	Report- Opens up the report page.

3.Appointment Page:
	Filter- Filters appointments by current month/week/all.
	Create- Create a new appointment. (The hours and minutes from combobox is user's local time, it checks against office hour in EST time).
	Edit- Makes changes to a selected appointment.(When date and time do not change, overlap will not occur. All other validations still apply)
	Delete- Deletes a selected appointment.
The message "Available Hours from current timezone" changes the hours based on user's local timezone to assist select hours within officer hour in ETS.

4.Report Page: 
	Contact's appointment schedule- a combobox appears when this radio button is selected, please select a contact from the combobox to view the report.
	Custoner by country and division- a combox appears when this radio button is selected, please select a country from the combobox to view the report.

5.Other information:
	login_activity.txt is located in the main directory of the project.
	Javadoc index.html is located in the dist folder.
	
**************************************Description of Additional Report************************************************************************
My additional report is to display how many customers in each division from each country. The user is able to select a country from a combo box.
Any divisions from the selected country that has a customer will be displayed in a tableview with an indicating count. 
