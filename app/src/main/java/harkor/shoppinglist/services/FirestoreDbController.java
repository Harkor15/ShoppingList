package harkor.shoppinglist.services;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import harkor.shoppinglist.interfaces.SuccesOperationOnDBInterface;
import harkor.shoppinglist.interfaces.ListOfShoppingListsInterface;
import harkor.shoppinglist.model.ShoppingListModel;

public class FirestoreDbController {
    private static final String TAG = "sld";
    private String uid;

    public FirestoreDbController(String uid) {
        this.uid = uid;
    }

    public void getAllLists(ListOfShoppingListsInterface listOfShoppingListsInterface){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").document(uid).collection("lists")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<ShoppingListModel> listOfShoppingLists=new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                listOfShoppingLists.add(new ShoppingListModel(document.getId(),
                                        document.getData().get("shop_name").toString(),
                                        Float.valueOf(document.getData().get("budget").toString()),
                                        (ArrayList<String>) document.getData().get("products")));
                            }
                            listOfShoppingListsInterface.setListOfShoppingLists(listOfShoppingLists);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    public void addNewShoppingList(String shopName, Float budget, ArrayList<String> productList,
                                   SuccesOperationOnDBInterface succesOperationOnDBInterface){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> newShoppingList = new HashMap<>();
        newShoppingList.put("shop_name", shopName);
        newShoppingList.put("budget", budget);
        newShoppingList.put("products", productList);
        db.collection("users").document(uid).collection("lists")
                .add(newShoppingList)
                .addOnSuccessListener(aVoid -> succesOperationOnDBInterface.deleteCompleted())
                .addOnFailureListener(e -> Log.d("sld","failure"));
    }

    public void deleteShoppingList(String id, SuccesOperationOnDBInterface succesOperationOnDBInterface){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").document(uid).collection("lists").document(id)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        succesOperationOnDBInterface.deleteCompleted();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("sld", e.toString());
                    }
                });
    }
}
