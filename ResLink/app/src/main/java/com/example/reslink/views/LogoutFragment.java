package com.example.reslink.views;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.local.UserIdStorageFactory;
import com.example.reslink.R;
import com.example.reslink.data_models.Event;
import com.example.reslink.databinding.FragmentLogoutBinding;
import com.example.reslink.viewModels.AnnouncementViewModel;

public class LogoutFragment extends Fragment
{

    FragmentLogoutBinding fragmentLogoutBinding;
    AnnouncementViewModel announcementViewModel;
    Event event;

    public LogoutFragment()
    {
        // Required empty public constructor

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        fragmentLogoutBinding = fragmentLogoutBinding.inflate(inflater, container, false);

        return fragmentLogoutBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();

        AlertDialog.Builder alert = new AlertDialog.Builder(requireContext());
        alert.setCancelable(false);

        alert.setMessage("Are sure you want to logout?")
                .setTitle("Logout")
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        NavController navController = Navigation.findNavController(view);
                        navController.navigate(R.id.action_logout_to_announcementFragment);
                       /* navController.navigate(R.id.announcementFragment);*/
                        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
                        dialog.cancel();
                    }
                })
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        Backendless.UserService.logout(new AsyncCallback<Void>() {
                            @Override
                            public void handleResponse(Void response)
                            {
                                Toast.makeText(requireContext(), "Successfully logged out", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent((requireContext()), LoginActivity.class));
                                UserIdStorageFactory.instance().getStorage().set(null);
                                requireActivity().finish();
                            }

                            @Override
                            public void handleFault(BackendlessFault fault)
                            {
                                Toast.makeText((requireContext()),  "Error: " + fault.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                })
                .create().show();

    }

}
