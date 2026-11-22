import java.util.Arrays;

class Pharmacy {
    public static String[] itemNameArr = {"Paracetamol", "Vitamin-C", "Bandage", "Sanitizer"};
    public static float[] itemPriceArr = {20.0f, 150.0f, 50.0f, 80.0f};
    public static int[] itemQuantityArr = {100, 50, 75, 30};

    public float findPricePerItem(String itemName) {
        float priceItem = -1.0f;
        for (int index = 0; index < Pharmacy.itemNameArr.length; index++) {
            if (itemName.equals(Pharmacy.itemNameArr[index])) {
                priceItem = Pharmacy.itemPriceArr[index];
            }
        }
        return priceItem;
    }
}

class OnlinePharmacy extends Pharmacy {
    private int memberDiscountPercentage;
    private PharmacyOrder order;

    public OnlinePharmacy(PharmacyOrder order) {
        this.order = order;
    }

    public PharmacyOrder getOrder() {
        return this.order;
    }

    public void identifyMemberDiscount() {
        if (this.order.getCustomerType().equals("Gold")) {
            this.memberDiscountPercentage = 10;
        } else if (this.order.getCustomerType().equals("Silver")) {
            this.memberDiscountPercentage = 5;
        } else {
            this.memberDiscountPercentage = -1;
        }
    }

    //To Trainee
    @Override
    public float findPricePerItem(String itemName) {
        float pricePerItem = 0.0f;
        //Write your code here

        float unitPrice = super.findPricePerItem(itemName);

        if(unitPrice == -1.0f){
            pricePerItem = -1.0f;
        }
        else{
            this.identifyMemberDiscount();
            float memDiscountPercentage =  this.memberDiscountPercentage;

            if(memDiscountPercentage == -1) {
                pricePerItem = -1.0f;
            }
            else{
                float discount = memDiscountPercentage/100.0f * unitPrice;

                pricePerItem = unitPrice - discount;
            }
        }


        return pricePerItem;
    }

    @Override
    public String toString() {
        return "OnlinePharmacy (PharmacyOrder ( itemName=" + this.order.getItemName()
                + ", quantityRequired=" + this.order.getQuantityRequired() + ", customerType="
                + this.order.getCustomerType() + "))";
    }

    //To Trainee
    public int checkItemAvailability() {
        //write your code here
        String  itemName =  order.getItemName();
        int quantityRequired = order.getQuantityRequired();

        if(quantityRequired <= 0) return -1;

        int idx = -1;
        boolean flag = false;
        for(int i = 0; i< Pharmacy.itemNameArr.length; i++) {
            String s = Pharmacy.itemNameArr[i];

            if(s.equals(itemName)) {
                flag = true;
                idx = i;
                break;
            }
        }

        if(flag) {
            if(quantityRequired <= Pharmacy.itemQuantityArr[idx]){
                Pharmacy.itemQuantityArr[idx] -= quantityRequired;
            }
            else{
                return -1;
            }
        }
        else {
            return -1;
        }
        //change return statement accordingly
        return quantityRequired;
    }

    //To Trainee
    public void processOrder() {
        //write your code here

        int quantity = this.checkItemAvailability();
       // System.out.println(quantity);
        float pricePerItem = this.findPricePerItem(order.getItemName());
        // System.out.println(pricePerItem);
        if(quantity == -1 || pricePerItem== -1.0f){
            order.setOrderPrice(-1.0);
            order.setInvoiceNo("NA");
        }
        else{
            order.setOrderPrice(order.getQuantityRequired() * pricePerItem);
            order.generateInvoiceNo();
        }


    }
}

class PharmacyOrder {
    private static int counter = 100;
    private String invoiceNo;
    private String itemName;
    private int quantityRequired;
    private String customerType;
    private double orderPrice;

    public PharmacyOrder(String itemName, int quantityRequired, String customerType) {
        this.itemName = itemName;
        this.quantityRequired = quantityRequired;
        this.customerType = customerType;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public String getInvoiceNo() {
        return this.invoiceNo;
    }

    public String getItemName() {
        return this.itemName;
    }

    public int getQuantityRequired() {
        return this.quantityRequired;
    }

    public String getCustomerType() {
        return this.customerType;
    }

    public double getOrderPrice() {
        return this.orderPrice;
    }

    public void setOrderPrice(double orderPrice) {
        this.orderPrice = orderPrice;
    }

    @Override
    public String toString() {
        return "PharmacyOrder (invoiceNo=" + this.invoiceNo + ", itemName=" + this.itemName
                + ", quantityRequired=" + this.quantityRequired + ", customerType="
                + this.customerType + ", orderPrice=" + this.orderPrice + ")";
    }

    //To Trainee
    public void generateInvoiceNo() {
        //write your code here

        this.setInvoiceNo("INV" + PharmacyOrder.counter);
        PharmacyOrder.counter++;
    }
}

class Tester2 {
    public static void main(String[] args) {
        String[] items = {"Vitamin-C", "Bandage", "Sanitizer", "Paracetamol", "Paracetamol", "Paracetamol", "Paracetamol", "Sanitizer", "sanitizer"};
        int[] quantities = {10, 20, 31, 0, -5, 100, 1, 30, 1};
        String[] types = {"Gold", "Silver", "Gold", "Gold", "Silver", "Platinum", "Gold", "Silver", "Gold"};
        for (int i = 0; i < items.length; i++) {
            PharmacyOrder order = new PharmacyOrder(items[i], quantities[i], types[i]);
            OnlinePharmacy pharmacy = new OnlinePharmacy(order);
            pharmacy.processOrder();
            System.out.println("T" + (i + 1) + " Invoice No :" + order.getInvoiceNo() + " Order Price :" + order.getOrderPrice());
        }
        System.out.println("Stock left :" + Arrays.toString(Pharmacy.itemQuantityArr));
    }
}
