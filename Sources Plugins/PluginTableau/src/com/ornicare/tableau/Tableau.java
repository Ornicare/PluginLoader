package com.ornicare.tableau;

public class Tableau implements ITableau {
	
	private int somme;

	public Tableau(int i1, int i2, int i3, int i4) {
		this.somme=i1+i2+i3+i4;
	}

	@Override
	public int getSomme() {
		return somme;
	}
}
