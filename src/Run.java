import java.util.Scanner;
public class Run {
    Scanner input = new Scanner(System.in);
    int numPlayers;
    int[] scores;
    int[] dice;
    int [] use;
    boolean[] chosen;
    public Run() {
        numPlayers = 0;
        dice = new int[5];
        use = new int[5];
        chosen = new boolean[3];
        System.out.println("Enter Number Of players");
        numPlayers = Popup.promptNumPlayers();
        scores = new int[numPlayers];
        playRound();
    }
    public void playRound() {
        for (int i = 0; i < numPlayers; i++) {
            playTurn(i);
        }
        boolean end = false;
        for (int i : scores) {
            if (i >= 25)
            end = true;
        }
        if (end) {
            end();
        } else {
            playRound();
        }
    }
    public void playTurn(int player) {
        System.out.println("Ready Player " + (player + 1));
        for (int i = 0; i < 5; i++) {
            dice[i] = 0;
        }
        boolean play = true;
        while (play) {
            playRoll(diceLeft());
            if (diceLeft() == 0) {
                System.out.println("You are out of dice");
                play = false;
            } else {
                System.out.println("You have " + diceLeft() + " dice left.\nRoll Again?\ny/n");
                input.nextLine();
                String in = input.nextLine();
                switch(in) {
                    case "y": play = true;
                    break;
                    case "n": play = false;
                    
                }
            }
        }
        System.out.println("You Scored " + score() + "Points");
        scores[player] += score();
        System.out.println("You Now Have " + scores[player] + " Points");
        showScores();
        resetTurn();
    }
    public void playRoll(int rolls) {
        for (int i = 0; i < use.length; i++) {
            use[i] = 0;
        }
        for (int i = 0; i < rolls; i++) {
            use[Die.roll()]++;
        }
        dice[1] += use[1];
        System.out.println("You Rolled " + use[0] + " Death Rays, " + use[1] + " Tanks, " + use[2] + " Humans, " + use[3] + " Cows, " + use[4] + " Chickens.");
        System.out.println("You now have " + dice[1] + " Tanks.");
        if (dice[0] != 0) {
            System.out.println("You Already Have " + dice[0] + " Death Rays.");
        }
        String out = "";
        int choice = 4;
        if (chosen[0] || chosen[1] || chosen[2]) {
            out += "You have already chosen";
            if (chosen[0]) {
                out += "\nHumans: You have " + dice[2]; 
                choice--;
            }
            if (chosen[1]) {
                out += "\nCows: You have " + dice[3];
                choice--;
            }
            if (chosen[2]) {
                out += "\nChickens: You have " + dice[4];
                choice--;
            }
            System.out.println(out);
            out = "";
            //int[] choices = new int[choice];
        }
        int[] choices = new int[choice];
        System.out.println("Please Select A Choice");
        int i = 1;
        if (use[0] != 0) {
            out += i + ". " + use[0] + " Death Rays\n";
            choices[(i - 1)] = 0;
            i++;
        }
        if (use[2] != 0 && !chosen[0]) {
            out += i + ". " + use[2] + " Humans\n";
            choices[(i - 1)] = 2;
            i++;
        }
        if (use[3] != 0 && !chosen[1]) {
            out += i + ". " + use[3] + " Cows\n";
            choices[(i - 1)] = 3;
            i++;
        }
        if (use[4] != 0 && !chosen[2]) {
            out += i + ". " + use[4] + " Chickens\n";
            choices[(i - 1)] = 4;
            i++;
        }
        System.out.println(out);
        int select = 0;
        select = input.nextInt();
        dice[choices[(select - 1)]] += use[choices[(select - 1)]];
        printDice();
    }
    public void printDice() {
        System.out.println("You now have\n" + dice[1] + " Tanks\n" + dice[0] + " Death Rays\n" + dice[2] + " Humans\n" + dice[3] + " Cows\n" + dice[4] + " Chickens");
    }
    public int diceLeft() {
        int total = 0;
        for (int i : dice) {
         total += i;   
        }
        return (13 - total);
    }
    public void resetTurn() {
        for (int i = 0; i < use.length; i++) {
            dice[i] = 0;
            use[i] = 0;
        }
        for (int i = 0; i < 3; i++) {
            chosen[i] = false;
        }
    }
    public int score() {
        if (dice[0] < dice[1]) {
            return 0;
        } else {
            int out = 0;
            out += dice[2] + dice[3] + dice[4];
            if (dice[2] != 0 && dice[3] != 0 && dice[4] != 0) {
                out += 3;
            }
            return out;
        }
    }
    public void showScores() {
        for (int i = 0; i < numPlayers; i++) {
            System.out.println("Player " + (i + 1) + ". " + scores[i] + " points.");
        }
    }
    public void end() {
        System.out.println("Game Over\nFinal Scores Are:");
        showScores();
        getWinner();
    }
    public void getWinner() {
        if (!areMoreWinners()) {
            int winner = 0;
            for (int i = 0; i < scores.length; i++) {
                if (scores[i] == getMax()) {
                    winner = i + 1;
                }
            }
            System.out.println("The Winner Was Player " + winner + "\n");
        } else {
            System.out.print("There Has Been A Tie, Players ");
            int[] tiedPlayers = new int[getNumTied()];
            int ii = 0;
            for (int i = 0; i < scores.length; i++) {
                if (scores[i] == getMax()) {
                    tiedPlayers[ii] = i;
                    ii++;
                    System.out.print(i + 1 + ", ");
                }
            }
            System.out.print("Prepare For Tie Breaker");
            tieBreak(tiedPlayers);
        }
    }
    public int getMax() {
        int out = 0;
        for (int i : scores) {
            if (i > out) {
                out = i;
            }
        }
        return out;
    }
    public boolean areMoreWinners() {
        int i = 0;
        for (int ii : scores) {
            if (ii == getMax()) {
                i++;
            }
        }
        if (i > 1) {
            return true;
        } else {
            return false;
        }
    }
    public int getNumTied() {
        int total = 0;
        for (int i = 0; i < scores.length; i ++) {
            if (scores[i] == getMax()) {
                total++;
            }
        }
        return total;
    }
    public void tieBreak(int[] tiedPlayers) {
        int[] diceIn = new int[5];
        int[] tiedScores = new int[tiedPlayers.length];
        for (int i = 0; i < tiedPlayers.length; i++) {
            for (int ii = 0; ii < 6; ii++) {
                diceIn[Die.roll()]++;
            }
            System.out.println("\nPlayer " + (tiedPlayers[i] + 1) + " Rolls " + diceIn[0] + " Death Rays");
            tiedScores[i] = diceIn[0];
            for (int ii = 0; ii < diceIn.length; ii++) {
                diceIn[ii] = 0;
            }
        }
        int[] still = new int[getIndexOfLg(tiedScores).length];
        int[] temp = getIndexOfLg(tiedScores);
        for (int i = 0; i < still.length; i++) {
            still[i] = tiedPlayers[temp[i]];
        }
        if (still.length != 1) {
            tieBreak(still);
        } else {
            System.out.println("Player " + (still[0] + 1) + " Wins!");
            
        }
    }
    public int getLg(int[] arr) {
        int a = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] > a) {
                a = arr[i];
            }
        }
        return a;
    }
    public int[] getIndexOfLg(int[] arr) {
        int lg = getLg(arr);
        int a = 0;
        for (int i : arr) {
            if (i == lg) {
                a++;
            }
        }
        int[] out = new int[a];
        a = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == lg) {
                out[a] = i;
                a++;
            }
        }
        return out;
    }
}