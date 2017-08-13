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
 * furnished to do so, subject to t he following conditions:
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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jonathan
 */
public class Prestamo {

    private String codigoCuenta;
    private double saldo;
    private double saldoMaximo;
    private int plaso;
    final private Conexion cnx = new Conexion();

    public Prestamo() {
    }

    public Prestamo(String codigoCuenta) {
        this.codigoCuenta = codigoCuenta;
    }

    public String getCodigoCuenta() {
        return codigoCuenta;
    }

    public void setCodigoCuenta(String codigoCuenta) {
        this.codigoCuenta = codigoCuenta;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public double getSaldoMaximo() {
        return saldoMaximo;
    }

    public void setSaldoMaximo(double saldoMaximo) {
        this.saldoMaximo = saldoMaximo;
    }

    public int getPlaso() {
        return plaso;
    }

    public void setPlaso(int plaso) {
        this.plaso = plaso;
    }

    /*
    *Metodo  para iniciar los saldos basico y maximo que se pueden prestar.
    *@param codigoCuenta codigo de la cuenta ligada al prestamo.
    */
    final public Prestamo inicialisarSaldos(final String codigoCuenta) {
        Prestamo prestamo = new Prestamo(codigoCuenta);
        try {
            final Connection con = cnx.getConexion();
            final Statement statement = con.createStatement();
            final ResultSet result = statement.executeQuery("SELECT (avg(saldo)*3) as saldoMaximo FROM movimiento WHERE COD_CUENTA like '" + codigoCuenta + "' and to_days(fecha) between (to_days(now()) - 90) and to_days(now()) ;");
            while (result.next()) {
                prestamo.setSaldoMaximo(result.getDouble(1));
            }
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(Usuarios.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Usuarios.class.getName()).log(Level.SEVERE, null, ex);
        }
        return prestamo;
    }

    /**
     * Metodo para calcular el pago mensual.
     * @param montoPrestamo Saldo del prestamo
     * @param interesMensual interes de cada mes
     * @param numeroMeses numero de cuotas
     * @return devuelve el pago de cada mes
     */
    public double PagoMensual(double montoPrestamo, double interesMensual, int numeroMeses) {
        return montoPrestamo * interesMensual
                / (1 - 1 / Math.pow(1 + interesMensual, numeroMeses));
    }
}
