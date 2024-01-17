package carSharing.menu;

import carSharing.dbDao.DbDao;
import carSharing.model.Car;
import carSharing.model.Company;
import carSharing.model.Customer;

import java.util.List;
import java.util.Scanner;

public class Menu {
    private final DbDao dbDao;
    private final Scanner scanner = new Scanner(System.in);

    public Menu(DbDao dbDao) {
        this.dbDao = dbDao;
    }

    public void mainMenu() {
        System.out.println("1. Log in as a manager\n" +
                "2. Log in as a customer\n" +
                "3. Create a customer\n" +
                "0. Exit");

        switch (scanner.nextInt()) {
            case 0:
                break;

            case 1: managerMenu();
            break;

            case 2: showCustomers();
            break;

            case 3: createCustomer();
            break;

            default:
                mainMenu();
        }
    }

    private void managerMenu() {
        System.out.println("1. Company list\n" +
                "2. Create a company\n" +
                "0. Back");

        switch (scanner.nextInt()) {
            case 0: mainMenu();
                break;

            case 1: showCompanies();
                break;

            case 2: createCompany();
                break;

            default:
                managerMenu();
        }
    }

    private void showCompanies() {
        if (!dbDao.findAllCompanies().isEmpty()) {
            System.out.println("Choose the company:");
            printCompanyList(dbDao.findAllCompanies());
            System.out.println("0. Back");
            carMenu();
        }
        else {
            System.out.println("The company list is empty!");
            managerMenu();
        }
    }

    private void createCompany() {
        System.out.println("Enter the company name: ");
        scanner.nextLine(); // consumes the newline character that is left over from the previous call to nextInt()
        var newCompanyName = scanner.nextLine();

        var numberOfAllCompanies = dbDao.findAllCompanies().size();
        dbDao.addCompany(new Company(numberOfAllCompanies + 1, newCompanyName));
        System.out.println("The company was created!");

        managerMenu();
    }

    private void showCustomers() {
        if (!dbDao.findAllCustomers().isEmpty()) {
            System.out.println("Customer list:");
            printCustomerList(dbDao.findAllCustomers());
            System.out.println("0. Back");
            customerMenu();
        }
        else {
            System.out.println("The customer list is empty!");
            mainMenu();
        }
    }

    private void createCustomer() {
        System.out.println("Enter the customer name:");
        scanner.nextLine(); // consumes the newline character that is left over from the previous call to nextInt()
        var customerName = scanner.nextLine();

        var numberOfAllCustomers = dbDao.findAllCustomers().size();
        var newCustomer = new Customer(numberOfAllCustomers + 1, customerName);
        dbDao.addCustomer(newCustomer);
        System.out.println("The customer was added!");
        mainMenu();
    }

    private void customerMenu() {
        var customerNumber = scanner.nextInt();
        if (customerNumber < 1 || customerNumber > dbDao.findAllCustomers().size())
            mainMenu();

        var customer = dbDao.findAllCustomers().get(customerNumber - 1);
        customerMenuOptions(customer);
    }

    private void customerMenuOptions(Customer customer) {
        System.out.println("1. Rent a car\n" +
                "2. Return a rented car\n" +
                "3. My rented car\n" +
                "0. Back");

        switch (scanner.nextInt()) {
            case 0: mainMenu();
                break;

            case 1: rentCar(customer);
                break;

            case 2: returnCar(customer);
                break;

            case 3: showRentedCars(customer);

            default:
                customerMenuOptions(customer);
        }
    }

    private void showCars(Company company) {
        if (!dbDao.findAllCarsInCompany(company).isEmpty()) {
            printCarList(dbDao.findAllCarsInCompany(company));
            carMenuOptions(company);
        }
        else {
            System.out.println("The car list is empty!");
            carMenuOptions(company);
        }
    }

