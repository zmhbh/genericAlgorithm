package Lab_GA;

import java.util.Random;
import java.math.*;



public class Individual
{
	//Random rand = new Random();
	
	Random rand = PopAll.i_rand;		// I use the some seed to ensure the result to be same
    public static final int SIZE = 4;    // string length now is set to be 25
    private int[] genes = new int[SIZE];
    private double fitnessValue;
    
     public sc[] s1 = new sc[2];
     public sc[] s2 = new sc[2];
     public sc[] s3 = new sc[2];
     
     
    
  
    
//   final static private double WEIGHT_COST = 0.35;
//   final static private double WEIGHT_TIME = 0.30;
//   final static private double WEIGHT_RELIABLITY = 0.30;
//   final static private double WEIGHT_AVALIBILITY = 0.35;
    
   final static private int REVISED = 10000;
   final static private int deadLine = 40;
   final static private int VM = 5;
   final static private double RELIABLITY = 0.9;
    public Individual() {}

    public double getFitnessValue() {
        return fitnessValue;		//get the fitness of one individual 
    }

    public void setFitnessValue(double fitnessValue) {
        this.fitnessValue = fitnessValue;		//set the fitness of one individual 
    }

    public int getGene(int index) {
        return genes[index];		//get one particular gene of one individual 
    }

    public void setGene(int index, int gene) {
        this.genes[index] = gene;			//set one particular gene of one individual 
    }

    public void randGenes() {
        //Random rand = new Random();
    	//For initialization
    	for ( int i = 0; i < SIZE ; i++){
    	this.setGene(i,rand.nextInt(VM));    	
    	}
    }

    public void mutate() {        
        int index = rand.nextInt(SIZE);
    	this.setGene(index, rand.nextInt(VM));  
        
//        switch(index){
//        case 0 :{
//        	this.setGene(index, rand.nextInt(2));  
//        	break;
//        }
//        case 1 :{
//        	this.setGene(index, rand.nextInt(2));    
//        	break;
//        }
//        case 2 :{
//        	this.setGene(index, rand.nextInt(2));    
//        	break;
//        }
//        
//        }
        
    }

    public double evaluate() {					// figure out the fitness of one individual 
        double fitness = 0;
        double fcost =0;
        double ftime =0;
        double freliability =0;
        double favailablilty =0;
        
        /*
         * INPUT data
         */
        /**
        for (int i = 0; i < s1.length; ++i){
        	s1[i] = new sc();        	
        }
        for (int i = 0; i < s2.length; ++i){
        	s2[i] = new sc();        	
        }
        for (int i = 0; i < s3.length; ++i){
        	s3[i] = new sc();        	
        }
        
        for (int i = 0; i < s1.length; ++i){
        	s1[i].setCost(PopAll.data1[i][0]);
        	s1[i].setReliability((double)PopAll.data1[i][1]/100.0); // We need to maximize the Reliability, thus to minimize 100- reliability
        	s1[i].setTime(PopAll.data1[i][2]);
        	s1[i].setAvailability((double)PopAll.data1[i][3]/100.0);// Same as above 	
        }
        for (int i = 0; i < s2.length; ++i){
        	s2[i].setCost(PopAll.data2[i][0]);
        	s2[i].setReliability(PopAll.data2[i][1]/100.0); // We need to maximize the Reliability, thus to minimize 100- reliability
        	s2[i].setTime(PopAll.data2[i][2]);
        	s2[i].setAvailability(PopAll.data2[i][3]/100.0);// Same as above 	
        }
        for (int i = 0; i < s3.length; ++i){
        	s3[i].setCost(PopAll.data3[i][0]);
        	s3[i].setReliability(PopAll.data3[i][1]/100.0); // We need to maximize the Reliability, thus to minimize 100- reliability
        	s3[i].setTime(PopAll.data3[i][2]);
        	s3[i].setAvailability(PopAll.data3[i][3]/100.0);// Same as above 	
        }
        */
        
        /**
         * Next step is to calculate the Fitness function
         */
        
        
        							
//        fcost = s1[this.getGene(0)].cost+ s2[this.getGene(1)].cost;
//        ftime = s1[this.getGene(0)].time+s1[this.getGene(1)].time;
//        freliability = s1[this.getGene(0)].reliablity*s2[this.getGene(1)].reliablity;
//        favailablilty = s1[this.getGene(0)].availablity*s2[this.getGene(1)].availablity;            
//        
//        
//        fcost = Math.min(fcost, s1[this.getGene(0)].cost)+s3[this.getGene(2)].cost;
//        ftime = Math.min(ftime,s1[this.getGene(0)].time) +s3[this.getGene(2)].time;
//        freliability = Math.max(freliability, s1[this.getGene(0)].reliablity)*s3[this.getGene(2)].reliablity;
//       	favailablilty =	Math.max(favailablilty, s1[this.getGene(0)].availablity)*s3[this.getGene(2)].availablity;
       	
       	/**
       	 * As we need to minimize the cost and time, while maximize the reliability and availability
       	 * We need to do some modification to design a fitness function searching for the maximum.
       	 * Also normalization is needed.
       	 * For cost, max cost is min(30+53,30)+23=53, i use COST/53 to normalize and use 1-cost/53 to 
       	 * transfer searching for minimum to searching for maximum.
       	 * For time, max time is min (23+8,23)+12=35, likewise, i use TIME/35 to normalize and 1-time/35 to
       	 * be consistent with RELIABILITY and AVAILABILITY.
       	 */
        
        double timeRest = deadLine;	
        for ( int i = 0; i < SIZE; ++i){
        	int componentUnit = 2*i +1;
        	int cost = 100 - 20*this.getGene(i);
        	int time = this.getGene(i)+1;
	       	fcost = cost * componentUnit * RELIABLITY;
	       	timeRest = timeRest - time;
	       	if (timeRest == 0){
	       		fitness = 9999;
	       		break;
	       	}
	       	fcost = fcost * deadLine / ( timeRest );
	       	if (fcost < 0) 
	       		fcost = 9999;	       	
	       	fitness += fcost;
        }
        /*
       	fcost = s2[this.getGene(1)].cost * componetUnit[1] * s2[this.getGene(1)].reliablity;
       	timeRest = timeRest - s2[this.getGene(1)].time;
       	fcost = fcost * deadLine / ( timeRest );
    	if (fcost < 0) fcost = 200; 
       	fitness += fcost;
       	fcost = s3[this.getGene(2)].cost * componetUnit[2] * s3[this.getGene(2)].reliablity;
       	timeRest = timeRest - s3[this.getGene(2)].time;
       	fcost = fcost * deadLine / ( timeRest );
    	if (fcost < 0) fcost = 200; 
       	fitness += fcost;
       	*/
       	fitness = REVISED - fitness;
       	
       	/*
       	fitness = (1-fcost/53)*WEIGHT_COST
       			+(1-ftime/35)*WEIGHT_TIME
       			+favailablilty*WEIGHT_AVALIBILITY
       			+freliability*WEIGHT_RELIABLITY; 
       	 * 
       	 */
        this.setFitnessValue(fitness);

        return fitness;
    }
}
