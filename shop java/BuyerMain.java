import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class BuyerMain extends JFrame {
    private ProductManager productManager;
    private ArrayList<Product> cart;
    private JTable productTable;
    private JTable cartTable;
    private JButton addToCartButton, checkoutButton;

    public BuyerMain() {
        productManager = new ProductManager();
        cart = new ArrayList<>();
        
        // Adding some sample products for testing
        productManager.addProduct(new Product("Beras", 10000, 50));
        productManager.addProduct(new Product("Gula", 12000, 40));
        productManager.addProduct(new Product("Minyak", 15000, 30));

        setTitle("Belanja Bahan Pokok");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        add(panel);

        // Tabel produk
        productTable = new JTable();
        JScrollPane productScrollPane = new JScrollPane(productTable);
        productScrollPane.setBounds(10, 10, 360, 300);
        panel.add(productScrollPane);

        // Tombol tambah ke keranjang
        addToCartButton = new JButton("Tambah ke Keranjang");
        addToCartButton.setBounds(380, 150, 200, 25);
        panel.add(addToCartButton);
        addToCartButton.addActionListener(new ActionListener() {
            @Override 
            public void actionPerformed(ActionEvent e) {
                int selectedRow = productTable.getSelectedRow();
                if (selectedRow != -1) {
                    String name = (String) productTable.getValueAt(selectedRow, 0);
                    Product product = productManager.findProductByName(name);
                    if (product != null && product.getStock() > 0) {
                        cart.add(new Product(product.getName(), product.getPrice(), 1));
                        product.setStock(product.getStock() - 1);
                        refreshTables();
                    } else {
                        JOptionPane.showMessageDialog(null, "Produk tidak tersedia atau stok habis");
                    }
                }
            }
        });

        // Tabel keranjang
        cartTable = new JTable();
        JScrollPane cartScrollPane = new JScrollPane(cartTable);
        cartScrollPane.setBounds(10, 320, 360, 200);
        panel.add(cartScrollPane);

        // Tombol checkout
        checkoutButton = new JButton("Checkout");
        checkoutButton.setBounds(380, 350, 200, 25);
        panel.add(checkoutButton);
        checkoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double total = 0;
                for (Product product : cart) {
                    total += product.getPrice();
                }
                JOptionPane.showMessageDialog(null, "Total Belanja: Rp " + total);
                cart.clear();
                refreshTables();
            }
        });

        refreshTables();
    }

    private void refreshTables() {
        // Memperbarui tabel produk
        String[] productColumnNames = {"Nama", "Harga", "Stok"};
        Object[][] productData = new Object[productManager.getProducts().size()][3];
        for (int i = 0; i < productManager.getProducts().size(); i++) {
            Product product = productManager.getProducts().get(i);
            productData[i][0] = product.getName();
            productData[i][1] = product.getPrice();
            productData[i][2] = product.getStock();
        }
        productTable.setModel(new DefaultTableModel(productData, productColumnNames));

        // Memperbarui tabel keranjang
        String[] cartColumnNames = {"Nama", "Harga"};
        Object[][] cartData = new Object[cart.size()][2];
        for (int i = 0; i < cart.size(); i++) {
            Product product = cart.get(i);
            cartData[i][0] = product.getName();
            cartData[i][1] = product.getPrice();
        }
        cartTable.setModel(new DefaultTableModel(cartData, cartColumnNames));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new BuyerMain().setVisible(true);
            }
        });
    }
}
