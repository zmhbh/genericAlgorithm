package Lab_GA;

import java.util.Random;
import java.io.*;

import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
//import org.jfree.chart.plot.XYPlot;
//import org.jfree.chart.axis.*;
import org.jfree.chart.ChartFactory; 
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.JFreeChart; 
import org.jfree.chart.ChartUtilities; 

public class PopAll
{
	public static Random i_rand = new Random();  // random-number generator with seed 1234!
	final static int Run_times = 1;// Run How Many Times?
	final static int L = 4; //string length?
	final static int ELITISM_K = 0; // currently no elitism is allowed!
    final static int POP_SIZE = 1000 + ELITISM_K;  // Population size?
    final static int MAX_GENERATION =50;             // max number of generations?
    final static double MUTATION_RATE = 1/L;     // probability of mutation?
    final static double CROSSOVER_RATE = 0.7;     // probability of crossover?
    final static int REVISED = 10000;
    
    
   static public int[][] data1 = 
    		{{70,90,30,0},
			{80,90,10,0}};
   static public int[][] data2 = 
		   {{70,90,30,0},
			{80,90,10,0}};
   static public int[][] data3 = 
		   {{70,90,30,0},
			{80,90,10,0}};
    
    private Individual[] m_PopAll;
    private double totalFitness;

    public PopAll() {
        m_PopAll = new Individual[POP_SIZE];

        // init Population
        for (int i = 0; i < POP_SIZE; i++) {
            m_PopAll[i] = new Individual();
            m_PopAll[i].randGenes();
        }

        // evaluate current Population
        this.evaluate();
    }

    public void setPopAll(Individual[] newPop) {
        // this.m_PopAll = newPop;
        System.arraycopy(newPop, 0, this.m_PopAll, 0, POP_SIZE); // will be the next generation population
    }

    public Individual[] getPopAll() {
        return this.m_PopAll;
    }

    public double evaluate() {			// evaluate all fitness
        this.totalFitness = 0.0;
        for (int i = 0; i < POP_SIZE; i++) {
            this.totalFitness += m_PopAll[i].evaluate();
        }
        return this.totalFitness;
    }

    public Individual rouletteWheelSelection() { //rouletteWheelSelection
        double randNum = i_rand.nextDouble() * this.totalFitness;
        int idx;
        for (idx=0; idx<POP_SIZE && randNum>0; ++idx) {
            randNum -= m_PopAll[idx].getFitnessValue();
        }
        return m_PopAll[idx-1];
    }

    public Individual findBestIndividual() {// find best individual with max fitness 
        int idxMax = 0;
        double currentMax = 0.0;
        double currentMin = 1.0;
        double currentVal;

        for (int idx=0; idx<POP_SIZE; ++idx) {
            currentVal = m_PopAll[idx].getFitnessValue();
            if (currentMax < currentMin) {
                currentMax = currentMin = currentVal;
                idxMax = idx;
               
            }
            if (currentVal > currentMax) {
                currentMax = currentVal;
                idxMax = idx;
            }
            
            /*
                if (currentVal < currentMin) {
                currentMin = currentVal;
                idxMin = idx; 
            }
            */
        }

        //return m_PopAll[idxMin];      // minimization
        return m_PopAll[idxMax];        // maximization
       // return currentMax;
    }
    
    
    public Individual findWorstIndividual() { // find worst individual with min fitness
        int idxMin = 0;
        double currentMax = 0.0;
        double currentMin = 1.0;
        double currentVal;

        for (int idx=0; idx<POP_SIZE; ++idx) {
            currentVal = m_PopAll[idx].getFitnessValue();
            if (currentMax < currentMin) {
                currentMax = currentMin = currentVal;
                idxMin = idx;
            }
            /*
            if (currentVal > currentMax) {
                currentMax = currentVal;
                idxMax = idx;
            }
            */
            if (currentVal < currentMin) {
                currentMin = currentVal;
                idxMin = idx;
            }
        }

        return m_PopAll[idxMin];      // minimization
        //return m_PopAll[idxMax];        // maximization
       // return currentMax;
    }

