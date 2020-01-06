/**
 * Classe ayant pour rôle de mettre un document
 * en attente (en réservation)
 * @author VO Thierry & VYAS Ishan
 * @version 1.1
 */
package timertasks;

import java.util.Timer;
import java.util.TimerTask;

import bibliothèque.Document;
import exception.RetourException;

public class DocumentAttente extends TimerTask{
	private Document document;
	private Timer timer;
	
	public DocumentAttente(Document document, Timer timer) {
		this.document = document;
		this.timer = timer;
	}

	@Override
	public void run() {
		try {
			this.document.retour();
			this.timer.cancel();
		} catch (RetourException e) {
			e.printStackTrace();
		}
		
	}
	
}
