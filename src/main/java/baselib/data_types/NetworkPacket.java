package baselib.data_types;

/**
 * NetworkPacket
 */
public class NetworkPacket {

    private String type;
    private String content;

    NetworkPacket(String type, String content) {

    }

    public String getType() {
        return this.type;
    }

    public String getContent() {
        return this.content;
    }

}