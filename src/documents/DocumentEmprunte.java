package documents;

import java.util.Timer;
import java.util.TimerTask;

import bibliothèque.Abonne;

public class DocumentEmprunte extends TimerTask{
	private Timer timer;
	private Abonne abonne;
	
	public DocumentEmprunte( Abonne ab, Timer timer) {
		this.abonne = ab;
		this.timer = timer;
	}

	@Override
	public void run() {
		try {
			this.abonne.punission();
			this.timer.cancel();
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		
	}
	
}
