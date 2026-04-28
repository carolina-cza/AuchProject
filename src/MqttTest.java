import org.eclipse.paho.client.mqttv3.*;

public class MqttTest {
    public static void main(String[] args) throws Exception {
        String broker = "tcp://10.50.12.150:1883";
        String clientId = "AuchClient-" + System.currentTimeMillis();

        MqttClient client = new MqttClient(broker, clientId);

        client.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {
                System.out.println("Verbindung verloren: " + cause.getMessage());
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) {
                System.out.println(topic + " -> " + new String(message.getPayload()));
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {}
        });

        client.connect();
        System.out.println("Verbunden mit MQTT Broker!");

        client.subscribe("#");
        System.out.println("Alle Topics abonniert. Warte auf Nachrichten...");
    }
}