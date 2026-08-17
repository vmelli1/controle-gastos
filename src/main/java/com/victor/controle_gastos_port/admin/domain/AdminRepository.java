package com.victor.controle_gastos_port.admin.domain;

import com.victor.controle_gastos_port.usuario.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

interface AdminRepository extends JpaRepository<Usuario, Long> {

}
