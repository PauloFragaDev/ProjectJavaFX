/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dao;

import java.util.List;

/**
 *
 * @author Pol
 */
public interface DAO<T, k> {
    public void insert(T t);
    public void update(T t);
    public void delete(T t);
    public T selectById(k id);
    public List<T> selectAll();
}
