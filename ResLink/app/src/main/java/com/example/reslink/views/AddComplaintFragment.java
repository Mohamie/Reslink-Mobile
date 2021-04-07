package com.example.reslink.views;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.example.reslink.R;
import com.example.reslink.data_models.Complaint;
import com.example.reslink.data_models.Student;
import com.example.reslink.databinding.FragmentAddComplaintBinding;
import com.example.reslink.viewModels.ComplaintViewModel;

import java.util.List;

public class AddComplaintFragment extends Fragment
{
    FragmentAddComplaintBinding addComplaintBinding;
    ComplaintViewModel complaintViewModel;
    NavController navController;

    public AddComplaintFragment()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        addComplaintBinding = FragmentAddComplaintBinding.inflate(inflater, container, false);
        return addComplaintBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        initComplaintViewModel();

        addComplaintBinding.btnSaveComplaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(addComplaintBinding.etComplaintDescription.getText().toString().isEmpty() || addComplaintBinding.etComplaintTitle.getText().toString().isEmpty())
                {
                    Toast.makeText(requireContext(), "Please enter all fields", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Complaint newComplaint = new Complaint();
                    newComplaint.setComplaintTitle(addComplaintBinding.etComplaintTitle.getText().toString().trim());
                    newComplaint.setComplaintDescription(addComplaintBinding.etComplaintDescription.getText().toString().trim());



                    complaintViewModel.addComplaint(newComplaint);



                    complaintViewModel.getIsLoading().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
                        @Override
                        public void onChanged(Boolean aBoolean) {
                            if(aBoolean)
                            {
                                addComplaintBinding.progress.setVisibility(View.VISIBLE);
                            }
                            else
                            {
                                navController.navigate(R.id.action_addComplaintFragment_to_complaintFragment);
                                addComplaintBinding.progress.setVisibility(View.GONE);
                            }
                        }
                    });

                }

            }
        });

    }

    private void initComplaintViewModel()
    {
        complaintViewModel = new ViewModelProvider(requireActivity()).get(ComplaintViewModel.class);
        complaintViewModel.init();


    }


}