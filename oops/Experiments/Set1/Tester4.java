import java.util.Arrays;

class Restaurant {
    public static String[] itemNameArr = {"Pizza", "Burger", "Pasta", "Salad"};
    public static float[] itemPriceArr = {300.0f, 150.0f, 250.0f, 120.0f};
    public static int[] itemQuantityArr = {12, 25, 15, 30};

    public float findPricePerItem(String itemName) {
        float priceItem = -1.0f;
        for (int index = 0; index < Restaurant.itemNameArr.length; index++) {
            if (itemName.equals(Restaurant.itemNameArr[index])) {
                priceItem = Restaurant.itemPriceArr[index];
            }
        }
        return priceItem;
    }
}

class OnlineRestaurant extends Restaurant {
    private int packagingPercentage;
    private FoodOrder order;

    public OnlineRestaurant(FoodOrder order) {
        this.order = order;
    }

    public FoodOrder getOrder() {
        return this.order;
    }

    public void identifyPackagingCharge() {
        if (this.order.getOrderMode().equals("Delivery")) {
            this.packagingPercentage = 10;
        } else if (this.order.getOrderMode().equals("Takeaway")) {
            this.packagingPercentage = 5;
        } else if (this.order.getOrderMode().equals("Dine-In")) {
            this.packagingPercentage = 0;
        } else {
            this.packagingPercentage = -1;
        }
    }

    //To Trainee
    @Override
    public float findPricePerItem(String itemName) {
        float pricePerItem = -1.0f;
        //Write your code here


        float unitPrice = super.findPricePerItem(itemName);

        if(unitPrice == -1.0f){
            pricePerItem  = -1.0f;
        }
        else{
            this.identifyPackagingCharge();

            int packagePercent = this.packagingPercentage;

            if(this.packagingPercentage == -1){
                pricePerItem = -1.0f;
            }
            else{
                float packagingCharge = unitPrice * this.packagingPercentage/100.0f;
                pricePerItem = unitPrice + packagingCharge;
            }
        }
        return pricePerItem;
    }

    @Override
    public String toString() {
        return "OnlineRestaurant (FoodOrder ( itemName=" + this.order.getItemName()
                + ", quantityRequired=" + this.order.getQuantityRequired() + ", orderMode="
                + this.order.getOrderMode() + "))";
    }

    //To Trainee
    public int checkItemAvailability() {
        //write your code here

        String itemName =  this.getOrder().getItemName();

        int idx = -1;
        boolean flag = false;

        for(int i = 0; i< Restaurant.itemNameArr.length; i++) {
            String s = Restaurant.itemNameArr[i];

            if(s.equals(itemName)) {
                flag = true;
                idx = i;
                break;
            }
        }
        int quantityRequired =-1;
        if (flag) {
             quantityRequired = this.getOrder().getQuantityRequired();

            if(quantityRequired  <= Restaurant.itemQuantityArr[idx]){
                Restaurant.itemQuantityArr[idx] -= quantityRequired;
            }
            else{
                return -1;
            }


        }
        else{
            return -1;
        }
        //change return statement accordingly
        return quantityRequired;
    }

    //To Trainee
    public void confirmOrder() {
        //write your code here

        int quantity = checkItemAvailability();
        float pricePerItem = this.findPricePerItem(this.getOrder().getItemName());
        //System.out.println(quantity);
        //System.out.println(pricePerItem);

        if(quantity == -1 || pricePerItem == -1.0f ){
            this.getOrder().setBillAmount(-1.0);
            this.getOrder().setOrderCode("NA");
        }
        else {
            this.getOrder().setBillAmount(quantity * pricePerItem);
            this.getOrder().generateOrderCode();
        }
    }
}

class FoodOrder {
    private static int counter = 100;
    private String orderCode;
    private String itemName;
    private int quantityRequired;
    private String orderMode;
    private double billAmount;

    public FoodOrder(String itemName, int quantityRequired, String orderMode) {
        this.itemName = itemName;
        this.quantityRequired = quantityRequired;
        this.orderMode = orderMode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getOrderCode() {
        return this.orderCode;
    }

    public String getItemName() {
        return this.itemName;
    }

    public int getQuantityRequired() {
        return this.quantityRequired;
    }

    public String getOrderMode() {
        return this.orderMode;
    }

    public double getBillAmount() {
        return this.billAmount;
    }

    public void setBillAmount(double billAmount) {
        this.billAmount = billAmount;
    }

    @Override
    public String toString() {
        return "FoodOrder (orderCode=" + this.orderCode + ", itemName=" + this.itemName
                + ", quantityRequired=" + this.quantityRequired + ", orderMode="
                + this.orderMode + ", billAmount=" + this.billAmount + ")";
    }

    //To Trainee
    public void generateOrderCode() {
        //write your code here
        this.setOrderCode("OD" + FoodOrder.counter);
        FoodOrder.counter+=5;
    }
}

class Tester4 {
    public static void main(String[] args) {
        String[] items = {"Pizza", "Burger", "Pasta", "Salad", "Pizza", "Salad", "Pizza", "Burger"};
        int[] quantities = {4, 10, 3, 31, 9, 5, 8, 15};
        String[] modes = {"Delivery", "Takeaway", "Dine-In", "Delivery", "Delivery", "Home", "Takeaway", "Delivery"};
        for (int i = 0; i < items.length; i++) {
            FoodOrder order = new FoodOrder(items[i], quantities[i], modes[i]);
            OnlineRestaurant restaurant = new OnlineRestaurant(order);
            restaurant.confirmOrder();
            System.out.println("T" + (i + 1) + " Order Code :" + order.getOrderCode() + " Bill Amount :" + order.getBillAmount());
        }
        System.out.println("Stock left :" + Arrays.toString(Restaurant.itemQuantityArr));
    }
}
