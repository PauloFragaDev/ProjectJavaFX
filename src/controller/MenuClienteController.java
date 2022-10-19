/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import dao.AtraccionDAO;
import dao.MecanicaDAO;
import dao.PuestoDAO;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Atraccion;
import model.Mecanica;
import model.Puesto;
import model.Usuario;

/**
 * FXML Controller class
 *
 * @author Pol
 */
public class MenuClienteController implements Initializable {
    
    // OBJETOS / ARRAYLIST / OBSERVABLELIST / MORE --> QUE UTILIZO EN ESTA CLASE
    
    Usuario usuarioLogeado;
    
    MecanicaDAO mecanicaDAO;
    PuestoDAO puestoDAO;
    AtraccionDAO atraccionDAO;
    
    ArrayList<Mecanica> mecanicasArList;
    ArrayList<Puesto> puestosArList;
    ArrayList<Atraccion> atraccionArList;
    
    ObservableList<Mecanica> mecanicasObList;
    ObservableList<Puesto> puestosObList;
    
    // BOTONES / TXTFIELDS / TABLAS / CHOICEBOX / MORE --> DE ESTA CLASE
    
    @FXML
    private TabPane tabpane;
    @FXML
    private Button btnSalir;
    @FXML
    private TableColumn<Mecanica, String> colNombeMecanica;
    @FXML
    private TableColumn<Mecanica, Integer> colPrecioMecanica;
    @FXML
    private TableColumn<Mecanica, Integer> colEdadMinMecanica;
    @FXML
    private TableColumn<Mecanica, Integer> colAlturaMinMecanica;
    @FXML
    private TableColumn<Puesto, String> colNombrePuesto;
    @FXML
    private TableColumn<Puesto, Integer> colPrecioPuesto;
    @FXML
    private TableColumn<Puesto, Integer> colEdadMinPuesto;
    @FXML
    private TableColumn<Puesto, String> colPremioPuesto;
    @FXML
    private TableColumn<Mecanica, Integer> colNumPasajeros;
    @FXML
    private Button btnMisTiquets;
    @FXML
    private Button btnGenerarTiquet;
    @FXML
    private TableView<Mecanica> tblMecanicas;
    @FXML
    private TableView<Puesto> tblPuestos;
    @FXML
    private Label txtLabel;
    
    // CONSTRUCTORES DE LA CLASE
    
    public MenuClienteController() {
    }

    public MenuClienteController(Usuario usuarioIn) {
        this.usuarioLogeado = usuarioIn;
        this.mecanicaDAO = new MecanicaDAO();
        this.puestoDAO = new PuestoDAO();
        this.atraccionDAO = new AtraccionDAO();
        
        this.mecanicasArList = new ArrayList(mecanicaDAO.selectAll());
        this.puestosArList = new ArrayList(puestoDAO.selectAll());
        this.atraccionArList = new ArrayList(atraccionDAO.selectAll());
    }

    /**
     * Initializes the controller class.
     */
    
