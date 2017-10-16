package org.jdatepicker;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JFormattedTextField;

public class DateLabelFormatter extends JFormattedTextField.AbstractFormatter {
    private DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public DateFormat getDateFormatter() {
        return dateFormat;
    }

    public void setDateFormatter(DateFormat dateFormatter) {
        this.dateFormat = dateFormatter;
    }

    @Override
    public Object stringToValue(String text) throws ParseException {
        Date result = dateFormat.parse(text);
        Calendar cal = Calendar.getInstance();
        cal.setTime(result);
        return cal;
    }

    @Override
    public String valueToString(Object value) throws ParseException {
        if (value != null) {
            Calendar cal = (Calendar) value;
            return dateFormat.format(cal.getTime());
        }

        return "";
    }
}