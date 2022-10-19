/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import dao.AtraccionDAO;
import dao.LogDAO;
import dao.MecanicaDAO;
import dao.PuestoDAO;
import dao.TiquetDAO;
import dao.UsuarioDAO;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Pol
 */

// CLASE TIQUETAUXILIAR --> ES UNA CLASE UTILIZADA PARA NO MOSTRAR ID'S DE LA CLASE TIQUET
public class TiquetAuxiliar {
    
// ATRIBUTOS DE LA CLASE TIQUETAUXILIAR    
    private int idTiquetAux;
    private String nombreUsuario;
    private String nombreAtraccion;
    private String fechaUso;
    
// CONSTRUCTORES DE LA CLASE TIQUETAUXILIAR    
    
    public TiquetAuxiliar(){}
    
    public TiquetAuxiliar(String nombreAtraccion, String fechaUso) {
        this.nombreAtraccion = nombreAtraccion;
        this.fechaUso = fechaUso;
    }
    
    public TiquetAuxiliar (int idTiquetAuxIn, String nombreUsuarioIn, String nombreAtraccionIn, String fechaUsoIn){
        this.idTiquetAux = idTiquetAuxIn;
        this.nombreUsuario = nombreUsuarioIn;
        this.nombreAtraccion = nombreAtraccionIn;
        this.fechaUso = fechaUsoIn;
    }

// GETTERS & SETTERS DE LA CLASE TIQUETAUXILIAR    
    
    public int getIdTiquetAux() {
        return idTiquetAux;
    }

    public void setIdTiquetAux(int idTiquetAux) {
        this.idTiquetAux = idTiquetAux;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
    
    
    
    public String getNombreAtraccion() {
        return nombreAtraccion;
    }

    public void setNombreAtraccion(String nombreAtraccion) {
        this.nombreAtraccion = nombreAtraccion;
    }

    public String getFechaUso() {
        return fechaUso;
    }

    public void setFechaUso(String fechaUso) {
        this.fechaUso = fechaUso;
    }

// METODO TOSTRING DE LA CLASE TIQUETAUXILIAR    
    
    @Override
    public String toString() {
        return "TiquetAuxiliar{" + "nombreAtraccion=" + nombreAtraccion + ", fechaUso=" + fechaUso + '}';
    }
    
    
}