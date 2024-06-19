package com.example.QuizQuest.model;

public class Ranking {
    private Integer codRespondedor;
    private Integer codigoQuiz;
    private String nomeCompleto;
    private Integer pontuacao;
    private String perguntasErradas;

    public Ranking(Integer codRespondedor, Integer codigoQuiz, String nomeCompleto, Integer pontuacao, String perguntasErradas) {
        this.codRespondedor = codRespondedor;
        this.codigoQuiz = codigoQuiz;
        this.nomeCompleto = nomeCompleto;
        this.pontuacao = pontuacao;
        this.perguntasErradas = perguntasErradas;
    }
    public Ranking() {
        this.codRespondedor = 0;
        this.codigoQuiz = 0;
        this.nomeCompleto = "";
        this.pontuacao = 0;
        this.perguntasErradas = "";
    }

    public Integer getCodRespondedor() {
        return codRespondedor;
    }

    public void setCodRespondedor(Integer codRespondedor) {
        this.codRespondedor = codRespondedor;
    }

    public Integer getCodigoQuiz() {
        return codigoQuiz;
    }

    public void setCodigoQuiz(Integer codigoQuiz) {
        this.codigoQuiz = codigoQuiz;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public Integer getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(Integer pontuacao) {
        this.pontuacao = pontuacao;
    }

    public String getPerguntasErradas() {
        return perguntasErradas;
    }

    public void setPerguntasErradas(String perguntasErradas) {
        this.perguntasErradas = perguntasErradas;
    }
}
