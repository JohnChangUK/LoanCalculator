# Loan calculation 

## Run the project
- Install Gradle if not installed already: https://gradle.org/install/
- From the root directory, run `./gradlew clean check`, this will clean the project and run all tests
- Run `gradle customFatJar`, as this will build a jar of all the dependencies
- This will create the jar file under the folder `build/libs/<created_jar_file.jar>` which 
you can run the program


- To run the program, run use the following command in the root directory:
`java -jar build/libs/quote-1.0-SNAPSHOT.jar <path_to_csv> <requested_amount>`

- Alternatively if all else fails, run the `all-in-one-jar-1.0-SNAPSHOT.jar` file in the root directory
and run `java -jar all-in-one-jar-1.0-SNAPSHOT.jar src/main/resources/marketdata.csv 1000 `

Running the following: 
`java -jar build/libs/all-in-one-jar-1.0-SNAPSHOT.jar src/main/resources/marketdata.csv 1000`

Produces:
```aidl
Requested Amount: £1000
Rate: 7.0% 
Monthly Repayment: £30.88
Total Repayment: £1111.58

```
