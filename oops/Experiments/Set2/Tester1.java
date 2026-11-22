import java.util.Arrays;

class ParcelDelivery {
    private int weightInGrams;
    private float deliveryCharge;

    public ParcelDelivery(int weightInGrams) {
        this.weightInGrams = weightInGrams;
    }

    public float getDeliveryCharge() {
        return this.deliveryCharge;
    }

    public void setDeliveryCharge(float deliveryCharge) {
        this.deliveryCharge = deliveryCharge;
    }

    public int getWeightInGrams() {
        return this.weightInGrams;
    }

    public void calculateDeliveryCharge() {
        float deliveryCharge = -1.0f;
        if (this.weightInGrams > 0) {
            deliveryCharge = this.weightInGrams / 10.0f;
        }
        this.deliveryCharge = deliveryCharge;
    }

    @Override
    public String toString() {
        return "ParcelDelivery (weightInGrams=" + this.weightInGrams + ")";
    }
}


//-----------------
class DomesticParcel extends ParcelDelivery {
    private static String[][] addOnDetailsArr = {{"Insurance", "Packaging", "SMS-Alert", "Fragile-Tag"}, {"150", "80", "10", "120"}};
    private String[] addOnServices;
    private int pincode;

    public DomesticParcel(int weightInGrams, int pincode, String[] addOnServices) {
        super(weightInGrams);
        this.pincode = pincode;
        this.addOnServices = addOnServices;
    }

    public int getPincode() {
        return this.pincode;
    }

    public String[] getAddOnServices() {
        return this.addOnServices;
    }

    // To_Trainee
    public Boolean validatePincode() {
        // write your code here

        if(this.getPincode() >= 100000 && this.getPincode()<=999999) return true;
        // change return statement accordingly
        return false;
    }

    // To_Trainee
    @Override
    public void calculateDeliveryCharge() {
        // write your code here
        super.calculateDeliveryCharge();
        float basicCharge = super.getDeliveryCharge();

        boolean flag = this.validatePincode();
        float addOnCharge = 0.0f;
        if(basicCharge == -1.0f || !flag|| this.getAddOnServices() == null){
            super.setDeliveryCharge(-1.0f)  ;
        }
        else {

            boolean flag1 = false;
            int idx = -1;
            boolean[] alreadyCharged = new boolean[DomesticParcel.addOnDetailsArr[0].length];
            for(int i = 0; i< this.getAddOnServices().length; i++) {
                String s1 = this.getAddOnServices()[i];
                flag1 = false;
                for(int j = 0; j< DomesticParcel.addOnDetailsArr[0].length; j++){
                    if(!alreadyCharged[j] && s1 != null && s1.equalsIgnoreCase(DomesticParcel.addOnDetailsArr[0][j])){
                        flag1 = true;
                        idx = j;
                        alreadyCharged[j] = true;
                        break;
                    }
                }
                if(flag1){
                    addOnCharge += Integer.parseInt(DomesticParcel.addOnDetailsArr[1][idx]);

                }
            }

            if(addOnCharge != 0.0f){
                basicCharge += addOnCharge ;
                super.setDeliveryCharge(basicCharge+(0.05f * basicCharge));
            }
            else {
                super.setDeliveryCharge(basicCharge);
            }

        }

    }

    @Override
    public String toString() {
        return "DomesticParcel (ParcelDelivery (weightInGrams=" + this.getWeightInGrams()
                + ") addOnServices=" + Arrays.toString(this.addOnServices)
                + ", pincode=" + this.pincode + ")";
    }
}



//-------------------
class InternationalParcel extends ParcelDelivery {
    private char zone;
    private int declaredValue;

    public InternationalParcel(int weightInGrams, char zone, int declaredValue) {
        super(weightInGrams);
        this.zone = zone;
        this.declaredValue = declaredValue;
    }

