package com.corel.corelfullapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.corel.corelfullapp.entites.Product;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final String TAG = MainActivity.class.getCanonicalName();

    private TextInputEditText designationEditText;
    private TextInputEditText descriptionEditText;
    private TextInputEditText priceEditText;
    private TextInputEditText quantityInStockEditText;
    private TextInputEditText alertQuantityEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        designationEditText = findViewById(R.id.name);
        descriptionEditText = findViewById(R.id.description);
        priceEditText = findViewById(R.id.price);
        quantityInStockEditText = findViewById(R.id.quantity_in_stock);
        alertQuantityEditText = findViewById(R.id.alert_quantity);

        findViewById(R.id.my_btn).setOnClickListener(this::saveProduct);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = new MenuInflater(this);
        menuInflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void saveProduct(View view) {

        if (isEmptyInput(designationEditText, false)){
            Toast.makeText(getApplicationContext(), "Le nom de produit est vide", Toast.LENGTH_SHORT).show();
        }else if (isEmptyInput(descriptionEditText, false)){
            Toast.makeText(getApplicationContext(), "La description du produit est vide", Toast.LENGTH_SHORT).show();
        }else if (isEmptyInput(priceEditText, true)){
            Toast.makeText(getApplicationContext(), "Le prix du produit est invalid", Toast.LENGTH_SHORT).show();
        }else if (isEmptyInput(quantityInStockEditText, true)){
            Toast.makeText(getApplicationContext(), "Le quantité du produit est invalid", Toast.LENGTH_SHORT).show();
        }else if (isEmptyInput(alertQuantityEditText, true)){
            Toast.makeText(getApplicationContext(), "La quantité d'alert du produit est invalid", Toast.LENGTH_SHORT).show();
        }else if (Double.parseDouble(Objects.requireNonNull(alertQuantityEditText.getText()).toString()) > Double.parseDouble(Objects.requireNonNull(quantityInStockEditText.getText()).toString())){
            Toast.makeText(getApplicationContext(), "La quantité d'alert du produit est invalid", Toast.LENGTH_SHORT).show();
        }else {
            Product product = new Product();
            product.name = Objects.requireNonNull(designationEditText.getText()).toString();
            product.description = Objects.requireNonNull(descriptionEditText.getText()).toString();
            product.price = Double.parseDouble(Objects.requireNonNull(priceEditText.getText()).toString());
            product.quantityInStock = Double.parseDouble(Objects.requireNonNull(quantityInStockEditText.getText()).toString());
            product.alertQuantity = Double.parseDouble(Objects.requireNonNull(alertQuantityEditText.getText()).toString());
            Log.e(TAG, "saveProduct: " + product);
            Toast.makeText(getApplicationContext(), "Produit enregistré", Toast.LENGTH_SHORT).show();

            Intent intent = getIntent();
            intent.putExtra("product", product);
            setResult(RESULT_OK, intent);
            finish();
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

    @Override
    public void onClick(View view) {

    }
}