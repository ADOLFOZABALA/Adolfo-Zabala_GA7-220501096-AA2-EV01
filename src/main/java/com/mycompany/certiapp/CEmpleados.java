package com.mycompany.certiapp;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class CEmpleados {
    private int id;
    private String nombres;
    private String cargo;

    // Métodos Get y Set
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }

    public String getCargo() { return cargo; }
    public void setCargo(String cargo) { this.cargo = cargo; }
    
    public void limpiarCampos(JTextField paramId, JTextField paramNombres, JTextField paramCargo) {
    paramId.setText("");      // Limpia el campo ID
    paramNombres.setText(""); // Limpia el campo nombres
    paramCargo.setText("");   // Limpia el campo cargo
}

    // Método para mostrar empleados
    public void MostrarEmpleados(JTable paramTablaTotalEmpleados) {
        CConexion objetoConexion = new CConexion();
        DefaultTableModel modelo = new DefaultTableModel();

        modelo.addColumn("id");
        modelo.addColumn("nombres");
        modelo.addColumn("cargo");

        paramTablaTotalEmpleados.setModel(modelo);
        String sql = "SELECT * FROM empleados";

        String[] datos = new String[3];
        Statement st;

        try {
            st = objetoConexion.establecerConexion().createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                modelo.addRow(datos);
            }

            paramTablaTotalEmpleados.setModel(modelo);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar: " + e.getMessage());
        }
    }

    // Método para insertar empleado
    public void insertarEmpleado(JTextField paramNombres, JTextField paramCargo) {
        setNombres(paramNombres.getText());
        setCargo(paramCargo.getText());

        CConexion objetoConexion = new CConexion();
        String consulta = "INSERT INTO empleados (nombres, cargo) VALUES (?, ?);";

        try {
            CallableStatement cs = objetoConexion.establecerConexion().prepareCall(consulta);
            cs.setString(1, getNombres());
            cs.setString(2, getCargo());

            cs.execute();
            JOptionPane.showMessageDialog(null, "Insertado Correctamente");
            limpiarCampos(new JTextField(), paramNombres, paramCargo); // Llama a limpiarCampos()
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al insertar: " + e.getMessage());
        }
    }

    // Método para seleccionar empleado
    public void SeleccionarEmpleado(JTable paramTablaEmpleados, JTextField paramId, JTextField paramNombres, JTextField paramCargo) {
        try {
            int fila = paramTablaEmpleados.getSelectedRow();
            if (fila >= 0) {
                paramId.setText(paramTablaEmpleados.getValueAt(fila, 0).toString());
                paramNombres.setText(paramTablaEmpleados.getValueAt(fila, 1).toString());
                paramCargo.setText(paramTablaEmpleados.getValueAt(fila, 2).toString());
            } else {
                JOptionPane.showMessageDialog(null, "Fila no seleccionada");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    // Método para modificar empleado
  public void ModificarEmpleado(JTextField paramId, JTextField paramNombres, JTextField paramCargo) {
    setId(Integer.parseInt(paramId.getText()));  // Convertir ID a entero
    setNombres(paramNombres.getText());
    setCargo(paramCargo.getText());

    CConexion objetoConexion = new CConexion();
    String consulta = "UPDATE empleados SET nombres = ?, cargo = ? WHERE id = ?;";  // ID al final

    try {
        CallableStatement cs = objetoConexion.establecerConexion().prepareCall(consulta);
        cs.setString(1, getNombres());  // Primer parámetro es el nombre
        cs.setString(2, getCargo());    // Segundo parámetro es el cargo
        cs.setInt(3, getId());          // Tercer parámetro es el ID

        int rowsUpdated = cs.executeUpdate(); // Ejecuta la actualización

        if (rowsUpdated > 0) {
            JOptionPane.showMessageDialog(null, "Modificado Correctamente");
            limpiarCampos(paramId, paramNombres, paramCargo); // Llama a limpiarCampos()
            
        } else {
            JOptionPane.showMessageDialog(null, "No se encontró el empleado con ID: " + getId());
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error al modificar: " + e.getMessage());
    }
}
    // Método para eliminar empleado
    public void EliminarEmpleado(JTextField paramId) {
        setId(Integer.parseInt(paramId.getText()));

        CConexion objetoConexion = new CConexion();
        String consulta = "DELETE FROM empleados WHERE id = ?;";

        try {
            CallableStatement cs = objetoConexion.establecerConexion().prepareCall(consulta);
            cs.setInt(1, getId());
            
            cs.execute();
            JOptionPane.showMessageDialog(null, "Eliminado Correctamente");
            limpiarCampos(paramId, new JTextField(), new JTextField()); // Limpia solo el ID
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar: " + e.getMessage());
        }
    }
}