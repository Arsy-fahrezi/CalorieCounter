package com.example.caloriecounter;

import androidx.appcompat.app.AppCompatActivity;

import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class sign_up extends AppCompatActivity {

    /* Variables */
    private String[] arraySpinnerDOBDay = new String[31];
    private String[] arraySpinnerDOBYear = new String[100];



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        /* Fill numbers for date of birth days */
        int human_counter = 0;
        for(int x=0;x<31;x++){
            human_counter=x+1;
            this.arraySpinnerDOBDay[x] = "" + human_counter;
        }
        Spinner spinnerDOBDay = (Spinner) findViewById(R.id.spinnerDOBDay);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinnerDOBDay);
        spinnerDOBDay.setAdapter(adapter);

        /* Fill numbers for date of birth year */
        // get current year、month and day
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int end = year-100;
        int index = 0;
        for(int x=year;x>end;x--){
            this.arraySpinnerDOBYear[index] = "" + x;
            // Toast.makeText(this, "x = " + x, Toast.LENGTH_SHORT).show();

            index++;
        }

        Spinner spinnerDOBYear = (Spinner) findViewById(R.id.spinnerDOBYear);
        ArrayAdapter<String> adapterYear = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinnerDOBYear);
        spinnerDOBYear.setAdapter(adapterYear);




        /* Hide error icon and message */
        ImageView imageViewError = (ImageView)findViewById(R.id.imageViewError);
        imageViewError.setVisibility(View.GONE);

        TextView textViewErrorMessage = (TextView)findViewById(R.id.textViewErrorMessage);
        textViewErrorMessage.setVisibility(View.GONE);

        /* Hide icnhes field */
        EditText editTextHeightInches = (EditText)findViewById(R.id.editTextHeightInches);
        editTextHeightInches.setVisibility(View.GONE);


        /* Listener Mesurment spinner */
        Spinner spinnerMesurment = (Spinner)findViewById(R.id.spinnerMesurment);
        spinnerMesurment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                mesurmentChanged();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // mesurmentChanged();
            }
        });





        /* Listener buttonSignUp */
        Button buttonSignUp = (Button)findViewById(R.id.buttonSignUp);
        buttonSignUp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                signUpSubmit();
            }
        });


    } // protected void onCreate

    /*- Mesurment changed ------------------------------------------ */
    public void mesurmentChanged() {

        // Mesurment spinner
        Spinner spinnerMesurment = (Spinner)findViewById(R.id.spinnerMesurment);
        String stringMesurment = spinnerMesurment.getSelectedItem().toString();


        EditText editTextHeightCm = (EditText)findViewById(R.id.editTextHeightCm);
        EditText editTextHeightInches = (EditText)findViewById(R.id.editTextHeightInches);
        String stringHeightCm = editTextHeightCm.getText().toString();
        String stringHeightInches = editTextHeightInches.getText().toString();

        double heightCm = 0;
        double heightFeet = 0;
        double heightInches = 0;

        TextView textViewCm = (TextView)findViewById(R.id.textViewCm);
        TextView textViewKg = (TextView)findViewById(R.id.textViewKg);

        if(stringMesurment.startsWith("I")){
            // Imperial
            editTextHeightInches.setVisibility(View.VISIBLE);
            textViewCm.setText("feet and inches");
            textViewKg.setText("pound");

            // Find feet
            try {
                heightCm = Double.parseDouble(stringHeightCm);
            }
            catch(NumberFormatException nfe) {

            }
            if(heightCm != 0){
                // Convert CM into feet
                // feet = cm * 0.3937008)/12
                heightFeet = (heightCm * 0.3937008)/12;
                editTextHeightCm.setText("" + heightFeet);

            }

        }
        else{
            // Metric
            editTextHeightInches.setVisibility(View.GONE);
            textViewCm.setText("cm");
            textViewKg.setText("kg");

            // Change feet and inches to cm

            // Convert Feet
            try {
                heightFeet = Double.parseDouble(stringHeightCm);
            }
            catch(NumberFormatException nfe) {

            }

            // Convert inches
            try {
                heightInches = Double.parseDouble(stringHeightInches);
            }
            catch(NumberFormatException nfe) {

            }

            // Need to convert, we want to save the number in cm
            // cm = ((foot * 12) + inches) * 2.54
            if(heightFeet != 0 && heightInches != 0) {
                heightCm = ((heightFeet * 12) + heightInches) * 2.54;
                editTextHeightCm.setText("" + heightCm);
            }
        }
    }

    /*- Sign up Submit ---------------------------------------------- */
    public void signUpSubmit() {
        // Error
        ImageView imageViewError = (ImageView)findViewById(R.id.imageViewError);
        TextView textViewErrorMessage = (TextView)findViewById(R.id.textViewErrorMessage);
        String errorMessage = "";

        // Email
        TextView textViewEmail = (TextView)findViewById(R.id.textViewEmail);
        EditText editTextEmail = (EditText)findViewById(R.id.editTextEmail);
        String stringEmail = editTextEmail.getText().toString();
        if(stringEmail.isEmpty() || stringEmail.startsWith(" ")){
            errorMessage = "Please fill inn an e-mail address.";
        }

        // Date of Birth Day
        Spinner spinnerDOBDay = (Spinner)findViewById(R.id.spinnerDOBDay);
        String stringDOBDay = spinnerDOBDay.getSelectedItem().toString();
        int intDOBDay = 0;
        try {
            intDOBDay = Integer.parseInt(stringDOBDay);

            if(intDOBDay < 10){
                stringDOBDay = "0" + stringDOBDay;
            }

        }
        catch(NumberFormatException nfe) {
            System.out.println("Could not parse " + nfe);
            errorMessage = "Please select a day for your birthday.";
        }

        // Date of Birth Month
        Spinner spinnerDOBMonth = (Spinner)findViewById(R.id.spinnerDOBMonth);
        String stringDOBMonth = spinnerDOBMonth.getSelectedItem().toString();
        if(stringDOBMonth.startsWith("Jan")){
            stringDOBMonth = "01";
        }
        else if(stringDOBMonth.startsWith("Feb")){
            stringDOBMonth = "02";
        }
        else if(stringDOBMonth.startsWith("Feb")){
            stringDOBMonth = "02";
        }
        else if(stringDOBMonth.startsWith("Mar")){
            stringDOBMonth = "03";
        }
        else if(stringDOBMonth.startsWith("Apr")){
            stringDOBMonth = "04";
        }
        else if(stringDOBMonth.startsWith("May")){
            stringDOBMonth = "05";
        }
        else if(stringDOBMonth.startsWith("Jun")){
            stringDOBMonth = "06";
        }
        else if(stringDOBMonth.startsWith("Jul")){
            stringDOBMonth = "07";
        }
        else if(stringDOBMonth.startsWith("Aug")){
            stringDOBMonth = "08";
        }
        else if(stringDOBMonth.startsWith("Sep")){
            stringDOBMonth = "09";
        }
        else if(stringDOBMonth.startsWith("Oct")){
            stringDOBMonth = "10";
        }
        else if(stringDOBMonth.startsWith("Nov")){
            stringDOBMonth = "11";
        }
        else if(stringDOBMonth.startsWith("Dec")){
            stringDOBMonth = "12";
        }


        // Date of Birth Year
        Spinner spinnerDOBYear = (Spinner)findViewById(R.id.spinnerDOBYear);
        String stringDOBYear = spinnerDOBYear.getSelectedItem().toString();
        int intDOBYear = 0;
        try {
            intDOBYear = Integer.parseInt(stringDOBYear);
        }
        catch(NumberFormatException nfe) {
            System.out.println("Could not parse " + nfe);
            errorMessage = "Please select a year for your birthday.";
        }

        // Put date of birth togheter
        String dateOfBirth = intDOBYear + "-" + stringDOBMonth + "-" + stringDOBDay;


        // Gender
        RadioGroup radioGroupGender = (RadioGroup)findViewById(R.id.radioGroupGender);
        int selectedId = radioGroupGender.getCheckedRadioButtonId(); // get selected radio button from radioGroup
        RadioButton radioButtonGender = (RadioButton) findViewById(selectedId); // find the radiobutton by returned id


        /* Height */
        EditText editTextHeightCm = (EditText)findViewById(R.id.editTextHeightCm);
        EditText editTextHeightInches = (EditText)findViewById(R.id.editTextHeightInches);
        String stringHeightCm = editTextHeightCm.getText().toString();
        String stringHeightInches = editTextHeightInches.getText().toString();

        double heightCm = 0;
        double heightFeet = 0;
        double heightInches = 0;
        boolean metric = true;

        // Metric or imperial?
        Spinner spinnerMesurment = (Spinner)findViewById(R.id.spinnerMesurment);
        String stringMesurment = spinnerMesurment.getSelectedItem().toString();
        if(stringMesurment.startsWith("I")) {
            metric = false;
        }

        if(metric == true) {

            // Convert CM
            try {
                heightCm = Double.parseDouble(stringHeightCm);
            }
            catch(NumberFormatException nfe) {
                errorMessage = "Height (cm) has to be a number.";
            }
        }
        else {

            // Convert Feet
            try {
                heightFeet = Double.parseDouble(stringHeightCm);
            }
            catch(NumberFormatException nfe) {
                errorMessage = "Height (feet) has to be a number.";
            }

            // Convert inches
            try {
                heightInches = Double.parseDouble(stringHeightInches);
            }
            catch(NumberFormatException nfe) {
                errorMessage = "Height (inches) has to be a number.";
            }

            // Need to convert, we want to save the number in cm
            // cm = ((foot * 12) + inches) * 2.54
            heightCm = ((heightFeet * 12) + heightInches) * 2.54;
        }

        Toast.makeText(this, "Height cm = " + heightCm + "\nHeight feet inches = " + heightFeet + " and " +heightInches, Toast.LENGTH_LONG).show();


        // Error handling
        if(errorMessage.isEmpty()){
            // Put data into database
            imageViewError.setVisibility(View.GONE);
            textViewErrorMessage.setVisibility(View.GONE);
        }
        else {
            // There is error
            textViewErrorMessage.setText(errorMessage);
            imageViewError.setVisibility(View.VISIBLE);
            textViewErrorMessage.setVisibility(View.VISIBLE);
        }
    }

}