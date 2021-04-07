package com.example.reslink.views;

import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.reslink.R;
import com.example.reslink.data_models.Event;
import com.example.reslink.databinding.FragmentEventDetailsBinding;
import com.example.reslink.viewModels.EventViewModel;

import java.text.DateFormat;


public class EventDetailsFragment extends Fragment{

    FragmentEventDetailsBinding eventDetailsBinding;
    EventViewModel eventViewModel;
    Event event;
    public EventDetailsFragment()
    {
        // Required empty public constructor


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        eventDetailsBinding = FragmentEventDetailsBinding.inflate(inflater, container, false);

        return eventDetailsBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {

        super.onViewCreated(view, savedInstanceState);

        eventViewModel = new ViewModelProvider(requireActivity()).get(EventViewModel.class);
        eventViewModel.init();

        eventViewModel.getEvent().observe(getViewLifecycleOwner(), new Observer<Event>() {
            @Override
            public void onChanged(Event event)
            {
                eventDetailsBinding.setEvent(event);
                String date = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT).format(eventDetailsBinding.getEvent().getCreated());
                eventDetailsBinding.postedTime.setText("Posted on: " + date);

                EventDetailsFragment.this.event = event;
            }
        });


        eventDetailsBinding.btnAddEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //replacing the intent with content provider
                Intent intent = new Intent(Intent.ACTION_EDIT);
                intent.setData(CalendarContract.Events.CONTENT_URI);
                intent.putExtra(CalendarContract.Events.TITLE,event.getEventTitle());
                intent.putExtra(CalendarContract.Events.DESCRIPTION,event.getEventDescription());
                intent.putExtra(CalendarContract.Events.ALL_DAY,false);
                intent.putExtra(CalendarContract.Events.DTSTART,event.getEventStartTime());
                intent.putExtra(CalendarContract.Events.DTEND,event.getEventEndTime());
                startActivity(intent);


            }
        });

    }


    public void onAddToCalender(View v) {

        if(v.getId() == R.id.btnAddEvent)
        {


        }
    }
}