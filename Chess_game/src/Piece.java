public abstract class Piece {
    public String color;
	public boolean hasMoved;// for pawn
	
	public abstract String getColor();

	public abstract boolean validateMove(Piece[][] board, int currentRow, int currentCol, int newRow, int newCol);
}
