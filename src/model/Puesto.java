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

// CLASE PUESTO QUE HEREDA DE LA CLASE ATRACCION
public class Puesto extends Atraccion implements Serializable{
    
// ATRIBUTOS DE LA CLASE PUESTO    
    private String premio;
    
// CONSTRUCTORES DE LA CLASE PUESTO    
    
    public Puesto(){}
    
    public Puesto(String premio) {
        this.premio = premio;
    }
    
    public Puesto(String nombreAtraccion, int precioAtraccion, int edadMinima, String premio) {
        super.idAtraccion = idAtraccion;
        super.nombreAtraccion = nombreAtraccion;
        super.precioAtraccion = precioAtraccion;
        super.edadMinima = edadMinima;
        this.premio = premio;
    }
    
    public Puesto(int idAtraccion, String nombreAtraccion, int precioAtraccion, int edadMinima, String premio) {
        super.idAtraccion = idAtraccion;
        super.nombreAtraccion = nombreAtraccion;
        super.precioAtraccion = precioAtraccion;
        super.edadMinima = edadMinima;
        this.premio = premio;
    }

// GETTERS & SETTERS DE LA CLASE PUESTO    
    
    public String getPremio() {
        return premio;
    }

    public void setPremio(String premio) {
        this.premio = premio;
    }

    public int getIdAtraccion() {
        return idAtraccion;
    }

    public void setIdAtraccion(int idAtraccion) {
        this.idAtraccion = idAtraccion;
    }

    public String getNombreAtraccion() {
        return nombreAtraccion;
    }

    public void setNombreAtraccion(String nombreAtraccion) {
        this.nombreAtraccion = nombreAtraccion;
    }

    public int getPrecioAtraccion() {
        return precioAtraccion;
    }

    public void setPrecioAtraccion(int precioAtraccion) {
        this.precioAtraccion = precioAtraccion;
    }

    public int getEdadMinima() {
        return edadMinima;
    }

    public void setEdadMinima(int edadMinima) {
        this.edadMinima = edadMinima;
    }
    
    
}
