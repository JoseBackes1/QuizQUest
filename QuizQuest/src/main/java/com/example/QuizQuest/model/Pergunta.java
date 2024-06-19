package com.example.QuizQuest.model;

public class Pergunta {
    private Integer codigoPergunta;
    private Integer codigoQuiz;
    private String textoPergunta;

    public Pergunta(Integer codigoPergunta, Integer codigoQuiz, String textoPergunta) {
        this.codigoPergunta = codigoPergunta;
        this.codigoQuiz = codigoQuiz;
        this.textoPergunta = textoPergunta;
    }

    public Pergunta() {
        this.codigoPergunta = 0;
        this.codigoQuiz = 0;
        this.textoPergunta = "";
    }

    public Integer getCodigoPergunta() {
        return codigoPergunta;
    }

    public void setCodigoPergunta(Integer codigoPergunta) {
        this.codigoPergunta = codigoPergunta;
    }

    public Integer getCodigoQuiz() {
        return codigoQuiz;
    }

    public void setCodigoQuiz(Integer codigoQuiz) {
        this.codigoQuiz = codigoQuiz;
    }

    public String getTextoPergunta() {
        return textoPergunta;
    }

    public void setTextoPergunta(String textoPergunta) {
        this.textoPergunta = textoPergunta;
    }
}
