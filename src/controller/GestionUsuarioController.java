/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import dao.TiquetDAO;
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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Tiquet;
import model.Usuario;

/**
 * FXML Controller class
 *
 * @author polfr
 */
public class GestionUsuarioController implements Initializable {
    
    // OBJETOS / ARRAYLIST / OBSERVABLELIST / MORE --> QUE UTILIZO EN ESTA CLASE
    
    Usuario usuarioLogin;
    String accion;
    private UsuarioDAO usuarioDAO;
    private TiquetDAO tiquetDAO;

    private ArrayList<Usuario> usuariosArList;
    private ArrayList<Tiquet> tiquetsArList;
    private ObservableList<Usuario> usuariosObList;
    private ObservableList<String> tipoUsuario = FXCollections.observableArrayList("Cliente", "Admin");
    
    // BOTONES / TXTFIELDS / TABLAS / CHOICEBOX / MORE --> DE ESTA CLASE
    
    @FXML
    private Button btnSalir;
    @FXML
    private Button btnCrearUsuario;
    @FXML
    private Button btnModificarUsuario;
    @FXML
    private Button btnEliminarUsuario;
    @FXML
    private TableView<Usuario> tblUsuarios;
    @FXML
    private TableColumn<Usuario, Integer> colIdUsuario;
    @FXML
    private TableColumn<Usuario, String> colNombreUsuario;
    @FXML
    private TableColumn<Usuario, String> colContraUsuario;
    @FXML
    private TableColumn<Usuario, String> colDniUsuario;
    @FXML
    private TableColumn<Usuario, Boolean> colAdmin;
    @FXML
    private TextField txtContraUsuario;
    @FXML
    private TextField txtDniUsuario;
    @FXML
    private TextField txtNombreUsuario;
    @FXML
    private ChoiceBox<String> cbTipoUsuario;
    
    // CONSTRUCTORES DE LA CLASE
    
    public GestionUsuarioController() {
        this.usuarioDAO = new UsuarioDAO();
        this.usuariosArList = new ArrayList(usuarioDAO.selectAll());
    }

    public GestionUsuarioController(Usuario usuarioLoginEnviado) {
        this.usuarioLogin = usuarioLoginEnviado;
        this.usuarioDAO = new UsuarioDAO();
        this.tiquetDAO = new TiquetDAO();
        this.usuariosArList = new ArrayList(usuarioDAO.selectAll());
        this.tiquetsArList = new ArrayList(tiquetDAO.selectAll());
    }
    
