# COP2552 - Project 3

# Description
This program is designed to collect exercise activities from user input and 
aggregate those activities to show the total and average time spent for each
activity and the total and average number of calories burned. 

## Usage
With Maven use the following to compile and run the project's default setings:

```bash
$ mvn clean compile exec:java@default
```
This project comes with in spanish translated with Google tranlate. To see the
program in spanish use the following maven arguments
```bash
$ mvn clean compile exec:java@es
```

To add additional languages just copy and suffix existing *.properties file with 
the ISO 639-1 language code and pass that as the programs argument.

```bash
$ mvn clean compile jar:jar
$ java -cp target/project3-0.0.1-SNAPSHOT.jar com.nasumilu.cop2552.project3.Launcher <language code>
```

To generate the javadocs use:
```bash
mvn javadoc:javadoc
```