package com.example.reslink.views;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.backendless.BackendlessUser;
import com.example.reslink.R;
import com.example.reslink.data_models.Residence;
import com.example.reslink.data_models.Student;
import com.example.reslink.databinding.FragmentUserProfileBinding;
import com.example.reslink.viewModels.UserAuthViewModel;


public class UserProfileFragment extends Fragment
{
    
    FragmentUserProfileBinding profileBinding;
    UserAuthViewModel userAuthViewModel;

    public UserProfileFragment()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        profileBinding = FragmentUserProfileBinding.inflate(inflater, container, false);
        return profileBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        initViewModel();
    }

    private void initViewModel()
    {
        userAuthViewModel = new ViewModelProvider(requireActivity()).get(UserAuthViewModel.class);
        userAuthViewModel.Init();

        userAuthViewModel.checkLoggedUser();


        //observe UserAccount
        userAuthViewModel.getUserAccount().observe(getViewLifecycleOwner(), new Observer<BackendlessUser>() {
            @Override
            public void onChanged(BackendlessUser backendlessUser)
            {
                if(backendlessUser != null)
                {
                    profileBinding.setUser(backendlessUser);

                    Object residence = backendlessUser.getProperty("residence");

                    profileBinding.setResidence((Residence) residence);
                }
            }
        });

        //observe Student object using StudentAccount(Users)
        userAuthViewModel.getStudent().observe(getViewLifecycleOwner(), new Observer<Student>()
        {
            @Override
            public void onChanged(Student student)
            {
                if(student != null)
                {
                    profileBinding.setStudent(student);
                }
            }
        });

        //observe Errors
        userAuthViewModel.getErrorMessage().observe(getViewLifecycleOwner(), new Observer<String>()
        {
            @Override
            public void onChanged(String s)
            {
                if(s != null)
                {
                    Toast.makeText(requireContext(), "Error: " + s, Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}