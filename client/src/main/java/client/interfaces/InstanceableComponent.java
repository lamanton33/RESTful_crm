package client.interfaces;

public interface InstanceableComponent {


    /**
     * refreshes the component and all its fields, propagates downwards
     */
    public void refresh();
    public void registerForMessages();



}
