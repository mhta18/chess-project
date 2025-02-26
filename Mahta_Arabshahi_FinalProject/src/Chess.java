import java.io.IOException;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Scanner;

public class Chess {
       static Scanner inputReader = new Scanner(System.in);
    ArrayList<Player> players = new ArrayList<>();
    ArrayList<Game> games = new ArrayList<>();
    String computerName = "Engine 2";
    {
        addFakeName();
        addFakeGame();
    }

    public static int getNumberInput() {
        int userNumber = inputReader.nextInt();
        inputReader.nextLine();
        return userNumber;
    }

    public void addFakeName() {
        players.add(new Player("mahta"));
        players.add(new Player("hasti"));
    }

    public void addFakeGame() {
        LocalTime startTime = LocalTime.now();
        LocalTime firstFakeEndTime = LocalTime.now().plusMinutes(20);
        LocalTime secondFakeEndTime = LocalTime.now().plusMinutes(10);
        LocalTime thirdFakeEndTime = LocalTime.now().plusMinutes(24);
        LocalTime fourthFakeEndTine = LocalTime.now().plusMinutes(30);

        Long firstFakeTime = ChronoUnit.MINUTES.between(startTime, firstFakeEndTime);
        Long secondFakeTime = ChronoUnit.MINUTES.between(startTime, secondFakeEndTime);
        Long thirdFakeTime= ChronoUnit.MINUTES.between(startTime, thirdFakeEndTime);
        Long fourthFakeTime = ChronoUnit.MINUTES.between(startTime, fourthFakeEndTine);

        games.add(new Game("mahta", "hasti", 1, 0, "mahta", "hasti", firstFakeTime));
        games.add(new Game("hasti", "mahta", 0, 1, "mahta", "hasti", fourthFakeTime));
        games.add(new Game("kiana", "mahta", 0, 1, "kiana", "mahta", secondFakeTime));
        games.add(new Game("mahta", computerName, 1, 0, "mahta", computerName, thirdFakeTime));
        games.add(new Game("-", "-", 0.5, 0.5, "hasti", "kiana", fourthFakeTime));
    }

    public void playChessWithEngine(String playerName) {
        // loserName or winnerName
        String color = "white";
        // timer starts to count
        Board gameBoard = new Board();
        LocalTime start = LocalTime.now();
        System.out.println("The game is starting..." + start);
        while (true) {

            System.out.println(gameBoard);

            System.out.println(color + " Please Make a move(e.p: d2 d4 ):");

            String playerMove = inputReader.nextLine();
            if (playerMove.contains("resign")) {
                LocalTime end = LocalTime.now();
                Long gamePeriod = ChronoUnit.MINUTES.between(start, end);
                System.out.println(playerName + " resigns");
                System.out.println(computerName + " wins the game!");
                games.add(new Game(computerName, playerName, 0, 1, playerName, computerName, gamePeriod));

            }
            try {
                gameBoard.performMove(playerMove, color, true);
            } catch (IOException e) {
                System.out.println("Invalid move!");
                continue;
            }

            if (gameBoard.isInCheck(colorToggle(color))) {
                System.out.println(colorToggle(color) + " is in check.");
            }
            if (gameBoard.isInCheck(colorToggle(color))) {
                if (!gameBoard.canAnyPieceMakeAnyMove(colorToggle(color))) {
                    System.out.println("Checkmate. " + computerName + " wins");
                    LocalTime end = LocalTime.now();
                    Long gamePeriod = ChronoUnit.MINUTES.between(start, end);
                    games.add(new Game(playerName, computerName, 1, 0, playerName, computerName, gamePeriod));

                    System.out.println("Game over!");
                }
            }
            if (!(gameBoard.isInCheck(color)) && gameBoard.canAnyPieceMakeAnyMove(color) == false) {
                System.out.println("stalemate");
                LocalTime end = LocalTime.now();
                Long gamePeriod = ChronoUnit.MINUTES.between(start, end);
                games.add(new Game("-", "-", 0.5, 0.5, playerName, computerName, gamePeriod));

            }

            // computer turns
            Piece[][] oldBoard1 = gameBoard.board.clone();
            gameBoard.board = oldBoard1;
            System.out.println(gameBoard);
            color = colorToggle(color);
            System.out.println("Computers Turn");
            try {
                gameBoard.performComputerMove(gameBoard.validMoves(color),
                        color, true);
            } catch (IOException e) {
                System.out.println("Invalid move!");
                continue;
            }
            if (!(gameBoard.isInCheck(color)) && gameBoard.canAnyPieceMakeAnyMove(color) == false) {
                System.out.println("stalemate");
                LocalTime end = LocalTime.now();
                Long gamePeriod = ChronoUnit.MINUTES.between(start, end);
                games.add(new Game("-", "-", 0.5, 0.5, playerName, computerName, gamePeriod));

            }
            
            if (gameBoard.isInCheck(colorToggle(color))) {
                System.out.println(colorToggle(color) + " is in check.");
            }
            if (gameBoard.isInCheck(colorToggle(color))) {
                if (!gameBoard.canAnyPieceMakeAnyMove(colorToggle(color))) {
                    System.out.println("Checkmate. " + computerName + " wins");
                    LocalTime end = LocalTime.now();
                    Long gamePeriod = ChronoUnit.MINUTES.between(start, end);
                    games.add(new Game(computerName, playerName, 0, 1, playerName, computerName, gamePeriod));

                    System.out.println("Game over!");
                }
            }
            Piece[][] oldBoard = gameBoard.board.clone();
            gameBoard.board = oldBoard;
            color = colorToggle(color);

        }

    }

