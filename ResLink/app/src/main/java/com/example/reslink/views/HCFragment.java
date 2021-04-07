package com.example.reslink.views;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.reslink.R;
import com.example.reslink.adapters.HouseCommitteAdapter;
import com.example.reslink.data_models.HouseCommitte;
import com.example.reslink.databinding.FragmentHCBinding;
import com.example.reslink.utilities.ApplicationClass;
import com.example.reslink.viewModels.HouseCommitteeViewModel;

import java.util.List;


public class HCFragment extends Fragment implements HouseCommitteAdapter.HouseCommitteInterface
{
     FragmentHCBinding fragmentHCBinding;
     HouseCommitteeViewModel hcViewModel;
    RecyclerView recyclerView;
    HouseCommitteAdapter houseCommitteAdapter;
    NavController navController;

    public HCFragment()
    {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        fragmentHCBinding = FragmentHCBinding.inflate(inflater, container, false);
        return fragmentHCBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        initRecyclerView();
        initViewModel();
        navController = Navigation.findNavController(view);
    }

    private void initViewModel() {
        hcViewModel = new ViewModelProvider(requireActivity()).get(HouseCommitteeViewModel.class);
        hcViewModel.init();

        hcViewModel.getHCListByResId(ApplicationClass.getLoggedResidence().getObjectId()).observe(getViewLifecycleOwner(), new Observer<List<HouseCommitte>>() {
            @Override
            public void onChanged(List<HouseCommitte> houseCommittes) {

                //if (houseCommittes != null) {
                    houseCommitteAdapter.submitList(houseCommittes);
                   // Toast.makeText(requireContext(), "HC size: " + houseCommittes.size(), Toast.LENGTH_SHORT).show();
               // }
            }
        });

        hcViewModel.getErrorMessage().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s != null) {
                    Toast.makeText(requireContext(), "Error: " + s, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

        private void initRecyclerView()
        {
            recyclerView = fragmentHCBinding.hcList;
            houseCommitteAdapter = new HouseCommitteAdapter(this);
            recyclerView.setAdapter(houseCommitteAdapter);

        }

    @Override
    public void onClick(HouseCommitte houseCommitte)
    {
        hcViewModel.setHC(houseCommitte);
        navController.navigate(R.id.action_HCFragment_to_hcDetailsFragment);
    }
}


