package main.java.com.mycompany.employeeloginui;


/**
 *
 * @author Jonalyn Ramos
 */
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EmployeeReview {

    public EmployeeReview() {
        JFrame f = new JFrame("Review Employee");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(700, 400);

        String[] columnNames = {"Employee ID","Request", "Name", "Department", "Weekly Task Status", "Rating", "Approved"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable employeeTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(employeeTable);

        f.getContentPane().setBackground(Color.DARK_GRAY);
        tableScrollPane.getViewport().setBackground(Color.LIGHT_GRAY);
        employeeTable.setBackground(Color.LIGHT_GRAY);
        

        JButton reviewButton = new JButton("Review");
        reviewButton.setBackground(Color.BLUE);
        reviewButton.setForeground(Color.WHITE);
        reviewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = employeeTable.getSelectedRow();
                if (selectedRow != -1) {
                    Object[] rowData = new Object[employeeTable.getColumnCount()];
                    for (int i = 0; i < employeeTable.getColumnCount(); i++) {
                        rowData[i] = employeeTable.getValueAt(selectedRow, i);
                    }
                    openReviewFrame(tableModel, selectedRow, rowData);
                } else {
                    JOptionPane.showMessageDialog(f, "Please select a row first.");
                }
            }
        });

        JButton approveButton = new JButton("Approve");
        approveButton.setBackground(Color.BLUE);
        approveButton.setForeground(Color.WHITE);
        approveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = employeeTable.getSelectedRow();
                if (selectedRow != -1) {
                    String employeeID = (String) employeeTable.getValueAt(selectedRow, 0);
                    tableModel.setValueAt("Yes", selectedRow, 6); // Set the "Approved" column to "Yes"
                    updateApprovalStatus(employeeID, true); // Update the approval status in the database
                } else {
                    JOptionPane.showMessageDialog(f, "Please select a row first.");
                }
            }
        });      
                
        JButton backButton = new JButton("Back");
        backButton.setBackground(Color.BLUE);
        backButton.setForeground(Color.WHITE);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                f.dispose();
                new MainMenu();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(reviewButton);
        buttonPanel.add(approveButton);
        buttonPanel.add(backButton);

        f.add(tableScrollPane, BorderLayout.CENTER);
        f.add(buttonPanel, BorderLayout.SOUTH);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
        
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/finalsoop", "root", "Rachelle");
            Statement st = conn.createStatement();
            String query = "SELECT * FROM tbl_employeereview";
            ResultSet rs = st.executeQuery(query);
           
            while(rs.next()){
                String employeeID = rs.getString("employeeID");
                String request = rs.getString("request");
                String name = rs.getString("name");
                String department = rs.getString("department");
                String weeklystat = rs.getString("weeklystat");
                String rating = rs.getString("rating");
                boolean approveBoolean = rs.getBoolean("approve");
                String approve = approveBoolean ? "Yes" : "No";
                
                tableModel.addRow(new Object[]{employeeID, request, name, department,weeklystat,rating,approve});
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
    
    private void updateApprovalStatus(String employeeID, boolean approve) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/finalsoop", "root", "Rachelle");
            String updateQuery = "UPDATE tbl_employeereview SET approve = ? WHERE employeeID = ?";
            PreparedStatement pstmt = conn.prepareStatement(updateQuery);
            pstmt.setBoolean(1, approve);
            pstmt.setString(2, employeeID);

            pstmt.executeUpdate();
            pstmt.close();
            conn.close();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    private static void openReviewFrame(DefaultTableModel model, int rowIndex, Object[] rowData) {
        JFrame reviewFrame = new JFrame("Review and Edit");
        reviewFrame.setSize(350, 300);

        JPanel reviewPanel = new JPanel(new GridLayout(rowData.length + 2, 2));
        reviewPanel.setBackground(Color.WHITE);
        JTextField[] textFields = new JTextField[rowData.length];

        for (int i = 0; i < rowData.length; i++) {
            reviewPanel.add(new JLabel(model.getColumnName(i)));
            textFields[i] = new JTextField(rowData[i].toString());
            reviewPanel.add(textFields[i]);
        }

        JButton submitButton = new JButton("Submit");
        submitButton.setBackground(Color.BLUE);
        submitButton.setForeground(Color.WHITE);
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/finalsoop", "root", "Rachelle");
                    String updateQuery = "UPDATE tbl_employeereview SET request = ?, name = ?, department = ?, weeklystat = ?, rating = ?, approve = ? WHERE employeeID = ?";
                    PreparedStatement pstmt = conn.prepareStatement(updateQuery);

                    pstmt.setString(1, textFields[1].getText()); // request
                    pstmt.setString(2, textFields[2].getText()); // name
                    pstmt.setString(3, textFields[3].getText()); // department
                    pstmt.setString(4, textFields[4].getText()); // weeklystat
                    pstmt.setString(5, textFields[5].getText()); // rating
                    pstmt.setBoolean(6, textFields[6].getText().equalsIgnoreCase("Yes")); // approve
                    pstmt.setString(7, textFields[0].getText()); // employeeID

                    pstmt.executeUpdate();
                    pstmt.close();
                    conn.close();

                    for (int i = 0; i < textFields.length; i++) {
                        model.setValueAt(textFields[i].getText(), rowIndex, i);
                    }
                } catch (SQLException ex) {
                    System.out.println(ex);
                }
                reviewFrame.dispose();
            }
        });



        JButton backButton = new JButton("Back");
        backButton.setBackground(Color.BLUE);
        backButton.setForeground(Color.WHITE);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reviewFrame.dispose();
                new EmployeeReview();
            }
        });

        reviewPanel.add(submitButton);
        reviewPanel.add(backButton);
        reviewFrame.add(reviewPanel);
        reviewFrame.setLocationRelativeTo(null);
        reviewFrame.setVisible(true);
        
    } 
}