    private void showRentedCars(Customer customer) {
        if (dbDao.findRentedCustomerCar(customer) == null) {
            System.out.println("You didn't rent a car!");
            customerMenuOptions(customer);
        }
        var rentedCar = dbDao.findRentedCustomerCar(customer);
        var rentedCarCompany = dbDao.findCompanyById(rentedCar.companyId());
        System.out.println("Your rented car:\n" +
                rentedCar.name() +
                "\nCompany:\n" +
                rentedCarCompany.name());
    }

    private void createCar(Company company) {
        System.out.println("Enter the car name: ");
        scanner.nextLine(); // consumes the newline character that is left over from the previous call to nextInt()
        var newCarName = scanner.nextLine();

        var numberOfAllCars = dbDao.findCars().size();
        dbDao.addCar(new Car( numberOfAllCars + 1, newCarName, company.id()));
        System.out.println("The car was added!");

        carMenuOptions(company);
    }

    private void returnCar(Customer customer) {
        if (dbDao.findRentedCustomerCar(customer) == null) {
            System.out.println("You didn't rent a car!");
            customerMenuOptions(customer);
        }
        else {
            dbDao.setCustomerRentedCarIdToNull(customer);
            System.out.println("You've returned a rented car!");
            customerMenuOptions(customer);
        }
    }

    private void rentCar(Customer customer) {
        if (dbDao.findRentedCustomerCar(customer) != null) {
            System.out.println("You've already rented a car!");
            customerMenuOptions(customer);
        }
        else if (!dbDao.findAllCompanies().isEmpty()) {
            System.out.println("Choose a company:");
            printCompanyList(dbDao.findAllCompanies());
            System.out.println("0. Back");
            chooseCompany(customer);
        }
        else {
            System.out.println("The company list is empty!");
            customerMenuOptions(customer);
        }
    }

    private void chooseCompany(Customer customer) {
        var companyNumber = scanner.nextInt();
        if (companyNumber < 1 || companyNumber > dbDao.findAllCompanies().size())
            customerMenuOptions(customer);
        else {
            var company = dbDao.findCompanyById(companyNumber);
            chooseCar(company, customer);
        }
    }

    private void chooseCar(Company company, Customer customer) {
        var freeCarsInCompany = dbDao.findFreeCarsInCompany(company);
        if (freeCarsInCompany.isEmpty()) {
            System.out.println("No available cars in the " + "'" + company.name() + "' company");
            customerMenuOptions(customer);
        }

        System.out.println("Choose a car:");
        printCarList(freeCarsInCompany);
        System.out.println("0. Back");

        var input = scanner.nextInt();
        if (input > 0 && input <= freeCarsInCompany.size()) {
            var chosenCar = freeCarsInCompany.get(input - 1);
            dbDao.setCustomerRentedCarId(customer, chosenCar);
            System.out.println("You rented " + "'" + chosenCar.name() + "'");
            customerMenuOptions(customer);
        }
        else if (input == 0)
            customerMenuOptions(customer);
        else
            chooseCar(company, customer);
    }

    private void carMenu() {
        var companyNumber = scanner.nextInt();
        if (companyNumber < 1 || companyNumber > dbDao.findAllCompanies().size())
            managerMenu();
        else {
            var company = dbDao.findCompanyById(companyNumber);
            System.out.println("'" + company.name() + "'" + " company:");
            carMenuOptions(company);
        }
    }

    private void carMenuOptions(Company company) {
        System.out.println("1. Car list\n" +
                "2. Create a car\n" +
                "0. Back");

        switch (scanner.nextInt()) {
            case 0: managerMenu();
                break;

            case 1: showCars(company);
                break;

            case 2: createCar(company);
                break;

            default:
                carMenuOptions(company);
        }
    }

    private void printCompanyList(List<Company> companyList) {
        for (var company : companyList)
            System.out.println(company.id() + ". " + company.name());
    }

    private void printCustomerList(List<Customer> customerList) {
        for (var customer : customerList)
            System.out.println(customer.id() + ". " + customer.name());
    }

    private void printCarList(List<Car> carList) {
        var carCounter = 1;
        System.out.println("Car list:");
        for (var car : carList)
            System.out.println(carCounter++ + ". " + car.name());
    }
}
