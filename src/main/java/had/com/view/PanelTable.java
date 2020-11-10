package had.com.view;

import had.com.MyHandle;
import had.com.model.DataTable;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PanelTable extends JPanel {

    private final Color colorBackground = Color.WHITE;
    private JTextField tfName;
    private JTextField tfValue;
    private JTable table ;

    public PanelTable(String title) {

        setBackground(colorBackground);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createTitledBorder(title));
        setBorder(BorderFactory.createCompoundBorder(
                getBorder(),
                BorderFactory.createEmptyBorder(30, 15, 15, 15)));
        // create JFrame and JTable
        table = new JTable();


        // create a table model and set a Column Identifiers to this model 
        Object[] columns = {"Thuộc tính", "Giá trị"};
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(columns);

        // set the model to the table
        table.setModel(model);
        table.setForeground(Color.decode("#4285f4"));

        table.setBackground(colorBackground);
        Font font = new Font(table.getFont().getName(), Font.PLAIN, 14);
        table.setFont(font);
        table.setRowHeight(30);


        // create JTextFields
        JPanel pnHandle = new JPanel();
        pnHandle.setBackground(Color.WHITE);
        pnHandle.setLayout(new GridBagLayout());

        JPanel pnThuocTinh = MyHandle.createTextFied("Tên", "tfPtAdd");
        tfName = (JTextField) pnThuocTinh.getComponent(1);
        JPanel pnValue = MyHandle.createTextFied("Giá trị", "tfPtValueAdd");
        tfValue = (JTextField) pnValue.getComponent(1);

        MyHandle.addItem(pnHandle, pnThuocTinh, 0, 0, 1, 1, GridBagConstraints.WEST);
        MyHandle.addItem(pnHandle, pnValue, 0, 1, 1, 1, GridBagConstraints.WEST);

        // create JButtons
        JPanel pnButton = new JPanel();
        pnButton.setBackground(colorBackground);
        pnButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 0, 0));
        pnButton.setLayout(new BoxLayout(pnButton, BoxLayout.Y_AXIS));
        pnButton.setSize(600, 30);
        JButton btnAdd = new JButton("Add");
        JButton btnDelete = new JButton("Delete");
        JButton btnUpdate = new JButton("Update");
        pnButton.add(btnAdd);
        pnButton.add(btnAdd);
        pnButton.add(btnAdd);

        JPanel pnBottom = new JPanel();
        pnBottom.setBackground(colorBackground);
        pnBottom.setLayout(new BorderLayout());

        // create JScrollPane
        JScrollPane pane = new JScrollPane(table);
        pane.setSize(880, 250);
        pane.setPreferredSize(new Dimension(pane.getWidth(), 250));

        //add pane
        add(pane);
        pnBottom.add(pnHandle, BorderLayout.WEST);
        pnBottom.add(pnButton, BorderLayout.CENTER);
        add(pnBottom);


        // add JButtons to the jframe
        pnButton.add(btnAdd);
        pnButton.add(btnDelete);
        pnButton.add(btnUpdate);

        // create an array of objects to set the row data
        Object[] row = new Object[4];

        // button add row
        btnAdd.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                row[0] = tfName.getText();
                row[1] = tfValue.getText();

                // add row to the model
                model.addRow(row);

                tfName.setText("");
                tfValue.setText("");
            }
        });

        // button remove row
        btnDelete.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                // i = the index of the selected row
                int i = table.getSelectedRow();
                if (i >= 0) {
                    // remove a row from jtable
                    model.removeRow(i);
                    tfName.setText("");
                    tfValue.setText("");
                } else {
                    System.out.println("Delete Error");
                }
            }
        });

        // get selected row data From table to textfields 
        table.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {

                // i = the index of the selected row
                int i = table.getSelectedRow();

                tfName.setText(model.getValueAt(i, 0).toString());
                tfValue.setText(model.getValueAt(i, 1).toString());

            }
        });

        // button update row
        btnUpdate.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                // i = the index of the selected row
                int i = table.getSelectedRow();

                if (i >= 0) {
                    model.setValueAt(tfName.getText(), i, 0);
                    model.setValueAt(tfValue.getText(), i, 1);
                    tfName.setText("");
                    tfValue.setText("");
                } else {
                    System.out.println("Update Error");
                }
            }
        });

        setSize(900, 300);

    }

    public List<DataTable> getValueOfTable(){
        List<DataTable> dataTableList = new ArrayList<>();
        int rowCount = table.getRowCount();
        int columnCount = table.getColumnCount();
        for (int j = 0; j  < rowCount; j++) {
            DataTable dataTable = new DataTable();
            dataTable.setName((String) table.getValueAt(j, 0));
            dataTable.setValue((String) table.getValueAt(j, 1));
            System.out.println(dataTable.toString());
            dataTableList.add(dataTable);
        }
        return dataTableList;
    }
}
