/**
 * Enumération des ages limites
 * @author VO Thierry & VYAS Ishan
 * @version 1.0
 */
package documents;

public enum AgeLimite {

	  SEIZE (16),
	  DOUZE (12),
	  TOUT_PUBLIC (3);
	  
	  private int ageMax;
	   

	  AgeLimite(int ageMax){
	    this.ageMax = ageMax;
	  }
	   
	  public int getAgeMax() {
		  return ageMax;
	  }
	}