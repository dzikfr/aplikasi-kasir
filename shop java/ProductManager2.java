import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProductManager2 extends JFrame {
    private JTable productTable;
    private DefaultTableModel tableModel;
    private JTextField nameField, priceField, unitField, stockField;

    public ProductManager2() {
        // Set up the frame
        setTitle("Product Manager");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Set up the table
        String[] columnNames = {"Nama Produk", "Harga", "Satuan", "Stok"};
        tableModel = new DefaultTableModel(columnNames, 0);
        productTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(productTable);

        // Set up the input fields and labels
        JPanel inputPanel = new JPanel(new GridLayout(5, 2));
        inputPanel.add(new JLabel("Nama Produk:"));
        nameField = new JTextField();
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Harga:"));
        priceField = new JTextField();
        inputPanel.add(priceField);

        inputPanel.add(new JLabel("Satuan:"));
        unitField = new JTextField();
        inputPanel.add(unitField);

        inputPanel.add(new JLabel("Stok:"));
        stockField = new JTextField();
        inputPanel.add(stockField);

        // Set up the add button
        JButton addButton = new JButton("Tambah Produk");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addProduct();
            }
        });
        inputPanel.add(addButton);

        // Add components to the frame
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        getContentPane().add(inputPanel, BorderLayout.SOUTH);
    }

    private void addProduct() {
        String name = nameField.getText();
        String price = priceField.getText();
        String unit = unitField.getText();
        String stock = stockField.getText();

        if (!name.isEmpty() && !price.isEmpty() && !unit.isEmpty() && !stock.isEmpty()) {
            tableModel.addRow(new Object[]{name, price, unit, stock});
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "Semua bidang harus diisi", "Kesalahan", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearFields() {
        nameField.setText("");
        priceField.setText("");
        unitField.setText("");
        stockField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ProductManager2().setVisible(true);
            }
        });
    }
}
