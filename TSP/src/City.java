/**
 * @Discription: 城市的抽象类,维护笛卡尔坐标
 * @Author: allen
 * @Date: 2017/9/6
 */
public class City {

    private int x;
    private int y;

    public City(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * 勾股定理,计算两点间的距离
     */
    public double distanceFrom(City city){
        // 计算x坐标的高度差的平方
        double deltaXSq = Math.pow((city.getX()-this.getX()),2);
        // 计算y左边的高度差的平方
        double deltaYSq = Math.pow((city.getY()-this.getY()),2);
        // 计算两点间的距离
        double distance = Math.sqrt(Math.abs(deltaXSq + deltaYSq));

        return distance;
    }


    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
