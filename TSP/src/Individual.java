/**
 * @Discription: 代表一个候选解及其染色体
 * @Author: allen
 * @Date: 2017/9/6
 */
public class Individual {

    // 染色体
    private int[] chromosome;
    // 个体适应度
    private double fitness = -1;

    /**
     * 初始化染色体长度，随机创建一条染色体
     */
    public Individual(int[] chromosome) {
        this.chromosome = chromosome;
    }

    /**
     * 随机创建一条染色体
     */
    public Individual(int chromosomeLength){
        this.chromosome = new int[chromosomeLength];
        for(int gene=0;gene < chromosomeLength ;gene++){
            // 1/2概率基因是0或1
            if(0.5 < Math.random()){
                setGene(gene,1);
            }else {
                setGene(gene,0);
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
        return chromosome.length;
    }

    /**
     * 取得染色体的某个位置的基因
     */
    public int getGene(int offset){
        return chromosome[offset];
    }

    /**
     * 为染色体的某个位置插入基因
     */
    public void setGene(int offset, int gene){
        chromosome[offset]  = gene ;
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
        for(int gene = 0 ; gene <chromosome.length;gene++){
            output += chromosome[gene];
        }
        return output;
    }

    public boolean containsGene(int gene){
        for(int i= 0; i < this.chromosome.length;i++){
            if(this.chromosome[i] == gene){
                return true;
            }
        }
        return false;
    }

}
