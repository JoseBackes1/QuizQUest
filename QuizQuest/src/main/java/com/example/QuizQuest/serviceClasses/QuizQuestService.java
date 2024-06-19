package com.example.QuizQuest.serviceClasses;
import com.example.QuizQuest.model.CamposLoginUsuario;
import com.example.QuizQuest.model.CamposRanqueados;

import com.example.QuizQuest.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class QuizQuestService {

    @Autowired
    DatabaseService databaseService;

    public Usuario efetuarLogin(Login login) {
        // Adicione uma mensagem de log para depuração
        System.out.println("Efetuando login para: " + login.getEmail());
        Usuario usuario = databaseService.getUsuarioPorEmailSenha(login.getEmail(), login.getSenha());
        if (usuario != null && usuario.getSenha().equals(login.getSenha())) {
            return usuario;
        } else {
            return null;
        }
    }

    public boolean createUsuario(CamposLoginUsuario camposUsuario) {
        return databaseService.createUsuario(camposUsuario);
    }

    public CamposQuizUsuario obterQuizzesDoUsuario(Integer userId) {
        CamposQuizUsuario camposQuizUsuario = new CamposQuizUsuario();
        Usuario usuario = databaseService.findUserById(userId);
        if(usuario != null) {

            camposQuizUsuario.setCodUsuario(usuario.getCodUsuario());
            camposQuizUsuario.setNomeUsuario(usuario.getNome());

            List<Quiz> quizList = databaseService.obterQuizPorUsuario(usuario.getCodUsuario());
            if( quizList != null || !quizList.isEmpty()){
                camposQuizUsuario.setQuizList(obterQuizList(quizList));
            }
        }
        return camposQuizUsuario;
    }

    private List<CamposQuiz> obterQuizList(List<Quiz> quizList) {
        List<CamposQuiz> camposQuizList = new ArrayList<>();

        for(Quiz quiz : quizList){
            CamposQuiz camposQuiz = new CamposQuiz();
            camposQuiz.setCodQuiz(quiz.getCodigoQuiz());
            camposQuiz.setNomeQuiz(quiz.getNomeQuiz());
            camposQuiz.setPerguntasList(obterPerguntas(quiz.getCodigoQuiz()));

            camposQuizList.add(camposQuiz);
        }
        return camposQuizList;
    }

    private List<CamposPergunta> obterPerguntas(int codigoQuiz) {
        List<Pergunta> perguntaList = databaseService.obterPerguntasPorQuiz(codigoQuiz);
        if(perguntaList == null || perguntaList.isEmpty()) {
            return new ArrayList<>();
        }
        List<CamposPergunta> camposPerguntaList = new ArrayList<>();
        for(Pergunta pergunta : perguntaList) {
            CamposPergunta camposPergunta = new CamposPergunta();
            camposPergunta.setCodPergunta(pergunta.getCodigoPergunta());
            camposPergunta.setTextoPergunta(pergunta.getTextoPergunta());
            camposPergunta.setRespostaList(obterRespostaList(pergunta.getCodigoPergunta()));

            camposPerguntaList.add(camposPergunta);
        }

        return camposPerguntaList;
    }

    private List<CamposResposta> obterRespostaList(Integer codigoPergunta) {
        List<Resposta> respostaList = databaseService.obterRespostasPorPergunta(codigoPergunta);
        if(respostaList == null || respostaList.isEmpty()){
            return new ArrayList<>();
        }
        List<CamposResposta> camposRespostaList = new ArrayList<>();
        for(Resposta resposta : respostaList){
            CamposResposta camposResposta = new CamposResposta();
            camposResposta.setCodResposta(resposta.getCodigoResposta());
            camposResposta.setTextoResposta(resposta.getTextoResposta());
            camposResposta.setFlRespCorreta(resposta.getFlagRespostaCorreta());
            camposRespostaList.add(camposResposta);
        }
        return camposRespostaList;
    }
    @Transactional
    public boolean saveQuiz(CamposQuiz camposQuiz) {
        Quiz quiz = createQuiz(camposQuiz);
        if (quiz == null) {
            return false;
        }

        if (!savePerguntas(quiz, camposQuiz)) {
            return false;
        }

        return true;
    }

    private Quiz createQuiz(CamposQuiz camposQuiz) {
        Quiz quiz = new Quiz();
        quiz.setCodUsuario(camposQuiz.getCodUsuario());
        quiz.setNomeQuiz(camposQuiz.getNomeQuiz());

        Quiz quizCriado = databaseService.createQuiz(quiz);
        return quizCriado;
    }

    private boolean savePerguntas(Quiz quiz, CamposQuiz camposQuiz) {
        for (CamposPergunta camposPergunta : camposQuiz.getPerguntasList()) {
            Pergunta pergunta = createPergunta(quiz, camposPergunta);
            if (pergunta == null) {
                return false;
            }

            if (!saveRespostas(pergunta, camposPergunta)) {
                return false;
            }
        }
        return true;
    }

    private Pergunta createPergunta(Quiz quiz, CamposPergunta camposPergunta) {
        Pergunta pergunta = new Pergunta();
        pergunta.setCodigoQuiz(quiz.getCodigoQuiz());
        pergunta.setTextoPergunta(camposPergunta.getTextoPergunta());

        Pergunta perguntaCriada = databaseService.createPergunta(pergunta);
        return perguntaCriada;
    }

    private boolean saveRespostas(Pergunta pergunta, CamposPergunta camposPergunta) {
        for (int i = 0; i < camposPergunta.getRespostaList().size(); i++) {
            Resposta resposta = createResposta(pergunta, camposPergunta.getRespostaList().get(i));
            if (resposta == null) {
                return false;
            }
        }
        return true;
    }

    private Resposta createResposta(Pergunta pergunta, CamposResposta camposResposta) {
        Resposta resposta = new Resposta();
        resposta.setCodigoPergunta(pergunta.getCodigoPergunta());
        resposta.setTextoResposta(camposResposta.getTextoResposta());
        resposta.setFlagRespostaCorreta(camposResposta.getFlRespCorreta());

        Resposta repostaCriada = databaseService.createResposta(resposta);
        return repostaCriada;
    }

    public List<CamposQuiz> obterQuiz(Integer codQuiz) {
        Quiz quiz = databaseService.getQuiz(codQuiz);
        List<Quiz> quizList = new ArrayList<>();
        quizList.add(quiz);
        List<CamposQuiz> camposQuizList = obterQuizList(quizList);
        retirarFlagsRespostaCorreta(camposQuizList);
        return camposQuizList;
    }

    private void retirarFlagsRespostaCorreta(List<CamposQuiz> camposQuizList) {
        for(CamposPergunta pergunta : camposQuizList.get(0).getPerguntasList()){
            for(CamposResposta resposta : pergunta.getRespostaList()){
                resposta.setFlRespCorreta(null);
            }
        }
    }

    public CamposResultado saveRankingRetornaCamposResultado(CamposQuizRespondido camposQuizRespondido) {
        int ultimoNum = databaseService.getMaxId("codrespondedor", "ranking");
        int pontuacao = calcularPontuacaoEAtualizarRanking(camposQuizRespondido);
        Ranking ranking = new Ranking();
        ranking.setCodRespondedor(ultimoNum + 1);
        ranking.setCodigoQuiz(camposQuizRespondido.getCodQuiz());
        ranking.setNomeCompleto(camposQuizRespondido.getNomeUsuario());
        ranking.setPontuacao(pontuacao);
        ranking.setPerguntasErradas(obterRespostasErradas(camposQuizRespondido));
        boolean criouRanking = databaseService.createRanking(ranking);
        if (!criouRanking) {
            throw new RuntimeException("Erro ao criar o ranking no banco de dados");
        }

        CamposResultado camposResultado = new CamposResultado();
        camposResultado.setNumRespostas(camposQuizRespondido.getRespostaList().size());
        camposResultado.setNumRespostasCorretas(pontuacao);

        return camposResultado;
    }

    private String obterRespostasErradas(CamposQuizRespondido camposQuizRespondido) {
        StringBuilder perguntasErradas = new StringBuilder();

        for (CamposRespondido resposta : camposQuizRespondido.getRespostaList()) {
            Resposta respostaCorreta = databaseService.findRespostaCorreta(resposta.getCodPergunta());

            if (!resposta.getCodResposta().equals(respostaCorreta.getCodigoResposta())) {
                if (perguntasErradas.length() > 0) {
                    perguntasErradas.append(";");
                }
                perguntasErradas.append(resposta.getCodPergunta());
            }
        }
        return perguntasErradas.toString();
    }

    private int calcularPontuacaoEAtualizarRanking(CamposQuizRespondido camposQuizRespondido) {
        int pontuacao = 0;
        for (CamposRespondido resposta : camposQuizRespondido.getRespostaList()) {
            Resposta respostaCorreta = databaseService.findRespostaCorreta(resposta.getCodPergunta());

            if (resposta.getCodResposta().equals(respostaCorreta.getCodigoResposta())) {
                pontuacao++;
            }
        }
        return pontuacao;
    }

    public CamposQuizRanking obterRanking(Integer codQuiz) {
        Quiz quiz = databaseService.getQuiz(codQuiz);
        CamposQuizRanking camposQuizRanking = new CamposQuizRanking();
        camposQuizRanking.setCodQuiz(quiz.getCodigoQuiz());
        camposQuizRanking.setNomeQuiz(quiz.getNomeQuiz());
        camposQuizRanking.setRanqueadosList(obterRanqueados(quiz.getCodigoQuiz()));
        return camposQuizRanking;
    }

    private List<CamposRanqueados> obterRanqueados(Integer codigoQuiz) {
        List<Pergunta> perguntaList = databaseService.obterPerguntasPorQuiz(codigoQuiz);
        perguntaList.sort(Comparator.comparing(Pergunta::getCodigoPergunta));
        List<Ranking> rankingList = databaseService.getRankingByCodigoQuiz(codigoQuiz);
        return setRanqueados(perguntaList, rankingList, codigoQuiz);
    }

    private List<CamposRanqueados> setRanqueados(List<Pergunta> perguntaList, List<Ranking> rankingList, Integer codigoQuiz) {
        List<CamposRanqueados> camposRanqueadosList = new ArrayList<>();

        for(Ranking ranking : rankingList){
            CamposRanqueados campos = new CamposRanqueados();
            campos.setNome(ranking.getNomeCompleto());
            campos.setPerguntasQueAcertouList(obterPerguntasErradasECertas(perguntaList, ranking.getPerguntasErradas()));
            campos.setPontuacao(ranking.getPontuacao());
            camposRanqueadosList.add(campos);
        }
        camposRanqueadosList.sort(Comparator.comparing(CamposRanqueados::getPontuacao));
        return camposRanqueadosList;
    }

    private List<Boolean> obterPerguntasErradasECertas(List<Pergunta> perguntaList, String perguntasErradas) {
        List<Boolean> booleanList = new ArrayList<>();

        List<Integer> perguntasErradasList = Arrays.stream(perguntasErradas.split(";"))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        Collections.sort(perguntasErradasList);

        for (Pergunta pergunta : perguntaList) {
            if (perguntasErradasList.contains(pergunta.getCodigoPergunta())) {
                booleanList.add(false);
            } else {
                booleanList.add(true);
            }
        }

        return booleanList;
    }

    public void deleteQuiz(Integer codQuiz) {
        Quiz quiz = databaseService.getQuiz(codQuiz);
        databaseService.deleteRankingByCodQuiz(quiz.getCodigoQuiz());
        List<Pergunta> perguntaList = databaseService.obterPerguntasPorQuiz(quiz.getCodigoQuiz());
        for(Pergunta pergunta : perguntaList){
            List<Resposta> respostaList = databaseService.obterRespostasPorPergunta(pergunta.getCodigoPergunta());

            for(Resposta resposta : respostaList){
                databaseService.deleteResposta(resposta.getCodigoResposta());
            }
            databaseService.deletePergunta(pergunta.getCodigoPergunta());
        }

        databaseService.deleteQuiz(quiz.getCodigoQuiz());
    }
}



