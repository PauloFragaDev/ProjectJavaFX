/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.io.Serializable;
import java.util.List;
import model.Atraccion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author Pol
 */
public class AtraccionDAO extends DBConnection implements DAO<Atraccion, Integer>, Serializable {

    // SENTENCIAS PARA LA TABLA ATRACCION DE LA BASE DE DATOS
    
    private final String INSERT = "INSERT INTO ATRACCION(nombreAtraccion,precioAtraccion,edadMinima) VALUES(?,?,?)";
    private final String UPDATE = "UPDATE ATRACCION SET nombreAtraccion=?, precioAtraccion=?, edadMinima=? WHERE idAtraccion=?";
    private final String DELETE = "DELETE FROM ATRACCION WHERE idAtraccion=?";
    private final String DELETEBYATRACCION = "DELETE FROM TIQUET WHERE idAtraccion=?";
    private final String DELETEALL = "DELETE FROM ATRACCION";
    private final String SELECTBYID = "SELECT * FROM ATRACCION WHERE idAtraccion=?";
    private final String SELECTALL = "SELECT * FROM ATRACCION";
    private final String INSERTID = "INSERT INTO ATRACCION VALUES(?,?,?,?)";
    
    // METODOS QUE EJECUTARAN LAS SENTENCIAS DE LA BASE DE DATOS
    
    public void insertId(Atraccion t) {
        try {
            connect();
            PreparedStatement ps = connection.prepareStatement(INSERTID);
            ps.setInt(1, t.getIdAtraccion());
            ps.setString(2, t.getNombreAtraccion());
            ps.setInt(3, t.getPrecioAtraccion());
            ps.setInt(4, t.getEdadMinima());
            if (ps.executeUpdate() != 0) {
                System.out.println("Atraccion añadida correctamente en la DDBB.");
            }
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insert(Atraccion t) {
        try {
            connect();
            PreparedStatement ps = connection.prepareStatement(INSERT);
            ps.setString(1, t.getNombreAtraccion());
            ps.setInt(2, t.getPrecioAtraccion());
            ps.setInt(3, t.getEdadMinima());
            if (ps.executeUpdate() != 0) {
                int id = ps.getGeneratedKeys().getInt(1);
                t.setIdAtraccion(id);
                System.out.println("Atraccion añadida correctamente en la DDBB.");
            }
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Atraccion t) {
        try {
            connect();
            PreparedStatement ps = connection.prepareStatement(UPDATE);
            ps.setString(1, t.getNombreAtraccion());
            ps.setInt(2, t.getPrecioAtraccion());
            ps.setInt(3, t.getEdadMinima());
            ps.setInt(4, t.getIdAtraccion());
            if (ps.executeUpdate() != 0) {
                System.out.println("Atraccion modificada correctamente de la DDBB.");
            }
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Atraccion t) {
        try {
            connect();
            PreparedStatement ps = connection.prepareStatement(DELETE);
            ps.setInt(1, t.getIdAtraccion());
            if (ps.executeUpdate() != 0) {
                System.out.println("Atraccion eliminada correctamente de la DDBB.");
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
                System.out.println("Tabla Atraccion Eliminada");
            }
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void deleteTiquetsAtraccion(Atraccion t) {
        try {
            connect();
            PreparedStatement ps = connection.prepareStatement(DELETEBYATRACCION);
            ps.setInt(1, t.getIdAtraccion());
            if (ps.executeUpdate() != 0) {
                System.out.println("Tiquets de la Atraccion eliminados correctamente de la DDBB.");
            }
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Atraccion selectById(Integer id) {
        Atraccion atraccion = null;
        try {
            connect();
            PreparedStatement ps = connection.prepareStatement(SELECTBYID);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int idAtraccion = rs.getInt("idAtraccion");
                String nombreAtraccion = rs.getString("nombreAtraccion");
                int precioAtraccion = rs.getInt("precioAtraccion");
                int edadMinima = rs.getInt("edadMinima");
                atraccion = new Atraccion(nombreAtraccion, precioAtraccion, edadMinima);
                atraccion.setIdAtraccion(idAtraccion);
            }
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return atraccion;
    }

    @Override
    public ArrayList<Atraccion> selectAll() {
        ArrayList<Atraccion> atracciones = new ArrayList<>();
        try {
            connect();
            PreparedStatement ps = connection.prepareStatement(SELECTALL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int idAtraccion = rs.getInt("idAtraccion");
                String nombreAtraccion = rs.getString("nombreAtraccion");
                int precioAtraccion = rs.getInt("precioAtraccion");
                int edadMinima = rs.getInt("edadMinima");
                Atraccion atraccion = new Atraccion(nombreAtraccion, precioAtraccion, edadMinima);
                atraccion.setIdAtraccion(idAtraccion);
                atracciones.add(atraccion);
            }
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return atracciones;
    }

}
