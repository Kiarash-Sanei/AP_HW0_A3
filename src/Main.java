import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int numberOfEmployees = input.nextInt();
        ArrayList<Employee> employeesInformation = new ArrayList<>(numberOfEmployees);
        double maxLocation = Double.MIN_VALUE;
        double minLocation = Double.MAX_VALUE;
        for (int i = 0; i < numberOfEmployees; i++) {
            employeesInformation.add(i, new Employee());
            double location = input.nextDouble();
            employeesInformation.get(i).location = location;
            if (maxLocation < location)
                maxLocation = location;
            if (minLocation > location)
                minLocation = location;
        }
        for (int i = 0; i < numberOfEmployees; i++)
            employeesInformation.get(i).rightVelocity = input.nextDouble();
        for (int i = 0; i < numberOfEmployees; i++)
            employeesInformation.get(i).leftVelocity = input.nextDouble();
        for (int i = 0; i < numberOfEmployees; i++)
            employeesInformation.get(i).loadingTime = input.nextDouble();
        System.out.printf("%.2f", ternarySearch(minLocation, maxLocation, employeesInformation));
    }

    public static double ternarySearch(double start, double end, ArrayList<Employee> employeeInformation) {
        double accuracy = 0.01f;
        while (end - start > accuracy) {
            double addingAmount = (end - start) / 3;
            double place1 = start + addingAmount;
            double place2 = end - addingAmount;
            double time1 = bestTimeCalculator(employeeInformation, place1);
            double time2 = bestTimeCalculator(employeeInformation, place2);
            if (time1 < time2)
                end = place2;
            else if (time1 > time2)
                start = place1;
            else {
                end = place2;
                start = place1;
            }
        }
        return bestTimeCalculator(employeeInformation, start);
    }

    public static double bestTimeCalculator(ArrayList<Employee> employeesInformation, double locationOfScarecrow) {
        double timeForThisLocation = 0;
        for (Employee employee : employeesInformation) {
            double employeeTime = employee.timeCalculator(locationOfScarecrow);
            if (employeeTime > timeForThisLocation)
                timeForThisLocation = employeeTime;
        }
        return timeForThisLocation;
    }
}

class Employee {
    double location;
    double rightVelocity;
    double leftVelocity;
    double loadingTime;

    public double timeCalculator(double locationOfScarecrow) {
        double employeeTime = this.loadingTime;
        if (this.location > locationOfScarecrow)
            employeeTime += (this.location - locationOfScarecrow) / this.leftVelocity;
        else
            employeeTime += (locationOfScarecrow - this.location) / this.rightVelocity;
        return employeeTime;
    }
}