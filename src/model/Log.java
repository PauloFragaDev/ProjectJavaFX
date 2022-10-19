/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;

/**
 *
 * @author polfr
 */
//CLASE LOG 
public class Log implements Serializable{
    
// ATRIBUTOS QUE CONFORMAN LA CLASE LOG    
    private int idLog;
    private int idUsuario;
    private String accion;
    private String descripcion;

// CONSTRUCTORES DE LA CLASE LOG    
    
    public Log(){}
    
    public Log(int idUsuario, String accion, String descripcion) {
        this.idLog = idLog;
        this.idUsuario = idUsuario;
        this.accion = accion;
        this.descripcion = descripcion;
    }
    
    public Log(int idLog, int idUsuario, String accion, String descripcion) {
        this.idLog = idLog;
        this.idUsuario = idUsuario;
        this.accion = accion;
        this.descripcion = descripcion;
    }

// GETTERS & SETTERS DE LA CLASE LOG    
    
    public int getIdLog() {
        return idLog;
    }

    public void setIdLog(int idLog) {
        this.idLog = idLog;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

// METODO TOSTRING DE LA CLASE LOG    
    
    @Override
    public String toString() {
        return "Log{" + "idLog=" + idLog + ", idUsuario=" + idUsuario + ", accion=" + accion + ", descripcion=" + descripcion + '}';
    }
    
    
}
