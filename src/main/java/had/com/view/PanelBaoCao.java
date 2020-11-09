package had.com.view;

import had.com.MyHandle;
import had.com.MyUtil;
import had.com.converter.DateLabelFormatter;
import had.com.converter.DateLabelShortFormatter;
import had.com.model.DataBody;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PanelBaoCao extends JPanel {
    private Color colorBackground = Color.WHITE;
    private List<JComponent> arrJComponents = new ArrayList<>();
    private List<DataBody> dataBodyList = new ArrayList<>();
    private Font fontLabel = null;

    public PanelBaoCao() {
        setBackground(colorBackground);
        dataBodyList = DataBody.createDataFormBC();
        setLayout(new BorderLayout());

        JPanel pnFirst = new JPanel();
        pnFirst.setBackground(colorBackground);
        pnFirst.setLayout(new GridBagLayout());

        Dimension d = new Dimension(400, 30);
        d.height = 30;

        DataBody dataBodyNumberContract = dataBodyList.get(0);
        JPanel pnNumberContract = createInputTextField(dataBodyNumberContract);
        //Date created
        DataBody dataBodyDateCreated = dataBodyList.get(1);
        JPanel pnDateCreated = createInputDate(dataBodyDateCreated);

        DataBody dataBodyNumberContract1 = dataBodyList.get(2);
        JPanel pnNumberContract1 = createInputTextField(dataBodyNumberContract1);
        //Date created
        DataBody dataBodyDateCreated1 = dataBodyList.get(3);
        JPanel pnDateCreated1 = createInputDate(dataBodyDateCreated1);

        DataBody dataBodyNumberContract2 = dataBodyList.get(4);
        JPanel pnNumberContract2 = createInputTextField(dataBodyNumberContract2);
        //Date created
        DataBody dataBodyDateCreated2 = dataBodyList.get(5);
        JPanel pnDateCreated2 = createInputDate(dataBodyDateCreated2);

        JPanel pnLable = new JPanel();
        pnLable.setBackground(colorBackground);
        pnLable.setLayout(new GridBagLayout());

        JLabel lbTitle = new JLabel("BÁO CÁO THẨM ĐỊNH");
        fontLabel = new Font(lbTitle.getFont().getName(), Font.PLAIN, 14);
        Font font = new Font(lbTitle.getFont().getName(), Font.BOLD, 18);
        lbTitle.setFont(font);
        pnLable.add(lbTitle);

        addItem(pnFirst, pnLable, 0, 0, 1, 1, GridBagConstraints.WEST);
        addItem(pnFirst, pnNumberContract, 0, 1, 1, 1, GridBagConstraints.WEST);
        addItem(pnFirst, pnDateCreated, 0, 2, 1, 1, GridBagConstraints.WEST);
        addItem(pnFirst, pnNumberContract1, 0, 3, 1, 1, GridBagConstraints.WEST);
        addItem(pnFirst, pnDateCreated1, 0, 4, 1, 1, GridBagConstraints.WEST);
        addItem(pnFirst, pnNumberContract2, 0, 5, 1, 1, GridBagConstraints.WEST);
        addItem(pnFirst, pnDateCreated2, 0, 6, 1, 1, GridBagConstraints.WEST);
        add(pnFirst, BorderLayout.NORTH);


        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(5, 5, 5, 5);

        //Fake
        JPanel pnSecond = new JPanel();
        pnSecond.setBackground(colorBackground);
        pnSecond.setLayout(new BoxLayout(pnSecond, BoxLayout.Y_AXIS));

        JPanel pnBody = new JPanel();
        pnBody.setBackground(colorBackground);
        pnBody.setLayout(new GridLayout(1, 2));

        for (int i = 0; i < 1; i++) {
            JPanel pnColumn = new JPanel();
            pnColumn.setBackground(colorBackground);
            pnColumn.setSize(pnBody.getWidth() / 2, pnColumn.getHeight());
            pnColumn.setLayout(new BoxLayout(pnColumn, BoxLayout.Y_AXIS));
            pnColumn.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 30));

            for (int j = 0; j < 9; j++) {
                int idx = i * 5 + 6 + j;
                if (idx >= dataBodyList.size() || (i == 0 && j >= 5)) {
                    break;
                }
                DataBody dataBody = dataBodyList.get(idx);
                JPanel pnRow = createRowField(d, j, idx, dataBody, true);
                pnColumn.add(pnRow);

            }

            pnColumn.setBorder(BorderFactory.createTitledBorder(i == 0 ? "BÊN A" : "Bên B"));
            pnColumn.setBorder(BorderFactory.createCompoundBorder(
                    pnColumn.getBorder(),
                    BorderFactory.createEmptyBorder(15, 15, 15, 15)));
            pnBody.add(pnColumn);

        }
        pnSecond.add(pnBody);

        JPanel pnColumn = new JPanel();
        pnColumn.setBackground(colorBackground);
        pnColumn.setLayout(new GridLayout(2, 2));
        pnColumn.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));


        JPanel pnAction = new JPanel();
        pnAction.setBackground(colorBackground);
        pnAction.setLayout(new BoxLayout(pnAction, BoxLayout.Y_AXIS));
        pnAction.setSize(pnAction.getWidth(), 100);
        int w = pnAction.getWidth();

        JPanel pnLastInfoParent = new JPanel();
        pnLastInfoParent.setLayout(new GridLayout(1, 2));
        pnLastInfoParent.setBackground(colorBackground);
        for (int i = 0; i < 1; i++) {
            JPanel pnLastInfo = new JPanel();
            pnLastInfo.setBackground(colorBackground);
            pnLastInfo.setSize(pnBody.getWidth() / 2, pnLastInfo.getHeight());
            pnLastInfo.setLayout(new BoxLayout(pnLastInfo, BoxLayout.Y_AXIS));

            int indexField = 11 + 2 * i;

            for (int j = 0; indexField < dataBodyList.size(); j++) {
//                if (j == 2 && i == 0)
//                    break;
                JPanel pnRow = createRowField(d, j, indexField, dataBodyList.get(indexField), false);
                pnRow.setBackground(colorBackground);
                pnRow.setSize(w, pnRow.getHeight());
                pnRow.setBorder(BorderFactory.createCompoundBorder(
                        pnRow.getBorder(),
                        BorderFactory.createEmptyBorder(0, 15, 0, 15)));
                pnLastInfo.add(pnRow);
                indexField += 1;

            }
            pnLastInfoParent.add(pnLastInfo);
        }
        pnAction.add(pnLastInfoParent);
        pnAction.setBorder(BorderFactory.createCompoundBorder(
                pnColumn.getBorder(),
                BorderFactory.createEmptyBorder(0, 0, 200, 0)));

        JPanel pnButton = new JPanel();
        pnButton.setBackground(colorBackground);
        pnButton.setSize(500, 30);
        JButton btn = new JButton("Lưu file");
        btn.setPreferredSize(new Dimension(150, 30));
        pnButton.add(btn);
        JButton btnPdf = new JButton("Lưu file và xuất PDF");
        btnPdf.setPreferredSize(new Dimension(200, 30));
        btnPdf.setName("PDF");
        pnButton.add(btnPdf);

        pnAction.add(pnButton);

        pnSecond.add(pnAction);

        add(pnSecond, BorderLayout.CENTER);
        setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 400));
        addClickButton(btn);
        addClickButton(btnPdf);

        addEventHandleChangePrice();
        MyHandle.initValueDefault(arrJComponents, dataBodyList);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseReleased(e);
                requestFocusInWindow();

            }
        });
    }

    private JPanel createInputTextField(DataBody dataBodyNumberContract) {
        JPanel pnNumberContract = new JPanel();
        pnNumberContract.setBackground(colorBackground);
        JLabel lbNumberContract = new JLabel(dataBodyNumberContract.getTitle());
        lbNumberContract.setFont(fontLabel);
        lbNumberContract.setPreferredSize(new Dimension(220, 30));// Width, Height
        JTextField tfNumberContract = new JTextField(30);
        tfNumberContract.setName(dataBodyNumberContract.getContent());
        Dimension dimension = tfNumberContract.getPreferredSize();
        dimension.height = 30;

        tfNumberContract.setMaximumSize(dimension);
        tfNumberContract.setPreferredSize(dimension);
        tfNumberContract.setSize(dimension);
        arrJComponents.add(tfNumberContract);

        pnNumberContract.add(lbNumberContract);
        pnNumberContract.add(tfNumberContract);
        return pnNumberContract;
    }

    private JPanel createInputDate(DataBody dataBodyDateCreated) {
        JPanel pnDateCreated = new JPanel();
        pnDateCreated.setBackground(colorBackground);

        JLabel lbDateCreated = new JLabel(dataBodyDateCreated.getTitle());
        lbDateCreated.setFont(fontLabel);
        lbDateCreated.setPreferredSize(new Dimension(220, 30));// Width, Height

        UtilDateModel model = new UtilDateModel();
        JDatePanelImpl datePanel = new JDatePanelImpl(model);
        JDatePickerImpl dpDateCreated = new JDatePickerImpl(datePanel, new DateLabelFormatter());
        dpDateCreated.getComponent(0).setPreferredSize(new Dimension(300, 30)); //JFormattedTextField
        dpDateCreated.getComponent(1).setPreferredSize(new Dimension(40, 30));//JButton
        dpDateCreated.setSize(440, 30);
        dpDateCreated.setTextEditable(true);
        dpDateCreated.setName(dataBodyDateCreated.getContent());
        arrJComponents.add(dpDateCreated);

        pnDateCreated.add(lbDateCreated);
        pnDateCreated.add(dpDateCreated);
        return pnDateCreated;
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

    private void addClickButton(JButton btn) {
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HashMap<String, String> mapData = MyHandle.getValueFormHashMap(arrJComponents);

                JFrame parentFrame = new JFrame();

                JFileChooser fileChooser = new JFileChooser();
                fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("MS Word", "docx"));
                fileChooser.setDialogTitle("Specify a file to save");

                int userSelection = fileChooser.showSaveDialog(parentFrame);
                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    String fileToSave = fileChooser.getSelectedFile().getAbsolutePath();
                    boolean isPDF = btn.getName() != null && btn.getName().equals("PDF");
                    handleSaveFileDocOrPdf(mapData, fileToSave, isPDF);
                }
            }

        });
    }

    private void handleSaveFileDocOrPdf(HashMap<String, String> mapData, String fileToSave, boolean isPDF) {
        XWPFDocument doc = null;
        URL resource = getClass().getClassLoader().getResource("Bc.docx");
        MyHandle.handleSaveFile(mapData, fileToSave, isPDF, doc, resource);
    }

    private void addEventHandleChangePrice() {
//        JFormattedTextField tfPriceNoTax = (JFormattedTextField) arrJComponents.get(18);
//        JFormattedTextField tfTax = (JFormattedTextField) arrJComponents.get(19);
//        JFormattedTextField tfTotalPrice = (JFormattedTextField) arrJComponents.get(20);
//        JFormattedTextField tfTemp = (JFormattedTextField) arrJComponents.get(21);
//        JTextField tfTempCharacter = (JTextField) arrJComponents.get(22);
//
//        MyHandle.setCursoFormatTextField(tfPriceNoTax);
//        MyHandle.setCursoFormatTextField(tfTax);
//        MyHandle.setCursoFormatTextField(tfTotalPrice);
//        MyHandle.setCursoFormatTextField(tfTemp);
//
//        tfPriceNoTax.addPropertyChangeListener("value", new PropertyChangeListener() {
//            @Override
//            public void propertyChange(PropertyChangeEvent evt) {
//                if (tfPriceNoTax.getValue() != null) {
//                    Double amount = ((Number) tfPriceNoTax.getValue()).doubleValue();
//                    tfTax.setValue(amount * 0.1);
//                    tfTotalPrice.setValue(amount * 1.1);
//                }
//
//            }
//        });
//
//        tfTemp.addPropertyChangeListener("value", new PropertyChangeListener() {
//            @Override
//            public void propertyChange(PropertyChangeEvent evt) {
//                if (tfTemp.getValue() != null) {
//                    Double amount = ((Number) tfTemp.getValue()).doubleValue();
//                    BigDecimal priceTemp = BigDecimal.valueOf(amount.longValue());
//                    System.out.println(priceTemp);
//                    MyUtil.numberCurrencyToString(priceTemp);
//                    System.out.println(MyUtil.numberCurrencyToString(priceTemp));
//                    tfTempCharacter.setText(
//                            MyUtil.numberCurrencyToString(priceTemp)
//                    );
//                }
//
//            }
//        });


    }


    private JPanel createRowField(Dimension dimension, int index, int indexField, DataBody dataBody, boolean isCheckTextArea) {
        JPanel pnRow = new JPanel();
        pnRow.setBackground(colorBackground);
        pnRow.setLayout(new BorderLayout());
        pnRow.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        JLabel lb = new JLabel();
        lb.setFont(fontLabel);
        lb.setText(dataBody.getTitle());
        pnRow.add(lb, BorderLayout.NORTH);

        UtilDateModel modelRow = new UtilDateModel();
        JDatePanelImpl datePanelRow = new JDatePanelImpl(modelRow);

        JPanel jPanel = new JPanel();
        jPanel.setBackground(colorBackground);
        jPanel.setLayout(new FlowLayout());
        jPanel.setSize(400, 30);

        int type = dataBody.getTypeData();
        if (type == MyUtil.TYPE_FORMATTED_TEXT_FIELD) {
            NumberFormat principleFormat = NumberFormat.getNumberInstance();
            JFormattedTextField tf = new JFormattedTextField(principleFormat);
            tf.setColumns(10);

            tf.setMaximumSize(dimension);
            tf.setAutoscrolls(true);
            tf.setFont(tf.getFont().deriveFont(14f));
            tf.setSize(tf.getWidth(), 80);
            tf.setBorder(BorderFactory.createCompoundBorder(
                    tf.getBorder(),
                    BorderFactory.createEmptyBorder(5, 5, 5, 5)));
            pnRow.add(tf, BorderLayout.CENTER);

            tf.setName(dataBody.getContent());
            arrJComponents.add(tf);

        } else if (type == MyUtil.TYPE_DATE_SHORT) {
            JDatePickerImpl dp = new JDatePickerImpl(datePanelRow, new DateLabelShortFormatter());

            dp.setPreferredSize(new Dimension(340, 30));
            dp.getComponent(0).setPreferredSize(new Dimension(300, 30)); //JFormattedTextField
            dp.getComponent(1).setPreferredSize(new Dimension(40, 30));//JButton
            dp.setSize(400, 30);
            dp.setTextEditable(true);
            jPanel.add(dp);

            pnRow.add(jPanel, BorderLayout.WEST);
            dp.setName(dataBody.getContent());
            arrJComponents.add(dp);

        } else if (type == MyUtil.TYPE_DATE) {
            JDatePickerImpl dp = new JDatePickerImpl(datePanelRow, new DateLabelFormatter());
            dp.setPreferredSize(new Dimension(340, 30));
            dp.getComponent(0).setPreferredSize(new Dimension(300, 30)); //JFormattedTextField
            dp.getComponent(1).setPreferredSize(new Dimension(40, 30));//JButton
            dp.setSize(400, 30);
            dp.setTextEditable(true);
            jPanel.add(dp);

            pnRow.add(jPanel, BorderLayout.WEST);
            dp.setName(dataBody.getContent());
            arrJComponents.add(dp);

        } else if (type == MyUtil.TYPE_TEXT_AREA) {
            JTextArea tf = new JTextArea(2, 1);

            tf.setLineWrap(true);
            tf.setMaximumSize(dimension);
            tf.setAutoscrolls(true);
            tf.setFont(tf.getFont().deriveFont(14f));
            tf.setSize(tf.getWidth(), 80);
            tf.setBorder(BorderFactory.createCompoundBorder(
                    tf.getBorder(),
                    BorderFactory.createEmptyBorder(5, 5, 5, 5)));
            JScrollPane scrollPane = new JScrollPane(tf);
            scrollPane.setWheelScrollingEnabled(false);
            pnRow.add(scrollPane, BorderLayout.CENTER);

            tf.setName(dataBody.getContent());
            arrJComponents.add(tf);
        } else {
            JTextField tf = new JTextField();
            tf.setMaximumSize(dimension);
            tf.setAutoscrolls(true);
            tf.setFont(tf.getFont().deriveFont(14f));
            tf.setSize(tf.getWidth(), 80);
            tf.setBorder(BorderFactory.createCompoundBorder(
                    tf.getBorder(),
                    BorderFactory.createEmptyBorder(5, 5, 5, 5)));

            pnRow.add(tf, BorderLayout.CENTER);
            tf.setName(dataBody.getContent());
            arrJComponents.add(tf);

        }
        return pnRow;
    }
}
