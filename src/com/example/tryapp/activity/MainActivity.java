package com.example.tryapp.activity;

import java.text.DateFormat;
import java.util.Calendar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.tryapp.R;
import com.example.tryapp.object.Employee;
import com.example.tryapp.sqlite.DatabaseAdapter;

public class MainActivity extends Activity implements OnClickListener{
	
	private DatabaseAdapter dbAdapter;
	private DatePicker dpBirth;
	private EditText nameField;
	private EditText emailField;
	private EditText cellNumField;
	private Spinner empSpinner;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        initDatePicker();
        initSpinner();
        
        Button submitBtn = (Button) findViewById(R.id.submitButton);
    	submitBtn.setOnClickListener(this);
    	
    }

	private void initSpinner() {
		empSpinner = (Spinner) findViewById(R.id.employeeSpinner);
    	ArrayAdapter<CharSequence> spinAdapter = ArrayAdapter.createFromResource(this, R.array.employee_type, 
    											  android.R.layout.simple_spinner_item); 
    	spinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    	empSpinner.setAdapter(spinAdapter);
	}

	private void initDatePicker() {
    	
    	dpBirth = (DatePicker) findViewById(R.id.dpBirthday);
    	
    	final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        
        dpBirth.init(year, month, day, null);
        
	}


	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

	@Override
	public void onClick(View v) {
		insertToDatabase();
		
	}

	private void insertToDatabase() {
		nameField = (EditText) findViewById(R.id.nameField);
		emailField = (EditText) findViewById(R.id.emailField);
		cellNumField = (EditText) findViewById(R.id.cellNumField);
//		empSpinner.getSelectedItem();
		
		Employee contact = new Employee(nameField.getText().toString(), emailField.getText().toString(), cellNumField.getText().toString(), getDate());
		
		clearEditText();
		
		dbAdapter = new DatabaseAdapter(this);
		dbAdapter.openToWrite();
		dbAdapter.insertContact(contact);
		dbAdapter.close();
		
	}

	private void clearEditText() {
		nameField.setText("");
		emailField.setText("");
		cellNumField.setText("");
		initDatePicker();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
		case R.id.contact_item:
			Intent intent = new Intent(this, ViewContactsActivity.class);
			startActivity(intent);
			return true;	
			
		default:
			return super.onOptionsItemSelected(item);
		
		}
	}


	@SuppressLint("NewApi") private String getDate() {
		return DateFormat.getDateInstance().format(dpBirth.getCalendarView().getDate());
	}
    
}
