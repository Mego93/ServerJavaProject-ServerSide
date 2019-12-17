package serveur;

import java.io.*;
import java.net.*;

import bibliothèque.Bibliothèque;
import services.ServiceEmprunt;

public class ServeurEmprunt implements Runnable {

	private static final int PORT_EMPRUNT = 2600;

	private ServerSocket ServerSocketEmprunt;
	private static Bibliothèque bibliothèque;
	/**
	 * Crée un serveur TCP pour le service Reservation
	 * @throws IOException
	 */
	public ServeurEmprunt(Bibliothèque bibliothèque) throws IOException {
		ServerSocketEmprunt = new ServerSocket(PORT_EMPRUNT);
		ServeurEmprunt.bibliothèque=bibliothèque;
	}

	/**
	 * Le serveur crée le service de Reservation
	 */
	@Override
	public void run() {

		// Service de Reservation
		try {
			System.err.println("Lancement du serveur d'emprunt au port " + this.ServerSocketEmprunt.getLocalPort());
			while (true) {
				new Thread(new ServiceEmprunt(ServerSocketEmprunt.accept(),bibliothèque)).start();

			}
		} catch (IOException e) {
			try {
				this.ServerSocketEmprunt.close();
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
			this.ServerSocketEmprunt.close();
		} catch (IOException e1) {
		}
	}
}