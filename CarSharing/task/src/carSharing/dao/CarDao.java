package carSharing.dao;

import carSharing.model.Car;
import carSharing.model.Company;
import carSharing.model.Customer;

import java.util.List;

public interface CarDao {
    List<Car> findCars();
    List<Car> findAllCarsInCompany(Company company);
    List<Car> findFreeCarsInCompany(Company company);
    Car findRentedCustomerCar(Customer customer);
    void addCar(Car car);
}
