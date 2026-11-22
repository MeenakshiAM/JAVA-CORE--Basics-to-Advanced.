import java.util.Arrays;

class Bookstore {
    public static String[] itemNameArr = {"Novel", "Textbook", "Comic", "Journal"};
    public static float[] itemPriceArr = {350.0f, 600.0f, 120.0f, 250.0f};
    public static int[] itemQuantityArr = {15, 10, 40, 25};

    public float findPricePerItem(String itemName) {
        float priceItem = -1.0f;
        for (int index = 0; index < Bookstore.itemNameArr.length; index++) {
            if (itemName.equals(Bookstore.itemNameArr[index])) {
                priceItem = Bookstore.itemPriceArr[index];
            }
        }
        return priceItem;
    }
}

class OnlineBookstore extends Bookstore {
    private int paymentDiscountPercentage;
    private BookOrder order;

    public OnlineBookstore(BookOrder order) {
        this.order = order;
    }

    public BookOrder getOrder() {
        return this.order;
    }

    public void identifyPaymentDiscount() {
        if (this.order.getPaymentMode().equals("UPI")) {
            this.paymentDiscountPercentage = 10;
        } else if (this.order.getPaymentMode().equals("Card")) {
            this.paymentDiscountPercentage = 5;
        } else if (this.order.getPaymentMode().equals("Cash")) {
            this.paymentDiscountPercentage = 0;
        } else {
            this.paymentDiscountPercentage = -1;
        }
    }

    //To Trainee
    @Override
    public float findPricePerItem(String itemName) {
        float pricePerItem = 0.0f;

        float unitPrice = super.findPricePerItem(itemName);

        if(unitPrice == -1.0f){
            pricePerItem = -1.0f;
        }
        else {
            this.identifyPaymentDiscount();

            if(this.paymentDiscountPercentage == -1) {
                pricePerItem = -1.0f;
            }
            else {
                float discount = paymentDiscountPercentage/100.0f * unitPrice;

                pricePerItem = unitPrice - discount;
            }
        }



        return pricePerItem;
    }

    @Override
    public String toString() {
        return "OnlineBookstore (BookOrder ( itemName=" + this.order.getItemName()
                + ", quantityRequired=" + this.order.getQuantityRequired() + ", paymentMode="
                + this.order.getPaymentMode() + "))";
    }

    //To Trainee
    public int checkItemAvailability() {
        //write your code here
        String itemName =  this.getOrder().getItemName();

        int idx = -1;
        boolean flag  = false;

        for(int i = 0; i< Bookstore.itemNameArr.length; i++) {
            String s = Bookstore.itemNameArr[i];

            if(s.equals(itemName)){
                flag = true;
                idx = i;
                break;
            }
        }
        int quantityRequired = 0;
        if(flag ){
            quantityRequired = this.getOrder().getQuantityRequired();

            if(quantityRequired <= Bookstore.itemQuantityArr[idx]){
                Bookstore.itemQuantityArr[idx] -= quantityRequired;
            }
            else  {
                return -1;
            }
            //return  quantityRequired;
        }
        else {
            return -1;
        }
        //change return statement accordingly
        return quantityRequired;
    }

    //To Trainee
    public void placeOrder() {
        //write your code here

        int quantity = this.checkItemAvailability();
    //    System.out.println(quantity);
        float pricePerItem = this.findPricePerItem(this.getOrder().getItemName());
//System.out.println(pricePerItem);
        if(quantity == -1 || pricePerItem == -1.0f) {
            this.getOrder().setOrderPrice(-1.0);
            this.getOrder().setOrderId("NA");

        }
        else {
            float orderPrice = quantity * pricePerItem;

            if(orderPrice < 500.0f) {
                orderPrice += 40.0f;
            }
            this.getOrder().setOrderPrice(orderPrice);
            this.getOrder().generateOrderId();
        }
    }
}

class BookOrder {
    private static int counter = 2000;
    private String orderId;
    private String itemName;
    private int quantityRequired;
    private String paymentMode;
    private double orderPrice;

    public BookOrder(String itemName, int quantityRequired, String paymentMode) {
        this.itemName = itemName;
        this.quantityRequired = quantityRequired;
        this.paymentMode = paymentMode;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return this.orderId;
    }

    public String getItemName() {
        return this.itemName;
    }

    public int getQuantityRequired() {
        return this.quantityRequired;
    }

    public String getPaymentMode() {
        return this.paymentMode;
    }

    public double getOrderPrice() {
        return this.orderPrice;
    }

    public void setOrderPrice(double orderPrice) {
        this.orderPrice = orderPrice;
    }

    @Override
    public String toString() {
        return "BookOrder (orderId=" + this.orderId + ", itemName=" + this.itemName
                + ", quantityRequired=" + this.quantityRequired + ", paymentMode="
                + this.paymentMode + ", orderPrice=" + this.orderPrice + ")";
    }

    //To Trainee
    public void generateOrderId() {
        //write your code here

        this.setOrderId("BO" + BookOrder.counter);
        BookOrder.counter++;
    }
}

class Tester3 {
    public static void main(String[] args) {
        String[] items = {"Novel", "Comic", "Journal", "Textbook", "Textbook", "Novel", "Comic", "Comic", "Journal"};
        int[] quantities = {2, 3, 1, 10, 1, 1, 4, 5, 2};
        String[] modes = {"UPI", "Cash", "Card", "UPI", "UPI", "Wallet", "Card", "Card", "Cash"};
        for (int i = 0; i < items.length; i++) {
            BookOrder order = new BookOrder(items[i], quantities[i], modes[i]);
            OnlineBookstore store = new OnlineBookstore(order);
            store.placeOrder();
            System.out.println("T" + (i + 1) + " Order ID :" + order.getOrderId() + " Order Price :" + order.getOrderPrice());
        }
        System.out.println("Stock left :" + Arrays.toString(Bookstore.itemQuantityArr));
    }
}
