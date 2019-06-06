package harkor.shoppinglist.adapters;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import harkor.shoppinglist.R;
import harkor.shoppinglist.interfaces.ItemsListDeleteInterface;

public class ItemListAdapter extends BaseAdapter {
    Context context;
    ArrayList<String> listItems;
    ItemsListDeleteInterface itemsListDeleteInterface;

    public ItemListAdapter(Context context, ArrayList<String> listItems, ItemsListDeleteInterface itemsListDeleteInterface) {
        this.context = context;
        this.listItems = listItems;
        this.itemsListDeleteInterface = itemsListDeleteInterface;
    }

    @Override
    public int getCount() {
        return  listItems.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return ((long) i);
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater= LayoutInflater.from(context);
        View listRow =layoutInflater.inflate(R.layout.list_item_row,viewGroup,false);
        TextView textProduct=listRow.findViewById(R.id.item_product_text);
        textProduct.setText(listItems.get(position));
        ImageView deleteButton=listRow.findViewById(R.id.delete_icon);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemsListDeleteInterface.deleteItem(position);
            }
        });
        return listRow;
    }
}
