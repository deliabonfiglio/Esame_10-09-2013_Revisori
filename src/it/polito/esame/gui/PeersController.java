/**
 * Sample Skeleton for 'peers.fxml' Controller Class
 */

package it.polito.esame.gui;

import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import it.polito.porto.bean.Model;
import it.polito.porto.bean.PortoArticle;
import it.polito.porto.bean.PortoCreator;
import it.polito.porto.bean.PortoCreatorIdMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class PeersController {

	private Model model;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtIdCreator"
    private TextField txtIdCreator; // Value injected by FXMLLoader

    @FXML // fx:id="x1"
    private Insets x1; // Value injected by FXMLLoader

    @FXML // fx:id="btnCercaArticoli"
    private Button btnCercaArticoli; // Value injected by FXMLLoader

    @FXML // fx:id="txtIdArticolo"
    private TextField txtIdArticolo; // Value injected by FXMLLoader

    @FXML // fx:id="btnCercaArticolo"
    private Button btnCercaArticolo; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCercaAricoli(ActionEvent event) {
    	
    	String matricola = txtIdCreator.getText();
    	
    	try{
    		int mat= Integer.parseInt(matricola);
    		
    		if(mat<=0){
    			txtResult.appendText("Inserire matricola maggiore di 0!\n");
    			return;
    		}
    		PortoCreatorIdMap  map = model.getMappaCreatori();
    		PortoCreator pc= map.get(mat);
    		
    		if(pc==null){
    			txtResult.appendText("Matricola non presente nel db autori.\n");
    			return;
    		}
    		
    		List<PortoArticle> articoli = model.getArticoli(pc);
    		txtResult.clear();
    		
    		for(PortoArticle pa: articoli){
    			txtResult.appendText(pa.toString()+"\n");
    		}
    		
    	}catch (NumberFormatException e){
    		txtResult.appendText("Errore: inserire matricola con sole cifre.\n");
    	}
    }

    @FXML
    void doCercaRevisori(ActionEvent event) {
    	txtResult.clear();
    	
    	String matricola = txtIdArticolo.getText();
    	
    	try{
    		long mat= Long.parseLong(matricola);
    		
    		if(mat<=0){
    			txtResult.appendText("Inserire codice articolo maggiore di 0!\n");
    			return;
    		}
    		PortoCreatorIdMap  map = model.getMappaCreatori();
    		
    		Map<Long, PortoArticle> mapA= model.getMappaArticoli();
    		//System.out.println(mapA.toString());
    		
    		PortoArticle pa= mapA.get(mat);
    		
    		if(pa==null){
    			txtResult.appendText("Articolo con questo id non presente nel db articoli.\n");
    			return;
    		}
    		
    		Collection<PortoCreator> revisori =model.getPossibiliRevisori(pa);
    		txtResult.clear();
    		
    		for(PortoCreator ptemp: revisori){
    			txtResult.appendText(ptemp.toString()+"\n");
    		}
    		txtResult.appendText(revisori.size()+"\n");
    		
    	}catch (NumberFormatException e){
    		txtResult.appendText("Errore: inserire matricola con sole cifre.\n");
    	}
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtIdCreator != null : "fx:id=\"txtIdCreator\" was not injected: check your FXML file 'peers.fxml'.";
        assert x1 != null : "fx:id=\"x1\" was not injected: check your FXML file 'peers.fxml'.";
        assert btnCercaArticoli != null : "fx:id=\"btnCercaArticoli\" was not injected: check your FXML file 'peers.fxml'.";
        assert txtIdArticolo != null : "fx:id=\"txtIdArticolo\" was not injected: check your FXML file 'peers.fxml'.";
        assert btnCercaArticolo != null : "fx:id=\"btnCercaArticolo\" was not injected: check your FXML file 'peers.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'peers.fxml'.";

    }

	public void setModel(Model model2) {
		this.model= model2;
		
	}
}
