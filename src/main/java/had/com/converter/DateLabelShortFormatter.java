package had.com.converter;

import javax.swing.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateLabelShortFormatter extends JFormattedTextField.AbstractFormatter {

    private String datePattern = "MM/yyyy";
    private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

    @Override
    public Object stringToValue(String text) throws ParseException {
        Calendar cal = Calendar.getInstance();
        cal.setTime((Date) dateFormatter.parseObject(text));
        return cal;
    }

    @Override
    public String valueToString(Object value) throws ParseException {
        if (value != null) {
            if(value instanceof Date){
                Date date = (Date) value;
                return dateFormatter.format(date);
            }
            Calendar cal = (Calendar) value;
            return dateFormatter.format(cal.getTime());
        }

        return "";
    }


}