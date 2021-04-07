package com.example.reslink.adapters;

import android.app.usage.UsageEvents;
import android.provider.CalendarContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reslink.data_models.Event;
import com.example.reslink.databinding.EventsRowBinding;
import com.example.reslink.databinding.ExpandEventRowBinding;
import com.example.reslink.databinding.ExpandEventRowLayoutBinding;
import com.example.reslink.repositories.EventRepo;

import java.text.DateFormat;
import java.util.List;

public class EventAdapter extends ListAdapter<Event,EventAdapter.ViewHolder> implements Filterable
{
    private EventInterface eventInterface;

   public EventAdapter(EventInterface eventInterface){

       super(Event.itemCallback);
       this.eventInterface = eventInterface;
   }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        EventsRowBinding rowBinding = EventsRowBinding.inflate(inflater);
        rowBinding.setEventInterface(this.eventInterface);
        return new ViewHolder(rowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
       Event event = getItem(position);
        holder.eventsRowBinding.setEvent(event);
        String dateCreated = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT).format(holder.eventsRowBinding.getEvent().getCreated());
        String eventDate = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT).format(holder.eventsRowBinding.getEvent().getEventStartTime());

        holder.eventsRowBinding.time.setText("Posted: " + dateCreated);
        holder.eventsRowBinding.tvEventDate.setText("Event date: " + eventDate);
       // holder.eventsRowBinding.tvEventTitle.setText();

        /*boolean isExpanded = event.isExpanded();
        holder.eventsRowBinding.linearLayoutExpand.setVisibility(isExpanded ? View.VISIBLE : View.GONE);*/

    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            return null;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

        }
    };

    class ViewHolder extends RecyclerView.ViewHolder
    {
        EventsRowBinding eventsRowBinding;

        public ViewHolder(EventsRowBinding rowBinding)
        {
            super(rowBinding.getRoot());

            eventsRowBinding = rowBinding;
        }
    }

    public interface EventInterface
    {
        void onClick(Event event);
    }

}
