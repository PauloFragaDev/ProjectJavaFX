/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.io.Serializable;
import java.util.List;
import model.Puesto;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author polfr
 */
public class PuestoDAO extends DBConnection implements DAO<Puesto, Integer>, Serializable {
    
    // SENTENCIAS PARA LA TABLA PUESTO DE LA BASE DE DATOS 
    
    private final String INSERT = "INSERT INTO PUESTO(nombreAtraccion,precioAtraccion,edadMinima,premio) VALUES(?,?,?,?)";
    private final String UPDATE = "UPDATE PUESTO SET nombreAtraccion=?, precioAtraccion=?, edadMinima=?, premio=? WHERE idAtraccion=?";
    private final String DELETE = "DELETE FROM PUESTO WHERE idAtraccion=?";
    private final String DELETEALL = "DELETE FROM PUESTO";
    private final String SELECTBYID = "SELECT * FROM PUESTO WHERE idAtraccion=?";
    private final String SELECTALL = "SELECT * FROM PUESTO";
    private final String INSERTID = "INSERT INTO PUESTO VALUES(?,?,?,?,?)";
    
    // METODOS QUE EJECUTARAN LAS SENTENCIAS DE LA BASE DE DATOS 
    
    public void insertId(Puesto t) {
        try {
            connect();
            PreparedStatement ps = connection.prepareStatement(INSERTID);
            ps.setInt(1, t.getIdAtraccion());
            ps.setString(2, t.getNombreAtraccion());
            ps.setInt(3, t.getPrecioAtraccion());
            ps.setInt(4, t.getEdadMinima());
            ps.setString(5, t.getPremio());
            if (ps.executeUpdate() != 0) {
                System.out.println("Atraccion Puesto añadida correctamente en la DDBB.");
            }
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void insert(Puesto t) {
        try {
            connect();
            PreparedStatement ps = connection.prepareStatement(INSERT);
            ps.setString(1, t.getNombreAtraccion());
            ps.setInt(2, t.getPrecioAtraccion());
            ps.setInt(3, t.getEdadMinima());
            ps.setString(4, t.getPremio());
            if (ps.executeUpdate() != 0) {
                int id = ps.getGeneratedKeys().getInt(1);
                t.setIdAtraccion(id);
                System.out.println("Atraccion Puesto añadida correctamente en la DDBB.");
            }
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Puesto t) {
        try {
            connect();
            PreparedStatement ps = connection.prepareStatement(UPDATE);
            ps.setString(1, t.getNombreAtraccion());
            ps.setInt(2, t.getPrecioAtraccion());
            ps.setInt(3, t.getEdadMinima());
            ps.setString(4, t.getPremio());
            ps.setInt(5, t.getIdAtraccion());
            if (ps.executeUpdate() != 0) {
                System.out.println("Atraccion Puesto modificada correctamente de la DDBB.");
            }
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Puesto t) {
        try {
            connect();
            PreparedStatement ps = connection.prepareStatement(DELETE);
            ps.setInt(1, t.getIdAtraccion());
            if (ps.executeUpdate() != 0) {
                System.out.println("Atraccion Puesto eliminada correctamente de la DDBB.");
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
                System.out.println("Tabla Puesto Eliminada");
            }
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Puesto selectById(Integer id) {
        Puesto puesto = null;
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
                String premio = rs.getString("premio");
                puesto = new Puesto(nombreAtraccion, precioAtraccion, edadMinima, premio);
                puesto.setIdAtraccion(idAtraccion);
            }
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return puesto;
    }

    @Override
    public List<Puesto> selectAll() {
        ArrayList<Puesto> puestos = new ArrayList<>();
        try {
            connect();
            PreparedStatement ps = connection.prepareStatement(SELECTALL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int idAtraccion = rs.getInt("idAtraccion");
                String nombreAtraccion = rs.getString("nombreAtraccion");
                int precioAtraccion = rs.getInt("precioAtraccion");
                int edadMinima = rs.getInt("edadMinima");
                String premio = rs.getString("premio");
                Puesto puesto = new Puesto(nombreAtraccion, precioAtraccion, edadMinima, premio);
                puesto.setIdAtraccion(idAtraccion);
                puestos.add(puesto);
            }
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return puestos;
    }
    
}
