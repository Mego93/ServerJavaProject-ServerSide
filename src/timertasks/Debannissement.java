/**
 * Classe ayant pour rôle de "libérer" l'utilisateur
 * au bout d'un délai donné
 * @author VO Thierry & VYAS Ishan
 * @version 1.0
 */
package timertasks;

import java.util.Timer;
import java.util.TimerTask;

import bibliothèque.Abonne;
import exception.AbonnePuniException;

public class Debannissement extends TimerTask{
	private Abonne abonne;
	private Timer timer;
	
	public Debannissement(Abonne abonne, Timer timer) {
		this.abonne = abonne;
		this.timer = timer;
	}

	@Override
	public void run() {
		try {
			this.abonne.libérer();
			this.timer.cancel();
		} catch (AbonnePuniException e) {
			e.printStackTrace();
		}
		
	}
	
}
