/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import dao.TiquetDAO;
import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Atraccion;
import model.Tiquet;
import model.Usuario;

/**
 * FXML Controller class
 *
 * @author Pol
 */
public class GenerarTiquetController implements Initializable {
    
    // OBJETOS / ARRAYLIST / OBSERVABLELIST / MORE --> QUE UTILIZO EN ESTA CLASE
    
    Usuario usuarioTiquet = new Usuario();
    Atraccion atraccionTiquet = new Atraccion();
    
    TiquetDAO tiquetDAO;
    
    // BOTONES / TXTFIELDS / TABLAS / CHOICEBOX / MORE --> DE ESTA CLASE
    
    @FXML
    private Button btnSalir;
    @FXML
    private DatePicker btnFecha;
    @FXML
    private Button btnGenerar;
    
    // CONSTRUCTORES DE LA CLASE
    
    public GenerarTiquetController() {
    }

    public GenerarTiquetController(Usuario usuarioIn, Atraccion atraccionIn) {
        this.usuarioTiquet = usuarioIn;
        this.atraccionTiquet = atraccionIn;
        this.tiquetDAO = new TiquetDAO();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    // FUNCION PARA SALIR DE LA VIEW
    
    @FXML
    private void salirAplicacion(ActionEvent event) throws IOException {
        Stage stage = (Stage) this.btnSalir.getScene().getWindow();
        stage.close();
    }
    
    // FUNCION QUE GENERA EL TIQUET
    
    @FXML
    private void generarTiquet(ActionEvent event) throws IOException {
        
        // EXTRAIGO LOS ID DEL USUARIO Y LA ATRACCION, SELECCIONO UNA FECHA
        // Y CREO EL TIQUET QUE SE INSERTARA EN LA BASE D EDATOS
        
        int idUsuario = usuarioTiquet.getId();
        int idAtraccion = atraccionTiquet.getIdAtraccion();
        String fechaUso = this.btnFecha.getValue().format(DateTimeFormatter.ofPattern("MM-dd-yyyy"));

        Tiquet tiquet = new Tiquet(idUsuario, idAtraccion, fechaUso);
        tiquetDAO.insert(tiquet);
            
        // SALTARA UN ALERT INFORMANDO QUE EL TIQUET SE HA CREADO
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setTitle("Informacion");
        alert.setContentText("Tiquet Creado");
        alert.showAndWait();
    }

}
