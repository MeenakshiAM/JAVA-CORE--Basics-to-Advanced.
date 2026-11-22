import java.util.Arrays;

class OnlineOrder {
    private int deliveryDistanceKm;
    private float totalAmount;

    public OnlineOrder(int deliveryDistanceKm) {
        this.deliveryDistanceKm = deliveryDistanceKm;
    }

    public int getDeliveryDistanceKm() {
        return this.deliveryDistanceKm;
    }

    public float getTotalAmount() {
        return this.totalAmount;
    }

    public void setTotalAmount(float totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void calculateTotalAmount() {
        float totalAmount = -1.0f;
        if (this.deliveryDistanceKm > 0) {
            if (this.deliveryDistanceKm <= 5) {
                totalAmount = 0.0f;
            } else {
                totalAmount = (this.deliveryDistanceKm - 5) * 10;
            }
        }
        this.totalAmount = totalAmount;
    }

    @Override
    public String toString() {
        return "OnlineOrder (deliveryDistanceKm=" + this.deliveryDistanceKm + ")";
    }
}

class GroceryOrder extends OnlineOrder {
    private static String[][] priceDetailsArr = {{"Rice", "Milk", "Eggs", "Bread", "Oil"}, {"60", "25", "6", "40", "150"}};
    private String[] items;
    private int[] quantities;

    public GroceryOrder(int deliveryDistanceKm, String[] items, int[] quantities) {
        super(deliveryDistanceKm);
        this.items = items;
        this.quantities = quantities;
    }

    public String[] getItems() {
        return this.items;
    }

    public int[] getQuantities() {
        return this.quantities;
    }

    // To_Trainee
    public Boolean validateOrder() {
        if(this.getItems() == null || this.getQuantities() == null|| this.getItems().length != this.getQuantities().length){
            return false;
        }
        //boolean flag  = false;
        for(int i = 0; i< this.getQuantities().length; i++) {
            if(this.getQuantities()[i] <= 0) return false;
        }

        return true;
    }

    // To_Trainee
    @Override
    public void calculateTotalAmount() {
        // write your code here
        super.calculateTotalAmount();

        float delivaryCharge =  super.getTotalAmount();
        if(delivaryCharge == -1.0f || !this.validateOrder()){
            super.setTotalAmount(-1.0f);
        }
        else {

            float itemTotal = 0.0f;
            int idx = -1;
            boolean flag1 = false;
            for(int i = 0 ; i< this.getItems().length ; i++) {
                String s1 = this.getItems()[i];

                idx = -1;
                flag1 = false;
                for(int j = 0; j < GroceryOrder.priceDetailsArr[0].length; j++) {
                    String s2 = GroceryOrder.priceDetailsArr[0][j];

                    if(s1 != null && s1.equalsIgnoreCase(s2)){
                        idx = j;
                        flag1 = true;
                        break;
                    }
                }

                if(flag1) {
                    float pricePerUnit = Integer.parseInt(GroceryOrder.priceDetailsArr[1][idx]);
                    itemTotal += pricePerUnit * this.getQuantities()[i];
                }
            }

            if(itemTotal == 0.0f) {
                this.setTotalAmount(-1.0f);
            }
            else{
                float discount = 0.0f;

                if(itemTotal >= 1000){
                    discount = 0.1f * itemTotal;
                }
                else if(itemTotal >= 500 && itemTotal < 1000){
                    discount =  0.05f * itemTotal;
                }
                else {
                    discount = 0.0f;
                }
                setTotalAmount(itemTotal - discount +delivaryCharge);
            }
        }
    }

    @Override
    public String toString() {
        return "GroceryOrder (OnlineOrder (deliveryDistanceKm=" + this.getDeliveryDistanceKm()
                + ") items=" + Arrays.toString(this.items)
                + ", quantities=" + Arrays.toString(this.quantities) + ")";
    }
}

class ElectronicsOrder extends OnlineOrder {
    private int productPrice;
    private int warrantyYears;
    private char category;

    public ElectronicsOrder(int deliveryDistanceKm, int productPrice, char category, int warrantyYears) {
        super(deliveryDistanceKm);
        this.productPrice = productPrice;
        this.category = category;
        this.warrantyYears = warrantyYears;
    }

    // To_Trainee
    public Boolean validateWarrantyYears() {
        // write your code here
        if(this.warrantyYears >=1 && this.warrantyYears<=5) return true;
        return false;
    }

    // To_Trainee
    public int identifyGstPercent() {
        int gstPercent = 0;
        // write your code here

        if (category == 'M' || category =='m'){
            gstPercent = 12;
        }
        else if (category == 'L' || category =='l') {
            gstPercent = 18;
        } else if (category == 'A' || category == 'a') {
            gstPercent = 5;
        }
        else {
            gstPercent = -1;
        }
        return gstPercent;
    }

    // To_Trainee
    @Override
    public void calculateTotalAmount() {
        // write your code here
        super.calculateTotalAmount();
        float deliveryCharge = super.getTotalAmount();
        int gstPercent = this.identifyGstPercent();

        if(deliveryCharge == -1.0f || gstPercent == -1 || !this.validateWarrantyYears()||this.productPrice <=0){
            this.setTotalAmount(-1.0f);
        } else {
            float warantyCharge = 0;

            if(warrantyYears == 2){
                warantyCharge = 0.05f * this.productPrice;
            }
            else if(warrantyYears == 3) {
                warantyCharge = 0.08f * this.productPrice;
            } else if (warrantyYears== 4 || warrantyYears == 5) {
                warantyCharge = 0.12f * this.productPrice;

            }

            float taxableAmount = productPrice + warantyCharge;
            float gstAmount =  gstPercent/100 * taxableAmount;
            super.setTotalAmount(taxableAmount + gstAmount + deliveryCharge);
        }
    }

