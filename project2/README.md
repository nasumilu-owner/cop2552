# COP2552 - Project 2

# Description
Merge three input files of patient information. Where one patient file
represents the current repository of patinent information, another file
represents a file of new patients to add and the third a file of paitents
to remove. 

Two output files are expected, first a file with the newly merged data and
an error file showing collisions and invalid patient information. 

## Usage
With Maven use the following to compile and run the project:

```bash
$ mvn clean compile exec:java
```

To generate the javadocs use:
```bash
mvn javadoc:javadoc
```
## Note
The data for file PatientListW3.txt is formated as follows:
```txt
Previous date
Patient id number
Patient name
Date of birth
Year added
Patient id number
Patient name
Date of birth
Year added
...
```
The data for file NewPatientList.txt and RemovePatient.txt 
is formated as follows:
```txt
Patient id number
Patient name
Date of birth
Patient id number
Patient name
Date of birth
...
```

Expected output file PatientListW4.txt *MUST* have the following 
structure:
```txt
Current System date
Patient id number
Patient name
Date of birth
Year added
Patient id number
Patient name
Date of birth
Year added
...
```

Expected output file for PatientErrorsW4.txt
```txt
Current System data
Patient id number
Patient name
Date of birth
Patient id number
Patient name
Date of birth
...
```