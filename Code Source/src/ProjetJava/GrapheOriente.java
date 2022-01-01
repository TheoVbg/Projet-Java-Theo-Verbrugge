package ProjetJava;

public class GrapheOriente extends Graphe {
	
	public GrapheOriente (int size)//Met le même constructeur que dans la classe Graphe 
	{
		super(size);
	}
	
	@Override
	public void addArete (int i, int j)//Ajoute une arete oriente entre le i-eme sommet j-eme sommet
	{
		sommets.get(i).addVoisin(sommets.get(j));
	}
	
}
