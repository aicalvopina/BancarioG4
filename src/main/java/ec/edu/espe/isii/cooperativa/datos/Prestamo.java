/*
 * The MIT License
 *
 * Copyright 2017 Adrián.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package ec.edu.espe.isii.cooperativa.datos;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jonathan
 */
public class Prestamo {
    private String cedula, saldo;
    final private Conexion cnx = new Conexion();

    public Prestamo() {
    }

    public Prestamo(String cedula, String saldo) {
        this.cedula = cedula;
        this.saldo = saldo;
    }

    public String getCedula() {
        return cedula;
    }

    public String getSaldo() {
        return saldo;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public void setSaldo(String saldo) {
        this.saldo = saldo;
    }
    
        final public ArrayList<Prestamo> buscarCliente(final String client) {
        final Prestamo prestamoVal = new Prestamo();
        final ArrayList<Prestamo> prestamoArray = new ArrayList<>();
        try {
            final Connection con  = cnx.getConexion();
            final Statement statement = con.createStatement();
            final ResultSet result = statement.executeQuery("SELECT cedula,saldo FROM cuenta WHERE cedula='"+client+"';");
            while (result.next()){
                prestamoVal.setCedula(result.getString("cedula"));
                prestamoVal.setSaldo(result.getString("saldo"));
                prestamoArray.add(prestamoVal);
            }
            try { con.close(); }
            catch (SQLException ex) {
                Logger.getLogger(Usuarios.class.getName()).log(Level.SEVERE, null, ex); }
        }
        catch (SQLException ex) {
            Logger.getLogger(Usuarios.class.getName()).log(Level.SEVERE, null, ex); }
        return prestamoArray;
    }
}
