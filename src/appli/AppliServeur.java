/**
 * Classe d'application du serveur
 * @author VO Thierry & VYAS Ishan
 * @version 1.7
 */
package appli;

import java.io.IOException;

import biblioth�que.Abonne;
import biblioth�que.Biblioth�que;
import biblioth�que.Document;
import documents.AgeLimite;
import documents.DVD;
import documents.Livre;
import serveur.ServeurEmprunt;
import serveur.ServeurReservation;
import serveur.ServeurRetour;

public class AppliServeur {

	public static void main(String[] args) {
		try {
			
			Biblioth�que b = new Biblioth�que();
			
			Document l1 = new Livre(1,"Regret et chagrin",b);
			Document l2 = new Livre(2,"Faisons Des Projets !",b);
			Document l3 = new Livre(3,"Pourquoi les gens ?",b);
			Document l4 = new Livre(4,"Popo, son histoire",b);
			
			Document d1 = new DVD(5,"RAMBO : Le retour au Vietnam",b, AgeLimite.SEIZE);
			Document d2 = new DVD(6,"Galactik Football : Saison 10",b, AgeLimite.DOUZE);
			
			Abonne a1 = new Abonne(1, "Thierry", "2000-12-12","thierry123@gmail.com");
			Abonne a2 = new Abonne(2, "Brette","1972-12-03","bretteDescartes@gmail.com");
			Abonne a3 = new Abonne(3, "Ishan","1999-03-07","ishanvyas99@gmail.com");
			
			b.getAbonn�s().put(a1.getNumero(),a1);
			b.getAbonn�s().put(a2.getNumero(),a2);
			b.getAbonn�s().put(a3.getNumero(),a3);
			
			b.getBiblio().put(l1.numero(),l1);
			b.getBiblio().put(l2.numero(),l2);
			b.getBiblio().put(l3.numero(),l3);
			b.getBiblio().put(l4.numero(),l4);
			
			b.getBiblio().put(d1.numero(),d1);
			b.getBiblio().put(d2.numero(),d2);
			
			System.out.println("Serveur d'emprunt lanc�");
			new Thread(new ServeurEmprunt(b)).start();
			System.out.println("Serveur de reservation lanc�");
			new Thread(new ServeurReservation(b)).start();
			System.out.println("Serveur de retour lanc�");
			new Thread(new ServeurRetour(b)).start();		

		} catch (IOException e) {
			System.err.println("Probl�me lors de la cr�ation du serveur : " + e);

		}
	}
}
