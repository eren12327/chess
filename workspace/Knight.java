// William Jenkins 2/28/25
// Piece Name: Knight
// Description: This class defines a regular Knight chess piece. It includes attributes such as color and position. It also has methods for moving and legal moves.
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;

public class Knight extends Piece {
    
    public Knight(boolean isWhite, String img_file) {
    	super(isWhite, img_file);        
    }
    
    @Override
 // Precondition: The Knight object has been initialized with a color.
 // Postcondition: Returns a string of the Knight in the format "A <color> Knight".
    public String toString() {
        String colorString;
        if (getColor()) {
            colorString = "white";
        } else {
            colorString = "black";
        }
        return "A " + colorString + " Knight";
    }
    
 // Precondition: The board is an 8x8 grid, and the start square is valid. 
 // Postcondition: Returns a list of valid squares the knight can control within board boundaries.
    public ArrayList<Square> getControlledSquares(Square[][] board, Square start) {
    	ArrayList<Square> controlledSquares = new ArrayList<>();

        int[][] knightMoves = {
            {-2, -1}, {-2, 1}, {2, -1}, {2, 1},
            {-1, -2}, {1, -2}, {-1, 2}, {1, 2} 
        };

        int row = start.getRow();
        int col = start.getCol();

        for (int[] move : knightMoves) {
            int newRow = row + move[0];
            int newCol = col + move[1];

            if (newRow >= 0 && newRow < 8 && newCol >= 0 && newCol < 8) {
                controlledSquares.add(board[newRow][newCol]);
            }
        }

        return controlledSquares;
    }
    

 // Precondition: The Board object and start square are valid, with an 8x8 grid initialized.
 // Postcondition: Returns a list of legal squares the knight can move to, avoiding same-color pieces.
    public ArrayList<Square> getLegalMoves(Board b, Square start){
    	ArrayList<Square> moves = new ArrayList<>();
        int row = start.getRow();
        int col = start.getCol();
        Square[][] board = b.getSquareArray();

        int[][] moveOffsets = {
            {-2, -1}, {-2, 1}, {2, -1}, {2, 1}, 
            {-1, -2}, {-1, 2}, {1, -2}, {1, 2}
        };

        for (int[] offset : moveOffsets) {
            int newRow = row + offset[0];
            int newCol = col + offset[1];

            if (newRow >= 0 && newRow < 8 && newCol >= 0 && newCol < 8) {
                Square targetSquare = board[newRow][newCol];

                if (!targetSquare.isOccupied() || targetSquare.getOccupyingPiece().getColor() != super.getColor()) {
                    moves.add(targetSquare);
                }
            }
        }

        return moves;
    }
}