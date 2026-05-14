package aufgabe1d;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hivemq.client.mqtt.MqttClient;
import com.hivemq.client.mqtt.datatypes.MqttUtf8String;
import com.hivemq.client.mqtt.mqtt5.Mqtt5BlockingClient;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.UUID;

public class Main {

    private static final String BROKER_HOST = "10.50.12.150";
    private static final int BROKER_PORT = 1883;

    private static final String CHAT_TOPIC = "/aichat/default";
    private static final String STATE_TOPIC = "/aichat/clientstate";

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static void main(String[] args) throws Exception {
        String clientId = "AuchChat-" + UUID.randomUUID();
        String name = "RCK";

        Mqtt5BlockingClient client = MqttClient.builder()
                .useMqttVersion5()
                .identifier(clientId)
                .serverHost(BROKER_HOST)
                .serverPort(BROKER_PORT)
                .willPublish()
                .topic(STATE_TOPIC)
                .payload((clientId + " stopped").getBytes(StandardCharsets.UTF_8))
                .contentType("text/plain")
                .applyWillPublish()
                .buildBlocking();

        client.connect();

        client.publishWith()
                .topic(STATE_TOPIC)
                .payload((clientId + " started").getBytes(StandardCharsets.UTF_8))
                .contentType("text/plain")
                .send();

        client.toAsync().subscribeWith()
                .topicFilter("/aichat/#")
                .callback(publish -> {
                    String payload = new String(publish.getPayloadAsBytes(), StandardCharsets.UTF_8);
                    System.out.println();
                    System.out.println("[" + publish.getTopic() + "] " + payload);
                    System.out.print("> ");
                })
                .send();

        System.out.println("Chat-Client gestartet.");
        System.out.println("Schreibe Nachrichten. Mit /exit beenden.");

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("/exit")) {
                break;
            }

            ChatMessage msg = new ChatMessage(
                    clientId,
                    name,
                    input,
                    System.currentTimeMillis()
            );

            String json = objectMapper.writeValueAsString(msg);

            client.publishWith()
                    .topic(CHAT_TOPIC)
                    .payload(json.getBytes(StandardCharsets.UTF_8))
                    .contentType("application/json")
                    .send();
        }

        client.publishWith()
                .topic(STATE_TOPIC)
                .payload((clientId + " stopped normally").getBytes(StandardCharsets.UTF_8))
                .contentType("text/plain")
                .send();

        client.disconnect();
        System.out.println("Chat-Client beendet.");
    }
}