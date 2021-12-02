package com.corel.corelfullapp.ui.editproduct;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;

import com.corel.corelfullapp.dao.DataBaseRoom;
import com.corel.corelfullapp.dao.ProductRoomDao;
import com.corel.corelfullapp.entites.Product;
import com.corel.corelfullapp.webservices.ProductWebService;

public class EditProductViewModel extends AndroidViewModel {
    private static final String TAG = "EditProductViewModel";
    // TODO: Implement the ViewModel
    private final ProductRoomDao productRoomDao;
    ProductWebService productWebService;


    public EditProductViewModel(Application application) {
        super(application);
        productRoomDao = DataBaseRoom.getInstance(application).productRoomDao();
        productWebService = new ProductWebService();
    }

    public void saveProduct(Product product){
        new Thread(() -> {
            if (productWebService.createProduct(product) != null) {
                Log.e(TAG, "saveProduct: Product is saving");
                productRoomDao.insert(product);
                product.id = productRoomDao.findByName(product.name, product.description).get(0).id;
            }
        }).start();
    }

    public void updateProduct(Product product, int id){
        product.id = id;
        new Thread(() -> productRoomDao.update(product)).start();
    }
}