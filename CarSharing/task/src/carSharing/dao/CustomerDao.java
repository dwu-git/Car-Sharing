package carSharing.dao;

import carSharing.model.Car;
import carSharing.model.Customer;

import java.util.List;

public interface CustomerDao {
    List<Customer> findAllCustomers();
    void setCustomerRentedCarIdToNull(Customer customer);
    void setCustomerRentedCarId(Customer customer, Car car);
    void addCustomer(Customer customer);
}
