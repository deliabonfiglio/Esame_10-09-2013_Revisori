package it.polito.porto.bean;

import java.util.ArrayList;
import java.util.List;

public class PortoArticle implements Comparable<PortoArticle>{

	private long eprintid ;
	private int date ; // publication year
	private String title ;
	
	private List<PortoCreator> creators ;
	
	public PortoArticle(long eprintid, int date, String title) {
		super();
		this.eprintid = eprintid;
		this.date = date;
		this.title = title;
		
		this.creators = new ArrayList<>() ; // initialized with EMPTY list of authors!!!
	}

	public long getEprintid() {
		return eprintid;
	}

	public void setEprintid(long eprintid) {
		this.eprintid = eprintid;
	}

	public int getDate() {
		return date;
	}

	public void setDate(int date) {
		this.date = date;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<PortoCreator> getCreators() {
		return creators;
	}

	public void setCreators(List<PortoCreator> creators) {
		this.creators = creators;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (int) (prime * result + eprintid);
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
		PortoArticle other = (PortoArticle) obj;
		if (eprintid != other.eprintid)
			return false;
		return true;
	}

	@Override
	public int compareTo(PortoArticle o) {
		// TODO Auto-generated method stub
		return -(this.getDate()-o.getDate());
	}

	@Override
	public String toString() {
		return date+" "+title+" "+eprintid;
	}
	
	

}
