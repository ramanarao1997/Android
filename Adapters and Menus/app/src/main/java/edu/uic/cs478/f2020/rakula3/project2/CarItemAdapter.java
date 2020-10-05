package edu.uic.cs478.f2020.rakula3.project2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CarItemAdapter extends BaseAdapter {
    private Context mContext;
    private int mViewResourceId;
    private List<Integer> mCarThumbIds;
    private List<String> mCarNames;

    public CarItemAdapter(Context context, int viewResourceId, List<Integer> thumbIds, List<String> names) {
        mContext = context;
        mViewResourceId = viewResourceId;
        mCarThumbIds = thumbIds;
        mCarNames = names;
    }

    @Override
    public int getCount() {
        return mCarThumbIds.size();
    }

    @Override
    public Object getItem(int position) {
        return mCarThumbIds.get(position);
    }

    @Override
    public long getItemId(int position) { return mCarThumbIds.get(position); }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            LayoutInflater mInflator = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mInflator.inflate(mViewResourceId, null);
        }

        ImageView imageView = convertView.findViewById(R.id.iv_car_thumb);
        TextView textView =  convertView.findViewById(R.id.tv_car_name);

        imageView.setImageResource(mCarThumbIds.get(position));
        textView.setText(mCarNames.get(position));

        return convertView;
    }
}
