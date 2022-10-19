/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;

/**
 *
 * @author Pol
 */

// CLASE TIQUET
public class Tiquet implements Serializable{
    
// ATRIBUTOS DE LA CLASE TIQUET    
    private int idTiquet;
    private int idUsuario;
    private int idAtraccion;
    private String fechaUso;
    
// CONSTRUCTORES DE LA CLASE TIQUET    
    
    public Tiquet(){}
    
    public Tiquet(int idUsuario, int idAtraccion, String fechaUso) {
        this.idUsuario = idUsuario;
        this.idAtraccion = idAtraccion;
        this.fechaUso = fechaUso;
    }
    
    public Tiquet(int idTiquet, int idUsuario, int idAtraccion, String fechaUso) {
        this.idTiquet = idTiquet;
        this.idUsuario = idUsuario;
        this.idAtraccion = idAtraccion;
        this.fechaUso = fechaUso;
    }

// GETTERS & SETTERS DE LA CLASE TIQUET    
    
    public int getIdTiquet() {
        return idTiquet;
    }

    public void setIdTiquet(int idTiquet) {
        this.idTiquet = idTiquet;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdAtraccion() {
        return idAtraccion;
    }

    public void setIdAtraccion(int idAtraccion) {
        this.idAtraccion = idAtraccion;
    }

    public String getFechaUso() {
        return fechaUso;
    }

    public void setFechaUso(String fechaUso) {
        this.fechaUso = fechaUso;
    }
    
    
}
