package edu.uic.cs478.f2020.rakula3.project2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static android.content.Intent.CATEGORY_BROWSABLE;

public class MainActivity extends AppCompatActivity {
    protected static final String EXTRA_RESOURCE_ID = "RESOURCE_ID";
    protected static final String EXTRA_MANUFACTURER_URL = "MANUFACTURER_URL";
    protected static final String EXTRA_SELLERS_INFO = "SELLERS_INFO";
    protected static final String EXTRA_CAR_NAME = "CAR_NAME";
    protected static final String EXTRA_OPEN_URL_FLAG = "OPEN_URL_FLAG";

    protected ArrayList<Integer> carThumbs = new ArrayList<Integer> (
            Arrays.asList(R.drawable.audi_q7_3t_thumb, R.drawable.dodge_dart_sxt_thumb,
                    R.drawable.chevrolet_impala_thumb, R.drawable.chevrolet_malibu_thumb,
                    R.drawable.honda_civic_thumb, R.drawable.toyota_camry_thumb));

    protected ArrayList<Integer> carImages = new ArrayList<Integer> (
            Arrays.asList(R.drawable.audi_q7_3t, R.drawable.dodge_dart_sxt,
                    R.drawable.chevrolet_impala, R.drawable.chevrolet_malibu,
                    R.drawable.honda_civic, R.drawable.toyota_camry));

    protected List<String> carNames;
    protected List<String> carManufacturerLinks;

    protected List<String> carSellers1;
    protected List<String> carSellers2;
    protected List<String> carSellers3;
    protected HashMap<Integer, List<String>> carSellersInfo = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Context context = getApplicationContext();
        Resources resources = context.getResources();

        carNames = new ArrayList<String>(Arrays.asList(resources.getStringArray(R.array.car_names)));
        carManufacturerLinks = new ArrayList<String>(Arrays.asList(resources.getStringArray(R.array.car_manufacturer_links)));
        carSellers1 = new ArrayList<>(Arrays.asList(resources.getStringArray(R.array.car_sellers1)));
        carSellers2 = new ArrayList<>(Arrays.asList(resources.getStringArray(R.array.car_sellers2)));
        carSellers3 = new ArrayList<>(Arrays.asList(resources.getStringArray(R.array.car_sellers3)));

        for(int  i  = 0; i < carNames.size(); i++) {
            carSellersInfo.put(i, new ArrayList<String>());

            carSellersInfo.get(i).add(carSellers1.get(i));
            carSellersInfo.get(i).add(carSellers2.get(i));
            carSellersInfo.get(i).add(carSellers3.get(i));
        }

        GridView gridview = findViewById(R.id.gridview);
        gridview.setAdapter(new CarItemAdapter(this, R.layout.car_grid_item, carThumbs, carNames));

        registerForContextMenu(gridview);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(MainActivity.this, ImageViewActivity.class);
            intent.putExtra(EXTRA_RESOURCE_ID, carImages.get(position));
            intent.putExtra(EXTRA_OPEN_URL_FLAG, "YES");
            intent.putExtra(EXTRA_MANUFACTURER_URL, carManufacturerLinks.get(position));
            startActivity(intent);
            }
        });
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;

        // (int groupId, int itemId, int order, CharSequence title)
        menu.add(Menu.NONE, 1, 1, "View picture in full screen");
        menu.add(Menu.NONE, 2, 2, "Go to manufacturer's site");
        menu.add(Menu.NONE, 3, 3, "See car dealers");
    }

    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = info.position;

        Intent intent;

        switch (item.getItemId()) {
            case 1:
                intent = new Intent(MainActivity.this, ImageViewActivity.class);
                intent.putExtra(EXTRA_RESOURCE_ID, carImages.get(position));
                intent.putExtra(EXTRA_OPEN_URL_FLAG, "NO");
                startActivity(intent);
                return true;

            case 2:
                String url = carManufacturerLinks.get(position);
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                intent.addCategory(CATEGORY_BROWSABLE);
                startActivity(intent);
                return true;

            case 3:
                intent = new Intent(this, CarSellerInfoActivity.class);
                String []sellerInfo = new String[3];

                for(int i = 0; i < sellerInfo.length; i++)
                    sellerInfo[i] = carSellersInfo.get(position).get(i);

                intent.putExtra(EXTRA_SELLERS_INFO, sellerInfo);
                intent.putExtra(EXTRA_CAR_NAME, carNames.get(position));
                startActivity(intent);
                return true;

            default:
                return super.onContextItemSelected(item);
        }
    }
}