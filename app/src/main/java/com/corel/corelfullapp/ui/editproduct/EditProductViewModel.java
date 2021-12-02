package com.corel.corelfullapp.ui.editproduct;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.corel.corelfullapp.dao.DataBaseRoom;
import com.corel.corelfullapp.dao.ProductRoomDao;
import com.corel.corelfullapp.entites.Product;

public class EditProductViewModel extends AndroidViewModel {
    // TODO: Implement the ViewModel
    private final ProductRoomDao productRoomDao;


    public EditProductViewModel(Application application) {
        super(application);
        productRoomDao = DataBaseRoom.getInstance(application).productRoomDao();
    }

    public void saveProduct(Product product){
        new Thread(() -> {
            productRoomDao.insert(product);
            product.id = productRoomDao.findByName(product.name, product.description).get(0).id;
        }).start();
    }

    public void updateProduct(Product product, int id){
        product.id = id;
        new Thread(() -> productRoomDao.update(product)).start();
    }
}