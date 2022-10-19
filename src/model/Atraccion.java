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

//CLASE ATRACCION QUE ES PADRE DE 2 MAS
public class Atraccion implements Serializable{
    
//ATRIBUTOS QUE CONFORMAN ESTA CLASE
    public int idAtraccion;
    public String nombreAtraccion;
    public int precioAtraccion;
    public int edadMinima;
    
// CONSTRUCTORES DE LA CLASE
    
    public Atraccion(){}
    
    public Atraccion(String nombreAtraccion, int precioAtraccion, int edadMinima) {
        this.nombreAtraccion = nombreAtraccion;
        this.precioAtraccion = precioAtraccion;
        this.edadMinima = edadMinima;
    }
    
    public Atraccion(int idAtraccion, String nombreAtraccion, int precioAtraccion, int edadMinima) {
        this.idAtraccion = idAtraccion;
        this.nombreAtraccion = nombreAtraccion;
        this.precioAtraccion = precioAtraccion;
        this.edadMinima = edadMinima;
    }

// GETTERS & SETTERS DE LA CLASE ATRACCION    
    
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

// METODO TOSTRING DE LA CLASE ATRACCION    
    
    @Override
    public String toString() {
        return nombreAtraccion;
    }
    
    
}
