# Observer Pattern for MazeRunner

## Proposed Implementation (Not Actually Implemented)

The Observer Pattern would be an excellent addition to the MazeRunner application to provide real-time updates on the explorer's progress through the maze. This pattern would allow various components to be notified when the explorer moves, changes direction, or reaches important milestones.

## Benefits for MazeRunner

1. **Separation of Concerns**: The Explorer could focus solely on maze traversal while observers handle visualization, logging, or other reactions to movement.
2. **Real-time Feedback**: Users could get immediate notifications about the explorer's progress.
3. **Extensibility**: New observers could be added without modifying the Explorer class.
4. **Testing**: Observers could be used to verify the Explorer's movements in tests.

## How It Would Be Implemented

### 1. Observer Interface

```java
public interface ExplorerObserver {
    void onMove(int[] newPosition, String direction);
    void onTurn(String newDirection);
    void onMazeCompleted(List<String> path);
    void onMazeDeadEnd();
}
```

### 2. Making Explorer the Subject

```java
public class Explorer {
    // Existing code...

    private List<ExplorerObserver> observers = new ArrayList<>();

    public void addObserver(ExplorerObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(ExplorerObserver observer) {
        observers.remove(observer);
    }

    private void notifyObserversOnMove() {
        for(ExplorerObserver observer : observers) {
            observer.onMove(currentPos, getDirectionAsString());
        }
    }

    private void notifyObserversOnTurn() {
        for(ExplorerObserver observer : observers) {
            observer.onTurn(getDirectionAsString());
        }
    }

    private void notifyObserversOnMazeCompleted() {
        for(ExplorerObserver observer : observers) {
            observer.onMazeCompleted(moves);
        }
    }

    private void notifyObserversOnDeadEnd() {
        for(ExplorerObserver observer : observers) {
            observer.onMazeDeadEnd();
        }
    }

    private String getDirectionAsString() {
        switch(direction) {
            case 0: return "Right";
            case 1: return "Down";
            case 2: return "Left";
            case 3: return "Up";
            default: return "Unknown";
        }
    }

    // Update existing methods to notify observers at appropriate times
}
```

### 3. Concrete Observers

#### Console Output Observer

```java
public class ConsoleOutputObserver implements ExplorerObserver {
    @Override
    public void onMove(int[] newPosition, String direction) {
        System.out.println("Explorer moved to: [" + newPosition[0] + "," + newPosition[1] + "]");
    }

    @Override
    public void onTurn(String newDirection) {
        System.out.println("Explorer turned to face: " + newDirection);
    }

    @Override
    public void onMazeCompleted(List<String> path) {
        System.out.println("Maze completed with path: " + String.join("", path));
    }

    @Override
    public void onMazeDeadEnd() {
        System.out.println("Hit a dead end, backtracking...");
    }
}
```

#### Visualization Observer

```java
public class MazeVisualizationObserver implements ExplorerObserver {
    private Maze maze;
    private char[][] visualMaze;

    public MazeVisualizationObserver(Maze maze) {
        this.maze = maze;
        this.visualMaze = new char[maze.getRows()][maze.getCols()];

        // Initialize with the maze structure
        for(int y = 0; y < maze.getRows(); y++) {
            for(int x = 0; x < maze.getCols(); x++) {
                visualMaze[y][x] = maze.getGridAt(x, y);
            }
        }
    }

    @Override
    public void onMove(int[] newPosition, String direction) {
        // Mark the position in our visual representation
        visualMaze[newPosition[1]][newPosition[0]] = 'X';
        printMaze();
    }

    // Other methods...

    private void printMaze() {
        for(int y = 0; y < maze.getRows(); y++) {
            for(int x = 0; x < maze.getCols(); x++) {
                System.out.print(visualMaze[y][x]);
            }
            System.out.println();
        }
        System.out.println();
    }
}
```

### 4. Usage in Main

```java
public class Main {
    public static void main(String[] args) {
        // ... existing code

        Explorer explorer = new Explorer(maze, method);

        // Add observers
        explorer.addObserver(new ConsoleOutputObserver());
        explorer.addObserver(new MazeVisualizationObserver(maze));

        explorer.exploreMaze();

        // ... rest of the code
    }
}
```

## Integration with Existing Patterns

The Observer Pattern would integrate well with our existing Strategy and Singleton patterns:

1. **Strategy Pattern**: Each strategy could notify observers through the Explorer class, maintaining a clean separation of concerns.

2. **Singleton Pattern**: The InputHandler could potentially be an observer of the Explorer to track and validate paths.

## Conclusion

The Observer Pattern would enhance the MazeRunner application by providing real-time feedback on the explorer's progress, making the application more interactive and easier to debug. It would also increase extensibility by allowing new features to be added as observers without modifying the core exploration logic.
