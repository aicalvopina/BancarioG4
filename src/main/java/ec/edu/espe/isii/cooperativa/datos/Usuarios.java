/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.isii.cooperativa.datos;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pablo
 */
public class Usuarios {
    String clave, nombre, contra;
    final Conexion cnx = new Conexion();

    public Usuarios() {
    }

    final public String getClave() {
        return clave;
    }

    final public void setClave(final String clave) {
        this.clave = clave;
    }

    final public String getNombre() {
        return nombre;
    }

    final public void setNombre(final String nombre) {
        this.nombre = nombre;
    }

    final public String getContra() {
        return contra;
    }

    final public void setContra(final String contra) {
        this.contra = contra;
    }
    
    final public boolean verificarUsuario(final String user, final String pass)
    {
        boolean aux = false;
        try {
            final Connection con = cnx.getConexion();
            final Statement statement = con.createStatement();
            final ResultSet result = statement.executeQuery("SELECT clave, nombre FROM usuario WHERE clave='"+
                    pass+"' and nombre='"+user+"';");
            while (result.next()){
                aux = true;
            }
            try {con.close();}
            catch (SQLException ex) {
                Logger.getLogger(Usuarios.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Usuarios.class.getName()).log(Level.SEVERE, null, ex);
        }
        return aux;
    }
    
}
