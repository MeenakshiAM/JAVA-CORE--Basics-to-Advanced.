# OOPS Practice Set 3: Mart / OnlineMart / Order Pattern

**Pattern:** a fully implemented base class with static arrays (name, price, stock), an "online" child class that holds an `Order`-type object, and the order class with an auto-generated ID. You fill in four methods per question: the ID generator, the availability check, the overridden price method, and the method that ties them together.

Each Tester places a **sequence of orders one after another**. The static stock arrays and the static ID counter **carry over** from one order to the next, so a mistake in one method shows up in later lines too. The expected output is printed under every question. Run your code and compare line by line.

---

## How to get partial output even if you run out of time

The four methods can be written and tested in this order. Each one makes more lines of the output correct:

1. **ID generator (2 lines of code).** Do this first. Even if nothing else works, you will see the right IDs appear once you call it from the last method.
2. **Availability check.** Gives you the stock array at the end of the output and the -1 results.
3. **Price method.** Fixes every amount in the output.
4. **The method that ties them together.** It only calls the three above, so it is mostly copy-from-the-wording.

If you are short on time, write the method wording directly as code lines. Everything in the question is already a sequence of "invoke X, then if Y, do Z".

---

## Decode the wording

| The question says | You write |
|---|---|
| "Use the static variable counter appropriately" | use `Order.counter` for the number, then `Order.counter++` (the order matters: use it, then increment) |
| "prefixed with "TR" in uppercase followed by auto generated value" | `this.trackingId = "TR" + Order.counter;` |
| "Invoke the findPricePerItem() method of the Mart class by passing itemName" | `float unitPrice = super.findPricePerItem(itemName);` (`super.`, not `this.`, otherwise the method calls itself forever) |
| "Invoke the identifyOnlineDiscount() method to set onlineDiscountPercentage" | `this.identifyOnlineDiscount();` and then read `this.onlineDiscountPercentage` |
| "Check if X is -1" | `-1` for `int` values, `-1.0f` for `float` values |
| "Calculate discount on unitPrice using the percentage" | `float discount = unitPrice * this.percentage / 100;` |
| "Update the itemQuantityArr of Mart class by deducting the quantityRequired" | `Mart.itemQuantityArr[index] -= quantityRequired;` |
| "Perform case sensitive comparison" | `.equals(...)` |
| "set orderPrice to -1.0 and trackingId to "NA"" | `this.order.setOrderPrice(-1.0); this.order.setTrackingId("NA");` |
| "Calculate and set orderPrice as the product of quantity and pricePerItem" | `this.order.setOrderPrice(quantity * pricePerItem);` |
| "Invoke generateTrackingId() method of Order class" | `this.order.generateTrackingId();` |

**Things this pattern tests, every time:**
1. The **ID counter only moves on success.** Failed orders get "NA" and do not use a number.
2. The spec says to call the availability check **first**, and it **deducts the stock immediately**. So an order that later fails on the price (for example an invalid payment mode) has already removed its quantity. Follow the question exactly. Do not "fix" the order of calls.
3. `-1` (int) vs `-1.0f` (float).
4. `super.method()` inside the override.
5. Use the **getters** of the order object (`this.order.getItemName()`), since its fields are private.
6. Read what each question changes: a fee that is added instead of a discount subtracted, a 0% that is valid, a minimum quantity, a special ID format.

---

## Question 1: Theatre Booking (fee added, seats shrink, IDs only on success)

**Notes:**
 * Do not include any extra instance/static variables instance/static methods in the given classes
 * Case sensitive comparison is required to be done wherever applicable
 * Do not change any value or case of the given variables
 * Read notes and examples carefully for better understanding of the logic

**Implementation Details:**

| Class name | Implementation details |
|---|---|
| Theatre | Fully implemented |
| OnlineTheatre | Partially implemented |
| Booking | Partially implemented |

**Booking class:**

generateBookingId():
 * This method auto-generates and sets the bookingId (String)
 * The bookingId must be prefixed with "BK" in uppercase followed by auto generated value starting from 5000
 * The auto-generated value should be incremented by 1 for the next bookingId
 * Use the static variable counter appropriately to implement auto-generation logic

Example: The first bookingId would be "BK5000", second bookingId generated would be "BK5001" and so on

**Theatre class:**

showNameArr:
 * This is a static array (String[]) which has the showName (String) as its elements
 * The initial values of the showNameArr is as below:

| showNameArr | {"Comedy-Night", "Thriller-Hour", "Drama-Live", "Magic-Show"} |
|---|---|

Note:
 * This array is supplied and hence no need to code
 * Do not change the CASE of the elements of the array

ticketPriceArr:
 * This is a static array (float[]) which has the unitPrice (float) as its elements
 * The initial values of the ticketPriceArr is as below:

| ticketPriceArr | {250.0f, 300.0f, 200.0f, 350.0f} |
|---|---|

Note:
 * This array is supplied and hence no need to code

seatsAvailableArr:
 * This is a static array (int[]) which has the quantity (int) as its elements
 * The initial values of the seatsAvailableArr is as below:

| seatsAvailableArr | {50, 40, 30, 20} |
|---|---|

Note:
 * This array is supplied and hence no need to code
 * All three arrays have one to one correspondence. i.e., the ticketPrice of "Drama-Live" is 200.0f currency and there are 30 seats available in the Theatre

findPricePerSeat(showName):
 * This method is supplied and hence no need to code. It returns the unitPrice (float) of the given showName from Theatre class, or -1.0f if the showName is not present (case sensitive comparison)

**OnlineTheatre class:**

identifyConvenienceFee():
 * This method is supplied and hence no need to code. It sets the convenienceFeePercentage (int) based on the channel of the booking using the table below:

| channel | convenienceFeePercentage |
|---|---|
| "Mobile" | 4 |
| "Website" | 6 |
| Others | -1 |

