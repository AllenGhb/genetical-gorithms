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

    public GeneticAlgorithm(int populationSize, double mutationRate, double crossoverRate, int elitismCount, int tournamentSize) {
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
     * 检查是否符合终止条件
     *
     * 我们不知道机器人控制器问题的完美解决方案是什么样子的。
     * 所以我们只能给遗传约束算法是世代数的上界.
     */
    public boolean isTerminationConditionMet(int generationsCount,int maxGenerations){
        return (generationsCount > maxGenerations);
    }

    /**
     * 计算个体适应度
     * @param individual
     * @param cities
     * @return
     */
    public double calcFitness(Individual individual,City cities[]){
        Route route = new Route(individual,cities);
        double fitness = 1 / route.getDistance();

        individual.setFitness(fitness);

        return fitness;
    }

    /**
     * 整体评估
     */
    public void evalPopulation(Population population,City cites[]){
        double populationFitness = 0;

        for(Individual individual : population.getIndividuals()){
            populationFitness += calcFitness(individual,cites);
        }

        double avgFitness = populationFitness / population.size();
        population.setPopulationFitness(avgFitness);
    }

    /**
     * 使用锦标赛选择方法选择父代
     */
    public Individual selectParent(Population population){
        //设置锦标赛规模
        Population tournament = new Population(tournamentSize);

        population.shuffle();
        for(int i = 0 ;i<this.tournamentSize; i++){
            Individual tournamentIndividual = population.getIndividual(i);
            tournament.setIndividual(i,tournamentIndividual);
        }

        //返回最佳值
        return tournament.getFittest(0);

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


    /**
     * 基因重组 ，单点交叉
     */
    public Population crossoverPopulation(Population population){
        //创建新的种群
        Population newPopulation = new Population(population.size());

        for(int populationIndex = 0; populationIndex < population.size();populationIndex++){
            Individual parent1 = population.getFittest(populationIndex);

            if(this.crossoverRate > Math.random() && populationIndex >= this.elitismCount){
                // 种群后代
                Individual offspring = new Individual(parent1.getChromosomeLength());

                // 选择第二个父代
                Individual parent2 = this.selectParent(population);

                //获取随机交叉点
                int swapPoint = (int)(Math.random() * (parent1.getChromosomeLength()+1));

                //遍历基因
                for(int geneIndex = 0;geneIndex < parent1.getChromosomeLength();geneIndex++){
                    //如果基因位置小于交叉点，使用父代1的基因，否则，选择父代2的
                    if(geneIndex < swapPoint){
                        offspring.setGene(geneIndex,parent1.getGene(geneIndex));
                    }else{
                        offspring.setGene(geneIndex,parent2.getGene(geneIndex));
                    }
                }
                //添加后代到新的种群中
                newPopulation.setIndividual(populationIndex,offspring);
            }else{
                newPopulation.setIndividual(populationIndex,parent1);
            }
        }

        return newPopulation;
    }



}


