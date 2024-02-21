// Connor Leyden and Andrew Liu
// due 02212024 TUE
// Purpose: Project 2B class to execute program

import java.util.ArrayList;
import java.util.Arrays;

public class Tester {
    static boolean[][] grid;

    public static void createNewGrid(int numRowIntersections, int numColIntersections) {
        grid = new boolean[2 * numRowIntersections-1][2 * numColIntersections-1];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                grid[i][j] = false;
            }
        }
    }
    public static void fillObstacles(int numObstacles) {
        for (int i = 0; i < numObstacles; i++) {
            boolean foundEmptySpace = false;
            while (!foundEmptySpace) {
                int randomRow = (int) (Math.random() * grid.length);
                int randomCol = (int) (Math.random() * grid[0].length);
                int tempRowColSum = randomRow+randomCol;
                if (tempRowColSum % 2 == 1 && !grid[randomRow][randomCol]) {
                    grid[randomRow][randomCol] = true;
                    foundEmptySpace = true;
                }
            }
        }
    }

    public static int[] pickNextDirection(int currentRow, int currentCol, int targetRow, int targetCol) {
        ArrayList<String> possibleMovesList = new ArrayList<>();
        possibleMovesList.add("Up");
        possibleMovesList.add("Down");
        possibleMovesList.add("Right");
        possibleMovesList.add("Left");
//        Checking Boundaries and Obstacles
        if(currentRow == 0  || grid[currentRow-1][currentCol]) {
            possibleMovesList.remove("Up");
            System.out.println("Removed up");
        } else if (currentRow == grid.length-1 || grid[currentRow+1][currentCol]) {
            possibleMovesList.remove("Down");
            System.out.println("Removed Down");
        }

        if(currentCol == 0 || grid[currentRow][currentCol-1]) {
            possibleMovesList.remove("Left");
            System.out.println("Removed Left");
        } else if (currentCol == grid[0].length -1 || grid[currentRow][currentCol+1]) {
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

    public static int runOneSimulation(int targetRow, int targetCol, int numObstacles, int numRowIntersections, int numColIntersections)  {
        createNewGrid(numRowIntersections, numColIntersections);
        fillObstacles(numObstacles);
        targetRow = targetRow * 2 - 2;
        targetCol = targetCol * 2 - 2;
        int currentRow = 0;
        int currentCol = 0;
        int stepsTaken = 0;
        while (currentRow != targetRow && currentCol != targetCol) {
            int[] tempArr = pickNextDirection(currentRow, currentCol, targetRow, targetCol);
            currentRow = tempArr[0];
            currentCol = tempArr[1];
            stepsTaken++;
            if(stepsTaken >= 100) {
                return 100;
            }
        }
        return stepsTaken;
    }
    public static ArrayList<Integer> runSimulation(int numSimulations, int targetRow, int targetCol, int numObstacles, int numRowIntersections, int numColIntersections) {
        ArrayList<Integer> stepsArray = new ArrayList<>();
        for (int i = 0; i < numSimulations; i++) {
            stepsArray.add(runOneSimulation(targetRow, targetCol, numObstacles, numRowIntersections, numColIntersections));
        }
        return stepsArray;
    }

    public static void printGrid() {
        System.out.println();
        for (boolean[] i : grid) {
            for (boolean j : i) {
                System.out.print(j + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
    public static void runTestVisualization(int targetRow, int targetCol, int numObstacles, int numRowIntersections, int numColIntersections) {
        System.out.println("This is the beginning of a new trial!");
        createNewGrid(numRowIntersections, numColIntersections);
        fillObstacles(numObstacles);
        targetRow = targetRow * 2 - 2;
        targetCol = targetCol * 2 - 2;
//        grid[targetRow][targetCol] = true;
        printGrid();
        int currentRow = 0;
        int currentCol = 0;
        int stepsTaken = 0;
        while (!(currentRow == targetRow) || !(currentCol == targetCol)) {
            System.out.println("Next Iteration");
            System.out.println("Current row: " + currentRow + ". Current Col: " + currentCol);
            grid[currentRow][currentCol] = true;
            printGrid();
            grid[currentRow][currentCol] = false;
            int[] tempArr = pickNextDirection(currentRow, currentCol, targetRow, targetCol);
            currentRow = tempArr[0];
            currentCol = tempArr[1];
            System.out.println("New Current row: " + currentRow + ". New Current Col: " + currentCol);
            System.out.println();
            stepsTaken++;
            if(stepsTaken >= 100) {
                break;
            }
        }
        System.out.println("We made it with " + stepsTaken + "!");
    }
    public static void main(String[] args) {
        int desiredObstacleRange = 15;
        ArrayList<Integer>[] arrayOfArrayLists = new ArrayList[desiredObstacleRange];
        int numSimulations = 10_000;
        int targetRow = 6;
        int targetCol = 5;
        int numRowIntersections = 6;
        int numColIntersections = 5;

        runTestVisualization(targetRow, targetCol, 15, numRowIntersections, numColIntersections);
//        for (int numObstacles = 1; numObstacles <= desiredObstacleRange; numObstacles++) {
//            arrayOfArrayLists[numObstacles] = runSimulation(numSimulations, targetRow, targetCol, numObstacles, numRowIntersections, numColIntersections);
//        }
    }
}