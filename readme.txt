Java Version: 21
Maven Compiler: 3.11


The project contains 4 packages: 

	-broadcastershared (For classes shared amongst the 3 pattern implementations)
		-CommonBroadcastComponent
		-CommonSimulation
		-Logger
		-Message

	-chainofresponsibility (For the Chain of Responsibility pattern)
		-Handler (extends CommonBroadcastComponent)
		-ConcreteComponent (extends Handler)
		-ChainSimulation (extends CommonSimulation)

	-mediator (For the Mediator pattern)
		-Component (extends CommonBroadcastComponent)
		-Mediator
		-MediatorSimulation (extends CommonSimulation)

	-observer (For the Observer pattern)
		-Observer (extends CommonBroadcastComponent)
		-Subject
		-ConcreteObserverSubjectComponent (extends Oberver implements Subject)
		-ObserverSimulation (extends CommonSumulation)

To compile, from the project directory (Broadcaster) run:

mvn clean compile

To run Chain of Responsibility run:

mvn exec:java@chain
OR
java -cp target/classes chainofresponsibility.ChainSimulation

To run Mediator run:

mvn exec:java@mediator
OR
java -cp target/classes mediator.MediatorSimulation

To run Observer run:

mvn exec:java@observer
OR
java -cp target/classes observer.ObserverSimulation