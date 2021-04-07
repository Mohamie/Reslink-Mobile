package com.example.reslink.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import com.example.reslink.R;
import com.example.reslink.adapters.AnnouncementAdapter;
import com.example.reslink.data_models.Announcement;
import com.example.reslink.data_models.Residence;
import com.example.reslink.data_models.Student;
import com.example.reslink.databinding.FragmentAnnouncementBinding;
import com.example.reslink.utilities.ApplicationClass;
import com.example.reslink.utilities.NotificationHelper;
import com.example.reslink.viewModels.AnnouncementViewModel;
import com.example.reslink.viewModels.UserAuthViewModel;

import java.util.List;



public class AnnouncementFragment extends Fragment implements AnnouncementAdapter.AnnouncementInterface
{

    FragmentAnnouncementBinding announcementBinding;
    RecyclerView recyclerView;
    AnnouncementAdapter adapter;
    AnnouncementViewModel announcementViewModel;
    UserAuthViewModel userAuthViewModel;
    Student loggedStudent;

    List<Announcement> announcementList;

    NavController navController;

    Student student;
    Residence residence;


    public AnnouncementFragment()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        announcementBinding = FragmentAnnouncementBinding.inflate(inflater, container, false);
        return announcementBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        initRecyclerView();
        initViewModel();
        setSwipeToRefresh();
        navController = Navigation.findNavController(view);


    }


    private void initViewModel()
    {

        //Announcement ViewModel
        announcementViewModel = new ViewModelProvider(requireActivity()).get(AnnouncementViewModel.class);
        announcementViewModel.init();


        announcementViewModel.getAnnouncements(ApplicationClass.getLoggedResidence().getObjectId()).observe(getViewLifecycleOwner(), new Observer<List<Announcement>>() {
            @Override
            public void onChanged(List<Announcement> announcements) {
                announcementList = announcements;
                adapter.submitList(announcementList);
            }
        });


        announcementViewModel.getAnnouncementUpdates().observe(getViewLifecycleOwner(), new Observer<Announcement>()
        {
            @Override
            public void onChanged(Announcement announcement)
            {
                if(announcement != null)
                {
                    announcementList.add(0,announcement);
                    adapter.submitList(announcementList);

                    //Make a notification here
                    NotificationHelper notificationHelper = new NotificationHelper(requireActivity());

                    notificationHelper.sendHighPriorityNotification(announcement.getAnnouncementTitle(), announcement.getAnnouncementDescription(), MainActivity.class);
                }
            }
        });



        announcementViewModel.getIsLoading().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if ((aBoolean)) {
                    announcementBinding.progress.setVisibility(View.VISIBLE);
                } else {
                    announcementBinding.progress.setVisibility(View.GONE);
                }
            }
        });

        announcementViewModel.getErrorMessage().observe(getViewLifecycleOwner(), new Observer<String>()
        {
            @Override
            public void onChanged(String s)
            {
                if(s != null)
                {
                    Toast.makeText(requireContext(), "Error: " + s, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void setSwipeToRefresh()
    {
        announcementBinding.swipeToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                announcementViewModel.refreshList();
                announcementBinding.swipeToRefresh.setRefreshing(false);
            }
        });
    }

    private void initRecyclerView()
    {
        recyclerView = announcementBinding.announcementList;
        adapter = new AnnouncementAdapter(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(Announcement announcement) {

        announcementViewModel.setAnnouncement(announcement);
        navController.navigate(R.id.action_announcementFragment_to_announcementDetailsFragment);
    }
}