    //INICIALIZO LOS OBJETOS CORRESPONDIENTES PARA MOSTRAR LOS VALORES EN LAS TABLAS
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txtLabel.setText(usuarioLogeado.getNombreUsuario());
        mecanicasObList = FXCollections.observableArrayList(mecanicasArList);
        puestosObList = FXCollections.observableArrayList(puestosArList);
        tblMecanicas.setItems(mecanicasObList);
        tblMecanicas.refresh();
        tblPuestos.setItems(puestosObList);
        tblPuestos.refresh();
        //-----------------------------------------------------------------//
        this.colNombeMecanica.setCellValueFactory(new PropertyValueFactory("nombreAtraccion"));
        this.colPrecioMecanica.setCellValueFactory(new PropertyValueFactory("precioAtraccion"));
        this.colEdadMinMecanica.setCellValueFactory(new PropertyValueFactory("edadMinima"));
        this.colAlturaMinMecanica.setCellValueFactory(new PropertyValueFactory("alturaMinima"));
        this.colNumPasajeros.setCellValueFactory(new PropertyValueFactory("numeroPasajeros"));
        //-----------------------------------------------------------------//
        this.colNombrePuesto.setCellValueFactory(new PropertyValueFactory("nombreAtraccion"));
        this.colPrecioPuesto.setCellValueFactory(new PropertyValueFactory("precioAtraccion"));
        this.colEdadMinPuesto.setCellValueFactory(new PropertyValueFactory("edadMinima"));
        this.colPremioPuesto.setCellValueFactory(new PropertyValueFactory("premio"));
        //-----------------------------------------------------------------//
    }
    
    // FUNCION PARA SALIR DE ESTA VIEW
    
    @FXML
    private void salirAplicacion(ActionEvent event) throws IOException {
        Stage loginUsuario = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/view/login.fxml"));
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        loginUsuario.initStyle(StageStyle.TRANSPARENT);
        loginUsuario.setScene(scene);
        loginUsuario.show();
        Stage stage = (Stage) this.btnSalir.getScene().getWindow();
        stage.close();
    }
    
    // METODO PARA CARGAR LA VIEW DONDE SE MOSTRARAN LOS TIQUETS QUE TIENE ESE USUARIO
    
    @FXML
    private void tiquetsUsuario(ActionEvent event) throws IOException {
        Stage loginUsuario = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/listaTiquetsUsuario.fxml"));
        loader.setControllerFactory(t -> new ListaTiquetsUsuarioController(usuarioLogeado));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        loginUsuario.initStyle(StageStyle.TRANSPARENT);
        loginUsuario.setScene(scene);
        loginUsuario.show();
        Stage stage = (Stage) this.btnMisTiquets.getScene().getWindow();
        stage.close();
    }
    
    // METODO DONDE ABRIRA LA NUEVA VIEW PARA GENERAR UN NUEVO TIQUET
    
    @FXML
    private void generarTiquet(ActionEvent event) throws IOException {
        
        // IDENTIFICO EL TAB EN EL QUE SE ENCUENTRA EL USUARIO
        
        int tabPanel;
        tabPanel = this.tabpane.getSelectionModel().getSelectedIndex();
        
        // TAB MECANICO
        
        if (tabPanel == 0 && event.getSource() == btnGenerarTiquet) {
            
            // RECOJO LA ATRACCION MECANICA QUE HA SELECCIONADO EL USUARIO DE LA TABLA MECANICA
            
            Mecanica mecanica = this.tblMecanicas.getSelectionModel().getSelectedItem();
            
            // LLAMO A LA FUNCION QUE ME DEVOLVERA LA ATRACCION-MECANICA EN SOLO ATRACCION
            
            Atraccion atraccion = comprobarSeleccionMecanica(mecanica);
            
            // GENERO LA VISTA DONDE SE GENERARA EL TIQUET
            
            Stage loginUsuario = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/generarTiquet.fxml"));
            loader.setControllerFactory(t -> new GenerarTiquetController(usuarioLogeado, atraccion));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            scene.setFill(Color.TRANSPARENT);
            loginUsuario.initStyle(StageStyle.TRANSPARENT);
            loginUsuario.setScene(scene);
            loginUsuario.show();
            Stage stage = (Stage) this.btnMisTiquets.getScene().getWindow();

        } 
        
        // TAB PUESTO
        
        else if (tabPanel == 1 && event.getSource() == btnGenerarTiquet) {
            
            // RECOJO LA ATRACCION-PUESTO QUE HA SELECCIONADO EL USUARIO DE LA TABLA PUESTO
            
            Puesto puesto = this.tblPuestos.getSelectionModel().getSelectedItem();
            
            // LLAMO A LA FUNCION QUE ME DEVOLVERA LA ATRACCION-PUESTO EN SOLO ATRACCION
            
            Atraccion atraccion = comprobarSeleccionPuesto(puesto);
            
            // GENERO LA VISTA DONDE SE GENERARA EL TIQUET
            
            Stage loginUsuario = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/generarTiquet.fxml"));
            loader.setControllerFactory(t -> new GenerarTiquetController(usuarioLogeado, atraccion));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            scene.setFill(Color.TRANSPARENT);
            loginUsuario.initStyle(StageStyle.TRANSPARENT);
            loginUsuario.setScene(scene);
            loginUsuario.show();
            Stage stage = (Stage) this.btnMisTiquets.getScene().getWindow();
            
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Tienes que seleccionar una Atraccion para modificarla");
            alert.showAndWait();
        }
        

    }
    
    // COMPRUEBO QUE ATRACCION-MECANICA SE HA ESCOGIDO PARA DEVOLVER ESA MISMA ATRACCION PERO CON VALORES DE UN OBJETO ATRACCION
    
    private Atraccion comprobarSeleccionMecanica(Mecanica mecanica) {
        Atraccion atraccionEnviada = null;
        
        for(Atraccion atraccion : atraccionArList){
            if(atraccion.getNombreAtraccion().equals(mecanica.getNombreAtraccion())){
                atraccionEnviada = atraccion;
            }
        }
        
        return atraccionEnviada;
    }
    
    // COMPRUEBO QUE ATRACCION-PUESTO SE HA ESCOGIDO PARA DEVOLVER ESA MISMA ATRACCION PERO CON VALORES DE UN OBJETO ATRACCION
    
    private Atraccion comprobarSeleccionPuesto(Puesto puesto) {
        Atraccion atraccionEnviada = null;
        
        for(Atraccion atraccion : atraccionArList){
            if(atraccion.getNombreAtraccion().equals(puesto.getNombreAtraccion())){
                atraccionEnviada = atraccion;
            }
        }
        
        return atraccionEnviada;
    }

}