checkSeatAvailability():
 * This method validates the showName (String) of the booking class and returns the seatsRequired
 * Check if the showName of the booking is available in the showNameArr of the Theatre class
   Note: Perform case sensitive comparison
 * If not available, return -1
 * Otherwise,
   * Check if sufficient seats are available in seatsAvailableArr for the booking (seats available must be greater than or equal to seatsRequired)
   * If not available, return -1
   * Otherwise,
     * Update the seatsAvailableArr of Theatre class by deducting the seatsRequired from existing value
     * Return seatsRequired for the booking

Example 1: If showName is "Drama-Live", seatsRequired is 20 and seatsAvailableArr is [50, 40, 30, 20], this method updates the seatsAvailableArr as [50, 40, 10, 20] and returns 20.
Example 2: If showName is "Magic-Night", seatsRequired is 20 and seatsAvailableArr is [50, 40, 30, 20], this method returns -1 and the seatsAvailableArr remains same.
Example 3: If showName is "Drama-Live", seatsRequired is 31 and seatsAvailableArr is [50, 40, 30, 20], this method returns -1 and the seatsAvailableArr remains same.

findPricePerSeat(showName):
 * This is an overridden method which takes showName (String) as an input parameter and returns the pricePerSeat (float) as per the logic below
 * Identify ticketPrice (float) by invoking the findPricePerSeat() method of the Theatre class by passing showName
 * Check if ticketPrice is -1.0f. If so, set the value of pricePerSeat to -1.0f
 * Otherwise,
   * Invoke the identifyConvenienceFee() method to set convenienceFeePercentage
   * Check if convenienceFeePercentage is -1. If so, set the value of pricePerSeat to -1.0f
   * Otherwise,
     * Calculate the convenience fee on ticketPrice using convenienceFeePercentage
     * Calculate the pricePerSeat by **adding** the fee calculated in the above step to ticketPrice
 * Return pricePerSeat

Example 1: If showName requested is "Drama-Live" and channel is provided as "Mobile", the pricePerSeat is 208.0 currency
Example 2: If showName requested is "Comedy-Fest" and channel is provided as "Mobile", the pricePerSeat is -1

bookTickets():
 * This method calculates the totalAmount of booking and generates bookingId
 * Invoke checkSeatAvailability() method to identify seats (int)
 * Invoke findPricePerSeat() method of OnlineTheatre class by passing showName of Booking class to identify pricePerSeat
 * If either seats is -1 or pricePerSeat is -1.0f, set totalAmount to -1.0 and bookingId to "NA" in uppercase
 * Otherwise,
   * Calculate and set totalAmount of booking as the product of seats and pricePerSeat
   * Invoke generateBookingId() method of Booking class to generate bookingId

Example 1: For a booking where showName requested is "Drama-Live", seatsRequired is 10 and channel is "Mobile", the totalAmount would be 2080.0 currency and bookingId would be "BK5000" (if it is the first booking).
Example 2: For a booking where showName requested is "Comedy-Fest", seatsRequired is 10 and channel is "Mobile", the totalAmount would be -1.0 and bookingId would be "NA".

**Code in Java**
```java
import java.util.Arrays;

class Theatre {
	public static String[] showNameArr = {"Comedy-Night", "Thriller-Hour", "Drama-Live", "Magic-Show"};
	public static float[] ticketPriceArr = {250.0f, 300.0f, 200.0f, 350.0f};
	public static int[] seatsAvailableArr = {50, 40, 30, 20};

	public float findPricePerSeat(String showName) {
		float ticketPrice = -1.0f;
		for (int index = 0; index < Theatre.showNameArr.length; index++) {
			if (showName.equals(Theatre.showNameArr[index])) {
				ticketPrice = Theatre.ticketPriceArr[index];
			}
		}
		return ticketPrice;
	}
}

class OnlineTheatre extends Theatre {
	private int convenienceFeePercentage;
	private Booking booking;

	public OnlineTheatre(Booking booking) {
		this.booking = booking;
	}

	public Booking getBooking() {
		return this.booking;
	}

	public void identifyConvenienceFee() {
		if (this.booking.getChannel().equals("Mobile")) {
			this.convenienceFeePercentage = 4;
		} else if (this.booking.getChannel().equals("Website")) {
			this.convenienceFeePercentage = 6;
		} else {
			this.convenienceFeePercentage = -1;
		}
	}

	//To Trainee
	@Override
	public float findPricePerSeat(String showName) {
		float pricePerSeat = 0.0f;
		//Write your code here
		return pricePerSeat;
	}

	@Override
	public String toString() {
		return "OnlineTheatre (Booking ( showName=" + this.booking.getShowName()
				+ ", seatsRequired=" + this.booking.getSeatsRequired() + ", channel="
				+ this.booking.getChannel() + "))";
	}

	//To Trainee
	public int checkSeatAvailability() {
		//write your code here

		//change return statement accordingly
		return 0;
	}

	//To Trainee
	public void bookTickets() {
		//write your code here
	}
}

class Booking {
	private static int counter = 5000;
	private String bookingId;
	private String showName;
	private int seatsRequired;
	private String channel;
	private double totalAmount;

	public Booking(String showName, int seatsRequired, String channel) {
		this.showName = showName;
		this.seatsRequired = seatsRequired;
		this.channel = channel;
	}

	public void setBookingId(String bookingId) {
		this.bookingId = bookingId;
	}

	public String getBookingId() {
		return this.bookingId;
	}

	public String getShowName() {
		return this.showName;
	}

	public int getSeatsRequired() {
		return this.seatsRequired;
	}

	public String getChannel() {
		return this.channel;
	}

	public double getTotalAmount() {
		return this.totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	@Override
	public String toString() {
		return "Booking (bookingId=" + this.bookingId + ", showName=" + this.showName
				+ ", seatsRequired=" + this.seatsRequired + ", channel=" + this.channel
				+ ", totalAmount=" + this.totalAmount + ")";
	}

	//To Trainee
	public void generateBookingId() {
		//write your code here
	}
}

class Tester {
	public static void main(String[] args) {
		String[] shows = {"Drama-Live", "Magic-Show", "Magic-Show", "Magic-show", "Comedy-Night", "Comedy-Night", "Comedy-Night", "Thriller-Hour"};
		int[] seats = {10, 5, 16, 2, 10, 40, 1, 1};
		String[] channels = {"Mobile", "Website", "Mobile", "Mobile", "Kiosk", "Website", "Mobile", "Mobile"};
		for (int i = 0; i < shows.length; i++) {
			Booking booking = new Booking(shows[i], seats[i], channels[i]);
			OnlineTheatre theatre = new OnlineTheatre(booking);
			theatre.bookTickets();
			System.out.println("T" + (i + 1) + " Booking ID :" + booking.getBookingId() + " Total Amount :" + booking.getTotalAmount());
		}
		System.out.println("Seats left :" + Arrays.toString(Theatre.seatsAvailableArr));
	}
}
```

