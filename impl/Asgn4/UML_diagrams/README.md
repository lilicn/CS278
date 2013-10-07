UML diagrams for Guice
=====================
Background
-------------
Framework: Guice
Website: https://code.google.com/p/google-guice/
Get Source code: git clone https://code.google.com/p/google-guice/

What
-----
When applying Guice framework, we have to create a Class extends AbstractModule, and override 
configure() method to bind the implement class to interface. Here configure() method is a hook
method, which will be called by the framework itself, and follows the Hollywood Principle -
"don't call us, we'll call you." Just as life cycle methods of Activity in the Android framework. 
In the assignment, A Class diagram and a Sequence Diagram are made to show how definitely the hook 
method configure() is called by the Guice. In order to make it clear, some irrelevant classes, 
attributes and methods have been omitted.

Why 
-----
Just as what is illustrated in the official website, the biggest advantage of Guice is that it alleviates 
the need for factories and the use of new in Java code. It is conductive to reuse and change the code, and
unit test. Some other advantages are followed:
- The use of annotation and generics make it clean, lightweight and easy to use.
- It can inject constructors, fields and methods. 
- Scopes allow us to reuse instances for specific scope.
- The dependency injection pattern separates behaviour from dependency resolution, and make codes loose coupled.
- It supports Aspect Oriented Programming (AOP) method interception.
- Strong extensions.









