package ProjetJava;

import java.util.Vector;

//Classe qui permet de généraliser les graphes pour pouvoir les utiliser qu'ils soient orientés ou pas 
//Principe : contient un graphe normal, un graphe oriente et un booleen qui indique si on doit utiliser celui oriente ou non
public class DoubleGraph {
	Graphe grapheNormal;
	GrapheOriente grapheOriente;
	boolean estOriente;
	
	public DoubleGraph (int taille)//Constructeur a partir de la taille du graphe
	{
		grapheNormal = new Graphe (taille);
		grapheOriente = new GrapheOriente (taille);
		estOriente=false;
	}
	
	public void addSommet (int id)//Ajoute un sommet avec l'id en parametre
	{
		if (estOriente)
		{
			grapheOriente.addSommet(id);
		}
		else
		{
			grapheNormal.addSommet(id);
		}
	}
	
	public int trouverSommet (int id)//Retourne l'indice du sommet contenant l'id recherché
	{
		if (estOriente)
		{
			return grapheOriente.trouverSommet(id);
		}
		else
		{
			return grapheNormal.trouverSommet(id);
		}
	}

	public void addArete(int i, int j)//Ajoute une arete entre le i-eme sommet et le j-eme sommet
	{
		if(estOriente)
		{
			grapheOriente.addArete(i, j);
		}
		else
		{
			grapheNormal.addArete(i, j);
		}
	}

	public boolean contientSommet (int id)//Indique si un sommet a deja cet id 
	{
		return (trouverSommet(id)!=-1);
	}
	
	public String toString()//Transforme le graph en String 
	{
		if(estOriente)
		{
			return grapheOriente.toString();
		}
		else
		{
			return grapheNormal.toString();
		}
	}

	public int getsize ()//Retourne la taille du graphe
	{
		if(estOriente)
		{
			return grapheOriente.size;
		}
		else
		{
			return grapheNormal.size;
		}
	}
	
	public Vector<Sommet> getsommets ()//Retourne les sommets du graphe
	{
		if(estOriente)
		{
			return grapheOriente.sommets;
		}
		else
		{
			return grapheNormal.sommets;
		}
	}

	public void copier (DoubleGraph graph)//Permet de copier le graphe en parametre
	{
		grapheNormal=graph.grapheNormal;
		grapheOriente=graph.grapheOriente;
		estOriente=graph.estOriente;
	}
	
	public void colorier ()//Permet de colorier le graphe
	{
		if (estOriente)
		{
			grapheOriente.ColorierGraph();
		}
		else
		{
			grapheNormal.ColorierGraph();
		}
	}

	public void renommer (String nom)//Renomme le graphe
	{
		if (estOriente)
		{
			grapheOriente.nom=nom;
		}
		else
		{
			grapheNormal.nom=nom;
		}
	}
	
	public boolean isVoisin (int i, int j)//Teste si le i-eme sommet et le j-eme sommet sont voisins
	{
		if (estOriente)
		{
			return grapheOriente.isVoisin(i, j);
		}
		else
		{
			return grapheNormal.isVoisin(i, j);
		}
	}

	public String getNom ()//Retourne le nom du graphe
	{
		if(estOriente)
		{
			return grapheOriente.getNom();
		}
		else
		{
			return grapheNormal.getNom();
		}
	}
}
