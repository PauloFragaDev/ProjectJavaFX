/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import dao.AtraccionDAO;
import dao.LogDAO;
import dao.MecanicaDAO;
import dao.PuestoDAO;
import dao.TiquetDAO;
import dao.UsuarioDAO;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
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
import model.Log;
import model.Mecanica;
import model.Puesto;
import model.Tiquet;
import model.Usuario;

/**
 * FXML Controller class
 *
 * @author Pol
 */
public class GestionBackUpController implements Initializable {
    
    // OBJETOS / ARRAYLIST / OBSERVABLELIST / MORE --> QUE UTILIZO EN ESTA CLASE
    
    Usuario usuarioLogin;
    ModelsAll modelsAll;
    String accion;

    UsuarioDAO usuarioDAO = new UsuarioDAO();
    AtraccionDAO atraccionDAO = new AtraccionDAO();
    MecanicaDAO mecanicaDAO = new MecanicaDAO();
    PuestoDAO puestoDAO = new PuestoDAO();
    TiquetDAO tiquetDAO = new TiquetDAO();
    LogDAO logDAO = new LogDAO();
    
    // BOTONES / TXTFIELDS / TABLAS / CHOICEBOX / MORE --> DE ESTA CLASE
    
    @FXML
    private Button btnSalir;
    @FXML
    private Button btnGenerar;
    @FXML
    private Button btnCargar;
    @FXML
    private TextField txtURL;
    
    // CONSTRUCTORES DE LA CLASE
    
    public GestionBackUpController(Usuario usuarioIn) {
        this.usuarioLogin = usuarioIn;
        this.modelsAll = new ModelsAll(0);
    }

    public GestionBackUpController() {
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    // FUNCION PARA SALIR DE LA VISTA
    
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
    
    // FUNCION PARA GENERAR UN BACKUP
    
    @FXML
    private void generarBackUp(ActionEvent event) {
        
        // CON EL SIGUIENTE STRING RECOJO LA RUTA ESTABLECIDA POR EL USUARIO
        
        String ruta = txtURL.getText();
        try {
            
            // CREO UN OBJECTOUTPUTSTREAM CON LA RUTA ESTABLECIDA
            
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ruta));
            
            //CARGO LOS VALORES DE MODELSALL EN EL BACKUP
            
            oos.writeObject(modelsAll);
            
            // GENERO UN ALERT PARA INFORMAR QUE EL BACKUP SE HA REALIZADO
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Informacion");
            alert.setContentText("BackUp Creado");
            alert.showAndWait();
            
            // GENERO LA VIEW PARA QUE EL USUARIO DESCRIBA LA ACCION REALIZADA
            
            accion = "GenerarBackUp";
            Stage loginUsuario = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/asignarDescripcion.fxml"));
            loader.setControllerFactory(t -> new AsignarDescripcionController(accion, usuarioLogin));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            scene.setFill(Color.TRANSPARENT);
            loginUsuario.initStyle(StageStyle.TRANSPARENT);
            loginUsuario.setScene(scene);
            loginUsuario.show();
            Stage stage = (Stage) this.btnGenerar.getScene().getWindow();
        } catch (IOException ex) {
            
            // EN CASO QUE NO SE HAYA PODIDO HACER EL BACK UP SALDRA ESTA VENTANA EMERGENTE
            
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("BackUp no creado");
            alert.showAndWait();
        }
    }
    
    // FUNCION PARA CARGAR UN BACKUP
    
    @FXML
    private void cargarBackUp(ActionEvent event) {
        
        // ESTABLEZCO LA RUTA DEL ARCHIVO QUE SE LE PROPORCIONA EN UN CAMPO TXT
        
        String ruta = txtURL.getText();
        
        // CREO UN OBJETO DE LA CLASE DE ESTE .JAVA
        
        ModelsAll modelsAllAux;
        try {
            
            // CREO UN ObjectInputStream ESTABLECIENDOLE LA RUTA PROPORCIONADA
            
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ruta));
            
            // CARGO LOS VALORES DE LA RUTA EN EL OBJETO
            
            modelsAllAux = (ModelsAll) ois.readObject();
            
            // ELIMINO TODO LO QUE HAYA EN LA BASE DE DATOS
            
            usuarioDAO.deleteAll();
            atraccionDAO.deleteAll();
            mecanicaDAO.deleteAll();
            puestoDAO.deleteAll();
            tiquetDAO.deleteAll();
            logDAO.deleteAll();
            
            // REALIZO FORS PARA AÑADIR EL CONTENIDO DEL OBJETO MODELSALLAUX 
            // ESTE CONTIENE TODO LOS OBJETOS DEL BACKUP QUE SE ACABA DE RESTAURAR
            
            for (Usuario usuario : modelsAllAux.usuarioArrayList) {
                usuarioDAO.insertId(usuario);
            }
            for (Atraccion atraccion : modelsAllAux.atraccionArrayList) {
                atraccionDAO.insertId(atraccion);
            }
            for (Mecanica mecanica : modelsAllAux.mecanicaArrayList) {
                mecanicaDAO.insertId(mecanica);
            }
            for (Puesto puesto : modelsAllAux.puestoArrayList) {
                puestoDAO.insertId(puesto);
            }
            for (Tiquet tiquet : modelsAllAux.tiquetArrayList) {
                tiquetDAO.insertId(tiquet);
            }
            for (Log log : modelsAllAux.logArrayList) {
                logDAO.insertId(log);
            }
            
            // SALTA UN ALERT INFORMANDO QUE SE HA PODIDO REALIZAR LA RESTAURACION
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Informacion");
            alert.setContentText("Restauracion Exitosa");
            alert.showAndWait();
            
            // SALTA UNA VENTANA EMERGENTE DONDE EL USUARIO TIENE QUE REALIZAR LA DESCRIPCION DE LO REALIZADO
            
            accion = "CargarBackUp";
            Stage loginUsuario = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/asignarDescripcion.fxml"));
            loader.setControllerFactory(t -> new AsignarDescripcionController(accion, usuarioLogin));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            scene.setFill(Color.TRANSPARENT);
            loginUsuario.initStyle(StageStyle.TRANSPARENT);
            loginUsuario.setScene(scene);
            loginUsuario.show();
            Stage stage = (Stage) this.btnCargar.getScene().getWindow();

        } catch (Exception e) {
            
            // EN CASO QUE FALLE LA RESTAURACION SALTARA ESTE ALERT
            
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Restauracion Fallida");
            alert.showAndWait();
        }
    }

}

