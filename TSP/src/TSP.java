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
        // 评估
        ga.evalPopulation(population,cities);

        Route startRoute = new Route(population.getFittest(0),cities);
        System.out.println("开始距离: " + startRoute.getDistance());
        // 世代数目
        int generation = 1;
        // 循环评估
        while(ga.isTerminationConditionMet(generation,maxGenerations) == false){
            // 显示种群中最优的个体
            Route route = new Route(population.getFittest(0),cities);
            System.out.println("G" + generation +" 最优距离: " + route.getDistance());

            // 基因重组
            population = ga.crossoverPopulation(population);
            // 基因变异
            population = ga.mutatePopulation(population);
            // 重新评估
            ga.evalPopulation(population,cities);

            generation++;

        }

        System.out.println("迭代之前的最大迭代次数 :" + maxGenerations + "generations.");
        Route route = new Route(population.getFittest(0), cities);
        System.out.println("最优距离 :" + route.getDistance());

    }


}
