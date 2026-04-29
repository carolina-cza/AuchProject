package aufgabe3.messaging.mapper;

import aufgabe3.domain.command.PlaceMoveCommand;
import aufgabe3.domain.command.StartGameCommand;
import aufgabe3.messaging.dto.NameDTO;
import aufgabe3.messaging.dto.PlaceMoveRequestDTO;
import aufgabe3.messaging.dto.StartGameRequestDTO;

public class CommandMapper {

    public StartGameRequestDTO toStartGameRequest(StartGameCommand cmd) {
        return new StartGameRequestDTO(
                cmd.getGameId(),
                new NameDTO(cmd.getClient1Name()),
                new NameDTO(cmd.getPlayer1Name()),
                new NameDTO(cmd.getClient2Name()),
                new NameDTO(cmd.getPlayer2Name())
        );
    }

    public PlaceMoveRequestDTO toPlaceMoveRequest(PlaceMoveCommand cmd) {
        return new PlaceMoveRequestDTO(
                cmd.getGameId(),
                cmd.getPlayer().name(),
                cmd.getColumn()
        );
    }
}
