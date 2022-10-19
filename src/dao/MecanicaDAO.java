/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.io.Serializable;
import java.util.List;
import model.Mecanica;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author polfr
 */
public class MecanicaDAO extends DBConnection implements DAO<Mecanica, Integer>, Serializable  {

    // SENTENCIAS PARA LA TABLA MECANICA DE LA BASE DE DATOS
    
    private final String INSERT = "INSERT INTO MECANICA(nombreAtraccion,precioAtraccion,edadMinima,alturaMinima,numeroPasajeros) VALUES(?,?,?,?,?)";
    private final String UPDATE = "UPDATE MECANICA SET nombreAtraccion=?, precioAtraccion=?, edadMinima=?, alturaMinima=?, numeroPasajeros=? WHERE idAtraccion=?";
    private final String DELETE = "DELETE FROM MECANICA WHERE idAtraccion=?";
    private final String DELETEALL = "DELETE FROM MECANICA";
    private final String SELECTBYID = "SELECT * FROM MECANICA WHERE idAtraccion=?";
    private final String SELECTALL = "SELECT * FROM MECANICA";
    private final String INSERTID = "INSERT INTO MECANICA VALUES(?,?,?,?,?,?)";
    
    // METODOS QUE EJECUTARAN LAS SENTENCIAS DE LA BASE DE DATOS 
    
    public void insertId(Mecanica t) {
        try {
            connect();
            PreparedStatement ps = connection.prepareStatement(INSERTID);
            ps.setInt(1, t.getIdAtraccion());
            ps.setString(2, t.getNombreAtraccion());
            ps.setInt(3, t.getPrecioAtraccion());
            ps.setInt(4, t.getEdadMinima());
            ps.setInt(5, t.getAlturaMinima());
            ps.setInt(6, t.getNumeroPasajeros());
            if (ps.executeUpdate() != 0) {
                System.out.println("Atraccion Mecanica añadida correctamente en la DDBB.");
            }
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insert(Mecanica t) {
        try {
            connect();
            PreparedStatement ps = connection.prepareStatement(INSERT);
            ps.setString(1, t.getNombreAtraccion());
            ps.setInt(2, t.getPrecioAtraccion());
            ps.setInt(3, t.getEdadMinima());
            ps.setInt(4, t.getAlturaMinima());
            ps.setInt(5, t.getNumeroPasajeros());
            if (ps.executeUpdate() != 0) {
                int id = ps.getGeneratedKeys().getInt(1);
                t.setIdAtraccion(id);
                System.out.println("Atraccion Mecanica añadida correctamente en la DDBB.");
            }
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Mecanica t) {
        try {
            connect();
            PreparedStatement ps = connection.prepareStatement(UPDATE);
            ps.setString(1, t.getNombreAtraccion());
            ps.setInt(2, t.getPrecioAtraccion());
            ps.setInt(3, t.getEdadMinima());
            ps.setInt(4, t.getAlturaMinima());
            ps.setInt(5, t.getNumeroPasajeros());
            ps.setInt(6, t.getIdAtraccion());
            if (ps.executeUpdate() != 0) {
                System.out.println("Atraccion Mecanica modificada correctamente de la DDBB.");
            }
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Mecanica t) {
        try {
            connect();
            PreparedStatement ps = connection.prepareStatement(DELETE);
            ps.setInt(1, t.getIdAtraccion());
            if (ps.executeUpdate() != 0) {
                System.out.println("Atraccion Mecanica eliminada correctamente de la DDBB.");
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
                System.out.println("Tabla Mecanica Eliminada");
            }
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Mecanica selectById(Integer id) {
        Mecanica mecanica = null;
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
                int alturaMinima = rs.getInt("alturaMinima");
                int numeroPasajeros = rs.getInt("numeroPasajeros");
                mecanica = new Mecanica(nombreAtraccion, precioAtraccion, edadMinima, alturaMinima, numeroPasajeros);
                mecanica.setIdAtraccion(idAtraccion);
            }
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mecanica;
    }

    @Override
    public List<Mecanica> selectAll() {
        ArrayList<Mecanica> mecanicas = new ArrayList<>();
        try {
            connect();
            PreparedStatement ps = connection.prepareStatement(SELECTALL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int idAtraccion = rs.getInt("idAtraccion");
                String nombreAtraccion = rs.getString("nombreAtraccion");
                int precioAtraccion = rs.getInt("precioAtraccion");
                int edadMinima = rs.getInt("edadMinima");
                int alturaMinima = rs.getInt("alturaMinima");
                int numeroPasajeros = rs.getInt("numeroPasajeros");
                Mecanica mecanica = new Mecanica(nombreAtraccion, precioAtraccion, edadMinima, alturaMinima,
                        numeroPasajeros);
                mecanica.setIdAtraccion(idAtraccion);
                mecanicas.add(mecanica);
            }
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mecanicas;
    }

}
