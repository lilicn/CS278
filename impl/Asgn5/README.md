Assignment 5
============

PubnubClient / PubnubServer
----------------------------
This project contains two Android Apps - PubnubClient and PubnubServer via PubNub Real-Time Network.
The most perfect scenario for this App is in large events such as concert, sports competition and so on, 
where tens of thousands of people subscribe the same channel via their phones. Once a controller publishes 
one command to the channel, tens of thousands of phones will change their appearance at the same time.


Vagrant
---------
- Use 'Vagrant up' to open the VM
- Use 'Vagrant ssh' to enter into the VM
- Use 'cd /home/vagrant/AndroidApp/PubnubClient/ && ant -f buildTest.xml' to test PubnubClient
- Use 'cd /home/vagrant/AndroidApp/PubnubServer/ && ant -f buildTest.xml' to test PubnubServer
- Use 'Vagrant destroy' to kill the VM
- Currently, we cannot run them in Vagrant VM, as I haven't figured out how to run AVD in Vagrant VM, while both client/server are Android Apps.
- You can run them in AVD via Eclipse or in your Android phone.


Android integration test
-------------------------
- PubnubClientTest
- need to run in Eclipse

Command line 
-------------
- android update project --name client -p . --target android-18
- ant clean debug
- android update test-project -m PubnubClient/ -p . 
- ant clean debug
- ant clean emma debug install test

Due Date
--------
10/27/2013

Overview
--------

This assignment will require you to use a combination of the software engineering
and software design principles that you have learned in class to build a client/server
application. You must write an application of your choosing that meets the requirements
outlined below. You can choose any application that you would like, however the application
must be a client/server app.

Requirements
------------
1. The application is client/server. Possible options include, but are not limited to: a mobile app that communicates
with a Java Spring back-end over HTTP, an app that builds upon the soda framework used in the
SMS assignment, or a grid computing application that uses the Hazelcast library from Asgn2/3.

2. The application is written in Java.

3. The application has well-written code that has been refactored as needed to be modular and extensible.

4. The application is thoroughly unit tested.

5. The application is integration tested. The integration tests can be written in the language of your choice.

6. The application has a script (e.g., ANT, Maven, Gradle, etc.) to build and package the binaries for 
distribution as a single archive + at most one other file.

7. The application distribution can be installed / launched with a single command.

8. The application has a vagrant project that can be checked out of version control and run in order to
setup your server (http://docs.vagrantup.com/v2/getting-started/index.html).



 
