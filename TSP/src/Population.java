import java.util.Arrays;
import java.util.Comparator;

/**
 * @Discription: 一个种群或个体的一个世代,并对它们应用群组级别的操作
 * @Author: allen
 * @Date: 2017/9/6
 */
public class Population {

    // 一组候选解
    private Individual population[];
    // 总群的总体适应度
    private double populationFitness = -1;

    /**
     * 初始化种群的规模
     */
    public Population(int populationSize){
        population = new Individual[populationSize];
    }

    /**
     * 初始化种群规模,并创建每一条染色体
     */
    public Population(int populationSize,int chromosomeLength){
        population = new Individual[chromosomeLength];
        for(int individualCount = 0;individualCount <populationSize;individualCount++){
            Individual individual = new Individual(chromosomeLength);
            population[individualCount] = individual;
        }
    }

    /**
     * 取得种群中的这组染色体
     */
    public Individual[] getIndividuals(){
        return population;
    }

    /**
     * 根据适应度排序,选择最优值
     */
    public Individual getFittest(int offset){
        Arrays.sort(this.population, new Comparator<Individual>() {
            @Override
            public int compare(Individual o1, Individual o2) {
                if(o1.getFitness() > o2.getFitness()){
                    return -1;
                }else if(o1.getFitness() < o2.getFitness()){
                    return 1;
                }
                return 0;
            }
        });
        return  this.population[offset];
    }
    /**
     * 取得群体适应度
     */
    public double getPopulationFitness() {
        return populationFitness;
    }
    /**
     * 设置群体适应度
     */
    public void setPopulationFitness(double populationFitness) {
        this.populationFitness = populationFitness;
    }
    /**
     * 取得种群规模
     */
    public int size(){
        return this.population.length;
    }
    /**
     * 为种群的某个位置插入染色体
     */
    public Individual setIndividual(int offset,Individual individual){
        return population[offset] = individual;
    }
    /**
     * 取得种群某个位置的染色体
     */
    public Individual getIndividual(int offset){
        return population[offset];
    }
}
