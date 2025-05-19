package br.com.desafioItau.Itau.service;


import br.com.desafioItau.Itau.dto.EstatisticaDTO;
import br.com.desafioItau.Itau.dto.NovaTransacaoDTO;
import br.com.desafioItau.Itau.model.Transacao;
import br.com.desafioItau.Itau.repository.TransacaoRepository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.OffsetDateTime;

import java.util.DoubleSummaryStatistics;
import java.util.stream.Collectors;

@Service
public class TransacaoService {

    private static final Logger log = LogManager.getLogger(TransacaoService.class);
    private final TransacaoRepository transacaoRepository;
    public TransacaoService(TransacaoRepository transacaoRepository){
        this.transacaoRepository = transacaoRepository;

    }

    public void save(NovaTransacaoDTO novaTransacaoDTO){
        Transacao transacao = new Transacao(novaTransacaoDTO);
        this.transacaoRepository.save(transacao);
    }

    public void deleteAll(){
        this.transacaoRepository.deleteAll();
    }

    public EstatisticaDTO estatistica(long intervalo){
       long start =  System.currentTimeMillis();
        OffsetDateTime now = OffsetDateTime.now();
        DoubleSummaryStatistics doubleSummaryStatistics =  this.transacaoRepository.findAll().stream()
                .filter(t-> this.filtarTransacaoPorTempo(t,now,intervalo))
                .collect(Collectors.summarizingDouble(t->t.getValor()));
        log.info("tempo para calcular estatistica {} milliseconds",System.currentTimeMillis() - start);
        return new EstatisticaDTO(doubleSummaryStatistics);
    }

   private boolean filtarTransacaoPorTempo(Transacao transacao,OffsetDateTime now,long intervalo){
        if(Duration.between(transacao.getDataHora(),now).getSeconds() <= intervalo) return true;
        return  false;
    }


}
