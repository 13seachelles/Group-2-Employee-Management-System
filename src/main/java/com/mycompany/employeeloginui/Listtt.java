package main.java.com.mycompany.employeeloginui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;


public class Listtt{
    
    private Connection connection;
            
    public Listtt(){
        
        // Establish the database connection
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/finalsoop", "root", "Rachelle");           
        } catch (SQLException e) {
            System.out.println("Error while connecting to the database: " + e.getMessage());
        }

        JFrame frame = new JFrame();
        JTable table = new JTable();

        Object[] columns = {"EmployeeName", "Age", "Birthdate", "Address", "Email", "ContactNumber"};
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(columns);
        table.setModel(model);

        table.setBackground(Color.blue);
        table.setForeground(Color.white);
        Font font = new Font("Times new roman", 1, 24);
        table.setRowHeight(40);

        JTextField textEmployeeName = new JTextField();
        JTextField textAge = new JTextField();
        JTextField textBirthdate = new JTextField();
        JTextField textAddress = new JTextField();
        JTextField textEmail = new JTextField();
        JTextField textContactNumber = new JTextField();

        textEmployeeName.setBounds(220, 220, 200, 25);
        textEmployeeName.setFont(new Font("Times new roman", Font.BOLD, 14));
        textAge.setBounds(220, 350, 200, 25);
        textAge.setFont(new Font("Times new roman", Font.BOLD, 14));
        textBirthdate.setBounds(220, 280, 200, 25);
        textBirthdate.setFont(new Font("Times new roman", Font.BOLD, 14));
        textAddress.setBounds(620, 220, 200, 25);
        textAddress.setFont(new Font("Times new roman", Font.BOLD, 14));
        textEmail.setBounds(620, 350, 200, 25);
        textEmail.setFont(new Font("Times new roman", Font.BOLD, 14));
        textContactNumber.setBounds(620, 280, 200, 25);
        textContactNumber.setFont(new Font("Times new roman", Font.BOLD, 14));

        JLabel lblEmployeeName = new JLabel("EmployeeName");
        lblEmployeeName.setBounds(50, 230, 200, 25);
        lblEmployeeName.setFont(new Font("Times new  roman", Font.PLAIN, 14));
        JLabel lblAge = new JLabel("Age");
        lblAge.setBounds(50, 350, 100, 25);
        lblAge.setFont(new Font("Times new  roman", Font.PLAIN, 14));
        JLabel lblBirthdate = new JLabel("Birthdate");
        lblBirthdate.setBounds(50, 290, 100, 25);
        lblBirthdate.setFont(new Font("Times new  roman", Font.PLAIN, 14));
        JLabel lblAddress = new JLabel("Address");
        lblAddress.setBounds(500, 230, 150, 25);
        lblAddress.setFont(new Font("Times new  roman", Font.PLAIN, 14));
        JLabel lblEmail = new JLabel("Email");
        lblEmail.setBounds(500, 350,200, 25);
        lblEmail.setFont(new Font("Times new  roman", Font.PLAIN, 14));
        JLabel lblContactNumber = new JLabel("ContactNumber");
        lblContactNumber.setBounds(500, 290, 100, 25);
        lblContactNumber.setFont(new Font("Times new  roman", Font.PLAIN, 14));

        JButton btnAdd = new JButton("Add");
        JButton btnDelete = new JButton("Delete");
        JButton btnBack = new JButton("Back");
        JButton btnNext = new JButton("Next");
        
        btnAdd.setBounds(50, 500, 100, 25);
        btnDelete.setBounds(225, 500, 100, 25);
        btnBack.setBounds(650,500,100,25);
        btnNext.setBounds(750, 500, 100, 25);
        
        JScrollPane pane = new JScrollPane(table);
        pane.setBounds(0, 0, 880, 200);

        frame.setLayout(null);

        frame.add(pane);
        
        frame.add(lblEmployeeName);
        frame.add(lblAge);
        frame.add(lblBirthdate);
        frame.add(lblAddress);
        frame.add(lblEmail);
        frame.add(lblContactNumber);

        frame.add(textEmployeeName);
        frame.add(textAge);
        frame.add(textBirthdate);
        frame.add(textAddress);
        frame.add(textEmail);
        frame.add(textContactNumber);

        frame.setSize(900, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Employee List Record");

        frame.add(btnAdd);
        frame.add(btnDelete);
        frame.add(btnBack);
        frame.add(btnNext);
        
        loadEmployeeRecords(model);

        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String[] data = new String[6];
                data[0] = textEmployeeName.getText();
                data[1] = textAge.getText();
                data[2] = textBirthdate.getText();
                data[3] = textAddress.getText();
                data[4] = textEmail.getText();
                data[5] = textContactNumber.getText();

                // Insert the new record into the database
                insertEmployeeRecord(data);

                // Add the new record to the table model
                model.addRow(data);
            }
        });
        
        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            frame.dispose();
            new EmployeeList();
            }
        });

        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int i = table.getSelectedRow();
                if (i >= 0) {
                    String employeeName = (String) model.getValueAt(i, 0);

                    deleteEmployeeRecord(employeeName);
  
                    model.removeRow(i);
                } else {
                    System.out.println("Delete Error: No row selected");
                }
            }
        });
        
        frame.setSize(900, 600);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    
    private void loadEmployeeRecords(DefaultTableModel model) {
        String query = "SELECT * FROM tbl_employeerecord";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                String employeename = resultSet.getString("employeename");
                String birthdate = resultSet.getString("birthdate");
                String age = resultSet.getString("age");
                String address = resultSet.getString("address");
                String contactnum = resultSet.getString("contactnum");
                String email = resultSet.getString("email");

                model.addRow(new String[]{employeename, age, birthdate, address, email, contactnum});
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    private void insertEmployeeRecord(String[] data) {
        String query = "INSERT INTO tbl_employeerecord (employeename, age, birthdate, address, email, contactnum) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            for (int i = 0; i < data.length; i++) {
                statement.setString(i + 1, data[i]);
            }
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    private void deleteEmployeeRecord(String employeeName) {
        String query = "DELETE FROM tbl_employeerecord WHERE employeename = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, employeeName);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}

