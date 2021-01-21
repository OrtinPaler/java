package courseJava;

public class Score {
    private static int score_1 = 0; // счет игрока 1
    private static int score_2 = 0; // счет игрока 2

    // свойство для установки счета игрока 1
    public static void setScore_1(int score_1) {
        Score.score_1 = score_1;
    }

    // свойство для установки счета игрока 2
    public static void setScore_2(int score_2) {
        Score.score_2 = score_2;
    }

    // свойство для вывода счета игрока 1
    public static int getScore_1() {
        return score_1;
    }

    // свойство для вывода счета игрока 2
    public static int getScore_2() {
        return score_2;
    }

    // метод для обнуления счета
    public static void clearScore() {
        setScore_1(0);
        setScore_2(0);
    }

    // метод для определения конца игры
    public static boolean checkEndGame() {
        return score_1 == Constants.MAX_SCORE || score_2 == Constants.MAX_SCORE;
    }
}
