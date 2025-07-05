package dtos;

public class RecommendationDTO {
    private String message;

    public RecommendationDTO() {
    }

    public RecommendationDTO(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
