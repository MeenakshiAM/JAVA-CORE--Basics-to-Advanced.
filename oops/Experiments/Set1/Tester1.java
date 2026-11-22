import java.util.*;

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

        float ticketPrice = super.findPricePerSeat(showName);

        if(ticketPrice == -1.0f) pricePerSeat = -1.0f;
        else{
            this.identifyConvenienceFee();

            if(this.convenienceFeePercentage == -1) {
                pricePerSeat = -1.0f;
            }
            else {
                float conveniencefee = this.convenienceFeePercentage/100.0f * ticketPrice;
                pricePerSeat  +=conveniencefee + ticketPrice;
            }
        }
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
        String showName = booking.getShowName();

        boolean flag  = false;
        int idx = -1;
        for(int i= 0 ; i< Theatre.showNameArr.length; i++) {
            String s = Theatre.showNameArr[i];

            if(s.equals(showName)){
                flag = true;
                idx = i;
                break;
            }
        }

        if(flag){
            int seats =  Theatre.seatsAvailableArr[idx];
            if(seats >= booking.getSeatsRequired()){
                Theatre.seatsAvailableArr[idx] = seats - booking.getSeatsRequired();

            }
            else{
                return -1;
            }
        }
        else {
            return  -1;
        }

        return 0;
    }

    //To Trainee
    public void bookTickets() {
        //write your code here

        int seats = this.checkSeatAvailability();// oh this. is for avail
        System.out.println(seats);
        float pricePerSeat =  this.findPricePerSeat(booking.getShowName());
        //System.out.println(pricePerSeat);
        if(seats == -1 || pricePerSeat == -1.0f) {
            booking.setTotalAmount(-1.0);
            booking.setBookingId("NA");
        }
        else{
            booking.setTotalAmount(booking.getSeatsRequired()*pricePerSeat);
            booking.generateBookingId();
        }
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

        this.setBookingId("BK"+Booking.counter+"");
        Booking.counter++;
    }
}

class Tester1 {
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
