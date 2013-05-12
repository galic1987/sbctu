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


Start app administration:
mvn exec:java -Dexec.mainClass="tuwien.sbctu.App2" -Dexec.args="arg1 arg2"


Start core:
mvn exec:java -Dexec.mainClass="org.mozartspaces.core.Server"


====== RMI =======
Parameters needed to start ..

StartWaiter: <id> <hardcodedPort 10879> <hardcodedBindingName pizzeria> -  i.e. 1 10879 pizzeria
StartCook: <id> <hardcodedPort 10879> <hardcodedBindingName pizzeria> -  i.e. 1 10879 pizzeria
StartGuestGroup: <id> <hardcodedPort 10879> <hardcodedBindingName pizzeria> <groupSize> - i.e. 1 10879 pizzeria 1





