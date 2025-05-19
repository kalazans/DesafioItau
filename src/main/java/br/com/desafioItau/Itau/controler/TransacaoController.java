package br.com.desafioItau.Itau.controler;

import br.com.desafioItau.Itau.dto.EstatisticaDTO;
import br.com.desafioItau.Itau.dto.NovaTransacaoDTO;
import br.com.desafioItau.Itau.infra.exceptions.CustomErrorApi;
import br.com.desafioItau.Itau.service.TransacaoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/transacao")
public class TransacaoController {
    private final  TransacaoService transacaoService;
    public TransacaoController(TransacaoService transacaoService){
        this.transacaoService = transacaoService;
    }

    @PostMapping(value = "/post")
    public ResponseEntity<Object> create(@RequestBody(required = false) @Valid Optional<NovaTransacaoDTO> novaTransacaoDTO) throws JsonProcessingException {
        if(novaTransacaoDTO.isEmpty()) return ResponseEntity
                .badRequest()
                .body(new CustomErrorApi(HttpStatus.BAD_REQUEST,"requisiçaõ sem corpo",new ArrayList<>()));
        this.transacaoService.save(novaTransacaoDTO.get());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Object> deleteAll(){
        this.transacaoService.deleteAll();
        return ResponseEntity.ok().build();
    }

    @GetMapping(value= {"/estatistica/{intervalo}","/estatistica"})
    public ResponseEntity<EstatisticaDTO> estatistica(@PathVariable(value = "intervalo",required = false) Optional<Long> intervalo){
        if(intervalo.isEmpty()) return ResponseEntity.ok(this.transacaoService.estatistica(60));
      return  ResponseEntity.ok(this.transacaoService.estatistica(intervalo.get()));
    }



}
