package com.example.QuizQuest.model;

public class Quiz {
    private Integer codigoQuiz;
    private Integer codUsuario;
    private String nomeQuiz;

    public Quiz(Integer codigoQuiz, Integer codUsuario, String nomeQuiz) {
        this.codigoQuiz = codigoQuiz;
        this.codUsuario = codUsuario;
        this.nomeQuiz = nomeQuiz;
    }

    public Quiz() {
        this.codigoQuiz = 0;
        this.codUsuario = 0;
        this.nomeQuiz = "";
    }


    public Integer getCodigoQuiz() {
        return codigoQuiz;
    }

    public void setCodigoQuiz(Integer codigoQuiz) {
        this.codigoQuiz = codigoQuiz;
    }

    public Integer getCodUsuario() {
        return codUsuario;
    }

    public void setCodUsuario(Integer codUsuario) {
        this.codUsuario = codUsuario;
    }

    public String getNomeQuiz() {
        return nomeQuiz;
    }

    public void setNomeQuiz(String nomeQuiz) {
        this.nomeQuiz = nomeQuiz;
    }
}
