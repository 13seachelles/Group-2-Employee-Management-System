package main.java.com.mycompany.employeeloginui;


/**
 *
 * @author Jonalyn Ramos
 */

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

        // Adding "Request" column and sample requests
        String[] columnNames = {"Request", "Name", "Department", "Weekly Task Status", "Rating", "Approved"};
        Object[][] employeeData = {
            {"Request for leave", "Matthew", "IT", "Done Early", "100%", "No"},
            {"Request for budget increase", "Rachelle", "Marketing", "Done Early", "100%", "No"},
            {"Request for training", "Vanessa", "Sales", "Done Early", "100%", "No"},
            {"Request for new hires", "Jonalyn", "HR", "Done Early", "100%", "No"},
            {"Request for bonus", "Enzo", "Finance", "Done Early", "100%", "No"},
            {"Request for equipment upgrade", "Helaena", "Customer Service", "Done Early", "100%", "No"},
        };

        DefaultTableModel tableModel = new DefaultTableModel(employeeData, columnNames);
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
                    tableModel.setValueAt("Yes", selectedRow, 5); // Set the "Approved" column to "Yes"
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
        f.setVisible(true);
    }

    private static void openReviewFrame(DefaultTableModel model, int rowIndex, Object[] rowData) {
        JFrame reviewFrame = new JFrame("Review and Edit");
        reviewFrame.setSize(350, 300);

        JPanel reviewPanel = new JPanel(new GridLayout(rowData.length + 2, 2)); // Adjusted for additional button
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
                for (int i = 0; i < textFields.length; i++) {
                    model.setValueAt(textFields[i].getText(), rowIndex, i);
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
        reviewFrame.setVisible(true);
    }
}