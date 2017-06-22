package it.polito.porto.bean;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


public class PortoCreatorIdMap {
	private Map<Integer, PortoCreator> map;

	public PortoCreatorIdMap(){
		this.map= new HashMap<Integer, PortoCreator>();
	}
	
	public PortoCreator get(int mat) {
		return map.get(mat);
	}

	public PortoCreator put(PortoCreator value){
		PortoCreator old = map.get(value);
		if(old == null){
			map.put(value.getId(), value);
			return value;
		} else 
			return old;
	}

	public Collection<PortoCreator> getValues() {
		return map.values();
	}
}
