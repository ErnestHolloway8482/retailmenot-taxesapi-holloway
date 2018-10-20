# Summary

The sales tax database contains roughly 40K entries based on the data pulled and seeded from Avalara for all 52 states within the U.S. The API allows the user to enter a zip code and retrieve the sales tax information for that zip code in the form of a JSON payload that can easily be integrated into their shipping software.

# Build Instructions

The project was built with the community edition of IntelliJ and built with compiled with Java8. 

1)	Make sure to go to https://www.jetbrains.com/idea/ to download the community edition of IntelliJ for your specific platform.
2)	Make sure to download Java8 or higher from http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html for your specific platform.
3)	Follow the directions from the download pages to properly install Java8 or higher and IntelliJ community edition.
4)	Pull the software project from GitHub at https://github.com/ErnestHolloway8482/retailmenot-taxesapi-holloway.git. It is available as public repository and shouldn’t require any credentials to pull down the software.
5)	Open up the project within IntelliJ.
6)	Run the SalesTaxAPIApplicaiton.java class located in the applications package of the project. You’ll see the typically Spring Boot configuration output in the console logs. Once you are complete hit the stop button to end the execution of the software. Once the software stops running from within IntelliJ you’ll no longer have access to the API calls.
7)	Once the application is running open up your favorite browser and execute the API commands for a local host as defined below in the directions on working with the REST based API.

# Limitations of the API

* For the purposes of this coding exercise there is no concept of auto updating for new sales tax information that is available.
* Since this is my first time ever writing my own backend API service this has not been deployed properly to AWS, Azure, Digital Ocean, etc. for hosting the API. As a result, this can only be tested on a local host for now. If given more time, I would at least deploy this as a public API service utilizing Digital Ocean.
* I took the approach of writing the software first as a normal client side Java application and then bolted on the Spring framework afterwards to turn the client app into a server side app. As a result, I made the architectural decision to utilize an embedded database called ObjectDB https://www.objectdb.com/ instead of utilizing something more complex such as Hibernate, Mongo DB etc. This was simpler to me as I assumed the service would be deployed on a simple machine with adequate resource where I didn’t need a data service. As a result, all of the unit and integration tests can be executed locally by running the entire suite of files in the java test package framework that is within the IntelliJ project. I did not provide any testing for the Spring RestController additions since I’ve essentially already tested the correct operation of the framework with my normal suite of application tests.
* When the software is run on the local host for the very first time and in between shutdowns, the initial API response will take time to complete depending on the hardware used because it has to first delete the old seeded sales tax data and place over 40K entries into the database. To avoid this slowdown simply keep the service running. This approach was taken to avoid any side affects in the event the database was corrupted in between shutdown and startup cycles.
* For the sake of time advanced error handling on the RestController side of things has not been implemented. So, for now if something goes wrong it will default to a displaying a 404 page. With additional time, I would be able to go back and properly use Spring’s API response class and properly define the JSON responses that should come back. At a bare minimum, I do at least provide an API response in the event that the zip code entered does not return the sales tax information.
* I did not go overboard with documentation. At a bare minimum each class will have a brief description on what it does and what it is used for. Simple getter/setter method for database models are not documented, and Module classes method utilized for dependency injection have not been documented as well. This will become apparent when viewing the code.
* In the event that you are running the software and run into an issue where no responses are coming back for an entered zip code then delete the sales_tax.odb and sales_tax.odb$ files from the root directory of the project.

# Using the Restful API

## Searching for A Sales Tax Entry with Valid Zip Code

* REST Command: GET
* URL: salesTax/
* Parameter: zipCode
* URL Example: http://localhost:8080/salesTax?zipCode=72703

JSON Response:

{
	"uuid": "5f71e0eb-4051-4dd2-8aca-5f0ffd15e6fb",
	"state": "AR",
	"zipCode": "72703",
	"taxRegionName": "FAYETTEVILLE",
	"stateRate": 0.065,
	"estimatedCombinedRate": 0.0975,
	"estimatedCountyRate": 0.0125,
	"estimatedCityRate": 0.02,
	"estimatedSpecialRate": 0.0,
	"riskLevel": 1
}

## Searching for A Sales Tax Entry with Invalid ZipCode

* REST Command: GET
* URL: salesTax/
* Parameter: zipCode
* URL Example: http://localhost:8080/salesTax?zipCode=12345

JSON Response:

{ "No Sales Tax Entry Found For Zip Code" }
