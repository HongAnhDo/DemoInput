package had.com;

import had.com.model.DataBody;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTbl;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static javax.swing.JOptionPane.showMessageDialog;

public class MyHandle {
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
                JDatePickerImpl jTextArea = (JDatePickerImpl) jComponent;
                value = jTextArea.getJFormattedTextField().getText();
            }
            mapData.put(key, value);
            System.out.println(key + " : " + value);
        }
        return mapData;
    }

    public static void handleSaveFile(HashMap<String, String> mapData, String fileToSave, boolean isPDF, XWPFDocument doc, URL resource) {
        File file = null;
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

    private static void saveDocument(XWPFDocument doc, String file) {
        try (FileOutputStream out = new FileOutputStream(file)) {
            doc.write(out);
        } catch (IOException e) {
            e.printStackTrace();
            showMessageDialog(null, "Lỗi không thể lưu file \n" + e.getMessage());
        }
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
//        for (XWPFTable tbl : doc.getTables()) {
//            for (XWPFTableRow row : tbl.getRows()) {
//                for (XWPFTableCell cell : row.getTableCells()) {
//                    for (XWPFParagraph p : cell.getParagraphs()) {
//                        for (XWPFRun r : p.getRuns()) {
//                            String text = r.getText(0);
//                            if (text != null && text.contains("needle")) {
//                                text = text.replace("needle", "haystack");
//                                r.setText(text,0);
//                            }
//                        }
//                    }
//                }
//            }
//        }

        return doc;

    }

    private static void replaceText(Map<String, String> mapData, XWPFRun r) {
        String text = r.getText(0);
        for (Map.Entry<String, String> emEntry : mapData.entrySet()) {

            if (text != null && text.contains(emEntry.getKey())) {
//                text = text.replaceAll(emEntry.getKey(), emEntry.getValue());
//                r.setText(text, 0);

            } else {
                if(text != null && text.equals("<a_pos1>>")){
                    text = text.replaceAll("<a_pos1>>", "<<a_pos1>>");
                    r.setText(text, 0);
                }
                if(text != null && text.equals("<<a_pos")){
                    text = text.replaceAll("<<a_pos", "<<a_pos3>>");
                    r.setText(text, 0);
                }
            }
        }
    }

    public static XWPFDocument openDocument(File file) throws Exception {
        InputStream fis = new FileInputStream(file);
        XWPFDocument document = new XWPFDocument(fis);
        return document;
    }


}
