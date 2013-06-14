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
Start servers
mvn exec:java -Dexec.mainClass="org.mozartspaces.core.Server" -Dexec.args="5100"

mvn exec:java -Dexec.mainClass="org.mozartspaces.core.Server" -Dexec.args="5101"



Start environment (2 pizzeria and 1 LoadBalancer)
mvn exec:java -Dexec.mainClass="tuwien.sbctu.App" -Dexec.args="5100 5101" 


Start Waiters
mvn exec:java -Dexec.mainClass="tuwien.sbctu.runtime.RunWaiterSBC" -Dexec.args="6151 99 xvsm://localhost:5100"

mvn exec:java -Dexec.mainClass="tuwien.sbctu.runtime.RunWaiterSBC" -Dexec.args="6152 98 xvsm://localhost:5100"


mvn exec:java -Dexec.mainClass="tuwien.sbctu.runtime.RunWaiterSBC" -Dexec.args="6153 97 xvsm://localhost:5101"

mvn exec:java -Dexec.mainClass="tuwien.sbctu.runtime.RunWaiterSBC" -Dexec.args="6154 96 xvsm://localhost:5101"



Start Cooks
mvn exec:java -Dexec.mainClass="tuwien.sbctu.runtime.RunCookSBC" -Dexec.args="6161 89 xvsm://localhost:5100" 

mvn exec:java -Dexec.mainClass="tuwien.sbctu.runtime.RunCookSBC" -Dexec.args="6162 88 xvsm://localhost:5100"

mvn exec:java -Dexec.mainClass="tuwien.sbctu.runtime.RunCookSBC" -Dexec.args="6163 87 xvsm://localhost:5101"

mvn exec:java -Dexec.mainClass="tuwien.sbctu.runtime.RunCookSBC" -Dexec.args="6164 86 xvsm://localhost:5101"


Start Drivers

mvn exec:java -Dexec.mainClass="tuwien.sbctu.runtime.RunDriverSBC" -Dexec.args="6171 79 xvsm://localhost:5100"
mvn exec:java -Dexec.mainClass="tuwien.sbctu.runtime.RunDriverSBC" -Dexec.args="6172 78 xvsm://localhost:5100"
mvn exec:java -Dexec.mainClass="tuwien.sbctu.runtime.RunDriverSBC" -Dexec.args="6173 77 xvsm://localhost:5101"
mvn exec:java -Dexec.mainClass="tuwien.sbctu.runtime.RunDriverSBC" -Dexec.args="6174 76 xvsm://localhost:5101"





Start Simulation1 (100 Pizza)
mvn exec:java -Dexec.mainClass="tuwien.sbctu.Simulation1" -Dexec.args="6001 17 xvsm://localhost:5100"

Start Simulation2 (200 Pizza total)
mvn exec:java -Dexec.mainClass="tuwien.sbctu.Simulation2" -Dexec.args="6002 18 xvsm://localhost:5101"
====== RMI =======
Start Pizzeria:
mvn exec:java -Dexec.mainClass="tuwien.sbctu.rmi.RMIPizzeria" -Dexec.args="localhost 10879 pizzeria 100"

Start Waiter:
mvn exec:java -Dexec.mainClass="tuwien.sbctu.rmi.StartWaiter" -Dexec.args="pizzeria 10879"

Start Cook:
mvn exec:java -Dexec.mainClass="tuwien.sbctu.rmi.StartCook" -Dexec.args="pizzeria 10879"

Start Driver:
mvn exec:java -Dexec.mainClass="tuwien.sbctu.rmi.RMIDriver" -Dexec.args="pizzeria 10879"
mvn exec:java -Dexec.mainClass="tuwien.sbctu.rmi.RMIFiller" -Dexec.args=""





