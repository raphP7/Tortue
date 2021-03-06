package controleur.fichier;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import affichage.Fenetre;
import affichage.FenetreDessin;

public class FichierScreen implements ActionListener{ // pour la capture d'ecran

	private Fenetre fenetre;
	private FenetreDessin lafenetreDeDessin;
	public FichierScreen(Fenetre fenetre,FenetreDessin lafenetreDeDessin){
		this.fenetre=fenetre;
		this.lafenetreDeDessin=lafenetreDeDessin;
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

		if(lafenetreDeDessin==null){
			JOptionPane.showMessageDialog(fenetre,
	       			 "Rien a exporter","ERREUR",JOptionPane.WARNING_MESSAGE);
			return;
		}
		this.lafenetreDeDessin.setAfficherCurseur(false);
		this.lafenetreDeDessin.repaint();
		
		Dimension rec= lafenetreDeDessin.getSize();
		BufferedImage lacapture=(BufferedImage)lafenetreDeDessin.createImage(rec.width,rec.height); // je recupere la taille de la capture

		lafenetreDeDessin.paint(lacapture.getGraphics()); // je recupere l'image de la capture
		try {
			JFileChooser Fenetredechoix = new JFileChooser(); // pour avoir la fenetre de choix d'emplacement du screenshot

			Fenetredechoix.setSelectedFile(new File("captureDessin")); // pour mettre un nom par defaut au fichier de la capture

			int returnVal = Fenetredechoix.showSaveDialog(Fenetredechoix); // je recupere le choix
			if(returnVal == JFileChooser.APPROVE_OPTION) { // si le choix a ?t? effectu?
				File outputFile = Fenetredechoix.getSelectedFile(); // demande l'emplacement de sauvegarde dans l'ordinateur choisi precedement
				String a=outputFile.getCanonicalPath(); // pour recuper le chemin et le nom du fichier ( emplacement choisi par l'utilisateur )
				File sortie=new File(a+".png"); // pour mettre a la suite l'extension au fichier de l'utilisateur
				ImageIO.write(lacapture, "png",sortie); // pour ecrire le fichier a l'enplacement voulut
			}
		}
		catch (IOException e) { //Si erreur avec l'enregistrement du fichier ,probleme de droit d'utilisateur , manque d'espace ...
			
			JOptionPane.showMessageDialog(fenetre,
       			 "Erreur Enregistrement image","ERREUR",JOptionPane.WARNING_MESSAGE);
		}
		
		this.lafenetreDeDessin.setAfficherCurseur(true);
		this.lafenetreDeDessin.repaint();
	}
}
