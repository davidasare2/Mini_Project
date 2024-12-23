package com.example.miniproject;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class bookAppointmentActivity extends AppCompatActivity {

    Spinner deviceBrandDropDown, issueDropDown;
    CheckBox charger, mouse, laptopBag, others;
    EditText personEmail, deviceModel, serialNumber;
    String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-z.-]+\\.[a-z]{3,}$";
    String deviceModelRegex = "^[a-zA-Z0-9-]{6,30}$";
    String serialNumberRegex = "^[A-Z0-9]{6,}$";
    Button bookAppBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.book_appoinment);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            deviceBrandDropDown = findViewById(R.id.deviceBrand_menu);
            issueDropDown = findViewById(R.id.issue_menu);
            personEmail = findViewById(R.id.etemail);
            deviceModel = findViewById(R.id.etdeviceModel);
            serialNumber = findViewById(R.id.etserialText);
            bookAppBtn = findViewById(R.id.btnBookApp);
            charger = findViewById(R.id.chbCharger);
            mouse = findViewById(R.id.chbMouse);
            laptopBag = findViewById(R.id.chbLaptopBag);
            others = findViewById(R.id.chbOthers);

            String userName = getIntent().getStringExtra("Name");
            String displayInfo = "Welcome,\t\t"+ userName;

            if (getSupportActionBar() != null){
                getSupportActionBar().setTitle(displayInfo);
            }

            String[] dropdownOptions = {"Select device brand", "Apple", "HP", "Lenovo", "Dell", "Asus", "Others"};

            ArrayAdapter<String> brandMenu = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, dropdownOptions);
            brandMenu.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            deviceBrandDropDown.setAdapter(brandMenu);

            String[] issueDropdownOptions = {"Select device issue", "Battery fault", "Screen problem","BootUp issues", "Others"};

            ArrayAdapter<String> issuesMenu = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, issueDropdownOptions);
            issuesMenu.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            issueDropDown.setAdapter(issuesMenu);

            String emailErrorMsg = "Invalid email format";
            String deviceModelErrorMsg = "Device model number must be 6-30 characters";
            String serialNumberErrorMsg = "Serial number must be at least 6 characters (uppercase only)";

            personEmail.addTextChangedListener(textWatcherForFields(personEmail, emailRegex, emailErrorMsg));
            deviceModel.addTextChangedListener(textWatcherForFields(deviceModel, deviceModelRegex, deviceModelErrorMsg));
            serialNumber.addTextChangedListener(textWatcherForFields(serialNumber, serialNumberRegex, serialNumberErrorMsg));

            bookAppBtn.setOnClickListener(view -> {
                if (allFieldValid()){
                    //boolean validEmail = personEmail.getText().toString().trim().matches(emailRegex);

                    new AlertDialog.Builder(bookAppointmentActivity.this)
                            .setTitle("Confirm Submission")
                            .setMessage("Are you sure of your submission")
                            .setPositiveButton("Yes", (dialogInterface, i) -> {
                                Intent intent = new Intent(bookAppointmentActivity.this, thanksActivity.class);
                                intent.putExtra("pEmail", personEmail.getText().toString().trim());
                                startActivity(intent);
                            })
                            .setNegativeButton("No", (dialogInterface, i) -> dialogInterface.dismiss())
                            .setCancelable(false)
                            .show();
                } else {
                    String toastMsg = "Input all fields or fix all errors to proceed";
                    Toast.makeText(bookAppointmentActivity.this, toastMsg, Toast.LENGTH_LONG).show();
                }
            });

            return insets;
        });
    }

    private TextWatcher textWatcherForFields(EditText field, String regex, String errorMsg){
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String input = field.getText().toString().trim();

                if (input.isEmpty()){
                    field.setError(null);
                } else if (!input.matches(regex)){
                    field.setError(errorMsg);
                } else {
                    field.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
    }

    private boolean allFieldValid() {
        String emailTaken = personEmail.getText().toString().trim();
        boolean validEmail = emailTaken.matches(emailRegex) && !emailTaken.isEmpty();

        String deviceModelTaken = deviceModel.getText().toString().trim();
        boolean validDeviceModel = deviceModelTaken.matches(deviceModelRegex) && !deviceModelTaken.isEmpty();

        String serialNumberTaken = serialNumber.getText().toString().trim();
        boolean validSerialNumber = serialNumberTaken.matches(serialNumberRegex) && !serialNumberTaken.isEmpty();

        return validEmail && validDeviceModel && validSerialNumber;
    }
}