**Expected output of the Tester (when your code is correct)**
```
T1 Booking ID :BK5000 Total Amount :2080.0
T2 Booking ID :BK5001 Total Amount :1855.0
T3 Booking ID :NA Total Amount :-1.0
T4 Booking ID :NA Total Amount :-1.0
T5 Booking ID :NA Total Amount :-1.0
T6 Booking ID :BK5002 Total Amount :10600.0
T7 Booking ID :NA Total Amount :-1.0
T8 Booking ID :BK5003 Total Amount :312.0
Seats left :[0, 39, 20, 15]
```

---

## Question 2: Pharmacy Orders (quantity must be positive, member discount)

**Notes:**
 * Do not include any extra instance/static variables instance/static methods in the given classes
 * Case sensitive comparison is required to be done wherever applicable
 * Do not change any value or case of the given variables
 * Read notes and examples carefully for better understanding of the logic

**Implementation Details:**

| Class name | Implementation details |
|---|---|
| Pharmacy | Fully implemented |
| OnlinePharmacy | Partially implemented |
| PharmacyOrder | Partially implemented |

**PharmacyOrder class:**

generateInvoiceNo():
 * This method auto-generates and sets the invoiceNo (String)
 * The invoiceNo must be prefixed with "INV" in uppercase followed by auto generated value starting from 100
 * The auto-generated value should be incremented by 1 for the next invoiceNo
 * Use the static variable counter appropriately to implement auto-generation logic

Example: The first invoiceNo would be "INV100", second invoiceNo generated would be "INV101" and so on

**Pharmacy class:**

itemNameArr:
 * This is a static array (String[]) which has the itemName (String) as its elements
 * The initial values of the itemNameArr is as below:

| itemNameArr | {"Paracetamol", "Vitamin-C", "Bandage", "Sanitizer"} |
|---|---|

Note:
 * This array is supplied and hence no need to code
 * Do not change the CASE of the elements of the array

itemPriceArr:
 * This is a static array (float[]) which has the unitPrice (float) as its elements
 * The initial values of the itemPriceArr is as below:

| itemPriceArr | {20.0f, 150.0f, 50.0f, 80.0f} |
|---|---|

Note:
 * This array is supplied and hence no need to code

itemQuantityArr:
 * This is a static array (int[]) which has the quantity (int) as its elements
 * The initial values of the itemQuantityArr is as below:

| itemQuantityArr | {100, 50, 75, 30} |
|---|---|

Note:
 * This array is supplied and hence no need to code
 * All three arrays have one to one correspondence. i.e., the unitPrice of "Bandage" is 50.0f currency and there are 75 pieces (quantity) available in the Pharmacy

findPricePerItem(itemName):
 * This method is supplied and hence no need to code. It returns the unitPrice (float) of the given itemName from Pharmacy class, or -1.0f if the itemName is not present (case sensitive comparison)

**OnlinePharmacy class:**

identifyMemberDiscount():
 * This method is supplied and hence no need to code. It sets the memberDiscountPercentage (int) based on the customerType of the order using the table below:

| customerType | memberDiscountPercentage |
|---|---|
| "Gold" | 10 |
| "Silver" | 5 |
| Others | -1 |

checkItemAvailability():
 * This method validates the itemName (String) and the quantityRequired (int) of the order class and returns the quantityRequired
 * If the quantityRequired is 0 or less, return -1
 * Check if the itemName of the order is available in the itemNameArr of the Pharmacy class
   Note: Perform case sensitive comparison
 * If not available, return -1
 * Otherwise,
   * Check if sufficient quantityRequired is available in itemQuantityArr for the order
   * If not available, return -1
   * Otherwise,
     * Update the itemQuantityArr of Pharmacy class by deducting the quantityRequired from existing value
     * Return quantityRequired for the order

Example 1: If itemName is "Bandage", quantityRequired is 20 and itemQuantityArr is [100, 50, 75, 30], this method updates the itemQuantityArr as [100, 50, 55, 30] and returns 20.
Example 2: If itemName is "Syrup", quantityRequired is 20 and itemQuantityArr is [100, 50, 75, 30], this method returns -1 and the itemQuantityArr remains same.
Example 3: If itemName is "Bandage", quantityRequired is 76 and itemQuantityArr is [100, 50, 75, 30], this method returns -1 and the itemQuantityArr remains same.
Example 4: If itemName is "Bandage", quantityRequired is 0, this method returns -1 and the itemQuantityArr remains same.

findPricePerItem(itemName):
 * This is an overridden method which takes itemName (String) as an input parameter and returns the pricePerItem (float) as per the logic below
 * Identify unitPrice (float) by invoking the findPricePerItem() method of the Pharmacy class by passing itemName
 * Check if unitPrice per item is -1.0f. If so, set the value of pricePerItem to -1.0f
 * Otherwise,
   * Invoke the identifyMemberDiscount() method to set memberDiscountPercentage
   * Check if memberDiscountPercentage is -1. If so, set the value of pricePerItem to -1.0f
   * Otherwise,
     * Calculate discount on unitPrice per item using memberDiscountPercentage
     * Calculate the pricePerItem by deducting the discount calculated in the above step from unitPrice per item
 * Return pricePerItem

