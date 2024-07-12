import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class estudiante extends JFrame {
    private JTextField nom;
    private JTextField cedu;
    private JButton INSERTARButton;
    private JPanel Panel1;
    private JButton BUSQUEDA;
    private JTextArea resultado;
    private JTextField ceduBuscar;

    public estudiante() {
        super("Estudiante");
        setContentPane(Panel1);
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        INSERTARButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String url = "jdbc:mysql://localhost:3306/curso";
                String user = "root";
                String password = "";

                try (Connection conex = DriverManager.getConnection(url, user, password)) {
                    JOptionPane.showMessageDialog(null, "CONEXION EXITOSA :)");

                    String nombre = nom.getText();
                    String cedula = cedu.getText();

                    String SQL = "INSERT INTO estudiantes(nombre, cedula) values(?,?)";

                    PreparedStatement pst = conex.prepareStatement(SQL);
                    pst.setString(1, nombre);
                    pst.setString(2, cedula);

                    int rowsAffected = pst.executeUpdate();
                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(null, "Se ha registrado el estudiante");
                    } else {
                        JOptionPane.showMessageDialog(null, "No se ha registrado el estudiante", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    pst.close();
                    conex.close();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        BUSQUEDA.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String url="jdbc:mysql://localhost:3306/curso";
                String user="root";
                String password="";

                try (Connection conex = DriverManager.getConnection(url,user,password)){
                    JOptionPane.showMessageDialog(null,"CONEXION EXITOSA :)");
                    String cedulaBusqueda = ceduBuscar.getText();
                    String SQL = "select * from estudiantes where cedula = ?";

                        PreparedStatement pst = conex.prepareStatement(SQL);
                        pst.setString(1,cedulaBusqueda);
                        ResultSet rs = pst.executeQuery();

                        if(rs.next()){
                            String nombre = rs.getString("nombre");
                            String cedula = rs.getString("cedula");
                            resultado.setText("Nombre: "+ nombre + "\nCedula: "+ cedula);
                        }else{
                            resultado.setText("Estudiante no encontrado :("+ceduBuscar);
                        }
                        pst.close();
                        conex.close();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });
        };


}


