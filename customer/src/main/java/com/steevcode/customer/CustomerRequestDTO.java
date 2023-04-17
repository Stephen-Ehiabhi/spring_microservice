package com.steevcode.customer;

public record CustomerRequestDTO(
        String firstname,
        String lastname,
        String email) {
}
