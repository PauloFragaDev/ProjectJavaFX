/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Pol
 */

// CLASE USUARIO
public class Usuario implements Serializable{
    
// ATRIBUTOS QUE CONFORMAN LA CLASE USUARIO    
    private int idUsuario;
    private String nombreUsuario;
    private String contraseña;
    private String dni;
    private ArrayList<Tiquet> tiquets;
    private boolean administrador;

// CONSTRUCTORES DE LA CLASE USUARIO    
    
    public Usuario(){}
    
    public Usuario(String nombreUsuario, String contraseña, String dni, boolean administrador) {
        this.nombreUsuario = nombreUsuario;
        this.contraseña = contraseña;
        this.dni = dni;
        this.administrador = administrador;
    }
    
    public Usuario(int id, String nombreUsuario, String contraseña, String dni, boolean administrador) {
        this.idUsuario = id;
        this.nombreUsuario = nombreUsuario;
        this.contraseña = contraseña;
        this.dni = dni;
        this.administrador = administrador;
    }
    
    public Usuario(int id, String nombreUsuario, String contraseña, String dni, ArrayList<Tiquet> tiquets, boolean administrador) {
        this.idUsuario = id;
        this.nombreUsuario = nombreUsuario;
        this.contraseña = contraseña;
        this.dni = dni;
        this.tiquets = tiquets;
        this.administrador = administrador;
    }

// GETTERS & SETTERS DE LA CLASE USUARIO    
    
    public int getId() {
        return idUsuario;
    }

    public void setId(int id) {
        this.idUsuario = id;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombre) {
        this.nombreUsuario = nombre;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public ArrayList<Tiquet> getTiquets() {
        return tiquets;
    }

    public void setTiquets(ArrayList<Tiquet> tiquets) {
        this.tiquets = tiquets;
    }

    public boolean isAdministrador() {
        return administrador;
    }

    public void setAdministrador(boolean administrador) {
        this.administrador = administrador;
    }

// METODO TOSTRING DE LA CLASE USUARIO    
    
    @Override
    public String toString() {
        return nombreUsuario;
    }
    
    
}