Example 1: If itemName requested is "Vitamin-C" and customerType is provided as "Gold", the pricePerItem is 135.0 currency
Example 2: If itemName requested is "Syrup" and customerType is provided as "Gold", the pricePerItem is -1

processOrder():
 * This method calculates the orderPrice of order and generates invoiceNo
 * Invoke checkItemAvailability() method to identify quantity (int)
 * Invoke findPricePerItem() method of OnlinePharmacy class by passing itemName of PharmacyOrder class to identify pricePerItem
 * If either quantity is -1 or pricePerItem is -1.0f, set orderPrice to -1.0 and invoiceNo to "NA" in uppercase
 * Otherwise,
   * Calculate and set orderPrice of order as the product of quantity and pricePerItem
   * Invoke generateInvoiceNo() method of PharmacyOrder class to generate invoiceNo

Example 1: For an order where itemName requested is "Vitamin-C", quantityRequired is 10 and customerType is "Gold", the orderPrice would be 1350.0 currency and invoiceNo would be "INV100" (if it is the first order).
Example 2: For an order where itemName requested is "Syrup", quantityRequired is 10 and customerType is "Gold", the orderPrice would be -1.0 and invoiceNo would be "NA".

**Code in Java**
```java
import java.util.Arrays;

class Pharmacy {
	public static String[] itemNameArr = {"Paracetamol", "Vitamin-C", "Bandage", "Sanitizer"};
	public static float[] itemPriceArr = {20.0f, 150.0f, 50.0f, 80.0f};
	public static int[] itemQuantityArr = {100, 50, 75, 30};

	public float findPricePerItem(String itemName) {
		float priceItem = -1.0f;
		for (int index = 0; index < Pharmacy.itemNameArr.length; index++) {
			if (itemName.equals(Pharmacy.itemNameArr[index])) {
				priceItem = Pharmacy.itemPriceArr[index];
			}
		}
		return priceItem;
	}
}

class OnlinePharmacy extends Pharmacy {
	private int memberDiscountPercentage;
	private PharmacyOrder order;

	public OnlinePharmacy(PharmacyOrder order) {
		this.order = order;
	}

	public PharmacyOrder getOrder() {
		return this.order;
	}

	public void identifyMemberDiscount() {
		if (this.order.getCustomerType().equals("Gold")) {
			this.memberDiscountPercentage = 10;
		} else if (this.order.getCustomerType().equals("Silver")) {
			this.memberDiscountPercentage = 5;
		} else {
			this.memberDiscountPercentage = -1;
		}
	}

	//To Trainee
	@Override
	public float findPricePerItem(String itemName) {
		float pricePerItem = 0.0f;
		//Write your code here
		return pricePerItem;
	}

	@Override
	public String toString() {
		return "OnlinePharmacy (PharmacyOrder ( itemName=" + this.order.getItemName()
				+ ", quantityRequired=" + this.order.getQuantityRequired() + ", customerType="
				+ this.order.getCustomerType() + "))";
	}

	//To Trainee
	public int checkItemAvailability() {
		//write your code here

		//change return statement accordingly
		return 0;
	}

	//To Trainee
	public void processOrder() {
		//write your code here
	}
}

class PharmacyOrder {
	private static int counter = 100;
	private String invoiceNo;
	private String itemName;
	private int quantityRequired;
	private String customerType;
	private double orderPrice;

	public PharmacyOrder(String itemName, int quantityRequired, String customerType) {
		this.itemName = itemName;
		this.quantityRequired = quantityRequired;
		this.customerType = customerType;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public String getInvoiceNo() {
		return this.invoiceNo;
	}

	public String getItemName() {
		return this.itemName;
	}

	public int getQuantityRequired() {
		return this.quantityRequired;
	}

	public String getCustomerType() {
		return this.customerType;
	}

	public double getOrderPrice() {
		return this.orderPrice;
	}

	public void setOrderPrice(double orderPrice) {
		this.orderPrice = orderPrice;
	}

	@Override
	public String toString() {
		return "PharmacyOrder (invoiceNo=" + this.invoiceNo + ", itemName=" + this.itemName
				+ ", quantityRequired=" + this.quantityRequired + ", customerType="
				+ this.customerType + ", orderPrice=" + this.orderPrice + ")";
	}

	//To Trainee
	public void generateInvoiceNo() {
		//write your code here
	}
}

class Tester {
	public static void main(String[] args) {
		String[] items = {"Vitamin-C", "Bandage", "Sanitizer", "Paracetamol", "Paracetamol", "Paracetamol", "Paracetamol", "Sanitizer", "sanitizer"};
		int[] quantities = {10, 20, 31, 0, -5, 100, 1, 30, 1};
		String[] types = {"Gold", "Silver", "Gold", "Gold", "Silver", "Platinum", "Gold", "Silver", "Gold"};
		for (int i = 0; i < items.length; i++) {
			PharmacyOrder order = new PharmacyOrder(items[i], quantities[i], types[i]);
			OnlinePharmacy pharmacy = new OnlinePharmacy(order);
			pharmacy.processOrder();
			System.out.println("T" + (i + 1) + " Invoice No :" + order.getInvoiceNo() + " Order Price :" + order.getOrderPrice());
		}
		System.out.println("Stock left :" + Arrays.toString(Pharmacy.itemQuantityArr));
	}
}
```

**Expected output of the Tester (when your code is correct)**
```
T1 Invoice No :INV100 Order Price :1350.0
T2 Invoice No :INV101 Order Price :950.0
T3 Invoice No :NA Order Price :-1.0
T4 Invoice No :NA Order Price :-1.0
T5 Invoice No :NA Order Price :-1.0
T6 Invoice No :NA Order Price :-1.0
T7 Invoice No :NA Order Price :-1.0
T8 Invoice No :INV102 Order Price :2280.0
T9 Invoice No :NA Order Price :-1.0
Stock left :[0, 40, 55, 0]
```

---

## Question 3: Bookstore Orders (0% is valid, delivery charge below 500)

**Notes:**
 * Do not include any extra instance/static variables instance/static methods in the given classes
 * Case sensitive comparison is required to be done wherever applicable
 * Do not change any value or case of the given variables
 * Read notes and examples carefully for better understanding of the logic

