package com.mycompany.certiapp;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class CConexion {
    private Connection conn = null;
    private final String url = "jdbc:postgresql://localhost:5432/CertifiLaboral_db";
    private final String user = "admin";
    private final String password = "adolfo2022";

    // Método para establecer conexión
    public Connection establecerConexion() {
        try {
            Class.forName("org.postgresql.Driver"); // Cargar el driver
            conn = DriverManager.getConnection(url, user, password);
            JOptionPane.showMessageDialog(null, "Conexión exitosa", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Error: No se encontró el driver JDBC", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error de conexión: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return conn;
    }

    // Método para cerrar la conexión
    public void cerrar() {
        if (conn != null) {
            try {
                conn.close();
                JOptionPane.showMessageDialog(null, "Desconexión exitosa", "Desconexión", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error al cerrar la conexión: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
