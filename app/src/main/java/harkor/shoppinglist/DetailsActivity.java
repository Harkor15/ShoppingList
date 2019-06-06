package harkor.shoppinglist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import harkor.shoppinglist.adapters.DetailsItemListAdapter;
import harkor.shoppinglist.interfaces.SuccesOperationOnDBInterface;
import harkor.shoppinglist.services.FirestoreDbController;

public class DetailsActivity extends AppCompatActivity implements SuccesOperationOnDBInterface {
    @BindView(R.id.delete_button) Button deleteButton;
    @BindView(R.id.shop_name_text) TextView shopName;
    @BindView(R.id.money_text) TextView budgetMoney;
    @BindView(R.id.item_list_view) ListView itemListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);
        Bundle bundle=getIntent().getExtras();

        budgetMoney.setText(bundle.getFloat("budget")+"");
        shopName.setText(bundle.getString("shop_name"));
        ArrayList<String> productsList=bundle.getStringArrayList("array_list");
        Log.d("sld",productsList.size()+"");
        itemListView.setAdapter(new DetailsItemListAdapter(productsList,getApplicationContext()));
        SuccesOperationOnDBInterface succesOperationOnDBInterface = this;
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirestoreDbController firestoreDbController=new FirestoreDbController(bundle.getString("uid"));
                firestoreDbController.deleteShoppingList(bundle.getString("document_id"), succesOperationOnDBInterface);
            }
        });
    }

    @Override
    public void deleteCompleted() {
        finish();
    }
}
