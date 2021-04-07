package com.example.reslink.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reslink.data_models.Announcement;
import com.example.reslink.data_models.HouseCommitte;
import com.example.reslink.databinding.AnnouncementsRowBinding;
import com.example.reslink.databinding.HousecommitteeRowBinding;

public class HouseCommitteAdapter extends ListAdapter<HouseCommitte, HouseCommitteAdapter.ViewHolder> {

    private HouseCommitteInterface houseCommitteInterface;

    public HouseCommitteAdapter(HouseCommitteInterface houseCommitteInterface) {

        super(HouseCommitte.itemCallback);

        this.houseCommitteInterface=houseCommitteInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        HousecommitteeRowBinding rowBinding = HousecommitteeRowBinding.inflate(inflater);
        rowBinding.setHouseCommitteInterface(this.houseCommitteInterface);
        return new ViewHolder(rowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        HouseCommitte houseCommitte = getItem(position);
        holder.housecommitteeRowBinding.setHousecommittee(houseCommitte);

    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        HousecommitteeRowBinding housecommitteeRowBinding;

        public ViewHolder(HousecommitteeRowBinding rowBinding) {

            super(rowBinding.getRoot());

            housecommitteeRowBinding = rowBinding;
        }
    }

    public interface HouseCommitteInterface
    {
        void onClick(HouseCommitte houseCommitte);
    }
}
