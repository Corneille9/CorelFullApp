package com.corel.corelfullapp.ui.navigator;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.corel.corelfullapp.dao.DataBaseRoom;
import com.corel.corelfullapp.dao.ProductDao;
import com.corel.corelfullapp.dao.ProductRoomDao;
import com.corel.corelfullapp.entites.Product;
import com.corel.corelfullapp.webservices.ProductWebService;

import java.util.List;

public class NavigatorViewModel extends AndroidViewModel {
    // TODO: Implement the ViewModel
    private final ProductDao productDao;
    private final ProductRoomDao productRoomDao;
    protected MutableLiveData<List<Product>> mutableLiveData = new MutableLiveData<>();
    ProductWebService productWebService = new ProductWebService();

    public NavigatorViewModel(Application application) {
        super(application);
        productDao = new ProductDao(application);
        productRoomDao = DataBaseRoom.getInstance(application).productRoomDao();
    }

    public void loadProducts(){
        Log.e("TAG", "loadProduct: ");
        new Thread(() -> {
            productWebService = new ProductWebService();
            mutableLiveData.postValue(productWebService.getProducts());
            mutableLiveData.postValue(productRoomDao.findAll());
        }).start();
    }
}