package request;

public record ListRequest(
        String authToken
) implements CRequest {
}
