package com.example.QuizQuest.model;

import java.util.List;

public class CamposQuiz {
    private Integer codQuiz;
    private Integer codUsuario;
    private String nomeQuiz;
    private List<CamposPergunta> perguntasList;

    public Integer getCodQuiz() {
        return codQuiz;
    }

    public void setCodQuiz(Integer codQuiz) {
        this.codQuiz = codQuiz;
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

    public List<CamposPergunta> getPerguntasList() {
        return perguntasList;
    }

    public void setPerguntasList(List<CamposPergunta> perguntasList) {
        this.perguntasList = perguntasList;
    }
}
