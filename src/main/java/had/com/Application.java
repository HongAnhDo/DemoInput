package had.com;

import had.com.converter.DateLabelFormatter;
import had.com.converter.DateLabelShortFormatter;
import had.com.model.DataBody;
import had.com.view.SwingMenuDemo;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTbl;
import sun.java2d.SunGraphicsEnvironment;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Application extends JFrame {
    private static final String SOURCE_FILE = "C:/Users/H/Desktop/had/HĐ_Cục-Tần-số-vô-tuyến-điện_MITSUBISHI_79c-0677.docx";
    private static final String OUTPUT_FILE = "C:/Users/H/Desktop/had/test.docx";


    private JLabel headerLabel;
    private JLabel statusLabel;

    private List<JComponent> arrJComponents = new ArrayList<>();

    private void makeFrameFullSize(JFrame aFrame) {
        Rectangle r = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
        aFrame.setMaximizedBounds(r);
        aFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    public Application() {
        initMenu();

        JPanel mainPanel = new JPanel();
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JScrollPane pane = new JScrollPane(mainPanel);
        pane.getVerticalScrollBar().setUnitIncrement(16);

        add(pane, BorderLayout.CENTER);
        mainPanel.setBackground(new Color(0x93D5F6));
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 10, 30,10));
        pack();

        JPanel pnFirst = new JPanel();
        pnFirst.setLayout(new GridBagLayout());
        pnFirst.setBackground(Color.RED);

        JPanel pnNumberContract = new JPanel();
        pnNumberContract.setBackground(Color.RED);

        JLabel lbNumberContract = new JLabel("Số hợp đồng");
        lbNumberContract.setPreferredSize(new Dimension(120, 30));// Width, Height
        JTextField tfNumberContract = new JTextField(30);

        Dimension d = tfNumberContract.getPreferredSize();
        d.height = 30;
        tfNumberContract.setMaximumSize(d);
        tfNumberContract.setPreferredSize(d);
        tfNumberContract.setSize(d);

        pnNumberContract.add(lbNumberContract);
        pnNumberContract.add(tfNumberContract);
        addItem(pnFirst, pnNumberContract, 0, 0, 1, 1, GridBagConstraints.WEST);

        JPanel pnDateCreated = new JPanel();
        pnDateCreated.setBackground(Color.GREEN);

        JLabel lbDateCreated = new JLabel("Ngày lập hợp đồng");
        lbDateCreated.setPreferredSize(new Dimension(120, 30));// Width, Height

        UtilDateModel model = new UtilDateModel();
        JDatePanelImpl datePanel = new JDatePanelImpl(model);
        JDatePickerImpl dpDateCreated = new JDatePickerImpl(datePanel, new DateLabelFormatter());
        dpDateCreated.setMaximumSize(d);
        dpDateCreated.setPreferredSize(d);
        dpDateCreated.setSize(d);
        dpDateCreated.setTextEditable(true);

        pnDateCreated.add(lbDateCreated);
        pnDateCreated.add(dpDateCreated);



        addItem(pnFirst, pnDateCreated, 0, 1, 1, 1, GridBagConstraints.WEST);
        mainPanel.add(pnFirst, BorderLayout.NORTH);
        arrJComponents.add(tfNumberContract);
        arrJComponents.add(dpDateCreated);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(5, 5, 5, 5);

        //Fake
        List<DataBody> dataBodyList = DataBody.fakeDate();
        JPanel jPanelSecond = new JPanel();
        jPanelSecond.setBackground(Color.BLACK);
        jPanelSecond.setLayout(new GridLayout(4, 1));

        for (int i = 0; i < 3; i++) {
            JPanel pnColumn = new JPanel();
            pnColumn.setLayout(new GridLayout(4, 2));
            pnColumn.setBorder(BorderFactory.createEmptyBorder(30, 0, 0,0));
            for (int j = 0; j < 7; j++) {
                int idx = i * 7 + 2 + j;
                if(idx >= dataBodyList.size()){
                    break;
                }
                DataBody dataBody = dataBodyList.get(idx);

                JPanel pnRow = new JPanel();
                pnRow.setLayout(new BorderLayout());
                pnRow.setBorder(BorderFactory.createEmptyBorder(0, 0, 5,20));

                JLabel lb = new JLabel();
                lb.setText(dataBody.getTitle());
                pnRow.add(lb, BorderLayout.NORTH);

                UtilDateModel modelRow = new UtilDateModel();
                JDatePanelImpl datePanelRow = new JDatePanelImpl(modelRow);
                if (dataBody.getTypeData() == 2) {
                    JDatePickerImpl dp = new JDatePickerImpl(datePanelRow, new DateLabelShortFormatter());
//                    pnRow.add(dp, BorderLayout.CENTER);
                    JPanel jPanel = new JPanel();
                    jPanel.setLayout(null);
                    jPanel.add(dp);
                    dp.setPreferredSize(new Dimension(140,30));

                    dp.getComponent(0).setPreferredSize(new Dimension(100,30)); //JFormattedTextField
                    dp.getComponent(1).setPreferredSize(new Dimension(40,30));//JButton
                    dp.setSize(300, 30);
                    dp.setTextEditable(true);
                    pnRow.add(jPanel, BorderLayout.CENTER);

                    arrJComponents.add(dp);
                } else if (dataBody.getTypeData() == 1) {
                    JDatePickerImpl dp = new JDatePickerImpl(datePanelRow, new DateLabelFormatter());
                    JPanel jPanel = new JPanel();
                    jPanel.setLayout(null);
                    jPanel.add(dp);
                    dp.setPreferredSize(new Dimension(120,20));

                    dp.getComponent(0).setPreferredSize(new Dimension(100,20)); //JFormattedTextField
                    dp.getComponent(1).setPreferredSize(new Dimension(20,20));//JButton
                    dp.setSize(300, 60);
                    pnRow.add(jPanel, BorderLayout.CENTER);

//                    pnRow.add(dp, BorderLayout.CENTER);

                    arrJComponents.add(dp);
                } else {
//                    JTextArea tf = new JTextArea(1, 1);
                    JTextField tf = new JTextField();

//                    tf.setLineWrap(true);
                    tf.setMaximumSize(d);
                    tf.setAutoscrolls(true);
                    tf.setFont(tf.getFont().deriveFont(14f));
                    tf.setSize(tf.getWidth(), 80);
                    tf.setBorder(BorderFactory.createCompoundBorder(
                            tf.getBorder(),
                            BorderFactory.createEmptyBorder(5, 5, 5, 5)));
//                    JScrollPane scrollPane = new JScrollPane(tf);
//                    scrollPane.setWheelScrollingEnabled(false);
//                    pnRow.add(scrollPane, BorderLayout.CENTER);
                    pnRow.add(tf, BorderLayout.CENTER);
                    arrJComponents.add(tf);
                }


                pnColumn.add(pnRow);


            }
            jPanelSecond.add(pnColumn);


        }

        JPanel pnColumn = new JPanel();
        pnColumn.setLayout(new GridLayout(4, 2));
        pnColumn.setBorder(BorderFactory.createEmptyBorder(30, 0, 0,0));

        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.weightx = 1.0;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridwidth = 2;


        JPanel pnAction = new JPanel();
        pnAction.setLayout(null);
        pnAction.setSize(pnAction.getWidth(), 100);
        JButton btn= new JButton("Tạo hồ sơ");
        btn.setSize(200, 40);
        pnAction.add(btn);
        jPanelSecond.add(pnAction);
        mainPanel.add(jPanelSecond, BorderLayout.CENTER);



        setVisible(true);
    }

    private void initMenu() {
        //header
        setTitle("Phần mềm demo");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        makeFrameFullSize(this);
        setLayout(new BorderLayout());
        headerLabel = new JLabel("", JLabel.CENTER);
        statusLabel = new JLabel("", JLabel.CENTER);
        statusLabel.setSize(350, 100);
        add(headerLabel);
        add(statusLabel);
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

    private static JPanel createFormPanel() {
        //initialize fields
        JTextField nameField = new JTextField(10);
        JTextField streetField = new JTextField(10),
                cityField = new JTextField(5), zipCodeField = new JTextField(5);
        JTextField phoneNumberField = new JTextField();
        JTextField ageField = new JTextField();
        JTextPane descriptionField = new JTextPane();
        descriptionField.setBorder(new LineBorder(Color.gray));
        JButton submitBtn = new JButton("Submit");

        //create panel
        JPanel panel = new JPanel();
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        //using FormBuilder
        FormBuilder.init(panel)
                .add("Full Name", nameField, FormBuilder::spanX3)
                .newRow()
                .addLabelsAsRowHeading("", "Street", "City", "Zip Code")
                .newRow()
                .add("Address", streetField).add(cityField).add(zipCodeField)
                .newRow()
                .add("Phone", phoneNumberField).add("Age", ageField)
                .newRow()
                .add("Description", descriptionField, FormBuilder::spanX3, FormBuilder::spanY2,
                        FormBuilder::fillParentY)
                .newRow()
                .newRow()
                .skipColumns(2).add(submitBtn, FormBuilder::spanX2);

        return panel;
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
