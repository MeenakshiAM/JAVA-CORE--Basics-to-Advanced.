import java.util.Arrays;

class StudentFee {
    private int tuitionFee;
    private float totalFee;

    public StudentFee(int tuitionFee) {
        this.tuitionFee = tuitionFee;
    }

    public int getTuitionFee() {
        return this.tuitionFee;
    }

    public float getTotalFee() {
        return this.totalFee;
    }

    public void setTotalFee(float totalFee) {
        this.totalFee = totalFee;
    }

    public void calculateTotalFee() {
        float totalFee = -1.0f;
        if (this.tuitionFee > 0) {
            totalFee = this.tuitionFee + 1000;
        }
        this.totalFee = totalFee;
    }

    @Override
    public String toString() {
        return "StudentFee (tuitionFee=" + this.tuitionFee + ")";
    }
}

class HostelStudent extends StudentFee {
    private static String[][] roomDetailsArr = {{"Single", "Double", "Triple"}, {"60000", "42000", "30000"}};
    private String studentId;
    private String roomType;
    private int mealsPerDay;

    public HostelStudent(int tuitionFee, String studentId, String roomType, int mealsPerDay) {
        super(tuitionFee);
        this.studentId = studentId;
        this.roomType = roomType;
        this.mealsPerDay = mealsPerDay;
    }

    public String getStudentId() {
        return this.studentId;
    }

    public String getRoomType() {
        return this.roomType;
    }

    // To_Trainee
    public Boolean validateStudentId() {
        // write your code here
        if (this.getStudentId() == null || this.getStudentId().length() != 7 ||
        !this.getStudentId().substring(0,2).equalsIgnoreCase("HS") || !this.getStudentId().substring(2,7).matches("^[0-9]+$")) {
            return false;
        }
        // change return statement accordingly
        return true;
    }

    // To_Trainee
    @Override
    public void calculateTotalFee() {
        // write your code here
        super.calculateTotalFee();
        float basicFee = super.getTotalFee();

        if(basicFee == -1.0f || !this.validateStudentId() || this.mealsPerDay <1 || this.mealsPerDay >3) {
            super.setTotalFee(-1.0f);
        }
        else{
            int idx = -1;
            boolean flag = false;

            for(int i= 0 ; i< HostelStudent.roomDetailsArr[0].length; i++) {
                String s = HostelStudent.roomDetailsArr[0][i];

                if(roomType.equalsIgnoreCase(s)) {
                    flag = true;
                    idx = i;
                    break;
                }
            }

            if(flag) {
                float roomRent  = Integer.parseInt(HostelStudent.roomDetailsArr[1][idx]);
                int admissionNo  = Integer.parseInt(this.getStudentId().substring(2,7));

                if(admissionNo < 20000){
                    roomRent -= (0.1f * roomRent);
                }

                int mealsCharge = this.mealsPerDay * 9000;

                super.setTotalFee(basicFee + roomRent+mealsCharge);
            }
            else {
                super.setTotalFee(-1.0f);
            }

        }
    }

    @Override
    public String toString() {
        return "HostelStudent (StudentFee (tuitionFee=" + this.getTuitionFee()
                + ") studentId=" + this.studentId + ", roomType=" + this.roomType
                + ", mealsPerDay=" + this.mealsPerDay + ")";
    }
}

class DayScholar extends StudentFee {
    private int distanceKm;
    private int noOfSiblings;

    public DayScholar(int tuitionFee, int distanceKm, int noOfSiblings) {
        super(tuitionFee);
        this.distanceKm = distanceKm;
        this.noOfSiblings = noOfSiblings;
    }

    // To_Trainee
    public int identifyTransportFee() {
        int transportFee = 0;
        // write your code here

        if(this.distanceKm >= 1 && this.distanceKm <=5) transportFee = 8000;
        else if(this.distanceKm >= 6 && this.distanceKm <= 15) transportFee = 14000;
        else if (this.distanceKm >=16 && this.distanceKm <=30) {
            transportFee = 20000;
        }
        else{
            transportFee = -1;
        }
        //System.out.print(transportFee);
        return transportFee;
    }

    // To_Trainee
    @Override
    public void calculateTotalFee() {
        // write your code here
        super.calculateTotalFee();
        float basicFee =  super.getTotalFee();

        int transportFee = this.identifyTransportFee();

        if(basicFee == -1.0f || transportFee == -1 || this.noOfSiblings <0){
            super.setTotalFee(-1.f);
        }
        else{
            int concessionPercent = 5 * noOfSiblings;

            if(concessionPercent > 15) concessionPercent = 15;
            float concession = concessionPercent/100.0f * basicFee;
            super.setTotalFee(basicFee+transportFee-concession);
        }
    }

