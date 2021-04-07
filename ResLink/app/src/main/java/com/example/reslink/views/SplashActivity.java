package com.example.reslink.views;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.local.UserIdStorageFactory;
import com.example.reslink.R;
import com.example.reslink.utilities.ApplicationClass;
import com.example.reslink.viewModels.UserAuthViewModel;

public class SplashActivity extends AppCompatActivity
{

    Boolean isLogged = null;
    UserAuthViewModel userAuthViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getSupportActionBar().hide();


        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                // This method will be executed once the timer is over
                initViewModel();

            }
        }, 3000);
    }

    private void initViewModel()
    {
        userAuthViewModel = new ViewModelProvider(SplashActivity.this).get(UserAuthViewModel.class);
        userAuthViewModel.Init();

        userAuthViewModel.checkLoggedUser();

        userAuthViewModel.getErrorMessage().observe(SplashActivity.this, new Observer<String>()
        {
            @Override
            public void onChanged(String s)
            {
                Toast.makeText(SplashActivity.this, "Error: " + s, Toast.LENGTH_SHORT).show();
            }
        });

        userAuthViewModel.isLoading().observe(SplashActivity.this, new Observer<Boolean>()
        {
            @Override
            public void onChanged(Boolean aBoolean)
            {
                if(!aBoolean)
                {
                    userAuthViewModel.isLogged().observe(SplashActivity.this, new Observer<Boolean>()
                    {
                        @Override
                        public void onChanged(Boolean aBoolean)
                        {
                            Intent i;

                            if(aBoolean)
                            {
                                i = new Intent(SplashActivity.this, MainActivity.class);
                            }
                            else
                            {
                                i = new Intent(SplashActivity.this, LoginActivity.class);
                            }
                            startActivity(i);
                            finish();

                        }
                    });
                }
            }
        });

    }


}
