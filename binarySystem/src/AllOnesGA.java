//import
/**
 * 引导类，初始化遗传算法
 * Created by zhouhezhen on 2017/6/22.
 */
public class AllOnesGA {

    public static void main(String[] args) {
        // 创建遗传算法对象
        GeneticAlgorithm ga = new GeneticAlgorithm(100,0.01,0.95,2);
        // 实例化种群和个体类
        Population population = ga.initPopulation(50);
        // 评估
        ga.evalPopulation(population);
        //世代数目
        int generation = 1;
        // 遗传迭代
        while(ga.isTerminationConditionMet(population) == false){
            System.out.println("Best solution : " + population.getFittest(0).toString());
            // 基因重组
            population = ga.crossoverPopulation(population);
            // 基因变异
            population = ga.mutatePopulation(population);
            // 重新评估
            ga.evalPopulation(population);
            generation++;
        }
        System.out.println("Found solution in " + generation + "generations");
        System.out.println("Best solution: " + population.getFittest(0).toString());
    }

}
