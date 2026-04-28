package aufgabe2b;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherData2b {

    private double tempCurrent;
    private double tempMax;
    private double tempMin;
    private String timeStamp;
    private String city;

    public double getTempCurrent() { return tempCurrent; }
    public void setTempCurrent(double v) { tempCurrent = v; }

    public double getTempMax() { return tempMax; }
    public void setTempMax(double v) { tempMax = v; }

    public double getTempMin() { return tempMin; }
    public void setTempMin(double v) { tempMin = v; }

    public String getTimeStamp() { return timeStamp; }
    public void setTimeStamp(String v) { timeStamp = v; }

    public String getCity() { return city; }
    public void setCity(String v) { city = v; }

    @Override
    public String toString() {
        return city + ": " + tempCurrent + "°C (min " + tempMin + " / max " + tempMax + ") um " + timeStamp;
    }
}
