// Name: Eren;
// Date: 4/8/25


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.*;

//You will be implmenting a part of a function and a whole function in this document. Please follow the directions for the 
//suggested order of completion that should make testing easier.
@SuppressWarnings("serial")
public class Board extends JPanel implements MouseListener, MouseMotionListener {
	// Resource location constants for piece images
    private static final String RESOURCES_WBISHOP_PNG = "wbishop.png";
	private static final String RESOURCES_BBISHOP_PNG = "bbishop.png";
	private static final String RESOURCES_WKNIGHT_PNG = "wknight.png";
	private static final String RESOURCES_BKNIGHT_PNG = "bknight.png";
	private static final String RESOURCES_WROOK_PNG = "wrook.png";
	private static final String RESOURCES_BROOK_PNG = "brook.png";
	private static final String RESOURCES_WKING_PNG = "wking.png";
	private static final String RESOURCES_BKING_PNG = "bking.png";
	private static final String RESOURCES_BQUEEN_PNG = "bqueen.png";
	private static final String RESOURCES_WQUEEN_PNG = "wqueen.png";
	private static final String RESOURCES_WPAWN_PNG = "wpawn.png";
	private static final String RESOURCES_BPAWN_PNG = "bpawn.png";
	
	// Logical and graphical representations of board
	private final Square[][] board;
    private final GameWindow g;
 
    //contains true if it's white's turn.
    private boolean whiteTurn;

    //if the player is currently dragging a piece this variable contains it.
    private Piece currPiece;
    private Square fromMoveSquare;
    
    //used to keep track of the x/y coordinates of the mouse.
    private int currX;
    private int currY;
    

    
    public Board(GameWindow g) {
        this.g = g;
        board = new Square[8][8];
        setLayout(new GridLayout(8, 8, 0, 0));
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        for (int i=0; i<8; i++) {
            for (int k=0; k<8; k++) {
                if (i%2==0) {
                    if (k%2==0) {
                        board[i][k] = new Square(this, true, i,k);
                        this.add(board[i][k]);
                    } else {
                        board[i][k] = new Square(this, false, i,k);
                        this.add(board[i][k]);
                    }              
                } else {
                    if (k%2==0) {
                        board[i][k] = new Square(this, false, i,k);
                        this.add(board[i][k]);
                    } else {
                        board[i][k] = new Square(this, true, i,k);
                        this.add(board[i][k]);
                    } 
                }


            }
        }
        initializePieces();

        this.setPreferredSize(new Dimension(400, 400));
        this.setMaximumSize(new Dimension(400, 400));
        this.setMinimumSize(this.getPreferredSize());
        this.setSize(new Dimension(400, 400));

        whiteTurn = true;

    }

    
	

    
    private void initializePieces() {
        board[0][7].put(new Rook(false, RESOURCES_BROOK_PNG));
        board[7][0].put(new Rook(true, RESOURCES_WROOK_PNG));
        board[7][1].put(new Knight(true, RESOURCES_WKNIGHT_PNG));
        board[7][2].put(new Bishop(true, RESOURCES_WBISHOP_PNG));
        board[7][3].put(new King(true, RESOURCES_WKING_PNG));
        board[7][4].put(new Rook(true, RESOURCES_WQUEEN_PNG));
        board[7][5].put(new Bishop(true, RESOURCES_WBISHOP_PNG));
        board[7][6].put(new Knight(true, RESOURCES_WKNIGHT_PNG));
        board[7][7].put(new Rook(true, RESOURCES_WROOK_PNG));
        board[0][0].put(new Rook(false, RESOURCES_BROOK_PNG));
        board[0][1].put(new Knight(false, RESOURCES_BKNIGHT_PNG));
        board[0][2].put(new Bishop(false, RESOURCES_BBISHOP_PNG));
        board[0][3].put(new King(false, RESOURCES_BKING_PNG));
        board[0][4].put(new Rook(false, RESOURCES_BQUEEN_PNG));
        board[0][5].put(new Bishop(false, RESOURCES_BBISHOP_PNG));
        board[0][6].put(new Knight(false, RESOURCES_BKNIGHT_PNG));

       
        for (int i=0; i<8; i++) {
            board[1][i].put(new Pawn(false, RESOURCES_BPAWN_PNG));
        }
        for (int i=0; i<8; i++) {
            board[6][i].put(new Pawn(true, RESOURCES_WPAWN_PNG));
        }
    }

