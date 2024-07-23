import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminDashboard extends JFrame {
    private ProductManager productManager;
    private JTable productTable;
    private JButton addButton, editButton, deleteButton;

    public AdminDashboard() {
        productManager = new ProductManager();
        setTitle("Admin Dashboard");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        add(panel);
        placeComponents(panel);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        // Tabel produk
        productTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(productTable);
        scrollPane.setBounds(10, 10, 560, 250);
        panel.add(scrollPane);

        // Tombol tambah
        addButton = new JButton("Tambah Produk");
        addButton.setBounds(10, 270, 150, 25);
        panel.add(addButton);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implementasi tambah produk
                String name = JOptionPane.showInputDialog("Masukkan Nama Produk:");
                double price = Double.parseDouble(JOptionPane.showInputDialog("Masukkan Harga Produk:"));
                int stock = Integer.parseInt(JOptionPane.showInputDialog("Masukkan Stok Produk:"));
                productManager.addProduct(new Product(name, price, stock));
                refreshTable();
            }
        });

        // Tombol edit
        editButton = new JButton("Edit Produk");
        editButton.setBounds(170, 270, 150, 25);
        panel.add(editButton);
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implementasi edit produk
                int selectedRow = productTable.getSelectedRow();
                if (selectedRow != -1) {
                    String name = (String) productTable.getValueAt(selectedRow, 0);
                    Product product = productManager.findProductByName(name);
                    if (product != null) {
                        String newName = JOptionPane.showInputDialog("Masukkan Nama Baru:", product.getName());
                        double newPrice = Double.parseDouble(JOptionPane.showInputDialog("Masukkan Harga Baru:", product.getPrice()));
                        int newStock = Integer.parseInt(JOptionPane.showInputDialog("Masukkan Stok Baru:", product.getStock()));
                        product.setName(newName);
                        product.setPrice(newPrice);
                        product.setStock(newStock);
                        refreshTable();
                    }
                }
            }
        });

        // Tombol hapus
        deleteButton = new JButton("Hapus Produk");
        deleteButton.setBounds(330, 270, 150, 25);
        panel.add(deleteButton);
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implementasi hapus produk
                int selectedRow = productTable.getSelectedRow();
                if (selectedRow != -1) {
                    String name = (String) productTable.getValueAt(selectedRow, 0);
                    Product product = productManager.findProductByName(name);
                    if (product != null) {
                        productManager.removeProduct(product);
                        refreshTable();
                    }
                }
            }
        });

        refreshTable();
    }

    private void refreshTable() {
        // Memperbarui tabel dengan data produk terbaru
        String[] columnNames = {"Nama", "Harga", "Stok"};
        Object[][] data = new Object[productManager.getProducts().size()][3];
        for (int i = 0; i < productManager.getProducts().size(); i++) {
            Product product = productManager.getProducts().get(i);
            data[i][0] = product.getName();
            data[i][1] = product.getPrice();
            data[i][2] = product.getStock();
        }
        productTable.setModel(new DefaultTableModel(data, columnNames));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new AdminDashboard().setVisible(true);
            }
        });
    }
}