    // To_Trainee
    public int identifyZoneSurcharge() {
        int zoneSurcharge = -1;
        // write your code here
        if(this.zone == 'A' || this.zone == 'a'){
            zoneSurcharge = 1200;
        }
        else if(this.zone == 'B' || this.zone =='b') zoneSurcharge = 1800;
        else if(this.zone =='C' || this.zone == 'c') zoneSurcharge = 2500;

        return zoneSurcharge;
    }

    // To_Trainee
    @Override
    public void calculateDeliveryCharge() {
        // write your code here
        super.calculateDeliveryCharge();

        float basicCharge =  super.getDeliveryCharge();

        int zoneSurcharge =  this.identifyZoneSurcharge();

        if(zoneSurcharge == -1 || basicCharge == -1.0f ){
            super.setDeliveryCharge(-1.0f);
        }
        else {
            float charge =  basicCharge + zoneSurcharge;
            if(this.declaredValue  > 50000){
                charge += 0.02 * this.declaredValue;
            }
            super.setDeliveryCharge(charge);
        }
    }

    @Override
    public String toString() {
        return "InternationalParcel (ParcelDelivery (weightInGrams=" + this.getWeightInGrams()
                + ") zone=" + this.zone + ", declaredValue=" + this.declaredValue + ")";
    }
}

class Tester1 {
    public static void main(String[] args) {
        String[] s1 = {"insurance", "PACKAGING", "Insurance", null, "Gift-Wrap"};
        String[] s2 = {"Gift-Wrap", "Wrapping"};
        String[] s3 = {"sms-alert", "Fragile-Tag"};

        DomesticParcel d1 = new DomesticParcel(2500, 560001, s1);
        d1.calculateDeliveryCharge();
        System.out.println("T1 Domestic mixed add-ons: " + d1.getDeliveryCharge());

        DomesticParcel d2 = new DomesticParcel(2500, 560001, s2);
        d2.calculateDeliveryCharge();
        System.out.println("T2 Domestic unknown add-ons only: " + d2.getDeliveryCharge());

        DomesticParcel d3 = new DomesticParcel(2500, 12345, s1);
        d3.calculateDeliveryCharge();
        System.out.println("T3 Domestic short pincode: " + d3.getDeliveryCharge());

        DomesticParcel d4 = new DomesticParcel(2500, 560001, null);
        d4.calculateDeliveryCharge();
        System.out.println("T4 Domestic null add-ons: " + d4.getDeliveryCharge());

        DomesticParcel d5 = new DomesticParcel(0, 560001, s1);
        d5.calculateDeliveryCharge();
        System.out.println("T5 Domestic zero weight: " + d5.getDeliveryCharge());

        DomesticParcel d6 = new DomesticParcel(1000, 999999, s3);
        d6.calculateDeliveryCharge();
        System.out.println("T6 Domestic highest pincode: " + d6.getDeliveryCharge());

        DomesticParcel d7 = new DomesticParcel(1000, 1000000, s3);
        d7.calculateDeliveryCharge();
        System.out.println("T7 Domestic 7-digit pincode: " + d7.getDeliveryCharge());

        InternationalParcel i1 = new InternationalParcel(4000, 'b', 75000);
        i1.calculateDeliveryCharge();
        System.out.println("T8 International high value: " + i1.getDeliveryCharge());

        InternationalParcel i2 = new InternationalParcel(4000, 'B', 50000);
        i2.calculateDeliveryCharge();
        System.out.println("T9 International value exactly 50000: " + i2.getDeliveryCharge());

        InternationalParcel i3 = new InternationalParcel(4000, 'D', 75000);
        i3.calculateDeliveryCharge();
        System.out.println("T10 International unknown zone: " + i3.getDeliveryCharge());

        InternationalParcel i4 = new InternationalParcel(-5, 'A', 1000);
        i4.calculateDeliveryCharge();
        System.out.println("T11 International negative weight: " + i4.getDeliveryCharge());

        InternationalParcel i5 = new InternationalParcel(2000, 'a', 10000);
        i5.calculateDeliveryCharge();
        System.out.println("T12 International lowercase zone: " + i5.getDeliveryCharge());
    }
}
