# Loan calculation 

## To Run:

- From the root directory, run `./gradlew clean check`, this will clean the project and run all tests
- Run `gradle customFatJar`, as this will build a jar of all the dependencies

- To run the program, run use the following command:
`java -jar build/libs/quote-1.0-SNAPSHOT.jar [path_to_csv_file] [requested_loan_amount]`

Running the following: 
`java -jar build/libs/all-in-one-jar-1.0-SNAPSHOT.jar src/main/resources/marketdata.csv 1000`

Produces:
```aidl
RequestedAmount: £1000
Rate: 7.0% 
MonthlyRepayment: £30.88
TotalRepayment: £1111.58

```
