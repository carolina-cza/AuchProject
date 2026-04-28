package aufgabe1c;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hivemq.client.mqtt.MqttClient;
import com.hivemq.client.mqtt.mqtt5.Mqtt5AsyncClient;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

public class Main {

    private static final String BROKER_HOST = "10.50.12.150";
    private static final int BROKER_PORT = 1883;
    private static final String TOPIC = "/AZ-Envy/JSON";

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static void main(String[] args) throws Exception {
        CountDownLatch waitForever = new CountDownLatch(1);

        Mqtt5AsyncClient client = MqttClient.builder()
                .useMqttVersion5()
                .identifier("Auch-Aufgabe1c-" + UUID.randomUUID())
                .serverHost(BROKER_HOST)
                .serverPort(BROKER_PORT)
                .buildAsync();

        System.out.println("Verbinde mit MQTT-Broker " + BROKER_HOST + ":" + BROKER_PORT + "...");

        client.connect()
                .thenAccept(connAck -> {
                    System.out.println("Verbunden.");
                    System.out.println("Abonniere Wetterdaten: " + TOPIC);

                    client.subscribeWith()
                            .topicFilter(TOPIC)
                            .callback(publish -> {
                                String json = new String(
                                        publish.getPayloadAsBytes(),
                                        StandardCharsets.UTF_8
                                );

                                try {
                                    WeatherData data = objectMapper.readValue(json, WeatherData.class);
                                    printWeatherData(data);
                                } catch (Exception e) {
                                    System.out.println("Fehler beim Parsen der Wetterdaten:");
                                    System.out.println(json);
                                    e.printStackTrace();
                                }
                            })
                            .send();
                })
                .exceptionally(error -> {
                    System.out.println("Verbindung fehlgeschlagen.");
                    error.printStackTrace();
                    return null;
                });

        waitForever.await();
    }

    private static void printWeatherData(WeatherData data) {
        System.out.println("========== Wetterdaten ==========");
        System.out.println("Zeit:              " + LocalDateTime.now());
        System.out.println("Temperatur:        " + data.Temp + " °C");
        System.out.println("Luftfeuchtigkeit:  " + data.Humidity + " %");
        System.out.println("LPG:               " + data.Lpg);
        System.out.println("CO:                " + data.Co);
        System.out.println("Smoke:             " + data.Smoke);
        System.out.println("=================================");
        System.out.println();
    }
}