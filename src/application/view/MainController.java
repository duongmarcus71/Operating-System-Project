package application.view;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Vector;
import application.Main;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.util.Callback;
import element.*;
import element.Process;
import others.*;

public class MainController extends Pane implements Initializable  {
	private ObservableList<Resource> dataResource;
	
	private ObservableList<State> dataState;
	
	private int row, stateTurn, curProcess;
	
	private ObservableList<Map<String, String>> dataQuery; 
	
	private static Coordinator coordinator;
	
	private Query q;
	
	private TableViewSelectionModel a;
	
	private boolean turn, fTurn;
	
	private Vector<Integer> backup;
	
	@FXML
	private TableView<String[]> max;
	
	@FXML
	private TableView<String[]> allocate;
	
	@FXML
	private TableView<String[]> need;
	
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
    private Button viewDetailButton, nextStepButton, home, exit;
    
    @FXML
    private Label processStateLabel;
    
    @FXML
    private TableView<State> stateTable;
    
    @FXML
    private TableColumn<State, String> nameProcessCol, stateProcessCol;
    
    @FXML
    private Button backButton, stateNSButton;
    
    @FXML
    private TextArea resultArea;
    
    Callback factory = new Callback<TableColumn<String[], String>, TableCell<String[], String>>(){
	    public TableCell<String[], String> call(TableColumn<String[], String> param) {
	        return new TableCell<String[], String>() {
	            @Override
	            public void updateIndex(int i) {
	                super.updateIndex(i);
	            }
	            
	            @Override
	            protected void updateItem(String item, boolean empty) {
	                super.updateItem(item, empty);
	                // assign item's toString value as text
	                if (empty || item == null) {
	                    setText(null);
	                } else {
	                    setText(item.toString());
	                    
	                    if(this.getIndex() == row) {
	                    	this.setStyle("-fx-background-color: #155cd4; -fx-text-fill:white");
	                    }
	                    else {
	                    	this.setStyle("-fx-background-color: white; -fx-text-fill:black");
	                    }
	                }
	            }
	        };
	    }
	};
    
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		// init process tableview
		coordinator = new Coordinator ();
		initTable(max);
		initTable(allocate);
		initTable(need);
		
		// init number of process and resource
		nOfP.setText(Integer.toString(coordinator.getNProcess()));
		nOfR.setText(Integer.toString(coordinator.getNResource()));
		
		// init resource tableview
		dataResource = FXCollections.observableArrayList();
		nameResourceCol.setCellValueFactory(new PropertyValueFactory<Resource, String>("name"));
		availableResourceCol.setCellValueFactory(new PropertyValueFactory<Resource, Integer>("available"));
		resourceTable.setItems(dataResource);
		
		// init state tableview
		dataState = FXCollections.<State>observableArrayList();
		nameProcessCol.setCellValueFactory(new PropertyValueFactory<State, String>("name"));
		stateProcessCol.setCellValueFactory(new PropertyValueFactory<State, String>("status"));
		nameProcessCol.setCellFactory(factory);
		stateProcessCol.setCellFactory(factory);
		stateTable.setItems(dataState);
		
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
		processRequest.setVisible(false);
		row = -1;
	}
	
	public void initUI() {
		systemStatus.setVisible(false);
		viewDetailButton.setVisible(false);
		resultQuery.setVisible(false);
		showTable();
	}
	
	public void initStateTable() {
		dataState.clear();
		for(int i = 0; i < coordinator.getNProcess(); ++ i) {
			dataState.add(new State("Process " + i, "F"));
		}
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
	          Tc[i].setPrefWidth((table.getPrefWidth() - 60)/ (coordinator.getNResource() ) );
	    	  }
	    	  
	    	  Tc[i].setCellValueFactory(new Callback<CellDataFeatures<String[], String>, ObservableValue<String>>() {
		           @Override
		           public ObservableValue<String> call(CellDataFeatures<String[], String> p) {
		        	   return new SimpleStringProperty((p.getValue()[colNo]));
		           }
		      });
	    	  Tc[i].setStyle( "-fx-alignment: CENTER;");
	    	  Tc[i].setCellFactory(factory);
	      }
<<<<<<< HEAD
=======
	      table.getColumns().clear();
