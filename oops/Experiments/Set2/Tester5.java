import java.util.Arrays;

class InsurancePolicy {
    private int sumInsured;
    private float premium;

    public InsurancePolicy(int sumInsured) {
        this.sumInsured = sumInsured;
    }

    public int getSumInsured() {
        return this.sumInsured;
    }

    public float getPremium() {
        return this.premium;
    }

    public void setPremium(float premium) {
        this.premium = premium;
    }

    public void calculatePremium() {
        float premium = -1.0f;
        if (this.sumInsured > 0) {
            premium = this.sumInsured * 2 / 100;
        }
        this.premium = premium;
    }

    @Override
    public String toString() {
        return "InsurancePolicy (sumInsured=" + this.sumInsured + ")";
    }
}

class HealthPolicy extends InsurancePolicy {
    private static String[][] conditionLoadingArr = {{"Diabetes", "Asthma", "Hypertension", "Thyroid"}, {"20", "10", "15", "5"}};
    private int[] memberAges;
    private String[] conditions;

    public HealthPolicy(int sumInsured, int[] memberAges, String[] conditions) {
        super(sumInsured);
        this.memberAges = memberAges;
        this.conditions = conditions;
    }

    public int[] getMemberAges() {
        return this.memberAges;
    }

    public String[] getConditions() {
        return this.conditions;
    }

    // To_Trainee
    public Boolean validateMembers() {
        // write your code here

        if(memberAges == null|| this.memberAges.length <1 || this.getMemberAges().length > 6){
            return false;
        }

        for(int i :  this.getMemberAges()){
            if(i <0 || i>80) return false;
        }
        // change return statement accordingly




        return true;
    }

    // To_Trainee
    @Override
    public void calculatePremium() {
        // write your code here
        super.calculatePremium();

        float basicPremium  = super.getPremium();

        if(basicPremium == -1.0f || !this.validateMembers()){
            super.setPremium(-1.0f);
        }
        else {

            float ageCharge = 0.0f;
            for(int i : this.getMemberAges()){
                if(i <= 25) ageCharge += 1000.0f;
                else if (i >= 26 && i<=45) {
                    ageCharge += 2500.0f;

                } else if (i >=46 && i<=60) {
                    ageCharge += 5000.0f;

                } else if (i>60) {
                    ageCharge += 9000.0f;

                }
            }
            //if(this.memberAges[i] <= 25)

            boolean flag = false;
            int idx = -1;
            float totalLoadingPercent = 0.0f;
            if(conditions!= null) {
                boolean[] check = new boolean[HealthPolicy.conditionLoadingArr[0].length];
                for (int i = 0; i < this.getConditions().length; i++) {
                    String s = this.getConditions()[i];
                    flag = false;
                    for (int j = 0; j < HealthPolicy.conditionLoadingArr[0].length; j++) {
                        String s1 = HealthPolicy.conditionLoadingArr[0][j];

                        if (s != null && s.equalsIgnoreCase(s1) && !check[j]) {
                            flag = true;
                            check[j] = true;
                            idx = j;
                            break;
                        }
                    }
                    if (flag) {
                        float loadingPercent = Integer.parseInt(HealthPolicy.conditionLoadingArr[1][idx]);
                        totalLoadingPercent += loadingPercent;

                    }

                }
            }
            if(totalLoadingPercent > 30) totalLoadingPercent = 30;
            float loadingAmount =  totalLoadingPercent/100.0f * basicPremium;
            float totalPremium = basicPremium + ageCharge+ loadingAmount;

            if( getMemberAges().length >= 4) totalPremium -= 0.1f*totalPremium;

            this.setPremium(totalPremium);
        }
    }

    @Override
    public String toString() {
        return "HealthPolicy (InsurancePolicy (sumInsured=" + this.getSumInsured()
                + ") memberAges=" + Arrays.toString(this.memberAges)
                + ", conditions=" + Arrays.toString(this.conditions) + ")";
    }
}

class VehiclePolicy extends InsurancePolicy {
    private char vehicleType;
    private int vehicleAge;
    private int noClaimYears;

    public VehiclePolicy(int sumInsured, char vehicleType, int vehicleAge, int noClaimYears) {
        super(sumInsured);
        this.vehicleType = vehicleType;
        this.vehicleAge = vehicleAge;
        this.noClaimYears = noClaimYears;
    }

    // To_Trainee
    public int identifyOwnDamageRate() {
        int damageRate = 0;
        // write your code here
        if(this.vehicleType == 'C' || this.vehicleType == 'c') damageRate = 3;
        else if(this.vehicleType == 'B' || this.vehicleType =='b') damageRate = 2;
        else if(this.vehicleType == 'T' || this.vehicleType == 't') damageRate = 5;
        else{
            damageRate = -1;
        }
        return damageRate;
    }

    // To_Trainee
    @Override
    public void calculatePremium() {
        // write your code here

        super.calculatePremium();
        float thirdPartyPremium = super.getPremium();

        int damageRate = this.identifyOwnDamageRate();

        if(thirdPartyPremium == -1.0f || damageRate == -1 || vehicleAge <0 || vehicleAge >15 || noClaimYears < 0){
            super.setPremium(-1.0f);
        }
        else{
            float ownDamage =damageRate/100.0f * super.getSumInsured();
            if(vehicleAge >=6 && vehicleAge <= 10){
                ownDamage -= 0.20f * ownDamage;
            }
            if(vehicleAge >=11 && vehicleAge <=15){
                ownDamage -= 0.4f * ownDamage;
            }

            int ncbPercent  = 10* this.noClaimYears;

            if(ncbPercent >50) ncbPercent = 50;

            ownDamage-=ownDamage * ncbPercent/100.0f;
            super.setPremium(ownDamage+thirdPartyPremium);
        }
    }

