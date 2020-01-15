package baselib.interfaces;

public interface INetworkManagerController {

    default void dataReceived(String serviceName, Object data) {
        System.out.println("data has bee receieved");
    }

}
