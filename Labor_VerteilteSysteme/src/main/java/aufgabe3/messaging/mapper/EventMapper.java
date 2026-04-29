package aufgabe3.messaging.mapper;

import aufgabe3.domain.event.BottomRowDeletedEvent;
import aufgabe3.domain.event.GameEvent;
import aufgabe3.domain.event.GameStartedEvent;
import aufgabe3.domain.event.GameWonEvent;
import aufgabe3.domain.event.MovePlacedEvent;
import aufgabe3.domain.model.Player;
import aufgabe3.messaging.dto.GameEventEnvelopeDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class EventMapper {

    public List<GameEvent> map(GameEventEnvelopeDTO dto) {
        List<GameEvent> events = new ArrayList<>();

        if ("ERROR".equals(dto.getState())) {
            return events;
        }

        UUID gameId = dto.getGameId();

        for (Map<String, Object> action : dto.getActions()) {
            String type = (String) action.get("type");

            if ("newGame".equals(type)) {
                int rows = ((Number) action.get("rows")).intValue();
                int cols = ((Number) action.get("cols")).intValue();
                events.add(new GameStartedEvent(gameId, rows, cols));

            } else if ("move".equals(type)) {
                Player player = Player.valueOf((String) action.get("player"));
                int column = ((Number) action.get("column")).intValue();
                events.add(new MovePlacedEvent(gameId, player, column));

            } else if ("deleteBottomRow".equals(type)) {
                int row = ((Number) action.get("row")).intValue();
                events.add(new BottomRowDeletedEvent(gameId, row));

            } else if ("winAction".equals(type)) {
                Player player = Player.valueOf((String) action.get("player"));
                events.add(new GameWonEvent(gameId, player));
            }
        }

        return events;
    }

    public void printError(GameEventEnvelopeDTO dto) {
        String message = dto.getMessage();
        if (message != null && !message.isEmpty()) {
            System.out.println("[ERROR] " + message);
        } else {
            System.out.println("[ERROR] Game-Master rejected the move (no message provided).");
        }
    }
}
