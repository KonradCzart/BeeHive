# HivePoint

RESTful application for beekeepers and their employees allowing them to manage their apiaries, look after their bees' health and control their business's finances.

# How to run it
1. Clone the repo and import it as maven project
2. Install MySql Server and set password as authentication method 
2. Go to  BeeHive/JBeeSoft BeeHive/src/main/resources/application.properties. It's file containing server configuration.
```
spring.datasource.url= jdbc:mysql://localhost:3306/beehiveDB
spring.datasource.username= root
spring.datasource.password= root123
```
Create database named beehiveDB with root's password root123.

3. Build project with maven and run is as Java Application (entry point for appliaction is BeeHiveApplication class)
4. Perform this two insertions on created database
```
INSERT INTO roles(name) VALUES('ROLE_USER');
INSERT INTO roles(name) VALUES('ROLE_ADMIN');
```
5. Try register and login to your account with Postman.

# Adding a new Apiary
* choosing localisation on map - Weather module warns, if unpleasant weather conditions in the area are a threat to bees in that Apiary and you should not perform a survey on those Hives

# Adding a new Hive to existing Apiary
* choosing a race of mother's bees (choose a race from the database, where its attributes are stored)
* bee mother's date of birth | last date when she laid eggs
* Hive type (Warszawski/Wielkopolski, corpuses amount)
* starting power

# Commiting to a Hive
* survey
* honey collection

