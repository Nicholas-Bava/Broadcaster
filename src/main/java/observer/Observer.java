package observer;

import broadcastershared.CommonBroadcastComponent;
import broadcastershared.Message;

public abstract class Observer extends CommonBroadcastComponent {

    protected Observer(String ID) {
        super(ID,"observer_logs/" + ID + "_log.txt");
    }

    @Override
    public abstract void update(Message msg);

}
