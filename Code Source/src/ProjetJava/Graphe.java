package ProjetJava;

import java.util.Vector;

public class Graphe{

	Vector<Sommet> sommets=new Vector<Sommet>();
	int size;
	String nom;
	
	public Graphe(int size){//Constructeur a partir de la taille du graphe
		this.size=size;
		nom="";
		for(int id=0;id<size;id++) {
			sommets.add(new Sommet(id));
		}
	}

	public void addArete(int i, int j) {//Ajoute une arete entre le i-eme sommet et le j-eme sommet
		sommets.get(i).addVoisin(sommets.get(j));
		sommets.get(j).addVoisin(sommets.get(i));
	}

	public void addSommet(int id) {//Ajoute un sommet au graphe
		sommets.add(new Sommet (id));
		this.size++;
	}
	
	public boolean isVoisin(int i, int j) {//Teste si le i-eme sommet et le j-eme sommet sont voisins
		return sommets.get(i).isVoisin(sommets.get(j));
	}
	
	public int trouverSommet (int id)//Retourne l'indice du sommet contenant l'id recherché
	{
		for (int i=0; i<size; i++)
		{
			if(sommets.get(i).id==id)
			{
				return i;
			}
		}
		//Sommet non trouvé
		return -1;
	}
	
	public void ColorierGraph ()//Colorie le graph
	{
		
		for (int i=0; i<sommets.size(); i++)//Pour chaque sommet dans l'ordre
		{
			sommets.get(i).colorier();
		}
		
	}

	@Override
	public String toString() {//Transforme le graph en String
		String str="Affichage du graphe actuel "+nom+" :\n";
		for (int i=0;i<size;i++)str+=sommets.get(i);
		str+="\n";
		return str;
	}
	
	public void copierSommets (Graphe T)//Copie tous les sommet du graphe en parametre
	{
		size=0;
		sommets=new Vector<Sommet>();
		
		for (int i=0; i<T.size; i++)
		{
			addSommet(T.sommets.get(i).id);
		}
	}
	
	public String getNom ()//Retourne le nom du graphe
	{
		return nom;
	}

}
