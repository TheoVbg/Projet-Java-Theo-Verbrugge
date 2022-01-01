package ProjetJava;

//Boucle principale du programme
public class main {

	public static void main(String[] args) {
		AffichageTexte txt = new AffichageTexte ();//Initialise l'affichage texte
		DoubleGraph graphe = new DoubleGraph (0);//Initialise le graphe sur lequel on travaille
		boolean continuer = true;//Initialise la variable qui test si le programme doit continuer
		
		System.out.println("Bienvenue! \n\n");
		
		while (continuer)//Tant que l'utilisateur veut continuer
		{
			continuer=txt.faireBoucle(graphe);//Faire la boucle texte
		}


	}

}
