
package main.java.com.mycompany.employeeloginui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;


public class Listtt{
    
    private Connection connection;
    private boolean e;
            
    public Listtt(){

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
        frame.add(btnAdd);
        frame.add(btnDelete);
        frame.add(btnBack);
        frame.add(btnNext);
       

        frame.setSize(900, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Employee List Record");

        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String employeeName = textEmployeeName.getText();
                String age = textAge.getText();
                String birthdate = textBirthdate.getText();
                String address = textAddress.getText();
                String email = textEmail.getText();
                String contactNumber = textContactNumber.getText();
                
                try {
                    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/finalsoop", "root", "Rachelle");
                    String query = "INSERT INTO tbl_employeerecord (EmployeeName, Age, Birthdate, Address, Email, ContactNumber) VALUES (?, ?, ?, ?, ?, ?)";
                    PreparedStatement pst = conn.prepareStatement(query);
                    pst.setString(1, employeeName);
                    pst.setString(2, age);
                    pst.setString(3, birthdate);
                    pst.setString(4, address);
                    pst.setString(5, email);
                    pst.setString(6, contactNumber);
                    pst.executeUpdate();
                    
                    model.addRow(new Object[]{employeeName, age, birthdate, address, email, contactNumber});
                } catch (SQLException ex) {
                    System.out.println(e);
                }
            }
        });     
             

        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int i = table.getSelectedRow();
                if (i >= 0) {
                    String employeeName = model.getValueAt(i, 0).toString();
                    try {
                        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/finalsoop", "root", "Rachelle");
                        String query = "DELETE FROM tbl_employeerecord WHERE EmployeeName = ?";
                        PreparedStatement pst = conn.prepareStatement(query);
                        pst.setString(1, employeeName);
                        pst.executeUpdate();
                        
                        model.removeRow(i);
                    } catch (SQLException ex) {
                        System.out.println(e);
                    }
                } else {
                    System.out.println("No row selected for deletion!");
                }
            }
        });
        
        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            frame.dispose();
            new EmployeeList();
            }
        });
        
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/finalsoop", "root", "Rachelle");
            Statement st = conn.createStatement();
            String query = "SELECT * FROM tbl_employeerecord";
            ResultSet rs = st.executeQuery(query);
           
            while(rs.next()){
                String employeename = rs.getString("EmployeeName");
                String Age = rs.getString("Age");
                String Birthdate = rs.getString("Birthdate");
                String Address = rs.getString("Address");
                String Email = rs.getString("Email");
                String contactnum = rs.getString("ContactNumber");
                
                model.addRow(new Object[]{employeename, Age, Birthdate, Address,Email,contactnum});
            }
        } catch (SQLException ex) {
            System.out.println(e);
        }
        
        frame.setSize(900, 600);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
             