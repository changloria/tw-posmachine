package pos.machine;

import java.util.List;

public class GenerateReceipt {
    private List<Items> itemList;
    private int totalPrice;

    public GenerateReceipt(List<Items> itemList, int totalPrice) {
        this.itemList = itemList;
        this.totalPrice = totalPrice;
    }

    public List<Items> getItemList() {
        return itemList;
    }

    public int getPriceTotal() {
        return totalPrice;
    }

}
