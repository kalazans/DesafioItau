package br.com.desafioItau.Itau.controler;

import br.com.desafioItau.Itau.dto.EstatisticaDTO;
import br.com.desafioItau.Itau.dto.NovaTransacaoDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.time.OffsetDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class TransacaoControllerTest {
    @Autowired
    private MockMvc mock;
    @Autowired
    private JacksonTester<NovaTransacaoDTO> novaTransacaoDTOJacksonTester;
    @Autowired
    private JacksonTester<EstatisticaDTO> estatisticaDTOJacksonTester;

    @Test
    void create() {
    }

    @Test
    void deleteAll() {
    }

    @Test
    void estatistica() {
    }
    @Test
    @DisplayName("deve retornar 400 quando a requisaçao post vier sem corpo")
    void testar_post_semJson() throws Exception {
        MockHttpServletResponse response = mock.perform(post("/transacao/post")).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }
    @Test
    @DisplayName("deve retornar 404 quando vier uma Url que nao existe ou nao mapeada")
    void testar_url_naoExiste_ou_metodoHttpErrado() throws Exception {
        MockHttpServletResponse response = mock.perform(post("/transacao/post/teste")).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @DisplayName("deve retonar 422 quando nao passar nas validaçoes do Validations")
    void testar_error_nas_validaçoes_do_validations() throws Exception {
        MockHttpServletResponse response = mock.perform(
                post("/transacao/post").contentType(MediaType.APPLICATION_JSON)
                        .content(novaTransacaoDTOJacksonTester
                                .write(new NovaTransacaoDTO(null,OffsetDateTime.parse("2029-08-07T12:34:56.789-03:00")))
                                .getJson()
                        )
                )
                .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY.value());
    }

    @Test
    @DisplayName("deve retornar 400 caso o json seja invalido -> json com mais chaves ou que nao passe no Validations")
    void testar_json_invalido_porChaves_ou_naoPasseNoValidations() throws Exception {
        String json = """
                {
                "valor":"aaa",
                "dataHora":"08-2029-07T12:34:56.789-03:00",
                "oi":"teste
                }""";
        MockHttpServletResponse response = mock.perform(
                        post("/transacao/post").contentType(MediaType.APPLICATION_JSON)
                                .content(json.trim())
                ).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());

    }

    @Test
    @DisplayName("deve retorn 201 quando for recebido um post e a entidade for criada no banco de dados")
    void testar_create_entity() throws Exception {
        MockHttpServletResponse response = mock.perform(
                        post("/transacao/post").contentType(MediaType.APPLICATION_JSON)
                                .content(novaTransacaoDTOJacksonTester
                                        .write(new NovaTransacaoDTO(120.50F,OffsetDateTime.parse("2023-08-07T12:34:56.789-03:00")))
                                        .getJson()
                                )
                )
                .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    @DisplayName("dever retornar 200 e o estatisticaDTO no corpo")
    void testar_get_conversao_Transacao_para_EstatisticaDTO() throws Exception {
        MockHttpServletResponse response = mock.perform(
                get("/transacao/estatistica")
                        .contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse();
        String jsonGerado = estatisticaDTOJacksonTester.write(new EstatisticaDTO(0,0,0.0,0.0,0)).getJson();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonGerado);
    }




}