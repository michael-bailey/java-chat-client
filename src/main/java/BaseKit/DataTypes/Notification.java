package BaseKit.DataTypes;

public class Notification extends Object {

    // defined a reference to the sender object
    private Object sender;

    // defines the string that describes the notification
    private String message;

    // additional objects nessesery for the task
    private Object attachment;

    public Notification(String message, Object sender) {
        this.message = message;
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public Object getSender() {
        return sender;
    }

    public Object getAttachment() {
        return attachment;
    }

    public void setAttachment(Object attachment) { this.attachment = attachment; }
}
