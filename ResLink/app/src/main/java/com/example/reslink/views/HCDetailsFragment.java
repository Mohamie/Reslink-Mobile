package com.example.reslink.views;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
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

import com.example.reslink.data_models.HouseCommitte;
import com.example.reslink.databinding.FragmentHCDetailsBinding;
import com.example.reslink.utilities.ApplicationClass;
import com.example.reslink.viewModels.HouseCommitteeViewModel;

public class HCDetailsFragment extends Fragment {

    FragmentHCDetailsBinding hcDetailsBinding;
    HouseCommitteeViewModel hcViewModel;

    private String studentPhone;


    public HCDetailsFragment() {
        // Required empty public constructor
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        hcDetailsBinding = FragmentHCDetailsBinding.inflate(inflater,container,false);
        return hcDetailsBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        hcDetailsBinding.setRes(ApplicationClass.getLoggedResidence());

        hcViewModel = new ViewModelProvider(requireActivity()).get(HouseCommitteeViewModel.class);
        hcViewModel.init();

        hcViewModel.getHC().observe(getViewLifecycleOwner(), new Observer<HouseCommitte>() {
            @Override
            public void onChanged(HouseCommitte houseCommitte)
            {
                hcDetailsBinding.setHc(houseCommitte);

               studentPhone = houseCommitte.getStudent().getPhoneNumber();
            }
        });

        hcDetailsBinding.call.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {

                try {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + studentPhone));
                    startActivity(intent);
                }
                catch (ActivityNotFoundException e)
                {
                    Toast.makeText(getContext(), "Error " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}