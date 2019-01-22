# BeeHive

RESTful application for beekeepers and their employees allowing them to manage their apiaries, look after their bees' health and control their business's finances.

# How to run it
1. Clone the repo and import it as maven project
2. Install MySql Server and set password as authentication method 
3. Go to  BeeHive/JBeeSoft BeeHive/src/main/resources/application.properties. It's file containing server configuration.
```
spring.datasource.url= jdbc:mysql://localhost:3306/beehiveDB
spring.datasource.username= root
spring.datasource.password= root123
```
Create database named beehiveDB with root's password root123.

4. Build project with maven and run is as Java Application (entry point for appliaction is BeeHiveApplication class)
5. Run this script on beehiveDB
```
INSERT INTO roles(name) VALUES('ROLE_USER');
INSERT INTO roles(name) VALUES('ROLE_ADMIN');

INSERT INTO privileges(name, readable_name, description) VALUES('OWNER_PRIVILEGE', 'Apiary Owner', 'Right to grant privileges for owned apiary');
INSERT INTO privileges(name, readable_name, description) VALUES('HIVE_EDITING', 'Hive actions', 'Right to perform actions like honey collectiong or feeding');
INSERT INTO privileges(name, readable_name, description) VALUES('APIARY_EDITING', 'Apiary actions', 'Right to remove/add/modify hives in apiary');
INSERT INTO privileges(name, readable_name, description) VALUES('HIVE_STATS_READING', 'Hive statistics', 'Right to read statistics of each hive in apiary');
INSERT INTO privileges(name, readable_name, description) VALUES('APIARY_STATS_READING', 'Apiary statistics', 'Right to read general statistics of whole apairy.');
```
6. Try register and login to your account with Postman. (requests examples can be found on slack)

# Backend API endpoints manual
While the server is running, you may interact with it in the following ways:
1. sign up through URL `localhost:5000/api/auth/signup` with the following POST form:
```
{
  "name": "john smith",
  "username": "thejohny1993",
  "email": "thejohny1993@example.com",
  "password": "myweakpassword123"
}
```
2. sign in through URL `localhost:5000/api/auth/signin` with the following POST form:
```
{
  "usernameOrEmail": "thejohny1993@example.com",
  "password": "myweakpassword123"
}
```
3. check, whether an email is taken, through `localhost:5000/api/user/isAvailable/email/{GIVEN_EMAIL}` (GET); returns JSON:
```
{
  "isAvailable": "true"
}
```
4. check, whether a username is taken, through `localhost:5000/api/user/isAvailable/username/{GIVEN_USERNAME}` (GET); returns JSON:
```
{
  "isAvailable": "true"
}
```
5. get user's data suited for userpanel through `localhost:5000/api/user/me` (GET) (signed-in-user's session token in request's header); returns JSON:
```
{
  "id": 1,
  "username": "janek12",
  "name": "Jan Kowalski",
  "email": "janek@wp.pl"
}
```
6. create new apiary through `localhost:5000/api/apiary/new` (POST) (signed-in-user's session token in request's header); JSON body:
```
{
  "name": "Pasieka11",
  "country": "Poland",
  "city": "Kraków"
}
```
7. get associaded apiaries list through `localhost:5000/api/apiary/me` (GET) (signed-in-user's session token in request's header); returns JSON:
```
{
    "ownedApiaries": [
        {
            "id": 2,
            "name": "Pasieka2",
            "country": "Poland",
            "city": "Kraków",
            "hiveNumber": 0
        },
        {
            "id": 6,
            "name": "Pasieka11",
            "country": "Poland",
            "city": "Kraków",
            "hiveNumber": 0
        }
    ],
    "otherApiaries": [
        {
            "id": 1,
            "name": "Pasieka1",
            "country": "Poland",
            "city": "Kraków",
            "hiveNumber": 0
        }
    ]
}
```
8. grant privileges for owned appiary through `localhost:5000/api/privileges/grant` (POST) (signed-in-user's session token in request's header); JSON body:
```
{
	"targetUser": 2,
	"privileges": 
	[
		"HIVE_EDITING",
		"OWNER_PRIVILEGE"
	],
	"affectedApiaryId": 2
}
```
9. add new hive to apiary through `localhost:5000/api/hive/new` (POST) (signed-in-user's session token in request's header); JSON body:
```
{
	"name": "ul4",
	"apiaryId": 2,
	"hiveTypeId": 1,
	"boxNumber": 5
}
```
10. perform action on hives through `localhost:5000/api/action/feeding/{apiary_id}` (POST) (signed-in-user's session token in request's header); JSON body:
```
{
	"affectedHives": 
	[
		1,
		2
	],
	"feedType": "Bezglutenowy Chleb",
	"feedAmount": 2.5,
	"price": 3.99
}
```
# Adding a new Apiary
* choosing localisation on map - Weather module warns, if unpleasant weather conditions in the area are a threat to bees in that Apiary and you should not perform a survey on those Hives

# Adding a new Hive to an existing Apiary
* choosing a race of mother's bees (choose a race from the database, where its attributes are stored)
* bee mother's date of birth | last date when she laid eggs
* Hive type (Warszawski/Wielkopolski, corpuses amount)
* starting power

# Commiting to a Hive
* survey
* honey collection

_developer branch_
