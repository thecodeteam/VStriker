## Introduction

VStriker it’s a Java desktop application that provides connectivy validation and estimates of CRUD (Create, Read, Update and Delete) operations throughputs achievable against the ViPR® Data Services Object storage.  

VSTriker's goal is to provide developers a quick and simple way of understanding the throughput  that can be expected from a single node client application connecting to ViPR Data Services that will be running in the same running in the same network conditions. 

ViPR® Data Services is an object store that has the ability of supporting multiple protocols. Vstriker throughput analysis can be performed for applications that use AWS S3, OpenStack Swift and Atmos REST APIs.


![VStiker Throughput Analizer](https://github.com/emccode/VStriker/blob/master/documentation/media/VStriker-Application-Workflow.png)

## USE CASES


The following section provides an overview of the Use Cases for the VStriker throughput analyzer. There is only one actor for this application, the end-user:

 
Use Case	             | Description
---------------------- | -----------|
Manage Account         | Accounts contain information about the target system and APIs/protocols to perform the throughput analysis against (CRUD Operations on Account)
Manage APIs/Protocols  | APIs/Protocol contains the information regarding the specific API and Protocol that is supported in the account. There are three main APIs and the protocols supported are HTTP and HTTPS (CRUD for  AWS S3, Swift, and Atmos)
Validate Account       | 	Validates the account configuration. It tests that the accounts are properly configured. This validation includes the validation of all APIs/Protocols that are supported by the Account.
Validate APIs	          | Validate the API/Protocol configuration. It tests that the protocol information entered in correct.
Manage Test Plan	       | Test plans define the parameters, policies, and APIs/Protocols to be tested. There is a list of standard test and the ability for the use to customize it to their needs.  
Execute Test Plan      | Executes the selected test plan using the account selected.
Export Test            | 	Exports the test results to a coma delimited file for further analysis.
View Report and Graphs	| Provides the end user with a report of the test plan and charts that compares the throughput of the CRUD operations performed per API/Protocol used.
View Test Results      |	View that provides the information resulting from the test performed.

## Architecture 

This diagram shows the high level VStriker application architecture: 

![VStiker Throughput Analizer Architecture](http://macdown.uranusjr.com/static/base/img/logo-160.png)

Component Name |	Component Description
-------------- | ---------------------|
JAVA UI |	UI for configuration, execution and display of the throughput tests
Accounts Library | 	Holds all the Accounts, APIs and Ports information
Test Plan Library |	Holds the test plain configuration information, premade plans and custom test plans
Test files Generator |	Generates tests files to be used as objects for the operations
Export Test to CSVs |	Exports the last test run results into a CSV file to be used for further analysis
Test Results Collector | 	Collects and processes (conversion calculations) of the data resulting from a throughput analysis. 
Test Engine |	Test engine uses the test plan information and performs the throughput analyses based parameters. The test engine divides the work into test units. Each one of those units is an operation (CRUD), which is executed in parallel based on the selected threading configuration.
Connectivity Validator	| Connectivity validator provides the user with confirmation that the connection information submitted for an API/Protocol is correct and it is properly configured.
Java S3 API	| Amazon’s Java S3 SDK Libraries
Java Swift API |	OpenStack’s Java Swift SDK Libraries
Java Atmos API | 	Atmos’ Java SDK Libraries
Test Data Store |	Data repository to store the test results, accounts and test plans
 

##Application Workflow 

The VStriker application workflow is composed of four main steps: 

![VStiker Application Workflow](http://macdown.uranusjr.com/static/base/img/logo-160.png)
 

**1.	Account Management:**  The application starts on the Account tab. This enables users to create their session information or run a previous session. This step enables users to enter the information regarding the APIs and endpoint configurations that need to be tested. The current supported APIs are: AWS S3, OpenStack Swift and EMC Atmos. 

**2.	Configuration Management:** The configuration management steps enables users to select a test plan from a list of pre-built ones or to create a new one. The test plans provides a very granular set of options to the user. 

**3.	Test Execution:** This step takes the information defined in the session and the test plan configuration, generates the load to be applied to the defined endpoints and executes the test. This step also records the test execution information that will later be used for the user.

**4.	View Results:** This step provides consolidated view of the test results. It provides raw data, averages, graphs and the ability to export the data for further analysis. 

### Account Management 

Account Management enables users to organize the test plans and execution for each account that they create. An account can have multiple APIs that may to be tested as part of the throughput analysis test.  The following figure shows the relationship:

![VStiker Account Management](http://macdown.uranusjr.com/static/base/img/logo-160.png)

In addition, accounts and APIs can be validated. This function is performed by the Validation Engine. This function enables users to verify connectivity to the target server. It performs the following checks: 

1.	Check the connectivity to the server
2.	Check the ability to perform CRUD operations 
3.	Check the Ability to perform object and CRUD on a bucket. 
   

### Test Plan Configuration

[To Be Done]

 ![VStiker Account Management](http://macdown.uranusjr.com/static/base/img/logo-160.png)


Test Plan configuration Paramaters: 

Parameter Name | Parameter Description
---------------| ---------------------|
Object Size (in Kb) | Size of  the Objects to be used in CRUD operations 
Number of Operations | Number of operations to be performed. Operations. For example a CRUD on an object counts as 4 operations.  
Number of Threads | Number of Threads VStriker will operate with. This enables the simulation of multiple clients performing the test.
Operations to be Tested | This section enables the user to select the type of operations to be tested and the weight (as percent of times) that those operations will be exectuted. This enables to map and fine tune the expected loads for the applications using the backend system. For Example, if I wanted to tested thorughput of Deletions, I would select Delete operations and put 100% weihg on it. VStriker will take care of creating the objects, and analyze the Delete operations. I can also select a combination of Create, Delete, Update and Read operations to simulate a mixed workload. 
Number of Retries | Number of times VStriker will Retry an operation before giving up.
Object Size Report Unit | Unit to be used when generating the report. Sizes can be selected as (Kb, Mb, Gb)
Random size Objects Parameters | Clicking this checkbox enables the used of of Rando Size Objects. This setting will disable the Object Size field. 
Object Min. Size (in Kb.) | Enter the Mininum size of the objects to be used for the test
Ojbect Max. Size (in Kb.) | Enter the Maximum size of the objets to be usined in the test
Object Size Preference | This allows you to define the type of object size load you may have in your application. This enables to fine tune the test workload to your needs.
Protocols to be Tested | Select what API you want to use in this test. This is tied to the APIs that have been defined and validated in the selected Account. 




### Test Plan Execution 

[To Do]

### View and Export Restuls

[To Do] 

