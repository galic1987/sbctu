sbctu
=====

Space Based Computing TU

To import the project into Eclipse:
goto import maven projects-> navigate to root directory -> sbctu





To compile: (in project dir) - sbctu/sbctu

mvn package



To clean:

mvn clean



To run with arguments run:
====== SBC =======
1. Start server:

mvn exec:java -Dexec.mainClass="org.mozartspaces.core.Server" -Dexec.args="4242"


2. Start programGUI (containers)


3. Start guestgroups Procedure


4. Start Cook


5. Start Waiter




====== RMI =======
Parameters needed to start ..

StartWaiter: <id> <hardcodedPort 10879> <hardcodedBindingName pizzeria> -  i.e. 1 10879 pizzeria
StartCook: <id> <hardcodedPort 10879> <hardcodedBindingName pizzeria> -  i.e. 1 10879 pizzeria
StartGuestGroup: <id> <hardcodedPort 10879> <hardcodedBindingName pizzeria> <groupSize> - i.e. 1 10879 pizzeria 1





