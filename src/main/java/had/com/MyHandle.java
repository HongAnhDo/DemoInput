package had.com;

import had.com.model.DataBody;
import had.com.model.DataTable;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlException;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

import static javax.swing.JOptionPane.showMessageDialog;

public class MyHandle {
    private static Color colorBackground = Color.WHITE;
    public static final String datePattern = "dd/MM/yyyy";
    public static final SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

    public static void initValueDefault(List<JComponent> arrJComponents, List<DataBody> dataBodyList) {
        for (int i = 0; i < dataBodyList.size(); i++) {
            JComponent jComponent = arrJComponents.get(i);
            String valueDefault = dataBodyList.get(i).getValueDefault();
            if (valueDefault.equals("")) continue;
            if (jComponent instanceof JTextField) {
                JTextField jTextField = (JTextField) jComponent;
                jTextField.setText(valueDefault);
            } else if (jComponent instanceof JTextArea) {
                JTextArea jTextArea = (JTextArea) jComponent;
                jTextArea.setText(valueDefault);
            } else if (jComponent instanceof JFormattedTextField) {
                JFormattedTextField jFormattedTextField = (JFormattedTextField) jComponent;
                jFormattedTextField.setText(valueDefault);
            } else if (jComponent instanceof JDatePickerImpl) {
                JDatePickerImpl jDatePicker = (JDatePickerImpl) jComponent;
                jDatePicker.getJFormattedTextField().setText(valueDefault);
            }

        }

    }

    public static JPanel createTextFied(String textLabel, String name) {
        JPanel pnNumberContract = new JPanel();
        pnNumberContract.setBackground(colorBackground);
        JLabel lbNumberContract = new JLabel(textLabel);

        lbNumberContract.setFont(new Font(lbNumberContract.getFont().getName(), Font.PLAIN, 14));
        lbNumberContract.setPreferredSize(new Dimension(80, 30));// Width, Height
        JTextField tfNumberContract = new JTextField(35);
        tfNumberContract.setName(name);
        Dimension dimension = tfNumberContract.getPreferredSize();
        dimension.height = 30;

        tfNumberContract.setMaximumSize(dimension);
        tfNumberContract.setPreferredSize(dimension);
        tfNumberContract.setSize(dimension);

        pnNumberContract.add(lbNumberContract);
        pnNumberContract.add(tfNumberContract);
        return pnNumberContract;
    }

