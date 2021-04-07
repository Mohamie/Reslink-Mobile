package com.example.reslink.views;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.reslink.R;
import com.example.reslink.data_models.Complaint;
import com.example.reslink.data_models.Event;
import com.example.reslink.databinding.FragmentComplaintBinding;
import com.example.reslink.databinding.FragmentComplaintDetailsBinding;
import com.example.reslink.viewModels.AnnouncementViewModel;
import com.example.reslink.viewModels.ComplaintViewModel;
import com.example.reslink.viewModels.EventViewModel;

import java.text.DateFormat;


public class ComplaintDetailsFragment extends Fragment
{

    FragmentComplaintDetailsBinding complaintDetailsBinding;
    ComplaintViewModel complaintViewModel;

    public ComplaintDetailsFragment()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
     complaintDetailsBinding=FragmentComplaintDetailsBinding.inflate(inflater,container,false);
        return complaintDetailsBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        complaintViewModel = new ViewModelProvider(requireActivity()).get(ComplaintViewModel.class);
        complaintViewModel.init();

        complaintViewModel.getComplaint().observe(getViewLifecycleOwner(), new Observer<Complaint>() {
            @Override
            public void onChanged(Complaint complaint) {
                complaintDetailsBinding.setComplaint(complaint);

                String date = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT).format(complaintDetailsBinding.getComplaint().getCreated());
                complaintDetailsBinding.postedTime.setText("Posted on: " + date);
            }

        });

    }
}