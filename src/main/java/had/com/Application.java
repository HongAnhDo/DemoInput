package had.com;

import had.com.model.DataBody;
import had.com.view.SwingMenuDemo;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTbl;
import sun.java2d.SunGraphicsEnvironment;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Application extends JFrame implements ActionListener {
    private static final String SOURCE_FILE = "C:/Users/H/Desktop/had/HĐ_Cục-Tần-số-vô-tuyến-điện_MITSUBISHI_79c-0677.docx";
    private static final String OUTPUT_FILE = "C:/Users/H/Desktop/had/test.docx";
    // Components of the Form
    private Container c;
    private JLabel title;
    private JLabel name;
    private JTextField tname;
    private JLabel mno;
    private JTextField tmno;
    private JLabel gender;
    private JRadioButton male;
    private JRadioButton female;
    private ButtonGroup gengp;
    private JLabel dob;
    private JComboBox date;
    private JComboBox month;
    private JComboBox year;
    private JLabel add;
    private JTextArea tadd;
    private JCheckBox term;
    private JButton sub;
    private JButton reset;
    private JTextArea tout;
    private JLabel res;
    private JTextArea resadd;

    private JLabel headerLabel;
    private JLabel statusLabel;

    private void makeFrameFullSize(JFrame aFrame) {
        Rectangle r = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
        aFrame.setMaximizedBounds(r);
        aFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    public Application() {

        setTitle("Phần mềm demo");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        makeFrameFullSize(this);

        headerLabel = new JLabel("", JLabel.CENTER);
        statusLabel = new JLabel("", JLabel.CENTER);
        statusLabel.setSize(350, 100);

        add(headerLabel);
        add(statusLabel);

        JPanel newPanel = new JPanel();
        JScrollPane pane = new JScrollPane(newPanel);
        newPanel.setLayout(new GridBagLayout());
        addItem(newPanel, new JLabel("Name:"), 0, 0, 1, 1, GridBagConstraints.EAST);
        addItem(newPanel, new JLabel("Phone:"), 0, 1, 1, 1, GridBagConstraints.EAST);
        addItem(newPanel, new JLabel("Address:"), 0, 2, 1, 1, GridBagConstraints.EAST);
        JTextField name = new JTextField(20), phone = new JTextField(10), address = new JTextField(20);

        addItem(newPanel, name, 1, 0, 2, 1, GridBagConstraints.WEST);
        addItem(newPanel, phone, 1, 1, 1, 1, GridBagConstraints.WEST);
        addItem(newPanel, address, 1, 2, 2, 1, GridBagConstraints.WEST);
        add(pane);
        pack();


        //Fake
        List<DataBody> dataBodyList = DataBody.fakeDate();

        DataBody dataBodyFirst = dataBodyList.get(0);

        JPanel jPanelFirst = new JPanel();
        JLabel jLabelFirst = new JLabel();
        jLabelFirst.setText(dataBodyFirst.getTitle());
        JTextField jTextFieldFirst = new JTextField();

        jPanelFirst.add(jLabelFirst);
        jPanelFirst.add(jTextFieldFirst);
        jPanelFirst.setBorder(new EmptyBorder(10, 10, 10, 10));

        newPanel.add(jPanelFirst);

        JPanel jPanelSecond = new JPanel();
        jPanelSecond.setLayout(new GridLayout(0, 2));
        for (DataBody dataBody : dataBodyList) {
            JPanel jPanel = new JPanel();
            jPanel.setLayout(new GridLayout(2, 1));
            JLabel jLabel = new JLabel();
            jLabel.setText(dataBody.getTitle());

            JTextField jTextField = new JTextField();
            jPanel.add(jLabel);
            jPanel.add(jTextField);

            jPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

            jPanelSecond.add(jPanel);


        }
        addItem(newPanel, jPanelSecond, 1, 2, 2, 1, GridBagConstraints.WEST);

        setVisible(true);
    }
    private void addItem(JPanel p, JComponent c, int x, int y, int width, int height, int align) {
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = x;
        gc.gridy = y;
        gc.gridwidth = width;
        gc.gridheight = height;
        gc.weightx = 100.0;
        gc.weighty = 100.0;
        gc.insets = new Insets(5, 5, 5, 5);
        gc.anchor = align;
        gc.fill = GridBagConstraints.NONE;
        p.add(c, gc);
    }
    // method actionPerformed()
    // to get the action performed
    // by the user and act accordingly
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == sub) {
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Chọn file", "docx");

            JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
            jfc.setFileFilter(filter);

            jfc.setCurrentDirectory(new File(SOURCE_FILE));
            int returnValue = jfc.showOpenDialog(null);
            // int returnValue = jfc.showSaveDialog(null);

            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = jfc.getSelectedFile();
                tmno.setText(selectedFile.getAbsolutePath());
            }
        } else if (e.getSource() == reset) {
            XWPFDocument doc = null;
            try {
                doc = this.openDocument(tmno.getText());
            } catch (Exception ex) {
                System.out.println(ex);
                ex.printStackTrace();

            }
            if (doc != null) {
                doc = this.replaceText(doc, "<<name>>", tname.getText());
                this.saveDocument(doc, OUTPUT_FILE);
            }
        }

    }


    private XWPFDocument replaceText(XWPFDocument doc, String findText, String replaceText) {
        for (XWPFParagraph p : doc.getParagraphs()) {

            List<XWPFRun> runs = p.getRuns();
            if (runs != null) {
                for (XWPFRun r : runs) {
                    String text = r.getText(0);
                    if (text != null && text.contains(findText)) {
                        text = text.replace(findText, replaceText);
                        r.setText(text, 0);
                    }
                }
            }
        }
        for (XWPFTable tbl : doc.getTables()) {
            // Copying a existing table
            CTTbl ctTbl = CTTbl.Factory.newInstance(); // Create a new CTTbl for the new table
            ctTbl.set(tbl.getCTTbl()); // Copy the template table's CTTbl
            XWPFTable table2 = new XWPFTable(ctTbl, doc); // Create a new table using the CTTbl upon
            doc.createParagraph();
            doc.createTable(); // Create a empty table in the document
            doc.setTable(1, table2);  // Replace the empty table to table2
            break;
        }
        return doc;
    }

    private XWPFDocument openDocument(String file) throws Exception {
        File fileA = new File(file);
        FileInputStream fis = new FileInputStream(fileA.getAbsolutePath());

        XWPFDocument document = new XWPFDocument(fis);
        return document;
    }

    private void saveDocument(XWPFDocument doc, String file) {
        System.out.println("hong anh do");

        try (FileOutputStream out = new FileOutputStream(file)) {
            doc.write(out);
            System.out.println("hong anh do");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Application testForm = new Application();
        testForm.showMenuDemo();
    }


    private void showMenuDemo() {
        //create a menu bar
        final JMenuBar menuBar = new JMenuBar();
        //create menus
        JMenu fileMenu = new JMenu("File");
        JMenu editMenu = new JMenu("Edit");
        final JMenu aboutMenu = new JMenu("About");
        final JMenu linkMenu = new JMenu("Links");

        //create menu items
        JMenuItem newMenuItem = new JMenuItem("New");
        newMenuItem.setMnemonic(KeyEvent.VK_N);
        newMenuItem.setActionCommand("New");

        JMenuItem openMenuItem = new JMenuItem("Open");
        openMenuItem.setActionCommand("Open");

        JMenuItem saveMenuItem = new JMenuItem("Save");
        saveMenuItem.setActionCommand("Save");

        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.setActionCommand("Exit");

        JMenuItem cutMenuItem = new JMenuItem("Cut");
        cutMenuItem.setActionCommand("Cut");

        JMenuItem copyMenuItem = new JMenuItem("Copy");
        copyMenuItem.setActionCommand("Copy");

        JMenuItem pasteMenuItem = new JMenuItem("Paste");
        pasteMenuItem.setActionCommand("Paste");

        Application.MenuItemListener menuItemListener = new Application.MenuItemListener();

        newMenuItem.addActionListener(menuItemListener);
        openMenuItem.addActionListener(menuItemListener);
        saveMenuItem.addActionListener(menuItemListener);
        exitMenuItem.addActionListener(menuItemListener);
        cutMenuItem.addActionListener(menuItemListener);
        copyMenuItem.addActionListener(menuItemListener);
        pasteMenuItem.addActionListener(menuItemListener);

        final JCheckBoxMenuItem showWindowMenu = new JCheckBoxMenuItem("Show About", true);
        showWindowMenu.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {

                if (showWindowMenu.getState()) {
                    menuBar.add(aboutMenu);
                } else {
                    menuBar.remove(aboutMenu);
                }
            }
        });
        final JRadioButtonMenuItem showLinksMenu = new JRadioButtonMenuItem(
                "Show Links", true);
        showLinksMenu.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {

                if (menuBar.getMenu(3) != null) {
                    menuBar.remove(linkMenu);
                    repaint();
                } else {
                    menuBar.add(linkMenu);
                    repaint();
                }
            }
        });
        //add menu items to menus
        fileMenu.add(newMenuItem);
        fileMenu.add(openMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(showWindowMenu);
        fileMenu.addSeparator();
        fileMenu.add(showLinksMenu);
        fileMenu.addSeparator();
        fileMenu.add(exitMenuItem);

        editMenu.add(cutMenuItem);
        editMenu.add(copyMenuItem);
        editMenu.add(pasteMenuItem);

        //add menu to menubar
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(aboutMenu);
        menuBar.add(linkMenu);

        //add menubar to the frame
        setJMenuBar(menuBar);
        setVisible(true);
    }

    class MenuItemListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            statusLabel.setText(e.getActionCommand() + " JMenuItem clicked.");
        }
    }
}