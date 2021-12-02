package com.corel.corelfullapp.ui.showproduct;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.corel.corelfullapp.dao.DataBaseRoom;
import com.corel.corelfullapp.dao.ProductRoomDao;
import com.corel.corelfullapp.entites.Product;

public class ProductDetailViewModel extends AndroidViewModel {
    // TODO: Implement the ViewModel

    private final ProductRoomDao productRoomDao;

    public ProductDetailViewModel(Application application) {
        super(application);
        productRoomDao = DataBaseRoom.getInstance(application).productRoomDao();
    }

    public void deleteProduct(Product product){
        new Thread(() -> {
            productRoomDao.delete(product);
        }).start();
    }
}