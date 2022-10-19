/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import dao.AtraccionDAO;
import dao.MecanicaDAO;
import dao.PuestoDAO;
import dao.TiquetDAO;
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
import javafx.scene.control.Tab;
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
import model.Tiquet;
import model.Usuario;

/**
 * FXML Controller class
 *
 * @author Pol
 */
public class GestionAtraccionController implements Initializable {

    // OBJETOS / ARRAYLIST / OBSERVABLELIST / MORE --> QUE UTILIZO EN ESTA CLASE
    AtraccionDAO atraccionDAO;
    MecanicaDAO mecanicaDAO;
    PuestoDAO puestoDAO;
    TiquetDAO tiquetDAO;
    String accion;

    Usuario usuarioLogin;

    private ObservableList<Mecanica> mecanicaObList;
    private ObservableList<Puesto> puestoObList;
    private ArrayList<Mecanica> mecanicaArList;
    private ArrayList<Puesto> puestoArList;
    private ArrayList<Atraccion> atraccionArList;
    private ArrayList<Tiquet> tiquetsArList;

    // BOTONES / TXTFIELDS / TABLAS / CHOICEBOX / MORE --> DE ESTA CLASE
    @FXML
    private TabPane tabpane;
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
    private Button btnSalir;
    @FXML
    private Button btnModificarAtraccion;
    @FXML
    private Button btnCrearAtraccion;
    @FXML
    private Button btnEliminarAtraccion;
    @FXML
    private Tab tabMecanica;
    @FXML
    private TableColumn<Mecanica, Integer> colIdMecanica;
    @FXML
    private TableColumn<Mecanica, Integer> colNumPasajeros;
    @FXML
    private Tab tabPuestos;
    @FXML
    private TableColumn<Puesto, Integer> colIdPuesto;
    @FXML
    private TableView<Mecanica> tblMecanicas;
    @FXML
    private TableView<Puesto> tblPuestos;
    @FXML
    private TableColumn<Mecanica, String> colNombreMecanica;

    // CONSTRUCTORES DE LA CLASE
    public GestionAtraccionController(Usuario usuarioIn) {
        this.usuarioLogin = usuarioIn;
        this.atraccionDAO = new AtraccionDAO();
        this.mecanicaDAO = new MecanicaDAO();
        this.puestoDAO = new PuestoDAO();
        this.tiquetDAO = new TiquetDAO();
        this.mecanicaArList = new ArrayList(mecanicaDAO.selectAll());
        this.puestoArList = new ArrayList(puestoDAO.selectAll());
        this.atraccionArList = new ArrayList(atraccionDAO.selectAll());
        this.tiquetsArList = new ArrayList(tiquetDAO.selectAll());
    }

