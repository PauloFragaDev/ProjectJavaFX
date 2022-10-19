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

// CLASE MECANICA QUE HEREDA DE LA CLASE ATRACCION
public class Mecanica extends Atraccion implements Serializable{
    
// ATRIBUTOS DE LA CLASE MECANICA    
    private int alturaMinima;
    private int numeroPasajeros;
    
// CONSTRUCTORES DE LA CLASE MECANICA    
        
    public Mecanica(){}
    
    public Mecanica(int alturaMinima, int numeroPasajeros) {
        this.alturaMinima = alturaMinima;
        this.numeroPasajeros = numeroPasajeros;
    }
    
    public Mecanica(String nombreAtraccion, int precioAtraccion, int edadMinima, int alturaMinima, int numeroPasajeros) {
        super.nombreAtraccion = nombreAtraccion;
        super.precioAtraccion = precioAtraccion;
        super.edadMinima = edadMinima;
        this.alturaMinima = alturaMinima;
        this.numeroPasajeros = numeroPasajeros;
    }
    
    public Mecanica(int idAtraccion, String nombreAtraccion, int precioAtraccion, int edadMinima, int alturaMinima, int numeroPasajeros) {
        super.idAtraccion = idAtraccion;
        super.nombreAtraccion = nombreAtraccion;
        super.precioAtraccion = precioAtraccion;
        super.edadMinima = edadMinima;
        this.alturaMinima = alturaMinima;
        this.numeroPasajeros = numeroPasajeros;
    }

// SETTERS & GETTERS DE LA CLASE MECANICA    
    
    public int getAlturaMinima() {
        return alturaMinima;
    }

    public void setAlturaMinima(int alturaMinima) {
        this.alturaMinima = alturaMinima;
    }

    public int getNumeroPasajeros() {
        return numeroPasajeros;
    }

    public void setNumeroPasajeros(int numeroPsajeros) {
        this.numeroPasajeros = numeroPsajeros;
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
