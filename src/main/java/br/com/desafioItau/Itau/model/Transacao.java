package br.com.desafioItau.Itau.model;

import br.com.desafioItau.Itau.dto.NovaTransacaoDTO;
import jakarta.persistence.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.OffsetDateTime;
import java.util.Objects;
@Entity
public class Transacao {

    private static final Logger log = LogManager.getLogger(Transacao.class);
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private  Float valor;
    private OffsetDateTime dataHora;

    public Transacao(){}
    public Transacao(NovaTransacaoDTO novaTransacaoDTO){
        this.dataHora = novaTransacaoDTO.dataHora();
        this.valor  = novaTransacaoDTO.valor();
    }

    public Long getId() {
        return id;
    }

    public Float getValor() {
        return valor;
    }

    public OffsetDateTime getDataHora() {
        return dataHora;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transacao transacao = (Transacao) o;
        return Objects.equals(id, transacao.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Transacao{" +
                "id=" + id +
                ", valor=" + valor +
                ", dataHora=" + dataHora +
                '}';
    }

    @PrePersist
    public void logNewTransacaoAttempt(){
        log.info("tentando criar a entidade Transacao no banco de dados");
    }
    @PostPersist
    public void logNewTransacaoAdded(){
        log.info("Entidade Transacao criada no banco e tem o id: "+this.id);
    }
    @PreRemove
    public void logTransacaoRemovalAttempt(){
        log.info("tentando remover a Transacao do banco que tem o id: "+this.id);
    }
    @PostRemove
    public void logTransacaoRemoved(){
        log.info("Removida a transacao de id: "+this.id);
    }
    @PostLoad
    public void logTransacaoLoaded(){
        log.info("Transacao carregada: "+this.toString());
    }
}
