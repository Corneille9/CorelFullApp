package com.corel.corelfullapp.ui.showproduct;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.corel.corelfullapp.R;
import com.corel.corelfullapp.databinding.NavigatorFragmentBinding;
import com.corel.corelfullapp.databinding.ProductDetailFragmentBinding;
import com.corel.corelfullapp.entites.Product;

public class ProductDetailFragment extends Fragment {

    private ProductDetailViewModel mViewModel;
    private Product product;
    final static int MAIN_CALL = 123;
    ProductDetailFragmentBinding binding;

    public static ProductDetailFragment newInstance() {
        return new ProductDetailFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = ProductDetailFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        Bundle args = getArguments();
        if (args != null){
            Product product = (Product) args.getSerializable("product");
            assert product != null;
            updateView(product);
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.add_menu_item:
                modifyProduct();
                break;
            case R.id.del_menu_item:
                deleteProduct();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ProductDetailViewModel.class);
        // TODO: Use the ViewModel
    }

    public void deleteProduct(){
        mViewModel.deleteProduct(product);
        Bundle bundle = new Bundle();
        bundle.putSerializable("deletedProduct", product);
        NavHostFragment.findNavController(ProductDetailFragment.this).navigate(R.id.delete_action_productDetailFragment_to_navigatorFragment, bundle);
    }

    public void modifyProduct(){
        Bundle bundle = new Bundle();
        bundle.putSerializable("modifyProduct", product);
        NavHostFragment.findNavController(ProductDetailFragment.this).navigate(R.id.modifiy_action_productDetailFragment_to_editProductFragment2, bundle);
    }

    @SuppressLint("SetTextI18n")
    public void updateView(Product product){
        if (product != null) {
            binding.nameInfo.setText(product.name);
            binding.descriptionInfo.setText(product.description);
            binding.priceInfo.setText(product.price + " F CFA");
            binding.quantityInfo.setText(Double.toString(product.quantityInStock));
            binding.alertQuantityInfo.setText(Double.toString(product.alertQuantity));
        }
    }
}