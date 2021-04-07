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
import android.widget.Toast;

import com.example.reslink.R;
import com.example.reslink.adapters.ComplaintAdapter;
import com.example.reslink.data_models.Complaint;
import com.example.reslink.data_models.ComplaintStatus;
import com.example.reslink.data_models.Residence;
import com.example.reslink.data_models.Student;
import com.example.reslink.databinding.FragmentComplaintBinding;
import com.example.reslink.utilities.ApplicationClass;
import com.example.reslink.utilities.NotificationHelper;
import com.example.reslink.viewModels.ComplaintViewModel;

import java.util.ArrayList;
import java.util.List;

public class ComplaintFragment extends Fragment implements ComplaintAdapter.ComplaintInterface
{

    FragmentComplaintBinding complaintBinding;
    RecyclerView recyclerView;
    ComplaintAdapter adapter;
    ComplaintViewModel complaintViewModel;
    NavController navController;

    List<Complaint> complaintList;


    public ComplaintFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        complaintBinding = FragmentComplaintBinding.inflate(inflater, container, false);
        return complaintBinding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initRecyclerView();
        initViewModel();
        navController = Navigation.findNavController(view);

        setSwipeToRefresh();

        complaintBinding.btnAddComplaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_complaintFragment_to_addComplaintFragment);
            }
        });
    }

    private void initViewModel()
    {

        complaintViewModel = new ViewModelProvider(requireActivity()).get(ComplaintViewModel.class);
        complaintViewModel.init();

        complaintViewModel.setDefaultStatus();

        //get saved complaint to add related objects, student in this case
        complaintViewModel.getSavedComplaint().observe(getViewLifecycleOwner(), new Observer<Complaint>() {
            @Override
            public void onChanged(Complaint complaint)
            {
                if(complaint != null)
                {
                    List<Object> childObject = new ArrayList<>();
                    childObject.add(ApplicationClass.getLoggedStudent());
                    complaintViewModel.setRelation(complaint,"student", childObject);

                    List<Object> childStatus = new ArrayList<>();
                    childStatus.add(ApplicationClass.getDefaultStatus());

                    //Set Complaint Status to default
                    complaintViewModel.setRelation(complaint,"complaintStatus", childStatus);


                }

            }
        });

        complaintViewModel.getComplaints(ApplicationClass.getLoggedResidence().getObjectId()).observe(getViewLifecycleOwner(), new Observer<List<Complaint>>() {
            @Override
            public void onChanged(List<Complaint> complaints) {

                complaintList = complaints;
                adapter.submitList(complaintList);
            }
        });

        complaintViewModel.getIsLoading().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if ((aBoolean)) {
                    complaintBinding.progress.setVisibility(View.VISIBLE);
                } else {
                    complaintBinding.progress.setVisibility(View.GONE);
                }
            }
        });

        complaintViewModel.getErrorMessage().observe(getViewLifecycleOwner(), new Observer<String>()
        {
            @Override
            public void onChanged(String s)
            {
                Toast.makeText(requireContext(), "Error: " + s, Toast.LENGTH_LONG).show();
            }
        });

        complaintViewModel.getComplaintUpdates().observe(getViewLifecycleOwner(), new Observer<Complaint>()
        {
            @Override
            public void onChanged(Complaint complaint)
            {
                if(complaint != null)
                {
                    complaintList.add(0, complaint);
                    adapter.submitList(complaintList);
                }
            }
        });


    }
    private void setSwipeToRefresh()
    {

        complaintBinding.swipeToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh()
            {
                complaintViewModel.refreshList();
                complaintBinding.swipeToRefresh.setRefreshing(false);
            }
        });

    }

    private void initRecyclerView()
    {
        recyclerView = complaintBinding.complaintList;
        adapter = new ComplaintAdapter(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(Complaint complaint) {

        complaintViewModel.setComplaint(complaint);
        navController.navigate(R.id.action_complaintFragment_to_complaintDetailsFragment);
    }

}