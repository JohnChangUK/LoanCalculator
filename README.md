# Loan calculation 

## To Run:

- From the root directory, run `./gradlew clean check`, this will clean the project and run all tests
- Run `gradle customFatJar`, as this will build a jar of all the dependencies
(e.g. `/Users/username/.m2/repository/com/georgeeaton/yopa/quote/1.0-SNAPSHOT/quote-1.0-SNAPSHOT.jar`)
- To run the program, run use the following command:
`java -jar target/quote-1.0-SNAPSHOT.jar [path_to_csv_file] [requested_loan_amount]`