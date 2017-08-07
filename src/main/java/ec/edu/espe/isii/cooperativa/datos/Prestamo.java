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
        
    /**
     * Metodos para la parte de la tabla de amortizacion
     */
     public static void printAmortizationSchedule(double principal, double annualInterestRate,
                                                 int numYears) {
        double interestPaid, principalPaid, newBalance;
        double monthlyInterestRate, monthlyPayment;
        int month;
        int numMonths = numYears * 12;

        // Output monthly payment and total payment
        monthlyInterestRate = annualInterestRate / 12;
        monthlyPayment      = monthlyPayment(principal, monthlyInterestRate, numYears);
        System.out.format("Monthly Payment: %8.2f%n", monthlyPayment);
        System.out.format("Total Payment:   %8.2f%n", monthlyPayment * numYears * 12);

        // Print the table header
        printTableHeader();

        for (month = 1; month <= numMonths; month++) {
            // Compute amount paid and new balance for each payment period
            interestPaid  = principal      * (monthlyInterestRate / 100);
            principalPaid = monthlyPayment - interestPaid;
            newBalance    = principal      - principalPaid;

            // Output the data item
            printScheduleItem(month, interestPaid, principalPaid, newBalance);

            // Update the balance
            principal = newBalance;
        }
    }

    /**
     * @param loanAmount
     * @param monthlyInterestRate in percent
     * @param numberOfYears
     * @return the amount of the monthly payment of the loan
     */
    static double monthlyPayment(double loanAmount, double monthlyInterestRate, int numberOfYears) {
        monthlyInterestRate /= 100;  // e.g. 5% => 0.05
        return loanAmount * monthlyInterestRate /
                ( 1 - 1 / Math.pow(1 + monthlyInterestRate, numberOfYears * 12) );
    }

    /**
     * Prints a table data of the amortization schedule as a table row.
     */
    private static void printScheduleItem(int month, double interestPaid,
                                          double principalPaid, double newBalance) {
        System.out.format("%8d%10.2f%10.2f%12.2f\n",
            month, interestPaid, principalPaid, newBalance);
    }

    /**
     * Prints the table header for the amortization schedule.
     */
    private static void printTableHeader() {
        System.out.println("\nAmortization schedule");
        for(int i = 0; i < 40; i++) {  // Draw a line
            System.out.print("-");
        }
        System.out.format("\n%8s%10s%10s%12s\n",
            "Payment#", "Interest", "Principal", "Balance");
        System.out.format("%8s%10s%10s%12s\n\n",
            "", "paid", "paid", "");
    }
    /**
     * Parte de la impresion de la tabla de pretsamos via consola
     */
    /**
     * public class Prueba {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // Prompt the user for loan amount, number of years and annual interest rate

        System.out.print("Loan Amount: ");
        double loanAmount = sc.nextDouble();

        System.out.print("Number of Years: ");
        int numYears = sc.nextInt();

        System.out.print("Annual Interest Rate (in %): ");
        double annualInterestRate = sc.nextDouble();

        System.out.println();  // Insert a new line

        // Print the amortization schedule

        printAmortizationSchedule(loanAmount, annualInterestRate, numYears);
    }
     */
}
