package com.corel.corelfullapp.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.corel.corelfullapp.R;
import com.corel.corelfullapp.dao.DataBaseRoom;
import com.corel.corelfullapp.entites.Product;

public class ProductDetailActivity extends AppCompatActivity {

    private Product product;
    final static int MAIN_CALL = 123;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        product = (Product)getIntent().getSerializableExtra("product");
        updateView(product);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = new MenuInflater(this);
        menuInflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == MAIN_CALL && resultCode == RESULT_OK) {
            assert data != null;
            if (data.hasExtra("product")) {
                product = (Product) data.getExtras().getSerializable("product");
                updateView(product);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        Intent intent = getIntent();
        setResult(RESULT_OK, intent);
        finish();
        super.onBackPressed();
    }

    public void deleteProduct(){
        new Thread(() -> {
            DataBaseRoom.getInstance(getApplicationContext()).productRoomDao().delete(product);
            Intent intent = getIntent();
            setResult(RESULT_OK, intent);
            finish();
        }).start();

    }

    public void modifyProduct(){
        Intent intent = new Intent(ProductDetailActivity.this, MainActivity.class);
        intent.putExtra("product",product);
        startActivityIfNeeded(intent, MAIN_CALL);
    }

    @SuppressLint("SetTextI18n")
    public void updateView(Product product){
        if (product != null) {
            ((TextView) findViewById(R.id.name_info)).setText(product.name);
            ((TextView) findViewById(R.id.description_info)).setText(product.description);
            ((TextView) findViewById(R.id.price_info)).setText(product.price + " F CFA");
            ((TextView) findViewById(R.id.quantity_info)).setText(Double.toString(product.quantityInStock));
            ((TextView) findViewById(R.id.alert_quantity_info)).setText(Double.toString(product.alertQuantity));
        }
    }
}