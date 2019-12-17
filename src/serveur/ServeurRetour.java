package serveur;

import java.io.*;
import java.net.*;

import biblioth�que.Biblioth�que;
import services.ServiceRetour;

public class ServeurRetour implements Runnable {

	private static final int PORT_RETOUR = 2700;

	private ServerSocket ServerSocketRetour;
	private static Biblioth�que biblioth�que;
	/**
	 * Cr�e un serveur TCP pour le service Reservation
	 * @throws IOException
	 */
	public ServeurRetour(Biblioth�que biblioth�que) throws IOException {
		ServerSocketRetour = new ServerSocket(PORT_RETOUR);
		ServeurRetour.biblioth�que=biblioth�que;
	}

	/**
	 * Le serveur cr�e le service de Reservation
	 */
	@Override
	public void run() {

		// Service de Reservation
		try {
			System.err.println("Lancement du serveur de retour au port " + this.ServerSocketRetour.getLocalPort());
			while (true) {
				new Thread(new ServiceRetour(ServerSocketRetour.accept(),biblioth�que)).start();

			}
		} catch (IOException e) {
			try {
				this.ServerSocketRetour.close();
			} catch (IOException e1) {
			}
			System.err.println("Pb sur le port d'�coute :" + e);
		}

	}

	/**
	 * Restitution des ressources
	 */
	protected void finalize() throws Throwable {
		try {
			this.ServerSocketRetour.close();
		} catch (IOException e1) {
		}
	}
}