/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.com.mycompany.employeeloginui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class ReportandAnalyticsUI {

    private Connection connection;
            
    public ReportandAnalyticsUI() {
      
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/finalsoop", "root", "Rachelle");           
        } catch (SQLException e) {
            System.out.println("Error while connecting to the database: " + e.getMessage());
        }

        //  main frame
        JFrame frame = new JFrame("Report and Analytics");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(900, 600);

        Color lightBlue = new Color(204, 204, 255);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(lightBlue);

        JLabel label1 = new JLabel("Report and Analytics", JLabel.CENTER);
        label1.setFont(new Font("Sans Serif", Font.BOLD, 20));
        mainPanel.add(label1, BorderLayout.NORTH);

        //tabbed pane
        JTabbedPane tabbedPane = new JTabbedPane();

        //Financial Performance tab
        String[] finperfcolumns = {"Type", "Revenue", "Profit", "ProfitMargin"};
        DefaultTableModel finperfModel = new DefaultTableModel(null, finperfcolumns);
        JTable financialperftable = new JTable(finperfModel);
        JScrollPane finperfScrollPane = new JScrollPane(financialperftable);

        JPanel finperfPanel = new JPanel(new BorderLayout());
        finperfPanel.add(finperfScrollPane, BorderLayout.CENTER);

        JPanel finperfButtonPanel = new JPanel();
        JButton finperfAddButton = new JButton("Add");
        JButton finperfDeleteButton = new JButton("Delete");
        JButton finperfBackButton = new JButton("Back");
        finperfButtonPanel.add(finperfAddButton);
        finperfButtonPanel.add(finperfDeleteButton);
        finperfButtonPanel.add(finperfBackButton);

        finperfPanel.add(finperfButtonPanel, BorderLayout.SOUTH);
        tabbedPane.addTab("Financial Performance", finperfPanel);
        
        loadFinancialPerformanceData(finperfModel);

        finperfAddButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] newData = new String[finperfcolumns.length];
                for (int i = 0; i < finperfcolumns.length; i++) {
                    String value = JOptionPane.showInputDialog(frame, "Enter " + finperfcolumns[i]);
                    if (value != null && !value.trim().isEmpty()) {
                        newData[i] = value;
                    } else {
                        JOptionPane.showMessageDialog(frame, "Please enter a value for " + finperfcolumns[i]);
                        return;
                    }
                }
                finperfModel.addRow(newData); 
                insertFinancialPerformanceData(newData); 
            }
        });

        finperfDeleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = financialperftable.getSelectedRow();
                if (selectedRow != -1) {
                    String typeValue = (String) financialperftable.getValueAt(selectedRow, 0);
                    deleteFinancialPerformanceData(typeValue);
                    finperfModel.removeRow(selectedRow);
                } else {
                    JOptionPane.showMessageDialog(frame, "Please select a row to delete.");
                }
            }
        });
        
        finperfBackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new MainMenu();
            }
        });

        //Sales tab
        String[] salesColumns = {"Week", "TotalSales", "AveragePerf"};
        DefaultTableModel salesModel = new DefaultTableModel(null, salesColumns);
        JTable salesTable = new JTable(salesModel);
        JScrollPane salesScrollPane = new JScrollPane(salesTable);

        JPanel salesPanel = new JPanel(new BorderLayout());
        salesPanel.add(salesScrollPane, BorderLayout.CENTER);

        JPanel salesButtonPanel = new JPanel();
        JButton salesAddButton = new JButton("Add");
        JButton salesDeleteButton = new JButton("Delete");
        JButton salesBackButton = new JButton("Back");
        salesButtonPanel.add(salesAddButton);
        salesButtonPanel.add(salesDeleteButton);
        salesButtonPanel.add(salesBackButton);
        
        salesPanel.add(salesButtonPanel, BorderLayout.SOUTH);
        tabbedPane.addTab("Sales", salesPanel);
   
        loadSalesData(salesModel);

        salesAddButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] newData = new String[salesColumns.length];
                for (int i = 0; i < salesColumns.length; i++) {
                    String value = JOptionPane.showInputDialog(frame, "Enter " + salesColumns[i]);
                    if (value != null && !value.trim().isEmpty()) {
                        newData[i] = value;
                    } else {
                        JOptionPane.showMessageDialog(frame, "Please enter a value for " + salesColumns[i]);
                        return;
                    }
                }
                salesModel.addRow(newData); 
                insertSalesData(newData); 
            }
        });

        salesDeleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = salesTable.getSelectedRow();
                if (selectedRow != -1) {
                    String weekValue = (String) salesTable.getValueAt(selectedRow, 0);
                    deleteSalesData(weekValue);
                    salesModel.removeRow(selectedRow);
                } else {
                    JOptionPane.showMessageDialog(frame, "Please select a row to delete.");
                }
            }
        });
        
        salesBackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new MainMenu();
            }
        });
        
        //Employee Information tab
        String[] empInfoColumns = {"TotalEmployees", "EGrowthRate", "ETurnoverRate", "EAverageTenure"};
        DefaultTableModel empInfoModel = new DefaultTableModel(null, empInfoColumns);
        JTable empInfoTable = new JTable(empInfoModel);
        JScrollPane empInfoScrollPane = new JScrollPane(empInfoTable);

        JPanel empInfoPanel = new JPanel(new BorderLayout());
        empInfoPanel.add(empInfoScrollPane, BorderLayout.CENTER);

        JPanel empInfoButtonPanel = new JPanel();
        JButton empInfoAddButton = new JButton("Add");
        JButton empInfoDeleteButton = new JButton("Delete");
        JButton empInfoBackButton = new JButton("Back");
        empInfoButtonPanel.add(empInfoAddButton);
        empInfoButtonPanel.add(empInfoDeleteButton);
        empInfoButtonPanel.add(empInfoBackButton);

        empInfoPanel.add(empInfoButtonPanel, BorderLayout.SOUTH);
        tabbedPane.addTab("Employee Information", empInfoPanel);

        loadEmployeeInfoData(empInfoModel);

        empInfoAddButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] newData = new String[empInfoColumns.length];
                for (int i = 0; i < empInfoColumns.length; i++) {
                    String value = JOptionPane.showInputDialog(frame, "Enter " + empInfoColumns[i]);
                    if (value != null && !value.trim().isEmpty()) {
                        newData[i] = value;
                    } else {
                        JOptionPane.showMessageDialog(frame, "Please enter a value for " + empInfoColumns[i]);
                        return;
                    }
                }
                empInfoModel.addRow(newData); 
                insertEmployeeInfoData(newData); 
            }
        });

        empInfoDeleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = empInfoTable.getSelectedRow();
                if (selectedRow != -1) {
                    String totalEmployeesValue = (String) empInfoTable.getValueAt(selectedRow, 0);
                    deleteEmployeeInfoData(totalEmployeesValue);
                    empInfoModel.removeRow(selectedRow);
                } else {
                    JOptionPane.showMessageDialog(frame, "Please select a row to delete.");
                }
            }
        });
  
        empInfoBackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new MainMenu();
            }
        });

        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        frame.getContentPane().add(mainPanel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void loadFinancialPerformanceData(DefaultTableModel model) {
        String query = "SELECT * FROM financialperformance_tbl";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                String type = resultSet.getString("Type");
                String revenue = resultSet.getString("Revenue");
                String profit = resultSet.getString("Profit");
                String profitMargin = resultSet.getString("ProfitMargin");
                model.addRow(new String[]{type, revenue, profit, profitMargin});
            }
        } catch (SQLException e) {
            System.out.println("Error while loading Financial Performance data: " + e.getMessage());
        }
    }

    //insert new data into the financialperformance_tbl
    private void insertFinancialPerformanceData(String[] data) {
        String query = "INSERT INTO financialperformance_tbl (Type, Revenue, Profit, ProfitMargin) VALUES (?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            for (int i = 0; i < data.length; i++) {
                statement.setString(i + 1, data[i]);
            }
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error while inserting Financial Performance data: " + e.getMessage());
        }
    }

    //delete row
    private void deleteFinancialPerformanceData(String type) {
        String query = "DELETE FROM financialperformance_tbl WHERE Type = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, type);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error while deleting Financial Performance data: " + e.getMessage());
        }
    }

    private void loadSalesData(DefaultTableModel model) {
        String query = "SELECT * FROM sales_tbl";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                String week = resultSet.getString("Week");
                String totalSales = resultSet.getString("TotalSales");
                String averagePerf = resultSet.getString("AveragePerf");
                model.addRow(new String[]{week, totalSales, averagePerf});
            }
        } catch (SQLException e) {
            System.out.println("Error while loading Sales data: " + e.getMessage());
        }
    }

    // insert new data into the sales_tbl
    private void insertSalesData(String[] data) {
        String query = "INSERT INTO sales_tbl (Week, TotalSales, AveragePerf) VALUES (?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            for (int i = 0; i < data.length; i++) {
                statement.setString(i + 1, data[i]);
            }
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error while inserting Sales data: " + e.getMessage());
        }
    }
    //delete row
    private void deleteSalesData(String week) {
        String query = "DELETE FROM sales_tbl WHERE Week = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, week);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error while deleting Sales data: " + e.getMessage());
        }
    }

    private void loadEmployeeInfoData(DefaultTableModel model) {
        String query = "SELECT * FROM employeeinfo_tbl";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                String totalEmployees = resultSet.getString("TotalEmployees");
                String eGrowthRate = resultSet.getString("EGrowthRate");
                String eTurnoverRate = resultSet.getString("ETurnoverRate");
                String eAverageTenure = resultSet.getString("EAverageTenure");
                model.addRow(new String[]{totalEmployees, eGrowthRate, eTurnoverRate, eAverageTenure});
            }
        } catch (SQLException e) {
            System.out.println("Error while loading Employee Information data: " + e.getMessage());
        }
    }

    //insert new data into the employeeinfo_tbl
    private void insertEmployeeInfoData(String[] data) {
        String query = "INSERT INTO employeeinfo_tbl (TotalEmployees, EGrowthRate, ETurnoverRate, EAverageTenure) VALUES (?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            for (int i = 0; i < data.length; i++) {
                statement.setString(i + 1, data[i]);
            }
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error while inserting Employee Information data: " + e.getMessage());
        }
    }

    //delete row 
    private void deleteEmployeeInfoData(String totalEmployees) {
        String query = "DELETE FROM employeeinfo_tbl WHERE TotalEmployees = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, totalEmployees);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error while deleting Employee Information data: " + e.getMessage());
        }
    }

    
}
