import java.util.ArrayList;

/**
 * 迷宫
 * 0 = Empty
 * 1 = Wall
 * 2 = Starting position
 * 3 = Route
 * 4 = Goal position
 * Created by zhouhezhen on 2017/8/27.
 */
public class Maze {

    private final int maze[][];
    private int startPosition[] = {-1,-1};

    public Maze(int[][] maze) {
        this.maze = maze;
    }

    public int[]  getStartPosition(){
        if(this.startPosition[0] != -1 && this.startPosition[1] != -1){
            return this.startPosition;
        }

        int startPosition[] = {0,0};

        for(int rowIndex = 0;rowIndex < this.maze.length ; rowIndex++){
            for(int colIndex = 0; colIndex < this.maze[rowIndex].length ; colIndex++){
                if(this.maze[rowIndex][colIndex] == 2){
                    this.startPosition = new int[]{colIndex,rowIndex};
                    return new int[]{colIndex,rowIndex};
                }
            }
        }
        return startPosition;
    }

    public int getPositionValue(int x,int y){
        if(x < 0 || y < 0 || x >= this.maze.length || y >= this.maze[0].length){
            return 1;
        }
        return this.maze[y][x];
    }

    /**
     * 是否是墙
     */
    public boolean isWall(int x,int y){
        return (this.getPositionValue(x,y) == 1);
    }

    public int getMaxX(){
        return this.maze[0].length -1;
    }

    public int getMaxY(){
        return this.maze.length - 1;
    }

    /**
     * 迷宫路线
     **/
    public int scoreRoute(ArrayList<int[]> route){
        int score = 0;
        boolean visited[][] = new boolean[this.getMaxY() + 1][this.getMaxX() + 1];
        //循环路线和记分每一步
        for(Object routeStep : route){
            int step[] = (int[]) routeStep;
            if(this.maze[step[1]][step[0]] == 3 && visited[step[1]][step[0]] == false){
                //增加正确移动的分数
                score++;
                visited[step[1]][step[0]] = true;
            }
        }
        return score;
    }
}