**Implementation Details:**

| Class name | Implementation details |
|---|---|
| Bookstore | Fully implemented |
| OnlineBookstore | Partially implemented |
| BookOrder | Partially implemented |

**BookOrder class:**

generateOrderId():
 * This method auto-generates and sets the orderId (String)
 * The orderId must be prefixed with "BO" in uppercase followed by auto generated value starting from 2000
 * The auto-generated value should be incremented by 1 for the next orderId
 * Use the static variable counter appropriately to implement auto-generation logic

Example: The first orderId would be "BO2000", second orderId generated would be "BO2001" and so on

**Bookstore class:**

itemNameArr:
 * This is a static array (String[]) which has the itemName (String) as its elements
 * The initial values of the itemNameArr is as below:

| itemNameArr | {"Novel", "Textbook", "Comic", "Journal"} |
|---|---|

Note:
 * This array is supplied and hence no need to code
 * Do not change the CASE of the elements of the array

itemPriceArr:
 * This is a static array (float[]) which has the unitPrice (float) as its elements
 * The initial values of the itemPriceArr is as below:

| itemPriceArr | {350.0f, 600.0f, 120.0f, 250.0f} |
|---|---|

Note:
 * This array is supplied and hence no need to code

itemQuantityArr:
 * This is a static array (int[]) which has the quantity (int) as its elements
 * The initial values of the itemQuantityArr is as below:

| itemQuantityArr | {15, 10, 40, 25} |
|---|---|

Note:
 * This array is supplied and hence no need to code
 * All three arrays have one to one correspondence. i.e., the unitPrice of "Comic" is 120.0f currency and there are 40 pieces (quantity) available in the Bookstore

findPricePerItem(itemName):
 * This method is supplied and hence no need to code. It returns the unitPrice (float) of the given itemName from Bookstore class, or -1.0f if the itemName is not present (case sensitive comparison)

**OnlineBookstore class:**

identifyPaymentDiscount():
 * This method is supplied and hence no need to code. It sets the paymentDiscountPercentage (int) based on the paymentMode of the order using the table below:

| paymentMode | paymentDiscountPercentage |
|---|---|
| "UPI" | 10 |
| "Card" | 5 |
| "Cash" | 0 |
| Others | -1 |

checkItemAvailability():
 * This method validates the itemName (String) of the order class and returns the quantityRequired
 * Check if the itemName of the order is available in the itemNameArr of the Bookstore class
   Note: Perform case sensitive comparison
 * If not available, return -1
 * Otherwise,
   * Check if sufficient quantityRequired is available in itemQuantityArr for the order
   * If not available, return -1
   * Otherwise,
     * Update the itemQuantityArr of Bookstore class by deducting the quantityRequired from existing value
     * Return quantityRequired for the order

Example 1: If itemName is "Comic", quantityRequired is 30 and itemQuantityArr is [15, 10, 40, 25], this method updates the itemQuantityArr as [15, 10, 10, 25] and returns 30.
Example 2: If itemName is "Poster", quantityRequired is 5 and itemQuantityArr is [15, 10, 40, 25], this method returns -1 and the itemQuantityArr remains same.
Example 3: If itemName is "Comic", quantityRequired is 41 and itemQuantityArr is [15, 10, 40, 25], this method returns -1 and the itemQuantityArr remains same.

findPricePerItem(itemName):
 * This is an overridden method which takes itemName (String) as an input parameter and returns the pricePerItem (float) as per the logic below
 * Identify unitPrice (float) by invoking the findPricePerItem() method of the Bookstore class by passing itemName
 * Check if unitPrice per item is -1.0f. If so, set the value of pricePerItem to -1.0f
 * Otherwise,
   * Invoke the identifyPaymentDiscount() method to set paymentDiscountPercentage
   * Check if paymentDiscountPercentage is -1. If so, set the value of pricePerItem to -1.0f
   * Otherwise,
     * Calculate discount on unitPrice per item using paymentDiscountPercentage
     * Calculate the pricePerItem by deducting the discount calculated in the above step from unitPrice per item
 * Return pricePerItem

Example 1: If itemName requested is "Novel" and paymentMode is provided as "UPI", the pricePerItem is 315.0 currency
Example 2: If itemName requested is "Comic" and paymentMode is provided as "Cash", the pricePerItem is 120.0 currency
Example 3: If itemName requested is "Poster" and paymentMode is provided as "UPI", the pricePerItem is -1

placeOrder():
 * This method calculates the orderPrice of order and generates orderId
 * Invoke checkItemAvailability() method to identify quantity (int)
 * Invoke findPricePerItem() method of OnlineBookstore class by passing itemName of BookOrder class to identify pricePerItem
 * If either quantity is -1 or pricePerItem is -1.0f, set orderPrice to -1.0 and orderId to "NA" in uppercase
 * Otherwise,
   * Calculate the orderPrice (float) as the product of quantity and pricePerItem
   * If the orderPrice is less than 500, add a delivery charge of 40 to the orderPrice
   * Set the orderPrice of order
   * Invoke generateOrderId() method of BookOrder class to generate orderId

Example 1: For an order where itemName requested is "Novel", quantityRequired is 2 and paymentMode is "UPI", the orderPrice would be 630.0 currency and orderId would be "BO2000" (if it is the first order).
Example 2: For an order where itemName requested is "Comic", quantityRequired is 3 and paymentMode is "Cash", the orderPrice would be 400.0 currency (360.0 plus delivery charge 40) and the orderId would be generated.
Example 3: For an order where itemName requested is "Journal", quantityRequired is 2 and paymentMode is "Cash", the orderPrice would be 500.0 currency (no delivery charge as it is not less than 500).

**Code in Java**
```java
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
		//Write your code here
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

		//change return statement accordingly
		return 0;
	}

	//To Trainee
	public void placeOrder() {
		//write your code here
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
	}
}

class Tester {
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
```

