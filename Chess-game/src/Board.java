import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Board {
    public Piece[][] board = new Piece[8][8];

    public Board() {
        this.initialize();
    }

    private void initialize() {
        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board[0].length; y++) {
                board[x][y] = null;
            }
        }

        // White pawns
        for (int x = 0; x < 8; x++) {
            board[1][x] = new Pawn("white");
        }

        // Black pawns
        for (int x = 0; x < 8; x++) {
            board[6][x] = new Pawn("black");
        }

        // Rooks
        board[0][0] = new Rook("white");
        board[0][7] = new Rook("white");
        board[7][7] = new Rook("black");
        board[7][0] = new Rook("black");

        // Knights
        board[0][1] = new Knight("white");
        board[0][6] = new Knight("white");
        board[7][6] = new Knight("black");
        board[7][1] = new Knight("black");

        // Bishops
        board[0][2] = new Bishop("white");
        board[0][5] = new Bishop("white");
        board[7][2] = new Bishop("black");
        board[7][5] = new Bishop("black");

        // Queens
        board[0][3] = new Queen("white");
        board[7][3] = new Queen("black");

        // Kings
        board[0][4] = new King("white");
        board[7][4] = new King("black");

    }

    // print board
    public boolean isInCheck(String color) {
        int[] kingPos = getKingPos(color);
        int row = kingPos[0];
        int col = kingPos[1];

        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board[0].length; y++) {
                if (board[x][y] != null) {
                    if (board[x][y].validateMove(board, x, y, row, col) && !board[x][y].getColor().equals(color)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public String validMoves(String color) {
        ArrayList<String> capturingMoves = new ArrayList<>();
        ArrayList<String> validMoves = new ArrayList<>();
        ArrayList<int[]> bishopPos = getBiShopPos(color);
        ArrayList<int[]> knightPos = getKnightPos(color);
        ArrayList<int[]> pawnsPos = getPawnPos(color);
        int[] queenPos = getQueenPos(color);
        int[] kingPos = getKingPos(color);
        ArrayList<int[]> rooksPos = getRookPos(color);

        int rowKing = kingPos[0];
        int colKing = kingPos[1];
        int rowQueen = queenPos[0];
        int colQueen = queenPos[1];

        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board[0].length; y++) {
                for (int[] n : knightPos) {
                    if (board[n[0]][n[1]].validateMove(board, n[0], n[1], x, y) && !(n[0] == x && n[1] == y)) {
                        if (!(board[x][y] == null) && !(board[x][y].getColor().equals(color))) {
                            capturingMoves.add(coordinatesToMoveString(n[0], n[1], x, y));
                        } else if (board[x][y] == null) {
                            validMoves.add(coordinatesToMoveString(n[0], n[1], x, y));

                        }
                    }

                }

            }
        }
        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board[0].length; y++) {
                for (int[] b : bishopPos) {
                    if (board[b[0]][b[1]].validateMove(board, b[0], b[1], x, y) && !(b[0] == x && b[1] == y)) {
                        if (!(board[x][y] == null) && !board[x][y].getColor().equals(color)) {
                            capturingMoves.add(coordinatesToMoveString(b[0], b[1], x, y));
                        } else if (board[x][y] == null) {
                            validMoves.add(coordinatesToMoveString(b[0], b[1], x, y));

                        }
                    }

                }

            }
        }
        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board[0].length; y++) {
                if (board[rowKing][colKing].validateMove(board, rowKing, colKing, x, y)
                        && !(colKing == y && rowKing == x)) {
                    if (!(board[x][y] == null) && !board[x][y].getColor().equals(color)) {
                        capturingMoves.add(coordinatesToMoveString(rowKing, colKing, x, y));
                    } else if (board[x][y] == null) {
                        validMoves.add(coordinatesToMoveString(rowKing, colKing, x, y));

                    }
                }

            }
        }
        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board[0].length; y++) {
                if (board[rowQueen][colQueen].validateMove(board, rowQueen, colQueen, x, y)
                        && !(colQueen == y && rowQueen == x)) {
                    if (!(board[x][y] == null) && !board[x][y].getColor().equals(color)) {
                        capturingMoves.add(coordinatesToMoveString(rowQueen, colQueen, x, y));
                    } else if (board[x][y] == null) {
                        validMoves.add(coordinatesToMoveString(rowQueen, colQueen, x, y));

                    }
                }

            }
        }
        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board[0].length; y++) {
                for (int[] p : pawnsPos) {
                    if (board[p[0]][p[1]].validateMove(board, p[0], p[1], x, y) && !(p[0] == x && p[1] == y)) {
                        if (!(board[x][y] == null) && !board[x][y].getColor().equals(color)) {
                            capturingMoves.add(coordinatesToMoveString(p[0], p[1], x, y));
                        } else if (board[x][y] == null) {
                            validMoves.add(coordinatesToMoveString(p[0], p[1], x, y));

                        }
                    }

                }

            }
        }

        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board[0].length; y++) {
                for (int[] r : rooksPos) {
                    if (board[r[0]][r[1]].validateMove(board, r[0], r[1], x, y) && !(r[0] == x && r[1] == y)) {
                        if (!(board[x][y] == null) && !board[x][y].getColor().equals(color)) {
                            capturingMoves.add(coordinatesToMoveString(r[0], r[1], x, y));
                        } else if (board[x][y] == null) {
                            validMoves.add(coordinatesToMoveString(r[0], r[1], x, y));

                        }
                    }

                }

            }
        }

        return generateRandomValidMove(validMoves, capturingMoves);

    }

    // Generate random valid move for computer
    public String generateRandomValidMove(ArrayList<String> validMoves, ArrayList<String> capturingMoves) {
        Random rand = new Random();
        String computerMove;
        if (!(capturingMoves.size() == 0)) {
            int randomCapturingMove = rand.nextInt(capturingMoves.size());
            computerMove = capturingMoves.get(randomCapturingMove);
        } else {
            int randomItem = rand.nextInt(validMoves.size());
            computerMove = validMoves.get(randomItem);
        }

        return computerMove;

    }

    // check computer moves
    public void performComputerMove(String move, String color, boolean actuallyMove) throws IOException {

        int[] moveArray = parseInput(move);

        if (board[moveArray[0]][moveArray[1]] == null) {
            performComputerMove(validMoves(color), color, true);
        }

        if (!board[moveArray[0]][moveArray[1]].getColor().equals(color)) {
            performComputerMove(validMoves(color), color, true);
        }

        if (board[moveArray[2]][moveArray[3]] != null) {
            if (board[moveArray[2]][moveArray[3]].getColor().equals(color)) {
                performComputerMove(validMoves(color), color, true);
            }
        }

        if (board[moveArray[0]][moveArray[1]].validateMove(board, moveArray[0], moveArray[1], moveArray[2],
                moveArray[3])) {
            // This means the move was valid

            if (actuallyMove) {
                // Switch the two spots on the board because the move was valid
                board[moveArray[2]][moveArray[3]] = board[moveArray[0]][moveArray[1]];
                board[moveArray[0]][moveArray[1]] = null;
            }

            if (board[moveArray[2]][moveArray[3]] != null) {
                if (board[moveArray[2]][moveArray[3]].getClass().isInstance(new King("white"))) {
                    if (actuallyMove) {
                        ((King) board[moveArray[2]][moveArray[3]]).hasMoved = true;
                    }
                    // This Piece is a King
                    if (((King) board[moveArray[2]][moveArray[3]]).castled) {
                        if (moveArray[3] - moveArray[1] == 2) {
                            board[moveArray[2]][moveArray[3] - 1] = board[moveArray[2]][moveArray[3] + 1];
                            board[moveArray[2]][moveArray[3] + 1] = null;
                        } else {
                            board[moveArray[2]][moveArray[3] + 1] = board[moveArray[2]][moveArray[3] - 1];
                            board[moveArray[2]][moveArray[3] - 1] = null;
                        }
                        ((King) board[moveArray[2]][moveArray[3]]).castled = false;
                    }
                }
            }

        } else {
            performComputerMove(validMoves(color), color, true);
        }

        // Rules dealing with pawn
        if (actuallyMove) {
            Piece piece = board[moveArray[2]][moveArray[3]];
            // This way it gets toggled the next time it moves

            if (piece != null) {
                if (piece.getClass().isInstance(new Pawn("white"))) {
                    // The piece is a pawn
                    piece.hasMoved = true;

                    // Promotion for pawn
                    Piece replacement;
                    if (move.split(" ").length < 3) {
                        move += " s";
                    }
                    if (piece.getColor().equals("white")) {
                        if (moveArray[2] == 7) {
                            switch (move.split(" ")[2].charAt(0)) {
                                case 'N':
                                    replacement = new Knight("white");
                                    break;
                                case 'B':
                                    replacement = new Bishop("white");
                                    break;
                                default:
                                    replacement = new Queen("white");
                                    break;
                            }
                            board[moveArray[2]][moveArray[3]] = replacement;
                        }
                    } else {
                        if (moveArray[2] == 0) {
                            switch (move.split(" ")[2].charAt(0)) {
                                case 'N':
                                    replacement = new Knight("black");
                                    break;
                                case 'B':
                                    replacement = new Bishop("black");
                                    break;
                                default:
                                    replacement = new Queen("black");
                                    break;
                            }
                            board[moveArray[2]][moveArray[3]] = replacement;
                        }
                    }

                }
            }

        }
        if (isInCheck(color)) {
            if (actuallyMove) {
                // Switch the two spots on the board because the move was valid
                board[moveArray[0]][moveArray[1]] = board[moveArray[2]][moveArray[3]];
                board[moveArray[2]][moveArray[3]] = null;
            }
            performComputerMove(validMoves(color), color, true);

        }

    }

    public boolean checkValidMoves(String move, String color, boolean actuallyMove) throws IOException {
        int[] moveArray = parseInput(move);

        if (board[moveArray[0]][moveArray[1]] == null) {
            throw new IOException();
        }

        if (!board[moveArray[0]][moveArray[1]].getColor().equals(color)) {
            throw new IOException();
        }

        if (board[moveArray[2]][moveArray[3]] != null) {

            if (board[moveArray[2]][moveArray[3]].getColor().equals(color)) {
                throw new IOException();
            }
        }

        if (board[moveArray[0]][moveArray[1]].validateMove(board, moveArray[0], moveArray[1], moveArray[2],
                moveArray[3])) {
            return true;
        }
        return false;
    }


    public void performMove(String move, String color, boolean actuallyMove) throws IOException {
        if (move.length() > 6) {
            throw new IOException();
        }
        String alfabet = "abcdefgh";
        String num = "12345678";
        String space = "  ";
     
        if (!(alfabet.contains(String.valueOf(move.charAt(0))) && num.contains(String.valueOf(move.charAt(1))) && space.contains(String.valueOf(move.charAt(2)))  && alfabet.contains(String.valueOf(move.charAt(3))) && num.contains(String.valueOf(move.charAt(4)))))  {
            throw new IOException();
        }
        int[] moveArray = parseInput(move);

        if (board[moveArray[0]][moveArray[1]] == null) {
            throw new IOException();
        }

        if (!board[moveArray[0]][moveArray[1]].getColor().equals(color)) {
            throw new IOException();
        }

        if (board[moveArray[2]][moveArray[3]] != null) {
            if (board[moveArray[2]][moveArray[3]].getColor().equals(color)) {
                throw new IOException();
            }
        }

        if (board[moveArray[0]][moveArray[1]].validateMove(board, moveArray[0], moveArray[1], moveArray[2],
                moveArray[3])) {

            if (actuallyMove) {
                // Switch the two spots on the board because the move was valid
                board[moveArray[2]][moveArray[3]] = board[moveArray[0]][moveArray[1]];
                board[moveArray[0]][moveArray[1]] = null;
            }

            if (board[moveArray[2]][moveArray[3]] != null) {
                if (board[moveArray[2]][moveArray[3]].getClass().isInstance(new King("white"))) {
                    if (actuallyMove) {
                        ((King) board[moveArray[2]][moveArray[3]]).hasMoved = true;
                    }
                    // This Piece is a King
                    if (((King) board[moveArray[2]][moveArray[3]]).castled) {
                        if (moveArray[3] - moveArray[1] == 2) {
                            board[moveArray[2]][moveArray[3] - 1] = board[moveArray[2]][moveArray[3] + 1];
                            board[moveArray[2]][moveArray[3] + 1] = null;
                        } else {
                            board[moveArray[2]][moveArray[3] + 1] = board[moveArray[2]][moveArray[3] - 1];
                            board[moveArray[2]][moveArray[3] - 1] = null;
                        }
                        ((King) board[moveArray[2]][moveArray[3]]).castled = false;
                    }
                }
            }

        } else {
            throw new IOException();
        }

        // Rules for pawns
        if (actuallyMove) {
            Piece piece = board[moveArray[2]][moveArray[3]];
            // This way it gets toggled the next time it moves

            if (piece != null) {
                if (piece.getClass().isInstance(new Pawn("white"))) {
                    // The piece is a pawn
                    piece.hasMoved = true;

                    // Promotion for pawn
                    Piece replacement;
                    if (piece.getColor().equals("white")) {
                        if (moveArray[2] == 7) {
                            switch (askForPawnPromotion()) {
                                case 1:
                                    replacement = new Knight("white");
                                    break;
                                case 2:
                                    replacement = new Bishop("white");
                                    break;
                                case 3:
                                    replacement = new Queen("white");
                                    break;
                                case 4:
                                    replacement = new Rook("white");
                                    break;
                                default:
                                    replacement = new Queen("white");
                                    break;
                            }
                            board[moveArray[2]][moveArray[3]] = replacement;
                        }
                    } else {
                        if (moveArray[2] == 0) {
                            switch (askForPawnPromotion()) {
                                case 1:
                                    replacement = new Knight("black");
                                    break;
                                case 2:
                                    replacement = new Bishop("black");
                                    break;
                                case 3:
                                    replacement = new Queen("black");
                                    break;
                                case 4:
                                    replacement = new Rook("black");
                                    break;
                                default:
                                    replacement = new Queen("black");
                                    break;
                            }
                            board[moveArray[2]][moveArray[3]] = replacement;
                        }
                    }

                }
            }

        }
        if (isInCheck(color)) {
            if (actuallyMove) {
                // Switch the two spots on the board because the move was valid
                board[moveArray[0]][moveArray[1]] = board[moveArray[2]][moveArray[3]];
                board[moveArray[2]][moveArray[3]] = null;
            }
            // Because the current player put him/herself in check
            // I need to find out what is going to happen to the board in this case
            System.out.println("current player is in check");
            throw new IOException();
        }

    }

    public int askForPawnPromotion() {
        System.out.println("Choose one of the blew option for promotion: \n1-Knight\n2-Queen\n3-Bishop\n4-Rook");
        int promotion = Chess.getNumberInput();
        return promotion;
    }

    public static int[] parseInput(String move) {
        int[] returnArray = new int[4];

        String[] split = move.split(" ");
        returnArray[1] = charToInt(Character.toLowerCase(split[0].charAt(0)));// col
        returnArray[0] = Integer.parseInt(move.charAt(1) + "") - 1;// row

        returnArray[3] = charToInt(Character.toLowerCase(split[1].charAt(0)));// col
        returnArray[2] = Integer.parseInt(split[1].charAt(1) + "") - 1;// row
        return returnArray;

    }

    // Returns an integer corresponding to the user input

    public static int charToInt(char ch) {
        switch (ch) {
            case 'a':
                return 0;
            case 'b':
                return 1;
            case 'c':
                return 2;
            case 'd':
                return 3;
            case 'e':
                return 4;
            case 'f':
                return 5;
            case 'g':
                return 6;
            case 'h':
                return 7;
            default:
                return 8;
        }
    }

    private int[] getKingPos(String color) {
        int row = 0, col = 0;

        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board[0].length; y++) {
                if (board[x][y] != null) {
                    if (board[x][y].getClass().isInstance(new King("white")) && board[x][y].getColor().equals(color)) {
                        row = x;
                        col = y;
                    }
                }
            }
        }
        int[] returnArray = new int[2];
        returnArray[0] = row;
        returnArray[1] = col;

        return returnArray;

    }

    private int[] getQueenPos(String color) {
        int row = 0, col = 0;

        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board[0].length; y++) {
                if (board[x][y] != null) {
                    if (board[x][y].getClass().isInstance(new Queen("white")) && board[x][y].getColor().equals(color)) {
                        row = x;
                        col = y;
                    }
                }
            }
        }
        int[] returnArray = new int[2];
        returnArray[0] = row;
        returnArray[1] = col;

        return returnArray;

    }

    private ArrayList<int[]> getRookPos(String color) {
        int row = 0, col = 0;
        ArrayList<int[]> rooksPos = new ArrayList<>();
        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board[0].length; y++) {
                if (board[x][y] != null) {
                    if (board[x][y].getClass().isInstance(new Rook("white")) && board[x][y].getColor().equals(color)) {
                        int[] returnArray = new int[2];
                        row = x;
                        col = y;
                        returnArray[0] = row;
                        returnArray[1] = col;
                        rooksPos.add(returnArray);

                    }
                }
            }
        }

        return rooksPos;

    }

    private ArrayList<int[]> getPawnPos(String color) {
        int row = 0, col = 0;
        ArrayList<int[]> pawnsPos = new ArrayList<>();
        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board[0].length; y++) {
                if (board[x][y] != null) {
                    if (board[x][y].getClass().isInstance(new Pawn("white")) && board[x][y].getColor().equals(color)) {
                        int[] returnArray = new int[2];
                        row = x;
                        col = y;
                        returnArray[0] = row;
                        returnArray[1] = col;
                        pawnsPos.add(returnArray);

                    }
                }
            }
        }

        return pawnsPos;

    }

    private ArrayList<int[]> getKnightPos(String color) {
        int row = 0, col = 0;
        ArrayList<int[]> knightsPos = new ArrayList<>();
        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board[0].length; y++) {
                if (board[x][y] != null) {
                    if (board[x][y].getClass().isInstance(new Knight("white"))
                            && board[x][y].getColor().equals(color)) {
                        int[] returnArray = new int[2];
                        row = x;
                        col = y;
                        returnArray[0] = row;
                        returnArray[1] = col;
                        knightsPos.add(returnArray);

                    }
                }
            }
        }

        return knightsPos;

    }

    private ArrayList<int[]> getBiShopPos(String color) {
        int row = 0, col = 0;
        ArrayList<int[]> bishopsPos = new ArrayList<>();
        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board[0].length; y++) {
                if (board[x][y] != null) {
                    if (board[x][y].getClass().isInstance(new Bishop("white"))
                            && board[x][y].getColor().equals(color)) {
                        int[] returnArray = new int[2];
                        row = x;
                        col = y;
                        returnArray[0] = row;
                        returnArray[1] = col;
                        bishopsPos.add(returnArray);

                    }
                }
            }
        }

        return bishopsPos;

    }

    public boolean canAnyPieceMakeAnyMove(String color) {

        Piece[][] oldBoard = board.clone();

        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board[0].length; y++) {
                // Check this piece against every other piece
                for (int w = 0; w < board.length; w++) {
                    for (int z = 0; z < board[0].length; z++) {
                        try {
                            if (board[x][y] != null) {
                                if (board[x][y].getColor().equals(color)) {
                                    checkValidMoves(coordinatesToMoveString(x, y, w, z), board[x][y].getColor(), false);
                                    board = oldBoard;
                                    return true;
                                }
                            }
                            board = oldBoard;
                        } catch (Exception e) {
                            board = oldBoard;
                        }
                    }
                }
            }
        }

        board = oldBoard;
        return false;
    }

    private String coordinatesToMoveString(int row, int col, int newRow, int newCol) {

        String returnString = "";
        // current col is converting to String
        switch (col) {
            case 0:
                returnString += 'a';
                break;
            case 1:
                returnString += 'b';
                break;
            case 2:
                returnString += 'c';
                break;
            case 3:
                returnString += 'd';
                break;
            case 4:
                returnString += 'e';
                break;
            case 5:
                returnString += 'f';
                break;
            case 6:
                returnString += 'g';
                break;
            case 7:
                returnString += 'h';
                break;
            default:
                returnString += 'a';
                break;
        }
        // current row
        int addInt = row + 1;

        returnString += addInt + "";
        // space between moves
        returnString += " ";
        // new col is generating...
        switch (newCol) {
            case 0:
                returnString += 'a';
                break;
            case 1:
                returnString += 'b';
                break;
            case 2:
                returnString += 'c';
                break;
            case 3:
                returnString += 'd';
                break;
            case 4:
                returnString += 'e';
                break;
            case 5:
                returnString += 'f';
                break;
            case 6:
                returnString += 'g';
                break;
            case 7:
                returnString += 'h';
                break;
            default:
                returnString += 'a';
                break;
        }

        addInt = newRow + 1;

        returnString += addInt + "";
        return returnString;
    }

    public boolean staleMate(String color) {
        return false;
    }

    public String toString() {
        String boardRow = "";
        int rank = 0;
        // each line of board
        for (Piece[] pieces : board) {
            int file = 0;
            // each piece on board
            for (Piece piece : pieces) {
                if (piece == null) {
                    if (rank % 2 == 0) {// even row
                        if (file % 2 == 0) {
                            boardRow += "##";
                        } else {
                            boardRow += "  ";
                        }
                    } else {// odd row
                        if (file % 2 == 0) {
                            boardRow += "  ";
                        } else {
                            boardRow += "##";
                        }
                    }
                } else {
                    boardRow += piece;
                }
                boardRow += " ";
                file++;
            }
            rank++;
            boardRow += "\n";
        }

        String reverseString = "";

        reverseString += "   a  b  c  d  e  f  g  h \n";
        String[] stringSplit = boardRow.split("\n");
        for (int x = stringSplit.length - 1; x >= 0; x--) {
            reverseString += x + 1 + "  " + stringSplit[x] + "\n";
        }

        return reverseString;
    }
}
