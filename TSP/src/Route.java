/**
 * Created by zhouhezhen on 2017/9/6.
 */
public class Route {

    private City route[];
    private double distance = 0;

    /**
     * 初始化路线
     */
    public Route(Individual individual,City cities[]){
        int chromosome[] = individual.getChromosome();
        route = new City[cities.length];
        for(int geneIndex = 0 ; geneIndex < chromosome.length;geneIndex++){
            route[geneIndex] =cities[chromosome[geneIndex]];
        }
    }

    /**
     * 得到路线距离
     */
    public double getDistance(){
        if(distance > 0){
            return distance;
        }

        double totalDistance = 0 ;
        for(int cityIndex =0 ; cityIndex +1 < this.route.length;cityIndex++){
            totalDistance += route[cityIndex].distanceFrom(route[cityIndex+1]);
        }

        totalDistance += this.route[this.route.length - 1].distanceFrom(this.route[0]);
        this.distance = totalDistance;

        return totalDistance;
    }

}
