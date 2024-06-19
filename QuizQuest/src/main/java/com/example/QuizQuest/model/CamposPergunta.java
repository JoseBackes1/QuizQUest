package com.example.QuizQuest.model;

import java.util.List;

public class CamposPergunta {
    private Integer codPergunta;
    private String textoPergunta;
    private List<CamposResposta> respostaList;

    public Integer getCodPergunta() {
        return codPergunta;
    }

    public void setCodPergunta(Integer codPergunta) {
        this.codPergunta = codPergunta;
    }

    public String getTextoPergunta() {
        return textoPergunta;
    }

    public void setTextoPergunta(String textoPergunta) {
        this.textoPergunta = textoPergunta;
    }

    public List<CamposResposta> getRespostaList() {
        return respostaList;
    }

    public void setRespostaList(List<CamposResposta> respostaList) {
        this.respostaList = respostaList;
    }

}
