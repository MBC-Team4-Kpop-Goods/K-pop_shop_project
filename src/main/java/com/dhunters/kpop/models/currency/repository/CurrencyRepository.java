package com.dhunters.kpop.models.currency.repository;

import com.dhunters.kpop.common.entity.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRepository extends JpaRepository<Currency, Long> {
}
