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
import com.example.reslink.data_models.Announcement;
import com.example.reslink.databinding.FragmentAnnouncementDetailsBinding;
import com.example.reslink.viewModels.AnnouncementViewModel;

import java.text.DateFormat;


public class AnnouncementDetailsFragment extends Fragment
{

   FragmentAnnouncementDetailsBinding detailsBinding;
   AnnouncementViewModel announcementViewModel;

    public AnnouncementDetailsFragment()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        detailsBinding = FragmentAnnouncementDetailsBinding.inflate(inflater, container, false);

        return detailsBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        announcementViewModel = new ViewModelProvider(requireActivity()).get(AnnouncementViewModel.class);
        announcementViewModel.init();

        announcementViewModel.getAnnouncement().observe(getViewLifecycleOwner(), new Observer<Announcement>() {
            @Override
            public void onChanged(Announcement announcement) {
                detailsBinding.setAnnouncement(announcement);

                String date = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT).format(detailsBinding.getAnnouncement().getCreated());
                detailsBinding.postedTime.setText("Posted on: " + date);
            }
        });

    }
}