package com.victor.controle_gastos_port.logs.repository;

import com.victor.controle_gastos_port.logs.model.Logs;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogRepository extends JpaRepository<Logs, Long> {

    Page<Logs> findByOrderByDataHoraDesc (Pageable pageable);
}
