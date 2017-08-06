/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.isii.cooperativa.datos;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Date;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pablo
 */
public class Movimiento {
    int codMovimiento;
    String cuenta, tipo;
    Date fecha;
    float monto, saldo;
    final Conexion cnx = new Conexion();

    public Movimiento() {
    }

    public Movimiento(String cuenta, String tipo, Date fecha, float monto, float saldo) {
        this.codMovimiento = generarID();
        this.tipo = tipo;
        this.cuenta = cuenta;
        this.fecha = fecha;
        this.monto = monto;
        this.saldo = saldo;
    }

    final private int generarID() {
        int aux;
        Random rnd = new Random();
        aux = rnd.nextInt(9000);
        return aux;
    }

    final public int getCodMovimiento() {
        return codMovimiento;
    }

    final public void setCodMovimiento(final int codMovimiento) {
        this.codMovimiento = codMovimiento;
    }

    final public String getTipo() {
        return tipo;
    }

    final public void setTipo(final String tipo) {
        this.tipo = tipo;
    }

    final public Date getFecha() {
        return fecha;
    }

    final public void setFecha(final Date fecha) {
        this.fecha = fecha;
    }

    final public float getMonto() {
        return monto;
    }

    final public void setMonto(final float monto) {
        this.monto = monto;
    }

    final public float getSaldo() {
        return saldo;
    }

    final public void setSaldo(final float saldo) {
        this.saldo = saldo;
    }

    final public String getCuenta() {
        return cuenta;
    }

    final public void setCuenta(final String cuenta) {
        this.cuenta = cuenta;
    }

    final public int ingresarMovimiento(final Movimiento move){
        int valor = 0;
        final Connection con = cnx.getConexion();
        final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final String fechaCadena = format.format(move.getFecha());
        try {
            CallableStatement sentencia;
            sentencia = con.prepareCall("INSERT INTO `software`.`movimiento` (`cod_movimiento`, "
                    + "`Cuenta`, `tipo`, `fecha`, `monto`, `saldo`) VALUES (?,?,?,?,?,?);");
            sentencia.setString(1, Integer.toString(move.getCodMovimiento()));
            sentencia.setString(2, move.getCuenta());
            sentencia.setString(3, move.getTipo());
            sentencia.setString(4, fechaCadena);
            sentencia.setString(5, Float.toString(move.getMonto()));
            sentencia.setString(6, Float.toString(move.getSaldo()));
            valor = sentencia.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex); }
        try { con.close(); }
        catch (SQLException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex); }
        return valor;
    }

    final public ArrayList<Movimiento> buscarMovimiento(final String cuent) {
        final ArrayList<Movimiento> moveArray = new ArrayList<Movimiento>();
        final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            final Connection con = cnx.getConexion();
            final Statement statement = con.createStatement();
            final ResultSet result = statement.executeQuery("SELECT * FROM movimiento WHERE cuenta = '"+cuent+"';");
            while (result.next())
            {
                final Movimiento move = new Movimiento();
                move.setCodMovimiento(Integer.parseInt(result.getString("cod_movimiento")));
                move.setCuenta(result.getString("cuenta"));
                move.setTipo(result.getString("tipo"));
                try { move.setFecha(format.parse(result.getString("fecha"))); }
                catch (ParseException ex) {
                    Logger.getLogger(Movimiento.class.getName()).log(Level.SEVERE, null, ex); }
                move.setSaldo(Float.parseFloat(result.getString("saldo")));
                move.setMonto(Float.parseFloat(result.getString("monto")));
                moveArray.add(move);
            }
            try { con.close(); }
            catch (SQLException ex) {
                Logger.getLogger(Usuarios.class.getName()).log(Level.SEVERE, null, ex); }
        } catch (SQLException ex) {
            Logger.getLogger(Usuarios.class.getName()).log(Level.SEVERE, null, ex); }
        return moveArray;
    }

    final public ArrayList<Movimiento> buscarMovimientoFecha(final String cuent, final Date star, final Date end) {
        final ArrayList<Movimiento> moveArray = new ArrayList<Movimiento>();
        final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final String startFormat = format.format(star);
        final String endFormat = format.format(end);
        try {
            final Connection con = cnx.getConexion();
            final Statement statement = con.createStatement();
            final ResultSet result = statement.executeQuery("SELECT * FROM movimiento WHERE cuenta = '"+cuent
                    +"' and fecha  BETWEEN '"+startFormat+"' and '"+endFormat+"'");
            while (result.next()) {
                final Movimiento move = new Movimiento();
                move.setCodMovimiento(Integer.parseInt(result.getString("cod_movimiento")));
                move.setCuenta(result.getString("cuenta"));
                move.setTipo(result.getString("tipo"));
                try {
                    move.setFecha(format.parse(result.getString("fecha")));
                } catch (ParseException ex) {
                    Logger.getLogger(Movimiento.class.getName()).log(Level.SEVERE, null, ex);
                }
                move.setSaldo(Float.parseFloat(result.getString("saldo")));
                move.setMonto(Float.parseFloat(result.getString("monto")));
                moveArray.add(move);
            }
            try { con.close(); }
            catch (SQLException ex) {
                Logger.getLogger(Usuarios.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Usuarios.class.getName()).log(Level.SEVERE, null, ex);
        }
        return moveArray;
    }
}
