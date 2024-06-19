package com.example.QuizQuest.model;

public class CamposResposta {
    private Integer codResposta;
    private String textoResposta;
    private Boolean flRespCorreta;

    public int getCodResposta() {
        return codResposta;
    }

    public void setCodResposta(Integer codResposta) {
        this.codResposta = codResposta;
    }

    public String getTextoResposta() {
        return textoResposta;
    }

    public void setTextoResposta(String textoResposta) {
        this.textoResposta = textoResposta;
    }

    public Boolean getFlRespCorreta() {
        return flRespCorreta;
    }

    public void setFlRespCorreta(Boolean flRespCorreta) {
        this.flRespCorreta = flRespCorreta;
    }

}
