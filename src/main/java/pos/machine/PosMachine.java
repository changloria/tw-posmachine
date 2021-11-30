package pos.machine;

import java.util.*;
import java.util.stream.Collectors;

public class PosMachine {
    public String printReceipt(List<String> barcodes) {
        List<Items> items = convertToItems(barcodes);
        GenerateReceipt receipt = calculateReceipt(items);

        return generateReceipt(receipt);
    }


    private List<Items> convertToItems(List<String> barcodes) {
        List<ItemInfo> itemInfoList = ItemDataLoader.loadAllItemInfos();
        List<Items> itemList = new ArrayList<>();
        List<String> newbarcodes = barcodes.stream()
                .distinct()
                .collect(Collectors.toList());
        for (String barcode : newbarcodes) {
            for (ItemInfo itemInfo : itemInfoList) {
                if (barcode.equals(itemInfo.getBarcode())) {
                    int quantity = Collections.frequency(barcodes, barcode);
                    int price = itemInfo.getPrice();
                    int subTotal = quantity*price;
                    Items item = new Items(itemInfo.getName(), quantity, price, subTotal);
                    itemList.add(item);
                }
            }
        }
        return itemList;
    }

    private GenerateReceipt calculateTotalPrice(List<Items> itemsWithDetail, int totalPrice) {
        for (Items item : itemsWithDetail) {
            totalPrice += item.getSubTotal();
        }
        return new GenerateReceipt(itemsWithDetail, totalPrice);
    }

    private GenerateReceipt calculateReceipt(List<Items> itemsWithDetail) {
        int totalPrice = 0;
        return calculateTotalPrice(itemsWithDetail, totalPrice);
    }


    private String generateItemsDetail(GenerateReceipt receipt) {
        String itemsDetail = "";
        for (Items itemValue : receipt.getItemList()) {
            itemsDetail += "Name: "+ itemValue.getName() +
                    ", Quantity: " + itemValue.getQuantity() +
                    ", Unit price: " + itemValue.getUnitPrice() + " (yuan)" +
                    ", Subtotal: " + itemValue.getSubTotal() + " (yuan)\n";
        }

        return itemsDetail;
    }

    private String generateReceipt(GenerateReceipt receipt) {
        String itemsDetail = generateItemsDetail(receipt);

        return ("***<store earning no money>Receipt***\n" + itemsDetail +
                "----------------------\n" +
                "Total: " + receipt.getPriceTotal() + " (yuan)\n" +
                "**********************");
    }


}