    public static Individual[] crossover(Individual indiv1,Individual indiv2) {  //crossover
        Individual[] newIndiv = new Individual[2];
        newIndiv[0] = new Individual();
        newIndiv[1] = new Individual();

        int randPoint = i_rand.nextInt(Individual.SIZE);// random the point to cut
        int i;
        for (i=0; i<randPoint; ++i) {
            newIndiv[0].setGene(i, indiv1.getGene(i));
            newIndiv[1].setGene(i, indiv2.getGene(i));
        }
        for (; i<Individual.SIZE; ++i) {
            newIndiv[0].setGene(i, indiv2.getGene(i));
            newIndiv[1].setGene(i, indiv1.getGene(i));
        }

        return newIndiv;
    }

    
    public static double[] Meanfit = new double[MAX_GENERATION];
    public static double[] Bestfit = new double[MAX_GENERATION];
    public static double[] Worstfit = new double[MAX_GENERATION];
    public static double[] totaltime = new double[Run_times];
   
    
    public static void main(String[] args) {  //Main function here
    	
    	long timer1 = System.currentTimeMillis();
    	double MeanTimeTaken = 0.0;
    	double standardDev = 0.0;
    	
    	
    	for(int sprint = 0; sprint <Run_times ; ++sprint){
    		
    		
        PopAll pop = new PopAll();
        Individual[] newPop = new Individual[POP_SIZE];
        Individual[] indiv = new Individual[2];

        
        // current PopAll
        System.out.print("Mean Fitness = " + ( REVISED -pop.totalFitness/POP_SIZE));
        System.out.println(" ; Best Fitness = " + 
        		( REVISED - pop.findBestIndividual().getFitnessValue()));
        
        
        	        	
        
        double bestVal = 0;  //initial bestVal 
        
        // main loop
        int count;
        for (int generation = 0; generation < MAX_GENERATION ; generation++) {
            count = 0;

            // Elitism not needed
            
          /*  for (int i=0; i<ELITISM_K; ++i) {
                newPop[count] = pop.findBestIndividual();
                count++;
            }
            */

          
            // build new PopAll
            while ((count < POP_SIZE) ) {
            	
                // Selection
                indiv[0] = pop.rouletteWheelSelection();
                indiv[1] = pop.rouletteWheelSelection();

                // Crossover
                if ( i_rand.nextDouble() < CROSSOVER_RATE ) {
                    indiv = crossover(indiv[0], indiv[1]);
                }

                // Mutation
                if ( i_rand.nextDouble() < MUTATION_RATE ) {
                    indiv[0].mutate();
                }
                if ( i_rand.nextDouble() < MUTATION_RATE ) {
                    indiv[1].mutate();
                }

                // add to new PopAll
                newPop[count] = indiv[0];
                newPop[count+1] = indiv[1];
                count += 2;
                
               
                
            }
            
            pop.setPopAll(newPop);// refresh and re-evaluate new PopAll
            
            pop.evaluate(); 
            Individual bestIndiv = pop.findBestIndividual();
            Individual worstIndiv = pop.findWorstIndividual();
            
            /*
             * print out result in single generation
             */
            bestVal = pop.findBestIndividual().getFitnessValue();
            
            System.out.print("Mean Fitness = " + (REVISED-pop.totalFitness/POP_SIZE));
            System.out.println(
            " ; bestIndivFit = " + (REVISED-bestIndiv.getFitnessValue()) + " ;"
            	+ " worstIndivFit = " + (REVISED-worstIndiv.getFitnessValue()) 
            		+ " ; generation = " + generation             		
            			+ "; sprint = "+ sprint); 
            
            for ( int i = 0; i < L ; i++){
            	System.out.printf(" Gene "+(pop.findBestIndividual().getGene(i)+1));
            	
            }
            System.out.println();
             
             
             Meanfit[generation] =  REVISED -pop.totalFitness/POP_SIZE;
             Bestfit[generation] = REVISED -bestIndiv.getFitnessValue();
             Worstfit[generation] = REVISED -worstIndiv.getFitnessValue();
             
             totaltime[sprint] = generation ;
             
        }
        
        
    	MeanTimeTaken += totaltime[sprint];
    	
    	/*
    	 *plot on an x-y plane 
    	 */
        
        try {
            /* Define some XY Data series for the chart */
            XYSeries Meanfitness = new XYSeries("Meanfitness");
            XYSeries Bestfitness = new XYSeries("Bestfitness");
            XYSeries Worstfitness = new XYSeries("Worstfitness");
            for (int i = 0; i < MAX_GENERATION && PopAll.Meanfit[i] != 0 ; ++i){
            Meanfitness.add(i, PopAll.Meanfit[i]);
            Bestfitness.add(i, PopAll.Bestfit[i]);
            Worstfitness.add(i, PopAll.Worstfit[i]);
            }
            
           /*
            for (int i = 0; i <= 99 && PopAll.Meanfit[i] < 25 ; ++i){
               
                }
      
            for (int i = 0; i <= 99 && PopAll.Meanfit[i] < 25 ; ++i){
                
                }
            */  
            
            /* Add all XYSeries to XYSeriesCollection */
            //XYSeriesCollection implements XYDataset
            
            XYSeriesCollection my_data_series= new XYSeriesCollection();
            // add series using addSeries method
            
            my_data_series.addSeries(Meanfitness);
            my_data_series.addSeries(Bestfitness);
            my_data_series.addSeries(Worstfitness);
            
            
            //Use createXYLineChart to create the chart
            JFreeChart XYLineChart=ChartFactory.createXYLineChart("WorkflowSolution By GA","Generation","Fitness",my_data_series,PlotOrientation.VERTICAL,true,true,false);
      
            /* Write line chart to a file */               
             int width=1920; /* Width of the image */
             int height=1080; /* Height of the image */                
             File XYlineChart=new File("Run_No." + sprint + ".png");              
             ChartUtilities.saveChartAsPNG(XYlineChart,XYLineChart,width,height); 
     }
     catch (Exception i)
     {
         System.out.println(i);
     }
            
    	}
    	
    	
    	
     		
    	MeanTimeTaken /= Run_times;
    	standardDev = 0.0; 
    	for (int i =0 ; i < Run_times ; ++i){
    		standardDev += ( totaltime[i] - MeanTimeTaken ) * ( totaltime[i] - MeanTimeTaken );  		
    	}
    		standardDev /= Run_times;
    		standardDev = Math.sqrt (standardDev);
    		String Dev = String.format("%.2f",standardDev);
    		
    		System.out.println("***********************************************");
    		System.out.println("Mean of time taken of " + Run_times + " is " + MeanTimeTaken 
    									+ " and the Deviation is " + Dev );
       		System.out.println("***********************************************");
       		
       		//Out print the result of all run
    		
    		
       	  String s = new String();
       	  String s1 = new String();
       	  try {
       	   File Resultfile = new File("Result.txt");
       	   if(Resultfile.exists()){
       	    System.out.print("files exist already:");
       	   }else{
       	    System.out.print("files do not exist and have been created:");
       	 Resultfile.createNewFile();//not exist? then create a .txt file
       	   }
       	   BufferedReader input = new BufferedReader(new FileReader(Resultfile));
       	   
       	   while((s = input.readLine())!=null){
       	    s1 = "\n";
       	   }
       	   
       	   System.out.println(s1);
       	   input.close();
       	s1 += "********************************************************"+"\r\n";
       	s1 += "Mean of time taken of " + Run_times + " is " + MeanTimeTaken 
					+ " and the Deviation is " + Dev + "\r\n";
       	s1 += "********************************************************"+"\r\n";
       	long timer2 = System.currentTimeMillis();
       	
    	System.out.println("All Stages take : "+(timer2-timer1) + "ms");
       	BufferedWriter output = new BufferedWriter(new FileWriter(Resultfile));
       	   output.write(s1);
       	   //output.write(s);
       	   output.close();
       	  } 
       	  catch (Exception e) {
       	   e.printStackTrace();
       	  }
    		
    	
    	
    	
    }
}