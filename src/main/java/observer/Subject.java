package observer;

import broadcastershared.CommonBroadcastComponent;
import broadcastershared.Message;

public interface Subject {

    void subscribe(CommonBroadcastComponent observer);
    void unsubscribe(CommonBroadcastComponent observer);
    void notifyMessage(Message msg);
}
