/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import dao.AtraccionDAO;
import dao.TiquetDAO;
import dao.UsuarioDAO;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Atraccion;
import model.Tiquet;
import model.TiquetAuxiliar;
import model.Usuario;

/**
 * FXML Controller class
 *
 * @author Pol
 */
public class GestionTiquetController implements Initializable {
    
    // OBJETOS / ARRAYLIST / OBSERVABLELIST / MORE --> QUE UTILIZO EN ESTA CLASE
    
    Usuario usuarioLogin;
    TiquetDAO tiquetDAO;
    UsuarioDAO usuarioDAO;
    AtraccionDAO atraccionDAO;
    String accion;

    ArrayList<TiquetAuxiliar> tiquetAuxArList;
    ArrayList<Tiquet> tiquetsArList;
    ArrayList<Usuario> usuariosArList;
    ArrayList<Atraccion> atraccionArList;

    ObservableList<TiquetAuxiliar> tiquetAuxObList;
    ObservableList<Tiquet> tiquetsObList;
    ObservableList<Usuario> usuarioObList;
    ObservableList<Atraccion> atraccionObList;
    
    // BOTONES / TXTFIELDS / TABLAS / CHOICEBOX / MORE --> DE ESTA CLASE
    
    @FXML
    private Button btnSalir;
    @FXML
    private Button btnCrearTiquet;
    @FXML
    private Button btnModificarTiquet;
    @FXML
    private Button btnEliminarTiquet;
    @FXML
    private TableView<TiquetAuxiliar> tbTiquets;
    @FXML
    private TableColumn<TiquetAuxiliar, Integer> colIdTiquet;
    @FXML
    private TableColumn<TiquetAuxiliar, String> colIdUsuario;
    @FXML
    private TableColumn<TiquetAuxiliar, String> colIdAtraccion;
    @FXML
    private TableColumn<Tiquet, String> colFecha;
    @FXML
    private ChoiceBox<Atraccion> cbAtraccion;
    @FXML
    private ChoiceBox<Usuario> cbUsuario;
    @FXML
    private DatePicker dateFecha;
    
    private TextField txtNombreUsuario;
    private TextField txtNombreAtraccion;
    
    // CONSTRUCTORES DE LA CLASE
    
    public GestionTiquetController(Usuario usuarioIn) {
        this.usuarioLogin = usuarioIn;
        this.tiquetDAO = new TiquetDAO();
        this.usuarioDAO = new UsuarioDAO();
        this.atraccionDAO = new AtraccionDAO();

        this.tiquetsArList = new ArrayList(tiquetDAO.selectAll());
        this.usuariosArList = new ArrayList(usuarioDAO.selectAll());
        this.atraccionArList = new ArrayList(atraccionDAO.selectAll());
        this.tiquetAuxArList = converTiquets();
    }

    /**
     * Initializes the controller class.
     */
    