    public static void addItem(JPanel p, JComponent c, int x, int y, int width, int height, int align) {
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

    public static HashMap<String, String> getValueFormHashMap(List<JComponent> arrJComponents) {
        HashMap<String, String> mapData = new HashMap<>();
        for (int i = 0; i < arrJComponents.size(); i++) {
            JComponent jComponent = arrJComponents.get(i);
            String value = "";
            String key = jComponent.getName();

            if (jComponent instanceof JTextField) {
                JTextField jTextField = (JTextField) jComponent;
                value = jTextField.getText();
            } else if (jComponent instanceof JTextArea) {
                JTextArea jTextArea = (JTextArea) jComponent;
                value = jTextArea.getText();
            } else if (jComponent instanceof JFormattedTextField) {
                JFormattedTextField jTextArea = (JFormattedTextField) jComponent;
                value = jTextArea.getText();
                value = value.replaceAll(",", ".");

            } else if (jComponent instanceof JDatePickerImpl) {
                JDatePickerImpl jDatePicker = (JDatePickerImpl) jComponent;
                value = jDatePicker.getJFormattedTextField().getText();
                if (value != "") {
                    try {
                       String  valueFormat = "ngày "+ value.substring(0,2) + " tháng "
                               +value.substring(3,5) +" năm " + value.substring(6, 10);
                       value = valueFormat;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            mapData.put(key, value.trim());
            System.out.println(key + " : " + value);
        }
        return mapData;
    }

    public static void handleSaveFile(HashMap<String, String> mapData,
                                      String fileToSave, boolean isPDF,
                                      URL resource) {
        File file = null;
        XWPFDocument doc = null;
        try {
            file = new File(resource.toURI());
            doc = openDocument(file);
        } catch (Exception ex) {
            System.out.println(ex);
            ex.printStackTrace();
            try {
                showMessageDialog(null, "Có lỗi xảy ra" + resource.toURI().getPath());
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            return;
        }
        if (doc == null) {
            try {
                showMessageDialog(null, "Có lỗi xảy ra "+  resource.toURI().getPath());
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            return;
        }
        doc = replaceListText(doc, mapData);
        String filePdfPath = "";

        if (fileToSave.endsWith(".docx")) {
            int index = fileToSave.lastIndexOf(".");
            filePdfPath = fileToSave.substring(0, index).concat(".pdf");

        } else if (fileToSave.endsWith(".doc")) {
            fileToSave = fileToSave.concat("x");

            int index = fileToSave.lastIndexOf(".");
            filePdfPath = fileToSave.substring(0, index).concat(".pdf");

        } else if (fileToSave.endsWith(".pdf")) {
            int index = fileToSave.lastIndexOf(".");
            filePdfPath = fileToSave;
            fileToSave = fileToSave.substring(0, index).concat(".docx");

        } else {
            fileToSave = fileToSave.concat(".docx");
            filePdfPath = fileToSave.concat(".pdf");
        }
        saveDocument(doc, fileToSave);

        if (isPDF) {
            try {
                MyUtil.ConvertToPDF(fileToSave, filePdfPath, doc);
            } catch (Exception e) {
                e.printStackTrace();
                showMessageDialog(null, "Có lỗi xảy ra: " + e.getMessage());
            }
        }
        showMessageDialog(null, "Tạo file thành công\nĐường dẫn: " + fileToSave);
    }

    public static void handleSaveFileBC(HashMap<String, String> mapData,
                                      String fileToSave, boolean isPDF,
                                      URL resource, String pathKs) {
        File file = null;
        XWPFDocument doc = null;
        try {
            file = new File(resource.toURI());
            doc = openDocument(file);
        } catch (Exception ex) {
            System.out.println(ex);
            ex.printStackTrace();
            showMessageDialog(null, "Có lỗi xảy ra");
            return;
        }
        if (doc == null) {
            showMessageDialog(null, "Có lỗi xảy ra");
            return;
        }
        doc = replaceListText(doc, mapData);
        String filePdfPath = "";

        if (fileToSave.endsWith(".docx")) {
            int index = fileToSave.lastIndexOf(".");
            filePdfPath = fileToSave.substring(0, index).concat(".pdf");

        } else if (fileToSave.endsWith(".doc")) {
            fileToSave = fileToSave.concat("x");

            int index = fileToSave.lastIndexOf(".");
            filePdfPath = fileToSave.substring(0, index).concat(".pdf");

        } else if (fileToSave.endsWith(".pdf")) {
            int index = fileToSave.lastIndexOf(".");
            filePdfPath = fileToSave;
            fileToSave = fileToSave.substring(0, index).concat(".docx");

        } else {
            fileToSave = fileToSave.concat(".docx");
            filePdfPath = fileToSave.concat(".pdf");
        }
        saveDocument(doc, fileToSave);

        if (isPDF) {
            try {
                MyUtil.ConvertToPDF(fileToSave, filePdfPath, doc);
            } catch (Exception e) {
                e.printStackTrace();
                showMessageDialog(null, "Có lỗi xảy ra: " + e.getMessage());
            }
        }
        showMessageDialog(null, "Tạo file thành công\nĐường dẫn: " + fileToSave);
    }

    public static void handleSaveFileKS(HashMap<String, String> mapData,
                                        String fileToSave, boolean isPDF,
                                        URL resource, String phaply, List<DataTable> listPT,
                                        List<DataTable> listThongso, String hientrang) {
        File file = null;
        XWPFDocument doc = null;
        try {
            file = new File(resource.toURI());
            doc = openDocument(file);
        } catch (Exception ex) {
            System.out.println(ex);
            ex.printStackTrace();
            showMessageDialog(null, "Có lỗi xảy ra");
            return;
        }
        if (doc == null) {
            showMessageDialog(null, "Có lỗi xảy ra");
            return;
        }
        doc = replaceListText(doc, mapData);
        try {
            editKS(doc, phaply, listPT, listThongso, hientrang);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlException e) {
            e.printStackTrace();
        }
        String filePdfPath = "";

        if (fileToSave.endsWith(".docx")) {
            int index = fileToSave.lastIndexOf(".");
            filePdfPath = fileToSave.substring(0, index).concat(".pdf");

        } else if (fileToSave.endsWith(".doc")) {
            fileToSave = fileToSave.concat("x");

            int index = fileToSave.lastIndexOf(".");
            filePdfPath = fileToSave.substring(0, index).concat(".pdf");

        } else if (fileToSave.endsWith(".pdf")) {
            int index = fileToSave.lastIndexOf(".");
            filePdfPath = fileToSave;
            fileToSave = fileToSave.substring(0, index).concat(".docx");

        } else {
            fileToSave = fileToSave.concat(".docx");
            filePdfPath = fileToSave.concat(".pdf");
        }
        saveDocument(doc, fileToSave);

        if (isPDF) {
            try {
                MyUtil.ConvertToPDF(fileToSave, filePdfPath, doc);
            } catch (Exception e) {
                e.printStackTrace();
                showMessageDialog(null, "Có lỗi xảy ra: " + e.getMessage());
                return;
            }
        }
        showMessageDialog(null, "Tạo file thành công\nĐường dẫn: " + fileToSave);
    }

    private static void saveDocument(XWPFDocument doc, String file) {
        try (FileOutputStream out = new FileOutputStream(file)) {
            doc.write(out);
        } catch (IOException e) {
            e.printStackTrace();
            showMessageDialog(null, "Lỗi không thể lưu file \n" + e.getMessage());
        }
    }

    private static XWPFDocument editKS(XWPFDocument doc, String phaply, List<DataTable> listPT,
                                       List<DataTable> listThongso, String hienTrang) throws IOException, XmlException {
        int pos = 3;
        XWPFTable table = doc.getTableArray(0);

//insert new row, which is a copy of row 2, as new row 3:
        //listPT
        XWPFTableRow rowTitlePhaply = table.getRow(0);
        XWPFTableRow rowTitlePT = table.getRow(3);
        XWPFTableRow rowTitleThongSo = table.getRow(5);
        XWPFTableRow rowTitleHientrang = table.getRow(7);
        XWPFTableRow oldRow = table.getRow(1);

        CTRow ctrow;
        ctrow = CTRow.Factory.parse(rowTitlePhaply.getCtRow().newInputStream());
        XWPFTableRow newRow = new XWPFTableRow(ctrow, table);

        int idxRemove = 1;
        int idx = 0;
        for (XWPFTableCell cell : newRow.getTableCells()) {
            cell.removeParagraph(0);
            XWPFParagraph paragraph = cell.addParagraph();
            XWPFRun run = paragraph.createRun();
            run.setFontSize(13);
            run.setFontFamily("Times New Roman");
            if (idx != 0)
                run.setText("phaply");
            idx++;

        }
        table.addRow(newRow, 2);
        table.removeRow(idxRemove);


        ctrow = CTRow.Factory.parse(rowTitlePT.getCtRow().newInputStream());
        newRow = new XWPFTableRow(ctrow, table);
        idxRemove = 3;
        for (DataTable dataTable : listPT) {
            idx = 0;
            for (XWPFTableCell cell : newRow.getTableCells()) {
                cell.removeParagraph(0);
                XWPFParagraph paragraph = cell.addParagraph();
                XWPFRun run = paragraph.createRun();
                run.setFontSize(13);
                run.setFontFamily("Times New Roman");

                if (idx == 1)
                    run.setText(dataTable.getName());
                else if (idx == 2)
                    run.setText(dataTable.getValue());

                idx++;

            }
            pos++;
            table.addRow(newRow, pos);

        }
        if (listPT.size() > 0) {
            table.removeRow(idxRemove);
            idxRemove = pos + 1;
        } else {
            idxRemove = pos + 2;
        }
        pos = idxRemove;

        ctrow = CTRow.Factory.parse(rowTitleThongSo.getCtRow().newInputStream());
        newRow = new XWPFTableRow(ctrow, table);

        for (DataTable dataTable : listThongso) {
            idx = 0;
            for (XWPFTableCell cell : newRow.getTableCells()) {
                cell.removeParagraph(0);
                XWPFParagraph paragraph = cell.addParagraph();
                XWPFRun run = paragraph.createRun();
                run.setFontSize(13);
                run.setFontFamily("Times New Roman");

                if (idx == 1)
                    run.setText(dataTable.getName());
                else if (idx == 2)
                    run.setText(dataTable.getValue());

                idx++;

            }
            pos++;
            table.addRow(newRow, pos);

        }
        if (listPT.size() > 0) {
            table.removeRow(idxRemove);
            idxRemove = pos + 1;
        } else {
            idxRemove = pos + 2;
        }
        pos = idxRemove;


        //Hien trang
        ctrow = CTRow.Factory.parse(rowTitleHientrang.getCtRow().newInputStream());
        newRow = new XWPFTableRow(ctrow, table);

        String lines[] = hienTrang.split("\\r?\\n");

        for (String ht : lines) {
            idx = 0;
            for (XWPFTableCell cell : newRow.getTableCells()) {
                cell.removeParagraph(0);
                XWPFParagraph paragraph = cell.addParagraph();
                XWPFRun run = paragraph.createRun();
                run.setFontSize(13);
                run.setFontFamily("Times New Roman");

                if (idx == 1)
                    run.setText(ht);
                idx++;

            }
            pos++;
            table.addRow(newRow, pos);

        }
        if (listPT.size() > 0) {
            table.removeRow(idxRemove);
            idxRemove = pos + 1;
        } else {
            idxRemove = pos + 2;
        }
        pos = idxRemove;

        return doc;
    }

    public static void setCursoFormatTextField(JFormattedTextField formattedTextField) {
        MouseListener ml = new MouseAdapter() {
            public void mousePressed(final MouseEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        JTextField tf = (JTextField) e.getSource();
                        int offset = tf.getText().length();
                        tf.setCaretPosition(offset);
                    }
                });
            }
        };

        formattedTextField.addMouseListener(ml);
    }

    public static XWPFDocument replaceListText(XWPFDocument doc, Map<String, String> mapData) {
        for (XWPFParagraph p : doc.getParagraphs()) {

            String textParagraph = p.getText();
            System.out.println(p.getText());

            List<XWPFRun> runs = p.getRuns();
            if (runs != null) {
                for (XWPFRun r : runs) {
                    replaceText(mapData, r);
                }
            }
        }

        for (XWPFTable tbl : doc.getTables()) {
            for (XWPFTableRow row : tbl.getRows()) {
                for (XWPFTableCell cell : row.getTableCells()) {
                    for (XWPFParagraph p : cell.getParagraphs()) {
                        for (XWPFRun r : p.getRuns()) {
                            replaceText(mapData, r);
                        }
                    }
                }
            }
        }

        return doc;

    }

    private static void replaceText(Map<String, String> mapData, XWPFRun r) {
        String text = r.getText(0);
        for (Map.Entry<String, String> emEntry : mapData.entrySet()) {

            if (text != null && text.contains(emEntry.getKey())) {
                text = text.replaceAll(emEntry.getKey(), emEntry.getValue());
                r.setText(text, 0);

            } else {
//                if(text != null && text.equals("<a_pos1>>")){
//                    text = text.replaceAll("<a_pos1>>", "<<a_pos1>>");
//                    r.setText(text, 0);
//                }
//                if(text != null && text.equals(" <<a_pos")){
//                    text = text.replaceAll(" <<a_pos", "<<a_pos3>>");
//                    r.setText(text, 0);
//                }
            }
        }
    }

    public static XWPFDocument openDocument(File file) throws Exception {
        InputStream fis = new FileInputStream(file);
        XWPFDocument document = new XWPFDocument(fis);
        return document;
    }

    private void copyTable(XWPFTable source, XWPFTable target) {
        target.getCTTbl().setTblPr(source.getCTTbl().getTblPr());
        target.getCTTbl().setTblGrid(source.getCTTbl().getTblGrid());
        for (int r = 0; r<source.getRows().size(); r++) {
            XWPFTableRow targetRow = target.createRow();
            XWPFTableRow row = source.getRows().get(r);
            targetRow.getCtRow().setTrPr(row.getCtRow().getTrPr());
            for (int c=0; c<row.getTableCells().size(); c++) {
                //newly created row has 1 cell
                XWPFTableCell targetCell = c==0 ? targetRow.getTableCells().get(0) : targetRow.createCell();
                XWPFTableCell cell = row.getTableCells().get(c);
                targetCell.getCTTc().setTcPr(cell.getCTTc().getTcPr());
                XmlCursor cursor = targetCell.getParagraphArray(0).getCTP().newCursor();
                for (int p = 0; p < cell.getBodyElements().size(); p++) {
                    IBodyElement elem = cell.getBodyElements().get(p);
                    if (elem instanceof XWPFParagraph) {
                        XWPFParagraph targetPar = targetCell.insertNewParagraph(cursor);
                        cursor.toNextToken();
                        XWPFParagraph par = (XWPFParagraph) elem;
                        copyParagraph(par, targetPar);
                    } else if (elem instanceof XWPFTable) {
                        XWPFTable targetTable = targetCell.insertNewTbl(cursor);
                        XWPFTable table = (XWPFTable) elem;
                        copyTable(table, targetTable);
                        cursor.toNextToken();
                    }
                }
                //newly created cell has one default paragraph we need to remove
                targetCell.removeParagraph(targetCell.getParagraphs().size()-1);
            }
        }
        //newly created table has one row by default. we need to remove the default row.
        target.removeRow(0);
    }

    private void copyParagraph(XWPFParagraph source, XWPFParagraph target) {
        target.getCTP().setPPr(source.getCTP().getPPr());
        for (int i=0; i<source.getRuns().size(); i++ ) {
            XWPFRun run = source.getRuns().get(i);
            XWPFRun targetRun = target.createRun();
            //copy formatting
            targetRun.getCTR().setRPr(run.getCTR().getRPr());
            //no images just copy text
            targetRun.setText(run.getText(0));
        }
    }
}
