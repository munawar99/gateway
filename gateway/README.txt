Gateway
-------
The Main (App) class creates a Gateway using a factory class to allow its implementation to be changed more easily.
The Gateway implements an interface so the implementing class can be changed more easily.
The Gateway uses a blocking queue to allow producers and consumers of messages safe access to pending tasks.
The Gateway reads its number of threads from a properties file which makes it more configurable.

Messages
--------
The GenericMessage class implements the Message interface. A future enhancement is that messages objects could be created using the builder pattern.
Messages have been configured to carry metadata in header fields, allowing the system to better facilitate message flow.

Source
------
An Eclipse project has been bundled.

Building
--------
Eclipse has an M2Eclipse plugin which can be used to build (and run) the programme from within the IDE.
Otherwise if you have Maven installed, navigate to the gateway/ directory and type "mvn clean install".
The file gateway/src/main/resources/default.properties contains the number of threads to use and defaults to one thread.

Running Standalone
------------------
Navigate to the gateway/target/ directory and type "java -jar message-gateway.jar"
The Maven Shade plugin allows a single jar file of the programme with all dependencies included to be distributed easily.

Testing
-------
Testing is a large (and important) part of software development.
Some tests have been written but more can always be done.