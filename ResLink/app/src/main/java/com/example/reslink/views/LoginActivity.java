package com.example.reslink.views;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.example.reslink.R;
import com.example.reslink.viewModels.UserAuthViewModel;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etMail, etPassword,etResetMail;
    Button btnLogin;
    TextView tvReset;
    Switch keepLoggedIn;

    UserAuthViewModel userAuthViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        keepLoggedIn = findViewById(R.id.keepLoggedIn);
        etMail = findViewById(R.id.etMail);
        etPassword = findViewById(R.id.etPassword);

        btnLogin=findViewById(R.id.btnLogin);

        tvReset = findViewById(R.id.tvReset);

        btnLogin.setOnClickListener(this);
        tvReset.setOnClickListener(this);

        initViewModel();
    }

    private void initViewModel()
    {
        userAuthViewModel = new ViewModelProvider(LoginActivity.this).get(UserAuthViewModel.class);
        userAuthViewModel.Init();

        userAuthViewModel.getErrorMessage().observe(LoginActivity.this, new Observer<String>()
        {
            @Override
            public void onChanged(String s)
            {
                if(!s.isEmpty())
                {
                    Toast.makeText(LoginActivity.this, "Error: " + s, Toast.LENGTH_SHORT).show();
                    etMail.setText(null);
                    etPassword.setText(null);
                }
            }
        });

        userAuthViewModel.isAuthenticated().observe(LoginActivity.this, new Observer<Boolean>()
        {
            @Override
            public void onChanged(Boolean aBoolean)
            {
                if(aBoolean)
                {
                    //check if is still loading some other data, like Residence and Student associated with UserAccount logged in
                   userAuthViewModel.isLoading().observe(LoginActivity.this, new Observer<Boolean>()
                   {
                       @Override
                       public void onChanged(Boolean aBoolean)
                       {
                           //if is false
                            if(!aBoolean)
                            {
                                //Authenticate User
                                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(i);

                                Toast.makeText(LoginActivity.this, "Logged in successfully!", Toast.LENGTH_SHORT).show();
                                LoginActivity.this.finish();
                            }
                       }
                   });

                }//end if

                //if false getErrorMessage will execute and show message
            }
        });
    }

    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.btnLogin)
        {
            if(etMail.getText().toString().isEmpty() || etPassword.getText().toString().isEmpty()  )
            {
                Toast.makeText(LoginActivity.this, "Please enter all fields! ", Toast.LENGTH_SHORT).show();

            }
            else
            {
               userAuthViewModel.loginUser(etMail.getText().toString().trim(), etPassword.getText().toString().trim(), keepLoggedIn.isChecked());
            }

        }
        else if(v.getId() == R.id.tvReset)
        {
            AlertDialog.Builder dialog = new AlertDialog.Builder(LoginActivity.this);
            dialog.setMessage("Please enter the associated email account of which you want to reset the password for");

            View dialogView = getLayoutInflater().inflate(R.layout.dialog_view, null);
            dialog.setView(dialogView);
            etResetMail = dialogView.findViewById(R.id.etResetMail);

            dialog.setPositiveButton("RESET", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(etResetMail.getText().toString().isEmpty())
                    {
                        Toast.makeText(LoginActivity.this, "Please enter email! ", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Backendless.UserService.restorePassword(etResetMail.getText().toString().trim(), new AsyncCallback<Void>() {
                            @Override
                            public void handleResponse(Void response) {

                                Toast.makeText(LoginActivity.this, "Sending reset instructions", Toast.LENGTH_SHORT).show();

                                Toast.makeText(LoginActivity.this, "Instructions sent", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void handleFault(BackendlessFault fault) {
                                Toast.makeText(LoginActivity.this, "Error: "+ fault.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        });
                    }

                }
            });
            dialog.setNegativeButton("CANCEL: ", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            dialog.show();
        }

    }

}
