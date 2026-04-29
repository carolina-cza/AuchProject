package aufgabe3;

import aufgabe3.messaging.dto.GameEventEnvelopeDTO;
import aufgabe3.messaging.mapper.EventMapper;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class ErrorHandlingTest {

    @Test
    void errorEventsAreIgnored() {
        EventMapper mapper = new EventMapper();

        GameEventEnvelopeDTO dto = new GameEventEnvelopeDTO(
                System.currentTimeMillis(),
                Collections.singletonList(Map.of(
                        "type", "move",
                        "player", "PLAYER1",
                        "column", 1
                )),
                "ERROR",
                UUID.randomUUID(),
                "Invalid move",
                null
        );

        var events = mapper.map(dto);

        assertTrue(events.isEmpty()); // ERROR must not produce events
    }
}