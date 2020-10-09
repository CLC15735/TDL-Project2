# Project Title

TO DO LIST

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

Version Control System: Git
	-Remote: GitHub
	-Local: GitBash

Software: Java 7
Development tools: Eclipse/Spring or Maven
Testing: JUnit, Selenium
Database management: MySQL 5.7

### Installing


1. Set up the development environment:
	- Fork/Clone the dev or master branch of this repository using GitHub
3. The environement should be all set and ready for a test run in the Runner.

## Running the tests

Integration and unit tests are performed for service and controller for both tasks and lists.
Extra unit test are performed for Tasks and Lists domains.

### Unit Tests 
Unit tests use MockBean for repo/service, generate prior to the test the required input , and make sure that 
what is returned from the service/controller is what is is expected.
A verification that MockBean has been set up is also included in every test.

### Integration Tests 
Integration tests do not use MockBean but rather Autowire (use somthing that is already there).
Each test is initialised by deleting anything that is in the repository, and values are assigned to the variables.
Tests make sure that the response received from calling the controller/service is the one expected.

### And coding style tests


## Deployment



## Built With

* [Maven](https://maven.apache.org/) - Dependency Management

## Versioning

We use [SemVer](http://semver.org/) for versioning.

## Authors

* **Cristina López** - [CL15735](https://github.com/CLC15735)

## License


## Acknowledgments

* Adapted from nickrstewarttds code - (https://github.com/nickrstewarttds/Springust)

