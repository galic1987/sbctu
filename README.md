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






