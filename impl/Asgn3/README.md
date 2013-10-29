Assignment 3
============
Introduction
-------------
- Continuous Integrated test for Dropbox
- One command or click, all automatic
- You don't need to worry about the process not be killed or the different requirement for different system

How to run ?
-------------
- build.xml is the default Ant build file, main is the default method
- You can just use "ant main" without setting any arguments
- You can set the root directory for server and client, eg. "ant -Darg0=test-data/server/ -Darg1=test-data/client/ main"
- If using eclipse, you can set the Ant arguments in the External Tool Configurations, eg. "-Darg0=test-data/server/ -Darg1=test-data/client/"

How to debug ?
----------------
- You can use the log files which will be generated after test in log/ to debug

What's the expected result ?
-----------------------------------
- One failure should detected by the DirTest as the current Dropbox does not support DIR operations
- If more than one failure happened, it is possible that the DEFAULTREPONSETIME is not enough, just adjust the DEFAULTREPONSETIME in src/org/cs27x/util/Args.java

What tools or techniques are used ?
-------------------------------------
- Java ProcessBuilder 
- JUnit 
- Ant

What is new ?
-------------------------------------
- build.xml
- src/org/cs27x/test
- src/org/cs27x/util
- log/

What will be generated after test ?
-------------------------------------
- build/ and class files in it
- Dropbox.jar
- args.txt which stores the root directory for server and client
- log files in log/, such as debug.log, clientlog, serverlog and so on


Overview: 
-------------------------------------
This assignment will require you to build a series of integration tests for the
Dropbox code base. The key change from the previous assignment is that you must
create a script, executable, or other system for executing at least two instances
of the Dropbox client, adding/deleting files from their sync directories, and 
ensuring that the changes propagate correctly.

You can use any language/framework you want to complete the assignment. The requirements
are as follows:

1. The integration tests are fully automated and have no manual steps other than
   launching the tests
   
2. The integration tests must test two communicating instances of the Dropbox
   code base and include full network connectivity (two instances on localhost
   are fine)

Some languages/frameworks/tools that you might consider using:

1. Gradle
2. Maven
3. Ant
4. Bash shell scripts
5. Java + JavaProcessBuilder
6. Python
7. ...
