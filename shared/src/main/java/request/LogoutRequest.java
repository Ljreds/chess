package request;

public record LogoutRequest(String authToken) implements CRequest {
}
