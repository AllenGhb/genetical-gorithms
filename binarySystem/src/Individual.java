/**
 * 代表一个候选解及其染色体
 * Created by zhouhezhen on 2017/6/22.
 */
public class Individual {

    //染色体
    private int[] chromosome;
    //个体适应度
    private double fitness = -1;

    /**
    * 初始化染色体长度，随机创建一条染色体
    */
    public Individual(int[] chromosome) {
        this.chromosome = chromosome;
    }

    //随机创建一条染色体
    public Individual(int chromosomeLength){
        this.chromosome = new int[chromosomeLength];
        for(int gene = 0; gene < chromosomeLength ; gene++){
            // 1/2概率基因是0或1
            if(0.5 < Math.random()){
                this.setGene(gene,1);
            }else {
                this.setGene(gene ,0);
            }
        }
    }

    /**
     * 取得染色体
     */
    public int[] getChromosome() {
        return chromosome;
    }

    /**
     * 取得染色体的长度
     */
    public int getChromosomeLength(){
        return this.chromosome.length;
    }
    /**
     * 取得染色体的某个位置的基因
     */
    public int getGene(int offset){
        return this.chromosome[offset];
    }
    /**
     * 为染色体的某个位置插入基因
     */
    public void setGene(int offset, int gene){
        this.chromosome[offset]  = gene ;
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    @Override
    public String toString() {
        String output = "";
        for(int gene = 0 ; gene <this.chromosome.length;gene++){
            output += this.chromosome[gene];
        }
        return output;
    }
}
