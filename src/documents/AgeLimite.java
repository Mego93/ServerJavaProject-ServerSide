package documents;

public enum AgeLimite {
	  //Objets directement construits
	  SEIZE (16),
	  DOUZE (12),
	  TOUT_PUBLIC (3);
	  
	  private int ageMax;
	   
	  //Constructeur
	  AgeLimite(int ageMax){
	    this.ageMax = ageMax;
	  }
	   
	  public int getAgeMax() {
		  return ageMax;
	  }
	}