    @Override
    public String toString() {
        return "VehiclePolicy (InsurancePolicy (sumInsured=" + this.getSumInsured()
                + ") vehicleType=" + this.vehicleType + ", vehicleAge=" + this.vehicleAge
                + ", noClaimYears=" + this.noClaimYears + ")";
    }
}

class Tester5 {
    public static void main(String[] args) {
        int[] ages1 = {30, 28, 5, 62};
        String[] c1 = {"diabetes", "Asthma", "DIABETES", null, "Allergy"};
        HealthPolicy h1 = new HealthPolicy(500000, ages1, c1);
        h1.calculatePremium();
        System.out.println("T1 Health 4 members, repeated condition: " + h1.getPremium());

        HealthPolicy h2 = new HealthPolicy(500000, new int[]{40}, null);
        h2.calculatePremium();
        System.out.println("T2 Health 1 member, null conditions: " + h2.getPremium());

        String[] c3 = {"Diabetes", "Hypertension", "Asthma"};
        HealthPolicy h3 = new HealthPolicy(500000, new int[]{20, 50}, c3);
        h3.calculatePremium();
        System.out.println("T3 Health loading above cap: " + h3.getPremium());

        HealthPolicy h4 = new HealthPolicy(500000, new int[]{30, 81}, c3);
        h4.calculatePremium();
        System.out.println("T4 Health age 81: " + h4.getPremium());

        HealthPolicy h5 = new HealthPolicy(500000, new int[]{30, 30, 30, 30, 30, 30, 30}, c3);
        h5.calculatePremium();
        System.out.println("T5 Health 7 members: " + h5.getPremium());

        HealthPolicy h6 = new HealthPolicy(500000, new int[]{}, c3);
        h6.calculatePremium();
        System.out.println("T6 Health empty members: " + h6.getPremium());

        HealthPolicy h7 = new HealthPolicy(500000, null, c3);
        h7.calculatePremium();
        System.out.println("T7 Health null members: " + h7.getPremium());

        HealthPolicy h8 = new HealthPolicy(500000, new int[]{0, 80}, null);
        h8.calculatePremium();
        System.out.println("T8 Health ages 0 and 80: " + h8.getPremium());

        HealthPolicy h9 = new HealthPolicy(0, new int[]{30}, null);
        h9.calculatePremium();
        System.out.println("T9 Health zero sum insured: " + h9.getPremium());

        HealthPolicy h10 = new HealthPolicy(500000, new int[]{25, 26, 45, 46, 60, 61}, null);
        h10.calculatePremium();
        System.out.println("T10 Health 6 members, age slab edges: " + h10.getPremium());

        VehiclePolicy v1 = new VehiclePolicy(600000, 'c', 7, 3);
        v1.calculatePremium();
        System.out.println("T11 Vehicle car age 7: " + v1.getPremium());

        VehiclePolicy v2 = new VehiclePolicy(100000, 'b', 2, 7);
        v2.calculatePremium();
        System.out.println("T12 Vehicle bike, NCB capped: " + v2.getPremium());

        VehiclePolicy v3 = new VehiclePolicy(1000000, 't', 12, 0);
        v3.calculatePremium();
        System.out.println("T13 Vehicle truck age 12: " + v3.getPremium());

        VehiclePolicy v4 = new VehiclePolicy(600000, 'c', 16, 3);
        v4.calculatePremium();
        System.out.println("T14 Vehicle age 16: " + v4.getPremium());

        VehiclePolicy v5 = new VehiclePolicy(600000, 'X', 7, 3);
        v5.calculatePremium();
        System.out.println("T15 Vehicle unknown type: " + v5.getPremium());

        VehiclePolicy v6 = new VehiclePolicy(200000, 'c', 15, 1);
        v6.calculatePremium();
        System.out.println("T16 Vehicle age 15: " + v6.getPremium());

        VehiclePolicy v7 = new VehiclePolicy(100000, 'c', 5, 0);
        v7.calculatePremium();
        System.out.println("T17 Vehicle age 5: " + v7.getPremium());

        VehiclePolicy v8 = new VehiclePolicy(100000, 'c', 6, 0);
        v8.calculatePremium();
        System.out.println("T18 Vehicle age 6: " + v8.getPremium());

        VehiclePolicy v9 = new VehiclePolicy(100000, 'c', 11, 0);
        v9.calculatePremium();
        System.out.println("T19 Vehicle age 11: " + v9.getPremium());

        VehiclePolicy v10 = new VehiclePolicy(100000, 'c', 3, -1);
        v10.calculatePremium();
        System.out.println("T20 Vehicle negative no-claim years: " + v10.getPremium());

        VehiclePolicy v11 = new VehiclePolicy(0, 'c', 3, 1);
        v11.calculatePremium();
        System.out.println("T21 Vehicle zero sum insured: " + v11.getPremium());
    }
}
