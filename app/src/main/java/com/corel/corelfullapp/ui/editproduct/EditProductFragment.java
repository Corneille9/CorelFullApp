 package com.corel.corelfullapp.ui.editproduct;

 import android.annotation.SuppressLint;
 import android.os.Bundle;
 import android.util.Log;
 import android.view.LayoutInflater;
 import android.view.View;
 import android.view.ViewGroup;
 import android.widget.Toast;

 import androidx.annotation.NonNull;
 import androidx.annotation.Nullable;
 import androidx.fragment.app.Fragment;
 import androidx.lifecycle.ViewModelProvider;
 import androidx.navigation.NavController;
 import androidx.navigation.NavOptions;
 import androidx.navigation.fragment.NavHostFragment;

 import com.corel.corelfullapp.R;
 import com.corel.corelfullapp.activities.MainActivity;
 import com.corel.corelfullapp.databinding.EditProductFragmentBinding;
 import com.corel.corelfullapp.entites.Product;
 import com.google.android.material.textfield.TextInputEditText;

 import java.util.Objects;

 public class EditProductFragment extends Fragment {

    private EditProductViewModel mViewModel;

    private final String TAG = MainActivity.class.getCanonicalName();

    private final int INSERT_ACTION = 0;
    private final int MODIFICATION_ACTION = 1;

    EditProductFragmentBinding binding;


    public static EditProductFragment newInstance() {
        return new EditProductFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = EditProductFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

     @Override
     public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
         super.onViewCreated(view, savedInstanceState);
         hasProductModificationAction();
     }

     @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(EditProductViewModel.class);
        // TODO: Use the ViewModel
    }

     @SuppressLint("SetTextI18n")
     public void hasProductModificationAction(){
         Bundle args = getArguments();
         if (args != null && args.containsKey("modifyProduct")) {
             Product product = (Product) args.getSerializable("modifyProduct");
             assert product != null;
             binding.myBtn.setOnClickListener(view -> this.saveProduct(MODIFICATION_ACTION, product.id));
             binding.myBtn.setText("Modifier");
             binding.name.setText(product.name);
             binding.description.setText(product.description);
             binding.price.setText(String.valueOf(product.price));
             binding.quantityInStock.setText(String.valueOf(product.quantityInStock));
             binding.alertQuantity.setText(String.valueOf(product.alertQuantity));
         }else {
             binding.myBtn.setOnClickListener(view -> this.saveProduct(INSERT_ACTION, null));
         }
     }

     public void saveProduct(int action, @Nullable Integer id) {

         if (isEmptyInput(binding.name, false)){
             Toast.makeText(getContext(), "Le nom de produit est vide", Toast.LENGTH_SHORT).show();
         }else if (isEmptyInput(binding.description, false)){
             Toast.makeText(getContext(), "La description du produit est vide", Toast.LENGTH_SHORT).show();
         }else if (isEmptyInput(binding.price, true)){
             Toast.makeText(getContext(), "Le prix du produit est invalid", Toast.LENGTH_SHORT).show();
         }else if (isEmptyInput(binding.quantityInStock, true)){
             Toast.makeText(getContext(), "Le quantité du produit est invalid", Toast.LENGTH_SHORT).show();
         }else if (isEmptyInput(binding.alertQuantity, true)){
             Toast.makeText(getContext(), "La quantité d'alert du produit est invalid", Toast.LENGTH_SHORT).show();
         }else if (Double.parseDouble(Objects.requireNonNull(binding.alertQuantity.getText()).toString()) > Double.parseDouble(Objects.requireNonNull(binding.quantityInStock.getText()).toString())){
             Toast.makeText(getContext(), "La quantité d'alert du produit est invalid", Toast.LENGTH_SHORT).show();
         }else {
             Product product = new Product();
             product.name = Objects.requireNonNull(binding.name.getText()).toString();
             product.description = Objects.requireNonNull(binding.description.getText()).toString();
             product.price = Double.parseDouble(Objects.requireNonNull(binding.price.getText()).toString());
             product.quantityInStock = Double.parseDouble(Objects.requireNonNull(binding.quantityInStock.getText()).toString());
             product.alertQuantity = Double.parseDouble(Objects.requireNonNull(binding.alertQuantity.getText()).toString());
             Log.e(TAG, "saveProduct: " + product);
             Toast.makeText(getContext(), "Produit enregistré", Toast.LENGTH_SHORT).show();

             if (action == INSERT_ACTION){
                 mViewModel.saveProduct(product);
                 Bundle bundle = new Bundle();
                 bundle.putSerializable("product", product);
                 NavHostFragment.findNavController(this).popBackStack();

//                 NavHostFragment.findNavController(this).navigate(R.id.action_editProductFragment_to_navigatorFragment, bundle);
             }else if (action == MODIFICATION_ACTION){
                 assert id != null;
                 mViewModel.updateProduct(product, id);
                 Bundle bundle = new Bundle();
                 bundle.putSerializable("product", product);
                 NavHostFragment.findNavController(this).popBackStack();

//                 NavHostFragment.findNavController(this).navigate(R.id.success_modification_action_editProductFragment_to_productDetailFragment, bundle);
             }
         }
     }

     public boolean isEmptyInput(TextInputEditText textInputEditText, boolean mustContainNumber){
         if (mustContainNumber){
             boolean isvalid;
             try {
                 Double.parseDouble(Objects.requireNonNull(textInputEditText.getText()).toString());
                 isvalid = true;
             }catch (NumberFormatException e){
                 isvalid = false;
             }
             return Objects.requireNonNull(textInputEditText.getText()).toString().isEmpty() || textInputEditText.getText().toString().matches("^\\s+") || !isvalid;
         }else return Objects.requireNonNull(textInputEditText.getText()).toString().isEmpty() || textInputEditText.getText().toString().matches("^\\s+");

     }
 }