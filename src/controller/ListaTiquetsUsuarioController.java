/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import dao.AtraccionDAO;
import dao.TiquetDAO;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Atraccion;
import model.Tiquet;
import model.TiquetAuxiliar;
import model.Usuario;
import javafx.event.ActionEvent;

/**
 * FXML Controller class
 *
 * @author Pol
 */
public class ListaTiquetsUsuarioController implements Initializable {
    
    // OBJETOS / ARRAYLIST / OBSERVABLELIST / MORE --> QUE UTILIZO EN ESTA CLASE
    
    Usuario usuario;
    TiquetDAO tiquetDAO;
    AtraccionDAO atraccionDAO;
    ArrayList<TiquetAuxiliar> tiquetsAuxiliarArList = new ArrayList<>();
    ArrayList<Tiquet> tiquetsArList;
    ArrayList<Atraccion> atraccionArList;
    ObservableList<TiquetAuxiliar> tiquetsAuxiliarObList;
    
    // BOTONES / TXTFIELDS / TABLAS / CHOICEBOX / MORE --> DE ESTA CLASE
    
    @FXML
    private Button btnSalir;
    @FXML
    private TableColumn<TiquetAuxiliar, String> colNombreAtraccion;
    @FXML
    private TableColumn<TiquetAuxiliar, String> colFecha;
    @FXML
    private TableView<TiquetAuxiliar> tblTiquetsUsuario;
    @FXML
    private Label label;

    public ListaTiquetsUsuarioController(Usuario usuarioIn) {
        this.usuario = usuarioIn;
        this.atraccionDAO = new AtraccionDAO();
        this.tiquetDAO = new TiquetDAO();
        this.tiquetsArList = new ArrayList<>();
        this.atraccionArList = new ArrayList(atraccionDAO.selectAll());
    }

    /**
     * Initializes the controller class.
     */
    
    // INICIALIZO LAS TABLAS PARA MOSTRAR EN ESTE CASO LOS TIQUETS DEL USUARIO
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        convertirTiquets();
        label.setText(usuario.getNombreUsuario());
        tiquetsAuxiliarObList = FXCollections.observableArrayList(tiquetsAuxiliarArList);
        tblTiquetsUsuario.setItems(tiquetsAuxiliarObList);
        tblTiquetsUsuario.refresh();
        this.colNombreAtraccion.setCellValueFactory(new PropertyValueFactory("nombreAtraccion"));
        this.colFecha.setCellValueFactory(new PropertyValueFactory("fechaUso"));
    }
    
    // FUNCION PARA CONVERTIR LOS TIQUETS NORMALES A TIQUETS AUXILIARES
    // EN ESTE CASO HAGO ESTO PORQUE SE LE TIENE QUE MOSTRAR AL USUARIO VALORES MAS
    // VISIBLES Y NO NUMEROS --> ESTO HACE REFERENCIA A LAS ATRACCIONES
    
    private void convertirTiquets() {
        tiquetsArList = tiquetDAO.selectByUsuario(usuario.getId());
        for (Tiquet tiquet : tiquetsArList) {
            for (Atraccion atraccion : atraccionArList) {
                if (atraccion.getIdAtraccion() == tiquet.getIdAtraccion()) {
//                    tAux.setNombreAtraccion(atraccion.getNombreAtraccion());
//                    tAux.setFechaUso(tiquet.getFechaUso());
                    TiquetAuxiliar tAux = new TiquetAuxiliar(atraccion.getNombreAtraccion(), tiquet.getFechaUso());
                    tiquetsAuxiliarArList.add(tAux);
                }
            }
        }

    }
    
    // FUNCION PARA CERRAR ESTA VIEW

    @FXML
    public void salir(ActionEvent event) throws IOException {
        Stage loginUsuario = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/menuCliente.fxml"));
        loader.setControllerFactory(t -> new MenuClienteController(usuario));
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
