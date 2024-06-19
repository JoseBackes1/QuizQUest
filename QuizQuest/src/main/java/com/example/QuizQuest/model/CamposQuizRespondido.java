package com.example.QuizQuest.model;

import java.util.List;

public class CamposQuizRespondido {
    private Integer codQuiz;
    private String nomeUsuario;
    private List<CamposRespondido> respostaList;

    public Integer getCodQuiz() {
        return codQuiz;
    }

    public void setCodQuiz(Integer codQuiz) {
        this.codQuiz = codQuiz;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public List<CamposRespondido> getRespostaList() {
        return respostaList;
    }

    public void setRespostaList(List<CamposRespondido> respostaList) {
        this.respostaList = respostaList;
    }
}
