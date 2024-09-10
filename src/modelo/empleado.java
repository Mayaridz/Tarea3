/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;
import java.awt.HeadlessException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import javax.swing.table.DefaultTableModel;
import javax.swing.DefaultComboBoxModel;
/**
 *
 * @author Marllory Diaz
 */
public class empleado extends persona {
    Conexion cn;
   private String codigo;
   private int idpuesto;
   private int id;
    public empleado (){}

    public empleado (String codigo,int id, int idpuesto, String nombres, String apellidos, String direccion, String telefono, String fecha_nacimiento) {
        super(nombres, apellidos, direccion, telefono, fecha_nacimiento);
        this.id = id;
        this.codigo = codigo;
        this.idpuesto = idpuesto;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdpuesto() {
        return idpuesto;
    }

    public void setIdpuesto(int idpuesto) {
        this.idpuesto = idpuesto;
    }
    
    
    @Override
    public DefaultTableModel leer (){
        DefaultTableModel tabla = new DefaultTableModel();
        try{
            cn = new Conexion();
            cn.abrir_conexion();
            String query;
             query = "Select e.id_empleado as id, e.codigo, e.nombres, e.apellidos, e.direccion, e.telefono, e.fecha_nacimiento, concat(p.id_puesto,') ',p.puesto) as puesto FROM  empleados as e inner join puestos as p on e.id_puesto = p.id_puesto ;";
        ResultSet consulta = cn.conexionBD.createStatement().executeQuery(query);
        
        String encabezado[] = {"Id", "Codigo", "Nombres", "Apellidos", "Direccion", "Telefono", "Fecha_Nacimiento", "Puesto"};
        tabla.setColumnIdentifiers(encabezado);
        
        String datos[] = new String[8];
        while(consulta.next()){
            datos[0] = consulta.getString("id");
            datos[1] = consulta.getString("codigo");
            datos[2] = consulta.getString("nombres");
            datos[3] = consulta.getString("apellidos");
            datos[4] = consulta.getString("direccion");
            datos[5] = consulta.getString("telefono");
            datos[6] = consulta.getString("fecha_nacimiento");
            datos[7] = consulta.getString("puesto");
            tabla.addRow(datos);
        }
        cn.cerrar_conexion();
        }catch(SQLException ex){
          System.out.println("Error: " + ex.getMessage());
}
       return tabla; 
}
public DefaultComboBoxModel leer_puesto(){
    DefaultComboBoxModel  combo = new DefaultComboBoxModel ();
    try{
       cn = new Conexion ();
       cn.abrir_conexion();
       String query;
       query = "SELECT id_puesto as id, puesto from puestos";
       ResultSet consulta =  cn.conexionBD.createStatement().executeQuery(query);
       combo.addElement("0) Elija Puesto");
       

                  while (consulta.next())
                    {            
                      combo.addElement(consulta.getString("id")+") "+consulta.getString("puesto"));
                    }
              cn.cerrar_conexion();
              
       
    }catch(SQLException ex){
        System.out.println("Error: " + ex.getMessage() );
    }
    return combo;
}

 @Override
    public void agregar()
    {
        if (getIdpuesto() == 0) {
        JOptionPane.showMessageDialog(null, "Debe seleccionar un puesto válido", "Error", JOptionPane.ERROR_MESSAGE);
        return;  }
        try{
            PreparedStatement parametro;
            cn = new Conexion();
            cn.abrir_conexion();
            String query = "INSERT INTO empleados(codigo, nombres, apellidos, direccion, telefono, fecha_nacimiento, id_puesto) VALUES (?,?,?,?,?,?,?);";
            parametro = (PreparedStatement) cn.conexionBD.prepareStatement(query);
            parametro.setString(1, getCodigo());
            parametro.setString(2, getNombres());
            parametro.setString(3, getApellidos());
            parametro.setString(4, getDireccion());
            parametro.setString(5, getTelefono());
            parametro.setString(6, getFecha_nacimiento());
            parametro.setInt(7, getIdpuesto());
            
            int executar = parametro.executeUpdate();
            cn.cerrar_conexion();
            JOptionPane.showMessageDialog(null, Integer.toString(executar) + "Registro Ingresado", "Agregar", JOptionPane.INFORMATION_MESSAGE);
        }catch(HeadlessException |SQLException ex){
        System.out.println("Error: " + ex.getMessage());
        }
    }

        @Override
        
   public void actualizar(){
        if (getIdpuesto() == 0) {
        JOptionPane.showMessageDialog(null, "Debe seleccionar un puesto válido", "Error", JOptionPane.ERROR_MESSAGE);
        return;  
        }
       try{
            PreparedStatement parametro;
             cn = new Conexion();
            cn.abrir_conexion();
            String query = "UPDATE empleados set codigo = ?, nombres = ?, apellidos = ?, direccion = ?, telefono = ?, fecha_nacimiento = ?, id_puesto=? where id_empleado= ?";
            parametro = (PreparedStatement) cn.conexionBD.prepareStatement(query);
            parametro.setString(1, getCodigo());
            parametro.setString(2, getNombres());
            parametro.setString(3, getApellidos());
            parametro.setString(4, getDireccion());
            parametro.setString(5, getTelefono());
            parametro.setString(6, getFecha_nacimiento());
            parametro.setInt(7, getIdpuesto());
            parametro.setInt (8, getId());
            int executar = parametro.executeUpdate();
            cn.cerrar_conexion();
            JOptionPane.showMessageDialog(null, Integer.toString(executar) + "Registro Actualizaddo", "Actualizar", JOptionPane.INFORMATION_MESSAGE);
            
            
        }catch(HeadlessException |SQLException ex){
        System.out.println("Error: " + ex.getMessage());
        }
   }
    @Override
   public void eliminar(){
   try{
            PreparedStatement parametro;
             cn = new Conexion();
            cn.abrir_conexion();
            String query = "DELETE FROM  empleados where id_empleado = ?;";
            parametro = (PreparedStatement) cn.conexionBD.prepareStatement(query);
            parametro.setInt(1,getId());
            int executar = parametro.executeUpdate();

            JOptionPane.showMessageDialog(null, Integer.toString(executar) + "Registro eliminado", "Eliminar", JOptionPane.INFORMATION_MESSAGE);
            cn.cerrar_conexion();
            
        }catch(HeadlessException |SQLException ex){
        System.out.println("Error: " + ex.getMessage());
        }
   }
    
   
}


