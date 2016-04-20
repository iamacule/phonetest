package phamhungan.com.phonetestv3.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
 * Created by MrAn PC on 22-Jan-16.
 */
public class SingleTestAdapter extends BaseAdapter {
    private Context context;
    private float screenWidth;
    private View view;
    private Item[] listItem;
    private static LayoutInflater inflater = null;

    public SingleTestAdapter(Item[] listItem,Context context,float screenWidth){
        this.context = context;
        this.listItem = listItem;
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        view = convertView;
        Hodler holder;
        if (view == null) {
            view = inflater.inflate(R.layout.row_item, null);
            holder = new Hodler(view);
            view.setTag(holder);
        }
        holder = (Hodler)view.getTag();
        Item item = (Item)getItem(position);
        holder.txtItem.setText(item.getItemName());
        new LoadImgItemAsync(item,holder).execute(new String[]{});

        return view;
    }

    private class Hodler{
        public ImageView imgItem;
        public TextView txtItem;
        public ProgressBar pbItem;
        public Hodler(View v){
            imgItem = (ImageView)v.findViewById(R.id.imgItem);
            txtItem = (TextView)v.findViewById(R.id.txtItem);
            pbItem = (ProgressBar)v.findViewById(R.id.pbItem);
        }
    }

    private class LoadImgItemAsync extends AsyncTask<String,Void,Bitmap>{

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
            Bitmap bp = ResizeBitmap.resize(BitmapFactory.decodeResource(context.getResources(),item.getIdImage()),screenWidth/20);
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
