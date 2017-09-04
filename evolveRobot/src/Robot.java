import java.util.ArrayList;

/**
 * Created by zhouhezhen on 2017/8/27.
 */
public class Robot {

    private enum Direction {
        EAST,
        NORTH,
        WEST,
        SOUTH
    };

    private int xPosition;
    private int yPosition;
    private Direction heading;
    int maxMoves;
    int moves;
    // 传感器的值
    private int sensorVal;
    private final int sensorActions[];
    // 迷宫
    private Maze maze;
    private ArrayList<int[]> route;

    public Robot(int[] sensorActions, Maze maze, int maxMoves) {
        this.sensorActions = this.calcSensorActions(sensorActions);
        this.maze = maze;
        int startPos[] = this.maze.getStartPosition();
        this.xPosition = startPos[0];
        this.yPosition = startPos[1];
        this.sensorVal = -1;
        this.heading = Direction.EAST;
        this.maxMoves = maxMoves;
        this.moves = 0;
        this.route = new ArrayList<>();
        this.route.add(startPos);
    }

    /**
     * 基于传感器输入运行机器人的动作
     */
    public void run(){
        while(true){
            this.moves++;
            if(this.getNextAction() == 0){
                return;
            }
            //如果我们达到目标就休息
            if(maze.getPositionValue(this.xPosition,yPosition) == 4){
                return;
            }
            //如果我们达到了最大数量的移动
            if(this.moves > this.maxMoves){
                return;
            }
            this.makeNextAction();
        }
    }

    private int[] calcSensorActions(int[] sensorActionsStr){
        int numActions = (int) sensorActionsStr.length / 2;
        int sensorActions[] = new int[numActions];

        // Loop through actions
        for (int sensorValue = 0; sensorValue < numActions; sensorValue++){
            // Get sensor action
            int sensorAction = 0;
            if (sensorActionsStr[sensorValue*2] == 1){
                sensorAction += 2;
            }
            if (sensorActionsStr[(sensorValue*2)+1] == 1){
                sensorAction += 1;
            }

            // Add to sensor-action map
            sensorActions[sensorValue] = sensorAction;
        }

        return sensorActions;
    }

    /**
     * 运行下一个动作
     */
    public void makeNextAction(){
        // 如果向前移动
        if(this.getNextAction() == 1){
            int currentX = xPosition;
            int currentY = yPosition;

            // 按当前方向移动
            if(Direction.NORTH == this.heading){
                this.yPosition += -1;
                if(this.yPosition < 0){
                    this.yPosition = 0;
                }
            }
            else if(Direction.EAST == this.heading){
                this.xPosition += 1;
                if(this.xPosition > this.maze.getMaxX()){
                    this.xPosition = maze.getMaxX();
                }
            }else if(Direction.SOUTH == this.heading){
                this.yPosition += 1;
                if(this.yPosition > maze.getMaxY()){
                    yPosition = maze.getMaxY();
                }
            }else if (Direction.WEST == this.heading){
                this.xPosition += -1;
                if(this.xPosition < 0){
                    this.xPosition = 0;
                }
            }

            //不能移动
            if (this.maze.isWall(this.xPosition, this.yPosition) == true) {
                this.xPosition = currentX;
                this.yPosition = currentY;
            }else{
                if(currentX != this.xPosition || currentY != this.yPosition) {
                    this.route.add(this.getPosition());
                }
            }
        }
        //顺时针方向移动
        else if(this.getNextAction() == 2) {
            if (Direction.NORTH == this.heading) {
                this.heading = Direction.EAST;
            }
            else if (Direction.EAST == this.heading) {
                this.heading = Direction.SOUTH;
            }
            else if (Direction.SOUTH == this.heading) {
                this.heading = Direction.WEST;
            }
            else if (Direction.WEST == this.heading) {
                this.heading = Direction.NORTH;
            }
        }
        // 逆时针方向移动
        else if(this.getNextAction() == 3) {
            if (Direction.NORTH == this.heading) {
                this.heading = Direction.WEST;
            }
            else if (Direction.EAST == this.heading) {
                this.heading = Direction.NORTH;
            }
            else if (Direction.SOUTH == this.heading) {
                this.heading = Direction.EAST;
            }
            else if (Direction.WEST == this.heading) {
                this.heading = Direction.SOUTH;
            }
        }

        // 重置传感器值
        this.sensorVal = -1;

    }