    @Override
    public String toString() {
        return "ElectronicsOrder (OnlineOrder (deliveryDistanceKm=" + this.getDeliveryDistanceKm()
                + ") productPrice=" + this.productPrice + ", category=" + this.category
                + ", warrantyYears=" + this.warrantyYears + ")";
    }
}

class Tester3 {
    public static void main(String[] args) {
        String[] i1 = {"rice", "EGGS", "Milk", "Soap"};
        int[] q1 = {5, 10, 4, 2};
        GroceryOrder g1 = new GroceryOrder(12, i1, q1);
        g1.calculateTotalAmount();
        System.out.println("T1 Grocery below 500: " + g1.getTotalAmount());

        String[] i2 = {"Rice", "Oil", "Bread"};
        int[] q2 = {6, 4, 10};
        GroceryOrder g2 = new GroceryOrder(3, i2, q2);
        g2.calculateTotalAmount();
        System.out.println("T2 Grocery above 1000: " + g2.getTotalAmount());

        String[] i3 = {"Rice", "Milk"};
        int[] q3 = {5, 8};
        GroceryOrder g3 = new GroceryOrder(5, i3, q3);
        g3.calculateTotalAmount();
        System.out.println("T3 Grocery items total exactly 500: " + g3.getTotalAmount());

        String[] i4 = {"Oil", "Bread"};
        int[] q4 = {4, 10};
        GroceryOrder g4 = new GroceryOrder(10, i4, q4);
        g4.calculateTotalAmount();
        System.out.println("T4 Grocery items total exactly 1000: " + g4.getTotalAmount());

        GroceryOrder g5 = new GroceryOrder(10, i2, new int[]{1, 2});
        g5.calculateTotalAmount();
        System.out.println("T5 Grocery length mismatch: " + g5.getTotalAmount());

        GroceryOrder g6 = new GroceryOrder(10, i2, new int[]{1, 0, 3});
        g6.calculateTotalAmount();
        System.out.println("T6 Grocery zero quantity: " + g6.getTotalAmount());

        String[] i7 = {"Rice", "Milk", "Eggs", "Bread", "Oil", "Rice"};
        int[] q7 = {1, 1, 1, 1, 1, 1};
        GroceryOrder g7 = new GroceryOrder(10, i7, q7);
        g7.calculateTotalAmount();
        System.out.println("T7 Grocery six items: " + g7.getTotalAmount());

        String[] i8 = {"Soap", "Pen"};
        int[] q8 = {1, 1};
        GroceryOrder g8 = new GroceryOrder(10, i8, q8);
        g8.calculateTotalAmount();
        System.out.println("T8 Grocery all unknown items: " + g8.getTotalAmount());

        GroceryOrder g9 = new GroceryOrder(10, null, q8);
        g9.calculateTotalAmount();
        System.out.println("T9 Grocery null items: " + g9.getTotalAmount());

        GroceryOrder g10 = new GroceryOrder(0, i3, q3);
        g10.calculateTotalAmount();
        System.out.println("T10 Grocery zero distance: " + g10.getTotalAmount());

        String[] i11 = {"Rice", null};
        int[] q11 = {2, 1};
        GroceryOrder g11 = new GroceryOrder(6, i11, q11);
        g11.calculateTotalAmount();
        System.out.println("T11 Grocery null element: " + g11.getTotalAmount());

        ElectronicsOrder e1 = new ElectronicsOrder(4, 40000, 'l', 3);
        e1.calculateTotalAmount();
        System.out.println("T12 Electronics laptop 3 years: " + e1.getTotalAmount());

        ElectronicsOrder e2 = new ElectronicsOrder(9, 15000, 'M', 2);
        e2.calculateTotalAmount();
        System.out.println("T13 Electronics mobile 2 years: " + e2.getTotalAmount());

        ElectronicsOrder e3 = new ElectronicsOrder(5, 2000, 'A', 1);
        e3.calculateTotalAmount();
        System.out.println("T14 Electronics accessory 1 year: " + e3.getTotalAmount());

        ElectronicsOrder e4 = new ElectronicsOrder(5, 2000, 'Z', 1);
        e4.calculateTotalAmount();
        System.out.println("T15 Electronics unknown category: " + e4.getTotalAmount());

        ElectronicsOrder e5 = new ElectronicsOrder(5, 2000, 'A', 6);
        e5.calculateTotalAmount();
        System.out.println("T16 Electronics 6 years: " + e5.getTotalAmount());

        ElectronicsOrder e6 = new ElectronicsOrder(5, 2000, 'A', 0);
        e6.calculateTotalAmount();
        System.out.println("T17 Electronics 0 years: " + e6.getTotalAmount());

        ElectronicsOrder e7 = new ElectronicsOrder(5, 0, 'A', 2);
        e7.calculateTotalAmount();
        System.out.println("T18 Electronics zero price: " + e7.getTotalAmount());

        ElectronicsOrder e8 = new ElectronicsOrder(5, 60000, 'L', 5);
        e8.calculateTotalAmount();
        System.out.println("T19 Electronics laptop 5 years: " + e8.getTotalAmount());
    }
}