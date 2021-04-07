package com.example.reslink.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reslink.R;
import com.example.reslink.data_models.Complaint;
import com.example.reslink.databinding.ComplaintsRowBinding;

import java.text.DateFormat;

public class ComplaintAdapter extends ListAdapter<Complaint,ComplaintAdapter.ViewHolder>
{
    private ComplaintInterface complaintInterface;

   public ComplaintAdapter(ComplaintInterface complaintInterface)
   {
       super(Complaint.itemCallback);

       this.complaintInterface = complaintInterface;
   }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

       LayoutInflater inflater=LayoutInflater.from(parent.getContext());
       ComplaintsRowBinding rowBinding=ComplaintsRowBinding.inflate(inflater);
        rowBinding.setComplaintInterface(this.complaintInterface);
        return new ComplaintAdapter.ViewHolder(rowBinding);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Complaint complaint = getItem(position);
        holder.complaintsRowBinding.setComplaint(complaint);
        String date = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT).format(holder.complaintsRowBinding.getComplaint().getCreated());
        holder.complaintsRowBinding.time.setText(date);

       if(complaint.getComplaintStatus() != null)
       {
           switch(complaint.getComplaintStatus().getStatusID())
           {
               case 0:
               {
                   holder.complaintsRowBinding.ivStatus.setImageResource(R.drawable.ic_pill);
                   break;
               }

               case 1:
               {
                   holder.complaintsRowBinding.ivStatus.setImageResource(R.drawable.ic_pill_yellow);
                   break;
               }

               case 2:
               {
                   holder.complaintsRowBinding.ivStatus.setImageResource(R.drawable.ic_pill_green);
                   break;
               }
           }
       }
    }

     class ViewHolder extends RecyclerView.ViewHolder {

       ComplaintsRowBinding complaintsRowBinding;
         public ViewHolder(ComplaintsRowBinding rowBinding)
         {

             super(rowBinding.getRoot());
             complaintsRowBinding=rowBinding;
         }
     }

     public interface ComplaintInterface
     {
        void onClick(Complaint complaint);
     }
}
