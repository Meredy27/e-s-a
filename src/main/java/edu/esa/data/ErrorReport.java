package edu.esa.data;

import edu.esa.core.facade.ErrorType;

import java.util.List;

public class ErrorReport {
    private ErrorType type;

    private String name;
    private String message;
    private String recommendations;
    private List<String> points;

    public ErrorReport(ErrorType type, String message, String recommendations, List<String> points, String name) {
        this.type = type;
        this.message = message;
        this.recommendations = recommendations;
        this.points = points;
        this.name = name;
    }

    public ErrorType getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }

    public String getRecommendations() {
        return recommendations;
    }

    public List<String> getPoints() {
        return points;
    }

    public String getName() {
        return name;
    }
}
