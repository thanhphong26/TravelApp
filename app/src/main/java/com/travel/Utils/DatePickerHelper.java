package com.travel.Utils;

import android.app.DatePickerDialog;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DatePickerHelper implements DatePickerDialog.OnDateSetListener{

    private Calendar myCalendar = Calendar.getInstance();
    private EditText date;

    private View view;

    public void setEditText(EditText date) {
        this.date = date;
    }

    public void setView(View view){
        this.view=view;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        myCalendar.set(Calendar.YEAR, year);
        myCalendar.set(Calendar.MONTH,month);
        myCalendar.set(Calendar.DAY_OF_MONTH,day);
        updateLabel();
    }

    private void updateLabel(){
        String myFormat="dd/MM/YYYY";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat);
        date.setText(dateFormat.format(myCalendar.getTime()));
    }

    public void initDatePicker(){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(view.getContext(),DatePickerHelper.this,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }
}
