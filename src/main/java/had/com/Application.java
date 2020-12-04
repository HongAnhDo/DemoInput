package had.com;
import had.com.view.*;

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
        JMenu fileMenu = new JMenu("Menu");

        //create menu items

        JMenuItem hdMenuItem = new JMenuItem("Hợp đồng thẩm định");
        hdMenuItem.setActionCommand("New");

        JMenuItem ctMenuItem = new JMenuItem("Chứng thư thẩm định");
        ctMenuItem.setActionCommand("New");

        JMenuItem newMenuItem = new JMenuItem("Báo cáo thẩm định");
        newMenuItem.setMnemonic(KeyEvent.VK_N);
        newMenuItem.setActionCommand("New");

        JMenuItem openMenuItem = new JMenuItem("Biên bản nghiệm thu");
        openMenuItem.setActionCommand("Open");

        JMenuItem saveMenuItem = new JMenuItem("Biên bản Khảo sát");
        saveMenuItem.setActionCommand("Save");


        Application.MenuItemListener menuItemListener = new Application.MenuItemListener();

        newMenuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                pane.getViewport().removeAll();
                pane.setViewportView(new PanelBaoCaoThamDinh());
                revalidate();
                repaint();
            }
        });

        hdMenuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                pane.getViewport().removeAll();
                pane.setViewportView(new PanelHopDong());
                revalidate();
                repaint();
            }
        });

        ctMenuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                pane.getViewport().removeAll();
                pane.setViewportView(new PanelChungThu());
                revalidate();
                repaint();
            }
        });
        openMenuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                pane.getViewport().removeAll();
                pane.setViewportView(new PanelNghiemThu());
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


        //add menu items to menus
        fileMenu.add(newMenuItem);
        fileMenu.add(openMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.add(hdMenuItem);
        fileMenu.add(ctMenuItem);

        fileMenu.addSeparator();
        fileMenu.addSeparator();
        fileMenu.addSeparator();

        //add menu to menubar
        menuBar.add(fileMenu);

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
