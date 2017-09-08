import java.util.Arrays;

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
    public void evalPopulation(Population population,City cities[]){
        double populationFitness = 0;

        for(Individual individual : population.getIndividuals()){
            populationFitness += calcFitness(individual,cities);
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

            if(populationIndex >= elitismCount){
                for(int geneIndex = 0 ; geneIndex < individual.getChromosomeLength() ;geneIndex++){
                    if(this.mutationRate > Math.random()){
                        int newGenePos = (int) (Math.random() * individual.getChromosomeLength());

                        int gene1 = individual.getGene(newGenePos);
                        int gene2 = individual.getGene(geneIndex);

                        individual.setGene(geneIndex,gene1);
                        individual.setGene(newGenePos,gene2);
                    }
                }
            }


            newPopulation.setIndividual(populationIndex,individual);

        }

        return newPopulation;

    }


    /**
     * 基因重组,有序交叉突变
     */
    public Population crossoverPopulation(Population population){
        //创建新的种群
        Population newPopulation = new Population(population.size());

        for(int populationIndex = 0; populationIndex < population.size();populationIndex++){

            // 获取第一个父代
            Individual parent1 = population.getFittest(populationIndex);


            if(this.crossoverRate > Math.random() && populationIndex >= this.elitismCount){

                // 选择第二个父代
                Individual parent2 = this.selectParent(population);

                // 创建空白后代染色体
                int offspringChromosome[] = new int[parent1.getChromosomeLength()];
                Arrays.fill(offspringChromosome,-1);
                Individual offspring = new Individual(offspringChromosome);

                // 获取父染色体的子集
                int substrPos1 = (int) (Math.random() * parent1.getChromosomeLength());
                int substrPos2 = (int) (Math.random() * parent1.getChromosomeLength());

                final int startSubstr = Math.min(substrPos1,substrPos2);
                final int endSubstr = Math.max(substrPos1,substrPos2);

                // 将父代的某段基因遗传给下一代
                for( int i = startSubstr; i < endSubstr ; i++){
                    offspring.setGene(i,parent1.getGene(i));
                }

                for(int i=0 ; i < parent2.getChromosomeLength();i++){
                    int parent2Gene = i + endSubstr;
                    if(parent2Gene >= parent2.getChromosomeLength()){
                        parent2Gene -= parent2.getChromosomeLength();
                    }

                    // 如果后代染色体中不存在此城市则添加
                    if(offspring.containsGene(parent2.getGene(parent2Gene)) == false){
                        for(int ii = 0; ii < offspring.getChromosomeLength();ii++){
                            if(offspring.getGene(ii) == -1){
                                offspring.setGene(ii,parent2.getGene(parent2Gene));
                                break;
                            }
                        }
                    }
                }

                // 新种群中添加新生成的后代
                newPopulation.setIndividual(populationIndex,offspring);
            }else{
                newPopulation.setIndividual(populationIndex,parent1);
            }
        }

        return newPopulation;
    }



}


