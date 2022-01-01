package ProjetJava;

//Gere l'affichage texte du programme
public class AffichageTexte 
{
	Fonctions fct;//Permet d'utiliser les fonctions de la classe fonction
	 
	AffichageTexte()//Initialise l'affichage texte
	{
		fct=new Fonctions ();
	}
	
	public int choisir (DoubleGraph graph)//Propose intelligement le choix a l'utilisateur et renvoie sa decision
	{
		int choix=-1;
		int taille;
		while (choix<0||choix>7)
		{
			taille=graph.getsize();
			System.out.println("\n\nQue voulez-vous faire: (Entrez le numéro de l'action à faire)\n");
			
			System.out.println("1 : Importer un fichier dot en graphe");
			
			System.out.println("2 : Creer un graphe aleatoire\n");
			
			if (!graph.estOriente&&taille!=0)
			{
				System.out.println("3 : Transformer le graphe actuel en arbre BFS");
			}
			
			if(taille!=0)
			{
				System.out.println("4 : Colorier le graphe actuel");
				
				System.out.println("5 : Afficher le graphe actuel sous forme dot");
				
				System.out.println("6 : Renommer le graphe actuel\n");
				
				System.out.println("7 : Exporter le graphe en fichier dot");
			}
			
			System.out.println("0 : Quitter\n");
	        
			
			choix=fct.DemanderEntier("Quel est votre choix : ");
			
		}
		
		
		
		return choix;
		
	}

	public boolean faireBoucle (DoubleGraph graph)//Fait une boucle principale du programme
	{
		System.out.println("\n"+graph.toString());//Affiche le graphe actuel
		fct.AttendreAction();//Attend que l'utilisateur appuie sur Enter
		int choix = choisir(graph);//Demande a l'utilisateur son choix
		boolean res = fct.EffectuerLeChoix(choix, graph);//Effectue le choix qu'a fait l'utilisateur
		fct.effacerAffichage();//Efface l'affichage
		return res;
		
	}
}
