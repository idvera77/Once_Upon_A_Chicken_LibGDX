package basededatos;

import java.sql.Timestamp;

public class Puntuacion {
    private int score;
    private Timestamp gameStartDate;
    private Timestamp gameEndDate;

    public Puntuacion(int score, Timestamp gameStartDate, Timestamp gameEndDate) {
        this.score = score;
        this.gameStartDate = gameStartDate;
        this.gameEndDate = gameEndDate;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Timestamp getGameStartDate() {
        return gameStartDate;
    }

    public void setGameStartDate(Timestamp gameStartDate) {
        this.gameStartDate = gameStartDate;
    }

    public Timestamp getGameEndDate() {
        return gameEndDate;
    }

    public void setGameEndDate(Timestamp gameEndDate) {
        this.gameEndDate = gameEndDate;
    }
}
