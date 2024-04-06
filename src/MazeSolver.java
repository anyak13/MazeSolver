// Anya Kothari
// 4/03/24
// This program solves a maze using both DFS and BFS, and returns the solution to the maze
/**
 * Solves the given maze using DFS or BFS
 * @author Ms. Namasivayam and Anya Kothari
 * @version 03/10/2023
 */

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class MazeSolver {
    private Maze maze;

    public MazeSolver() {
        this.maze = null;
    }

    public MazeSolver(Maze maze) {
        this.maze = maze;
    }

    public void setMaze(Maze maze) {
        this.maze = maze;
    }

    /**
     * Starting from the end cell, backtracks through
     * the parents to determine the solution
     * @return An arraylist of MazeCells to visit in order
     */
    public ArrayList<MazeCell> getSolution() {
        // TODO: Get the solution from the maze
        // Make a stack to store the solution from end to start cells
        Stack<MazeCell> reverseSolution = new Stack<MazeCell>();
        MazeCell currentCell = maze.getEndCell();
        // Loop until you get to the start of the maze
        while (currentCell != maze.getStartCell()) {
            // Add the cell to the stack
            reverseSolution.push(currentCell);
            // Move to the current cell's parent
            currentCell = currentCell.getParent();
        }
        // Add the start cell to the solution
        reverseSolution.push(currentCell);
        // Reverse the solution so the solution is start to end cells
        // Create an ArrayList
        ArrayList<MazeCell> solution = new ArrayList<MazeCell>();
        while (!reverseSolution.isEmpty()) {
            // Add the top element from the stack to the ArrayList
            solution.add(reverseSolution.pop());
        }
        return solution;
    }


    /**
     * Performs a Depth-First Search to solve the Maze
     * @return An ArrayList of MazeCells in order from the start to end cell
     */
    public ArrayList<MazeCell> solveMazeDFS() {
        // TODO: Use DFS to solve the maze
        // Create a stack to store the cells to visit
        Stack<MazeCell> cellsToVisit = new Stack<MazeCell>();
        MazeCell currentCell = maze.getStartCell();
        int row;
        int col;

        // Loop until the current cell reaches the end of the maze
        while (currentCell != maze.getEndCell()) {
            currentCell.setExplored(true);
            row = currentCell.getRow();
            col = currentCell.getCol();

            // Call exploreNeighborDFS on the North cell
            exploreNeighborDFS(cellsToVisit,row - 1, col, currentCell);
            // // Call exploreNeighborDFS on the East cell
            exploreNeighborDFS(cellsToVisit, row,col + 1, currentCell);
            // Call exploreNeighborDFS on the South cell
            exploreNeighborDFS(cellsToVisit,row + 1, col, currentCell);
            // Call exploreNeighborDFS on the West cell
            exploreNeighborDFS(cellsToVisit, row,col - 1, currentCell);

            // Go to the next cell from the stack
            currentCell = cellsToVisit.pop();
        }
        // Call getSolution to return an ArrayList of MazeCells from the start to end cell
        return getSolution();
    }

    /**
     * Helper function to support DFS - explores a neighbor cell at the given location, determines if it is valid
     * to visit, and if so, adds the neighbor cell to the stack
     */
    public void exploreNeighborDFS(Stack<MazeCell> cellsToVisit, int row, int col, MazeCell currentCell) {
        // Checks if the neighboring cell is valid
        if (maze.isValidCell(row, col)) {
            // Add the cell to the queue
            cellsToVisit.push(maze.getCell(row, col));
            // Set the cell as explored
            maze.getCell(row, col).setExplored(true);
            // Sets the parent of the neighbor cell as the current cell
            maze.getCell(row, col).setParent(currentCell);
        }
    }

    /**
     * Performs a Breadth-First Search to solve the Maze
     * @return An ArrayList of MazeCells in order from the start to end cell
     */
    public ArrayList<MazeCell> solveMazeBFS() {
        // TODO: Use BFS to solve the maze
        // Create a queue to store the cells to visit
        Queue<MazeCell> cellsToVisit = new LinkedList<MazeCell>();
        MazeCell currentCell = maze.getStartCell();
        int row;
        int col;

        // Loop until the current cell reaches the end of the maze
        while (currentCell != maze.getEndCell()) {
            currentCell.setExplored(true);
            row = currentCell.getRow();
            col = currentCell.getCol();

            // Call exploreNeighborBFS on the North cell
            exploreNeighborBFS(cellsToVisit,row - 1, col, currentCell);
            // Call exploreNeighborBFS on the East cell
            exploreNeighborBFS(cellsToVisit, row,col+ 1, currentCell);
            // Call exploreNeighborBFS on the South cell
            exploreNeighborBFS(cellsToVisit,row + 1,col, currentCell);
            // Call exploreNeighborBFS on the West cell
            exploreNeighborBFS(cellsToVisit, row,col - 1, currentCell);

            // Go to the next cell from the queue
            currentCell = cellsToVisit.remove();
        }
        // Call getSolution to return an ArrayList of MazeCells from the start to end cell
        return getSolution();
    }

    public void exploreNeighborBFS(Queue<MazeCell> cellsToVisit, int row, int col, MazeCell currentCell) {
        // Checks if the neighboring cell is valid
        if (maze.isValidCell(row,col)) {
            // Add the cell to the queue
            cellsToVisit.add(maze.getCell(row, col));
            // Set the cell as explored
            maze.getCell(row, col).setExplored(true);
            // Sets the parent of the neighbor cell as the current cell
            maze.getCell(row, col).setParent(currentCell);
        }
    }

    public static void main(String[] args) {
        // Create the Maze to be solved
        Maze maze = new Maze("Resources/maze3.txt");

        // Create the MazeSolver object and give it the maze
        MazeSolver ms = new MazeSolver();
        ms.setMaze(maze);

        // Solve the maze using DFS and print the solution
        ArrayList<MazeCell> sol = ms.solveMazeDFS();
        maze.printSolution(sol);

        // Reset the maze
        maze.reset();

        // Solve the maze using BFS and print the solution
        sol = ms.solveMazeBFS();
        maze.printSolution(sol);
    }
}
