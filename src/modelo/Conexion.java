/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;
import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Home
 */
public class Conexion {
    public Connection conexionBD;
    public String bd = "db_empresa";
    public String urlConexion = String.format("jdbc:mysql://localhost:3306/%s", bd);
    public String usuario = "root";
    public String contraseña = "maryo";
    public String jdbc = "com.mysql.cj.jdbc.Driver";
    
    
 
    
    public void abrir_conexion(){
        try{
            Class.forName(jdbc);
            conexionBD = DriverManager.getConnection(urlConexion, usuario, contraseña);
            JOptionPane.showMessageDialog(null, "Conexion exitosa :)", "Exito", JOptionPane.INFORMATION_MESSAGE);
        }catch(HeadlessException | ClassNotFoundException | SQLException ex){
            System.out.println("Error: " + ex.getMessage());
        }
    }
    
    public void cerrar_conexion(){
        try{
            conexionBD.close();
        }catch(SQLException ex){
            System.out.println("Error: " + ex.getMessage());
        }
        }
    }

