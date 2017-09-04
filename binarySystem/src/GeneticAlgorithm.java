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

    public GeneticAlgorithm(int populationSize, double mutationRate, double crossoverRate, int elitismCount) {
        this.populationSize = populationSize;
        this.mutationRate = mutationRate;
        this.crossoverRate = crossoverRate;
        this.elitismCount = elitismCount;
    }

    /**
     * 实例化种群和个体类
     */
    public Population initPopulation(int chromosomeLength){
        Population population = new Population(this.populationSize,chromosomeLength);
        return population;
    }

    /**
     * 适应度函数
     */
    public double calcFitness(Individual individual){
        //基因为1的数目
        int correctGenes = 0;
        for (int geneIndex = 0; geneIndex < individual.getChromosomeLength();geneIndex++){
            if(individual.getGene(geneIndex) == 1){
                correctGenes+=1;
            }
        }
        //个体适应度 = 基因为1的数目 / 染色体长度
        double fitness = (double) correctGenes / individual.getChromosomeLength();
        // 插入个体适应度
        individual.setFitness(fitness);
        return fitness;
    }

    /**
     * 遍历每个个体，并评估它们
     */
    public void evalPopulation(Population population){
        double populationFitness = 0;
        for (Individual individual : population.getIndividuals()){
            populationFitness += calcFitness(individual);
        }

        population.setPopulationFitness(populationFitness);
    }

    /**
     * 遗传算法终止检查
     */
    public boolean isTerminationConditionMet(Population population){
        for(Individual individual : population.getIndividuals()){
            if(individual.getFitness() == 1){
                return true;
            }
        }
        return false;
    }

    /**
     * 轮盘赌局选择交叉伙伴
     */
    public Individual selectParent(Population population){
        Individual[] individuals = population.getIndividuals();

        double populationFitness = population.getPopulationFitness();
        double rouletteWheelPosition = Math.random()* populationFitness;

        double spinWheel = 0;
        for(Individual individual : individuals){
            spinWheel += individual.getFitness();
            if(spinWheel >= rouletteWheelPosition){
                return  individual;
            }
        }
        return individuals[population.size() -1 ];
    }

    /**
     * 基因重组(交叉)
     */
    public Population crossoverPopulation(Population population){
        //初始化下一代新种群
        Population newPopulation = new Population(population.size());

        for(int populationIndex = 0; populationIndex < population.size();populationIndex++){
            Individual parent1 = population.getFittest(populationIndex);

            if(this.crossoverRate > Math.random() && populationIndex >= this.elitismCount){
                Individual offspring = new Individual(parent1.getChromosomeLength());

                Individual parent2 = selectParent(population);

                for (int geneIndex = 0; geneIndex < parent1.getChromosomeLength();geneIndex++){
                    if(0.5 > Math.random()){
                        offspring.setGene(geneIndex,parent1.getGene(geneIndex));
                    }else{
                        offspring.setGene(geneIndex,parent2.getGene(geneIndex));
                    }
                }
                newPopulation.setIndividual(populationIndex,offspring);
            }else {
                newPopulation.setIndividual(populationIndex,parent1);
            }
        }
        return newPopulation;
    }

    /**
     * 基因变异
     */
    public Population mutatePopulation(Population population){
        Population newPopulation = new Population(this.populationSize);
        for(int populationIndex = 0 ; populationIndex < population.size();populationIndex++){
            Individual individual = population.getFittest(populationIndex);

            for(int geneIndex = 0;geneIndex < individual.getChromosomeLength();geneIndex++){
                if(populationIndex >this.elitismCount){
                    if(this.mutationRate > Math.random()){
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


