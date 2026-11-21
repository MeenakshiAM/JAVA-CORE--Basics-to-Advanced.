class ElectricityBill {
    private Consumer consumer;
    private int previousReading;
    private int currentReading;
    private int unitsConsumed;

    public ElectricityBill(Consumer consumer, int previousReading, int currentReading) {
        this.consumer = consumer;
        this.previousReading = previousReading;
        this.currentReading = currentReading;
        this.unitsConsumed = 0;
    }

    public int getUnitsConsumed() {
        return this.unitsConsumed;
    }

    public Consumer getConsumer() {
        return this.consumer;
    }

    public int identifyUnits() {
        this.unitsConsumed = this.currentReading - this.previousReading;
        if (this.unitsConsumed < 0) {
            return -1;
        }
        return this.unitsConsumed > 200 ? this.unitsConsumed - 200 : 0;
    }

    public double calculateFinalAmount() {
        return 0.0;
    }
}

class Consumer {
    public static String[] consumerIdArr = {"E401A", "E402O", "E403A", "E404O"};
    public static int[] pendingDuesArr = {0, 250, 0, 1200};
    private String consumerId;
    private String consumerName;

    public Consumer(String consumerId, String consumerName) {
        this.consumerId = consumerId;
        this.consumerName = consumerName;
    }

    public String getConsumerId() {
        return this.consumerId;
    }

    public String getConsumerName() {
        return this.consumerName;
    }

    // To_Trainee ============================
    //==============================================


    public float calculateDiscount(float billAmount) {
        float discountAmount = 0.0f;

        boolean flag = false;
        int idx = -1;

        for(int i = 0 ; i< Consumer.consumerIdArr.length; i++) {
            String s = Consumer.consumerIdArr[i];

            if(s.equals(this.getConsumerId())){
                flag  = true;
                idx = i;
                break;
            }
        }

        if(flag) {
            char last = this.getConsumerId().charAt(this.getConsumerId().length() - 1);
            if(last == 'A') {
                discountAmount = 0.08f * billAmount;
            }
            else {
                discountAmount = 0.03f * billAmount;
            }
        }
        else{
            discountAmount = -1.0f;
        }
        return discountAmount;
    }
}

class MeteredBill extends ElectricityBill {
    private String connectionType;

    public MeteredBill(Consumer consumer, int previousReading, int currentReading, String connectionType) {
        super(consumer, previousReading, currentReading);
        this.connectionType = connectionType;
    }

    // To_Trainee
    @Override
    public double calculateFinalAmount() {
        double finalAmount = 0.0;
        int fixedCharges = ConnectionDetails.identifyFixedCharge(this.connectionType);

        int excessUnits = super.identifyUnits();

        if(fixedCharges == -1 || excessUnits < 0) finalAmount = -1.0;
        else{
            int baseUnits = super.getUnitsConsumed() - excessUnits;
            float billAmount = 0;

            int temp = baseUnits;

            if(temp >=100){
                billAmount = 5.0f * 100.0f;
                temp -= 100;
            }
            else{
                billAmount = temp * 5.0f;
            }
            if(temp >0){
                billAmount+= 7 *temp;
            }
            billAmount += excessUnits *10.f;

            billAmount += fixedCharges;

            float discountAmount = super.getConsumer().calculateDiscount(billAmount);

            if(discountAmount == -1.0f) finalAmount = -1.0;
            else{
                String con = super.getConsumer().getConsumerId();

                int idx = 0;
                boolean flag = false;
                for(int i = 0; i< Consumer.consumerIdArr.length; i++){
                    String s = Consumer.consumerIdArr[i];

                    if(s.equals(con)){
                        idx = i;
                        flag = true;
                        break;
                    }
                }

                int pendingDues = Consumer.pendingDuesArr[idx];
                finalAmount =  billAmount - discountAmount +pendingDues;

                Consumer.pendingDuesArr[idx] = 0;

            }
        }
        return finalAmount;
    }
}

class ConnectionDetails {
    private static String[] connectionTypesArr = {"Domestic", "Commercial", "Industrial"};
    private static int[] fixedChargesArr = {100, 400, 1000};

    // To_Trainee
    public static int identifyFixedCharge(String connectionType) {
        int fixedCharge = 0;

        boolean flag = false;
        int idx = -1;

        for(int i = 0; i<ConnectionDetails.connectionTypesArr.length; i++){
            String s = ConnectionDetails.connectionTypesArr[i];

            if(s.equals(connectionType)){
                flag = true;
                idx = i;
                break;
            }
        }

        if(flag){
            fixedCharge = ConnectionDetails.fixedChargesArr[idx];
        }
        else {
            fixedCharge = -1;
        }
        return fixedCharge;
    }
}

class Tester3 {
    public static void main(String[] args) {
        Consumer consumerObj = new Consumer("E402O", "Raj");
        MeteredBill billObj = new MeteredBill(consumerObj, 1200, 1520, "Commercial");
        for (int ind = 0; ind < Consumer.consumerIdArr.length; ind++) {
            System.out.print(Consumer.consumerIdArr[ind] + ":" + Consumer.pendingDuesArr[ind] + " ");
        }
        System.out.println();
        double finalAmount = billObj.calculateFinalAmount();
        System.out.println("Final bill amount: " + finalAmount);
        for (int ind = 0; ind < Consumer.consumerIdArr.length; ind++) {
            System.out.print(Consumer.consumerIdArr[ind] + ":" + Consumer.pendingDuesArr[ind] + " ");
        }
    }
}
