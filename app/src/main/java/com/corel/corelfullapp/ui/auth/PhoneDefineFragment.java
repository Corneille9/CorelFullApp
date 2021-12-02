package com.corel.corelfullapp.ui.auth;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.corel.corelfullapp.R;
import com.corel.corelfullapp.databinding.CodeVerificationFragmentBinding;
import com.corel.corelfullapp.databinding.PhoneDefineFragmentBinding;
import com.corel.corelfullapp.ui.AuthCallback;

import java.util.Objects;

public class PhoneDefineFragment extends Fragment {

    private PhoneDefineFragmentBinding binding;
    private String phoneNum;
    private AuthCallback authCallback;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = PhoneDefineFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phoneNum = binding.phoneNumber.getText().toString();
                if(phoneNum.trim().isEmpty()){
                    Toast.makeText(getContext(), "Le numero de telephone est obligatoire", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(authCallback!=null)
                    authCallback.sendMessage(phoneNum);
//                NavHostFragment.findNavController(PhoneDefineFragment.this)
//                        .navigate(R.id.action_First2Fragment_to_Second2Fragment);
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof AuthCallback) {
            authCallback = (AuthCallback) context;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}