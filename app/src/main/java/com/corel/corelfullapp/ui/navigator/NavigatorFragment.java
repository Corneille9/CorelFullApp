package com.corel.corelfullapp.ui.navigator;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.corel.corelfullapp.R;
import com.corel.corelfullapp.adapter.ProductAdapter;
import com.corel.corelfullapp.databinding.NavigatorFragmentBinding;
import com.corel.corelfullapp.entites.Product;

import java.util.ArrayList;
import java.util.List;

public class NavigatorFragment extends Fragment {

    private NavigatorViewModel mViewModel;
    private final List<Product> products = new ArrayList<>();
    private ProductAdapter productAdapter;
    private NavigatorFragmentBinding binding;


    public static NavigatorFragment newInstance() {
        return new NavigatorFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = NavigatorFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.fab.setOnClickListener(view1 -> NavHostFragment.findNavController(NavigatorFragment.this).navigate(R.id.create_navigatorFragment_to_editProductFragment));

        buildCustomAdapter();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onStart() {
        super.onStart();
        if (products.isEmpty()) {
            mViewModel.loadProducts();
        }else {
            Bundle args = getArguments();
            if (args != null){
                Product product = (Product) args.getSerializable("product");
                if (args.containsKey("product")){
                    products.add(product);
                    productAdapter.notifyDataSetChanged();
                }else if (args.containsKey("deletedProduct")){
                    products.removeIf(product::equals);
                    productAdapter.notifyDataSetChanged();
                }
            }
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(NavigatorViewModel.class);
        // TODO: Use the ViewModel
        mViewModel.mutableLiveData.observe(getViewLifecycleOwner(), (productslists -> {
            products.addAll(productslists);
            productAdapter.notifyDataSetChanged();
        }));
    }

    private void buildCustomAdapter() {
        productAdapter = new ProductAdapter(getContext(), products);
        binding.ourListView.setAdapter(productAdapter);
        binding.ourListView.setOnItemClickListener((adapterView, view, i, l) -> {
            // i: Position , l: id
            Product product = (Product)binding.ourListView.getItemAtPosition(i);
            Bundle bundle = new Bundle();
            bundle.putSerializable("product", product);
            NavHostFragment.findNavController(NavigatorFragment.this).navigate(R.id.show_action_navigatorFragment_to_productDetailFragment, bundle);
        });
    }

}