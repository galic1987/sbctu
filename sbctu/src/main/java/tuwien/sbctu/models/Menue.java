package tuwien.sbctu.models;

import java.io.Serializable;

public class Menue {
	
	public enum MenuePizza implements Serializable{
		MARGARITA, SALAMI, CARDINALE;
		
		public String toString(){
			return super.toString().toLowerCase();
		}
	} 
	
	public int pizzaCount(){
		MenuePizza[] all = MenuePizza.values();
		return all.length;
	}
	
	public MenuePizza selectPizza(){
		MenuePizza[] all = MenuePizza.values();
		int random = (1 + (int)(Math.random() * ((all.length - 1) + 1))) -1;
		
		return all[random];
	}
	public Double getPricesFor(MenuePizza mp){
		Double result = 0.0;
		switch(mp){
		case MARGARITA:
			result = 5.00;
			break;
		case SALAMI:
			result = 5.50;
			break;
		case CARDINALE:
			result = 6.00;
			break;
		}
		return result;
	}
	
	public int getTimeFor(MenuePizza mp){
		int result = 0;
		switch(mp){
		case MARGARITA:
			result = 3000;
			break;
		case SALAMI:
			result = 7000;
			break;
		case CARDINALE:
			result = 5000;
			break;
		}
		return result;
	}
}
