package com.example.tryapp.fragments;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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

public class AddContactsFragment extends Fragment implements OnClickListener {

	private View view;
	private DatePicker dpBirth;
	private EditText nameField;
	private EditText emailField;
	private EditText cellNumField;
	private EditText wage;
	private EditText hours;
	private Spinner empSpinner;
	private SeekBar bonusBar;
	private DatabaseAdapter dbAdapter;
	private View hoursView;
	private View bonusView;
	private boolean isEditMode;
	private int id;
	private int type;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		dbAdapter = new DatabaseAdapter(this.getActivity());
	}

	@SuppressWarnings("deprecation")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_add_contacts, container, false);
		isEditMode = false;
		initFields();
		initDatePicker();
		initSpinner();

		initIfEditActivity();

		Button submitBtn = (Button) view.findViewById(R.id.submitButton);
		submitBtn.setOnClickListener(this);

		return view;
	}

	private void initIfEditActivity() {
		id = getActivity().getIntent().getIntExtra("id", -1);
		type = getActivity().getIntent().getIntExtra("type", -1);
		
		if (id != -1 && type != -1) { 
			isEditMode = true;
			dbAdapter.openToRead();
			Cursor employeeData = dbAdapter.getEmployeeData(id, type);
			employeeData.moveToFirst();
			if(type == 0) {
				editEmployee(employeeData);
			} else if (type == 1) {
				editEmployee(employeeData);
				empSpinner.setSelection(1);
				wage.setText(employeeData.getString(employeeData.getColumnIndex(DatabaseHelper.COLUMN_WAGE)));
				hours.setText(employeeData.getString(employeeData.getColumnIndex(DatabaseHelper.COLUMN_HOURS)));
			} else if (type == 2) {
				editEmployee(employeeData);
				empSpinner.setSelection(2);
				bonusBar.setProgress(employeeData.getInt(employeeData.getColumnIndex(DatabaseHelper.COLUMN_BONUS)));
			} else {
				editEmployee(employeeData);
				empSpinner.setSelection(3);
			}
			dbAdapter.close();
			
		}
	}

	private void editEmployee(Cursor employeeData) {
		nameField.setText(employeeData.getString(employeeData.getColumnIndex(DatabaseHelper.COLUMN_NAME)));
		emailField.setText(employeeData.getString(employeeData.getColumnIndex(DatabaseHelper.COLUMN_EMAIL)));
		cellNumField.setText(employeeData.getString(employeeData.getColumnIndex(DatabaseHelper.COLUMN_CELLNUM)));
		empSpinner.setSelection(0);
		SimpleDateFormat format = new SimpleDateFormat("MMM dd, yyyy");
		Date date;
		try {
			date = format.parse(employeeData.getString(employeeData.getColumnIndex(DatabaseHelper.COLUMN_BIRTHDAY)));
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			
			int year = cal.get(Calendar.YEAR);
			int month = cal.get(Calendar.MONTH);
			int day = cal.get(Calendar.DAY_OF_MONTH);
			dpBirth.init(year, month, day, null);
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	private void initFields() {
		nameField = (EditText) view.findViewById(R.id.nameField);
		emailField = (EditText) view.findViewById(R.id.emailField);
		cellNumField = (EditText) view.findViewById(R.id.cellNumField);
		empSpinner = (Spinner) view.findViewById(R.id.employeeSpinner);
		dpBirth = (DatePicker) view.findViewById(R.id.dpBirthday);

		hoursView = view.findViewById(R.id.hourlyEmployeeContainer);
		wage = (EditText) hoursView.findViewById(R.id.wageField);
		hours = (EditText) hoursView.findViewById(R.id.hoursField);
		
		bonusView = view.findViewById(R.id.executiveContainer);
		bonusBar = (SeekBar) bonusView.findViewById(R.id.bonusBar);
		bonusBar.setIndeterminate(false);
		bonusBar.setOnSeekBarChangeListener(seekBarListener(bonusView));
	}

	private void initSpinner() {
		ArrayAdapter<CharSequence> spinAdapter = ArrayAdapter
				.createFromResource(this.getActivity(), R.array.employee_type,
						android.R.layout.simple_spinner_item);
		spinAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		empSpinner.setAdapter(spinAdapter);

		empSpinner.setOnItemSelectedListener(spinnerListener());

	}

	// inflate additional fields for hourly employees and executives
	private OnItemSelectedListener spinnerListener() {
		OnItemSelectedListener selected = new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int pos, long id) {
				if (pos == 0) {
					bonusView.setVisibility(View.GONE);
					hoursView.setVisibility(View.GONE);
				} else if (pos == 1) {
					bonusView.setVisibility(View.GONE);
					hoursView.setVisibility(View.VISIBLE);
				} else if (pos == 2) {
					bonusView.setVisibility(View.VISIBLE);
					hoursView.setVisibility(View.GONE);
				} else if (pos == 3) {
					bonusView.setVisibility(View.GONE);
					hoursView.setVisibility(View.GONE);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}

		};
		return selected;
	}

	private OnSeekBarChangeListener seekBarListener(View bonusView) {
		final TextView bonusField = (TextView) bonusView
				.findViewById(R.id.bonusLabelField);

		OnSeekBarChangeListener seekBar = new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				bonusField.setText(progress + "%");
			}
		};
		return seekBar;
	}

	private void initDatePicker() {

		final Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);

		dpBirth.init(year, month, day, null);

	}

	// Submit button listener
	@Override
	public void onClick(View v) {
		if(isEditMode) {
			if(empSpinner.getSelectedItemPosition() == type) {	
				insertToDatabase();
			} else {	
				dbAdapter.openToWrite();
				dbAdapter.deleteEntry(id, type);
				insertToDatabase();
			}
			
			getActivity().finish();
			
		} else {
			insertToDatabase();
		}
		
		refreshFields();
	}

	private void insertToDatabase() {
		dbAdapter.openToWrite();

		if (empSpinner.getSelectedItem().equals(getResources().getString(R.string.trainee))) {

			insertTraineeToDB();
			updateIncome(dbAdapter);

		} else if (empSpinner.getSelectedItem().equals(getResources().getString(R.string.hourEmp))) {

			insertHourlyEmpToDB();
			updateIncome(dbAdapter);

		} else if (empSpinner.getSelectedItem().equals(getResources().getString(R.string.executive))) {
			insertExecutiveToDB();

		} else {
			insertShareholderToDB();

		}

		dbAdapter.close();

	}

	private void insertShareholderToDB() {
		double bonus = (0.20 * getEmployeeIncome(dbAdapter));

		Executive contact = new Executive(nameField.getText().toString(), emailField.getText().toString(), cellNumField.getText()
							.toString(), getDate(), bonus, 3, 20);
		
		if(isEditMode) {
			dbAdapter.updateExecutive(id, contact);
		} else {
			dbAdapter.insertToExecutive(contact);
		}

	}

	private void insertExecutiveToDB() {
		double bonus = ((double) bonusBar.getProgress() / 100) * getEmployeeIncome(dbAdapter);
		double income = bonus + 20000;
		Executive contact = new Executive(nameField.getText().toString(), emailField.getText().toString(), cellNumField.getText()
						    .toString(), getDate(), income, 2, bonusBar.getProgress());
		
		if(isEditMode) {
			dbAdapter.updateExecutive(id, contact);
		} else {
			dbAdapter.insertToExecutive(contact);
		}
		
	}

	private void insertHourlyEmpToDB() {
		double doubleWage = Double.parseDouble(wage.getText().toString());
		int intHours = Integer.parseInt(hours.getText().toString());

		HourlyEmployee contact = new HourlyEmployee(nameField.getText().toString(), emailField.getText().toString(), cellNumField
								.getText().toString(), getDate(), (doubleWage * intHours), 1, doubleWage, intHours);

		if(isEditMode) {
			dbAdapter.updateHourlyEmp(id, contact);
		} else {
			dbAdapter.insertToHourlyEmployee(contact);
		}
		
	}

	private void insertTraineeToDB() {
		Employee contact = new Employee(nameField.getText().toString(), emailField.getText().toString(), cellNumField.getText()
						  .toString(), getDate(), 20000, 0);
		
		if(isEditMode) {
			dbAdapter.updateTrainee(id, contact);
		} else {
			dbAdapter.insertToTrainee(contact);
		}
		
	}

	public void updateIncome(DatabaseAdapter dbAdapter) {
		Cursor bonusPercent = dbAdapter.getBonusPercentage();
		bonusPercent.moveToFirst();

		while (!bonusPercent.isAfterLast()) {
			double income = 0;
			double bonus = getEmployeeIncome(dbAdapter) * (double) bonusPercent.getInt(
						   bonusPercent.getColumnIndex(DatabaseHelper.COLUMN_BONUS)) / 100;
			
			if (bonusPercent.getInt(bonusPercent.getColumnIndex(DatabaseHelper.COLUMN_TYPE)) == 2) {
				income = bonus + 20000;
			} else {
				income = bonus;
			}

			dbAdapter.updateIncome(income, bonusPercent.getInt(bonusPercent
					.getColumnIndex(DatabaseHelper.COLUMN_ID)));

			bonusPercent.moveToNext();
		}

		dbAdapter.close();
	}

	private double getEmployeeIncome(DatabaseAdapter dbAdapter) {
		double dIncome = 0;

		Cursor traineeIncome = dbAdapter.getTraineeIncome();
		traineeIncome.moveToFirst();

		while (!traineeIncome.isAfterLast()) {
			dIncome = dIncome + traineeIncome.getDouble(traineeIncome
					  .getColumnIndex(DatabaseHelper.COLUMN_INCOME));
			traineeIncome.moveToNext();
		}

		Cursor hourlyEmpIncome = dbAdapter.getHourlyEmpIcome();
		hourlyEmpIncome.moveToFirst();

		while (!hourlyEmpIncome.isAfterLast()) {
			dIncome = dIncome + hourlyEmpIncome.getDouble(hourlyEmpIncome
					 .getColumnIndex(DatabaseHelper.COLUMN_INCOME));
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

	@SuppressLint("NewApi")
	private String getDate() {
		return DateFormat.getDateInstance().format(
				dpBirth.getCalendarView().getDate());
	}

}
