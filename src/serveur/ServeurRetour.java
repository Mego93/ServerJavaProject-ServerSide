package serveur;

import java.io.*;
import java.net.*;

import bibliothèque.Bibliothèque;
import services.ServiceRetour;

public class ServeurRetour implements Runnable {

	private static final int PORT_RETOUR = 2700;

	private ServerSocket ServerSocketRetour;
	private static Bibliothèque bibliothèque;
	/**
	 * Crée un serveur TCP pour le service Reservation
	 * @throws IOException
	 */
	public ServeurRetour(Bibliothèque bibliothèque) throws IOException {
		ServerSocketRetour = new ServerSocket(PORT_RETOUR);
		ServeurRetour.bibliothèque=bibliothèque;
	}

	/**
	 * Le serveur crée le service de Reservation
	 */
	@Override
	public void run() {

		// Service de Reservation
		try {
			System.err.println("Lancement du serveur de retour au port " + this.ServerSocketRetour.getLocalPort());
			while (true) {
				new Thread(new ServiceRetour(ServerSocketRetour.accept(),bibliothèque)).start();

			}
		} catch (IOException e) {
			try {
				this.ServerSocketRetour.close();
			} catch (IOException e1) {
			}
			System.err.println("Pb sur le port d'écoute :" + e);
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