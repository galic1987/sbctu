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

mvn exec:java -Dexec.mainClass="tuwien.sbctu.App" -Dexec.args=""


3. Start guestgroups Procedure

mvn exec:java -Dexec.mainClass="tuwien.sbctu.runtime.RunGuestSBC" -Dexec.args="1500 51802"

mvn exec:java -Dexec.mainClass="tuwien.sbctu.runtime.RunGuestSBC" -Dexec.args="1501 51812"

mvn exec:java -Dexec.mainClass="tuwien.sbctu.runtime.RunGuestSBC" -Dexec.args="1502 51822"

mvn exec:java -Dexec.mainClass="tuwien.sbctu.runtime.RunGuestSBC" -Dexec.args="1503 51832"


4. Start Cook


mvn exec:java -Dexec.mainClass="tuwien.sbctu.runtime.RuookSBC" -Dexec.args="1205 5102"

mvn exec:java -Dexec.mainClass="tuwien.sbctu.runtime.RuookSBC" -Dexec.args="1206 5112"




5. Start Waiter


mvn exec:java -Dexec.mainClass="tien.sbctu.runtime.RunWaiterSBC" -Dexec.args="1303 51112"


mvn exec:java -Dexec.mainClass="tien.sbctu.runtime.RunWaiterSBC" -Dexec.args="1301 4214"




====== RMI =======
RMI server on localhost port 10879 will be started automatically on RMI-bindingName "pizzeria"

1. start "StartRMI.java" arguments: -numberOfTables
2. start "StartGUI.java" (no arguments)
3. start "StartWaiter.java" arguments: -waiterID -rmiPort -bindingName (i.e. 1 10879 pizzeria)
4. start "StartCook.java" arguments: -cookID -rmiPort -bindingName (i.e. 2 10879 pizzeria)
5. guests can be started from the gui





