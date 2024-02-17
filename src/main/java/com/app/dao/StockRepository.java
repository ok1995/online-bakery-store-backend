package com.app.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.pojos.Stock;

public interface StockRepository extends JpaRepository<Stock, Integer>{

}
