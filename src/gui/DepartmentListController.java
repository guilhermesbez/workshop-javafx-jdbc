package gui;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.entities.Department;
import model.services.DepartmentService;

public class DepartmentListController implements Initializable{
	
	//Declarar uma dependencia da Classe Department Serviço
	private DepartmentService service;
	
	@FXML
	private TableView<Department> tableViewDepartment;
	
	@FXML
	private TableColumn<Department, Integer> tableColumnId;
	
	@FXML
	private TableColumn<Department, String> tableColumnName;
	
	@FXML
	private Button btNew;
	
	//Trazer a lista de departmentos
	private ObservableList<Department> obsList;
	
	@FXML
	public void onBtNewAction() {
		System.out.println("OnBtAction");
	}
	
	//Injetar a dependencia do Service Controler, é necessario para
	public void setDepartmentService(DepartmentService service) {
		this.service = service;
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {		
		initializeNodes();
	}
	
	//Metodo para iniciar o comportamento das colunas da tabela
	private void initializeNodes() {
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
		
		//Ampliar o tamanho da tela do Department List
		Stage stage = (Stage)  Main.getMainScene().getWindow();
		tableViewDepartment.prefHeightProperty().bind(stage .heightProperty());	
	}
	
	//responsavel por acessar os serviços carregar os departmenos e jogar esses departamentos na ObservalList
	public void updateTableView() {
		//Verifica se tem serviço
		if (service == null) {
			throw new IllegalStateException("Service was null");
		}
		//Recupera os Departamentos dos serviços
		List<Department> list = service.findAll();
		//Carrega a lista dentro do ObservalList
		obsList = FXCollections.observableArrayList(list);
		//carrega os itens na TableView e mostrar na tela
		tableViewDepartment.setItems(obsList);
		
	}
}
