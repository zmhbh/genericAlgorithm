package Lab_GA;

public class sc{
	public int cost = 0;
	public int time = 0;
	public double availablity = 0.0;
	public double reliablity = 0.0;
	
	public sc() {}
	
	public int getCost() {
		return this.cost;
	}
	public void setCost(int co){
		cost = co;
	}
	
	public int getTime(){
		return this.time;
	}
	public void setTime(int ti){
		time = ti;
	}
	
	public double getAvailability(){
		return this.availablity;
	}
	public void setAvailability(double av){
		availablity = av;
	}
	
	public double getReliabity(){
		return this.reliablity;
	}
	public void setReliability(double re){
		reliablity = re;
	}
}