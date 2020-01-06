/**
 * Classe permettant le lancement d'un serveur de reservtion
 * @author VO Thierry & VYAS Ishan
 * @version 1.1
 */
package serveur;

import java.io.*;
import java.net.*;

import bibliothèque.Bibliothèque;
import services.ServiceReservation;

public class ServeurReservation implements Runnable {

	private static final int PORT_RESERVATION = 2500;

	private ServerSocket ServerSocketReservation;
	private static Bibliothèque bibliothèque;
	
	/**
	 * Crée un serveur TCP pour le service Reservation
	 * @throws IOException
	 */
	public ServeurReservation(Bibliothèque bibliothèque) throws IOException {
		ServerSocketReservation = new ServerSocket(PORT_RESERVATION);
		ServeurReservation.bibliothèque=bibliothèque;
	}

	/**
	 * Le serveur crée le service de Reservation
	 */
	@Override
	public void run() {

		// Service de Reservation
		try {
			System.err.println("Lancement du serveur de reservation au port " + this.ServerSocketReservation.getLocalPort());
			while (true) {
				new Thread(new ServiceReservation(ServerSocketReservation.accept(),bibliothèque)).start();

			}
		} catch (IOException e) {
			try {
				this.ServerSocketReservation.close();
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
			this.ServerSocketReservation.close();
		} catch (IOException e1) {
		}
	}
}