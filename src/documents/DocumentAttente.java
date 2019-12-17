package documents;

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
