class StayBooking {
    private Guest guest;
    private int noOfHours;
    private int stayDays;

    public StayBooking(Guest guest, int noOfHours) {
        this.guest = guest;
        this.noOfHours = noOfHours;
        this.stayDays = 0;
    }

    public int getStayDays() {
        return this.stayDays;
    }

    public Guest getGuest() {
        return this.guest;
    }

    public int identifyStayDays() {
        this.stayDays = this.noOfHours / 24;
        return this.noOfHours % 24;
    }

    public double calculateFinalAmount() {
        return 0.0;
    }
}

class Guest {
    public static String[] guestIdArr = {"G101V", "G102S", "G103V", "G104S", "G105S"};
    public static int[] guestBillAmountArr = {12000, 8500, 15400, 3000, 9750};
    private String guestId;
    private String guestName;

    public Guest(String guestId, String guestName) {
        this.guestId = guestId;
        this.guestName = guestName;
    }

    public String getGuestId() {
        return this.guestId;
    }

    public String getGuestName() {
        return this.guestName;
    }

    // To_Trainee
    public float calculateDiscount(float roomAmount) {
        float discountAmount = 0.0f;

        boolean flag = false;
        for(int i = 0; i< Guest.guestIdArr.length; i++){
            String s =  Guest.guestIdArr[i];

            if(this.getGuestId().equals(s)){
                flag = true;
                break;
            }
        }

        if(flag) {
            this.upgradeGuest(roomAmount);
            char last = this.getGuestId().charAt(this.getGuestId().length()-1);

            if(last == 'V') {
                discountAmount = 0.2f * roomAmount;
            }
            else if(last == 'S') {
                discountAmount = 0.08f * roomAmount;
            }
        }
        else{
            discountAmount = -1.0f;
        }
           // System.out.println(discountAmount);
        //if(this.getGuestId())
        return discountAmount;
    }

    public void upgradeGuest(float roomAmount) {
        if (roomAmount >= 20000) {
            int index = 0;
            for (int ind = 0; ind < Guest.guestIdArr.length; ind++) {
                if (Guest.guestIdArr[ind].equals(this.guestId)) {
                    index = ind;
                    break;
                }
            }
            this.guestId = this.guestId.substring(0, 4) + "V";
            Guest.guestIdArr[index] = this.guestId;
        }
    }
}

class RoomBooking extends StayBooking {
    private String roomType;

    public RoomBooking(Guest guest, int noOfHours, String roomType) {
        super(guest, noOfHours);
        this.roomType = roomType;
    }

    // To_Trainee
    @Override
    public double calculateFinalAmount() {
        double finalAmount = 0.0;

        RoomDetails r = new RoomDetails();

        int rentPerDay = r.identifyPerDayRent(this.roomType);
        int extraHours =  super.identifyStayDays();

        if(rentPerDay == -1 || extraHours <0 || this.getStayDays() <0){
            finalAmount = -1.0;
        }
        else{
            float roomAmount = this.getStayDays() * rentPerDay;
            int extraHoursAmount = extraHours * 150;
            roomAmount += extraHoursAmount;


            float discountAmount = super.getGuest().calculateDiscount(roomAmount);

            if(discountAmount == -1) return finalAmount=-1.0;
            else{
                finalAmount = roomAmount - discountAmount;

                String guestId = super.getGuest().getGuestId();

                int idx = 0;
                for(int i = 0; i< Guest.guestIdArr.length; i++) {
                    String s = Guest.guestIdArr[i];

                    if(s.equals(guestId)){
                        idx=i;
                        break;
                    }

                }

               int bill =  Guest.guestBillAmountArr[idx];
                finalAmount += bill;
                Guest.guestBillAmountArr[idx] = (int)finalAmount;
            }
        }

        return finalAmount;
    }
}

class RoomDetails {
    private static String[] roomTypesArr = {"Standard", "Deluxe", "Suite"};
    private static int[] perDayRentsArr = {2500, 4000, 7500};

    // To_Trainee
    public static int identifyPerDayRent(String roomType) {
        int rentPerDay = -1;

        boolean flag =  false;
        int idx = 0;
        for(int i = 0; i< RoomDetails.roomTypesArr.length; i++){
            String s = RoomDetails.roomTypesArr[i];

            if(s.equals(roomType)){
                flag = true;
                idx = i;
                break;
            }
        }

        if(flag) {
            rentPerDay =  RoomDetails.perDayRentsArr[idx];
        }
        return rentPerDay;
    }
}

class Tester {
    public static void main(String[] args) {
        Guest guestObj = new Guest("G102S", "Meera");
        RoomBooking roomBookingObj = new RoomBooking(guestObj, 53, "Deluxe");
        for (int ind = 0; ind < Guest.guestIdArr.length; ind++) {
            System.out.print(Guest.guestIdArr[ind] + ":" + Guest.guestBillAmountArr[ind] + " ");
        }
        System.out.println();
        double finalAmount = roomBookingObj.calculateFinalAmount();
        System.out.println("Final bill amount: " + finalAmount);
        for (int ind = 0; ind < Guest.guestIdArr.length; ind++) {
            System.out.print(Guest.guestIdArr[ind] + ":" + Guest.guestBillAmountArr[ind] + " ");
        }
    }
}