    public void playChessWithTwoPlayer(String color, String firstPlayerName) {

        System.out.println("Second Player Please Enter your Name");
        String secondPlayerName = inputReader.nextLine();
        signUp(secondPlayerName);
        Board gameBoard = new Board();
        System.out.println("The game starts...\nEnter (resign?) or Enter (draw?) whenever is needed");
        boolean drawAvailable = false;
        LocalTime start = LocalTime.now();
        System.out.println("The game is starting" + start);
        if (color.charAt(0) == 'w' || color.equals("white")) {
            color = "white";
            while (true) {

                System.out.println(gameBoard);

                System.out.println(color + " Please Make a move(e.p: d2 d4): ");

                String move = inputReader.nextLine();

                if (drawAvailable && move.equals("yes")) {
                    if (move.contains("draw")) {
                        LocalTime end = LocalTime.now();
                        Long gamePeriod = ChronoUnit.MINUTES.between(start, end);
                        games.add(new Game("-", "-", 0.5, 0.5, firstPlayerName, secondPlayerName,
                                gamePeriod));
                        System.out.println("The game is a draw.");
                    } else {
                        drawAvailable = false;
                    }
                }

                if (move.contains("resign")) {
                    System.out.println(color + " resigns");
                    System.out.println(colorToggle(color) + " wins the game!");
                    LocalTime end = LocalTime.now();
                    Long gamePeriod = ChronoUnit.MINUTES.between(start, end);
                    if (color.equals("white")) {
                        games.add(new Game(secondPlayerName, firstPlayerName, 0, 1, firstPlayerName, secondPlayerName,
                                gamePeriod));
                    } else {
                        games.add(new Game(firstPlayerName, secondPlayerName, 1, 0, firstPlayerName, secondPlayerName,
                                gamePeriod));
                    }

                }
                if (!(gameBoard.isInCheck(color)) && gameBoard.canAnyPieceMakeAnyMove(color) == false) {
                    System.out.println("stalemate");
                }

                try {
                    gameBoard.performMove(move, color, true);
                } catch (IOException e) {
                    // Ask for user input again
                    System.out.println("Invalid move!");
                    continue;

                }

                Piece[][] oldBoard = gameBoard.board.clone();
        

                if (gameBoard.isInCheck(colorToggle(color))) {
                    System.out.println(colorToggle(color) + " is in check.");
                }
                if (gameBoard.isInCheck(colorToggle(color))) {
                    if (!gameBoard.canAnyPieceMakeAnyMove(colorToggle(color))) {
                        System.out.println("Checkmate. " + color + " wins");
                        LocalTime end = LocalTime.now();
                        Long gamePeriod = ChronoUnit.MINUTES.between(start, end);
                        if (color.equals("white")) {
                            games.add(new Game(firstPlayerName, secondPlayerName, 1, 0, firstPlayerName,
                                    secondPlayerName, gamePeriod));

                        } else {
                            games.add(new Game(secondPlayerName, firstPlayerName, 0, 1, firstPlayerName,
                                    secondPlayerName, gamePeriod));

                        }
                        System.out.println("Game over!");
                    }
                }
            

                gameBoard.board = oldBoard;

                if (move.contains("draw?")) {
                    drawAvailable = true;
                }

                // Now I have to check to see if either player is in check or checkmate
                // I also have to see if there is a stalemate

                color = colorToggle(color);

            }
        } else {
            color = "white";// first player's color is black
            while (true) {

                System.out.println(gameBoard);

                System.out.println(color + " Please Make a move(e.p: d2 d4 ): ");

                String move = inputReader.nextLine();

                if (drawAvailable && move.equals("yes")) {
                    if (move.contains("draw")) {
                        System.out.println("The game is a draw.");
                        LocalTime end = LocalTime.now();
                        Long gamePeriod = ChronoUnit.MINUTES.between(start, end);
                        if (color.equals("white")) {
                            games.add(new Game("-", "-", 0.5, 0.5, firstPlayerName, secondPlayerName,
                            gamePeriod));
                        }
                    } else {
                        drawAvailable = false;
                    }
                }

                if (move.contains("resign")) {
                    System.out.println(color + " resigns");
                    System.out.println(colorToggle(color) + " wins the game!");
                    LocalTime end = LocalTime.now();
                    Long gamePeriod = ChronoUnit.MINUTES.between(start, end);
                    if (color.equals("white")) {
                        games.add(new Game(firstPlayerName, secondPlayerName, 1, 0, firstPlayerName, secondPlayerName,
                                gamePeriod));
                    } else {
                        games.add(new Game(secondPlayerName, firstPlayerName, 0, 1, firstPlayerName, secondPlayerName,
                                gamePeriod));
                    }
                    return;
                }
                if (!(gameBoard.isInCheck(color)) && gameBoard.canAnyPieceMakeAnyMove(color) == false) {
                    System.out.println("stalemate");
                }

                try {
                    gameBoard.performMove(move, color, true);
                } catch (IOException e) {
                    // Ask for user input again
                    System.out.println("Invalid move!");
                    continue;

                }

                Piece[][] oldBoard = gameBoard.board.clone();

                if (gameBoard.isInCheck(colorToggle(color))) {
                    System.out.println(colorToggle(color) + " is in check.");
                }
                if (gameBoard.isInCheck(colorToggle(color))) {
                    if (!gameBoard.canAnyPieceMakeAnyMove(colorToggle(color))) {
                        System.out.println("Checkmate. " + color + " wins");
                        LocalTime end = LocalTime.now();
                        Long gamePeriod = ChronoUnit.MINUTES.between(start, end);
                        if (color.equals("white")) {
                            games.add(new Game(secondPlayerName, firstPlayerName, 0, 1, firstPlayerName,
                                    secondPlayerName, gamePeriod));
                        } else {
                            games.add(new Game(firstPlayerName, secondPlayerName, 1, 0, firstPlayerName,
                                    secondPlayerName, gamePeriod));
                        }
                        System.out.println("Game over!");

                    }

                }

                gameBoard.board = oldBoard;

                if (move.contains("draw?")) {
                    drawAvailable = true;
                }

                // Now I have to check to see if either player is in check or checkmate
                // I also have to see if there is a stalemate

                color = colorToggle(color);

            }
        }

    }

