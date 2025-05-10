package main.java.algoritmo;

public class UnionFind {
	private int[] padre;
	private int[] rango;

	// TODO: Usar Map para no tener problemas con los IDs de las estaciones
	public UnionFind(int tamaño) {
		padre = new int[tamaño];
		rango = new int[tamaño];
		for (int i = 0; i < tamaño; i++) {
			padre[i] = i;
			rango[i] = 0;
		}
	}

	public int find(int p) {
		if (padre[p] != p) {
			padre[p] = find(padre[p]);
		}
		return padre[p];
	}

	public void union(int p, int q) {
		int raizP = find(p);
		int raizQ = find(q);
		if (raizP != raizQ) {
			if (rango[raizP] > rango[raizQ]) {
				padre[raizQ] = raizP;
			} else if (rango[raizP] < rango[raizQ]) {
				padre[raizP] = raizQ;
			} else {
				padre[raizQ] = raizP;
				rango[raizP]++;
			}
		}
	}
}
