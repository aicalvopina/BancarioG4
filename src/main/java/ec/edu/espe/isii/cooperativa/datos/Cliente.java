/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.isii.cooperativa.datos;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *Clase para majear los atibutos de los clientes de la cooperativa.
 * @author Pablo Guallichico
 * @author Mario Catota
 * @author Cristhian Arevalo
 */
public class Cliente {
    private String cedula, nombre;
    final private Conexion cnx = new Conexion();

    public Cliente(String cedula, String nombre) {
        this.cedula = cedula;
        this.nombre = nombre;
    }
    public Cliente() {

    }

    final public String getCedula() {
        return cedula;
    }

    final public void setCedula(final String cedula) {
        this.cedula = cedula;
    }

    final public String getNombre() {
        return nombre;
    }

    final public void setNombre(final String nombre) {
        this.nombre = nombre;
    }

    final public int ingresarCliente(final Cliente clien) {
        int valor = 0;
        final Connection con = cnx.getConexion();
        try{
            CallableStatement sentencia;
            sentencia = con.prepareCall("INSERT INTO `software`.`cliente` (`cedula`, `nombre`) VALUES (?,?);");
            sentencia.setString(1, clien.getCedula());
            sentencia.setString(2, clien.getNombre());
            valor = sentencia.executeUpdate();
        }catch (SQLException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        try { con.close(); }
        catch (SQLException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        return valor;
    }

    final public ArrayList<Cliente> buscarCliente(final String client) {
        final Cliente clienteNom = new Cliente();
        final ArrayList<Cliente> clienteArray = new ArrayList<>();
        try {
            final Connection con  = cnx.getConexion();
            final Statement statement = con.createStatement();
            final ResultSet result = statement.executeQuery("SELECT * FROM cliente WHERE cedula='"+client+"';");
            while (result.next()){
                clienteNom.setCedula(result.getString("cedula"));
                clienteNom.setNombre(result.getString("nombre"));
                clienteArray.add(clienteNom);
            }
            try { con.close(); }
            catch (SQLException ex) {
                Logger.getLogger(Usuarios.class.getName()).log(Level.SEVERE, null, ex); }
        }
        catch (SQLException ex) {
            Logger.getLogger(Usuarios.class.getName()).log(Level.SEVERE, null, ex); }
        return clienteArray;
    }

    final public boolean buscarClienteN(final String cliente) {
        boolean aux = false;
        try {
            final Connection con =  cnx.getConexion();
            final Statement statement = con.createStatement();
            final ResultSet result = statement.executeQuery("SELECT * FROM cliente WHERE cedula='"+cliente+"';");
            while (result.next())
            { aux = true; }
            try { con.close(); }
            catch (SQLException ex) {
                Logger.getLogger(Usuarios.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Usuarios.class.getName()).log(Level.SEVERE, null, ex); }
        return aux;
    }
}
