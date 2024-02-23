// Connor Leyden and Andrew Liu
// due 02212024 TUE
// Purpose: Project 2B class to execute program

import java.util.*;

public class Tester_Liu {

    static char[][] grid;
    final int numTrials = 1_000;

    static final int xExit = 4;

    static final int numRows = 5;
    static final int numCols = 6;
    static final int yExit = 6;

    static int xPosition;

    static int yPosition;


    public static void initializeGrid() {
        grid = new char[2 * numRows-1][2 * numCols-1];
        for (int i = 1; i < grid.length; i+=2) {
            for (int j = 1; j < grid[0].length; j+=2) {
                grid[i][j] = '☐';
            }
        }
        for (int i = 1; i < grid.length; i+=2) {
            for (int j = 0; j < grid[0].length; j+=2) {
                grid[i][j] = '|';
            }
        }
        for (int i = 0; i < grid.length; i+=2) {
            for (int j = 1; j < grid[0].length; j+=2) {
                grid[i][j] = '—';
            }
        }
        for (int i = 0; i < grid.length; i+=2) {
            for (int j = 0; j < grid[0].length; j+=2) {
                grid[i][j] = '*';
            }
        }
    }

    public static void printGrid() {
        for (int i = 0; i < grid.length; i++) {
            if (i % 2 == 0) {
                System.out.print(" ");
            }
            for (int j = 0; j < grid[0].length; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void fillObstacles(int numObstacles) {
        for (int i = 0; i < numObstacles; i++) {
            boolean foundEmptySpace = false;
            int x = 0;
            do{
                int randomRow = (int) (Math.random() * grid.length);
                int randomCol = (int) (Math.random() * grid[0].length);
                System.out.println(randomRow + " " + randomCol);
                if (grid[randomRow][randomCol] == '|' || grid[randomRow][randomCol] == '—') {
                    grid[randomRow][randomCol] = 'X';
                    foundEmptySpace = true;
                }
                System.out.println(foundEmptySpace);
                x++;
            }
            while (!foundEmptySpace);
            printGrid();
        }
    }

    public static int[] pickNextDirection(int currentRow, int currentCol, int targetRow, int targetCol) {
        ArrayList<String> possibleMovesList = new ArrayList<>();
        possibleMovesList.add("Up");
        possibleMovesList.add("Down");
        possibleMovesList.add("Right");
        possibleMovesList.add("Left");
//        Checking Boundaries and Obstacles
        if(currentRow == 0  || grid[currentRow-1][currentCol] == 'X') {
            possibleMovesList.remove("Up");
            System.out.println("Removed up");
        } else if (currentRow == grid.length-1 || grid[currentRow+1][currentCol] == 'X') {
            possibleMovesList.remove("Down");
            System.out.println("Removed Down");
        }

        if(currentCol == 0 || grid[currentRow][currentCol-1] == 'X') {
            possibleMovesList.remove("Left");
            System.out.println("Removed Left");
        } else if (currentCol == grid[0].length -1 || grid[currentRow][currentCol+1] == 'X') {
            possibleMovesList.remove("Right");
            System.out.println("Removed Right");
        }

        int minDistance = Integer.MAX_VALUE;
        ArrayList<String> shortestMoves = new ArrayList<>();

        for (String direction : possibleMovesList) {
            int distance;
            if (direction.equals("Up")) {
                distance = Math.abs(targetCol-currentCol) + Math.abs(targetRow - (currentRow-2));
            } else if(direction.equals("Down")) {
                distance = Math.abs(targetCol-currentCol) + Math.abs(targetRow - (currentRow+2));
            } else if(direction.equals("Right")) {
                distance = Math.abs(targetCol-(currentCol+2)) + Math.abs(targetRow - (currentRow));
            } else if (direction.equals("Left")) {
                distance = Math.abs(targetCol-(currentCol-2)) + Math.abs(targetRow - (currentRow));
            } else {
                throw new Error("There is a bug in the code right now");
            }

            if (distance < minDistance) {
                minDistance = distance;
                shortestMoves.clear();
                shortestMoves.add(direction);
            } else if (distance == minDistance) {
                shortestMoves.add(direction);
            }
        }

        int randomIndex = (int) (Math.random() * shortestMoves.size());
        String chosenDirection = shortestMoves.get(randomIndex);
        System.out.println("Selected: " + chosenDirection);

        if (chosenDirection.equals("Up")) {
            currentRow -= 2;
        } else if(chosenDirection.equals("Down")) {
            currentRow += 2;
        } else if(chosenDirection.equals("Right")) {
            currentCol+=2;
        } else if (chosenDirection.equals("Left")) {
            currentCol-=2;
        } else {
            throw new Error("There is a bug in the code right now");
        }

        System.out.println("Checking in Here with Current row: " + currentRow + ". Current Col: " + currentCol);
        return new int[]{currentRow, currentCol};
    }

    public static void moveLogic(char[][] grid, int moveCounter) {
        printGrid();




    }

    public static void main(String[] args) {
        initializeGrid();
        printGrid();
        fillObstacles(10);
    }




}
