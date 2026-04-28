package aufgabe1c;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherData1c {
    public double Temp;
    public double Humidity;
    public double Lpg;
    public double Co;
    public double Smoke;
}