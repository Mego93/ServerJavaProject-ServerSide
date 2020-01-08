/**
 * Classe ayant pour service retour
 * @author VO Thierry & VYAS Ishan
 * @version 3.0
 */
package services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import biblioth�que.Biblioth�que;
import codage.Decodage;
import exception.RetourException;

public class ServiceRetour implements Runnable {
	private final Socket client;
	private static Biblioth�que biblioth�que;

	public ServiceRetour(Socket socket, Biblioth�que biblioth�que) {
		client = socket;
		ServiceRetour.biblioth�que = biblioth�que;
	}

	@Override
	public void run() {
		String reponse = null;
		boolean affichageBiblio = true;

		do {
			try {
				BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
				PrintWriter out = new PrintWriter(client.getOutputStream(), true);

				if (affichageBiblio)
					out.print(Decodage.encoder(biblioth�que.toStringAbonn�s() + "\n" + biblioth�que.toStringDocs()));
				affichageBiblio = false;
				// Demande au client l'instruction propos�e
				out.println("Le num�ro de document � retourner :");
				int noDoc = Integer.parseInt(in.readLine());
				System.out.println("Requ�te pour le document n�" + noDoc + " pour un retour (IP:"
						+ this.client.getInetAddress() + ")");
				boolean verification = biblioth�que.getBiblio().containsKey(noDoc);
				if (verification)
					try {
						biblioth�que.getBiblio().get(noDoc).retour();
						reponse = Decodage.encoder("Retour du document n�" + noDoc
								+ " r�ussi, il est de nouveau disponible � la biblioth�que. \n");
					} catch (RetourException e) {
						System.out.println("RetourException : Le document n�\"+ noDoc + \" ne peut �tre retourn�.");
						reponse = Decodage.encoder("Le document n�" + noDoc + " ne peut �tre retourn�. \n");
					}
				else {
					reponse = Decodage.encoder("Le document n'existe pas. \n");
				}
				System.out.println(Decodage.decoder(reponse));
				out.println(reponse + "Voulez vous arr�ter ? ('O')");
				String repArret = in.readLine();
				if (repArret.equals("O"))
					break;

			} catch (IOException e) {
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		} while (true);
		try {
			client.close();
		} catch (IOException e2) {
		}
	}

	/**
	 * Ferme le client
	 */
	protected void finalize() throws Throwable {
		client.close();
	}

	/**
	 * Cr�e un thread
	 */
	public void lancer() {
		new Thread(this).start();

	}
}