**Expected output of the Tester (when your code is correct)**
```
T1 Order ID :BO2000 Order Price :630.0
T2 Order ID :BO2001 Order Price :400.0
T3 Order ID :BO2002 Order Price :277.5
T4 Order ID :BO2003 Order Price :5400.0
T5 Order ID :NA Order Price :-1.0
T6 Order ID :NA Order Price :-1.0
T7 Order ID :BO2004 Order Price :496.0
T8 Order ID :BO2005 Order Price :570.0
T9 Order ID :BO2006 Order Price :500.0
Stock left :[12, 0, 28, 22]
```

---

## Question 4: Restaurant Orders (ID goes up by 5, packaging added, 0% valid)

**Notes:**
 * Do not include any extra instance/static variables instance/static methods in the given classes
 * Case sensitive comparison is required to be done wherever applicable
 * Do not change any value or case of the given variables
 * Read notes and examples carefully for better understanding of the logic

**Implementation Details:**

| Class name | Implementation details |
|---|---|
| Restaurant | Fully implemented |
| OnlineRestaurant | Partially implemented |
| FoodOrder | Partially implemented |

**FoodOrder class:**

generateOrderCode():
 * This method auto-generates and sets the orderCode (String)
 * The orderCode must be prefixed with "OD" in uppercase followed by auto generated value starting from 100
 * The auto-generated value should be incremented by **5** for the next orderCode
 * Use the static variable counter appropriately to implement auto-generation logic

Example: The first orderCode would be "OD100", second orderCode generated would be "OD105", third would be "OD110" and so on

**Restaurant class:**

itemNameArr:
 * This is a static array (String[]) which has the itemName (String) as its elements
 * The initial values of the itemNameArr is as below:

| itemNameArr | {"Pizza", "Burger", "Pasta", "Salad"} |
|---|---|

Note:
 * This array is supplied and hence no need to code
 * Do not change the CASE of the elements of the array

itemPriceArr:
 * This is a static array (float[]) which has the unitPrice (float) as its elements
 * The initial values of the itemPriceArr is as below:

| itemPriceArr | {300.0f, 150.0f, 250.0f, 120.0f} |
|---|---|

Note:
 * This array is supplied and hence no need to code

itemQuantityArr:
 * This is a static array (int[]) which has the quantity (int) as its elements
 * The initial values of the itemQuantityArr is as below:

| itemQuantityArr | {12, 25, 15, 30} |
|---|---|

Note:
 * This array is supplied and hence no need to code
 * All three arrays have one to one correspondence. i.e., the unitPrice of "Pasta" is 250.0f currency and there are 15 plates (quantity) available in the Restaurant

findPricePerItem(itemName):
 * This method is supplied and hence no need to code. It returns the unitPrice (float) of the given itemName from Restaurant class, or -1.0f if the itemName is not present (case sensitive comparison)

**OnlineRestaurant class:**

identifyPackagingCharge():
 * This method is supplied and hence no need to code. It sets the packagingPercentage (int) based on the orderMode of the order using the table below:

| orderMode | packagingPercentage |
|---|---|
| "Delivery" | 10 |
| "Takeaway" | 5 |
| "Dine-In" | 0 |
| Others | -1 |

checkItemAvailability():
 * This method validates the itemName (String) of the order class and returns the quantityRequired
 * Check if the itemName of the order is available in the itemNameArr of the Restaurant class
   Note: Perform case sensitive comparison
 * If not available, return -1
 * Otherwise,
   * Check if sufficient quantityRequired is available in itemQuantityArr for the order
   * If not available, return -1
   * Otherwise,
     * Update the itemQuantityArr of Restaurant class by deducting the quantityRequired from existing value
     * Return quantityRequired for the order

Example 1: If itemName is "Pasta", quantityRequired is 10 and itemQuantityArr is [12, 25, 15, 30], this method updates the itemQuantityArr as [12, 25, 5, 30] and returns 10.
Example 2: If itemName is "Soup", quantityRequired is 5 and itemQuantityArr is [12, 25, 15, 30], this method returns -1 and the itemQuantityArr remains same.
Example 3: If itemName is "Pasta", quantityRequired is 16 and itemQuantityArr is [12, 25, 15, 30], this method returns -1 and the itemQuantityArr remains same.

findPricePerItem(itemName):
 * This is an overridden method which takes itemName (String) as an input parameter and returns the pricePerItem (float) as per the logic below
 * Identify unitPrice (float) by invoking the findPricePerItem() method of the Restaurant class by passing itemName
 * Check if unitPrice per item is -1.0f. If so, set the value of pricePerItem to -1.0f
 * Otherwise,
   * Invoke the identifyPackagingCharge() method to set packagingPercentage
   * Check if packagingPercentage is -1. If so, set the value of pricePerItem to -1.0f
   * Otherwise,
     * Calculate the packaging charge on unitPrice per item using packagingPercentage
     * Calculate the pricePerItem by **adding** the packaging charge calculated in the above step to unitPrice per item
 * Return pricePerItem

Example 1: If itemName requested is "Pizza" and orderMode is provided as "Delivery", the pricePerItem is 330.0 currency
Example 2: If itemName requested is "Pizza" and orderMode is provided as "Dine-In", the pricePerItem is 300.0 currency
Example 3: If itemName requested is "Soup" and orderMode is provided as "Delivery", the pricePerItem is -1

confirmOrder():
 * This method calculates the billAmount of order and generates orderCode
 * Invoke checkItemAvailability() method to identify quantity (int)
 * Invoke findPricePerItem() method of OnlineRestaurant class by passing itemName of FoodOrder class to identify pricePerItem
 * If either quantity is -1 or pricePerItem is -1.0f, set billAmount to -1.0 and orderCode to "NA" in uppercase
 * Otherwise,
   * Calculate and set billAmount of order as the product of quantity and pricePerItem
   * Invoke generateOrderCode() method of FoodOrder class to generate orderCode