    public static String colorToggle(String color) {
        if (color.equals("white")) {
            return "black";
        }

        return "white";
    }

    public void printMainMenu() {
        System.out.println(".............Welcom..........\nPlease Enter Your Name:");
        String playerName = inputReader.nextLine();
        signUp(playerName);
        boolean validAnswer = false;
        do {
            validAnswer = false;
            System.out.println(
                    "(e.p :1 , 2 or 3)\n1-Play chess with computer\n2-Play chess with another player\n3-sort games by time\n4-Display games\n5-fillter games by Rival Player \n6-Exit");
            int userChoice = inputReader.nextInt();
            inputReader.nextLine();
            switch (userChoice) {
                case 1:
                    playChessWithEngine(playerName);
                    askForContinuation();
                    break;
                case 2:
                    System.out.println("First player enter the color you pick(white or black)");
                    String color = inputReader.nextLine();
                    players.add(new Player(playerName));
                    playChessWithTwoPlayer(color, playerName);
                    askForContinuation();
                    break;
                case 3:
                    sortPlayerByGameTime(games, playerName);
                    askForContinuation();
                    break;
                case 4:
                    displayPlayerGames(playerName);
                    askForContinuation();
                    break;
                case 5:
                    fillterGameByRivalPlayer(playerName);
                    askForContinuation();
                    break;
                case 6:
                    System.out.println("Bye");
                    break;
                default:
                    System.out.println("The input was not in the option!");
                    validAnswer = true;
                    break;
            }
        } while (validAnswer);

    }

