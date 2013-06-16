package com.example.tryapp.fragments;

import java.text.DateFormat;
import java.util.Calendar;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.tryapp.R;
import com.example.tryapp.object.Employee;
import com.example.tryapp.object.Executive;
import com.example.tryapp.object.HourlyEmployee;
import com.example.tryapp.sqlite.DatabaseAdapter;
import com.example.tryapp.sqlite.DatabaseHelper;

public class AddContactsFragment extends Fragment implements OnClickListener{
	
	private View view;
	private RelativeLayout addContactsView;
//	private DatabaseAdapter dbAdapter;
	private DatePicker dpBirth;
	private EditText nameField;
	private EditText emailField;
	private EditText cellNumField;
	private EditText wage;
	private EditText hours;
	private Spinner empSpinner;
	private SeekBar bonusBar;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_add_contacts, container, false);
		
		addContactsView = (RelativeLayout) view.findViewById(R.id.addFileds);
		
		initDatePicker();
        initSpinner();
        
        Button submitBtn = (Button) view.findViewById(R.id.submitButton);
    	submitBtn.setOnClickListener(this);
    	
		return view;
	}
	
	private void initSpinner() {
		empSpinner = (Spinner) view.findViewById(R.id.employeeSpinner);
    	ArrayAdapter<CharSequence> spinAdapter = ArrayAdapter.createFromResource(this.getActivity(), R.array.employee_type, 
    											  android.R.layout.simple_spinner_item); 	
    	spinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    	empSpinner.setAdapter(spinAdapter);
    	
    	empSpinner.setOnItemSelectedListener(spinnerListener());
    	
	}

	//inflate additional fields
	private OnItemSelectedListener spinnerListener() {
		OnItemSelectedListener selected = new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
				if(pos == 0) {
					addContactsView.removeAllViews();
				} else if(pos == 1) {
					addContactsView.removeAllViews();
					inflateHoursField();
				} else if(pos == 2) {
					addContactsView.removeAllViews();
					inflateBonusField();
				} else if(pos == 3) {
					addContactsView.removeAllViews();
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				
			}
			
		};
		return selected;
	}

	protected void inflateBonusField() {
		final View bonusView = LayoutInflater.from(getActivity()).inflate(R.layout.executive, null);
		
		bonusBar = (SeekBar) bonusView.findViewById(R.id.bonusBar);
		bonusBar.setIndeterminate(false);
		
		bonusBar.setOnSeekBarChangeListener(seekBarListener(bonusView));
		
		addContactsView.addView(bonusView);
	}

	private OnSeekBarChangeListener seekBarListener(View bonusView) {
		final TextView bonusField = (TextView) bonusView.findViewById(R.id.bonusLabelField);
		
		OnSeekBarChangeListener seekBar = new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				bonusField.setText(progress + "%");
			}
		};
		return seekBar;
	}

	protected void inflateHoursField() {
		final View hoursView = LayoutInflater.from(getActivity()).inflate(R.layout.hourly_employee, null);
		
		wage = (EditText) hoursView.findViewById(R.id.wageField);
		hours = (EditText) hoursView.findViewById(R.id.hoursField);
		addContactsView.addView(hoursView);
	}

	private void initDatePicker() {
    	
    	dpBirth = (DatePicker) view.findViewById(R.id.dpBirthday);
    	
    	final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        
        dpBirth.init(year, month, day, null);
        
	}

	//Submit button listener
	@Override
	public void onClick(View v) {
		insertToDatabase();
		refreshFields();
	}

	private void insertToDatabase() {
		nameField = (EditText) view.findViewById(R.id.nameField);
		emailField = (EditText) view.findViewById(R.id.emailField);
		cellNumField = (EditText) view.findViewById(R.id.cellNumField);
		
		DatabaseAdapter dbAdapter = new DatabaseAdapter(this.getActivity());
		dbAdapter.openToWrite();
		
		if(empSpinner.getSelectedItem().equals(getResources().getString(R.string.trainee))) {
			
			insertTraineeToDB(dbAdapter);
			updateIncome(dbAdapter);
			
		} else if(empSpinner.getSelectedItem().equals(getResources().getString(R.string.hourEmp))) {
			
			insertHourlyEmpToDB(dbAdapter);
			updateIncome(dbAdapter);
			
		} else if(empSpinner.getSelectedItem().equals(getResources().getString(R.string.executive))) {
			insertExecutiveToDB(dbAdapter);
			
		} else {
			insertShareholderToDB(dbAdapter);
			
		}
		
		dbAdapter.close();
		
	}

	private void insertShareholderToDB(DatabaseAdapter dbAdapter) {
		double bonus = (0.20 * getEmployeeIncome(dbAdapter));
		
		Executive contact = new Executive(nameField.getText().toString(), emailField.getText().toString(), 
		   		cellNumField.getText().toString(), getDate(), bonus, 20, 1);

		dbAdapter.insertToExecutive(contact);
	}

	private void insertExecutiveToDB(DatabaseAdapter dbAdapter) {
		double bonus = ((double) bonusBar.getProgress() / 100) * getEmployeeIncome(dbAdapter);
		double income = bonus + 20000;
		System.out.println("To be inserted: " + bonusBar.getProgress());
		Executive contact = new Executive(nameField.getText().toString(), emailField.getText().toString(), 
				   		cellNumField.getText().toString(), getDate(), income, bonusBar.getProgress(), 0);
		
		dbAdapter.insertToExecutive(contact);
	}

	private void insertHourlyEmpToDB(DatabaseAdapter dbAdapter) {
		double doubleWage = Double.parseDouble(wage.getText().toString());
		int intHours = Integer.parseInt(hours.getText().toString()); 
		
		HourlyEmployee contact = new HourlyEmployee(nameField.getText().toString(), emailField.getText().toString(),
						cellNumField.getText().toString(), getDate(), (doubleWage * intHours) , doubleWage, intHours);
		
		dbAdapter.insertToHourlyEmployee(contact);
	}

	private void insertTraineeToDB(DatabaseAdapter dbAdapter) {
		Employee contact = new Employee(nameField.getText().toString(), emailField.getText().toString(), 
				   		cellNumField.getText().toString(), getDate(), 20000);
		
		dbAdapter.insertToTrainee(contact);
	}

	private void updateIncome(DatabaseAdapter dbAdapter) {
		
		Cursor bonusPercent = dbAdapter.getBonusPercentage();
		bonusPercent.moveToFirst();
		
		while(!bonusPercent.isAfterLast()) {
			double income = 0;
			double bonus = getEmployeeIncome(dbAdapter) * (double)bonusPercent.getInt(bonusPercent.getColumnIndex(DatabaseHelper.COLUMN_BONUS)) / 100;
			if(bonusPercent.getInt(bonusPercent.getColumnIndex(DatabaseHelper.COLUMN_TYPE)) == 0) {
				income = bonus + 20000;
			} else {
				income = bonus;
			}
			
			dbAdapter.updateIncome(income, bonusPercent.getInt(bonusPercent.getColumnIndex(DatabaseHelper.COLUMN_ID)));
			
			bonusPercent.moveToNext();
		}
		
	}

	private double getEmployeeIncome(DatabaseAdapter dbAdapter) {
		double dIncome = 0;
		
		Cursor traineeIncome = dbAdapter.getTraineeIncome();
		traineeIncome.moveToFirst();
		
		while(!traineeIncome.isAfterLast()) {		
			dIncome = dIncome + traineeIncome.getDouble(traineeIncome.getColumnIndex(DatabaseHelper.COLUMN_INCOME));
			traineeIncome.moveToNext();
		}
		
		Cursor hourlyEmpIncome = dbAdapter.getHourlyEmpIcome();
		hourlyEmpIncome.moveToFirst();
		
		while(!hourlyEmpIncome.isAfterLast()) {
			dIncome = dIncome + hourlyEmpIncome.getDouble(hourlyEmpIncome.getColumnIndex(DatabaseHelper.COLUMN_INCOME));
			hourlyEmpIncome.moveToNext();
		}
		return dIncome;
	}

	private void refreshFields() {
		nameField.setText("");
		emailField.setText("");
		cellNumField.setText("");
		empSpinner.setSelection(0);
		initDatePicker();
	}

	@SuppressLint("NewApi") private String getDate() {
		return DateFormat.getDateInstance().format(dpBirth.getCalendarView().getDate());
	}

}
