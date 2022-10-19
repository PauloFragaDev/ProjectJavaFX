/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Tiquet;

/**
 *
 * @author Pol
 */
public class TiquetDAO extends DBConnection implements DAO<Tiquet, Integer>, Serializable {
    
    // SENTENCIAS PARA LA TABLA TIQUET DE LA BASE DE DATOS 
    
    private final String INSERT = "INSERT INTO TIQUET(idUsuario,idAtraccion,fechaUso) VALUES(?,?,?)";
    private final String UPDATE = "UPDATE TIQUET SET idUsuario=?, idAtraccion=?, fechaUso=? WHERE idTiquet=?";
    private final String DELETE = "DELETE FROM TIQUET WHERE idTiquet=?";
    private final String DELETEALL = "DELETE FROM TIQUET";
    private final String SELECTBYID = "SELECT * FROM TIQUET WHERE idTiquet=?";
    private final String SELECTBYUSUARIO = "SELECT * FROM TIQUET WHERE idUsuario=?";
    private final String SELECTALL = "SELECT * FROM TIQUET";
    private final String INSERTID = "INSERT INTO TIQUET VALUES(?,?,?,?)";
    
    // METODOS QUE EJECUTARAN LAS SENTENCIAS DE LA BASE DE DATOS 
    
    public void insertId(Tiquet t) {
        try {
            connect();
            PreparedStatement ps = connection.prepareStatement(INSERTID);
            ps.setInt(1, t.getIdTiquet());
            ps.setInt(2, t.getIdUsuario());
            ps.setInt(3, t.getIdAtraccion());
            ps.setString(4, t.getFechaUso());
            if (ps.executeUpdate() != 0) {
                System.out.println("Tiquet añadido correctamente en la DDBB.");
            }
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void insert(Tiquet t) {
        try {
            connect();
            PreparedStatement ps = connection.prepareStatement(INSERT);
            ps.setInt(1, t.getIdUsuario());
            ps.setInt(2, t.getIdAtraccion());
            ps.setString(3, t.getFechaUso());
            if (ps.executeUpdate() != 0) {
                int id = ps.getGeneratedKeys().getInt(1);
                t.setIdTiquet(id);
                System.out.println("Tiquet añadido correctamente en la DDBB.");
            }
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Tiquet t) {
        try {
            connect();
            PreparedStatement ps = connection.prepareStatement(UPDATE);
            ps.setInt(1, t.getIdUsuario());
            ps.setInt(2, t.getIdAtraccion());
            ps.setString(3, t.getFechaUso());
            ps.setInt(4, t.getIdTiquet());
            if (ps.executeUpdate() != 0) {
                System.out.println("Tiquet modificado correctamente en la DDBB.");
            }
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Tiquet t) {
        try {
            connect();
            PreparedStatement ps = connection.prepareStatement(DELETE);
            ps.setInt(1, t.getIdTiquet());
            if (ps.executeUpdate() != 0) {
                System.out.println("Tiquet eliminado correctamente de la DDBB.");
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
                System.out.println("Tabla Tiquet Eliminada");
            }
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Tiquet selectById(Integer id) {
        Tiquet tiquet = null;
        try {
            connect();
            PreparedStatement ps = connection.prepareStatement(SELECTBYID);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int idTquet = rs.getInt("idTiquet");
                int idUsuario = rs.getInt("idUsuario");
                int idAtraccion = rs.getInt("idAtraccion");
                String fechaUso = rs.getString("fechaUso");
                tiquet = new Tiquet(idUsuario, idAtraccion, fechaUso);
                tiquet.setIdTiquet(idTquet);
            }
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tiquet;
    }

    public ArrayList selectByUsuario(Integer id) {
        ArrayList<Tiquet> tiquets = new ArrayList<>();
        try {
            connect();
            PreparedStatement ps = connection.prepareStatement(SELECTBYUSUARIO);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int idTiquet = rs.getInt("idTiquet");
                int idUsuario = rs.getInt("idUsuario");
                int idAtraccion = rs.getInt("idAtraccion");
                String fechaUso = rs.getString("fechaUso");
                Tiquet tiquet = new Tiquet(idUsuario, idAtraccion, fechaUso);
                tiquet.setIdTiquet(idTiquet);
                tiquets.add(tiquet);
            }
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tiquets;
    }

    @Override
    public List<Tiquet> selectAll() {
        ArrayList<Tiquet> tiquets = new ArrayList<>();
        try {
            connect();
            PreparedStatement ps = connection.prepareStatement(SELECTALL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int idTiquet = rs.getInt("idTiquet");
                int idUsuario = rs.getInt("idUsuario");
                int idAtraccion = rs.getInt("idAtraccion");
                String fechaUso = rs.getString("fechaUso");
                Tiquet tiquet = new Tiquet(idUsuario, idAtraccion, fechaUso);
                tiquet.setIdTiquet(idTiquet);
                tiquets.add(tiquet);
            }
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tiquets;
    }
    
}
