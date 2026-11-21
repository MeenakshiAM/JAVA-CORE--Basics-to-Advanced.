class CourseEnrollment {
    private Learner learner;
    private int noOfHours;
    private int noOfWeeks;

    public CourseEnrollment(Learner learner, int noOfHours) {
        this.learner = learner;
        this.noOfHours = noOfHours;
        this.noOfWeeks = 0;
    }

    public int getNoOfWeeks() {
        return this.noOfWeeks;
    }

    public Learner getLearner() {
        return this.learner;
    }

    public int identifyNoOfWeeks() {
        this.noOfWeeks = this.noOfHours / 10;
        return this.noOfHours % 10;
    }

    public double calculateFinalAmount() {
        return 0.0;
    }
}

class Learner {
    public static String[] learnerIdArr = {"L701G", "L702S", "L703B", "L704G"};
    public static int[] rewardPointsArr = {500, 1500, 0, 3200};
    private String learnerId;
    private String learnerName;

    public Learner(String learnerId, String learnerName) {
        this.learnerId = learnerId;
        this.learnerName = learnerName;
    }

    public String getLearnerId() {
        return this.learnerId;
    }

    public String getLearnerName() {
        return this.learnerName;
    }

    public int findIndex() {
        for (int ind = 0; ind < Learner.learnerIdArr.length; ind++) {
            if (Learner.learnerIdArr[ind].equals(this.learnerId)) {
                return ind;
            }
        }
        return -1;
    }

    // To_Trainee ----------------------------

    public float calculateDiscount(float feeAmount) {
        float discountAmount = 0.0f;

        int present =  this.findIndex();

        if(present != -1) {
            char last =  this.getLearnerId().charAt(this.getLearnerId().length()-1);

            if(last == 'G'){
                discountAmount =  0.25f * feeAmount;
            }
            else if(last == 'S'){
                discountAmount =  0.15f * feeAmount;
            } else if (last == 'B') {
                discountAmount = 0.05f * feeAmount;

            }
            if(discountAmount > 1000.0f) discountAmount = 1000.0f;
        }
        else{
            discountAmount = -1.0f;
        }
        return discountAmount;
    }
}

class OnlineCourse extends CourseEnrollment {
    private String courseType;

    public OnlineCourse(Learner learner, int noOfHours, String courseType) {
        super(learner, noOfHours);
        this.courseType = courseType;
    }

    // To_Trainee ----------------------

    @Override
    public double calculateFinalAmount() {
        double finalAmount = 0.0;

        int leftOverHours = super.identifyNoOfWeeks();
        int weeklyFee = CourseDetails.identifyWeeklyFee(this.courseType);

        if(weeklyFee == -1 || leftOverHours < 0 || super.getNoOfWeeks() <0){
            finalAmount = -1.0;
        }
        else {
            int feeAmount = super.getNoOfWeeks() * weeklyFee;
            int leftOverAmount = leftOverHours * 100;
            feeAmount +=  leftOverAmount;
             float discountAmount = super.getLearner().calculateDiscount(feeAmount);

            if(discountAmount == -1.0f) finalAmount = -1.0;
            else {
                float taxableAmount =  feeAmount  - discountAmount;
                float gstAmount =  0.18f * taxableAmount;
                finalAmount = gstAmount + taxableAmount;

                int idx = super.getLearner().findIndex();

                int reward = Learner.rewardPointsArr[idx];
                int nos =  (int)finalAmount/100;

               // finalAmount += nos;
                Learner.rewardPointsArr[idx] +=  nos;
            }
        }
        return finalAmount;
    }
}

class CourseDetails {
    private static String[] courseTypesArr = {"Java", "Python", "DataScience"};
    private static int[] weeklyFeesArr = {1200, 1000, 2000};

    // To_Trainee
    public static int identifyWeeklyFee(String courseType) {
        int weeklyFee = 0;

        int idx = 0;
        boolean flag = false;

        for(int i= 0; i<CourseDetails.courseTypesArr.length; i++) {
            String s = CourseDetails.courseTypesArr[i];

            if(s.equalsIgnoreCase(courseType)) {
                flag = true;
                idx = i;
                break;
            }
        }

        if(flag) {
            weeklyFee = CourseDetails.weeklyFeesArr[idx];
        }
        else {
            weeklyFee = -1;
        }
        return weeklyFee;
    }
}

class Tester2 {
    public static void main(String[] args) {
        Learner learnerObj = new Learner("L702S", "Asha");
        OnlineCourse courseObj = new OnlineCourse(learnerObj, 35, "Python");
        for (int ind = 0; ind < Learner.learnerIdArr.length; ind++) {
            System.out.print(Learner.learnerIdArr[ind] + ":" + Learner.rewardPointsArr[ind] + " ");
        }
        System.out.println();
        double finalAmount = courseObj.calculateFinalAmount();
        System.out.println("Final bill amount: " + finalAmount);
        for (int ind = 0; ind < Learner.learnerIdArr.length; ind++) {
            System.out.print(Learner.learnerIdArr[ind] + ":" + Learner.rewardPointsArr[ind] + " ");
        }
    }
}
