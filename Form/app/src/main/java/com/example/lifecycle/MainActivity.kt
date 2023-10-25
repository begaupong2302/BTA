package com.example.lifecycle

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.DatePicker
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random;
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val randomLayout = Random.nextInt(2)
//        setContentView(R.layout.activity_main)
        setContentView(R.layout.activity_main_no_constraint)


        val firstNameText = findViewById<EditText>(R.id.firstnameText)
        val lastNameText = findViewById<EditText>(R.id.lastnameText)
        val radioButtonMale = findViewById<RadioButton>(R.id.maleRadioButton)
        val radioButtonFemale = findViewById<RadioButton>(R.id.femaleRadioButton)
        val birthdayText = findViewById<EditText>(R.id.birthdayText)
        val addressText = findViewById<EditText>(R.id.addressText)
        val emailText = findViewById<EditText>(R.id.emailText)
        val selectDateButton = findViewById<Button>(R.id.selectDateButton)
        val checkBoxAgree = findViewById<CheckBox>(R.id.agreeCheckBox)
        val registerButton = findViewById<Button>(R.id.registerButton)

        selectDateButton.setOnClickListener {
            showDatePickerDialog(birthdayText)
        }

        registerButton.setOnClickListener {
            handleRegisterButtonClick(
                firstNameText,
                lastNameText,
                radioButtonMale,
                radioButtonFemale,
                birthdayText,
                addressText,
                emailText,
                checkBoxAgree
            )
        }


    }

    private fun showDatePickerDialog(editTextDate: EditText) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { view: DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                val selectedDate = "$dayOfMonth/${monthOfYear + 1}/$year"
                editTextDate.setText(selectedDate)
            },
            year, month, day
        )

        datePickerDialog.show()
    }

    private fun handleRegisterButtonClick(
        editTextFirstName: EditText,
        editTextLastName: EditText,
        maleRadioButton: RadioButton,
        femaleRadioButton: RadioButton,
        editTextBirthDay: EditText,
        addressText: EditText,
        emailText: EditText,
        agreeCheckBox: CheckBox
    ) {
        val firstName = editTextFirstName.text.toString()
        val lastName = editTextLastName.text.toString()
        val birthday = editTextBirthDay.text.toString()
        val address = addressText.text.toString()
        val email = emailText.text.toString()

        val allCheckAccept = firstName.isNotEmpty()
                && lastName.isNotEmpty()
                && (maleRadioButton.isChecked || femaleRadioButton.isChecked)
                && birthday.isNotEmpty()
                && address.isNotEmpty()
                && email.isNotEmpty()
                && agreeCheckBox.isChecked
        if (allCheckAccept) {
            showNotify("Thank you!")
        } else {
            var message = "";
            if (firstName.isEmpty()) message += "Please fill your first name!\n"
            if (lastName.isEmpty()) message += "Please fill your last name!\n"
            if (!(maleRadioButton.isChecked || femaleRadioButton.isChecked)) message += "Please choose your gender\n"
            if (birthday.isEmpty()) message += "Please fill your birthday!\n"
            if (address.isEmpty()) message += "Please fill your address!\n"
            if (email.isEmpty()) message += "Please fill your email!\n"
            if (!agreeCheckBox.isChecked) message += "Please agree to Terms\n"
            showNotify(message)
        }
    }

    private fun showNotify(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}