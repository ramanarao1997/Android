package edu.uic.cs478.f2020.rakula3.project2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.security.auth.login.LoginException;

public class CarSellerInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.car_sellers_info_activity);

        Intent intent = getIntent();
        String[] sellersInfo = intent.getStringArrayExtra(MainActivity.EXTRA_SELLERS_INFO);
        String carName = intent.getStringExtra(MainActivity.EXTRA_CAR_NAME);

        ArrayList<HashMap<String, String>> sellersInfoList = new ArrayList<>();
        String []dataKeys = {"sellerName", "sellerAddress"};
        int []viewsToMap = {R.id.tv_seller_name, R.id.tv_seller_address};

        for (String s : sellersInfo) {
            String[] nameAndAddress = s.split(" @ ");

            HashMap<String, String> info = new HashMap<>();

            info.put("sellerName", nameAndAddress[0]);
            info.put("sellerAddress", nameAndAddress[1]);

            sellersInfoList.add(info);
        }

        TextView tvInfo = findViewById(R.id.tv_sellers_for_car);
        tvInfo.setText("Sellers in Chicago for " + carName);
        ListView listView = findViewById(R.id.listview);
        listView.setAdapter(new SimpleAdapter(this, sellersInfoList, R.layout.car_seller_list_item, dataKeys, viewsToMap));
    }
}