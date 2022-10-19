/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import dao.AtraccionDAO;
import dao.MecanicaDAO;
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
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Atraccion;
import model.Mecanica;
import model.Usuario;

/**
 * FXML Controller class
 *
 * @author polfr
 */
public class ModificarAtraccionMecanicaController implements Initializable {
    
    // OBJETOS / ARRAYLIST / OBSERVABLELIST / MORE --> QUE UTILIZO EN ESTA CLASE
    
    Usuario usuarioLogin;
    Mecanica atraccionMecanica = new Mecanica();
    Atraccion atraccionModi = new Atraccion();
    ArrayList<Atraccion> atraccionArList;
    AtraccionDAO atraccionDAO;
    MecanicaDAO mecanicaDAO;
    String accion;
    
    // BOTONES / TXTFIELDS / TABLAS / CHOICEBOX / MORE --> DE ESTA CLASE
    
    @FXML
    private Button btnSalir;
    @FXML
    private TextField txtNombreAtraccion;
    @FXML
    private TextField txtPrecioAtraccion;
    @FXML
    private TextField txtEdadMinima;
    @FXML
    private TextField txtAlturaMinima;
    @FXML
    private TextField txtNumPasajeros;
    @FXML
    private Button btnModificarAtraccion;
    
    // CONSTRUCTORES DE LA CLASE
    
    public ModificarAtraccionMecanicaController() {
    }

    public ModificarAtraccionMecanicaController(Mecanica mecanicaIn, Usuario usuarioIn, Atraccion atraccionIn) {
        this.usuarioLogin = usuarioIn;
        this.atraccionMecanica = mecanicaIn;
        this.atraccionDAO = new AtraccionDAO();
        this.mecanicaDAO = new MecanicaDAO();
        this.atraccionArList = new ArrayList(atraccionDAO.selectAll());
        this.atraccionModi = atraccionIn;
    }

    /**
     * Initializes the controller class.
     */
    
    // INICIALIZO LOS CAMPOS QUE SE RELLENARAN AUTOMATICAMENTE
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txtNombreAtraccion.setText(atraccionMecanica.getNombreAtraccion());
        txtPrecioAtraccion.setText(String.valueOf(atraccionMecanica.getPrecioAtraccion()));
        txtEdadMinima.setText(String.valueOf(atraccionMecanica.getEdadMinima()));
        txtAlturaMinima.setText(String.valueOf(atraccionMecanica.getAlturaMinima()));
        txtNumPasajeros.setText(String.valueOf(atraccionMecanica.getNumeroPasajeros()));
    }
    
    // FUNCION PARA SALIR DE ESTA VIEW
    
    @FXML
    private void salirAplicacion(ActionEvent event) throws IOException {
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
    
    // METODO PARA MODIFICAR UNA ATRACCION
    
    @FXML
    private void modificarAtraccion(ActionEvent event) {
        try {
            // RECOJO LOS NUEVOS VALORES QUE SE HAYAN ESCOGIDO
            String nombreAtraccion = this.txtNombreAtraccion.getText();
            String precioAtraccionAAA = this.txtPrecioAtraccion.getText();
            String edadMinimaAAA = this.txtEdadMinima.getText();
            String alturaMinimaAAA = this.txtAlturaMinima.getText();
            String numeroPasajerosAAA = this.txtNumPasajeros.getText();
            int precioAtraccion, edadMinima, alturaMinima, numeroPasajeros;
            
            // COMPRUEBO QUE SE PUEDA HACER LA CONVERSION DE STRING A INTEGER
            
            if (isNumero(precioAtraccionAAA, edadMinimaAAA, alturaMinimaAAA, numeroPasajerosAAA) == true) {
                precioAtraccion = Integer.parseInt(precioAtraccionAAA);
                edadMinima = Integer.parseInt(edadMinimaAAA);
                alturaMinima = Integer.parseInt(alturaMinimaAAA);
                numeroPasajeros = Integer.parseInt(numeroPasajerosAAA);
                
                // CREO LOS OBJETOS QUE REMPLAZARAN LOS VALORES DE LA ATRACCION ESCOGIDA
                
                Mecanica mecanica = new Mecanica(nombreAtraccion, precioAtraccion, edadMinima, alturaMinima, numeroPasajeros);
                Atraccion atraccion = new Atraccion(nombreAtraccion, precioAtraccion, edadMinima);

                //EN EL CASO QUE SE ENTRE SETEARA LOS NUEVOS VALORES
                //AL OBEJETO SELECCIONADO
                atraccionMecanica.setNombreAtraccion(mecanica.getNombreAtraccion());
                atraccionMecanica.setPrecioAtraccion(mecanica.getPrecioAtraccion());
                atraccionMecanica.setEdadMinima(mecanica.getEdadMinima());
                atraccionMecanica.setAlturaMinima(mecanica.getAlturaMinima());
                atraccionMecanica.setNumeroPasajeros(mecanica.getNumeroPasajeros());

                atraccionModi.setNombreAtraccion(mecanica.getNombreAtraccion());
                atraccionModi.setPrecioAtraccion(mecanica.getPrecioAtraccion());
                atraccionModi.setEdadMinima(mecanica.getEdadMinima());
                
                // EJECUTO LOS METODOS CORRESPONDIENTES PARA HACER EL UPDATE EN LA BASE DE DATOS
                
                mecanicaDAO.update(atraccionMecanica);
                atraccionDAO.update(atraccionModi);
                
                // SALTA UN ALERT INFORMANDO QUE SE HA MODIFICADO LA ATRACCION
                
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText(null);
                alert.setTitle("Informacion");
                alert.setContentText("Atraccion -> Mecanica Modificada");
                alert.showAndWait();
                
                // DESPUES SALTARA ESTA VENTANA DONDE EL ADMIN TENDRA QUE PONER QUE ACABA DE REALIZAR
                // ESTO CREARA UNA CONSTANCIA EN EL LOG
                
                accion = "ModificarAtraccionMecanica";
                Stage loginUsuario = new Stage();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/asignarDescripcion.fxml"));
                loader.setControllerFactory(t -> new AsignarDescripcionController(accion, usuarioLogin));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                scene.setFill(Color.TRANSPARENT);
                loginUsuario.initStyle(StageStyle.TRANSPARENT);
                loginUsuario.setScene(scene);
                loginUsuario.show();
                Stage stage = (Stage) this.btnModificarAtraccion.getScene().getWindow();
            } else {
                
                // EN CASO QUE SE HAYAN INTRODUCIDO CARACTERES NO VALIDOS SALTARA ESTE ALERT 
                
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("Error");
                alert.setContentText("Uno de los campos contiene un caracter no valido");
                alert.showAndWait();
            }

        } catch (Exception e) {
            System.out.println("Error");
        }
    }
    
    // METODO PARA CONVERTIR UN STRING A INTEGER
    
    private boolean isNumero(String texto, String texto2, String texto3, String texto4) {
        boolean resultado;
        try {
            Integer.parseInt(texto);
            resultado = true;
            try {
                Integer.parseInt(texto2);
                resultado = true;
                try {
                    Integer.parseInt(texto3);
                    resultado = true;
                    try {
                        Integer.parseInt(texto4);
                        resultado = true;
                    } catch (NumberFormatException e) {
                        resultado = false;
                    }
                } catch (NumberFormatException e) {
                    resultado = false;
                }
            } catch (NumberFormatException e) {
                resultado = false;
            }
        } catch (NumberFormatException e) {
            resultado = false;
        }
        return resultado;
    }
}
