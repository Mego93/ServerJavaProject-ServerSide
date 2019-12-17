package serveur;

import java.io.*;
import java.net.*;

import biblioth�que.Biblioth�que;
import services.ServiceEmprunt;

public class ServeurEmprunt implements Runnable {

	private static final int PORT_EMPRUNT = 2600;

	private ServerSocket ServerSocketEmprunt;
	private static Biblioth�que biblioth�que;
	/**
	 * Cr�e un serveur TCP pour le service Reservation
	 * @throws IOException
	 */
	public ServeurEmprunt(Biblioth�que biblioth�que) throws IOException {
		ServerSocketEmprunt = new ServerSocket(PORT_EMPRUNT);
		ServeurEmprunt.biblioth�que=biblioth�que;
	}

	/**
	 * Le serveur cr�e le service de Reservation
	 */
	@Override
	public void run() {

		// Service de Reservation
		try {
			System.err.println("Lancement du serveur d'emprunt au port " + this.ServerSocketEmprunt.getLocalPort());
			while (true) {
				new Thread(new ServiceEmprunt(ServerSocketEmprunt.accept(),biblioth�que)).start();

			}
		} catch (IOException e) {
			try {
				this.ServerSocketEmprunt.close();
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
			this.ServerSocketEmprunt.close();
		} catch (IOException e1) {
		}
	}
}