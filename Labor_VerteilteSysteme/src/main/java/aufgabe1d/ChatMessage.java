package aufgabe1d;

public class ChatMessage {
    public String clientId;
    public String name;
    public String message;
    public long timestamp;

    public ChatMessage() {}

    public ChatMessage(String clientId, String name, String message, long timestamp) {
        this.clientId = clientId;
        this.name = name;
        this.message = message;
        this.timestamp = timestamp;
    }
}