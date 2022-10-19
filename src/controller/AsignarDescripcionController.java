/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import dao.LogDAO;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Log;
import model.Usuario;

/**
 * FXML Controller class
 *
 * @author polfr
 */
public class AsignarDescripcionController implements Initializable {
    
    // OBJETOS / ARRAYLIST / OBSERVABLELIST / MORE --> QUE UTILIZO EN ESTA CLASE
    
    String accion;
    Usuario usuarioLog;
    
    LogDAO logDAO;
    
    ArrayList<Log> logs;
    
    // BOTONES / TXTFIELDS / TABLAS / CHOICEBOX / MORE --> DE ESTA CLASE
    
    @FXML
    private Button btnSalir;
    @FXML
    private TextArea tADescripcion;
    @FXML
    private Button btnAsignar;
    
    // CONSTRUCTORES DE LA CLASE
    
    public AsignarDescripcionController(){}
    
    public AsignarDescripcionController(String accionIn, Usuario usuarioIn){
        this.accion = accionIn;
        this.usuarioLog = usuarioIn;
        this.logDAO = new LogDAO();
        this.logs = new ArrayList(logDAO.selectAll());
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    // FUNCION PARA CREAR UN LOG
    
    @FXML
    private void crearLog(ActionEvent event) {
        
        // EN CASO QUE NO SE PONGA UNA DESCRIPCION APARECERA ESTE ALERT
        
        if(this.tADescripcion.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Tienes que poner una descripcion");
            alert.showAndWait();
        } 
        
        // EN CASO QUE SE PONGA UNA DESCRIPCION SE CREARA EL LOG Y SE INSERTARA EN LA BASE DE DATOS
        
        else{
        int idUsuario = extraerIdUsuario();
        String accionUsuario = accion;
        String descripcion = this.tADescripcion.getText();
        
        Log log = new Log(idUsuario,accionUsuario,descripcion);
        
        logDAO.insert(log);
        
        Stage stage = (Stage) this.btnAsignar.getScene().getWindow();
        stage.close();
        }
    }
    
    // FUNCION PARA SALIR DE LA VIEW
    
    @FXML
    private void salirAplicacion(ActionEvent event) throws IOException {
        Stage stage = (Stage) this.btnSalir.getScene().getWindow();
        stage.close();
    }
    
    // FUNCION PARA EXTRAER EL ID DEL USUARIO
    
    private int extraerIdUsuario(){
        int idUsuario = usuarioLog.getId();
        return idUsuario;
    }
    
}
