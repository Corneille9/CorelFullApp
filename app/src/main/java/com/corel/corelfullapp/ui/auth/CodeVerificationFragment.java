package com.corel.corelfullapp.ui.auth;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.corel.corelfullapp.databinding.CodeVerificationFragmentBinding;
import com.corel.corelfullapp.ui.AuthCallback;

import java.util.Objects;

public class CodeVerificationFragment extends Fragment {

    private CodeVerificationFragmentBinding binding;
    private String phoneNumber;
    private String code;
    private AuthCallback authCallback;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = CodeVerificationFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("TAG", "onViewCreated: " + getArguments().getString("phoneNumber"));
        phoneNumber = getArguments().getString("phoneNumber");
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        binding.buttonSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                code = binding.code.getText().toString();
                if(code.trim().isEmpty()){
                    Toast.makeText(getContext(), "Le code est obligatoire", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(code.trim().length()!=6){
                    Toast.makeText(getContext(), "Le nombre de chiffre du code est de 6 caractere", Toast.LENGTH_SHORT).show();
                    return;
                }
                Log.e("TAG", "onClick: " + code );
                authCallback.verification(code);

//                NavHostFragment.findNavController(CodeVerificationFragment.this)
//                        .navigate(R.id.action_Second2Fragment_to_First2Fragment);
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