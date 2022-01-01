package ProjetJava;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;
import java.io.FileReader;

import java.io.BufferedReader;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Fonctions {
	
	Scanner scanner;//Permet d'utiliser un seul et meme scanner
	
	public Fonctions()//Initialise le scanner
	{
		scanner = new Scanner( System.in ); 
	}
	
	
	public boolean EffectuerLeChoix (int choix, DoubleGraph graphactuel)//Effectue l'action demandée par l'utilisateur
	{
		int taille=graphactuel.getsize();//Recupere la taille du graphe
		
		switch (choix)
		{
		case 0://Quitter
			return false;
			
		case 1://Importer un fichier dot en graphe
			String nomFic = DemanderNom ("Quel est le nom du fichier contenant le language dot a traiter (avec le .txt) ? ");
            graphactuel.copier(TransformerDotEnGraphe(nomFic));//Change le graphe actuel
	            
			break;
			
		case 2://Creer un graphe aleatoire
			String strChoix = DemanderNom( "Voulez vous un graphe oriente ? (Mettre \"oui\" si vous voulez)");
            boolean choixOrient=(strChoix.contains("oui"));
            int tailleAl=DemanderEntier ("De quelle taille devra etre le graphe aleatoire ? ");
            DoubleGraph alea = CreerGrapheAleatoire(tailleAl, choixOrient);
            alea.renommer("Aleatoire");
            graphactuel.copier(alea);//Change le graphe actuel
	            
			break;
			
		case 3://Transformer le graphe actuel en arbre BFS
			if (!graphactuel.estOriente&&taille>0)
			{
				int idracine=DemanderEntier( "Quel sommet prendre pour le BFS ? (indiquer son id)");
				try//Recupere le sommet avec l'id donne
				{
					Sommet racine = graphactuel.getsommets().get(graphactuel.trouverSommet(idracine));
					graphactuel.copier(GrapheToBFS(graphactuel,racine));
				}
				catch (Exception ex)
				{
					System.out.println("Erreur dans le choix du sommet");
					AttendreAction();
				}
				
			}
			break;
			
		case 4://Colorier le graphe actuel
			if(taille!=0)
			{
				graphactuel.colorier();
			}
			break;
			
		case 5://Afficher le graphe actuel sous forme dot
			if(taille!=0)
			{
				effacerAffichage();
				String dot = GrapheToStringDot (graphactuel);
				System.out.println("Affichage du graphe sous forme dot :\n"+dot);
				AttendreAction();
			}
			break;
			
		case 6://Renommer le graphe actuel
			if(taille!=0)
			{
				String nom = DemanderNom( "Quel nouveau nom donner au graph ? ");
	            graphactuel.renommer(nom);
			}
			
			break;
		case 7://Exporter le graphe en fichier dot
			if(taille!=0)
			{
				String nomFichier=DemanderNom( "Quel nom donner au fichier (laisser vide pour garder le nom du graphe) ? ");
				
	            if (nomFichier=="")//Recupere le nom du graphe
	            {
	            	nomFichier=graphactuel.getNom();
	            }
	            nomFichier+=".txt";
	           
				GrapheToDot(graphactuel, nomFichier);
			}
			
			break;
		default :
			
			break;
		}
		return true;
	}
	
	public static void GrapheToDot (DoubleGraph graph, String nomFic)//Exporte le graph sous le nom nomFic au format dot

	{
		
		String str = GrapheToStringDot (graph);
		
		Path path = Paths.get(nomFic);//obtient le path a partir du nom de fichier
		
		try {//Ecrit le dot dans le path
			byte[] bs = str.getBytes();
			Path writtenFilePath = Files.write(path, bs);
			System.out.println("Written content in file:\n"+ new String(Files.readAllBytes(writtenFilePath)));
		}
		catch (java.io.IOException e) 
		{
			e.printStackTrace();
		}
	}

	public static String GrapheToStringDot (DoubleGraph graph)//Transforme le graphe en String
	{
		int nbVoisins=0;
		String str="";//String retourné
		String ligne="";//String temporaire pour chaque ligne
		
		if (graph.estOriente)//Ecrit l'entete
		{
			str+="digraph "+graph.grapheOriente.nom+" {";
		}
		else
		{
			str+="graph "+graph.grapheNormal.nom+" {";
		}
		
		boolean afficherCouleur=false; //Permet de ne pas afficher la couleur si elle n'a aucun sommet 
		String chaineTempo="";
		int size=graph.getsize();
		
		str+="\n";
			
		//Définit les couleurs
		for (int k=0;k<16;k++)//Ecrit les sommets pour chaque couleur
		{
			afficherCouleur=false;
			chaineTempo="\nnode [style=filled,color="+TransformerCouleur(k)+"];\n";//Préset la couleur
			for (int l=0; l<size; l++)//On parcourt tous les sommets
			{
				
				if (graph.getsommets().get(l).couleur==k)//Si la couleur du sommet = la couleur parcourue
				{
					chaineTempo+=graph.getsommets().get(l).id+";";//On ecrit le sommet de cette couleur
					afficherCouleur=true;
				}
					
			}
			
			if (afficherCouleur)//Si cette couleur a des sommets
			{
				str=str+chaineTempo;//On l'ajoute au dot
			}
		}
		
		str+="\n";
		
		
		for (int n=0;n<graph.getsize();n++)//Pour chaque sommet
		{
			ligne="";
			nbVoisins=graph.getsommets().get(n).voisins.size();//Recupere le nb de voisins
			
			for (int j=0;j<nbVoisins;j++)//Pour chaque voisin de ce sommet
				{
					if (graph.estOriente)//Ecrit les aretes orientes 
					{
						ligne+=graph.getsommets().get(n).id;
						ligne+=" -> ";
						ligne+=graph.getsommets().get(n).voisins.get(j).id;
						ligne+="; ";
					}
					else//Ecrit les aretes non orientes
					{
						if (graph.getsommets().get(n).id<=graph.getsommets().get(n).voisins.get(j).id)//Evite d'avoir les instructions en double
						{
							ligne+=graph.getsommets().get(n).id;
							ligne+=" -- ";
							ligne+=graph.getsommets().get(n).voisins.get(j).id;
							ligne+="; ";
						}
					}
					
		}
			
			if(ligne!="") {str+="\n"+ligne;}//Evite les lignes vides
		}
		
		str+="\n";
		str+="}";
		
		return str;
	}
	
	public DoubleGraph GrapheToBFS (DoubleGraph G, Sommet r)//Transforme un graphe non oriente en BFS
	{
		DoubleGraph res = new DoubleGraph (0);//Nouveau graphe qui sera le BFS
		
		if (G.estOriente)//Test qu'il n'est pas oriente
		{
			System.out.println("Erreur: on ne peut pas faire de BFS sur un graphe oriente.");
			AttendreAction();
		}
		else
		{
			res.grapheNormal.nom=G.getNom();
			Graphe T = new Graphe (0);//On créé un graphe T qui sera le BFS
			T.copierSommets(G.grapheNormal);
			r.flag=true; //On marque le premier la racine (evite de la retrouver 2 fois)
			LinkedList<Sommet> Q = new LinkedList<Sommet> ();//On créé une file d'attente Q et on met R dedans
			Q.add(r);
			
			Sommet sommetActuel;
			int indiceSommetActuel, indiceVoisin;
			
			
			while(Q.size()>0)//Tant que la liste d'attente n'est pas vide
			{
				sommetActuel=Q.remove();//On définit sommetActuel comme le premier sommet de la file
				
				for (int i=0; i<sommetActuel.voisins.size(); i++)//On parcourt tous les voisins du sommetActuel
				{
					indiceSommetActuel=T.trouverSommet(sommetActuel.id); //Cherche l'indice dans T du sommetActuel
					if (!sommetActuel.voisins.get(i).flag)//Pour chaque de ses voisins si il n'est pas dans T
					{
						Q.add(sommetActuel.voisins.get(i));//Alors on l'ajoute à Q
						
						indiceVoisin=T.trouverSommet(sommetActuel.voisins.get(i).id); //Cherche l'indice dans T du sommet Voisin que l'on traite
						T.addArete(indiceSommetActuel, indiceVoisin);//Créé l'arete dans T entre le SommetActuel et le sommet Voisin que l'on traite
					}
					
					sommetActuel.voisins.get(i).flag=true; //On marque ce voisin qui a été ajouté à T
				}
					
			}
			
			res.grapheNormal=T;
			res.estOriente=false;
		}
		
		
		return res;
	}
	
	public static String TransformerCouleur (int color)//Transforme un numéro de couleur en couleur (jusqu'à 16)
	{
		color=color%16;
		
		switch (color)
		{
		case 0:
			return "red";
		case 1:
			return "cyan";
		case 2:
			return "blue";
		case 3:
			return "navy";
		case 4:
			return "lightblue";
		case 5:
			return "purple";
		case 6:
			return "yellow";
		case 7:
			return "lime";
		case 8:
			return "pink";
		case 9:
			return "silver";
		case 10:
			return "gray";
		case 11:
			return "orange";
		case 12:
			return "brown";
		case 13:
			return "maroon";
		case 14:
			return "green";
		case 15:
			return "olive";
		default:
			return "";
				
		}
	}

	 public static DoubleGraph CreerGrapheAleatoire (int taille, boolean oriente)//Créé et retourne un graphe aleatoire de taille choisie, oriente ou non
	{
		DoubleGraph res=new DoubleGraph (0);
		res.estOriente=oriente;
		
		//Variables temporaires
		int nbmax=0;
		double a=0,b,c;
		int abis=0, bbis;
		boolean test1=false, test2;
		
		int [] tabAlea = new int [taille];//Stocke les valeurs aleatoires des sommets 
		boolean [] valPrises = new boolean [100];//Permet de marquer les valeurs des id deja prises
		
		for (int k=0; k<100; k++)//Initialise le tableau
		{
			valPrises[k]=false;
		}
		
		for (int i=0; i<taille; i++)//Attribue des id aleatoires entre 0 et 100 a chaque sommet
		{
			test1=true;
			while (test1)//S'assure que le nombre n'a pas deja ete pris
			{
				a=Math.random()*100;
				abis=(int)a;
				
				test1=valPrises[abis];
			}
			
			tabAlea[i]=abis;
			valPrises[abis]=true;
			
		}
		
		res.addSommet(tabAlea[0]);
		for (int i=1; i<taille; i++)
		{
			
			res.addSommet(tabAlea[i]);
			res.addArete(i-1, i);//S'assure que tous les sommets sont relies en les reliant
		}
		
		if (res.estOriente)
		{
			nbmax=taille*(taille-1);//Nb max d'aretes = taille * (taille-1) pour un graphe oriente
		}
		else
		{
			for (int i=1; i<taille; i++)//Nombre max d'aretes = 1+2+....+(taille-1) pour un graphe non oriente
			{
				nbmax=nbmax+i;
			}
		}
		
		nbmax=nbmax-taille+1;//On a deja ajoute (taille-1) aretes precedemment donc on les enleves au nbmax
				
		c=Math.random()*nbmax;//Prend un nombre aleatoire d'aretes entre 0 et Nb max d'aretes
		
		for (int j=0; j<(int)c; j++)//Ajoute le nombre d'aretes tirees au hasard
		{
			//Tirage des 2 sommets aleatoirement (ils sont double)
			a=Math.random()*taille;
			b=Math.random()*taille;
			
			//Les transforme en entiers
			abis=(int)a;
			bbis=(int)b;
			
			//Verifie que ce n'est pas le meme nombre
			test1=(abis!=bbis);
			//Verifie que ils ne sont pas deja voisins
			test2=!res.isVoisin(abis,bbis);
			
			if(test1&&test2)//Si ces 2 conditions sont validees ont créé une arete
			{
				
				res.addArete(abis, bbis);//Ajoute une arete entre deux nombres aleatoires
			}
			
		}

		return res;
	}

	public String LireFichier (String nomFic)//Lit le fichier du nom passe en parametre et le retourne sous forme de String
	{
		BufferedReader lecteurAvecBuffer = null;
	    String ligne="";
	    String dot="";
	    boolean test=false;

	    try//Essaye d'ouvrir le fichier
	    {
	    	lecteurAvecBuffer = new BufferedReader(new FileReader(nomFic));
	    }
	    	catch(FileNotFoundException exc)
	    {
	    	System.out.println("Erreur d'ouverture");
	    	AttendreAction ();
	    	return "";
	    }
	    
	    
	    while (!test)//Tant qu'il reste des lignes dans le fichier
	    {

	      try//Lire la ligne
	      {
	    	  ligne = lecteurAvecBuffer.readLine();
	      }
	      catch(IOException exc)
	      {
	    	  test=true;
	      }
	      
	      if(ligne!=null)//Si elle n'est pas nulle on l'ajoute au String dot
	      {
	    	  dot=dot+ligne;
	      }
	      else
	      {
	    	  test=true;
	      }
	      
	    }
	    
	    
	    try//On ferme le fichier
	    {
	    	lecteurAvecBuffer.close();
	    }
	    catch(IOException exc)
	    {
	    	System.out.println("Erreur Fermeture");
	    }
	    
	    return dot;
		
	}
	
	public DoubleGraph TransformerDotEnGraphe (String nomFicDot)//Transforme le fichier de nom donne en graphe
	{
		DoubleGraph Vide = new DoubleGraph (0);//Au cas ou il y a une erreur on retournera un graphe vide
		DoubleGraph Resultat = new DoubleGraph (0);//Graphe retourne au final
		
		//Variables temporaires
		int indicek=-1;
		int indicek1=-1;
		int idsommet=-1;
		
		String dot = LireFichier (nomFicDot);//Recupere le contenu du fichier
		dot=dot.replaceAll("\\s", "");//Enleve les espaces
		
		boolean digraph=dot.contains("digraph");//Test si c'est un graphe oriente
		Resultat.estOriente=digraph;
		
		String[] blocs = dot.split("\\{");//Split en blocs
		
		if(digraph)//Recupere et applique le nom du graph (en supprimant les espaces)
		{
			Resultat.renommer(blocs[0].replaceFirst("digraph", ""));
		}
		else
		{
			Resultat.renommer(blocs[0].replaceFirst("graph", ""));
		}

		
		
		ArrayList<String[]> instructions = new ArrayList<>() ;//Initialise la variable qui stocke les instructions 
		
		for(int i=0; i<blocs.length; i++)//Pour chaque bloc
		{
			instructions.add(blocs[i].split(";"));//Split pour recuperer chaque instruction

			if(i!=0)//Evite le bloc d'entete
			{
				for (int j=0; j<instructions.get(i).length; j++)//Pour chaque instruction
				{
					
					if (instructions.get(i)[j].contains("--"))//Ajouter arete
					{
						if (digraph)//Test si il y a une incoherence
						{
							System.out.println("Erreur le graphe non-oriente contient une arete oriente.");
							AttendreAction();
							return Vide;
						}
						
						
						String[] instructionArete=instructions.get(i)[j].split("--");//Split les id de sommets
						
						for(int k=0; k<instructionArete.length-1; k++)//Pour chaque paire adjacente de sommets
						{
							indicek=Resultat.trouverSommet(Integer.parseInt(instructionArete[k]));//Donne l'indice du sommet avec l'id k dans le graphe Resultat (-1 si pas trouvé)
							indicek1=Resultat.trouverSommet(Integer.parseInt(instructionArete[k+1]));//Donne l'indice du sommet avec l'id k+1 dans le graphe Resultat (-1 si pas trouvé)
							
							
							if (indicek==-1)//Si l'id du sommet k n'appartient pas au graphe on l'ajoute
							{
								
								Resultat.addSommet(Integer.parseInt(instructionArete[k]));
								indicek=Resultat.trouverSommet(Integer.parseInt(instructionArete[k]));
								
							}
							
							if (indicek1==-1)//Si l'id du sommet k+1 n'appartient pas au graphe on l'ajoute
							{
								
								
								Resultat.addSommet(Integer.parseInt(instructionArete[k+1]));
								indicek1=Resultat.trouverSommet(Integer.parseInt(instructionArete[k+1]));
							}
							
							Resultat.addArete(indicek, indicek1);//On ajoute l'arete entre ces deux sommets
						}
					}
					
					if (instructions.get(i)[j].contains("->"))//Ajouter arete oriente
					{
						if (!digraph)//Test si il y a une incoherence
						{
							System.out.println("Erreur le graphe oriente contient une arete non-oriente.");
							AttendreAction();
							return Vide;
						}
						
						String[] instructionArete=instructions.get(i)[j].split("->");//Split les id de sommets
						
						for(int k=0; k<instructionArete.length-1; k++)//Pour chaque paire adjacente de sommets
						{
							indicek=Resultat.trouverSommet(Integer.parseInt(instructionArete[k]));//Donne l'indice du sommet avec l'id k dans le graphe Resultat (-1 si pas trouvé)
							indicek1=Resultat.trouverSommet(Integer.parseInt(instructionArete[k+1]));//Donne l'indice du sommet avec l'id k+1 dans le graphe Resultat (-1 si pas trouvé)
							
							
							if (indicek==-1)//Si l'id du sommet k n'appartient pas au graphe on l'ajoute
							{
								
								Resultat.addSommet(Integer.parseInt(instructionArete[k]));
								indicek=Resultat.trouverSommet(Integer.parseInt(instructionArete[k]));
								
							}
							
							if (indicek1==-1)//Si l'id du sommet k+1 n'appartient pas au graphe on l'ajoute
							{
								
								
								Resultat.addSommet(Integer.parseInt(instructionArete[k+1]));
								indicek1=Resultat.trouverSommet(Integer.parseInt(instructionArete[k+1]));
							}
							
							Resultat.addArete(indicek, indicek1);//On ajoute l'arete entre ces deux sommets
						}
						
							
							
							
					}
					
					
					if (!(instructions.get(i)[j].contains("}")||instructions.get(i)[j].contains("-")||instructions.get(i)[j].contains("node")))//Si c'est une declaration de sommet
					{
						idsommet=Integer.parseInt(instructions.get(i)[j]);//On recupere l'id de sommet sous forme d'entier
						
						if (!Resultat.contientSommet(idsommet))//Si le sommet n'a pas deja ete créé on le créé
						{
							Resultat.addSommet(idsommet);
						}
					}
				}
			
		
			
			}
		
		
		}
		
		
		
		return Resultat;
		
	}
	
	public void effacerAffichage()//Efface ce qui est ecrit a l'ecran
	{
		for (int i=0; i<100; i++)
		{
			System.out.println("\n");
		}
	}
	
	public void AttendreAction ()//Permet d'attendre que l'utilisateur appuie sur Entrer
	{
		System.out.println( "Appuyer sur Entrer pour continuer le programme");
        scanner.nextLine();
	}

	public int DemanderEntier (String message)//Demande a l'utilisateur un entier valide avec le message voulu
	{
		String strNb="";
		boolean test=false;
		int nb=0;
		
		
		while (!test)//Tant que ce n'est pas un entier valide
		{
			System.out.println(message);
			strNb = scanner.nextLine();
			try
		    {
		        nb=Integer.parseInt(strNb);
		        test=true;
		    } catch (NumberFormatException ex)
		    {
		    	System.out.println("Erreur: ce doit etre un entier valide");
		        test=false;
		    }
		}
		return nb;
	}
	
	public String DemanderNom (String message)//Demande a l'utilisateur un String avec le message voulu
	{
		String nom="";
		System.out.println(message);
		nom = scanner.nextLine();
		return nom;
	}
	
}
