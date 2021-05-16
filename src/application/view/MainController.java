package application.view;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Vector;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.util.Callback;
import element.*;
import element.Process;
import others.*;

public class MainController extends Pane implements Initializable  {
	private ObservableList<Resource> dataResource; 
	private ObservableList<Map<String, String>> dataQuery;  
	private static Coordinator coordinator;
	@FXML
	private TableView<String[]> MAX;
	@FXML
	private TableView<String[]> ALLOCATE;
	@FXML
	private TableView<String[]> NEED;
    @FXML
    private Label nOfRLabel, nOfPLabel, queryStatusLabel, processRequestLabel;

    @FXML
    private Label nOfR, nOfP, queryStatus , processRequest;

    @FXML
    private TableView<Resource> resourceTable;

    @FXML
    private TableColumn<Resource, String> nameResourceCol;

    @FXML
    private TableColumn<Resource, Integer> availableResourceCol;

    @FXML
    private TableView queryTable;

    @FXML
    private TableColumn<Map, String> nameQueryCol;

    @FXML
    private TableColumn<Map, String> requestQueryCol;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		coordinator = new Coordinator ();
		initTable(MAX);
		setTable(MAX);
		initTable(ALLOCATE);
		setTable(ALLOCATE);
		initTable(NEED);
		setTable(NEED);
		
		// init number of process and resource
		nOfP.setText(Integer.toString(coordinator.getNProcess()));
		nOfR.setText(Integer.toString(coordinator.getNResource()));
		
		// init resource tableview
		dataResource = FXCollections.observableArrayList();
		nameResourceCol.setCellValueFactory(new PropertyValueFactory<Resource, String>("name"));
		availableResourceCol.setCellValueFactory(new PropertyValueFactory<Resource, Integer>("available"));
		resourceTable.setItems(dataResource);
		
		// add info of resouces in resource table
		 resourceTable(coordinator.getResource()); 
		
		// init query tableview
		dataQuery = FXCollections.<Map<String, String>>observableArrayList();
		nameQueryCol.setCellValueFactory(new MapValueFactory<>("Name"));
		requestQueryCol.setCellValueFactory(new MapValueFactory<>("Request"));
		queryTable.setItems(dataQuery);
		// add info of query in query table
		queryTable(coordinator.getNProcess(), coordinator);
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
	          Tc[i].setPrefWidth(60);
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
	
	public void queryTable(int n, Coordinator c) {
		Query q = new Query(n, c);
		processRequest.setText(Integer.toString(q.getPos()));
		Map<String, Integer> queryMap = q.getQueryMap();
		for(String name : queryMap.keySet()) {
			Map<String, String> item = new HashMap<>();
			item.put("Name", name);
			item.put("Request", queryMap.get(name).toString());
			dataQuery.add(item);
			
		}	
		//q.printQuery();
	}
}
