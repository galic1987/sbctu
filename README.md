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
RMI server on localhost port 10879 will be started automatically on RMI-bindingName "pizzeria"

1. start "StartRMI.java" arguments: -numberOfTables
2. start "StartGUI.java" (no arguments)
3. start "StartWaiter.java" arguments: -waiterID -rmiPort -bindingName (i.e. 1 10879 pizzeria)
4. start "StartCook.java" arguments: -cookID -rmiPort -bindingName (i.e. 2 10879 pizzeria)
5. guests can be started from the gui





