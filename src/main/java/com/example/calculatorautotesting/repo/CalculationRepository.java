package com.example.calculatorautotesting.repo;

import com.example.calculatorautotesting.model.Calculation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CalculationRepository extends JpaRepository<Calculation,Long> {

    List<Calculation> findByDateBetween(String str1,String str2);
    List<Calculation> findByTimeBetween(String str1,String str2);

}
