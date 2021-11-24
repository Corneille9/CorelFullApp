package com.corel.corelfullapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.corel.corelfullapp.adapter.ProductAdapter;
import com.corel.corelfullapp.dao.DataBaseRoom;
import com.corel.corelfullapp.dao.ProductRoomDao;
import com.corel.corelfullapp.databinding.ActivityProductBinding;
import com.corel.corelfullapp.entites.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductActivity extends AppCompatActivity {

    private ActivityProductBinding binding;
    private List<Product> products = new ArrayList<>();
    private ProductAdapter productAdapter;
    final static int MAIN_CALL = 120;
    final static int PRODUCT_DETAIL_CALL = 122;

//    private DataBaseHelper dataBaseHelper;
    private static final String TAG = "ProductActivity";

//    private ProductDao productDao = new ProductDao(this);
    private ProductRoomDao productRoomDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        binding.fab.setOnClickListener(view -> {
            Intent intent = new Intent(ProductActivity.this, MainActivity.class);
            startActivityIfNeeded(intent, MAIN_CALL);
        });

        buildCustomAdapter();

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (productRoomDao == null) {
            productRoomDao = DataBaseRoom.getInstance(this).productRoomDao();
            new Thread(new Runnable() {
                final List<Product> localProducts = new ArrayList<>();

                @Override
                public void run() {
                    localProducts.addAll(productRoomDao.findAll());
                    runOnUiThread(() -> {
                        products.addAll(localProducts);
                        productAdapter.notifyDataSetChanged();
                    });
                }
            }).start();
        }
//        dataBaseHelper = new DataBaseHelper(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        new Thread(new Runnable() {
            final List<Product> localProducts = new ArrayList<>();
            @Override
            public void run() {
                localProducts.addAll(productRoomDao.findAll());
                runOnUiThread(() -> {
                    products.clear();
                    products.addAll(localProducts);
                    productAdapter.notifyDataSetChanged();
                });
            }
        }).start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void buildCustomAdapter() {
        productAdapter = new ProductAdapter(this, products);
        binding.ourListView.setAdapter(productAdapter);
        binding.ourListView.setOnItemClickListener((adapterView, view, i, l) -> {
            // i: Position , l: id
            Product product = (Product)binding.ourListView.getItemAtPosition(i);
            Intent intent = new Intent(ProductActivity.this, ProductDetailActivity.class);
            intent.putExtra("product", product);
            startActivityIfNeeded(intent, PRODUCT_DETAIL_CALL);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == MAIN_CALL && resultCode == RESULT_OK) {
            // TODO: 18/11/21 Insertion des produits dans la listview
            assert data != null;
            if (data.hasExtra("product")) {
                Product product = (Product) data.getExtras().getSerializable("product");
                products.add(product);
                buildCustomAdapter();
            }
        }else if (requestCode == PRODUCT_DETAIL_CALL && resultCode == RESULT_OK){
            new Thread(new Runnable() {
                final List<Product> localProducts = new ArrayList<>();

                @Override
                public void run() {
                    localProducts.addAll(productRoomDao.findAll());
                    runOnUiThread(() -> {
                        products.clear();
                        products.addAll(localProducts);
                        productAdapter.notifyDataSetChanged();
                    });
                }
            }).start();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}