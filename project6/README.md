# COP2552 - Project 6

## Description
This is a simple JavaFx application which authenticates a user by an account number and password. Then the user is authorized by correctly answering 
a security question. The `Accounts` data is stored in a *.dat file of serialized objects. 
## Usage 

```bash
$ git clone
$ cd <project directory>
$ javac -cp <javafx rt> -d bin src/*.java
$ java -cp <javafx rt>:./bin App
```
To display the account information use the `ReadFile` class

```bash
$ java -cp ./bin ReadFile
``` 