    /**
     * 根据传感器映射获得下一个动作
     */
    public int getNextAction(){
        return this.sensorActions[this.getSensorValue()];
    }

    public int getSensorValue(){
        if(this.sensorVal > -1){
            return this.sensorVal;
        }

        boolean frontSensor, frontLeftSensor, frontRightSensor, leftSensor, rightSensor, backSensor;
        frontSensor = frontLeftSensor = frontRightSensor = leftSensor = rightSensor = backSensor = false;

        if(this.getHeading() == Direction.NORTH){
            //北
            //前端传感器
            frontSensor = this.maze.isWall(this.xPosition,this.yPosition-1);
            frontLeftSensor = this.maze.isWall(this.xPosition-1,this.yPosition-1);
            frontRightSensor= this.maze.isWall(this.xPosition+1,yPosition-1);
            leftSensor = this.maze.isWall(this.xPosition -1,yPosition);
            rightSensor = this.maze.isWall(this.xPosition + 1,yPosition);
            backSensor = this.maze.isWall(this.xPosition,yPosition+1);
        }else if (this.getHeading() == Direction.EAST) {
            frontSensor = this.maze.isWall(this.xPosition+1, this.yPosition);
            frontLeftSensor = this.maze.isWall(this.xPosition+1, this.yPosition-1);
            frontRightSensor = this.maze.isWall(this.xPosition+1, this.yPosition+1);
            leftSensor = this.maze.isWall(this.xPosition, this.yPosition-1);
            rightSensor = this.maze.isWall(this.xPosition, this.yPosition+1);
            backSensor = this.maze.isWall(this.xPosition-1, this.yPosition);
        }
        else if (this.getHeading() == Direction.SOUTH) {
            frontSensor = this.maze.isWall(this.xPosition, this.yPosition+1);
            frontLeftSensor = this.maze.isWall(this.xPosition+1, this.yPosition+1);
            frontRightSensor = this.maze.isWall(this.xPosition-1, this.yPosition+1);
            leftSensor = this.maze.isWall(this.xPosition+1, this.yPosition);
            rightSensor = this.maze.isWall(this.xPosition-1, this.yPosition);
            backSensor = this.maze.isWall(this.xPosition, this.yPosition-1);
        }
        else {
            frontSensor = this.maze.isWall(this.xPosition-1, this.yPosition);
            frontLeftSensor = this.maze.isWall(this.xPosition-1, this.yPosition+1);
            frontRightSensor = this.maze.isWall(this.xPosition-1, this.yPosition-1);
            leftSensor = this.maze.isWall(this.xPosition, this.yPosition+1);
            rightSensor = this.maze.isWall(this.xPosition, this.yPosition-1);
            backSensor = this.maze.isWall(this.xPosition+1, this.yPosition);
        }
        // Calculate sensor value
        int sensorVal = 0;

        if (frontSensor == true) {
            sensorVal += 1;
        }
        if (frontLeftSensor == true) {
            sensorVal += 2;
        }
        if (frontRightSensor == true) {
            sensorVal += 4;
        }
        if (leftSensor == true) {
            sensorVal += 8;
        }
        if (rightSensor == true) {
            sensorVal += 16;
        }
        if (backSensor == true) {
            sensorVal += 32;
        }

        this.sensorVal = sensorVal;

        return sensorVal;

    }


    /**
     * 获取机器人的位置
     * @return
     */
    public int[] getPosition(){
        return new int[]{
                this.xPosition,this.yPosition
        };
    }

    /**
     * 获取机器人的航向
     * @return
     */
    private Direction getHeading(){
        return this.heading;
    }

    /**
     * 返回机器人绕迷宫的完整路线
     * @return
     */
    public ArrayList<int[]> getRoute(){
        return this.route;
    }

    public String printRoute(){
        String route = "";
        for(Object routeStep : this.route){
            int step[] = (int[]) routeStep;
            route += "{" + step[0] + "," + step[1] + "}";
        }
        return route;
    }
}
