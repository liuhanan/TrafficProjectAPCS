// Connor Leyden and Andrew Liu
// due 02212024 TUE
// Purpose: Project 2B class to execute program

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tester {
    static boolean[][] grid;

    public static void createNewGrid(int numRowIntersections, int numColIntersections) {
        grid = new boolean[2 * numRowIntersections-1][2 * numColIntersections-1];
    }
    public static void fillObstacles(int numObstacles) {
        int numObstacleRows = (grid.length - 1) / 2;
        int numObstacleCols = (grid[0].length - 1) / 2;

        for (int i = 0; i < numObstacles; i++) {

            boolean foundEmptySpace = false;
            while (!foundEmptySpace) {
                int randomRow = (int) (Math.random() * numObstacleRows);
                int randomRowIndex = randomRow * 2 + 1;
                int randomCol = (int) (Math.random() * numObstacleCols);
                int randomColIndex = randomCol * 2 + 1;
                if (!grid[randomRowIndex][randomColIndex]) {
                    grid[randomRowIndex][randomColIndex] = true;
                    foundEmptySpace = true;
                } else {
                    continue;
                }
            }

        }
//       Get the number of obstacle rows and obstacle columns
//       Pick a random row and a random column
//       Check if the row and column one has been filled, if not, then fill it, otherwise, find a new one
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
        } else if (currentRow == grid.length-1 || grid[currentRow+1][currentCol]) {
            possibleMovesList.remove("Down");
        }

        if(currentCol == 0 || grid[currentRow][currentCol-1]) {
            possibleMovesList.remove("Left");
        } else if (currentCol == grid[0].length -1 || grid[currentRow][currentCol+1]) {
            possibleMovesList.remove("Right");
        }

        int minDistance = Integer.MAX_VALUE;
        ArrayList<String> shortestMoves = new ArrayList<>();


        for (String direction : possibleMovesList) {
            int distance;
            if (direction.equals("Up")) {
                distance = Math.abs(targetCol-currentCol) + Math.abs(targetRow - (currentRow-1));
            } else if(direction.equals("Down")) {
                distance = Math.abs(targetCol-currentCol) + Math.abs(targetRow - (currentRow+1));
            } else if(direction.equals("Right")) {
                distance = Math.abs(targetCol-(currentCol+1)) + Math.abs(targetRow - (currentRow));
            } else if (direction.equals("Left")) {
                distance = Math.abs(targetCol-(currentCol-1)) + Math.abs(targetRow - (currentRow));
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

        return new int[]{currentRow, currentCol};
    }

    public static int runOneSimulation(int targetRow, int targetCol, int numObstacles, int numRowIntersections, int numColIntersections)  {
        createNewGrid(numRowIntersections, numColIntersections);
        fillObstacles(numObstacles);
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

    public static void main(String[] args) {
//        Array of Arraylists, if possible

        int desiredObstacleRange = 15;
        ArrayList<Integer>[] arrayOfArrayLists = new ArrayList[desiredObstacleRange];

        int numSimulations = 10_000;
        int targetRow = 6;
        int targetCol = 5;
        int numRowIntersections = 6;
        int numColIntersections = 5;
        // Initialize each element in the array
        for (int numObstacles = 1; numObstacles <= desiredObstacleRange; numObstacles++) {
            arrayOfArrayLists[numObstacles] = runSimulation(numSimulations, targetRow, targetCol, numObstacles, numRowIntersections, numColIntersections);
        }
    }
}