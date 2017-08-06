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
 *
 * @author pablo
 */
public class Cuenta {
    String codCuenta, cedula, tipo, estado;
    float saldo;
    final Conexion cnx = new Conexion();

    public Cuenta(String cod_cuenta, String cedula, String tipo, String estado, float saldo) {
        this.codCuenta = cod_cuenta;
        this.cedula = cedula;
        this.tipo = tipo;
        this.estado = estado;
        this.saldo = saldo;
    }

    public Cuenta() {

    }

    final public String getCodCuenta() {
        return codCuenta;
    }

    final public void setCodCuenta(final String cod_cuenta) {
        this.codCuenta = cod_cuenta;
    }

    final public String getCedula() {
        return cedula;
    }

    final public void setCedula(final String cedula) {
        this.cedula = cedula;
    }

    final public String getTipo() {
        return tipo;
    }

    final public void setTipo(final String tipo) {
        this.tipo = tipo;
    }

    final public String getEstado() {
        return estado;
    }

    final public void setEstado(final String estado) {
        this.estado = estado;
    }

    final public float getSaldo() {
        return saldo;
    }

    final public void setSaldo(final float saldo) {
        this.saldo = saldo;
    }

    final public int ingresarCu(final Cuenta cuent) {
        int valor = 0;
        final Connection con = cnx.getConexion();
        try {
            CallableStatement sentencia;
            sentencia = con.prepareCall("INSERT INTO `software`.`cuenta` "
                    + "(`cod_cuenta`, `cedula`, `tipo`, `saldo`, `estado`) VALUES (?,?,?,?,?);");
            sentencia.setString(1, cuent.getCodCuenta());
            sentencia.setString(2, cuent.getCedula());
            sentencia.setString(3, cuent.getTipo());
            sentencia.setString(4, Float.toString(cuent.getSaldo()));
            sentencia.setString(5, cuent.getEstado());
            valor = sentencia.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex); }
        try { con.close(); }
        catch (SQLException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex); }
        return valor;
    }

    final public ArrayList<Cuenta> buscarCuenta(final String cuent) {
        final ArrayList<Cuenta> cuentArray = new ArrayList<Cuenta>();
        try {
            final Connection con = cnx.getConexion();
            final Statement statement = con.createStatement();
            final ResultSet result = statement.executeQuery("SELECT * FROM cuenta WHERE cedula='"+cuent+"';");
            while (result.next()) {
                final Cuenta cuen = new Cuenta();
                cuen.setCodCuenta(result.getString("cod_cuenta"));
                cuen.setCedula(result.getString("cedula"));
                cuen.setTipo(result.getString("tipo"));
                cuen.setSaldo(Float.parseFloat(result.getString("saldo")));
                cuen.setEstado(result.getString("estado"));
                cuentArray.add(cuen);
            }
            try { con.close(); }
            catch (SQLException ex) {
                Logger.getLogger(Usuarios.class.getName()).log(Level.SEVERE, null, ex); }
        } catch (SQLException ex) {
            Logger.getLogger(Usuarios.class.getName()).log(Level.SEVERE, null, ex); }
        return cuentArray;
    }

    final public ArrayList<Cuenta> buscarCuenta(final String cedul, final String codCuent) {
        final ArrayList<Cuenta> cuentArray = new ArrayList<Cuenta>();
        try {
            final Connection con = cnx.getConexion();
            final Statement statement = con.createStatement();
            final ResultSet result = statement.executeQuery("SELECT * FROM cuenta WHERE cedula='"+cedul+"' and cod_cuenta='"+codCuent+"';");
            while (result.next()){
                final Cuenta cuen = new Cuenta();
                cuen.setCodCuenta(result.getString("cod_cuenta"));
                cuen.setCedula(result.getString("cedula"));
                cuen.setTipo(result.getString("tipo"));
                cuen.setSaldo(Float.parseFloat(result.getString("saldo")));
                cuen.setEstado(result.getString("estado"));
                cuentArray.add(cuen);
            }
            try { con.close(); }
            catch (SQLException ex) {
                Logger.getLogger(Usuarios.class.getName()).log(Level.SEVERE, null, ex); }
        }
        catch (SQLException ex) {
            Logger.getLogger(Usuarios.class.getName()).log(Level.SEVERE, null, ex); }
        return cuentArray;
    }

    final public int actualizarCu(final String cuent, final float sald) {
        int valor = 0;
        final Connection con = cnx.getConexion();
        try {
            CallableStatement sentencia;
            sentencia = con.prepareCall(" UPDATE `software`.`cuenta` SET `saldo`=? WHERE `cod_cuenta`=?;");
            sentencia.setString(1, Float.toString(sald));
            sentencia.setString(2, cuent);
            valor = sentencia.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        try { con.close(); }
        catch (SQLException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex); }
        return valor;
    }

    final public int actualizarCuenta(final String cuent) {
        int valor = 0;
        final Connection con = cnx.getConexion();
        try {
            CallableStatement sentencia;
            sentencia = con.prepareCall("UPDATE `software`.`cuenta` SET `estado`='des' WHERE `cod_cuenta`=?;");
            sentencia.setString(1, cuent);
            valor = sentencia.executeUpdate();
        }
        catch (SQLException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex); }
        try { con.close(); }
        catch (SQLException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex); }
        return valor;
    }
}
