package edu.uic.cs478.f2020.rakula3.project2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import androidx.core.app.NavUtils;

import static android.content.Intent.CATEGORY_BROWSABLE;

public class ImageViewActivity extends Activity {
    String url;
    ImageView imageView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();

        imageView = new ImageView(getApplicationContext());
        imageView.setImageResource(intent.getIntExtra(MainActivity.EXTRA_RESOURCE_ID, 0));
        setContentView(imageView);

        String openUrlFlag = intent.getStringExtra(MainActivity.EXTRA_OPEN_URL_FLAG);

        if( openUrlFlag.equals("YES") ) {
            url = intent.getStringExtra(MainActivity.EXTRA_MANUFACTURER_URL);
            imageView.setOnClickListener(onImageClicked);
        }
    }

    public View.OnClickListener onImageClicked = new View.OnClickListener() {
        @Override
            public void onClick(View v) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            intent.addCategory(CATEGORY_BROWSABLE);
            startActivity(intent);
        }
    };

    @Override
    public void onBackPressed() {
        imageView = null;
        url = null;
        NavUtils.navigateUpFromSameTask(this);
    }
}
