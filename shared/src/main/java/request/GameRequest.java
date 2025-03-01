package request;

public record GameRequest(
        String authToken,
        String gameName) implements CRequest {
}
