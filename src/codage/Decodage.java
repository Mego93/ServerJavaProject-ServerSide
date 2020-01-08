/**
 * Classe permettant le codage/decodage des communications serveur/client
 * @author VO Thierry & VYAS Ishan
 * @version 2.5
 */
package codage;

public class Decodage {

	public static String encoder(String s) {
		return s.replace("\n", "#n");
	}
	
	public static String decoder(String s) {
		return s.replace("#n", "\n");	
	}
}
