package phamhungan.com.phonetestv3.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import phamhungan.com.phonetestv3.R;
import phamhungan.com.phonetestv3.model.Item;
import phamhungan.com.phonetestv3.util.ResizeBitmap;

/**
 * Created by MrAn PC on 27-Jan-16.
 */
public class ResultAdapter extends BaseAdapter {
    private final String PASS = "PASSED";
    private final String SKIP = "SKIPPED";
    private final String FAIL = "FAILED";

    private Context context;
    private float screenWidth;
    private View view;
    private Item[] listItem;
    private static LayoutInflater inflater = null;
    private String[] resultArray;

    public ResultAdapter(Item[] listItem,String[] resultArray,Context context,float screenWidth){
        this.context = context;
        this.listItem = listItem;
        this.resultArray = resultArray;
        this.screenWidth = screenWidth;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return listItem.length;
    }

    @Override
    public Object getItem(int position) {
        return listItem[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public String resultItem(int position){
        return resultArray[position];
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        view = convertView;
        Hodler holder;
        if (view == null) {
            view = inflater.inflate(R.layout.row_item_result, null);
            holder = new Hodler(view);
            view.setTag(holder);
        }
        holder = (Hodler)view.getTag();
        Item item = (Item)getItem(position);
        holder.txtItem.setText(item.getItemName());
        holder.txtResult.setText(resultItem(position));
        String result = resultItem(position);
        switch (result){
            case PASS:
                holder.txtResult.setTextColor(context.getResources().getColor(R.color.colorGreenDark));
                break;
            case SKIP:
                holder.txtResult.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                break;
            case FAIL:
                holder.txtResult.setTextColor(context.getResources().getColor(R.color.colorAccent));
                break;
        }
        new LoadImgItemAsync(item,holder).execute(new String[]{});

        return view;
    }

    private class Hodler{
        public ImageView imgItem;
        public TextView txtItem;
        public ProgressBar pbItem;
        public TextView txtResult;
        public Hodler(View v){
            imgItem = (ImageView)v.findViewById(R.id.imgItem);
            txtItem = (TextView)v.findViewById(R.id.txtItem);
            txtResult = (TextView)v.findViewById(R.id.txtResult);
            pbItem = (ProgressBar)v.findViewById(R.id.pbItem);
        }
    }

    private class LoadImgItemAsync extends AsyncTask<String,Void,Bitmap> {

        private Item item;
        private Hodler hodler;

        public LoadImgItemAsync(Item item,Hodler hodler){
            this.item = item;
            this.hodler = hodler;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            hodler.pbItem.setVisibility(View.VISIBLE);
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap bp = ResizeBitmap.resize(BitmapFactory.decodeResource(context.getResources(), item.getIdImage()), screenWidth / 20);
            return bp;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            hodler.pbItem.setVisibility(View.GONE);
            hodler.imgItem.setImageBitmap(bitmap);
        }
    }
}
