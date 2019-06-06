package harkor.shoppinglist.model;

import java.util.ArrayList;

public class ShoppingListModel {
    String shopName;
    Float budget;
    ArrayList<String> shoppingList;
    String documentId;


    public ShoppingListModel(String documentId,String shopName, Float budget,
                             ArrayList<String> shoppingList) {

        this.shopName = shopName;
        this.documentId=documentId;
        this.budget = budget;
        this.shoppingList = shoppingList;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public void setBudget(Float budget) {
        this.budget = budget;
    }

    public void setShoppingList(ArrayList<String> shoppingList) {
        this.shoppingList = shoppingList;
    }

    public String getShopName() {
        return shopName;
    }

    public Float getBudget() {
        return budget;
    }

    public ArrayList<String> getShoppingList() {
        return shoppingList;
    }
}
