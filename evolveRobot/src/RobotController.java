/**
 * Created by zhouhezhen on 2017/8/27.
 */
public class RobotController {

    public static int maxGenerations = 1000;

    public static void main(String[] args){

        /**
        * As a reminder:
        * 0 = Empty 空位置
        * 1 = Wall 墙
        * 2 = Starting position 起始位置
        * 3 = Route 记录了穿过迷宫的最佳途径
        * 4 = Goal position 目标位置
        */
        Maze maze = new Maze(new int[][] {
                { 0, 0, 0, 0, 1, 0, 1, 3, 2 },
                { 1, 0, 1, 1, 1, 0, 1, 3, 1 },
                { 1, 0, 0, 1, 3, 3, 3, 3, 1 },
                { 3, 3, 3, 1, 3, 1, 1, 0, 1 },
                { 3, 1, 3, 3, 3, 1, 1, 0, 0 },
                { 3, 3, 1, 1, 1, 1, 0, 1, 1 },
                { 1, 3, 0, 1, 3, 3, 3, 3, 3 },
                { 0, 3, 1, 1, 3, 1, 0, 1, 3 },
                { 1, 3, 3, 3, 3, 1, 1, 1, 4 }
        });
        // 创建遗传算法对象
        GeneticAlgorithm ga = new GeneticAlgorithm(200,0.05,0.9,2,10);
        // 实例化种群和个体类
        Population population = ga.initPopulation(128);
        // 评估
        ga.evalPopulation(population,maze);

        //世代数目
        int generation = 1;
        // 遗传迭代
        while(ga.isTerminationConditionMet(generation,maxGenerations) == false){

            // 从种群中打印合适的个体适应度
            Individual fittest = population.getFittest(0);
            System.out.println("G" + generation + "Best solution (" + fittest.getFitness()
                    + "): " + fittest.toString());

            // 基因重组

            // 基因变异
            ga.mutatePopulation(population);
            // 重新评估
            ga.evalPopulation(population,maze);

            generation++;
        }

        System.out.println("Stopped after " + maxGenerations + "generations.");
        Individual fittest = population.getFittest(0);
        System.out.println("Best solution (" + fittest.getFitness()  + "):" + fittest.toString());

    }

}
