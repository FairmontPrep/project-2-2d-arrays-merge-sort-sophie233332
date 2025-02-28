import java.awt.*;
import java.util.*;
import javax.swing.*;

public class GameBoard extends JFrame {
    private final int SIZE = 8;
    private final int SquareSize = 80;
    private final JPanel[][] squares = new JPanel[SIZE][SIZE]; // Board squares
    private final String[][] piecesArray = new String[SIZE * SIZE][2]; // Stores color and position

    public GameBoard() {
        setTitle("Color Board");
        setSize(SIZE * SquareSize, SIZE * SquareSize);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(SIZE, SIZE));

        // Initialize board squares
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                squares[row][col] = new JPanel();
                squares[row][col].setPreferredSize(new Dimension(SquareSize, SquareSize));
                squares[row][col].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                add(squares[row][col]);
            }
        }

        // Randomly distribute colors
        distributeColorsRandomly();

        // Print unsorted values
        printUnsortedValues();

        // Populate board with unsorted values
        populateBoard();

        // Delay 1 second, then sort and update board
        new javax.swing.Timer(1000, e -> {
            sortPiecesArray();
            System.out.println("\nSorted Pieces Array:");
            printSortedValues();
            SwingUtilities.invokeLater(this::populateBoard); // Ensure UI update
        }).start();
    }

    // Randomly distribute colors based on required counts
    private void distributeColorsRandomly() {
        Map<String, String> colorMap = new HashMap<>();
        colorMap.put("yellow", "#E3B709");
        colorMap.put("black", "#000000");
        colorMap.put("red", "#8A0E06");
        colorMap.put("brightYellow", "#E8D43F");
        colorMap.put("green", "#9C96C");
        colorMap.put("brown", "#754627");

        // Define the required number of blocks per color
        Map<String, Integer> colorCounts = new HashMap<>();
        colorCounts.put("yellow", 16);
        colorCounts.put("black", 5);
        colorCounts.put("red", 1);
        colorCounts.put("brightYellow", 18);
        colorCounts.put("brown", 5);
        colorCounts.put("green", 19); // Remaining will be filled with green

        // Create a list of available positions
        List<Integer> positions = new ArrayList<>();
        for (int i = 0; i < SIZE * SIZE; i++) {
            positions.add(i);
        }
        Collections.shuffle(positions); // Randomize positions

        int index = 0;
        for (String color : colorCounts.keySet()) {
            int count = colorCounts.get(color);
            for (int i = 0; i < count; i++) {
                int pos = positions.get(index++);
                piecesArray[pos][0] = colorMap.get(color);
                piecesArray[pos][1] = String.valueOf(pos);
            }
        }
    }

    // Print the unsorted values
    private void printUnsortedValues() {
        System.out.println("Unsorted Pieces Array:");
        for (String[] piece : piecesArray) {
            if (piece[0] != null) {
                System.out.println("Color: " + piece[0] + ", Position: " + piece[1]);
            }
        }
    }

    // Print the sorted values
    private void printSortedValues() {
        for (String[] piece : piecesArray) {
            if (piece[0] != null) {
                System.out.println("Color: " + piece[0] + ", Position: " + piece[1]);
            }
        }
    }

    // Sort the piecesArray by position (numerically)
    private void sortPiecesArray() {
        Arrays.sort(piecesArray, (a, b) -> {
            if (a[1] == null) return 1; // Move nulls to the end
            if (b[1] == null) return -1;
            return Integer.compare(Integer.parseInt(a[1]), Integer.parseInt(b[1]));
        });
    }

    private void populateBoard() {
        int pieceRow = 0;
        int squareName = 0;

        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (piecesArray[pieceRow][0] != null && Integer.parseInt(piecesArray[pieceRow][1]) == squareName) {
                    Color color = Color.decode(piecesArray[pieceRow][0]);
                    squares[row][col].setBackground(color);
                    pieceRow++;
                }
                squareName++;
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
