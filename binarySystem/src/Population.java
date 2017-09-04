import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

/**
 * 一个种群或个体的一个世代,并对它们应用群组级别的操作
 * Created by zhouhezhen on 2017/6/23.
 */
public class Population {

    //一组染色体
    private Individual population[];
    //总群的总体适应度
    private double populationFitness = -1 ;
    /**
     * 初始化种群规模
     */
    public Population(int populationSize){
        this.population = new Individual[populationSize];
    }
    /**
     * 初始化种群规模,并创建每一条染色体
     */
    public Population(int populationSize, int chromosomeLength){
        this.population = new Individual[populationSize];
        for(int individualCount = 0 ; individualCount < populationSize; individualCount++){
            Individual individual = new Individual(chromosomeLength);
            this.population[individualCount] = individual;
        }
    }

    /**
     * 取得种群中的这组染色体
     */
    public Individual[] getIndividuals() {
        return this.population;
    }

    /**
     * 根据适应度排序,选择最优值
     */
    public Individual getFittest(int offset){
        Arrays.sort(this.population, new Comparator<Individual>() {
            public int compare(Individual o1, Individual o2) {
                if(o1.getFitness() > o2.getFitness()){
                    return -1;
                }else if(o1.getFitness() < o2.getFitness()){
                    return 1;
                }
                return 0;
            }
        });
        return this.population[offset];
    }
    /**
     * 设置群体适应度
     */
    public void setPopulationFitness(double fitness) {
        this.populationFitness = fitness;
    }
    /**
     * 取得群体适应度
     */
    public double getPopulationFitness() {
        return this.populationFitness;
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

    public void shuffle(){
        Random rnd = new Random();
        for(int i = population.length - 1;i >0;i--){
            int index = rnd.nextInt(i+1);
            Individual a = population[index];
            population[index] = population[i];
            population[i]  = a;
        }
    }
}
