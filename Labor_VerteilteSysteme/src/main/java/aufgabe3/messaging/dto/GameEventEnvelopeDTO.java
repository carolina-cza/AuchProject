package aufgabe3.messaging.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GameEventEnvelopeDTO {

    private long timeStamp;
    private List<Map<String, Object>> actions;
    private String state;
    private UUID gameId;
    private String message;
    private String debugOut;

    public GameEventEnvelopeDTO() {
    }

    public GameEventEnvelopeDTO(long timeStamp, List<Map<String, Object>> actions,
                                String state, UUID gameId, String message, String debugOut) {
        this.timeStamp = timeStamp;
        this.actions = actions;
        this.state = state;
        this.gameId = gameId;
        this.message = message;
        this.debugOut = debugOut;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public List<Map<String, Object>> getActions() {
        return actions;
    }

    public void setActions(List<Map<String, Object>> actions) {
        this.actions = actions;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public UUID getGameId() {
        return gameId;
    }

    public void setGameId(UUID gameId) {
        this.gameId = gameId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDebugOut() {
        return debugOut;
    }

    public void setDebugOut(String debugOut) {
        this.debugOut = debugOut;
    }
}