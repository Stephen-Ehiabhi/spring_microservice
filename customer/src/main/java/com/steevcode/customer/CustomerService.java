package com.steevcode.customer;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public ResponseEntity<Customer> registerCustomer(CustomerRequestDTO request) {

        Customer customer = Customer.builder()
                .firstname(request.firstname())
                .lastname(request.lastname())
                .email(request.email())
                .build();

        // todo: check if email exists
        if (customerRepository.existsByEmail(customer.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }

        // todo: check if fraudster

       customerRepository.save(customer);

        // todo: send notification
    }

    public Optional<Customer> getCustomer(Integer customerId) {
        return customerRepository.findById(customerId);
    }

    public List<Customer> getAllCustomer() {
       return customerRepository.findAll();
    }

    public ResponseEntity<Customer> updateCustomer(Integer customerId, CustomerRequestDTO request) {
        Optional<Customer> customer = customerRepository.findById(customerId);
        if (!customer.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        Customer newcustomer = customer.get();
        newcustomer.setFirstname(request.firstname());
        newcustomer.setLastname(request.lastname());
        newcustomer.setEmail(request.email());

        return ResponseEntity.status(HttpStatus.OK).body(customerRepository.save(newcustomer));
    }

    public void deleteCustomer(Integer customerId) {
       customerRepository.deleteById(customerId);
    }
}
