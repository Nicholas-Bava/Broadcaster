package mediator;

import broadcastershared.Message;

import java.util.HashMap;
import java.util.Map;

public class Mediator {

    private Map<String, Component> components;

    public Mediator() {
        this.components = new HashMap<>();
    }

    public void addComponent(Component component) {
        components.put(component.getID(), component);
    }

    public void sendMessage(Message message) {
        if (message.getRecipientID().equals("*")){
            for (Map.Entry<String, Component> entry : components.entrySet()) {
                entry.getValue().handleMessage(message);
            }
        } else {
            components.get(message.getRecipientID()).handleMessage(message);
        }

    }
}
