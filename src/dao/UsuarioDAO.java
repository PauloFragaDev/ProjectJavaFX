/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.io.Serializable;
import java.util.List;
import model.Usuario;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author Pol
 */

public class UsuarioDAO extends DBConnection implements DAO<Usuario, Integer>, Serializable  {

// SENTENCIAS PARA LA TABLA USUARIO DE LA BASE DE DATOS    
    
    private final String INSERT = "INSERT INTO USUARIO(nombreUsuario,contrasenya,dni,administrador) VALUES(?,?,?,?)";
    private final String UPDATE = "UPDATE USUARIO SET nombreUsuario=?, contrasenya=?, dni=?, administrador=? WHERE idUsuario=?";
    private final String DELETE = "DELETE FROM USUARIO WHERE idUsuario=?";
    private final String DELETEALL = "DELETE FROM USUARIO";
    private final String DELETEBYUSUARIO = "DELETE FROM TIQUET WHERE idUsuario=?";
    private final String SELECTBYID = "SELECT * FROM USUARIO WHERE idUsuario=?";
    private final String SELECTALL = "SELECT * FROM USUARIO";
    private final String INSERTID = "INSERT INTO USUARIO VALUES(?,?,?,?,?)";
    
// METODOS QUE EJECUTARAN LAS SENTENCIAS DE LA BASE DE DATOS    
    
    public void insertId(Usuario t) {
        try {
            connect();
            PreparedStatement ps = connection.prepareStatement(INSERTID);
            ps.setInt(1, t.getId());
            ps.setString(2, t.getNombreUsuario());
            ps.setString(3, t.getContraseña());
            ps.setString(4, t.getDni());
            ps.setBoolean(5, t.isAdministrador());
            if (ps.executeUpdate() != 0) {
                System.out.println("Usuario añadido correctamente en la DDBB.");
            }
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insert(Usuario t) {
        try {
            connect();
            PreparedStatement ps = connection.prepareStatement(INSERT);
            ps.setString(1, t.getNombreUsuario());
            ps.setString(2, t.getContraseña());
            ps.setString(3, t.getDni());
            ps.setBoolean(4, t.isAdministrador());
            if (ps.executeUpdate() != 0) {
                int id = ps.getGeneratedKeys().getInt(1);
                t.setId(id);
                System.out.println("Usuario añadido correctamente en la DDBB.");
            }
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Usuario t) {
        try {
            connect();
            PreparedStatement ps = connection.prepareStatement(UPDATE);
            ps.setString(1, t.getNombreUsuario());
            ps.setString(2, t.getContraseña());
            ps.setString(3, t.getDni());
            ps.setBoolean(4, t.isAdministrador());
            ps.setInt(5, t.getId());
            if (ps.executeUpdate() != 0) {
                System.out.println("Usuario modificado correctamente en la DDBB.");
            }
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Usuario t) {
        try {
            connect();
            PreparedStatement ps = connection.prepareStatement(DELETE);
            ps.setInt(1, t.getId());
            if (ps.executeUpdate() != 0) {
                System.out.println("Usuario eliminado correctamente de la DDBB.");
            }
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void deleteAll() {
        try {
            connect();
            PreparedStatement ps = connection.prepareStatement(DELETEALL);
            if (ps.executeUpdate() != 0) {
                System.out.println("Usuario eliminado correctamente de la DDBB.");
            }
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void deleteTiquetsUsuario(Usuario t) {
        try {
            connect();
            PreparedStatement ps = connection.prepareStatement(DELETEBYUSUARIO);
            ps.setInt(1, t.getId());
            if (ps.executeUpdate() != 0) {
                System.out.println("Usuario eliminado correctamente de la DDBB.");
            }
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Usuario selectById(Integer id) {
        Usuario usuario = null;
        try {
            connect();
            PreparedStatement ps = connection.prepareStatement(SELECTBYID);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int idUsuario = rs.getInt("idUsuario");
                String nombre = rs.getString("nombreUsuario");
                String contraseña = rs.getString("contrasenya");
                String dni = rs.getString("dni");
                boolean administrador = rs.getBoolean("administrador");
                usuario = new Usuario(nombre, contraseña, dni, administrador);
                usuario.setId(idUsuario);
            }
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return usuario;
    }

    @Override
    public List<Usuario> selectAll() {
        ArrayList<Usuario> usuarios = new ArrayList<>();
        try {
            connect();
            PreparedStatement ps = connection.prepareStatement(SELECTALL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int idUsuario = rs.getInt("idUsuario");
                String nombre = rs.getString("nombreUsuario");
                String contraseña = rs.getString("contrasenya");
                String dni = rs.getString("dni");
                boolean administrador = rs.getBoolean("administrador");
                Usuario usuario = new Usuario(nombre, contraseña, dni, administrador);
                usuario.setId(idUsuario);
                usuarios.add(usuario);
            }
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return usuarios;
    }

}
