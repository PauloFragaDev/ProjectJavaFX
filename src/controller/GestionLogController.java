/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import dao.LogDAO;
import dao.UsuarioDAO;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Log;
import model.Usuario;

/**
 * FXML Controller class
 *
 * @author Pol
 */
public class GestionLogController implements Initializable {
    
    Usuario usuarioLogin;
    LogDAO logDAO;
    UsuarioDAO usuarioDAO;
    
    ArrayList<Usuario> usuariosArList;
    ArrayList<Log> logsArList;
    ObservableList<Log> logsObList;

    @FXML
    private Button btnSalir;
    @FXML
    private Button btnCambiar;
    @FXML
    private TextArea txtDescripcion;
    @FXML
    private TableView<Log> tblLogs;
    @FXML
    private TableColumn<Log, Integer> colIdLog;
    @FXML
    private TableColumn<Log, Integer> colIdUsuario;
    @FXML
    private TableColumn<Log, String> colAccion;
    @FXML
    private TableColumn<Log, String> colDescrip;
    @FXML
    private TextField txtNombreUsuario;
    
    public GestionLogController(Usuario usuarioIn){
        this.logDAO = new LogDAO();
        this.usuarioDAO = new UsuarioDAO();
        this.usuarioLogin = usuarioIn;
        this.logsArList = new ArrayList(logDAO.selectAll());
        this.usuariosArList = new ArrayList(usuarioDAO.selectAll());
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        logsObList = FXCollections.observableArrayList(logsArList);
        //--------------------------------------------------------------------//
        tblLogs.setItems(logsObList);
        //--------------------------------------------------------------------//
        tblLogs.refresh();
        //--------------------------------------------------------------------//
        this.colIdLog.setCellValueFactory(new PropertyValueFactory("idLog"));
        this.colIdUsuario.setCellValueFactory(new PropertyValueFactory("idUsuario"));
        this.colAccion.setCellValueFactory(new PropertyValueFactory("accion"));
        this.colDescrip.setCellValueFactory(new PropertyValueFactory("descripcion"));
    }

    @FXML
    private void salirAplicacion(ActionEvent event) throws IOException {
        Stage loginUsuario = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/menuAdmin.fxml"));
        loader.setControllerFactory(t -> new MenuAdminController(usuarioLogin));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        loginUsuario.initStyle(StageStyle.TRANSPARENT);
        loginUsuario.setScene(scene);
        loginUsuario.show();
        Stage stage = (Stage) this.btnSalir.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void cambiarDescripcion(ActionEvent event) throws IOException {
        Log log = this.tblLogs.getSelectionModel().getSelectedItem();

        if (log == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Tienes que seleccionar un Log");
            alert.showAndWait();
        } else {
            if (this.txtDescripcion.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("Error");
                alert.setContentText("Tienes que rellenar el campo de la descripcion");
                alert.showAndWait();
            } else {
                String descripcion = this.txtDescripcion.getText();
                log.setDescripcion(descripcion);
                logDAO.update(log);
                updateControls();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText(null);
                alert.setTitle("Informacion");
                alert.setContentText("La descripcion del Log se modifico");
                alert.showAndWait();
            }
        }
    }

    @FXML
    private void seleccionarDesc(MouseEvent event) {
        Log log = this.tblLogs.getSelectionModel().getSelectedItem();
        String nombreUsuario;
        if (log != null) {
            nombreUsuario = obtenerNombreUsuario(log);
            this.txtDescripcion.setText(log.getDescripcion());
            this.txtNombreUsuario.setText(nombreUsuario);
        }
        
    }
    
    private void updateControls() {
        this.logsArList = new ArrayList(logDAO.selectAll());
        logsObList = FXCollections.observableArrayList(logsArList);
        tblLogs.setItems(logsObList);
        tblLogs.refresh();
    }
    
    private String obtenerNombreUsuario(Log log){
        String nombreUsuario = "";
        
        for (Usuario usuario : usuariosArList){
            if(usuario.getId() == log.getIdUsuario()){
                nombreUsuario = usuario.getNombreUsuario();
            }
        }
        
        return nombreUsuario;
    }
    
}
