package client.interfaces;

public interface InstanceableComponent {


    /**
     * refreshes the component and all its fields, propagates downwards
     */
    void refresh();

    /**
     * Registers a component for messages from a client endpoint using websockets
     */
    void registerForMessages();

}