    @Override
    public String toString() {
        return "DayScholar (StudentFee (tuitionFee=" + this.getTuitionFee()
                + ") distanceKm=" + this.distanceKm + ", noOfSiblings=" + this.noOfSiblings + ")";
    }
}

class Tester4 {
    public static void main(String[] args) {
        HostelStudent h1 = new HostelStudent(50000, "hs10234", "double", 2);
        h1.calculateTotalFee();
        System.out.println("T1 Hostel early admission: " + h1.getTotalFee());

        HostelStudent h2 = new HostelStudent(50000, "HS25010", "Double", 2);
        h2.calculateTotalFee();
        System.out.println("T2 Hostel later admission: " + h2.getTotalFee());

        HostelStudent h3 = new HostelStudent(50000, "HS2501", "Double", 2);
        h3.calculateTotalFee();
        System.out.println("T3 Hostel 6-char id: " + h3.getTotalFee());

        HostelStudent h4 = new HostelStudent(50000, "HX25010", "Double", 2);
        h4.calculateTotalFee();
        System.out.println("T4 Hostel wrong prefix: " + h4.getTotalFee());

        HostelStudent h5 = new HostelStudent(50000, "HS25A10", "Double", 2);
        h5.calculateTotalFee();
        System.out.println("T5 Hostel letter in digits: " + h5.getTotalFee());

        HostelStudent h6 = new HostelStudent(50000, "HS25010", "Dorm", 2);
        h6.calculateTotalFee();
        System.out.println("T6 Hostel unknown room: " + h6.getTotalFee());

        HostelStudent h7 = new HostelStudent(50000, "HS25010", "Double", 4);
        h7.calculateTotalFee();
        System.out.println("T7 Hostel 4 meals: " + h7.getTotalFee());

        HostelStudent h8 = new HostelStudent(50000, "HS25010", "Double", 0);
        h8.calculateTotalFee();
        System.out.println("T8 Hostel 0 meals: " + h8.getTotalFee());

        HostelStudent h9 = new HostelStudent(30000, "HS20000", "single", 3);
        h9.calculateTotalFee();
        System.out.println("T9 Hostel admission number exactly 20000: " + h9.getTotalFee());

        HostelStudent h10 = new HostelStudent(30000, "hs19999", "single", 1);
        h10.calculateTotalFee();
        System.out.println("T10 Hostel admission number 19999: " + h10.getTotalFee());

        HostelStudent h11 = new HostelStudent(0, "HS10234", "single", 1);
        h11.calculateTotalFee();
        System.out.println("T11 Hostel zero tuition: " + h11.getTotalFee());

        HostelStudent h12 = new HostelStudent(50000, null, "single", 1);
        h12.calculateTotalFee();
        System.out.println("T12 Hostel null id: " + h12.getTotalFee());

        DayScholar d1 = new DayScholar(40000, 10, 2);
        d1.calculateTotalFee();
        System.out.println("T13 Day scholar 10 km, 2 siblings: " + d1.getTotalFee());

        DayScholar d2 = new DayScholar(40000, 10, 5);
        d2.calculateTotalFee();
        System.out.println("T14 Day scholar 5 siblings: " + d2.getTotalFee());

        DayScholar d3 = new DayScholar(40000, 5, 0);
        d3.calculateTotalFee();
        System.out.println("T15 Day scholar 5 km: " + d3.getTotalFee());

        DayScholar d4 = new DayScholar(40000, 6, 1);
        d4.calculateTotalFee();
        System.out.println("T16 Day scholar 6 km: " + d4.getTotalFee());

        DayScholar d5 = new DayScholar(40000, 16, 3);
        d5.calculateTotalFee();
        System.out.println("T17 Day scholar 16 km, 3 siblings: " + d5.getTotalFee());

        DayScholar d6 = new DayScholar(40000, 30, 0);
        d6.calculateTotalFee();
        System.out.println("T18 Day scholar 30 km: " + d6.getTotalFee());

        DayScholar d7 = new DayScholar(40000, 31, 0);
        d7.calculateTotalFee();
        System.out.println("T19 Day scholar 31 km: " + d7.getTotalFee());

        DayScholar d8 = new DayScholar(40000, 0, 0);
        d8.calculateTotalFee();
        System.out.println("T20 Day scholar 0 km: " + d8.getTotalFee());

        DayScholar d9 = new DayScholar(40000, 10, -1);
        d9.calculateTotalFee();
        System.out.println("T21 Day scholar negative siblings: " + d9.getTotalFee());
    }
}
