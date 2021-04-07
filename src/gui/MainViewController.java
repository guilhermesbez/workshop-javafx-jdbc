package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import application.Main;
import gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import model.services.DepartmentService;

public class MainViewController implements Initializable {
	
	//Adicionando os itens do Menu
	@FXML
	private MenuItem menuItemSeller;
	
	@FXML
	private MenuItem menuItemDepartament;
	
	@FXML
	private MenuItem menuItemAbout;
	
	//Adicionando os Eventos da tela
	@FXML
	public void onMenuItemSellerAction() {
		loadView("/gui/Seller.fxml", x -> {});//Informar qual View sera chamada
	}
	
	@FXML
	public void onMenuItemDepartmentAction() {
		loadView("/gui/DepartmentList.fxml", (DepartmentListController controller) -> {
			controller.setDepartmentService(new DepartmentService());
			controller.updateTableView();
		});//O segundo parametro é função para inicializar o controloador - Informar qual View sera chamada
	}
	
	@FXML
	public void onMenuItemAboutAction() {
		loadView("/gui/About.fxml", x -> {});//Informar qual View sera chamada
	}
	
	//Adicionado automcaticamente quando coloca Initializable no main
	@Override
	public void initialize(URL uri, ResourceBundle rb) {		
	}
	
	//Função para chamar outra tela - Syncronized garante que ela não sera interrompida
	public synchronized <T>void loadView(String absoluteName, Consumer<T> initializingAction) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			VBox newVBox = loader.load();
			
			//Show view in principal
			Scene mainScene = Main.getMainScene();
			
			//Criar uma referencia do Vbox
			VBox mainVBox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();
			
			//Exclui tudo que tiver nos filhos do VBox, e incluir os filhos
			Node mainMenu = mainVBox.getChildren().get(0);
			
			//Limpar os filhos do VBox
			mainVBox.getChildren().clear();
			
			//Depois de limpar tudo, tem que adicionar um por um
			mainVBox.getChildren().add(mainMenu);
			mainVBox.getChildren().addAll(newVBox.getChildren());
			
			//Comando para ativar a função espcial initializingAction
			T controller = loader.getController();
			initializingAction.accept(controller);
			
		}
		catch (IOException e){
			Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
		}
	}
	
	/*
	public synchronized void loadView2(String absoluteName) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			VBox newVBox = loader.load();
			
			//Show view in principal
			Scene mainScene = Main.getMainScene();
			
			//Criar uma referencia do Vbox
			VBox mainVBox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();
			
			//Exclui tudo que tiver nos filhos do VBox, e incluir os filhos
			Node mainMenu = mainVBox.getChildren().get(0);
			
			//Limpar os filhos do VBox
			mainVBox.getChildren().clear();
			
			//Depois de limpar tudo, tem que adicionar um por um
			mainVBox.getChildren().add(mainMenu);
			mainVBox.getChildren().addAll(newVBox.getChildren());
			
			//Criar uma variavel
			DepartmentListController controller = loader.getController();
			//injetar dependencia
			controller.SetDepartmentService(new DepartmentService());
			controller.updateTableView();
			
		}
		catch (IOException e){
			Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
		}
	}*/

	
}
