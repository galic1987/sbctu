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

cd sbctu/sbctu

Start All
mvn exec:java -Dexec.mainClass="tuwien.sbctu.App" -Dexec.args="5100 5101" 

Start Simulation2 (200 Pizza total)
mvn exec:java -Dexec.mainClass="tuwien.sbctu.Simulation2" -Dexec.args="6101 18 xvsm://localhost:5101"


Start Simulation1 (100 Pizza)
mvn exec:java -Dexec.mainClass="tuwien.sbctu.Simulation1" -Dexec.args="6000 17 xvsm://localhost:5100"

Start Workers 
mvn exec:java -Dexec.mainClass="tuwien.sbctu.SimulationComplete" -Dexec.args="5100 5101" 






====== RMI =======
Start Pizzeria:
mvn exec:java -Dexec.mainClass="tuwien.sbctu.rmi.RMIPizzeria" -Dexec.args="localhost 10879 pizzeria 100"

Start Waiter:
mvn exec:java -Dexec.mainClass="tuwien.sbctu.rmi.StartWaiter" -Dexec.args="pizzeria 10879"

Start Cook:
mvn exec:java -Dexec.mainClass="tuwien.sbctu.rmi.StartCook" -Dexec.args="pizzeria 10879"

Start Driver:
mvn exec:java -Dexec.mainClass="tuwien.sbctu.rmi.RMIDriver" -Dexec.args="pizzeria 10879"

Start Filler Benchmark:
mvn exec:java -Dexec.mainClass="tuwien.sbctu.rmi.RMIFiller" -Dexec.args=""





