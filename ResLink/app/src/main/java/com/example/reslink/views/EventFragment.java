package com.example.reslink.views;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.reslink.R;
import com.example.reslink.adapters.EventAdapter;
import com.example.reslink.data_models.Event;
import com.example.reslink.databinding.FragmentEventBinding;
import com.example.reslink.utilities.ApplicationClass;
import com.example.reslink.utilities.NotificationHelper;
import com.example.reslink.viewModels.EventViewModel;

import java.util.List;


public class EventFragment extends Fragment implements EventAdapter.EventInterface {

    FragmentEventBinding eventBinding;
    RecyclerView recyclerView;
    EventAdapter adapter;
    EventViewModel eventViewModel;
    NavController navController;

    List<Event> eventsList;

    public EventFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        eventBinding= FragmentEventBinding.inflate(inflater,container,false);
        return eventBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initRecyclerView();
        initViewModel();
        navController = Navigation.findNavController(view);
        setSwipeToRefresh();
    }

    private void setSwipeToRefresh()
    {
        eventBinding.swipeToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                eventViewModel.refreshList();
                eventBinding.swipeToRefresh.setRefreshing(false);
            }
        });
    }

    private void initViewModel()
    {
        eventViewModel= new ViewModelProvider(requireActivity()).get(EventViewModel.class);
        eventViewModel.init();

        eventViewModel.getEvents(ApplicationClass.getLoggedResidence().getObjectId()).observe(getViewLifecycleOwner(), new Observer<List<Event>>() {
            @Override
            public void onChanged(List<Event> events)
            {
                eventsList = events;
                adapter.submitList(eventsList);
            }
        });

        eventViewModel.getIsLoading().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean)
                {
                    eventBinding.progress.setVisibility(View.VISIBLE);
                }
                else
                {
                    eventBinding.progress.setVisibility(View.GONE);

                }
            }
        });


        eventViewModel.getEventUpdates().observe(getViewLifecycleOwner(), new Observer<Event>()
        {
            @Override
            public void onChanged(Event event)
            {
                if(event != null)
                {
                    eventsList.add(0,event);
                    adapter.submitList(eventsList);

                    //Make a notification here
                    NotificationHelper notificationHelper = new NotificationHelper(requireActivity());

                    notificationHelper.sendHighPriorityNotification(event.getEventTitle(), event.getEventDescription(), MainActivity.class);
                }
            }
        });
    }

    private void initRecyclerView()
    {
        recyclerView = eventBinding.eventList;
        adapter= new EventAdapter(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(Event event) {

       eventViewModel.setEvent(event);
       navController.navigate(R.id.action_eventFragment_to_eventDetailsFragment);

    }
}