    public void askForContinuation() {
        boolean check = true;
        while (check) {
            System.out.println("Do you want to Start another game ?(yes or no)");
            String continuation = inputReader.nextLine();
            if (continuation.equalsIgnoreCase("yes")) {
                printMainMenu();
            } else if (continuation.equalsIgnoreCase("no")) {
                System.out.println("Bye!");
                check = false;
                break;
            } else {
                System.out.println("Enter the correct answer");
                check = true;
            }
        }
    }

    public void signUp(String playerName) {
        // check this player was existed or not
        boolean exist = false;
        for (Player player : players) {
            if (player.getPlayerName().equalsIgnoreCase(playerName)) {
                System.out.println("You Already signed up!\nWelcom " + playerName + "!");
                exist = true;

            }

        }

        if (!exist) {
            for (Player player : players) {
                if (!(player.getPlayerName().equalsIgnoreCase(playerName))) {
                    players.add(new Player(playerName));
                    break;
                }
            }
        }

    }

    public void sortPlayerByGameTime(ArrayList<Game> games, String playerName) {
        for (Player player : players) {
            if (player.getPlayerName().equalsIgnoreCase(playerName)) {
                for (int i = 0; i < games.size(); i++) {
                    for (int j = i + 1; j < games.size(); j++) {
                        if (games.get(i).getTime().compareTo(games.get(j).getTime()) > 0) {
                            Game temp = games.get(i);
                            games.set(i, games.get(j));
                            games.set(j, temp);
                        }
                    }
                }
            }
        }
        printPlayerGames(games);
    }

    public void displayPlayerGames(String playerName) {
        ArrayList<Game> fillteredGames = new ArrayList<>();
        for (Game game : games) {
            if (game.getFirstPlayerName().equalsIgnoreCase(playerName)
                    || game.getSecondPlayerName().equalsIgnoreCase(playerName)) {
                fillteredGames.add(new Game(game.getWinnerName(), game.getloserName(), game.getFirstPlayerScore(),
                        game.getSecondPlayerScore(), game.getFirstPlayerName(), game.getSecondPlayerName(),
                        game.getTime()));

            }
        }
        printPlayerGames(fillteredGames);
    }

    public void printPlayerGames(ArrayList<Game> requestedGames) {
        for (Game requestedGame : requestedGames) {
            System.out.println("Winner: " + requestedGame.getWinnerName() + "\nLosser: " + requestedGame.getloserName()
                    + "\nFirst Player Score: " + requestedGame.getFirstPlayerScore() + "\nSecond Player Score: "
                    + requestedGame.getSecondPlayerScore() + "\nFirst Player Name: "
                    + requestedGame.getFirstPlayerName()
                    + "\nSecond Player Name: " + requestedGame.getSecondPlayerName() + "\nTime: "
                    + requestedGame.getTime());
        }
    }

    public void fillterGameByRivalPlayer(String playerName) {
        ArrayList<Game> playedGame = new ArrayList<>();
        System.out.println("Please enter the name of player you played with: ");
        String rivalPlayer = inputReader.nextLine();
        for (Player player : players) {
            if (player.getPlayerName().equalsIgnoreCase(playerName)) {
                for (Game game : games) {
                    if ((game.getSecondPlayerName().contains(rivalPlayer.toLowerCase())
                            && game.getFirstPlayerName().equalsIgnoreCase(playerName))
                            || (game.getFirstPlayerName().contains(rivalPlayer)
                                    && game.getSecondPlayerName().equalsIgnoreCase(playerName))) {
                        playedGame.add(new Game(game.getWinnerName(), game.getloserName(), game.getFirstPlayerScore(),
                                game.getSecondPlayerScore(), game.getFirstPlayerName(), game.getSecondPlayerName(),
                                game.getTime()));
                    }
                }
            }
        }
        printPlayerGames(playedGame);
    }
}
