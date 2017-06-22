package it.polito.porto.bean;

import java.util.ArrayList;
import java.util.List;

public class PortoCreator implements Comparable<PortoCreator> {
	
	private int id ;
	private String given ;
	private String family ;
	
	private List<PortoArticle> articoli=new ArrayList<>() ;

	public PortoCreator(int id, String given, String family) {
		super();
		this.id = id;
		this.given = given;
		this.family = family;
		
		this.articoli = new ArrayList<>() ; // initialized with EMPTY list of articles!!!
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getGiven() {
		return given;
	}
	public void setGiven(String given) {
		this.given = given;
	}
	public String getFamily() {
		return family;
	}
	public void setFamily(String family) {
		this.family = family;
	}
	
	public List<PortoArticle> getArticoli() {
		return articoli;
	}

	public void addArticolo(PortoArticle art) {
		articoli.add(art) ;
	}
	
	public void setArticoli(List<PortoArticle> articoli) {
		this.articoli = articoli;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PortoCreator other = (PortoCreator) obj;
		if (id != other.id)
			return false;
		return true;
	}
	

	public String toString() {
		return String.format("%s %s", family, given) ;
	}
	
	public String toCSV() {
		return String.format("%d;\"%s\";\"%s\"", id, given, family) ;
	}
	
	@Override
	public int compareTo(PortoCreator o) {
		int fam = family.compareTo(o.family) ; 
		if( fam!=0 )
			return fam ;
		else
			return given.compareTo(o.given) ; 
	}
	
}
