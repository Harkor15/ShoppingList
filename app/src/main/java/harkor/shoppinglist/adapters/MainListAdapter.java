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
import harkor.shoppinglist.interfaces.CustomAdapterInterface;
import harkor.shoppinglist.model.ShoppingListModel;

public class MainListAdapter extends BaseAdapter {
    Context context;
    ArrayList<ShoppingListModel> shoppingListModelArrayList;
    CustomAdapterInterface customAdapterInterface;

    public MainListAdapter(Context context, ArrayList<ShoppingListModel> shoppingListModelArrayList, CustomAdapterInterface customAdapterInterface) {
        this.context = context;
        this.shoppingListModelArrayList = shoppingListModelArrayList;
        this.customAdapterInterface = customAdapterInterface;
    }

    @Override
    public int getCount() {
        return shoppingListModelArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return (long) i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater= LayoutInflater.from(context);
        View listRow=layoutInflater.inflate(R.layout.list_row,viewGroup,false);
        TextView shopName=listRow.findViewById(R.id.shop_name);
        shopName.setText(shoppingListModelArrayList.get(position).getShopName());
        TextView budget=listRow.findViewById(R.id.bugdet);
        budget.setText("$ "+shoppingListModelArrayList.get(position).getBudget());
        listRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customAdapterInterface.openDetailsActivity(shoppingListModelArrayList.get(position));
            }
        });
        return listRow;
    }
}
