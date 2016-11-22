# ChangeDispenser
This app is created to get the optimal change for a certain amount of pence asked for. The various denominations of
pence used are:
* 100
* 50
* 20
* 10
* 5
* 2
* 1

All the change dispensed is in above denominations

## Downloading the application
The application can be downloaded from the github website using below link:
https://github.com/rishigogia/kcom

## Description of the application
This is a java based app used to dispense the optimal number of coins of various denominations

## Running the application
There are some JUnit test cases written in order to test
the functionality of the application, however it doesn't output anything.
Following assumptions are made in order to execute this:
* Java 7 is installed and JAVA_HOME and classpaths are set accordingly
* Maven is installed and M2_HOME and classpaths are set accordingly
In order to download the dependencies and compile the application, please use below command:
```
mvn clean install
```
Although the above will execute the test cases by itself, however, the test cases can be separately executed by using
below:
```
mvn test
```
To execute the application, please go to the project home directory and use below command:
```
<JAVA_HOME\bin>\java com.kcom.javatest.CoinChanger <pence (an integer)> [<file path>]
```
where the file path is an optional parameter. As an example, you can use below:
1. Single param
```
java com.kcom.javatest.CoinChanger 135
```
2. Two params
```
java com.kcom.javatest.CoinChanger 135 src/main/resources/coin.properties
```

## More that could be implemented
Logger functionality could be implemented in the application, but looking at the time involved in setting up the logging
framework, this was not done at the moment. There are no println statements, thus the application doesn't output anything.
However, there are some comments and javadocs available to understand the functionality and test cases implemented.

## Queries or Concerns
Please contact rishi_gogia@hotmail.com in case of any queries or concerns
