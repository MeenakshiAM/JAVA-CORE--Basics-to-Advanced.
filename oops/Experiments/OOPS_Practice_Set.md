# OOPS Practice Set: 5 Questions in Exam Format

**How to use:** set a 30-minute timer per question. Copy the template into your IDE, fill in every `// To_Trainee` part, run the Tester, then compare with the expected outputs listed under each question. The answer key is a separate file, so don't open it until you've tried.

---

## Step 0: Decode the wording (read once, then use as a checklist)

The long sentences are just code lines in disguise. Translate each phrase as you read it:

| The question says | You write |
|---|---|
| "Invoke `identifyPerDayRent()` of `CarDetails` passing `carType` to get `rentPerDay` (int)" | `int rentPerDay = CarDetails.identifyPerDayRent(this.carType);` (static method, so use the **class name**) |
| "Invoke `calculateDiscount()` of `Customer` passing `rentalAmount`" | `float discountAmount = this.getCustomer().calculateDiscount(rentalAmount);` (instance method, so use the **object**, usually via a getter) |
| "Invoke `identifyJourneyDays()` (which sets `journeyDays` and returns `excessKms`); store the returned value in `excessKms`" | `int excessKms = this.identifyJourneyDays();` and later read the field with `this.getJourneyDays()` |
| "Obtain the last character of `custId`" | `char t = id.charAt(id.length() - 1);` |
| "If X is not present in the array, result is -1" | Start with `-1` (or `-1.0f`) and only change it inside the `if (found)` block |
| "Find the index of `custId` in the array" | loop, compare with `.equals()`, save `ind`, `break` |
| "Update the element at that index by adding `finalAmount`" | `arr[index] += finalAmount;` |
| "Case-insensitive comparison" | `.equalsIgnoreCase(...)` |
| "Case-sensitive comparison" | `.equals(...)` |
| "If A == -1 or B < 0 or C < 0, finalAmount is -1.0, otherwise..." | `if (A == -1 || B < 0 || C < 0) { finalAmount = -1.0; } else { ... }` |

**The percentage rule (this is where you lose marks):**
`15%` of `amount` is `amount * 15 / 100`. Multiply first, divide last, and keep a `float` on the left.
Never write `15 / 100` (integer division gives 0). Avoid `15 / 100.0f` too; `amount * 15 / 100` is cleaner and exact in these questions.

