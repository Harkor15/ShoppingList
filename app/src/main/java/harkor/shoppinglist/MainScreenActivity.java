package harkor.shoppinglist;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import harkor.shoppinglist.adapters.MainListAdapter;
import harkor.shoppinglist.interfaces.CustomAdapterInterface;
import harkor.shoppinglist.interfaces.ListOfShoppingListsInterface;
import harkor.shoppinglist.model.ShoppingListModel;
import harkor.shoppinglist.services.FirestoreDbController;
import io.realm.Realm;

public class MainScreenActivity extends AppCompatActivity implements ListOfShoppingListsInterface, CustomAdapterInterface {
    private FirebaseAuth mAuth;
    FirestoreDbController firestoreDbController;
    @OnClick(R.id.exit_button) void logOut(){
        mAuth.signOut();
        finish();
    }
    @BindView(R.id.add_button) FloatingActionButton addNewListButton;
    @BindView(R.id.list_view) ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        ButterKnife.bind(this);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser==null){ //OFFLINE
            Log.d("sld current user", "null");
        }else{ //LOGGED IN
            Log.d("sld currentUser", currentUser.getUid());
            firestoreDbController= new FirestoreDbController(currentUser.getUid());
            addNewListButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(getApplicationContext(),AddShoppingListActivity.class);
                    startActivity(intent);
                }
            });
        }
    }

    @Override
    public void setListOfShoppingLists(ArrayList<ShoppingListModel> listData) {
        listView.setAdapter(new MainListAdapter(getApplicationContext(),listData,this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        firestoreDbController.getAllLists(this);
    }

    @Override
    public void openDetailsActivity(ShoppingListModel shoppingListModel) {
        Bundle bundle= new Bundle();
        bundle.putString("document_id",shoppingListModel.getDocumentId());
        bundle.putString("shop_name",shoppingListModel.getShopName());
        bundle.putFloat("budget",shoppingListModel.getBudget());
        FirebaseUser currentUser = mAuth.getCurrentUser();
        bundle.putString("uid",currentUser.getUid());
        bundle.putStringArrayList("array_list",shoppingListModel.getShoppingList());
        Intent intent = new Intent(this,DetailsActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
