package com.example.QuizQuest.model;

public class Resposta {
    private Integer codigoResposta;
    private Integer codigoPergunta;
    private String textoResposta;
    private boolean flagRespostaCorreta;

    public Resposta(Integer codigoResposta, Integer codigoPergunta, String textoResposta, boolean flagRespostaCorreta) {
        this.codigoResposta = codigoResposta;
        this.codigoPergunta = codigoPergunta;
        this.textoResposta = textoResposta;
        this.flagRespostaCorreta = flagRespostaCorreta;
    }

    public Resposta() {
        this.codigoResposta = 0;
        this.codigoPergunta = 0;
        this.textoResposta = "";
        this.flagRespostaCorreta = false;
    }
    public Integer getCodigoResposta() {
        return codigoResposta;
    }

    public void setCodigoResposta(Integer codigoResposta) {
        this.codigoResposta = codigoResposta;
    }

    public Integer getCodigoPergunta() {
        return codigoPergunta;
    }

    public void setCodigoPergunta(Integer codigoPergunta) {
        this.codigoPergunta = codigoPergunta;
    }

    public String getTextoResposta() {
        return textoResposta;
    }

    public void setTextoResposta(String textoResposta) {
        this.textoResposta = textoResposta;
    }

    public Boolean getFlagRespostaCorreta() {
        return flagRespostaCorreta;
    }

    public void setFlagRespostaCorreta(Boolean flagRespostaCorreta) {
        this.flagRespostaCorreta = flagRespostaCorreta;
    }
}
