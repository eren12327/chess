
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;

//you will need to implement two functions in this file.
public class Piece {
    private final boolean color;
    private BufferedImage img;
    
    public Piece(boolean isWhite, String img_file) {
        this.color = isWhite;
        
        try {
            if (this.img == null) {
              this.img = ImageIO.read(getClass().getResource(img_file));
            }
          } catch (IOException e) {
            System.out.println("File not found: " + e.getMessage());
          }
    }
    
    

    
    public boolean getColor() {
        return color;
    }
    
    public Image getImage() {
        return img;
    }
    
    public void draw(Graphics g, Square currentSquare) {
        int x = currentSquare.getX();
        int y = currentSquare.getY();
        
        g.drawImage(this.img, x, y, null);
    }
    
    // PreCondition: Needs a square grid structure and the current piece's position
    // PostCondition: Returns ArrayList of Squares of where that piece can control, capture, etc.
    public ArrayList<Square> getControlledSquares(Square[][] b, Square start) {
      ArrayList<Square> moves = new ArrayList<Square>();
      for (int cr = start.getRow(); cr < 8; cr++) {
          if (start.getRow() != cr) {
              Square tSquare = b[cr][start.getCol()];
              if (tSquare.getOccupyingPiece() == null) {
                  moves.add(tSquare);
              } else {
                  moves.add(tSquare);
                  if (tSquare.getOccupyingPiece().getColor() != start.getOccupyingPiece().getColor()) {
                      break;
                  }
              }
          }
      }
  
      for (int cr = start.getRow(); cr >= 0; cr--) {
          if (start.getRow() != cr) {
              Square tSquare = b[cr][start.getCol()];
              if (tSquare.getOccupyingPiece() == null) {
                  moves.add(tSquare);
              } else {
                  moves.add(tSquare);
                  if (tSquare.getOccupyingPiece().getColor() != start.getOccupyingPiece().getColor()) {
                      break;
                  }
              }
          }
      }
  
      for (int rc = start.getCol(); rc < 8; rc++) {
          if (start.getCol() != rc) {
              Square tSquare = b[start.getRow()][rc];
              if (tSquare.getOccupyingPiece() == null) {
                  moves.add(tSquare);
              } else {
                  moves.add(tSquare);
                  if (tSquare.getOccupyingPiece().getColor() != start.getOccupyingPiece().getColor()) {
                      break;
                  }
              }
          }
      }
  
      for (int rc = start.getCol(); rc >= 0; rc--) {
          if (start.getCol() != rc) {
              Square tSquare = b[start.getRow()][rc];
              if (tSquare.getOccupyingPiece() == null) {
                  moves.add(tSquare);
              } else {
                  moves.add(tSquare);
                  if (tSquare.getOccupyingPiece().getColor() != start.getOccupyingPiece().getColor()) {
                      break;
                  }
              }
          }
      }
  
      return moves;
  }
  

  // PreCondition: Requires board and square instances of the whole board and of the original starting position.
  // PostCondition: Returns arrayList of all the legal moves.

public ArrayList<Square> getLegalMoves(Board b, Square start) {
  ArrayList<Square> moves = new ArrayList<Square>();

  for (int cr = start.getRow(); cr < 8; cr++) {
      if (start.getRow() != cr) {
          Square tSquare = b.getSquareArray()[cr][start.getCol()];
          if (tSquare.getOccupyingPiece() == null) {
              moves.add(tSquare);
          } else {
              if (tSquare.getOccupyingPiece().getColor() != start.getOccupyingPiece().getColor()) {
                  moves.add(tSquare);
              }
              break;
          }
      }
  }

  for (int cr = start.getRow(); cr >= 0; cr--) {
      if (start.getRow() != cr) {


          Square tSquare = b.getSquareArray()[cr][start.getCol()];
          if (tSquare.getOccupyingPiece() == null) {

              moves.add(tSquare);
          } else {


              if (tSquare.getOccupyingPiece().getColor() != start.getOccupyingPiece().getColor()) {
                  moves.add(tSquare);
              }
              break;
          }
      }
  }
  for (int rc = start.getCol(); rc < 8; rc++) {
      if (start.getCol() != rc) {
          Square tSquare = b.getSquareArray()[start.getRow()][rc];
          if (tSquare.getOccupyingPiece() == null) {
              moves.add(tSquare);
          } else {
              if (tSquare.getOccupyingPiece().getColor() != start.getOccupyingPiece().getColor()) {
                  moves.add(tSquare);
              }
              break;
          }
      }
  }

  for (int rc = start.getCol(); rc >= 0; rc--) {
      if (start.getCol() != rc) {
          Square tSquare = b.getSquareArray()[start.getRow()][rc];
          if (tSquare.getOccupyingPiece() == null) {


              moves.add(tSquare);
          } else {
              if (tSquare.getOccupyingPiece().getColor() != start.getOccupyingPiece().getColor()) {
                  moves.add(tSquare);
              }



              break;
          }
      }
  }

  return moves;
}
}