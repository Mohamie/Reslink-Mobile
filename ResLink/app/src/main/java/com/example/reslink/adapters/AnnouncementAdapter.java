package com.example.reslink.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reslink.data_models.Announcement;
import com.example.reslink.databinding.AnnouncementsRowBinding;

import java.text.DateFormat;


public class AnnouncementAdapter extends ListAdapter<Announcement, AnnouncementAdapter.ViewHolder> implements Filterable
{
    private AnnouncementInterface announcementInterface;

    public AnnouncementAdapter(AnnouncementInterface announcementInterface)
    {
        super(Announcement.itemCallback);

        this.announcementInterface = announcementInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        AnnouncementsRowBinding rowBinding = AnnouncementsRowBinding.inflate(inflater);
        rowBinding.setAnnouncementInterface(this.announcementInterface);
        return new ViewHolder(rowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        Announcement announcement = getItem(position);
        holder.announcementsRowBinding.setAnnouncement(announcement);
        String date = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT).format(holder.announcementsRowBinding.getAnnouncement().getCreated());
        holder.announcementsRowBinding.time.setText(date);
    }

    @Override
    public Filter getFilter() {
        return null;
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        AnnouncementsRowBinding announcementsRowBinding;
        public ViewHolder(AnnouncementsRowBinding rowBinding)
        {
            super(rowBinding.getRoot());

            announcementsRowBinding = rowBinding;
        }
    }

    public interface AnnouncementInterface
    {
        void onClick(Announcement announcement);
    }
}
