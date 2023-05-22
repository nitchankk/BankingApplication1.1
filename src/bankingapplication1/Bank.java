/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bankingapplication1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Student
 */
public class Bank {
    private String name;
    
    public Bank(){
    }
    public Bank(String name){
        this.name = name;
    }
    
    public void listAccounts(){
        Connection connection = BankingConnection.connect();
        Statement statement;
        try {
            statement = connection.createStatement();
            String sql = "SELECT * FROM account";
            ResultSet results = statement.executeQuery(sql);
            
            while (results.next()){
                System.out.println(results.getString(1)+" "+results.getString(2)+" "+
                        results.getString(3)+" "+results.getString(4));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Bank.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void openAccount(Account account){
        Connection connection = BankingConnection.connect();
        String sql = "INSERT INTO account (accNumber,accName,accBalance,accType) VALUES(?,?,?,?)";
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, account.getAccountNumber());
            preparedStatement.setString(2, account.getAccountName());
            preparedStatement.setDouble(3, account.getBalance());
            preparedStatement.setString(4, account.getAccountType());
            preparedStatement.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(Bank.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void closeAccount(Account accout){
        Connection connection = BankingConnection.connect();
        String sql = "DELETE FROM account WHERE accNumber=?";
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, accout.getAccountNumber());
            preparedStatement.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(Bank.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void depositMoney(Account account, double amount){
        account.deposit(amount);
        Connection connection = BankingConnection.connect();
        String sql = "UPDATE account SET accBalance=? WHERE accNumber=?";
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDouble(1, account.getBalance());
            preparedStatement.setInt(2, account.getAccountNumber());
            System.out.println("Balance: " + account.getBalance());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Bank.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void withdrawMoney(Account account, double amount){
        account.withdraw(amount);
        Connection connection = BankingConnection.connect();
        String sql = "UPDATE account SET accBalance=? WHERE accNumber=?";
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDouble(1, account.getBalance());
            preparedStatement.setInt(2, account.getAccountNumber());
            preparedStatement.executeUpdate();
            System.out.println("Balance: " + account.getBalance());
        } catch (SQLException ex) {
            Logger.getLogger(Bank.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Account getAccount(int accountNumber){
        Connection connection = BankingConnection.connect();
        String sql = "SELECT * FROM account WHERE accNumber=?";
        PreparedStatement preparedStatement;
        Account account = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, accountNumber);
            ResultSet result = preparedStatement.executeQuery();
            
            result.next();
            String accName = result.getString(2);
            double balance = result.getDouble(3);
            String accType = result.getString(4);
            if (accType.equals("SavingsAccount")){
                account = new SavingsAccount(accountNumber, accName, balance);
            }
            else if (accType.equals("CurrentAccount")){
                account = new CurrentAccount(accountNumber, accName, balance);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Bank.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return account;
    }

    void closeAccount(int number) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    void depositMoney(int number, double amount) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    void withdrawMoney(int number, double amount) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