    // INICIALIZO LO NECESARIO PARA CARGAR LAS TABLAS
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mecanicaObList = FXCollections.observableArrayList(mecanicaArList);
        puestoObList = FXCollections.observableArrayList(puestoArList);
        //-----------------------------------------------------------------//
        tblMecanicas.setItems(mecanicaObList);
        tblMecanicas.refresh();
        tblPuestos.setItems(puestoObList);
        tblPuestos.refresh();
        //-----------------------------------------------------------------//
        this.colIdMecanica.setCellValueFactory(new PropertyValueFactory("idAtraccion"));
        this.colNombreMecanica.setCellValueFactory(new PropertyValueFactory("nombreAtraccion"));
        this.colPrecioMecanica.setCellValueFactory(new PropertyValueFactory("precioAtraccion"));
        this.colEdadMinMecanica.setCellValueFactory(new PropertyValueFactory("edadMinima"));
        this.colAlturaMinMecanica.setCellValueFactory(new PropertyValueFactory("alturaMinima"));
        this.colNumPasajeros.setCellValueFactory(new PropertyValueFactory("numeroPasajeros"));
        //-----------------------------------------------------------------//
        this.colIdPuesto.setCellValueFactory(new PropertyValueFactory("idAtraccion"));
        this.colNombrePuesto.setCellValueFactory(new PropertyValueFactory("nombreAtraccion"));
        this.colPrecioPuesto.setCellValueFactory(new PropertyValueFactory("precioAtraccion"));
        this.colEdadMinPuesto.setCellValueFactory(new PropertyValueFactory("edadMinima"));
        this.colPremioPuesto.setCellValueFactory(new PropertyValueFactory("premio"));
    }

    // FUNCION PARA CERRAR ESTA VENTANA
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

    // METODO DONDE EL USUARIO DEPENDIENDO DEL TAB Y DEL VALOR ESCOGIDO EN LA TABLA
    // LE LLEVARA A UNA VENTANA DONDE TENDRA QUE MODIFICAR LOS VALORES DEL OBJETO
    // EN CUESTION.
    @FXML
    private void modificarAtraccion(ActionEvent event) throws IOException {
        int tabPanel;
        tabPanel = this.tabpane.getSelectionModel().getSelectedIndex();

        if (tabPanel == 0 && event.getSource() == btnModificarAtraccion) {

            Mecanica mecanica = this.tblMecanicas.getSelectionModel().getSelectedItem();
            

            if (mecanica == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("Error");
                alert.setContentText("Tienes que seleccionar una Atraccion para modificarla");
                alert.showAndWait();
            } else {
                Atraccion atraccion = extraerAtraccion(mecanica);
                Stage loginUsuario = new Stage();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/modificarAtraccionMecanica.fxml"));
                loader.setControllerFactory(t -> new ModificarAtraccionMecanicaController(mecanica, usuarioLogin, atraccion));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                scene.setFill(Color.TRANSPARENT);
                loginUsuario.initStyle(StageStyle.TRANSPARENT);
                loginUsuario.setScene(scene);
                loginUsuario.show();
                Stage stage = (Stage) this.btnCrearAtraccion.getScene().getWindow();
                stage.close();
            }
        } else if (tabPanel == 1 && event.getSource() == btnModificarAtraccion) {

            Puesto puesto = this.tblPuestos.getSelectionModel().getSelectedItem();
            

            if (puesto == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("Error");
                alert.setContentText("Tienes que seleccionar una Atraccion para modificarla");
                alert.showAndWait();
            } else {
                Atraccion atraccion = extraerAtraccion(puesto);
                Stage loginUsuario = new Stage();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/modificarAtraccionPuesto.fxml"));
                loader.setControllerFactory(t -> new ModificarAtraccionPuestoController(puesto, usuarioLogin, atraccion));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                scene.setFill(Color.TRANSPARENT);
                loginUsuario.initStyle(StageStyle.TRANSPARENT);
                loginUsuario.setScene(scene);
                loginUsuario.show();
                Stage stage = (Stage) this.btnCrearAtraccion.getScene().getWindow();
                stage.close();

            }

        }
    }

    // FUNCION PARA CREAR UNA ATRACCION DEPENDIENDO EL TAB EN EL QUE SE ENCUENTRE
    // EL USUARIO
    @FXML
    private void crearAtraccion(ActionEvent event) throws IOException {
        int tabPanel;
        tabPanel = this.tabpane.getSelectionModel().getSelectedIndex();

        if (tabPanel == 0 && event.getSource() == btnCrearAtraccion) {
            Stage loginUsuario = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/crearAtraccionMecanica.fxml"));
            loader.setControllerFactory(t -> new CrearAtraccionMecanicaController(usuarioLogin));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            scene.setFill(Color.TRANSPARENT);
            loginUsuario.initStyle(StageStyle.TRANSPARENT);
            loginUsuario.setScene(scene);
            loginUsuario.show();
            Stage stage = (Stage) this.btnCrearAtraccion.getScene().getWindow();
            stage.close();
        } else if (tabPanel == 1 && event.getSource() == btnCrearAtraccion) {
            Stage loginUsuario = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/crearAtraccionPuesto.fxml"));
            loader.setControllerFactory(t -> new CrearAtraccionPuestoController(usuarioLogin));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            scene.setFill(Color.TRANSPARENT);
            loginUsuario.initStyle(StageStyle.TRANSPARENT);
            loginUsuario.setScene(scene);
            loginUsuario.show();
            Stage stage = (Stage) this.btnCrearAtraccion.getScene().getWindow();
            stage.close();
        }
    }

    // EL USUARIO ESCOJE UN VALOR DE LA TABLA DEPENDIENDO DEL TAB, ENTONCES LLAMARA A UNA
    // FUNCION PARA ELIMINAR LA ATRACCION CORRESPONDIENTE
    @FXML
    private void eliminarAtraccion(ActionEvent event) throws IOException {
        int tabPanel;
        tabPanel = this.tabpane.getSelectionModel().getSelectedIndex();

        if (tabPanel == 0 && event.getSource() == btnEliminarAtraccion) {
            eliminarAtraccionMecanica();
        } else if (tabPanel == 1 && event.getSource() == btnEliminarAtraccion) {
            eliminarAtraccionPuesto();
        }
    }

    // FUNCION QUE ELIMINA UNA ATRACCION MECANICA
    private void eliminarAtraccionMecanica() throws IOException {
        Mecanica mecanica = this.tblMecanicas.getSelectionModel().getSelectedItem();

        Atraccion atraccion = extraerAtraccion(mecanica);

        if (mecanica == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Tienes que seleccionar una Atraccion");
            alert.showAndWait();
        } else {
            mecanicaDAO.delete(mecanica);
            comprobarTiquets(atraccion);
            atraccionDAO.delete(atraccion);
            updateControls();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setTitle("Informacion");
            alert.setContentText("Atraccion Eliminada");
            alert.showAndWait();

            accion = "EliminarAtraccionMecanica";
            Stage loginUsuario = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/asignarDescripcion.fxml"));
            loader.setControllerFactory(t -> new AsignarDescripcionController(accion, usuarioLogin));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            scene.setFill(Color.TRANSPARENT);
            loginUsuario.initStyle(StageStyle.TRANSPARENT);
            loginUsuario.setScene(scene);
            loginUsuario.show();
            Stage stage = (Stage) this.btnEliminarAtraccion.getScene().getWindow();

        }
    }

    // FUNCION QUE ELIMINA UN PUESTO
    private void eliminarAtraccionPuesto() throws IOException {
        Puesto puesto = this.tblPuestos.getSelectionModel().getSelectedItem();

        Atraccion atraccion = extraerAtraccion(puesto);

        if (puesto == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Tienes que seleccionar una Atraccion");
            alert.showAndWait();
        } else {
            puestoDAO.delete(puesto);
            comprobarTiquets(atraccion);
            atraccionDAO.delete(atraccion);
            updateControls();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setTitle("Informacion");
            alert.setContentText("Atraccion Eliminada");
            alert.showAndWait();

            accion = "EliminarAtraccionPuesto";
            Stage loginUsuario = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/asignarDescripcion.fxml"));
            loader.setControllerFactory(t -> new AsignarDescripcionController(accion, usuarioLogin));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            scene.setFill(Color.TRANSPARENT);
            loginUsuario.initStyle(StageStyle.TRANSPARENT);
            loginUsuario.setScene(scene);
            loginUsuario.show();
            Stage stage = (Stage) this.btnEliminarAtraccion.getScene().getWindow();

        }
    }

    // ACTUALIZA LOS VALORES DE LA TABLA CON LA ACCION CORRESPONDIENTE QUE HIZO EL USUARIO
    private void updateControls() {
        this.mecanicaArList = new ArrayList(mecanicaDAO.selectAll());
        this.puestoArList = new ArrayList(puestoDAO.selectAll());
        this.atraccionArList = new ArrayList(atraccionDAO.selectAll());
        mecanicaObList = FXCollections.observableArrayList(mecanicaArList);
        puestoObList = FXCollections.observableArrayList(puestoArList);
        tblMecanicas.setItems(mecanicaObList);
        tblPuestos.setItems(puestoObList);
        tblMecanicas.refresh();
        tblPuestos.refresh();
    }

    // EXTRAIGO LA ATRACCION QUE COINCIDA CON EL NOMBRE OTORGADO
    public Atraccion extraerAtraccion(Atraccion atraccionIn) {
        Atraccion atraccion = null;

        for (Atraccion atc : atraccionArList) {
            if (atc.nombreAtraccion.equals(atraccionIn.getNombreAtraccion())) {
                atraccion = atraccionIn;
            }
        }

        return atraccion;
    }

    // FUNCION QUE ELIMINA LOS TIQUETS RELACIONAS A LA ATRACCION QUE SE ELIMINARA
    private void comprobarTiquets(Atraccion atraccion) {
        for (Tiquet tiquet : tiquetsArList) {
            if (tiquet.getIdAtraccion() == atraccion.idAtraccion) {
                tiquetDAO.delete(tiquet);
            }
        }
    }

}
