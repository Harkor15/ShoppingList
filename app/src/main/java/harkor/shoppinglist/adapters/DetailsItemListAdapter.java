package harkor.shoppinglist.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import harkor.shoppinglist.R;

public class DetailsItemListAdapter extends BaseAdapter {
    ArrayList<String> products;
    Context context;

    public DetailsItemListAdapter(ArrayList<String> products,Context context) {
        this.products = products;
        this.context=context;
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Object getItem(int i) {
        return products.get(i);
    }

    @Override
    public long getItemId(int i) {
        return (long)i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater= LayoutInflater.from(context);
        View listRow = layoutInflater.inflate(R.layout.details_list_single_item, viewGroup, false);
        TextView singleItem=listRow.findViewById(R.id.single_item);
        Log.d("sld", products.get(i));
        singleItem.setText(products.get(i));
        return listRow;
    }
}