// CREO UNA CLASE ESPECIFICA PARA ESTE .JAVA LA CUAL ME PROPORCIONARA LOS VALORES
// NECESARIOS PARA HACER LOS BACKUP Y LAS RESTAURACIONES

class ModelsAll implements Serializable {

    // ATRIBUTOS QUE CONTIENE ESTA CLASE
    
    ArrayList<Usuario> usuarioArrayList;
    ArrayList<Atraccion> atraccionArrayList;
    ArrayList<Mecanica> mecanicaArrayList;
    ArrayList<Puesto> puestoArrayList;
    ArrayList<Tiquet> tiquetArrayList;
    ArrayList<Log> logArrayList;

    UsuarioDAO usuarioDAO = new UsuarioDAO();
    AtraccionDAO atraccionDAO = new AtraccionDAO();
    MecanicaDAO mecanicaDAO = new MecanicaDAO();
    PuestoDAO puestoDAO = new PuestoDAO();
    TiquetDAO tiquetDAO = new TiquetDAO();
    LogDAO logDAO = new LogDAO();
    
    // CONSTRUCTORES DE ESTA CLASE
    
    public ModelsAll() {
    }

    public ModelsAll(int serializar) {
        this.usuarioArrayList = new ArrayList(usuarioDAO.selectAll());
        this.atraccionArrayList = new ArrayList(atraccionDAO.selectAll());
        this.mecanicaArrayList = new ArrayList(mecanicaDAO.selectAll());
        this.puestoArrayList = new ArrayList(puestoDAO.selectAll());
        this.tiquetArrayList = new ArrayList(tiquetDAO.selectAll());
        this.logArrayList = new ArrayList(logDAO.selectAll());
    }

    // GETTERS & SETTERS DE LA CLASE
    
    public ArrayList<Usuario> getUsuarioArrayList() {
        return usuarioArrayList;
    }

    public void setUsuarioArrayList(ArrayList<Usuario> usuarioArrayList) {
        this.usuarioArrayList = usuarioArrayList;
    }

    public ArrayList<Atraccion> getAtraccionArrayList() {
        return atraccionArrayList;
    }

    public void setAtraccionArrayList(ArrayList<Atraccion> atraccionArrayList) {
        this.atraccionArrayList = atraccionArrayList;
    }

    public ArrayList<Mecanica> getMecanicaArrayList() {
        return mecanicaArrayList;
    }

    public void setMecanicaArrayList(ArrayList<Mecanica> mecanicaArrayList) {
        this.mecanicaArrayList = mecanicaArrayList;
    }

    public ArrayList<Puesto> getPuestoArrayList() {
        return puestoArrayList;
    }

    public void setPuestoArrayList(ArrayList<Puesto> puestoArrayList) {
        this.puestoArrayList = puestoArrayList;
    }

    public ArrayList<Tiquet> getTiquetArrayList() {
        return tiquetArrayList;
    }

    public void setTiquetArrayList(ArrayList<Tiquet> tiquetArrayList) {
        this.tiquetArrayList = tiquetArrayList;
    }

    public ArrayList<Log> getLogArrayList() {
        return logArrayList;
    }

    public void setLogArrayList(ArrayList<Log> logArrayList) {
        this.logArrayList = logArrayList;
    }

    public UsuarioDAO getUsuarioDAO() {
        return usuarioDAO;
    }

    public void setUsuarioDAO(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    public AtraccionDAO getAtraccionDAO() {
        return atraccionDAO;
    }

    public void setAtraccionDAO(AtraccionDAO atraccionDAO) {
        this.atraccionDAO = atraccionDAO;
    }

    public MecanicaDAO getMecanicaDAO() {
        return mecanicaDAO;
    }

    public void setMecanicaDAO(MecanicaDAO mecanicaDAO) {
        this.mecanicaDAO = mecanicaDAO;
    }

    public PuestoDAO getPuestoDAO() {
        return puestoDAO;
    }

    public void setPuestoDAO(PuestoDAO puestoDAO) {
        this.puestoDAO = puestoDAO;
    }

    public TiquetDAO getTiquetDAO() {
        return tiquetDAO;
    }

    public void setTiquetDAO(TiquetDAO tiquetDAO) {
        this.tiquetDAO = tiquetDAO;
    }

    public LogDAO getLogDAO() {
        return logDAO;
    }

    public void setLogDAO(LogDAO logDAO) {
        this.logDAO = logDAO;
    }

}
