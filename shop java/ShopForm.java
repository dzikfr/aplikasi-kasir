import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ShopForm extends javax.swing.JFrame {

    // Constructor
    public ShopForm() {
        initComponents();
        populateShopTable();
    }

    // Method untuk mengisi data di shopTable
    private void populateShopTable() {
        DefaultTableModel model = (DefaultTableModel) shopTable.getModel();
        model.addRow(new Object[]{"Product A", "10000", "10"});
        model.addRow(new Object[]{"Product B", "15000", "5"});
        model.addRow(new Object[]{"Product C", "20000", "7"});
    }

    // Method yang dipanggil saat tombol Checkout diklik
    private void checkoutButtonActionPerformed(java.awt.event.ActionEvent evt) {
        int selectedRow = shopTable.getSelectedRow();
        if (selectedRow != -1) {
            String productName = (String) shopTable.getValueAt(selectedRow, 0);
            String priceString = (String) shopTable.getValueAt(selectedRow, 1);
            int price = Integer.parseInt(priceString);

            DefaultTableModel checkoutModel = (DefaultTableModel) checkoutTable.getModel();
            checkoutModel.addRow(new Object[]{productName, price});
        }
    }

    // Method yang dipanggil saat tombol checkoutBtn diklik
    private void checkoutBtnActionPerformed(java.awt.event.ActionEvent evt) {
        String namaPembeli = namaPembeliField.getText();
        if (namaPembeli.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nama pembeli tidak boleh kosong", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        DefaultTableModel checkoutModel = (DefaultTableModel) checkoutTable.getModel();
        int rowCount = checkoutModel.getRowCount();
        if (rowCount == 0) {
            JOptionPane.showMessageDialog(this, "Keranjang belanja kosong", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int totalHarga = 0;
        for (int i = 0; i < rowCount; i++) {
            totalHarga += (int) checkoutModel.getValueAt(i, 1);
        }

        // Simpan data ke database
        try {
            Connection conn = getConnection();
            String sql = "INSERT INTO transactions (customer_name, total_price) VALUES (?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, namaPembeli);
            statement.setInt(2, totalHarga);
            statement.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error saving data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(this, "Pesanan berhasil, nama: " + namaPembeli + "\nTotal belanja: " + totalHarga);
    }

    // Method untuk mendapatkan koneksi ke database
    private Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/shop_db";
        String username = "root";
        String password = ""; // ganti dengan password MySQL Anda
        return DriverManager.getConnection(url, username, password);
    }

    // Method initComponents untuk inisialisasi komponen GUI
    @SuppressWarnings("unchecked")
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        shopTable = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        checkoutTable = new javax.swing.JTable();
        checkoutButton = new javax.swing.JButton();
        checkoutBtn = new javax.swing.JButton();
        namaPembeliField = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        shopTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nama Produk", "Harga", "Stok"
            }
        ));
        jScrollPane1.setViewportView(shopTable);

        checkoutTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nama Produk", "Harga"
            }
        ));
        jScrollPane2.setViewportView(checkoutTable);

        checkoutButton.setText("Add to Cart");
        checkoutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkoutButtonActionPerformed(evt);
            }
        });

        checkoutBtn.setText("Checkout");
        checkoutBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkoutBtnActionPerformed(evt);
            }
        });

        jLabel1.setText("Nama Pembeli:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 375, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(namaPembeliField))
                    .addComponent(checkoutButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(checkoutBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(namaPembeliField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(checkoutBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 275, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(checkoutButton)
                .addContainerGap())
        );

        pack();
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ShopForm().setVisible(true);
            }
        });
    }

    // Variables declaration
    private javax.swing.JButton checkoutButton;
    private javax.swing.JButton checkoutBtn;
    private javax.swing.JTable checkoutTable;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField namaPembeliField;
    private javax.swing.JTable shopTable;

}
    

