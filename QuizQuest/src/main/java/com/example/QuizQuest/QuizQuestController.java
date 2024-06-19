package com.example.QuizQuest;

import com.example.QuizQuest.model.*;
import com.example.QuizQuest.serviceClasses.QuizQuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quizQuestController")
@CrossOrigin(origins = "*") // Permitir qualquer origem
public class QuizQuestController {

    @Autowired
    QuizQuestService quizQuestService;

    @PostMapping("/login")
    public Usuario loginUsuario(@RequestBody Login login){
        return quizQuestService.efetuarLogin(login);
    }

    @PostMapping("/createUsuario")
    public boolean createUsuario(@RequestBody CamposLoginUsuario camposUsuario){
        return quizQuestService.createUsuario(camposUsuario);
    }

    @PostMapping("/obterQuizzesUsuario")
    public CamposQuizUsuario obterQuizzesUsuario(@RequestBody Usuario usuario){
        return quizQuestService.obterQuizzesDoUsuario(usuario.getCodUsuario());
    }

    @PostMapping("/obterQuiz")
    public List<CamposQuiz> obterQuiz(@RequestBody CamposQuiz quiz){
        return quizQuestService.obterQuiz(quiz.getCodQuiz());
    }

    @PostMapping("/saveQuiz")
    public boolean saveQuiz(@RequestBody CamposQuiz camposQuiz){
        return quizQuestService.saveQuiz(camposQuiz);
    }

    @PostMapping("/deleteQuiz")
    public void deleteQuiz(@RequestBody Integer codQuiz){
        quizQuestService.deleteQuiz(codQuiz);
    }

    @PostMapping("/enviarRespostas")
    public CamposResultado enviarRespostas(@RequestBody CamposQuizRespondido camposQuizRespondido){
        return quizQuestService.saveRankingRetornaCamposResultado(camposQuizRespondido);
    }

    @PostMapping("/obterRanking")
    public CamposQuizRanking obterRanking(@RequestBody Integer codQuiz){
        return quizQuestService.obterRanking(codQuiz);
    }
}