Example 1: For an order where itemName requested is "Pizza", quantityRequired is 4 and orderMode is "Delivery", the billAmount would be 1320.0 currency and orderCode would be "OD100" (if it is the first order).
Example 2: For an order where itemName requested is "Soup", quantityRequired is 4 and orderMode is "Delivery", the billAmount would be -1.0 and orderCode would be "NA".

**Code in Java**
```java
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
		float pricePerItem = 0.0f;
		//Write your code here
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

		//change return statement accordingly
		return 0;
	}

	//To Trainee
	public void confirmOrder() {
		//write your code here
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
	}
}

class Tester {
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
```

**Expected output of the Tester (when your code is correct)**
```
T1 Order Code :OD100 Bill Amount :1320.0
T2 Order Code :OD105 Bill Amount :1575.0
T3 Order Code :OD110 Bill Amount :750.0
T4 Order Code :NA Bill Amount :-1.0
T5 Order Code :NA Bill Amount :-1.0
T6 Order Code :NA Bill Amount :-1.0
T7 Order Code :OD115 Bill Amount :2520.0
T8 Order Code :OD120 Bill Amount :2475.0
Stock left :[0, 0, 12, 25]
```

---

## Question 5: Accessory Shop (ID built from item name, purchase limit, coupons)

**Notes:**
 * Do not include any extra instance/static variables instance/static methods in the given classes
 * Case sensitive comparison is required to be done wherever applicable
 * Do not change any value or case of the given variables
 * Read notes and examples carefully for better understanding of the logic

**Implementation Details:**

| Class name | Implementation details |
|---|---|
| AccessoryShop | Fully implemented |
| OnlineAccessoryShop | Partially implemented |
| Purchase | Partially implemented |

**Purchase class:**

generatePurchaseId():
 * This method auto-generates and sets the purchaseId (String)
 * The purchaseId must be prefixed with the first two letters of the itemName in uppercase, followed by auto generated value starting from 9000
 * The auto-generated value should be incremented by 1 for the next purchaseId (the counter is shared by all items)
 * Use the static variable counter appropriately to implement auto-generation logic

Example: The first purchase of "Charger" would get "CH9000", the next purchase of "Earphones" would get "EA9001", the next purchase of "Cable" would get "CA9002" and so on

**AccessoryShop class:**

itemNameArr:
 * This is a static array (String[]) which has the itemName (String) as its elements
 * The initial values of the itemNameArr is as below:

| itemNameArr | {"Charger", "Earphones", "Case", "Cable"} |
|---|---|

Note:
 * This array is supplied and hence no need to code
 * Do not change the CASE of the elements of the array

itemPriceArr:
 * This is a static array (float[]) which has the unitPrice (float) as its elements
 * The initial values of the itemPriceArr is as below:

| itemPriceArr | {500.0f, 800.0f, 300.0f, 200.0f} |
|---|---|

Note:
 * This array is supplied and hence no need to code

itemQuantityArr:
 * This is a static array (int[]) which has the quantity (int) as its elements
 * The initial values of the itemQuantityArr is as below:

| itemQuantityArr | {10, 8, 25, 40} |
|---|---|

Note:
 * This array is supplied and hence no need to code
 * All three arrays have one to one correspondence. i.e., the unitPrice of "Case" is 300.0f currency and there are 25 pieces (quantity) available in the AccessoryShop

findPricePerItem(itemName):
 * This method is supplied and hence no need to code. It returns the unitPrice (float) of the given itemName from AccessoryShop class, or -1.0f if the itemName is not present (case sensitive comparison)

**OnlineAccessoryShop class:**

identifyCouponDiscount():
 * This method is supplied and hence no need to code. It sets the couponDiscountPercentage (int) based on the couponCode of the purchase using the table below:

| couponCode | couponDiscountPercentage |
|---|---|
| "SAVE10" | 10 |
| "SAVE20" | 20 |
| Others | -1 |

checkItemAvailability():
 * This method validates the itemName (String) and the quantityRequired (int) of the purchase class and returns the quantityRequired
 * A customer can buy only 1 to 5 pieces (both included) in one purchase. If the quantityRequired is not between 1 and 5, return -1
 * Check if the itemName of the purchase is available in the itemNameArr of the AccessoryShop class
   Note: Perform case sensitive comparison
 * If not available, return -1
 * Otherwise,
   * Check if sufficient quantityRequired is available in itemQuantityArr for the purchase
   * If not available, return -1
   * Otherwise,
     * Update the itemQuantityArr of AccessoryShop class by deducting the quantityRequired from existing value
     * Return quantityRequired for the purchase

Example 1: If itemName is "Case", quantityRequired is 5 and itemQuantityArr is [10, 8, 25, 40], this method updates the itemQuantityArr as [10, 8, 20, 40] and returns 5.
Example 2: If itemName is "Mouse", quantityRequired is 2 and itemQuantityArr is [10, 8, 25, 40], this method returns -1 and the itemQuantityArr remains same.
Example 3: If itemName is "Case", quantityRequired is 6 and itemQuantityArr is [10, 8, 25, 40], this method returns -1 and the itemQuantityArr remains same (even though stock is available).
Example 4: If itemName is "Earphones", quantityRequired is 4 and itemQuantityArr is [10, 3, 25, 40], this method returns -1 and the itemQuantityArr remains same.

findPricePerItem(itemName):
 * This is an overridden method which takes itemName (String) as an input parameter and returns the pricePerItem (float) as per the logic below
 * Identify unitPrice (float) by invoking the findPricePerItem() method of the AccessoryShop class by passing itemName
 * Check if unitPrice per item is -1.0f. If so, set the value of pricePerItem to -1.0f
 * Otherwise,
   * Invoke the identifyCouponDiscount() method to set couponDiscountPercentage
   * Check if couponDiscountPercentage is -1. If so, set the value of pricePerItem to -1.0f
   * Otherwise,
     * Calculate discount on unitPrice per item using couponDiscountPercentage
     * Calculate the pricePerItem by deducting the discount calculated in the above step from unitPrice per item
 * Return pricePerItem

