package chainofresponsibility;

// Not much reason for this class since it is the only type of concrete handler, but still wanted to demonstrate that
// this is the typical pattern
public class ConcreteComponent extends Handler {

    public ConcreteComponent(String ID, Handler firstComponent) {
        super(ID, firstComponent);
    }
}
