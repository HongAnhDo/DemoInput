package had.com;
import had.com.view.PanelBaoCao;
import had.com.view.PanelBcKhaoSat;
import had.com.view.PanelHopDong;
import had.com.view.PanelThamDinh;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.prefs.Preferences;


public class Application extends JFrame {

    private static final String PATH_FILE = "PATH_FILE_01";

    private static boolean pathFileStore(String newValue) {
        Preferences prefs = Preferences.userRoot().node("");
        try {
            String oldValue = prefs.get(PATH_FILE, "./data/file01.docx");
            prefs.put(PATH_FILE, newValue);
            prefs.flush();
            System.out.println(oldValue + " " + newValue);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private static String getPathFileStore() {
        Preferences prefs = Preferences.userRoot().node("");
        try {
            return prefs.get(PATH_FILE, "./data/file01.docx");

        } catch (Exception e) {
        }
        return null;
    }

    private JLabel headerLabel;
    private JLabel statusLabel;
    private JScrollPane pane;

    private void makeFrameFullSize(JFrame aFrame) {
        Rectangle r = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
        aFrame.setMaximizedBounds(r);
        aFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    public Application() {
        initMenu();
        JPanel mainPanel = new PanelHopDong();
        pane = new JScrollPane(mainPanel);
        pane.getVerticalScrollBar().setUnitIncrement(16);
        add(pane, BorderLayout.CENTER);
        pack();
        setVisible(true);

    }

    private void initMenu() {
        //header
        setTitle("Phần mềm nhập file");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        makeFrameFullSize(this);
        setLayout(new BorderLayout());
        headerLabel = new JLabel("", JLabel.CENTER);
        statusLabel = new JLabel("", JLabel.CENTER);
        statusLabel.setSize(350, 100);
        add(headerLabel);
        add(statusLabel);
    }

    public static void setUIFont(FontUIResource f) {
        Enumeration keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof FontUIResource) {
                FontUIResource orig = (FontUIResource) value;
                Font font = new Font(f.getFontName(), orig.getStyle(), f.getSize());
                UIManager.put(key, new FontUIResource(font));
            }
        }
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

        JMenuItem saveMenuItem2 = new JMenuItem("Save");
        saveMenuItem2.setActionCommand("Save2");

        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.setActionCommand("Exit");

        JMenuItem cutMenuItem = new JMenuItem("Cut");
        cutMenuItem.setActionCommand("Cut");

        JMenuItem copyMenuItem = new JMenuItem("Copy");
        copyMenuItem.setActionCommand("Copy");

        JMenuItem pasteMenuItem = new JMenuItem("Paste");
        pasteMenuItem.setActionCommand("Paste");

        Application.MenuItemListener menuItemListener = new Application.MenuItemListener();

        newMenuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                pane.getViewport().removeAll();
                pane.setViewportView(new PanelBaoCao());
                revalidate();
                repaint();
            }
        });
        openMenuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                pane.getViewport().removeAll();
                pane.setViewportView(new PanelThamDinh());
                revalidate();
                repaint();
            }
        });
        saveMenuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                pane.getViewport().removeAll();
                pane.setViewportView(new PanelBcKhaoSat());
                revalidate();
                repaint();
            }
        });
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
        fileMenu.add(saveMenuItem2);
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

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                }
                setUIFont(new FontUIResource(new Font("Arial", 0, 14)));

                Application testForm = new Application();
                testForm.showMenuDemo();
            }
        });
    }

    class MenuItemListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            statusLabel.setText(e.getActionCommand() + " JMenuItem clicked.");
        }
    }
}
