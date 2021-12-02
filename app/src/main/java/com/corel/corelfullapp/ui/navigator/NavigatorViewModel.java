package com.corel.corelfullapp.ui.navigator;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.corel.corelfullapp.dao.DataBaseRoom;
import com.corel.corelfullapp.dao.ProductDao;
import com.corel.corelfullapp.dao.ProductRoomDao;
import com.corel.corelfullapp.entites.Product;

import java.util.List;

public class NavigatorViewModel extends AndroidViewModel {
    // TODO: Implement the ViewModel
    private final ProductDao productDao;
    private final ProductRoomDao productRoomDao;
    protected MutableLiveData<List<Product>> mutableLiveData = new MutableLiveData<>();

    public NavigatorViewModel(Application application) {
        super(application);
        productDao = new ProductDao(application);
        productRoomDao = DataBaseRoom.getInstance(application).productRoomDao();
    }

    public void loadProducts(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                mutableLiveData.postValue(productRoomDao.findAll());
            }
        }).start();
    }
}