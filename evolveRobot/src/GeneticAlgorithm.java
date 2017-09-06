/**
 * 二进制遗传算法
 * 抽象了遗传算法本身，为接口方法提供了针对问题的实现，如交叉、变异、适应度评估和终止条件检查
 * Created by zhouhezhen on 2017/6/22.
 */
public class GeneticAlgorithm {

    // 种群规模
    private int populationSize;

    // 变异率
    private double mutationRate;

    // 交叉率
    private double crossoverRate;

    // 精英成员数
    private int elitismCount;

    protected   int tournamentSize;

    public GeneticAlgorithm(int populationSize, double mutationRate, double crossoverRate, int elitismCount,int tournamentSize) {
        this.populationSize = populationSize;
        this.mutationRate = mutationRate;
        this.crossoverRate = crossoverRate;
        this.elitismCount = elitismCount;
        this.tournamentSize = tournamentSize;
    }

    /**
     * 实例化种群和个体类
     */
    public Population initPopulation(int chromosomeLength){
        Population population = new Population(this.populationSize,chromosomeLength);
        return population;
    }

    /**
     * 计算个体适应度
     *
     * 创建新的机器人，在给定的迷宫中评估其性能
     */
    public double calcFitness(Individual individual,Maze maze){
        //获取个体的染色体
        int[] chromosome = individual.getChromosome();
        //初始化新机器人
        Robot robot = new Robot(chromosome,maze,100);
        robot.run();
        int fitness = maze.scoreRoute(robot.getRoute());
        individual.setFitness(fitness);
        return fitness;
    }

    /**
     * 整体评估
     */
    public void evalPopulation(Population population,Maze maze){

        //整体适应度
        double populationFitness = 0;

        for(Individual individual : population.getIndividuals()){
            populationFitness += this.calcFitness(individual,maze);
        }

        population.setPopulationFitness(populationFitness);
    }

    /**
     * 检查是否符合终止条件
     *
     * 我们不知道机器人控制器问题的完美解决方案是什么样子的。
     * 所以我们只能给遗传约束算法是世代数的上界.
     */
    public boolean isTerminationConditionMet(int generationsCount,int maxGenerations){
        return (generationsCount > maxGenerations);
    }

    /**
     * 基因变异
     */
    public Population mutatePopulation(Population population){
        // 初始化新种群
        Population newPopulation = new Population(populationSize);

        for(int populationIndex = 0; populationIndex < population.size();populationIndex++){
            // 根据适应度排序,选择最优值
            Individual individual = population.getFittest(populationIndex);

            //遍历个体基因
            for(int geneIndex = 0; geneIndex < individual.getChromosomeLength(); geneIndex++){
                // 跳过突变，如果这是一个精英个体
                if(populationIndex >= this.elitismCount){
                    if(mutationRate > Math.random()){
                        // 获取新基因
                        int newGene = 1;
                        if(individual.getGene(geneIndex) == 1){
                            newGene = 0;
                        }
                        individual.setGene(geneIndex,newGene);
                    }
                }
            }

            newPopulation.setIndividual(populationIndex,individual);

        }

        return newPopulation;

    }


}


