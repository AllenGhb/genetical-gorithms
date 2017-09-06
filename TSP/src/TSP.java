/**
 * Created by zhouhezhen on 2017/9/6.
 */
public class TSP {

    //最大迭代次数
    public static int maxGenerations = 10000;

    public static void main(String[] args) {

        //创建城市
        int numCities = 100;
        City cities[] = new City[numCities];

        for(int cityIndex = 0 ; cityIndex < numCities ; cityIndex++){

            int xPos = (int)(100*Math.random());
            int yPos = (int)(100*Math.random());

            cities[cityIndex] = new City(xPos,yPos);
        }

        // 创建遗传算法对象
        GeneticAlgorithm ga = new GeneticAlgorithm(100,0.001,0.9,2,5);
        // 实例化种群和个体类
        Population population = ga.initPopulation(cities.length);

    }


}