>>>>>>> 5680ba43d53f014f167bc1c765cf19bbc7f5a6ab
	      table.getColumns().addAll(Tc);
	}
	
	@FunctionalInterface
	interface get {
	    public Vector<Integer> Feature(Process p);
	}
	
	public void setTable(TableView table) {
		String [][] data = new String[coordinator.getNProcess()][coordinator.getNResource()+1];
		int index = 0;
		get Get = (p) -> {
			Vector<Integer> v = new Vector<Integer>();
			if(table == max) v = p.getMax();
			if(table == allocate) v = p.getAllocation();
			if(table == need) v = p.getNeed();
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
		processRequest.setVisible(true);
		processRequest.setText("P" + Integer.toString(q.getPos()));
		
		int tmp = coordinator.getNResource();
		for(int i = 0; i < tmp; ++ i) {
			Map<String, String> item = new HashMap<>();
			item.put("Name", coordinator.getResource().get(i).getName());
			item.put("Request", q.getRequest().get(i).toString());
			dataQuery.add(item);
		}	
	}
	
	public void showTable() {
		setTable(max);
		setTable(allocate);
		setTable(need);
		resourceTable(coordinator.getResource());
	}
	
	public void reloadTable() {
		stateTable.refresh();
		max.refresh();
		allocate.refresh();
		need.refresh();
		resourceTable.refresh();
	}
	
	@FXML
	public void showViewDetail(ActionEvent e) {
		systemStatus.setVisible(false);
		viewDetailButton.setVisible(false);
		resultQuery.setVisible(false);
		nextStepButton.setVisible(false);
		queryStatusLabel.setVisible(false);
		processRequestLabel.setVisible(false);
		processRequest.setVisible(false);
		queryTable.setVisible(false);
		
		processStateLabel.setVisible(true);
		stateTable.setVisible(true);
		backButton.setVisible(true);
		stateNSButton.setVisible(true);
		resultArea.setVisible(true);
		
		stateTurn = 0;
		curProcess = 0;
		initStateTable();
		backup = new Vector<Integer>();
		resultArea.clear();
		row = 0;
		reloadTable();
	}
	
	@FXML
	public void back(ActionEvent e) {
		systemStatus.setVisible(true);
		viewDetailButton.setVisible(true);
		resultQuery.setVisible(true);
		nextStepButton.setVisible(true);
		queryStatusLabel.setVisible(true);
		processRequestLabel.setVisible(true);
		processRequest.setVisible(true);
		queryTable.setVisible(true);
		
		processStateLabel.setVisible(false);
		stateTable.setVisible(false);
		backButton.setVisible(false);
		stateNSButton.setVisible(false);
		resultArea.setVisible(false);
		
		for(int i = 0; i < coordinator.getNResource(); ++ i) {
			int tmp = coordinator.getResource().get(i).getAvailable();
			for(int j: backup) {
				tmp -= coordinator.getProcess().get(j).getMax().get(i);
			}
			coordinator.getResource().get(i).setAvailable( tmp );
		}
		row = q.getPos();
		reloadTable();
	}
	
	public void writeResult(String ins, Vector<Integer> output) {
		resultArea.appendText(ins + " Process " + curProcess + ": ");
		for(int i = 0; i < coordinator.getNResource(); ++ i) {
			resultArea.appendText(coordinator.getResource().get(i).getName()
				+ " " + output.get(i) + " đơn vị");
			if(i == coordinator.getNResource() - 1) resultArea.appendText(".\n\n");
			else resultArea.appendText(", ");
		}
	}
	
	@FXML
	public void stateNextStep(ActionEvent e) {
		if(stateTurn < coordinator.getTrace().size()) {
			if(coordinator.getTrace().get(stateTurn) == true ) {
				dataState.get(curProcess).setStatus("T");
				writeResult("Cung cấp cho", coordinator.getProcess().get(curProcess).getNeed());
				writeResult("Thu hồi từ", coordinator.getProcess().get(curProcess).getMax());
				
				for(int i = 0; i < coordinator.getNResource(); ++ i) {
					int tmp = coordinator.getResource().get(i).getAvailable();
					tmp += coordinator.getProcess().get(curProcess).getMax().get(i);
					coordinator.getResource().get(i).setAvailable( tmp );
				}
				backup.add(curProcess);
			}
			
			row = curProcess;
			reloadTable();
			stateTurn ++;
			curProcess ++;
			if(curProcess == coordinator.getNProcess()) curProcess = 0; 
		} else stateNSButton.setVisible(false);
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
					row = q.getPos();
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
	
	public void home(ActionEvent e) {
		// just in case system is processing viewDetail
		systemStatus.setVisible(true);
		viewDetailButton.setVisible(true);
		resultQuery.setVisible(true);
		nextStepButton.setVisible(true);
		queryStatusLabel.setVisible(true);
		processRequestLabel.setVisible(true);
		processRequest.setVisible(true);
		queryTable.setVisible(true);
		processStateLabel.setVisible(false);
		stateTable.setVisible(false);
		backButton.setVisible(false);
		stateNSButton.setVisible(false);
		resultArea.setVisible(false);
		
		Main.stage.setScene(Main.sceneOpening);
		this.initialize(null, null);
	}
	
	public void exitApp(ActionEvent e) {
		Main.stage.setScene(Main.sceneExit);
	}
	
}
