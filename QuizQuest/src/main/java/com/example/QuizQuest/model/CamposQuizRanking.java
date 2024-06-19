package com.example.QuizQuest.model;

import java.util.List;

public class CamposQuizRanking {
    private Integer codQuiz;
    private String nomeQuiz;
    private List<CamposRanqueados> ranqueadosList;

    public Integer getCodQuiz() {
        return codQuiz;
    }

    public void setCodQuiz(Integer codQuiz) {
        this.codQuiz = codQuiz;
    }

    public String getNomeQuiz() {
        return nomeQuiz;
    }

    public void setNomeQuiz(String nomeQuiz) {
        this.nomeQuiz = nomeQuiz;
    }

    public List<CamposRanqueados> getRanqueadosList() {
        return ranqueadosList;
    }

    public void setRanqueadosList(List<CamposRanqueados> ranqueadosList) {
        this.ranqueadosList = ranqueadosList;
    }
}
