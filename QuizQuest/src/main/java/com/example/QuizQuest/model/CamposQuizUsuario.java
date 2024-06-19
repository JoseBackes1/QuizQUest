package com.example.QuizQuest.model;

import java.util.List;

public class CamposQuizUsuario {
    private Integer codUsuario;
    private String nomeUsuario;
    private List<CamposQuiz> quizList;

    public Integer getCodUsuario() {
        return codUsuario;
    }

    public void setCodUsuario(Integer codUsuario) {
        this.codUsuario = codUsuario;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public List<CamposQuiz> getQuizList() {
        return quizList;
    }

    public void setQuizList(List<CamposQuiz> quizList) {
        this.quizList = quizList;
    }

}