**Reading order for the question (spend 5 minutes here):**
1. Underline every **invoke / obtain / identify / add / find / update / return**. Each one is a line of code.
2. Mark which things are **supplied** (don't code) and which are **To_Trainee**.
3. Note every `-1` / `-1.0` / `-1.0f` exit: those are your `if` conditions.
4. Note the **order**: validate first, side effects (array updates, counters) only at the very end.

---

## Question 1: Hotel Stay Booking

### Guest class
- **guestIdArr**
  - Static array (`String[]`) containing `guestId` (String) elements.
  - Initial values: `{"G101V", "G102S", "G103V", "G104S", "G105S"}`
  - Supplied; no need to code. Do not change the CASE of the elements.
- **guestBillAmountArr**
  - Static array (`int[]`) containing `initialBillAmount` (int) elements.
  - Initial values: `{12000, 8500, 15400, 3000, 9750}`
  - Supplied; no need to code. Has one-to-one correspondence with `guestIdArr`.
- **upgradeGuest(roomAmount)**
  - Supplied; no need to code. If `roomAmount >= 20000`, the last character of `guestId` becomes `'V'`, in the object **and** in `guestIdArr`.
- **calculateDiscount(roomAmount)**
  - Identifies and returns `discountAmount` (float) based on `roomAmount` (float) passed as an input parameter.
  - If `guestId` is present in `guestIdArr`:
    - Invoke `upgradeGuest()` passing `roomAmount` as input parameter.
    - Obtain the last character of `guestId` (after the upgrade), which represents `guestType`.
    - Use the table to identify `discountAmount`:
      - `'V'` → 20% of `roomAmount`
      - `'S'` → 8% of `roomAmount`
  - If `guestId` is not present in `guestIdArr`, `discountAmount` is -1.0f.
  - Return `discountAmount`.
  - Assumption: `guestType` is `'V'` or `'S'`. Case-sensitive comparison for `guestId`; no need to validate assumptions.
  - Example: if `guestType` is `'S'` and `roomAmount` is 200.0f, `discountAmount` is 16.0f.

### RoomDetails class
- **roomTypesArr**: static `String[]`, `{"Standard", "Deluxe", "Suite"}`. Supplied. Do not change the CASE.
- **perDayRentsArr**: static `int[]`, `{2500, 4000, 7500}`. Supplied. One-to-one with `roomTypesArr`.
- **identifyPerDayRent(roomType)**
  - Identifies and returns `rentPerDay` (int) based on `roomType` (String).
  - Check if `roomType` is in `roomTypesArr` (case-insensitive comparison).
  - If present, return the corresponding rent from `perDayRentsArr`. Otherwise `rentPerDay` is -1.
  - Example: if `roomType` is "Suite", `rentPerDay` is 7500.

### StayBooking class (supplied)
- **identifyStayDays()**: sets `stayDays` = `noOfHours / 24` and returns `extraHours` = `noOfHours % 24`.

### RoomBooking class
- **calculateFinalAmount()**
  - Calculates and returns `finalAmount` (double) using the logic below:
  - Invoke `identifyPerDayRent()` of `RoomDetails` passing `roomType` to get `rentPerDay` (int).
  - Invoke `identifyStayDays()` of `StayBooking` (which sets `stayDays` and returns `extraHours`); store the returned value in `extraHours` (int).
  - If `rentPerDay == -1` or `extraHours < 0` or `stayDays < 0`, `finalAmount` is -1.0.
  - Otherwise:
    - Obtain `roomAmount` (float) as `stayDays * rentPerDay`.
    - Identify `extraHoursAmount` (int) using `extraHours` (150 currency charged for every extra hour). Add `extraHoursAmount` to `roomAmount`.
    - Invoke `calculateDiscount()` of `Guest` passing `roomAmount` to get `discountAmount` (float).
    - If `discountAmount == -1.0f`, `finalAmount` is -1.0.
    - Otherwise:
      - Compute `finalAmount` by subtracting `discountAmount` from `roomAmount`.
      - Find the index of the **current** `guestId` in `Guest.guestIdArr`.
      - Find the corresponding `initialBillAmount` at that index in `Guest.guestBillAmountArr` and update it by adding `finalAmount`.
  - Return `finalAmount`.
  - Assumptions: `noOfHours` is a non-zero integer; all amounts are in currency. No need to validate assumptions.
  - Example: `guestId` "G102S", `noOfHours` 53, `roomType` "Deluxe" → `finalAmount` is 8050.0 and 8500 (for "G102S") is updated to 16550.

### Template
```java
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

        return finalAmount;
    }
}

class RoomDetails {
    private static String[] roomTypesArr = {"Standard", "Deluxe", "Suite"};
    private static int[] perDayRentsArr = {2500, 4000, 7500};

    // To_Trainee
    public static int identifyPerDayRent(String roomType) {
        int rentPerDay = 0;

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
```

### Test cases (restart the program fresh for each one)
| # | Guest id, name | Hours | Room type | Expected final | Bill array afterwards |
|---|---|---|---|---|---|
| 1 | G102S, Meera | 53 | Deluxe | 8050.0 | G102S:16550, rest unchanged |
| 2 | G105S, Kiran | 100 | suite | 24480.0 | **G105V**:34230 (the id changes too!) |
| 3 | G999V, Zed | 50 | Standard | -1.0 | nothing changes |
| 4 | G101V, Ravi | 30 | Cottage | -1.0 | nothing changes (including ids) |

---

## Question 2: Courier Express Shipment

### Sender class
- **senderIdArr**
  - Static `String[]` of `senderId` elements: `{"S301X", "S302N", "S303X", "S304N", "S305N"}`
  - Supplied; no need to code. Do not change the CASE of the elements.
- **orderCountArr**
  - Static `int[]` of `orderCount` elements: `{4, 9, 14, 2, 7}`
  - Supplied. One-to-one with `senderIdArr`.
- **totalSpentArr**
  - Static `int[]` of `totalSpent` elements: `{4100, 1800, 9600, 650, 2300}`
  - Supplied. One-to-one with `senderIdArr`.
- **incrementOrderCount()**
  - Supplied; no need to code. Increases the order count of the sender in `orderCountArr` by 1 and **returns the updated order count** (int).
- **calculateDiscount(shippingAmount)**
  - Identifies and returns `discountAmount` (float) based on `shippingAmount` (float) passed as an input parameter.
  - If `senderId` is present in `senderIdArr`:
    - Invoke `incrementOrderCount()` and store the returned value in `orderCount` (int).
    - Obtain the last character of `senderId`, which represents `senderType`.
    - Use the table to identify `discountAmount`:
      - `'X'` → 12% of `shippingAmount`
      - `'N'` → 4% of `shippingAmount`
    - If `orderCount` is a multiple of 5, add a loyalty bonus of 25 to `discountAmount`.
  - If `senderId` is not present in `senderIdArr`, `discountAmount` is -1.0f.
  - Return `discountAmount`.
  - Assumption: `senderType` is `'X'` or `'N'`. Case-sensitive comparison; no need to validate assumptions.
  - Example: `senderType` 'N', `shippingAmount` 200.0f, updated `orderCount` 3 → `discountAmount` is 8.0f.

### ParcelDetails class
- **parcelTypesArr**: static `String[]`, `{"Document", "Box", "Fragile"}`. Supplied. Do not change the CASE.
- **ratePerKgArr**: static `int[]`, `{50, 75, 100}`. Supplied. One-to-one with `parcelTypesArr`.
- **identifyRatePerKg(parcelType)**
  - Identifies and returns `ratePerKg` (int). Check if `parcelType` is in `parcelTypesArr` (case-insensitive).
  - If present, return the corresponding rate. Otherwise -1.
  - Example: "Box" → 75.

### ShipmentService class (supplied)
- **identifyFullKgs()**: sets `fullKgs` = `weightInGrams / 1000` and returns `remainingGrams` = `weightInGrams % 1000`.

### ExpressShipment class
- **calculateFinalAmount()**
  - Calculates and returns `finalAmount` (double) using the logic below:
  - Invoke `identifyRatePerKg()` of `ParcelDetails` passing `parcelType` to get `ratePerKg` (int).
  - Invoke `identifyFullKgs()` (which sets `fullKgs` and returns `remainingGrams`); store the returned value in `remainingGrams` (int).
  - If `ratePerKg == -1` or `remainingGrams < 0` or `fullKgs < 0`, `finalAmount` is -1.0.
  - Otherwise:
    - Identify `billableKgs` (int): equal to `fullKgs`; if `remainingGrams > 0`, the leftover is charged as one more full kg.
    - Obtain `shippingAmount` (float) as `billableKgs * ratePerKg`. Add an express fee of 50 to `shippingAmount`.
    - Invoke `calculateDiscount()` of `Sender` passing `shippingAmount` to get `discountAmount` (float).
    - If `discountAmount == -1.0f`, `finalAmount` is -1.0.
    - Otherwise:
      - Compute `finalAmount` by subtracting `discountAmount` from `shippingAmount`.
      - Find the index of `senderId` in `Sender.senderIdArr`.
      - Find the corresponding `totalSpent` at that index in `Sender.totalSpentArr` and update it by adding `finalAmount`.
  - Return `finalAmount`.
  - Assumption: `weightInGrams` is a non-zero integer. No need to validate assumptions.
  - Example: `senderId` "S304N", `weightInGrams` 2300, `parcelType` "Box" → `finalAmount` is 264.0; `totalSpent` 650 becomes 914 and the order count of "S304N" becomes 3.

### Template
```java
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

        return finalAmount;
    }
}

class ParcelDetails {
    private static String[] parcelTypesArr = {"Document", "Box", "Fragile"};
    private static int[] ratePerKgArr = {50, 75, 100};

    // To_Trainee
    public static int identifyRatePerKg(String parcelType) {
        int ratePerKg = 0;

        return ratePerKg;
    }
}

class Tester {
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
```
(The Tester prints `id:totalSpent:orderCount`.)

### Test cases (restart fresh for each)
| # | Sender id, name | Grams | Parcel type | Expected final | Afterwards |
|---|---|---|---|---|---|
| 1 | S304N, Anu | 2300 | box | 264.0 | S304N:914:3 |
| 2 | S301X, Ravi | 4500 | Fragile | 459.0 | S301X:4559:5 (bonus applied!) |
| 3 | S303X, Dev | 3000 | document | 151.0 | S303X:9751:15 (bonus applied!) |
| 4 | S999N, Zed | 1000 | box | -1.0 | nothing changes |
| 5 | S302N, Mina | 1000 | Envelope | -1.0 | nothing changes (count stays 9!) |

---

## Question 3: Online Course Enrollment

### Learner class
- **learnerIdArr**
  - Static `String[]` of `learnerId` elements: `{"L701G", "L702S", "L703B", "L704G"}`
  - Supplied; no need to code. Do not change the CASE.
- **rewardPointsArr**
  - Static `int[]` of `rewardPoints` elements: `{500, 1500, 0, 3200}`
  - Supplied. One-to-one with `learnerIdArr`.
- **findIndex()**
  - Supplied; no need to code. Returns the index of `learnerId` in `learnerIdArr`, or -1 if not present.
- **calculateDiscount(feeAmount)**
  - Identifies and returns `discountAmount` (float) based on `feeAmount` (float) passed as an input parameter.
  - Invoke `findIndex()` to check whether `learnerId` is present.
  - If present:
    - Obtain the last character of `learnerId`, which represents `learnerType`.
    - Use the table to identify `discountAmount`:
      - `'G'` → 25% of `feeAmount`
      - `'S'` → 15% of `feeAmount`
      - `'B'` → 5% of `feeAmount`
    - The `discountAmount` cannot be more than 1000. If it is, set it to 1000.0f.
  - If `learnerId` is not present, `discountAmount` is -1.0f.
  - Return `discountAmount`.
  - Assumption: `learnerType` is 'G', 'S' or 'B'. Case-sensitive comparison; no need to validate assumptions.
  - Example: `learnerType` 'S' and `feeAmount` 200.0f → `discountAmount` is 30.0f.

### CourseDetails class
- **courseTypesArr**: static `String[]`, `{"Java", "Python", "DataScience"}`. Supplied. Do not change the CASE.
- **weeklyFeesArr**: static `int[]`, `{1200, 1000, 2000}`. Supplied. One-to-one with `courseTypesArr`.
- **identifyWeeklyFee(courseType)**
  - Identifies and returns `weeklyFee` (int). Check if `courseType` is in `courseTypesArr` (case-insensitive).
  - If present, return the corresponding weekly fee. Otherwise -1.
  - Example: "Python" → 1000.

### CourseEnrollment class (supplied)
- **identifyNoOfWeeks()**: sets `noOfWeeks` = `noOfHours / 10` (one week is 10 hours) and returns `leftoverHours` = `noOfHours % 10`.

### OnlineCourse class
- **calculateFinalAmount()**
  - Calculates and returns `finalAmount` (double) using the logic below:
  - Invoke `identifyWeeklyFee()` of `CourseDetails` passing `courseType` to get `weeklyFee` (int).
  - Invoke `identifyNoOfWeeks()` (which sets `noOfWeeks` and returns `leftoverHours`); store it in `leftoverHours` (int).
  - If `weeklyFee == -1` or `leftoverHours < 0` or `noOfWeeks < 0`, `finalAmount` is -1.0.
  - Otherwise:
    - Obtain `feeAmount` (float) as `noOfWeeks * weeklyFee`.
    - Identify `leftoverAmount` (int) using `leftoverHours` (100 currency charged for every leftover hour). Add `leftoverAmount` to `feeAmount`.
    - Invoke `calculateDiscount()` of `Learner` passing `feeAmount` to get `discountAmount` (float).
    - If `discountAmount == -1.0f`, `finalAmount` is -1.0.
    - Otherwise:
      - Obtain `taxableAmount` (float) by subtracting `discountAmount` from `feeAmount`.
      - Identify `gstAmount` (float) as 18% of `taxableAmount`.
      - Compute `finalAmount` by adding `gstAmount` to `taxableAmount`.
      - Invoke `findIndex()` of `Learner` to get the index.
      - Find the corresponding `rewardPoints` at that index in `Learner.rewardPointsArr` and update it by adding the whole number of hundreds in `finalAmount` (one point for every full 100 currency; ignore the decimal part).
  - Return `finalAmount`.
  - Assumption: `noOfHours` is a non-zero integer. No need to validate assumptions.
  - Example: `learnerId` "L702S", `noOfHours` 35, `courseType` "Python" → `finalAmount` is 3510.5 and 1500 (for "L702S") becomes 1535.

### Template
```java
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

    // To_Trainee
    public float calculateDiscount(float feeAmount) {
        float discountAmount = 0.0f;

        return discountAmount;
    }
}

class OnlineCourse extends CourseEnrollment {
    private String courseType;

    public OnlineCourse(Learner learner, int noOfHours, String courseType) {
        super(learner, noOfHours);
        this.courseType = courseType;
    }

    // To_Trainee
    @Override
    public double calculateFinalAmount() {
        double finalAmount = 0.0;

        return finalAmount;
    }
}

class CourseDetails {
    private static String[] courseTypesArr = {"Java", "Python", "DataScience"};
    private static int[] weeklyFeesArr = {1200, 1000, 2000};

    // To_Trainee
    public static int identifyWeeklyFee(String courseType) {
        int weeklyFee = 0;

        return weeklyFee;
    }
}

class Tester {
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
```

### Test cases (restart fresh for each)
| # | Learner id, name | Hours | Course | Expected final | Reward points afterwards |
|---|---|---|---|---|---|
| 1 | L702S, Asha | 35 | python | 3510.5 | L702S:1535 |
| 2 | L704G, Ravi | 62 | DataScience | 13216.0 | L704G:3332 (discount cap hit!) |
| 3 | L703B, Kiran | 40 | Python | 4484.0 | L703B:44 |
| 4 | L777G, Zed | 20 | Java | -1.0 | nothing changes |
| 5 | L701G, Dev | 20 | Cobol | -1.0 | nothing changes |

---

## Question 4: Electricity Bill (slabs + pending dues)

### Consumer class
- **consumerIdArr**
  - Static `String[]` of `consumerId` elements: `{"E401A", "E402O", "E403A", "E404O"}`
  - Supplied; no need to code. Do not change the CASE.
- **pendingDuesArr**
  - Static `int[]` of `pendingDues` elements: `{0, 250, 0, 1200}`
  - Supplied. One-to-one with `consumerIdArr`.
- **calculateDiscount(billAmount)**
  - Identifies and returns `discountAmount` (float) based on `billAmount` (float) passed as an input parameter.
  - If `consumerId` is present in `consumerIdArr`:
    - Obtain the last character of `consumerId`, which represents `consumerType`.
    - Use the table to identify `discountAmount`:
      - `'A'` (auto-pay) → 8% of `billAmount`
      - `'O'` (online) → 3% of `billAmount`
  - If `consumerId` is not present, `discountAmount` is -1.0f.
  - Return `discountAmount`.
  - Assumption: `consumerType` is 'A' or 'O'. Case-sensitive comparison; no need to validate assumptions.
  - Example: `consumerType` 'A' and `billAmount` 250.0f → `discountAmount` is 20.0f.

### ConnectionDetails class
- **connectionTypesArr**: static `String[]`, `{"Domestic", "Commercial", "Industrial"}`. Supplied. Do not change the CASE.
- **fixedChargesArr**: static `int[]`, `{100, 400, 1000}`. Supplied. One-to-one with `connectionTypesArr`.
- **identifyFixedCharge(connectionType)**
  - Identifies and returns `fixedCharge` (int). Check if `connectionType` is in `connectionTypesArr` (case-insensitive).
  - If present, return the corresponding fixed charge. Otherwise -1.
  - Example: "Commercial" → 400.

### ElectricityBill class (supplied)
- **identifyUnits()**
  - Sets `unitsConsumed` = `currentReading - previousReading`.
  - If `unitsConsumed` is negative, returns -1.
  - Otherwise returns `excessUnits`: the number of units above 200 (0 if `unitsConsumed` is 200 or less).

### MeteredBill class
- **calculateFinalAmount()**
  - Calculates and returns `finalAmount` (double) using the logic below:
  - Invoke `identifyFixedCharge()` of `ConnectionDetails` passing `connectionType` to get `fixedCharge` (int).
  - Invoke `identifyUnits()` of `ElectricityBill` (which sets `unitsConsumed` and returns `excessUnits`); store the returned value in `excessUnits` (int).
  - If `fixedCharge == -1` or `excessUnits < 0`, `finalAmount` is -1.0.
  - Otherwise:
    - Obtain `baseUnits` (int) as `unitsConsumed - excessUnits`.
    - Obtain `billAmount` (float) using the slab table:
      - First 100 of `baseUnits` → 5 per unit
      - Remaining `baseUnits` above 100 → 7 per unit
      - `excessUnits` → 10 per unit
    - Add `fixedCharge` to `billAmount`.
    - Invoke `calculateDiscount()` of `Consumer` passing `billAmount` to get `discountAmount` (float).
    - If `discountAmount == -1.0f`, `finalAmount` is -1.0.
    - Otherwise:
      - Find the index of `consumerId` in `Consumer.consumerIdArr`.
      - Obtain the `pendingDues` at that index in `Consumer.pendingDuesArr`.
      - Compute `finalAmount` by subtracting `discountAmount` from `billAmount` and adding `pendingDues` (dues get **no** discount).
      - Set the `pendingDues` at that index in `Consumer.pendingDuesArr` to 0 (it has now been paid).
  - Return `finalAmount`.
  - Assumption: all amounts are in currency. No need to validate assumptions.
  - Example: `consumerId` "E402O", `previousReading` 1200, `currentReading` 1520, `connectionType` "Commercial" → `finalAmount` is 2966.0 and the pending dues 250 (for "E402O") become 0.

### Template
```java
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

    // To_Trainee
    public float calculateDiscount(float billAmount) {
        float discountAmount = 0.0f;

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

        return finalAmount;
    }
}

class ConnectionDetails {
    private static String[] connectionTypesArr = {"Domestic", "Commercial", "Industrial"};
    private static int[] fixedChargesArr = {100, 400, 1000};

    // To_Trainee
    public static int identifyFixedCharge(String connectionType) {
        int fixedCharge = 0;

        return fixedCharge;
    }
}

class Tester {
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
```
(The Tester prints `id:pendingDues`.)

### Test cases (restart fresh for each)
| # | Consumer id, name | Prev | Curr | Connection | Expected final | Dues afterwards |
|---|---|---|---|---|---|---|
| 1 | E402O, Raj | 1200 | 1520 | commercial | 2966.0 | E402O:0 |
| 2 | E401A, Sita | 500 | 650 | Domestic | 874.0 | unchanged (E401A was already 0) |
| 3 | E404O, Arun | 3000 | 3200 | Industrial | 3334.0 | E404O:0 |
| 4 | E405A, Zed | 100 | 200 | Domestic | -1.0 | nothing changes |
| 5 | E401A, Sita | 100 | 200 | Rural | -1.0 | nothing changes |
| 6 | E401A, Sita | 700 | 650 | Domestic | -1.0 | nothing changes (negative units) |

---

## Question 5: Event Catering (tiered discount + rounding + two arrays)

### Client class
- **clientIdArr**
  - Static `String[]` of `clientId` elements: `{"CL-HY-P", "CL-BL-R", "CL-CH-P", "CL-HY-R"}`
  - Supplied; no need to code. Do not change the CASE.
- **clientBillArr**
  - Static `int[]` of `initialBillAmount` elements: `{15000, 4200, 22000, 3100}`
  - Supplied. One-to-one with `clientIdArr`.
- **clientOrderCountArr**
  - Static `int[]` of `orderCount` elements: `{5, 2, 9, 1}`
  - Supplied. One-to-one with `clientIdArr`.
- **calculateDiscount(orderAmount)**
  - Identifies and returns `discountAmount` (float) based on `orderAmount` (float) passed as an input parameter.
  - If `clientId` is present in `clientIdArr`:
    - Obtain the last character of `clientId`, which represents `clientType`.
    - Use the table to identify `discountAmount`:

      | clientType | orderAmount below 10000 | orderAmount 10000 or more |
      |---|---|---|
      | `'P'` | 12% of `orderAmount` | 18% of `orderAmount` |
      | `'R'` | 5% of `orderAmount` | 8% of `orderAmount` |
  - If `clientId` is not present in `clientIdArr`, `discountAmount` is -1.0f.
  - Return `discountAmount`.
  - Assumption: `clientType` is 'P' or 'R'. Case-sensitive comparison for `clientId`; no need to validate assumptions.
  - Example: `clientType` 'R' and `orderAmount` 400.0f → `discountAmount` is 20.0f.

### MenuDetails class
- **menuTypesArr**: static `String[]`, `{"Veg", "NonVeg", "Premium"}`. Supplied. Do not change the CASE.
- **pricePerPlateArr**: static `int[]`, `{175, 260, 400}`. Supplied. One-to-one with `menuTypesArr`.
- **identifyPricePerPlate(menuType)**
  - Identifies and returns `pricePerPlate` (int). Check if `menuType` is in `menuTypesArr` (case-insensitive).
  - If present, return the corresponding price. Otherwise -1.
  - Example: "NonVeg" → 260.

### CateringOrder class (supplied)
- **identifyBatches()**: sets `noOfBatches` = `noOfPlates / 50` and returns `loosePlates` = `noOfPlates % 50`.

### EventCatering class
- **calculateFinalAmount()**
  - Calculates and returns `finalAmount` (double) using the logic below:
  - Invoke `identifyPricePerPlate()` of `MenuDetails` passing `menuType` to get `pricePerPlate` (int).
  - Invoke `identifyBatches()` (which sets `noOfBatches` and returns `loosePlates`); store the returned value in `loosePlates` (int).
  - If `pricePerPlate == -1` or `loosePlates < 0` or `noOfBatches < 0`, `finalAmount` is -1.0.
  - Otherwise:
    - Obtain `orderAmount` (float) as `noOfPlates * pricePerPlate` (**all** plates, including the loose ones).
    - Identify `serviceAmount` (int) using `noOfBatches` (500 currency charged for every batch of 50 plates, for serving staff). Add `serviceAmount` to `orderAmount`.
    - Invoke `calculateDiscount()` of `Client` passing `orderAmount` to get `discountAmount` (float).
    - If `discountAmount == -1.0f`, `finalAmount` is -1.0.
    - Otherwise:
      - Obtain `payableAmount` (float) by subtracting `discountAmount` from `orderAmount`.
      - Round `payableAmount` to the nearest whole number to get `roundedAmount` (int); `finalAmount` is `roundedAmount`.
      - Find the index of `clientId` in `Client.clientIdArr`.
      - Update the `initialBillAmount` at that index in `Client.clientBillArr` by adding `roundedAmount`.
      - Update the `orderCount` at that index in `Client.clientOrderCountArr` by adding 1.
  - Return `finalAmount`.
  - Assumption: `noOfPlates` is a non-zero integer. No need to validate assumptions.
  - Example: `clientId` "CL-BL-R", `noOfPlates` 37, `menuType` "Veg" → `finalAmount` is 6151.0; 4200 becomes 10351 and the order count 2 becomes 3.

### Template
```java
class CateringOrder {
    private Client client;
    private int noOfPlates;
    private int noOfBatches;

    public CateringOrder(Client client, int noOfPlates) {
        this.client = client;
        this.noOfPlates = noOfPlates;
        this.noOfBatches = 0;
    }

    public int getNoOfPlates() {
        return this.noOfPlates;
    }

    public int getNoOfBatches() {
        return this.noOfBatches;
    }

    public Client getClient() {
        return this.client;
    }

    public int identifyBatches() {
        this.noOfBatches = this.noOfPlates / 50;
        return this.noOfPlates % 50;
    }

    public double calculateFinalAmount() {
        return 0.0;
    }
}

class Client {
    public static String[] clientIdArr = {"CL-HY-P", "CL-BL-R", "CL-CH-P", "CL-HY-R"};
    public static int[] clientBillArr = {15000, 4200, 22000, 3100};
    public static int[] clientOrderCountArr = {5, 2, 9, 1};
    private String clientId;
    private String clientName;

    public Client(String clientId, String clientName) {
        this.clientId = clientId;
        this.clientName = clientName;
    }

    public String getClientId() {
        return this.clientId;
    }

    public String getClientName() {
        return this.clientName;
    }

    // To_Trainee
    public float calculateDiscount(float orderAmount) {
        float discountAmount = 0.0f;

        return discountAmount;
    }
}

class EventCatering extends CateringOrder {
    private String menuType;

    public EventCatering(Client client, int noOfPlates, String menuType) {
        super(client, noOfPlates);
        this.menuType = menuType;
    }

    // To_Trainee
    @Override
    public double calculateFinalAmount() {
        double finalAmount = 0.0;

        return finalAmount;
    }
}

class MenuDetails {
    private static String[] menuTypesArr = {"Veg", "NonVeg", "Premium"};
    private static int[] pricePerPlateArr = {175, 260, 400};

    // To_Trainee
    public static int identifyPricePerPlate(String menuType) {
        int pricePerPlate = 0;

        return pricePerPlate;
    }
}

class Tester {
    public static void main(String[] args) {
        Client clientObj = new Client("CL-BL-R", "Maya");
        EventCatering orderObj = new EventCatering(clientObj, 37, "Veg");
        for (int ind = 0; ind < Client.clientIdArr.length; ind++) {
            System.out.print(Client.clientIdArr[ind] + ":" + Client.clientBillArr[ind] + ":" + Client.clientOrderCountArr[ind] + " ");
        }
        System.out.println();
        double finalAmount = orderObj.calculateFinalAmount();
        System.out.println("Final bill amount: " + finalAmount);
        for (int ind = 0; ind < Client.clientIdArr.length; ind++) {
            System.out.print(Client.clientIdArr[ind] + ":" + Client.clientBillArr[ind] + ":" + Client.clientOrderCountArr[ind] + " ");
        }
    }
}
```
(The Tester prints `id:bill:orderCount`.)

### Test cases (restart fresh for each)
| # | Client id, name | Plates | Menu | Expected final | Afterwards |
|---|---|---|---|---|---|
| 1 | CL-BL-R, Maya | 37 | veg | 6151.0 | CL-BL-R:10351:3 |
| 2 | CL-HY-P, Ravi | 100 | Premium | 33620.0 | CL-HY-P:48620:6 |
| 3 | CL-CH-P, Dev | 20 | NonVeg | 4576.0 | CL-CH-P:26576:10 |
| 4 | CL-HY-R, Anu | 130 | premium | 48760.0 | CL-HY-R:51860:2 |
| 5 | cl-hy-p, Zed | 50 | Veg | -1.0 | nothing changes |
| 6 | CL-HY-P, Ravi | 50 | Buffet | -1.0 | nothing changes |

---

## Percentage drill (2 minutes, do this before every mock)

Write the Java expression (don't run it, just write it), then check the key:

1. 15% of `rentalAmount` (float)
2. 8% of `roomAmount` (float)
3. 18% GST **added on top of** `taxableAmount` (float), as the final amount
4. A discount that can never exceed 1000
5. One reward point for every full 100 in `finalAmount` (double)
6. Round a `float payableAmount` to the nearest whole number
7. 12 currency charged per excess km, added to `rentalAmount`
8. "Charged as one more full kg if there are leftover grams": write the 2 lines
