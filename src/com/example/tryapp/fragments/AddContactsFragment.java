package com.example.tryapp.fragments;

import java.text.DateFormat;
import java.util.Calendar;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.tryapp.R;
import com.example.tryapp.object.Employee;
import com.example.tryapp.sqlite.DatabaseAdapter;

public class AddContactsFragment extends Fragment implements OnClickListener{
	
	private View view;
	private DatabaseAdapter dbAdapter;
	private DatePicker dpBirth;
	private EditText nameField;
	private EditText emailField;
	private EditText cellNumField;
	private Spinner empSpinner;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_add_contacts, container, false);
		
		initDatePicker();
        initSpinner();
        
        Button submitBtn = (Button) view.findViewById(R.id.submitButton);
    	submitBtn.setOnClickListener(this);
		
		return view;
	}
	
	private void initSpinner() {
		empSpinner = (Spinner) view.findViewById(R.id.employeeSpinner);
    	ArrayAdapter<CharSequence> spinAdapter = ArrayAdapter.createFromResource(this.getActivity(), R.array.employee_type, 
    											  android.R.layout.simple_spinner_item); 	//getActivity().getApplicationContext();
    	spinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    	empSpinner.setAdapter(spinAdapter);
    	
	}

	private void initDatePicker() {
    	
    	dpBirth = (DatePicker) view.findViewById(R.id.dpBirthday);
    	
    	final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        
        dpBirth.init(year, month, day, null);
        
	}

	@Override
	public void onClick(View v) {
		insertToDatabase();
	}

	private void insertToDatabase() {
		nameField = (EditText) view.findViewById(R.id.nameField);
		emailField = (EditText) view.findViewById(R.id.emailField);
		cellNumField = (EditText) view.findViewById(R.id.cellNumField);
//		empSpinner.getSelectedItem();
		
		Employee contact = new Employee(nameField.getText().toString(), emailField.getText().toString(), 
						   cellNumField.getText().toString(), getDate(), computeIncome());
		
		clearEditText();
		
		dbAdapter = new DatabaseAdapter(this.getActivity());
		dbAdapter.openToWrite();
		dbAdapter.insertContact(contact);
		dbAdapter.close();
		
	}

	private Double computeIncome() {
		
		if(empSpinner.getSelectedItem().equals(getResources().getString(R.string.trainee))) {
			return (double) 20000;
		} else if(empSpinner.getSelectedItem().equals(getResources().getString(R.string.hourEmp))) {
			return (double) 10000;		//how to get hours? days?
		} else if(empSpinner.getSelectedItem().equals(getResources().getString(R.string.executive))) {
			return (double) 25000;
		} else {
			return (double) 30000;
		}
		
	}
	
	private void clearEditText() {
		nameField.setText("");
		emailField.setText("");
		cellNumField.setText("");
		initDatePicker();
	}

	@SuppressLint("NewApi") private String getDate() {
		return DateFormat.getDateInstance().format(dpBirth.getCalendarView().getDate());
	}

}
