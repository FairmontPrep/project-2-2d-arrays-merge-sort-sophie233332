import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;

public class GameBoard extends JFrame {
    public int SIZE = 8;
    private JPanel[][] squares = new JPanel[SIZE][SIZE]; 
    private Color[][] piecesArray; // Store colors for pieces

    public GameBoard() {
        setTitle("Color Board");
        setSize(750, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(SIZE, SIZE));

        // Initialize the board with panels
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                squares[row][col] = new JPanel();
                squares[row][col].setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Adds borders for clarity
                add(squares[row][col]);
            }
        }

        // Initialize color array
        piecesArray = new Color[SIZE][SIZE]; 
        loadPieces();
        populateBoard();
    }

    private void loadPieces() {
        // Define the color assignments
        Map<String, Color> colorMap = new HashMap<>();
        colorMap.put("yellow", Color.YELLOW);
        colorMap.put("black", Color.BLACK);
        colorMap.put("red", Color.RED);
        colorMap.put("brightYellow", new Color(255, 255, 102));
        colorMap.put("green", Color.GREEN);

        // Define the coordinates for each color
        Map<Color, int[]> colorAssignments = new HashMap<>();
        colorAssignments.put(colorMap.get("yellow"), new int[]{
            3,0, 3,1, 4,0, 4,1, 1,3, 1,7, 2,7, 4,7, 5,4, 5,5, 5,6, 6,5, 6,3, 7,3, 7,6, // Original yellow coordinates
            5,1, 6,1, 7,1, 5,7, 6,6, 6,7, 7,7, 7,4, 7,5, 6,4, 3,5, 3,6 // Additional yellow coordinates
        });
        colorAssignments.put(colorMap.get("black"), new int[]{0,1, 0,2, 0,7, 3,4, 3,7});
        colorAssignments.put(colorMap.get("red"), new int[]{4,3});
        colorAssignments.put(colorMap.get("brightYellow"), new int[]{4,4, 4,5, 4,6, 3,3, 2,3, 2,4, 2,5, 2,6, 5,3});

        // Assign colors to the piecesArray based on the coordinates
        for (Color color : colorAssignments.keySet()) {
            int[] coords = colorAssignments.get(color);
            for (int i = 0; i < coords.length; i += 2) {
                int row = coords[i];
                int col = coords[i+1];
                piecesArray[row][col] = color;
            }
        }

        // Fill the rest with green
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (piecesArray[row][col] == null) {
                    piecesArray[row][col] = colorMap.get("green");
                }
            }
        }
    }

    private void populateBoard() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                squares[row][col].setBackground(piecesArray[row][col]); // Assign the color
            }
        }

        revalidate();
        repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameBoard board = new GameBoard();
            board.setVisible(true);
        });
    }
}