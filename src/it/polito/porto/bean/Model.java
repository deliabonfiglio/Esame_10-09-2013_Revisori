package it.polito.porto.bean;

import java.util.*;
import it.polito.porto.dao.PortoDAO;

public class Model {
	private PortoCreatorIdMap map;
	private Map<Long, PortoArticle> mapA;
	
	
	public PortoCreatorIdMap getMappaCreatori() {
		if(map==null){
			map = new PortoCreatorIdMap();
			
			PortoDAO dao = new PortoDAO();
			List<PortoCreator> autori= dao.getAllCreator(map);
			
			for(PortoCreator pcre: autori){
				map.put(pcre);
			}			
		}
		return map;
	}

	public List<PortoArticle> getArticoli(PortoCreator pc) {
		List<PortoArticle> articoli = pc.getArticoli();
		
		if(articoli.isEmpty()){
			PortoDAO dao = new PortoDAO();
			articoli = dao.getArticlesOfCreator(pc);
		}
		Collections.sort(articoli);
		
		return articoli;
	}

	private Set<PortoCreator> getCreatoriArticolo(PortoArticle pa){
		PortoDAO dao = new PortoDAO();
		//System.out.println(dao.getCreatorsOfArticle(pa, map));
		
		return dao.getCreatorsOfArticle(pa, map);
	}
	
	public Collection<PortoCreator> getPossibiliRevisori(PortoArticle pa){
		Collection<PortoCreator> revisori = map.getValues();
		
		Set<PortoCreator> daRimuovere= new HashSet<PortoCreator>();
		
		PortoDAO dao = new PortoDAO();
		//autori dell'articolo
		Set<PortoCreator> autori = this.getCreatoriArticolo(pa);
		
		daRimuovere.addAll(autori);
		//System.out.println(autori.toString());
		
		for(PortoCreator autor: autori){
			//coautori degli autori dell'articolo
			List<PortoCreator> coautori = dao.getCoauthors(autor, map);

			daRimuovere.addAll(coautori);
			
			for(PortoCreator coautor: coautori){
				//coautori dei coautori
				
				List<PortoCreator> coautori2 = dao.getCoauthors(coautor, map);
				daRimuovere.addAll(coautori2);
			}			
		}
		
		revisori.removeAll(daRimuovere);
		
		return revisori;
	}
	
	
	public Map<Long, PortoArticle> getMappaArticoli(){
		if(mapA==null){
			PortoDAO dao = new PortoDAO();
			mapA = new HashMap<Long, PortoArticle>();
			List<PortoArticle> articles= dao.getAllArticle();
			
			for(PortoArticle pa: articles){
				mapA.put(pa.getEprintid(), pa);
			}
		}
		return mapA;
	}

}