    // INICIALIZO LOS OBSERVABLE LIST PARA QUE SE CARGUEN EN LAS TABLAS
    // Y EN LOS CHOICEBOX'S
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tiquetAuxObList = FXCollections.observableArrayList(tiquetAuxArList);
        usuarioObList = FXCollections.observableArrayList(usuariosArList);
        atraccionObList = FXCollections.observableArrayList(atraccionArList);
        //-----------------------------------------------------------------//
        tbTiquets.setItems(tiquetAuxObList);
        tbTiquets.refresh();
        //-----------------------------------------------------------------//
        cbAtraccion.setItems(atraccionObList);
        cbUsuario.setItems(usuarioObList);
        //-----------------------------------------------------------------//
        this.colIdTiquet.setCellValueFactory(new PropertyValueFactory("idTiquetAux"));
        this.colIdUsuario.setCellValueFactory(new PropertyValueFactory("nombreUsuario"));
        this.colIdAtraccion.setCellValueFactory(new PropertyValueFactory("nombreAtraccion"));
        this.colFecha.setCellValueFactory(new PropertyValueFactory("fechaUso"));
    }
    
    // FUNCION PARA SALIR
    
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

    // FUNCION PARA CREAR TIQUETS 
    
    @FXML
    private void crearTiquet(ActionEvent event) throws IOException {
        int idUsuario = extraerIdUsuario();
        int idAtraccion = extraerIdAtraccion();
        String fechaUso = this.dateFecha.getValue().format(DateTimeFormatter.ofPattern("MM-dd-yyyy"));

        Tiquet tiquet = new Tiquet(idUsuario, idAtraccion, fechaUso);
        tiquetDAO.insert(tiquet);

        updateControls();
        limpiarCampos();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setTitle("Informacion");
        alert.setContentText("Tiquet creado correctamente");
        alert.showAndWait();

        accion = "CrearTiquet";
        Stage loginUsuario = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/asignarDescripcion.fxml"));
        loader.setControllerFactory(t -> new AsignarDescripcionController(accion, usuarioLogin));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        loginUsuario.initStyle(StageStyle.TRANSPARENT);
        loginUsuario.setScene(scene);
        loginUsuario.show();
        Stage stage = (Stage) this.btnCrearTiquet.getScene().getWindow();

    }

    @FXML
    private void modificarTiquet(ActionEvent event) {
        //SELECCIONO UN ITEM DE LA TABLA
        TiquetAuxiliar tiquetAux = this.tbTiquets.getSelectionModel().getSelectedItem();
        
        Tiquet ti = converTAuxToTiquet(tiquetAux);
        
        //VERIFICO QUE SE HAYA ESCOGIDO UN ITEM
        if (ti == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Tienes que seleccionar un Tiquet");
            alert.showAndWait();
        } else {
        //HAGO UN TRY 
            try {
                
        // RECOJO LOS NUEVOS VALORES QUE SE HAYAN ESCOGIDO
                int idUsuario = extraerIdUsuario();
                int idAtraccion = extraerIdAtraccion();
                String fechaUso = this.dateFecha.getValue().format(DateTimeFormatter.ofPattern("MM-dd-yyyy"));
                Tiquet tiquet = new Tiquet(idUsuario, idAtraccion, fechaUso);
        //EN EL CASO QUE SE ENTRE SETEARA LOS NUEVOS VALORES
        //AL OBEJETO SELECCIONADO
                ti.setIdUsuario(tiquet.getIdUsuario());
                ti.setIdAtraccion(tiquet.getIdAtraccion());
                ti.setFechaUso(tiquet.getFechaUso());
                tiquetDAO.update(ti);
                updateControls();
                tbTiquets.refresh();
                limpiarCampos();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText(null);
                alert.setTitle("Informacion");
                alert.setContentText("Tiquet Modificado");
                alert.showAndWait();

        // SALTA UNA VENTANA EMERGENTE DONDE EL USUARIO TIENE QUE PONER UNA DESCRIPCION DE LO REALIZADO        
                
                accion = "ModificarTiquet";
                Stage loginUsuario = new Stage();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/asignarDescripcion.fxml"));
                loader.setControllerFactory(t -> new AsignarDescripcionController(accion, usuarioLogin));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                scene.setFill(Color.TRANSPARENT);
                loginUsuario.initStyle(StageStyle.TRANSPARENT);
                loginUsuario.setScene(scene);
                loginUsuario.show();
                Stage stage = (Stage) this.btnModificarTiquet.getScene().getWindow();

            } catch (Exception e) {

            }
        }
    }

    @FXML
    private void eliminarTiquet(ActionEvent event) throws IOException {
        //SELECCIONO UN OBJETO DE LA TABLA
        TiquetAuxiliar tiquetAux = this.tbTiquets.getSelectionModel().getSelectedItem();
        
        Tiquet tiquet = converTAuxToTiquet(tiquetAux);
        //VERIFICO QUE SE HAYA SELECCIONADO ALGO, EN CASO CONTRARIO
        //Y QUE SE DE AL BOTON DE ELIMINAR APARECERA UNA VENTANA EMERGENTE
        //DONDE SE INFORMARA QUE TIENE QUE ESCOGER UN ITEM DE LA TABLA
        if (tiquet == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Tienes que seleccionar un Tiquet");
            alert.showAndWait();
        } else {
            tiquetDAO.delete(tiquet);
//            //ACTUALIZO LA TABLAS
            updateControls();
            limpiarCampos();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setTitle("Informacion");
            alert.setContentText("Tiquet Eliminado");
            alert.showAndWait();

        // SALTA UNA VENTANA EMERGENTE DONDE EL USUARIO TIENE QUE PONER UNA DESCRIPCION DE LO REALIZADO    
            
            accion = "EliminarTiquet";
            Stage loginUsuario = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/asignarDescripcion.fxml"));
            loader.setControllerFactory(t -> new AsignarDescripcionController(accion, usuarioLogin));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            scene.setFill(Color.TRANSPARENT);
            loginUsuario.initStyle(StageStyle.TRANSPARENT);
            loginUsuario.setScene(scene);
            loginUsuario.show();
            Stage stage = (Stage) this.btnEliminarTiquet.getScene().getWindow();

        }
    }
    
    // FUNCION QUE SE EJECUTARA CUANDO EL USUARIO SELECCIONE ALGUN VALOR DE LA TABLA
    
    @FXML
    private void seleccionar(MouseEvent event) {
        
    // SELECIONO UN VALOR DE LA TABLA
    
        TiquetAuxiliar tiquetAux = this.tbTiquets.getSelectionModel().getSelectedItem();
        
        Tiquet t = converTAuxToTiquet(tiquetAux);
            
    // SI ESTE VALOR NO ES NULL SE METERA DENTRO DEL IF      
        
        if (t != null) {
            
    // SETEAREMOS LOS SIGUIENTES CAMPOS CON LOS ATRIBUTOS DEL VALOR ESCOGIDO EN LA TABLA        
            
            this.cbUsuario.setValue(extraerUsuario(t.getIdUsuario()));
            this.cbAtraccion.setValue(extraerAtraccion(t.getIdAtraccion()));
            String fecha = t.getFechaUso();
            DateTimeFormatter customDateTimeFormatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
            LocalDate localDate = LocalDate.parse(fecha, customDateTimeFormatter);
            this.dateFecha.setValue(localDate);
            this.txtNombreUsuario.setText(extraerNombreUsuario(t));
            this.txtNombreAtraccion.setText(extraerNombreAtraccion(t));

        }
    }
    
    // EXTRAIGO EL ID DEL USUARIO ESCOGIDO EN EL CHOICEBOX
    
    private int extraerIdUsuario() {
        Usuario usuario = this.cbUsuario.getValue();
        int idUsuario = usuario.getId();
        return idUsuario;
    }
    
    // EXTRAIGO EL ID DE LA ATRACCION ESCOGIDA EN EL CHOICEBOX
    
    private int extraerIdAtraccion() {
        Atraccion atraccion = this.cbAtraccion.getValue();
        int idAtraccion = atraccion.getIdAtraccion();
        return idAtraccion;
    }
    
    // EXTRAIGO EL USUARIO QUE COINCIDA CON EL ID PROPORCIONADO
    
    private Usuario extraerUsuario(int idUsuarioIn) {
        Usuario usuario = null;
        for (Usuario u : usuariosArList) {
            if (idUsuarioIn == u.getId()) {
                usuario = u;
            }
        }
        return usuario;
    }
    
    // EXTRAIGO LA ATRACCION QUE COINCIDA CON EL ID PROPORCIONADO
    
    private Atraccion extraerAtraccion(int idAtraccionIn) {
        Atraccion atraccion = null;

        for (Atraccion a : atraccionArList) {
            if (idAtraccionIn == a.idAtraccion) {
                atraccion = a;
            }
        }

        return atraccion;
    }
    
    // ACTUALIZO LOS VALORES DE LA TABLA CON LA ULTIMA ACCION QUE EL USUARIO A REALIZADO
    
    private void updateControls() {
        this.tiquetsArList = new ArrayList(tiquetDAO.selectAll());
        this.tiquetAuxArList = converTiquets();
        tiquetAuxObList = FXCollections.observableArrayList(tiquetAuxArList);
        tbTiquets.setItems(tiquetAuxObList);
        tbTiquets.refresh();
    }
    
    // SIMPLE FUNCION QUE LIMPIA LOS CAMPOS DESPUES DE QUE EL USUARIO HAYA REALIZADO UNA ACCION
    
    private void limpiarCampos() {
        cbAtraccion.setValue(null);
        cbUsuario.setValue(null);
        dateFecha.setValue(null);
    }
    
    // EXTRAIGO EL NOMBRE DEL USUARIO MEDIANTE EL ID PROPORCIONADO POR UN TIQUET
    
    private String extraerNombreUsuario(Tiquet tiquet) {
        String nombreUsuario = "";
        for (Usuario usuario : usuariosArList) {
            if (usuario.getId() == tiquet.getIdUsuario()) {
                nombreUsuario = usuario.getNombreUsuario();
            }
        }
        return nombreUsuario;
    }
    
    // EXTRAIGO EL NOMBRE DE LA ATRACCION MEDIANTE EL ID PROPORCIONADO POR UN TIQUET
    
    private String extraerNombreAtraccion(Tiquet tiquet) {
        String nombreAtraccion = "";
        for (Atraccion atraccion : atraccionArList) {
            if (atraccion.getIdAtraccion() == tiquet.getIdAtraccion()) {
                nombreAtraccion = atraccion.getNombreAtraccion();
            }
        }
        return nombreAtraccion;
    }
    
    // FUNCION PARA CONVERTIR LOS TIQUETS EN TIQUETS AUXILIARES
    
    private ArrayList<TiquetAuxiliar> converTiquets() {
        ArrayList<TiquetAuxiliar> tiquetsAux = new ArrayList<>();
        for (Tiquet tiquet : tiquetsArList) {
            TiquetAuxiliar tiquetAux = new TiquetAuxiliar();
            for (Usuario usuario : usuariosArList) {
                if (tiquet.getIdUsuario() == usuario.getId()) {
                    tiquetAux.setNombreUsuario(usuario.getNombreUsuario());
                }
            }
            for (Atraccion atraccion : atraccionArList) {
                if (atraccion.getIdAtraccion() == tiquet.getIdAtraccion()) {
                    tiquetAux.setNombreAtraccion(atraccion.getNombreAtraccion());
                }
            }
            tiquetAux.setIdTiquetAux(tiquet.getIdTiquet());
            tiquetAux.setFechaUso(tiquet.getFechaUso());
            tiquetsAux.add(tiquetAux);
        }
        return tiquetsAux;
    }
    
    // FUNCION QUE CONVIERTE LOS TIQUETS AUXILIARES EN TIQUETS
    
    private Tiquet converTAuxToTiquet(TiquetAuxiliar tiquetAux){
        Tiquet tiquetReturn = new Tiquet();
        
        for (Tiquet tiquet : tiquetsArList){
            if(tiquet.getIdTiquet() == tiquetAux.getIdTiquetAux()){
                tiquetReturn = tiquet;
            }
        }
        
        return tiquetReturn;
    }
    
}