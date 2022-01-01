package ProjetJava;

import java.util.Vector;

public class Sommet {

	int id;
	Vector<Sommet> voisins=new Vector<Sommet>();
	boolean flag=false;
	int couleur; 
	
	
	Sommet(int id) {//Constructeur avec l'id
		this.id=id;
		couleur=-1;
		
	}
	
	
	void addVoisin(Sommet s) {//Permet d'ajouter un sommet en voisin
		if (!isVoisin(s))
		{
			voisins.add(s);
		}
	}
	
	
	void setCouleur (int Couleur)//Change la couleur 
	{
		couleur=Couleur;
	}
	
	int getCouleur ()//Permet d'obtenir la couleur
	{
		return couleur;
	}
	
	void colorier ()//Colorie le sommet en fonction des couleurs sommets voisins
	{
		int couleurMinTempo=-1;//Stocke le numero minimum de couleur disponible
		boolean [] couleurprises=new boolean [16];//Note les couleurs prises
		for(int i=0; i<16; i++) {couleurprises[i]=false;}//Initialise le tableau a false
		
		for (int k=0; k<voisins.size(); k++)//Pour chacun des voisins de ce sommet on note les couleurs prises
		{
			if(voisins.get(k).getCouleur()!=-1)
			{
				couleurprises[voisins.get(k).getCouleur()]=true;
			}
		}
		
		boolean test=true;
		while (test)//Permet a couleurMinTempo d'avoir le numero minimum de couleur disponible
		{
			couleurMinTempo++;
			test=couleurprises[couleurMinTempo];
		}
		
		setCouleur(couleurMinTempo);//On définit la couleur comme couleurMinTempo
	}
	
	boolean isVoisin(Sommet s) {//Test si le sommet donne est un voisin
		return voisins.contains(s);
	}
	
	@Override
	public String toString() {//Transforme le Sommet en String
		String str="";
	
		if(couleur!=-1) {str+="["+couleur+"]";}
		
		str+=id+":(";
		if(voisins.size()-1<0)
		{
			return str+")\n";
		}
		for (int i=0;i<voisins.size()-1;i++)str+=voisins.get(i).id+",";
		return str+voisins.get(voisins.size()-1).id+")\n";
	}
}
