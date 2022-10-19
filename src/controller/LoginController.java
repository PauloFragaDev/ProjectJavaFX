/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import dao.UsuarioDAO;
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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Usuario;

/**
 * FXML Controller class
 *
 * @author Pol
 */
public class LoginController implements Initializable {
    
    // OBJETOS / ARRAYLIST / OBSERVABLELIST / MORE --> QUE UTILIZO EN ESTA CLASE
    
    UsuarioDAO usuarioDAO;
    boolean auxiliar = true;
    ArrayList<Usuario> usuarios;
    
    // BOTONES / TXTFIELDS / TABLAS / CHOICEBOX / MORE --> DE ESTA CLASE
    
    @FXML
    private Button btnSalir;
    @FXML
    private Button btnAcceder;
    @FXML
    private Button btnRegistrarse;
    @FXML
    private TextField txtNombreUsuario;
    @FXML
    private PasswordField txtContraUsuario;
    
    // CONSTRUCTORES DE LA CLASE
    
    public LoginController() {
        this.usuarioDAO = new UsuarioDAO();
        this.usuarios = new ArrayList(usuarioDAO.selectAll());
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    // FUNCION PARA SALIR DE ESTA VIEW
    
    @FXML
    private void salirAplicacion(ActionEvent event) {
        //METODO PARA CERRAR ESTA VENTANA
        Stage stage = (Stage) this.btnSalir.getScene().getWindow();
        stage.close();
    }
    
    // FUNCION PARA LOGEARSE CON UN USUARIO
    
    @FXML
    private void accederMenu(ActionEvent event) throws IOException {
        if (event.getSource() == btnAcceder) {
            
    // RECOJO LOS VALORES DE LOS CAMPOS Y LOS PONGO EN VARIABLES        
            
            String nombre = txtNombreUsuario.getText();
            String contraseña = txtContraUsuario.getText();
            boolean comprobar = true;
            
    // RECORRO UN FOR DE USUARIOS PARA COMPARAR EL NOMBRE Y CONTRASEÑA DE CADA UNO PARA SABER SI ES ADMIN O CLIENTE
    // (LO COMPRUEBO CON UN BOOLEAN QUE CREO ANTES DONDE ESTA MARCADO COMO TRUE, EN CASO QUE TODO COINCIDA NOS METERA DENTRO DE UNO DE LOS IF)
            
            for (Usuario us : usuarios) {
                if (nombre.equals(us.getNombreUsuario()) && contraseña.equals(us.getContraseña()) && auxiliar != us.isAdministrador()) {
                    comprobar = false;
                   
    // CARGO LA NUEVA VIEW , QUE EN ESTE CASO ES LA VISTA DEL CLIENTE                
                    
                    System.out.println("Login Correcto");
                    Stage loginUsuario = new Stage();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/menuCliente.fxml"));
                    loader.setControllerFactory(t -> new MenuClienteController(us));
                    Parent root = loader.load();
                    Scene scene = new Scene(root);
                    scene.setFill(Color.TRANSPARENT);
                    loginUsuario.initStyle(StageStyle.TRANSPARENT);
                    loginUsuario.setScene(scene);
                    loginUsuario.show();
                    Stage stage = (Stage) this.btnAcceder.getScene().getWindow();
                    stage.close();
                }            
                if (nombre.equals(us.getNombreUsuario()) && contraseña.equals(us.getContraseña()) && auxiliar == us.isAdministrador()) {
                    comprobar = false;
                    
    // CARGO LA NUEVA VISTA QUE EN ESTE CASO SERIA EL MENU DE ADMINISTRADORES                
                    
                    System.out.println("Login Correcto");
                    Stage loginUsuario = new Stage();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/menuAdmin.fxml"));
                    loader.setControllerFactory(t -> new MenuAdminController(us));
                    Parent root = loader.load();
                    Scene scene = new Scene(root);
                    scene.setFill(Color.TRANSPARENT);
                    loginUsuario.initStyle(StageStyle.TRANSPARENT);
                    loginUsuario.setScene(scene);
                    loginUsuario.show();
                    Stage stage = (Stage) this.btnAcceder.getScene().getWindow();
                    stage.close();
                }
            }
            
    // EN CASO QUE NO SE HAYA ENCONTRADO EL USUARIO ESPECIFICADO NOS SALTARA UN ALERT INFORMANDO QUE
    // EL USUARIO INTRODUCIDO ES INCORRECTO
            
            if (comprobar) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("Error");
                alert.setContentText("Usuario / Contraseña incorrecta");
                alert.showAndWait();
            }
        }
    }
    
    // FUNCION PARA CARGAR LA VISTA PARA PODER REGISTRARNOS CON UN USUARIO NUEVO
    
    @FXML
    private void registrarUsuario(ActionEvent event) throws IOException {
        Stage loginUsuario = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/view/registrarUsuario.fxml"));
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        loginUsuario.initStyle(StageStyle.TRANSPARENT);
        loginUsuario.setScene(scene);
        loginUsuario.show();
        Stage stage = (Stage) this.btnAcceder.getScene().getWindow();
        stage.close();
    }
}
