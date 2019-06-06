package harkor.shoppinglist;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import harkor.shoppinglist.adapters.ItemListAdapter;
import harkor.shoppinglist.interfaces.ItemsListDeleteInterface;
import harkor.shoppinglist.interfaces.SuccesOperationOnDBInterface;
import harkor.shoppinglist.services.FirestoreDbController;

public class AddShoppingListActivity extends AppCompatActivity implements ItemsListDeleteInterface, SuccesOperationOnDBInterface {
    @BindView(R.id.product_edit) EditText productEdit;
    @BindView(R.id.shop_name_edit) EditText shopNameEdit;
    @BindView(R.id.amount_edit) EditText amountEdit;
    @BindView(R.id.added_items_list) ListView addedItemsList;
    @BindView(R.id.add_product_button) FloatingActionButton addProductButton;
    @BindView(R.id.done_button) Button doneButton;

    private ArrayList<String> listItems=new ArrayList<>();
    private ItemListAdapter itemListAdapter = new ItemListAdapter(this,listItems,this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shopping_list);
        ButterKnife.bind(this);
        addedItemsList.setAdapter(itemListAdapter);
        SuccesOperationOnDBInterface succesOperationOnDBInterface=this;
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Float amount;
                if (amountEdit.getText().toString()=="") {
                    amount = Float.valueOf("0");
                } else {
                    amount = Float.valueOf(amountEdit.getText().toString());
                }
                String shopName=shopNameEdit.getText().toString();
                if(shopName!=""&&!listItems.isEmpty()){
                    FirebaseAuth mAuth= FirebaseAuth.getInstance();
                    FirestoreDbController firestoreDbController=new FirestoreDbController(mAuth.getUid());
                    firestoreDbController.addNewShoppingList(shopName,amount,listItems,succesOperationOnDBInterface);
                }
            }
        });
                addProductButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        addItem(productEdit.getText().toString());
                        productEdit.setText("");
                    }
                });
            }

    private void addItem(String item){
        listItems.add(item);
        itemListAdapter.notifyDataSetChanged();
    }

    @Override
    public void deleteItem(int id) {
        listItems.remove(id);
        itemListAdapter.notifyDataSetChanged();
    }

    @Override
    public void deleteCompleted() {
        finish();
    }

}