    public Square[][] getSquareArray() {
        return this.board;
    }

    public boolean getTurn() {
        return whiteTurn;
    }

    public void setCurrPiece(Piece p) {
        this.currPiece = p;
    }

    public Piece getCurrPiece() {
        return this.currPiece;
    }
    public boolean isInCheck(boolean kingColor) {
        Square kingPosition = null;
        ArrayList<Square> allControlled = new ArrayList<Square>();
        for (int i=0; i<board.length; i++) {
            
            for (int j=0; j<board[i].length; j++) {
                Piece x = board[i][j].getOccupyingPiece();
                if (x != null) {
                if ((x.getColor() == kingColor) && (x instanceof King)) {
                    kingPosition = board[i][j];
                    break;
                } else if (x.getColor() != kingColor) {
                   ArrayList<Square> controlledThis = x.getControlledSquares(board, board[i][j]);

                   for (int w=0; w<controlledThis.size(); w++) {
                        allControlled.add(controlledThis.get(w));
                   }
                }
                }
                            }
        }
        if (kingPosition != null) {
            for (int i=0; i<allControlled.size(); i++) {
                if ((allControlled.get(i).getRow()==kingPosition.getRow()) && (allControlled.get(i).getCol()==kingPosition.getCol())) {
                    return true;
    
                }
            }
        }
        return false;
       
    }
    




    @Override
    public void paintComponent(Graphics g) {
     
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                Square sq = board[x][y];
                if(sq == fromMoveSquare)
                	 sq.setBorder(BorderFactory.createLineBorder(Color.blue));
                sq.paintComponent(g);
                
            }
        }
    	if (currPiece != null) {
            if ((currPiece.getColor() && whiteTurn)
                    || (!currPiece.getColor()&& !whiteTurn)) {
                final Image img = currPiece.getImage();
                g.drawImage(img, currX, currY, null);
            }
        }
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        currX = e.getX();
        currY = e.getY();

        Square sq = (Square) this.getComponentAt(new Point(e.getX(), e.getY()));

        if (sq.isOccupied()) {
            currPiece = sq.getOccupyingPiece();
            fromMoveSquare = sq;
            if (!currPiece.getColor() && whiteTurn) {
                currPiece = null;
                return;
            } else if  (currPiece.getColor() && !whiteTurn) {
                currPiece = null;
                return;
            }
              
            sq.setDisplay(false);
        }
        repaint();
    }

    // PreCondition: needs a valid mouseEvent 
    // PostCondition: Moves piece and switches turn if all variables are defined
    @Override
    public void mouseReleased(MouseEvent e) {
        Square endSquare = (Square) this.getComponentAt(new Point(e.getX(), e.getY()));

for (Square[] row : board) {
    for (Square s : row) {
        s.setBorder(null);
    }
}
    fromMoveSquare.setDisplay(true);

    System.out.println("This occured " +isInCheck(whiteTurn));
if (currPiece != null && endSquare != null) {
    ArrayList<Square> possibleMoves = currPiece.getLegalMoves(this, fromMoveSquare);
    for (int i = 0; i < possibleMoves.size(); i++) {
        Square possibleMove = possibleMoves.get(i);
        if (possibleMove == endSquare) {
            Piece pieceCaptured = endSquare.getOccupyingPiece();
            endSquare.put(currPiece);
            fromMoveSquare.put(null);
            if (isInCheck(whiteTurn)) {
                endSquare.put(pieceCaptured);
                fromMoveSquare.put(currPiece);
            } else {
                whiteTurn = !whiteTurn;
            }
            
        }
    }
}

if (endSquare != null && currPiece != null) {
    currPiece.draw(getGraphics(), fromMoveSquare);
    currPiece = null;
}

repaint();

    }


    @Override
    public void mouseDragged(MouseEvent e) {
        currX = e.getX() - 24;
        currY = e.getY() - 24;

        if(currPiece!= null) {
        	for(Square s: currPiece.getLegalMoves(this, fromMoveSquare)) {
        		s.setBorder(BorderFactory.createLineBorder(Color.MAGENTA));
        	}
        	
        }
        
        repaint();
    }
    
    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

}