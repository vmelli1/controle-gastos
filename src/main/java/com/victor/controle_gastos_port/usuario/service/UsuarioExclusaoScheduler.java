package com.victor.controle_gastos_port.usuario.service;


import com.victor.controle_gastos_port.usuario.model.StatusConta;
import com.victor.controle_gastos_port.usuario.model.Usuario;
import com.victor.controle_gastos_port.usuario.repository.UsuarioRepository;
import org.aspectj.weaver.ast.Var;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class UsuarioExclusaoScheduler {
    private final UsuarioRepository usuarioRepository;


    public UsuarioExclusaoScheduler(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }



   @Scheduled(cron = "0 0 0 * * *" )
   public void rotinaDiaria(){
        deletarContasComCarenciaExpirada();
        normalizarContasReativadas();
   }

   private void deletarContasComCarenciaExpirada(){
        List<Usuario> usuarioExpirido = usuarioRepository.findByStatusContaAndCarenciaExclusaoLessThanEqual(StatusConta.PENDENTE_EXCLUSAO, LocalDate.now());
        if(!usuarioExpirido.isEmpty()){
            usuarioRepository.deleteAll(usuarioExpirido);
        }
   }

   private void normalizarContasReativadas(){
        LocalDate limiteParaAtivar  = LocalDate.now().minusDays(14);

        List<Usuario> usuariosReativados = usuarioRepository.findByStatusConta(StatusConta.CONTA_REATIVADA, limiteParaAtivar );
        for(Usuario usuario : usuariosReativados){
            usuario.setStatusConta(StatusConta.ATIVA);
            usuarioRepository.save(usuario);
        }


   }

}
