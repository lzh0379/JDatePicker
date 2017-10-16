package org.jdatepicker;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JTextField;

public class DatePickerKeyListener implements KeyListener {
    private final JDatePicker datePicker;

    public DatePickerKeyListener(JDatePicker owner) {
	this.datePicker = owner;
    }

    /**
     * Try to parse and format the date string that user input
     */
    private final Pattern TYPING_1 = Pattern.compile("^(\\d\\d?)\\/?(((\\d\\d?)\\/?)?(\\d{1,4})?\\/?)?");

    private final Pattern TYPING_2 = Pattern.compile("^(\\d\\d\\d\\d)\\/?(((\\d\\d?)\\/?)?(\\d{1,2})?\\/?)?");

    private final String TYPING_DATE_FORMAT_1 = "dd/MM/yyyy";

    private final String TYPING_DATE_FORMAT_2 = "dd/MM/yyyy";

    public void keyTyped(KeyEvent event) {
        Pattern typing = null;
        if (TYPING_DATE_FORMAT_1.equals(datePicker.getDateFormat())) {
            // Typing assistance is only applied to the default date format
            typing = TYPING_1;
        } else if (TYPING_DATE_FORMAT_2.equals(datePicker.getDateFormat())) {
            // Typing assistance is only applied to the default date format
            typing = TYPING_2;
        } else {
            return;
        }

	JTextField field = (JTextField) event.getSource();

	char keyChar = event.getKeyChar();
	if (keyChar >= '0' && keyChar <= '9') {
	    String input = field.getText();
	    int start = field.getSelectionStart();
	    int end = field.getSelectionEnd();

	    String leftText = input.substring(0, start);
	    String rightText = input.substring(end);
	    String newValue = leftText + keyChar + rightText;

	    Matcher m = typing.matcher(newValue);
	    if (m.find()) {
		String dateText = "";

		if (m.group(1) != null) {
		    dateText += m.group(1);
		}

		if (m.group(4) != null) {
		    dateText += "/";
		    dateText += m.group(4);
		}

		if (m.group(5) != null) {
		    dateText += "/";
		    dateText += m.group(5);
		}

		System.out.println(dateText);
		field.setText(dateText);

		// If not appending at the end
		if (end < input.length()) {
		    try {
			// Move the caret to right position
			String leading = Pattern.quote(leftText);
			Pattern p = Pattern.compile(leading + ".*?(" + keyChar + ").*?");
			Matcher caret = p.matcher(dateText);
			if (caret.find()) {
			    field.setSelectionStart(caret.end(1));
			    field.setSelectionEnd(caret.end(1));
			}
		    } catch (Exception e) {
			// ignore the expression error
		    }
		}
	    }

	    event.setKeyChar('\0');
	} else if (keyChar == '/') {
	    // Accept the separator char
	} else {
	    event.setKeyChar('\0');
	}
    }

    public void keyPressed(KeyEvent event) {
	if (event.getKeyCode() == KeyEvent.VK_UP) {
	    datePicker.showPopup();
	} else if (event.getKeyCode() == KeyEvent.VK_DOWN) {
	    datePicker.showPopup();
	} else if (event.getKeyCode() == KeyEvent.VK_SPACE) {
	    // Press space to show popup
	    event.setKeyCode(0);
	    datePicker.showPopup();
	} else if (event.getKeyCode() == KeyEvent.VK_ESCAPE) {
	    datePicker.hidePopup();
	}
    }

    public void keyReleased(KeyEvent event) {
    }
}