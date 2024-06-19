package com.example.QuizQuest.model;

public class CamposRespondido {
    private Integer codPergunta;
    private Integer codResposta;

    public CamposRespondido(Integer codPergunta, Integer codResposta) {
        this.codPergunta = codPergunta;
        this.codResposta = codResposta;
    }

    public Integer getCodPergunta() {
        return codPergunta;
    }

    public void setCodPergunta(Integer codPergunta) {
        this.codPergunta = codPergunta;
    }

    public Integer getCodResposta() {
        return codResposta;
    }

    public void setCodResposta(Integer codResposta) {
        this.codResposta = codResposta;
    }
}
