package com.example.miniproject;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    EditText nameInput;
    Button appBookBtn;
    String nameRegex, nameTaken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            nameInput = findViewById(R.id.etNameInput);
            appBookBtn = findViewById(R.id.btnProceed);

            appBookBtn.setEnabled(false);

            TextWatcher watcher = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    nameRegex = "^[a-zA-Z\\s]+$";
                    nameTaken = editable.toString();
                    boolean validName = nameTaken.matches(nameRegex) && !nameTaken.isEmpty();

                    if (validName){
                        appBookBtn.setEnabled(true);

                        appBookBtn.setOnClickListener(v -> {
                            Intent intent = new Intent(MainActivity.this, bookAppointmentActivity.class);
                            String name = nameTaken.substring(0, 1).toUpperCase() + nameTaken.substring(1).toLowerCase();
                            intent.putExtra("Name", name);
                            startActivity(intent);
                        });

                    } else if(nameTaken.isEmpty()){
                        appBookBtn.setError(null);
                        appBookBtn.setEnabled(false);

                    } else  {
                        nameInput.setError("Name can only contain letters and spaces.");
                        appBookBtn.setEnabled(false);

                    }
                }
            };

            nameInput.addTextChangedListener(watcher);

            return insets;
        });
    }
}