/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Usuario;

/**
 * FXML Controller class
 *
 * @author Pol
 */
public class MenuAdminController implements Initializable {
    
    // OBJETOS / ARRAYLIST / OBSERVABLELIST / MORE --> QUE UTILIZO EN ESTA CLASE
    
    Usuario usuarioLogin;
    
    // BOTONES / TXTFIELDS / TABLAS / CHOICEBOX / MORE --> DE ESTA CLASE
    
    @FXML
    private Label lblNombreUsuario;
    @FXML
    private Button btnSalir;
    @FXML
    private Button btnUsuario;
    @FXML
    private Button btnAtraccion;
    @FXML
    private Button btnTicket;
    @FXML
    private Button btnLog;
    @FXML
    private Button btnBackUp;
    
    // CONSTRUCTORES DE LA CLASE
    
    public MenuAdminController(){}
    
    public MenuAdminController(Usuario usuarioLoginEnviado){
        this.usuarioLogin = usuarioLoginEnviado;
    }
    
    // INICIALIZO LOS CAMPOS QUE SE RELLENARAN AUTOMATICAMENTE
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lblNombreUsuario.setText(usuarioLogin.getNombreUsuario());
    }
    
    // FUNCION PARA SALIR DE ESTA VIEW
    
    @FXML
    private void sairAplicacion(ActionEvent event) throws IOException {
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
    
    // FUNCION PARA DESPLAZARME A LA VIEW DONDE SE GESTIONAN LOS USUARIOS
    
    @FXML
    private void usuarioController(ActionEvent event) throws IOException {
        Stage loginUsuario = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/gestionUsuario.fxml"));
        loader.setControllerFactory(t -> new GestionUsuarioController(usuarioLogin));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        loginUsuario.initStyle(StageStyle.TRANSPARENT);
        loginUsuario.setScene(scene);
        loginUsuario.show();
        Stage stage = (Stage) this.btnSalir.getScene().getWindow();
        stage.close();
    }
    
    // FUNCION PARA DESPLAZARME A LA VIEW DONDE SE GESTIONAN LAS ATRACCIONES
    
    @FXML
    private void atraccionController(ActionEvent event) throws IOException {
        Stage loginUsuario = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/gestionAtraccion.fxml"));
        loader.setControllerFactory(t -> new GestionAtraccionController(usuarioLogin));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        loginUsuario.initStyle(StageStyle.TRANSPARENT);
        loginUsuario.setScene(scene);
        loginUsuario.show();
        Stage stage = (Stage) this.btnSalir.getScene().getWindow();
        stage.close();
    }

    // FUNCION PARA DESPLAZARME A LA VIEW DONDE SE GESTIONAN LOS TIQUETS
    
    @FXML
    private void ticketController(ActionEvent event) throws IOException {
        Stage loginUsuario = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/gestionTiquet.fxml"));
        loader.setControllerFactory(t -> new GestionTiquetController(usuarioLogin));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        loginUsuario.initStyle(StageStyle.TRANSPARENT);
        loginUsuario.setScene(scene);
        loginUsuario.show();
        Stage stage = (Stage) this.btnSalir.getScene().getWindow();
        stage.close();
    }
    
    // FUNCION PARA DESPLAZARME A LA VIEW DONDE SE GESTIONAN LOS LOGS
    
    @FXML
    private void logController(ActionEvent event) throws IOException {
        Stage loginUsuario = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/gestionLog.fxml"));
        loader.setControllerFactory(t -> new GestionLogController(usuarioLogin));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        loginUsuario.initStyle(StageStyle.TRANSPARENT);
        loginUsuario.setScene(scene);
        loginUsuario.show();
        Stage stage = (Stage) this.btnSalir.getScene().getWindow();
        stage.close();
    }
    
    // FUNCION PARA DESPLAZARME A LA VIEW DONDE SE GESTIONA EL BACKUP
    
    @FXML
    private void backUpController(ActionEvent event) throws IOException {
        Stage loginUsuario = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/gestionBackUp.fxml"));
        loader.setControllerFactory(t -> new GestionBackUpController(usuarioLogin));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        loginUsuario.initStyle(StageStyle.TRANSPARENT);
        loginUsuario.setScene(scene);
        loginUsuario.show();
        Stage stage = (Stage) this.btnSalir.getScene().getWindow();
        stage.close();
    }

}
