/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import dao.UsuarioDAO;
import java.io.IOException;
import java.net.URL;
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
public class RegistrarUsuarioController implements Initializable {
    
    // OBJETOS / ARRAYLIST / OBSERVABLELIST / MORE --> QUE UTILIZO EN ESTA CLASE
    
    UsuarioDAO usuarioDAO;
    
    // BOTONES / TXTFIELDS / TABLAS / CHOICEBOX / MORE --> DE ESTA CLASE
    
    @FXML
    private Button btnSalir;
    @FXML
    private Button btnRegistrar;
    @FXML
    private TextField txtNombreUsuario;
    @FXML
    private TextField txtDniUsuario;
    @FXML
    private PasswordField txtContraseñaUsuario;
    
    // CONSTRUCTORES DE LA CLASE
    
    public RegistrarUsuarioController(){
        this.usuarioDAO = new UsuarioDAO();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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
    
    // FUNCION PARA REGISTRAR UN NUEVO USUARIO (CLIENTE)
    
    @FXML
    private void registrarUsuario(ActionEvent event) throws IOException {
        
    // EXTRAIGO LOS VALORES DE LOS CAMPOS    
        
        String nombre = this.txtNombreUsuario.getText();
        String contra = this.txtContraseñaUsuario.getText();
        String dni = this.txtDniUsuario.getText();
        boolean admin = false;
    
    // COMPRUEBO QUE LOS CAMPOS NO ESTEN VACIOS    
        
        if (!nombre.isEmpty() && !contra.isEmpty() && !dni.isEmpty()) {
            
    // CREO UN OBJETO DE LA CLASE USUARIO (DE VALOR CLIENTE)        
    
            Usuario usuario = new Usuario(nombre, contra, dni, admin);
            
    // EJECUTO LA FUNCION QUE HACE LA SENTENCIA PARA AÑADIR AL USUARIO EN LA
    // BASE DE DATOS
            
            usuarioDAO.insert(usuario);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Info");
            alert.setContentText("Usuario creado correctamente");
            alert.showAndWait();
            
    // CIERRO ESTA VIEW
            
            Stage loginUsuario = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/view/login.fxml"));
            Scene scene = new Scene(root);
            scene.setFill(Color.TRANSPARENT);
            loginUsuario.initStyle(StageStyle.TRANSPARENT);
            loginUsuario.setScene(scene);
            loginUsuario.show();
            Stage stage = (Stage) this.btnSalir.getScene().getWindow();
            stage.close();
        } else {
            
    // EN CASO QUE LOS CAMPOS ESTEN VACIOS SALTARA ESTE ALERT     
            
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Campos incorrectos");
            alert.showAndWait();
        }
    }

}
