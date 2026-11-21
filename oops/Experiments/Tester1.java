class ShipmentService {
    private Sender sender;
    private int weightInGrams;
    private int fullKgs;

    public ShipmentService(Sender sender, int weightInGrams) {
        this.sender = sender;
        this.weightInGrams = weightInGrams;
        this.fullKgs = 0;
    }

    public int getFullKgs() {
        return this.fullKgs;
    }

    public Sender getSender() {
        return this.sender;
    }

    public int identifyFullKgs() {
        this.fullKgs = this.weightInGrams / 1000;
        return this.weightInGrams % 1000;
    }

    public double calculateFinalAmount() {
        return 0.0;
    }
}

class Sender {
    public static String[] senderIdArr = {"S301X", "S302N", "S303X", "S304N", "S305N"};
    public static int[] orderCountArr = {4, 9, 14, 2, 7};
    public static int[] totalSpentArr = {4100, 1800, 9600, 650, 2300};
    private String senderId;
    private String senderName;

    public Sender(String senderId, String senderName) {
        this.senderId = senderId;
        this.senderName = senderName;
    }

    public String getSenderId() {
        return this.senderId;
    }

    public String getSenderName() {
        return this.senderName;
    }

    // To_Trainee
    public float calculateDiscount(float shippingAmount) {
        float discountAmount = 0.0f;


        boolean flag = false;
        int idx = -1;
        for(int i = 0; i< Sender.senderIdArr.length; i++){
            String s = Sender.senderIdArr[i];

            if(s.equals(this.getSenderId())){
                flag = true;
                idx = i;
                break;
            }
        }

        if(flag) {
            int orderCount = this.incrementOrderCount();

            char last =  this.getSenderId().charAt(this.getSenderId().length()-1);

            if(last == 'X'){
                discountAmount =  0.12f * shippingAmount;
            }
            else if(last == 'N'){
                discountAmount = 0.04f * shippingAmount;
            }

            if(orderCount%5 == 0){
                discountAmount+=25.0f;
            }
        }

        else{
            discountAmount = -1.0f;
        }
        return discountAmount;
    }

    public int incrementOrderCount() {
        int index = 0;
        for (int ind = 0; ind < Sender.senderIdArr.length; ind++) {
            if (Sender.senderIdArr[ind].equals(this.senderId)) {
                index = ind;
                break;
            }
        }
        Sender.orderCountArr[index]++;
        return Sender.orderCountArr[index];
    }
}



//-----------------------------


class ExpressShipment extends ShipmentService {
    private String parcelType;

    public ExpressShipment(Sender sender, int weightInGrams, String parcelType) {
        super(sender, weightInGrams);
        this.parcelType = parcelType;
    }

    // To_Trainee
    @Override
    public double calculateFinalAmount() {
        double finalAmount = 0.0;

        int ratePerKg = ParcelDetails.identifyRatePerKg(this.parcelType);
        int remainingGrams =  super.identifyFullKgs();

        if(ratePerKg == -1 || remainingGrams <0|| super.getFullKgs() <0) {
            finalAmount = -1.0;
        }

        else{
            int billableKgs = super.getFullKgs();
            if(remainingGrams >0){
                billableKgs += 1;
            }

            float shippingAmount = billableKgs *ratePerKg;
            shippingAmount += 50;

            float discountAmount = super.getSender().calculateDiscount(shippingAmount);
            if(discountAmount == -1.0f) finalAmount = -1.0;

            else{
                finalAmount = shippingAmount - discountAmount;

                int idx = -1;
                String senderId =  super.getSender().getSenderId();

                for(int i= 0; i< Sender.senderIdArr.length; i++){
                    String s = Sender.senderIdArr[i];

                    if(s.equalsIgnoreCase(senderId)){
                        idx = i;
                    }
                }
                Sender.totalSpentArr[idx]=(int)finalAmount + Sender.totalSpentArr[idx];
            }
        }

        return finalAmount;
    }
}

class ParcelDetails {
    private static String[] parcelTypesArr = {"Document", "Box", "Fragile"};
    private static int[] ratePerKgArr = {50, 75, 100};

    // To_Trainee
    public static int identifyRatePerKg(String parcelType) {
        int ratePerKg = 0;


        int idx = -1;
        boolean flag = false;
        for (int i = 0; i < ParcelDetails.parcelTypesArr.length; i++) {
            String s = ParcelDetails.parcelTypesArr[i];

            if (s.equalsIgnoreCase(parcelType)) {
                idx = i;
                flag = true;
                break;
            }
        }

        if (flag) {
            ratePerKg = ParcelDetails.ratePerKgArr[idx];
        } else {
            ratePerKg = -1;
        }

        return ratePerKg;
    }
}

public class Tester1 {
    public static void main(String[] args) {
        Sender senderObj = new Sender("S304N", "Anu");
        ExpressShipment shipmentObj = new ExpressShipment(senderObj, 2300, "Box");
        for (int ind = 0; ind < Sender.senderIdArr.length; ind++) {
            System.out.print(Sender.senderIdArr[ind] + ":" + Sender.totalSpentArr[ind] + ":" + Sender.orderCountArr[ind] + " ");
        }
        System.out.println();
        double finalAmount = shipmentObj.calculateFinalAmount();
        System.out.println("Final bill amount: " + finalAmount);
        for (int ind = 0; ind < Sender.senderIdArr.length; ind++) {
            System.out.print(Sender.senderIdArr[ind] + ":" + Sender.totalSpentArr[ind] + ":" + Sender.orderCountArr[ind] + " ");
        }
    }
}
