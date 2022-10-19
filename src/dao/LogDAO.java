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
import model.Log;

/**
 *
 * @author polfr
 */
public class LogDAO extends DBConnection implements DAO<Log, Integer>, Serializable {
    
    // SENTENCIAS PARA LA TABLA LOG DE LA BASE DE DATOS
    
    private final String INSERT = "INSERT INTO LOG(idUsuario,accion,descripcion) VALUES(?,?,?)";
    private final String UPDATE = "UPDATE LOG SET idUsuario=?, accion=?, descripcion=? WHERE idLog=?";
    private final String DELETE = "DELETE FROM LOG WHERE idLog=?";
    private final String DELETEALL = "DELETE FROM LOG";
    private final String SELECTBYID = "SELECT * FROM LOG WHERE idLog=?";
    private final String SELECTALL = "SELECT * FROM LOG";
    private final String INSERTID = "INSERT INTO LOG VALUES(?,?,?,?)";
    
    // METODOS QUE EJECUTARAN LAS SENTENCIAS DE LA BASE DE DATOS
    
    public void insertId(Log t) {
        try {
            connect();
            PreparedStatement ps = connection.prepareStatement(INSERTID);
            ps.setInt(1, t.getIdLog());
            ps.setInt(2, t.getIdUsuario());
            ps.setString(3, t.getAccion());
            ps.setString(4, t.getDescripcion());
            if (ps.executeUpdate() != 0) {
                System.out.println("Log añadido correctamente en la DDBB.");
            }
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void insert(Log t) {
        try {
            connect();
            PreparedStatement ps = connection.prepareStatement(INSERT);
            ps.setInt(1, t.getIdUsuario());
            ps.setString(2, t.getAccion());
            ps.setString(3, t.getDescripcion());
            if (ps.executeUpdate() != 0) {
                int id = ps.getGeneratedKeys().getInt(1);
                t.setIdLog(id);
                System.out.println("Log añadido correctamente en la DDBB.");
            }
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Log t) {
        try {
            connect();
            PreparedStatement ps = connection.prepareStatement(UPDATE);
            ps.setInt(1, t.getIdUsuario());
            ps.setString(2, t.getAccion());
            ps.setString(3, t.getDescripcion());
            ps.setInt(4, t.getIdLog());
            if (ps.executeUpdate() != 0) {
                System.out.println("Tiquet modificado correctamente en la DDBB.");
            }
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Log t) {
        try {
            connect();
            PreparedStatement ps = connection.prepareStatement(DELETE);
            ps.setInt(1, t.getIdLog());
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
                System.out.println("Tabla Log Eliminada");
            }
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public Log selectById(Integer id) {
        Log log = null;
        try {
            connect();
            PreparedStatement ps = connection.prepareStatement(SELECTBYID);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int idLog = rs.getInt("idLog");
                int idUsuario = rs.getInt("idUsuario");
                String accion = rs.getString("accion");
                String descripcion = rs.getString("descripcion");
                log = new Log(idUsuario, accion, descripcion);
                log.setIdLog(idLog);
            }
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return log;
    }

    @Override
    public List<Log> selectAll() {
        ArrayList<Log> logs = new ArrayList<>();
        try {
            connect();
            PreparedStatement ps = connection.prepareStatement(SELECTALL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int idLog = rs.getInt("idLog");
                int idUsuario = rs.getInt("idUsuario");
                String accion = rs.getString("accion");
                String descripcion = rs.getString("descripcion");
                Log log = new Log(idUsuario, accion, descripcion);
                log.setIdLog(idLog);
                logs.add(log);
            }
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return logs;
    }
    
}
