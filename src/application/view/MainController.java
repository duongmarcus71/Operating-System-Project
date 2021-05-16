package application.view;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Vector;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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
	
	private Query q;
	
	private boolean turn,fTurn;
	
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
    private Label systemStatusLabel, systemStatus, resultQuery;

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
    
    @FXML
    private Button viewDetailButton, nextStepButton;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		// init process tableview
		coordinator = new Coordinator ();
		initTable(MAX);
		initTable(ALLOCATE);
		initTable(NEED);
		
		// init number of process and resource
		nOfP.setText(Integer.toString(coordinator.getNProcess()));
		nOfR.setText(Integer.toString(coordinator.getNResource()));
		
		// init resource tableview
		dataResource = FXCollections.observableArrayList();
		nameResourceCol.setCellValueFactory(new PropertyValueFactory<Resource, String>("name"));
		availableResourceCol.setCellValueFactory(new PropertyValueFactory<Resource, Integer>("available"));
		resourceTable.setItems(dataResource); 
		
		// init query tableview
		dataQuery = FXCollections.<Map<String, String>>observableArrayList();
		nameQueryCol.setCellValueFactory(new MapValueFactory<>("Name"));
		requestQueryCol.setCellValueFactory(new MapValueFactory<>("Request"));
		queryTable.setItems(dataQuery);
		
		// init UI
		initUI();
		turn = false;
		fTurn = false;
		q = new Query(coordinator.getNResource());
	}
	
	public void initUI() {
		systemStatus.setVisible(false);
		viewDetailButton.setVisible(false);
		resultQuery.setVisible(false);
		showTable();
	}
	
	public void initTable(TableView table) {
		 
	      TableColumn[] Tc = new TableColumn[coordinator.getNResource()+1] ;
	  
	      for (int i = 0; i <= coordinator.getNResource(); i++) {
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
		String [][] data = new String[coordinator.getNProcess()][coordinator.getNResource()+1];
		int index =0;
		get Get = (p) -> {
			Vector<Integer> v = new Vector<Integer>();
			if(table == MAX) v = p.getMax();
			if(table == ALLOCATE) v = p.getAllocation();
			if(table == NEED) v = p.getNeed();
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
	
	public void resourceTable(Vector<Resource> r) {
		dataResource.clear();
		dataResource.addAll(r);
	}
	
	public void queryTable(int n) {
		dataQuery.clear();
		q = new Query(n, coordinator.getProcess() );
		processRequest.setText(Integer.toString(q.getPos()));
		
		int tmp = coordinator.getNResource();
		for(int i = 0; i < tmp; ++ i) {
			Map<String, String> item = new HashMap<>();
			item.put("Name", coordinator.getResource().get(i).getName());
			item.put("Request", q.getRequest().get(i).toString());
			dataQuery.add(item);
		}	
	}
	
	public void showTable() {
		setTable(MAX);
		setTable(ALLOCATE);
		setTable(NEED);
		resourceTable(coordinator.getResource());
	}
	
	@FXML
	public void showViewDetail(ActionEvent e) {
		
	}
	
	@FXML
	public void nextStep(ActionEvent e) {
		
		initUI();
		
		if(turn == true) {
			queryTable(coordinator.getNProcess());
			turn = false;
		} else {
			VectorOperator vO = new VectorOperator();
			int m = coordinator.getNResource();
			
			if(vO.cmp(m, q.getRequest(), coordinator.getProcess().get(q.getPos()).getNeed())) {
				Vector<Integer> work = new Vector<Integer>();
				for(int i = 0; i < m; ++ i) {
					work.add(coordinator.getResource().get(i).getAvailable());
				}
				
				if(vO.cmp(m, q.getRequest(), work)) {
					coordinator.changeState(q.getPos(), q.getRequest());
					showTable();
					
					if(coordinator.isSafe()) {
						resultQuery.setText("Distribute Successfully!");
						systemStatus.setText("Safe");
						viewDetailButton.setVisible(true);
					} else {
						resultQuery.setText("Block! System unsafe");
						systemStatus.setText("Unsafe");
						coordinator.changeState(q.getPos(), vO.reverse(q.getRequest()));
					}
					
					if(fTurn == false) fTurn = true;
					else resultQuery.setVisible(true);
					systemStatus.setVisible(true);
				} else {
					resultQuery.setText("Block! Not enough available resources");
					resultQuery.setVisible(true);
				}
			} else {
				resultQuery.setText("Error! Request exceeds resource declaration");
				resultQuery.setVisible(true);
			}
			
			turn = true;
		}
	}
}
