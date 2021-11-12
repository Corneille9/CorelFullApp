package com.corel.corelfullapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.corel.corelfullapp.entites.Product;

public class ProductDetailActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        Product product = (Product)getIntent().getSerializableExtra("product");
        ((TextView)findViewById(R.id.name_info)).setText(product.name);
        ((TextView)findViewById(R.id.description_info)).setText(product.description);
        ((TextView)findViewById(R.id.price_info)).setText(product.price + " FCFA");
        ((TextView)findViewById(R.id.quantity_info)).setText(Double.toString(product.quantityInStock));
        ((TextView)findViewById(R.id.alert_quantity_info)).setText(Double.toString(product.alertQuantity));

    }
}