    // INICIALIZO LOS SIGUIENTES VALORES PARA CARGAR LOS ATRIBUTOS - OBJETOS DE LA TABLA Y LOS CHOICE BOX
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cbTipoUsuario.setItems(tipoUsuario);
        //--------------------------------------------------------------------//
        usuariosObList = FXCollections.observableArrayList(usuariosArList);
        //--------------------------------------------------------------------//
        tblUsuarios.setItems(usuariosObList);
        //--------------------------------------------------------------------//
        tblUsuarios.refresh();
        //--------------------------------------------------------------------//
        this.colIdUsuario.setCellValueFactory(new PropertyValueFactory("id"));
        this.colNombreUsuario.setCellValueFactory(new PropertyValueFactory("nombreUsuario"));
        this.colContraUsuario.setCellValueFactory(new PropertyValueFactory("contraseña"));
        this.colDniUsuario.setCellValueFactory(new PropertyValueFactory("dni"));
        this.colAdmin.setCellValueFactory(new PropertyValueFactory("administrador"));
        //--------------------------------------------------------------------//
    }
    
    // FUNCION PARA CERRAR ESTA VIEW
    
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
    
    // FUNCION PARA CREAR UN NUEVO USUARIO
    
    @FXML
    private void crearUsuario(ActionEvent event) throws IOException {
        if (event.getSource() == btnCrearUsuario) {
            
    // RECOJO LOS VALORES DE LOS CAMPOS Y LOS METO DENTRO DE VARIABLES QUE LO QUE HARAN ES CREAR
    // UN OBJETO DE LA CLASE USUARIO
            
            String nombreUsuario = this.txtNombreUsuario.getText();
            String dni = this.txtDniUsuario.getText();
            String contra = this.txtContraUsuario.getText();
            boolean tipo = getTipoUsuario();
            
    // COMPRUEBO QUE LOS CAMPOS NO ESTEN VACIOS        
            
            if (!nombreUsuario.isEmpty() && !dni.isEmpty() && !contra.isEmpty()) {
                
    // CREO EL OBJETO DE LA CLASE USUARIO            
                
                Usuario usuario = new Usuario(nombreUsuario, contra, dni, tipo);
                
    // EJECUTO LA FUNCION DONDE HARA LA SENTENCIA PARA INSERTAR EL USUARIO EN LA BASE DE DATOS            
                
                usuarioDAO.insert(usuario);
                updateControls();
                limpiarCampos();
                //LANZO UNA VENTANA EMERGENTE PARA INFORMAR QUE SE HA CREADO EL USUARIO
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText(null);
                alert.setTitle("Informacion");
                alert.setContentText("Usuario Creado");
                alert.showAndWait();
                
    // LANZO UNA VENTANA EMERGENTE DONDE EL USUARIO TENDRA QUE PONER UNA DESCRIPCION DE LO QUE ACABA DE REALIZAR            
                
                accion = "CrearUsuario";
                Stage loginUsuario = new Stage();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/asignarDescripcion.fxml"));
                loader.setControllerFactory(t -> new AsignarDescripcionController(accion, usuarioLogin));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                scene.setFill(Color.TRANSPARENT);
                loginUsuario.initStyle(StageStyle.TRANSPARENT);
                loginUsuario.setScene(scene);
                loginUsuario.show();
                Stage stage = (Stage) this.btnCrearUsuario.getScene().getWindow();
            } 
            
    // EN CASO QUE LOS VALORES - CAMPOS SEAN INCORRECTOS SALTARA EL SIGUIENTE ALERT        
            
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Campos Incorrectos");
                alert.showAndWait();
            }

        }
    }
    
    // FUNCION PARA MODIFICAR LOS DATOS DE UN USUARIO

    @FXML
    private void modificarUsuario(ActionEvent event) {
        //SELECCIONO UN ITEM DE LA TABLA
        Usuario u = this.tblUsuarios.getSelectionModel().getSelectedItem();
        //VERIFICO QUE SE HAYA ESCOGIDO UN ITEM
        if (u == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Tienes que seleccionar un Usuario");
            alert.showAndWait();
        } else {
            //HAGO UN TRY 
            try {
                // RECOJO LOS NUEVOS VALORES QUE SE HAYAN ESCOGIDO
                String nombreUsuario = this.txtNombreUsuario.getText();
                String dni = this.txtDniUsuario.getText();
                String contra = this.txtContraUsuario.getText();
                boolean tipo = getTipoUsuario();
                Usuario usuario = new Usuario(nombreUsuario, contra, dni, tipo);
                if (!nombreUsuario.isEmpty() && !dni.isEmpty() && !contra.isEmpty()) {
                    //EN EL CASO QUE SE ENTRE SETEARA LOS NUEVOS VALORES
                    //AL OBEJETO SELECCIONADO
                    u.setNombreUsuario(usuario.getNombreUsuario());
                    u.setDni(usuario.getDni());
                    u.setContraseña(usuario.getContraseña());
                    u.setAdministrador(usuario.isAdministrador());
                    usuarioDAO.update(u);
                    tblUsuarios.refresh();
                    limpiarCampos();
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setHeaderText(null);
                    alert.setTitle("Informacion");
                    alert.setContentText("Usuario Modificado");
                    alert.showAndWait();
                    accion = "ModificarUsuario";
                    Stage loginUsuario = new Stage();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/asignarDescripcion.fxml"));
                    loader.setControllerFactory(t -> new AsignarDescripcionController(accion, usuarioLogin));
                    Parent root = loader.load();
                    Scene scene = new Scene(root);
                    scene.setFill(Color.TRANSPARENT);
                    loginUsuario.initStyle(StageStyle.TRANSPARENT);
                    loginUsuario.setScene(scene);
                    loginUsuario.show();
                    Stage stage = (Stage) this.btnCrearUsuario.getScene().getWindow();
                }
            } catch (Exception e) {

            }
        }
    }

    @FXML
    private void eliminarUsuario(ActionEvent event) throws IOException {
        
    // SELECCIONO UN OBJETO DE LA TABLA
    
        Usuario usuario = this.tblUsuarios.getSelectionModel().getSelectedItem(); 
        
    // VERIFICO QUE SE HAYA SELECCIONADO ALGO, EN CASO CONTRARIO QUE NO SE HAYA SELECCIONADO NADA DE LA TABLA
    // Y QUE SE HAYA PULSADO EL BOTON DE ELIMINAR SALTARA UN ALERT INFORMANDO QUE SE TIENE QUE SELECCIONAR
    // UN VALOR DE LA TABLA
    
        if (usuario == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Tienes que seleccionar un Usuario");
            alert.showAndWait();
        } 
        
    // EN CASO QUE NO SI SE HAYA SELECCIONADO UN VALOR DE LA TABLA     
        
        else {
            
    // EJECUTO LA FUNCION DONDE SE HARA LA SENTENCIA DE ELIMINAR EL USUARIO DE LA BASE DE DATOS        
            
            usuarioDAO.deleteTiquetsUsuario(usuario);
            usuarioDAO.delete(usuario);
            limpiarCampos();
            
    //ACTUALIZO LA TABLAS
    
            updateControls();
            
    // LANZO UN ALERT INFORMANDO QUE EL USUARIO SE HA ELIMINADO        
            
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setTitle("Informacion");
            alert.setContentText("Usuario Eliminado");
            alert.showAndWait();
            
    // SURGIRA UNA VENTANA EMERGENTE DONDE EL USUARIO TENDRA QUE PONER UNA DESCRIPCION
    // PARA SABER QUE ACCION ACABA DE REALIZAR, ESTO SE GUARDARA DENTRO DE LOS LOGS
            
            accion = "EliminarUsuario";
            Stage loginUsuario = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/asignarDescripcion.fxml"));
            loader.setControllerFactory(t -> new AsignarDescripcionController(accion, usuarioLogin));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            scene.setFill(Color.TRANSPARENT);
            loginUsuario.initStyle(StageStyle.TRANSPARENT);
            loginUsuario.setScene(scene);
            loginUsuario.show();
            Stage stage = (Stage) this.btnCrearUsuario.getScene().getWindow();
        }
    }
    
    // FUNCION QUE SE EJECUTARA CUANDO EL USUARIO SELECCIONE ALGUN VALOR DE LA TABLA
    
    @FXML
    private void seleccionar(MouseEvent event) {
    
    // SELECIONO UN VALOR DE LA TABLA
        
        Usuario u = this.tblUsuarios.getSelectionModel().getSelectedItem();
    
    // SI ESTE VALOR NO ES NULL SE METERA DENTRO DEL IF    
        
        if (u != null) {
            
    // SETEAREMOS LOS SIGUIENTES CAMPOS CON LOS ATRIBUTOS DEL VALOR ESCOGIDO EN LA TABLA        
            
            this.txtNombreUsuario.setText(u.getNombreUsuario());
            this.txtContraUsuario.setText(u.getContraseña());
            this.txtDniUsuario.setText(u.getDni());
            this.cbTipoUsuario.setValue(getTipoUsuarioString(u.isAdministrador()));
        }

    }
    
    // CUANDO SE PROCEDA A ELIMINAR A UN USUARIO SE LLAMARA A ESTA FUNCUION LA CUAL
    // SE ENCARGA DE ELIMINAR LOS TIQUETS QUE TENGAN EL ID DEL USUARIO EN CUESTION
    
    private void comprobarTiquets(Usuario usuario) {
        for (Tiquet tiquet : tiquetsArList) {
            if (tiquet.getIdUsuario() == usuario.getId()) {
                tiquetDAO.delete(tiquet);
            }
        }
    }
    
    // RECOJO EL VALOR DEL CHOICEBOX PARA DETERMINAR SI SE HA MARCADO LA OPCION
    // DE CLIENTE O ADMINISTRADOR
    
    public boolean getTipoUsuario() {
        String myOnline = this.cbTipoUsuario.getValue();
        boolean opcion = true;
        if (myOnline.equals("Cliente")) {
            opcion = false;
        }
        if (myOnline.equals("Admin")) {
            opcion = true;
        }
        return opcion;
    }
    
    // LO MISMO QUE LA FUNCION ANTERIOR PERO ESTO ES PARA SABER QUE TIENE QUE
    // SALIR EN EL CHOICEBOX
    
    public String getTipoUsuarioString(boolean admin) {
        String opcion = "";
        if (admin == true) {
            opcion = "Admin";
        }
        if (admin == false) {
            opcion = "Cliente";
        }
        return opcion;
    }
    
    // UNA FUNCION PARA LIMPIAR LOS CAMPOS UNA VEZ SE HAYA REALIZADO ALGUNA ACCION
    
    private void limpiarCampos() {
        txtNombreUsuario.setText("");
        txtDniUsuario.setText("");
        txtContraUsuario.setText("");
        cbTipoUsuario.setValue(null);
    }
    
    // ACTUALIZO LA TABLE VIEW PARA QUE MUESTRE LA NUEVA ACCION QUE SE HA REALIZADO
    
    private void updateControls() {
        this.usuariosArList = new ArrayList(usuarioDAO.selectAll());
        usuariosObList = FXCollections.observableArrayList(usuariosArList);
        tblUsuarios.setItems(usuariosObList);
        tblUsuarios.refresh();
    }
}
