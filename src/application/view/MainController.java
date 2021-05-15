package application.view;

import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Vector;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.util.Callback;
import element.*;
import element.Process;
import others.*;

public class MainController extends Pane implements Initializable  {
	private ObservableList<Resource> dataResource; 
	private ObservableList<Query> dataQuery; 
	private static Coordinator coordinator;
	@FXML
	private TableView<String[]> MAX;
	@FXML
	private TableView<String[]> ALLOCATE;
	@FXML
	private TableView<String[]> NEED;
    @FXML
    private Label nOfRLabel;

    @FXML
    private Label nOfR;

    @FXML
    private TableView<Resource> resourceTable;

    @FXML
    private TableColumn<Resource, String> nameResourceCol;

    @FXML
    private TableColumn<Resource, Integer> availableResourceCol;

    @FXML
    private TableView<Query> queryTable;

    @FXML
    private TableColumn<Resource, String> nameQueryCol;

    @FXML
    private TableColumn<Query, Integer> requestQueryCol;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		// generate coordinator
		
		coordinator = new Coordinator ();
		initTable(MAX);
		setTable(MAX);
		initTable(ALLOCATE);
		setTable(ALLOCATE);
		initTable(NEED);
		setTable(NEED);
		
		// init resource tableview
		dataResource = FXCollections.observableArrayList();
		nameResourceCol.setCellValueFactory(new PropertyValueFactory<Resource, String>("name"));
		availableResourceCol.setCellValueFactory(new PropertyValueFactory<Resource, Integer>("available"));
		resourceTable.setItems(dataResource);
		
		// add info of resouces in resource table
		resourceTable(coordinator.getResource()); 
		
		// init query tableview
//		dataQuery = FXCollections.observableArrayList();
//		nameQueryCol.setCellValueFactory(new PropertyValueFactory<Resource, String>("name"));
//		requestQueryCol.setCellValueFactory(new PropertyValueFactory<Query, Integer>("request"));
//		queryTable.setItems(dataQuery);
		
		// add info of query in query table
//		queryTable(coordinator.getnResource(), coordinator.getProcess().)
	}
	public void initTable(TableView table) {
		 
	      TableColumn[] Tc = new TableColumn[coordinator.getnResource()+1] ;
	  
	      for (int i = 0; i <= coordinator.getnResource(); i++) {
	    	  final int colNo = i;
	    	  if(i==0) {
	    		  Tc[i] = new TableColumn<String[],String>("Process");
	    		  Tc[i].setPrefWidth(60);
	    		
	    	  }
	    	  else {
	    	  Tc[i] = new TableColumn<String[],String>("R" + Integer.toString(i-1));
	          Tc[i].setPrefWidth( (table.getPrefWidth() - 60 ) / coordinator.getnResource());
	          
	    	  }
	    	  Tc[i].setCellValueFactory(new Callback<CellDataFeatures<String[], String>, ObservableValue<String>>() {
		           @Override
		           		public ObservableValue<String> call(CellDataFeatures<String[], String> p) {
		        	   		return new SimpleStringProperty((p.getValue()[colNo]));
		               }
		          	});
	    	  Tc[i].setStyle( "-fx-alignment: CENTER;");
	      }
	      table.getColumns().addAll(Tc);
	}
	
	@FunctionalInterface
	interface get {
	    public Vector<Integer> Feature(Process p);
	}
	
	public void setTable(TableView table) {
		String [][] data = new String[coordinator.getnProcess()][coordinator.getnResource()+1];
		int index =0;
		get Get = (p) -> {
			Vector<Integer> v = new Vector<Integer>();
			if(table == MAX) v = p.getMax();
			if(table == ALLOCATE) v=p.getAllocation();
			if(table == NEED) v=p.getNeed();
			return v;
		};
		for (Process p : coordinator.getProcess()) {
			Vector <Integer> v = Get.Feature(p);
			data[index][0] = "Process " + Integer.toString(index);
			for(int j=0;j<v.size();j++) {
				data[index][j+1] = v.get(j).toString();
			}
			index++;
		}
		ObservableList<String[]> data1 = FXCollections.observableArrayList(data);
		table.setItems(data1);
	}
	
	public static Coordinator getCoordinator() {
		return coordinator;
	}

	
	public void resourceTable(Vector<Resource> r) {
		dataResource.addAll(r);
	}
	
	public void queryTable(int n, Vector<Integer> max) {
		Query q = new Query(n, max);
	}
}
	

