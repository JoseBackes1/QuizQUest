package com.example.QuizQuest.model;

import java.util.List;

public class CamposRanqueados {
    private String nome;
    private List<Boolean> perguntasQueAcertouList;
    private Integer pontuacao;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Boolean> getPerguntasQueAcertouList() {
        return perguntasQueAcertouList;
    }

    public void setPerguntasQueAcertouList(List<Boolean> perguntasQueAcertouList) {
        this.perguntasQueAcertouList = perguntasQueAcertouList;
    }

    public Integer getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(Integer pontuacao) {
        this.pontuacao = pontuacao;
    }
}