Example 1: If itemName requested is "Charger" and couponCode is provided as "SAVE10", the pricePerItem is 450.0 currency
Example 2: If itemName requested is "Charger" and couponCode is provided as "save10", the pricePerItem is -1
Example 3: If itemName requested is "Mouse" and couponCode is provided as "SAVE10", the pricePerItem is -1

completePurchase():
 * This method calculates the purchasePrice of purchase and generates purchaseId
 * Invoke checkItemAvailability() method to identify quantity (int)
 * Invoke findPricePerItem() method of OnlineAccessoryShop class by passing itemName of Purchase class to identify pricePerItem
 * If either quantity is -1 or pricePerItem is -1.0f, set purchasePrice to -1.0 and purchaseId to "NA" in uppercase
 * Otherwise,
   * Calculate and set purchasePrice of purchase as the product of quantity and pricePerItem
   * Invoke generatePurchaseId() method of Purchase class to generate purchaseId

Example 1: For a purchase where itemName requested is "Charger", quantityRequired is 2 and couponCode is "SAVE10", the purchasePrice would be 900.0 currency and purchaseId would be "CH9000" (if it is the first purchase).
Example 2: For a purchase where itemName requested is "Mouse", quantityRequired is 2 and couponCode is "SAVE10", the purchasePrice would be -1.0 and purchaseId would be "NA".

**Code in Java**
```java
import java.util.Arrays;

class AccessoryShop {
	public static String[] itemNameArr = {"Charger", "Earphones", "Case", "Cable"};
	public static float[] itemPriceArr = {500.0f, 800.0f, 300.0f, 200.0f};
	public static int[] itemQuantityArr = {10, 8, 25, 40};

	public float findPricePerItem(String itemName) {
		float priceItem = -1.0f;
		for (int index = 0; index < AccessoryShop.itemNameArr.length; index++) {
			if (itemName.equals(AccessoryShop.itemNameArr[index])) {
				priceItem = AccessoryShop.itemPriceArr[index];
			}
		}
		return priceItem;
	}
}

class OnlineAccessoryShop extends AccessoryShop {
	private int couponDiscountPercentage;
	private Purchase purchase;

	public OnlineAccessoryShop(Purchase purchase) {
		this.purchase = purchase;
	}

	public Purchase getPurchase() {
		return this.purchase;
	}

	public void identifyCouponDiscount() {
		if (this.purchase.getCouponCode().equals("SAVE10")) {
			this.couponDiscountPercentage = 10;
		} else if (this.purchase.getCouponCode().equals("SAVE20")) {
			this.couponDiscountPercentage = 20;
		} else {
			this.couponDiscountPercentage = -1;
		}
	}

	//To Trainee
	@Override
	public float findPricePerItem(String itemName) {
		float pricePerItem = 0.0f;
		//Write your code here
		return pricePerItem;
	}

	@Override
	public String toString() {
		return "OnlineAccessoryShop (Purchase ( itemName=" + this.purchase.getItemName()
				+ ", quantityRequired=" + this.purchase.getQuantityRequired() + ", couponCode="
				+ this.purchase.getCouponCode() + "))";
	}

	//To Trainee
	public int checkItemAvailability() {
		//write your code here

		//change return statement accordingly
		return 0;
	}

	//To Trainee
	public void completePurchase() {
		//write your code here
	}
}

class Purchase {
	private static int counter = 9000;
	private String purchaseId;
	private String itemName;
	private int quantityRequired;
	private String couponCode;
	private double purchasePrice;

	public Purchase(String itemName, int quantityRequired, String couponCode) {
		this.itemName = itemName;
		this.quantityRequired = quantityRequired;
		this.couponCode = couponCode;
	}

	public void setPurchaseId(String purchaseId) {
		this.purchaseId = purchaseId;
	}

	public String getPurchaseId() {
		return this.purchaseId;
	}

	public String getItemName() {
		return this.itemName;
	}

	public int getQuantityRequired() {
		return this.quantityRequired;
	}

	public String getCouponCode() {
		return this.couponCode;
	}

	public double getPurchasePrice() {
		return this.purchasePrice;
	}

	public void setPurchasePrice(double purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	@Override
	public String toString() {
		return "Purchase (purchaseId=" + this.purchaseId + ", itemName=" + this.itemName
				+ ", quantityRequired=" + this.quantityRequired + ", couponCode="
				+ this.couponCode + ", purchasePrice=" + this.purchasePrice + ")";
	}

	//To Trainee
	public void generatePurchaseId() {
		//write your code here
	}
}

class Tester {
	public static void main(String[] args) {
		String[] items = {"Charger", "Earphones", "Case", "Cable", "Case", "Cable", "Charger", "Charger", "Case"};
		int[] quantities = {2, 3, 6, 0, 5, 5, 5, 5, 1};
		String[] coupons = {"SAVE10", "SAVE20", "SAVE10", "SAVE10", "save10", "SAVE20", "SAVE10", "SAVE10", "SAVE20"};
		for (int i = 0; i < items.length; i++) {
			Purchase purchase = new Purchase(items[i], quantities[i], coupons[i]);
			OnlineAccessoryShop shop = new OnlineAccessoryShop(purchase);
			shop.completePurchase();
			System.out.println("T" + (i + 1) + " Purchase ID :" + purchase.getPurchaseId() + " Purchase Price :" + purchase.getPurchasePrice());
		}
		System.out.println("Stock left :" + Arrays.toString(AccessoryShop.itemQuantityArr));
	}
}
```

**Expected output of the Tester (when your code is correct)**
```
T1 Purchase ID :CH9000 Purchase Price :900.0
T2 Purchase ID :EA9001 Purchase Price :1920.0
T3 Purchase ID :NA Purchase Price :-1.0
T4 Purchase ID :NA Purchase Price :-1.0
T5 Purchase ID :NA Purchase Price :-1.0
T6 Purchase ID :CA9002 Purchase Price :800.0
T7 Purchase ID :CH9003 Purchase Price :2250.0
T8 Purchase ID :NA Purchase Price :-1.0
T9 Purchase ID :CA9004 Purchase Price :240.0
Stock left :[3, 5, 19, 35]
```
