import java.net.SocketOption;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class basic {
    public static class Employee {
        private String name;
        private String department;
        private double salary;

        // Constructor matching your list
        public Employee(String name, String department, double salary) {
            this.name = name;
            this.department = department;
            this.salary = salary;
        }

        // Getters
        public String getName() {
            return name;
        }

        public String getDepartment() {
            return department;
        }

        public double getSalary() {
            return salary;
        }

        // Setters
        public void setName(String name) {
            this.name = name;
        }

        public void setDepartment(String department) {
            this.department = department;
        }

        public void setSalary(double salary) {
            this.salary = salary;
        }

        // toString method so it prints nicely if you output the objects
        @Override
        public String toString() {
            return "Employee{" +
                    "name='" + name + '\'' +
                    ", department='" + department + '\'' +
                    ", salary=" + salary +
                    '}';
        }
    }
    public static void main(String[] args) {
        List<Integer> nums = Arrays.asList(1,2,3,4,5,10,22,14,29,36);

        // print all the nos
        nums.stream().forEach(x -> System.out.print(x + " "));

        System.out.println();
        System.out.println("even nos");

        // print only even nos
        nums.stream().filter(x->x % 2 == 0).forEach(x->System.out.print(x+ " "));

        System.out.println();
        System.out.println("sq nos");

        //print square of all nos.
        nums.stream().map(x -> x*x).forEach(x->System.out.print(x+" "));

        System.out.println();
        System.out.println("nos greater than 10");

        //find all nos. greater than 10

        nums.stream().filter(x -> x > 10).forEach(x->System.out.print(x+" "));
        System.out.println();
        System.out.println("count even");

        // count the even nos

        long n = nums.stream().filter(x -> x % 2 == 0).count();
        System.out.println(n);
        System.out.println("sum of all nos.");

        // sum of all nos
        int sums = nums.stream().collect(Collectors.summingInt(x->x));
        int sum = nums.stream()
                .reduce(0, Integer::sum);
        System.out.println(sum);
        System.out.println(sums);
        System.out.println("remove duplicate");

        //remove duplicate
        nums.stream().distinct().forEach(x->System.out.print(x+" "));
        System.out.println();
        System.out.println("ascending order");

        // ascending order
        nums.stream()
                .sorted()
                .forEach(System.out::print);
        System.out.println();
        System.out.println("descending order");

        //descending
        nums.stream()
                .sorted(Comparator.reverseOrder())
                .forEach(System.out::println);
        System.out.println();
        System.out.println("Collect all even numbers into a list.");

        //Collect all even numbers into a list.
        List<Integer> even = nums.stream().filter(x -> x%2==0).toList();
        System.out.println(even);
        System.out.println("Find the maximum number..");


        //find the max
        Optional<Integer> max = nums.stream()
                .max(Integer::compareTo);
        System.out.println(max);
        System.out.println("Find the min  number..");

        //find the max
        Optional<Integer> min = nums.stream()
                .min(Integer::compareTo);
        System.out.println(min);
        System.out.println("Find the 1st even  number..");

        //1st even no.
        Optional<Integer> first = nums.stream()
                .filter(x -> x % 2 == 0)
                .findFirst();
        System.out.println(first);
        System.out.println("Remove duplicates & sort in descending order");

        //Remove duplicates & sort in descending order
        nums.stream().distinct().sorted(Comparator.reverseOrder()).forEach(x->System.out.print(x+" "));
        System.out.println(" ");
        System.out.println("Filter odd numbers & square them");


        //filter odd nos and square them
        nums.stream()
                .filter(x->x % 2!= 0)
                .map(x -> x*x)
                .forEach(x-> System.out.print(x+" "));
        System.out.println(" ");
        System.out.println("Find the 2nd and 3rd elements ");

        //find the 2nd and 3rd element

        List<Integer> lt = nums.stream()
                .skip(1).limit(2)
                .collect(Collectors.toList());
        System.out.println(lt);
        System.out.println("Partition all into even and odd");

        // Partition a likst int0 even and odd;

        Map<Boolean,List<Integer>> map = nums.stream()
                .collect(Collectors
                        .partitioningBy(x->x%2 == 0));
        System.out.println(map);
        System.out.println("find employees with highest paid salary");

        // find the employees with the highest paid salaries
        // employee data
        List<Employee> employees = new ArrayList<>(Arrays.asList(
                new Employee("Abhishek", "IT", 50000),
                new Employee("Ankit", "IT", 70000),
                new Employee("Rahul", "HR", 40000),
                new Employee("Tina", "HR", 45000),
                new Employee("Esha", "Finance", 60000),
                new Employee("Naman", "Finance", 55000),
                new Employee("Sachit", "IT", 80000),
                new Employee("Pushp", "Marketing", 50000),
                new Employee("Sumit", "Marketing", 52000)
        ));
        // find the employees with the highest paid salaries
        Optional<Employee> highestPaid = employees.stream()
                .max(Comparator.comparingDouble(Employee::getSalary));

        highestPaid.ifPresent(emp ->
                System.out.println("Highest Paid: " + emp.getName() + " ($" + emp.getSalary() + ")")
        );

        System.out.println(" ");
        System.out.println(" 2nd highest salary ");

        // get the 2nd highest salary
        // Group by salary in descending order
        Map<Double, List<Employee>> salaryMap = employees.stream()
                .collect(Collectors.groupingBy(
                        Employee::getSalary,
                        () -> new TreeMap<>(Comparator.reverseOrder()),
                        Collectors.toList()
                ));

        // Skip the 1st entry (highest) and grab the 2nd entry
        List<Employee> secondHighestEarners = salaryMap.values().stream()
                .skip(1)
                .findFirst()
                .orElse(List.of()); // Returns an empty list if everyone makes the same amount

        System.out.println("Second Highest Paid Employee(s): " + secondHighestEarners);
        System.out.println(" ");
        System.out.println(" Q9. Find the total salary by department ");
    }
}
