package com.example.QuizQuest.serviceClasses;

import com.example.QuizQuest.DatabaseConfig;
import com.example.QuizQuest.model.CamposLoginUsuario;
import com.example.QuizQuest.model.Resposta;
import com.example.QuizQuest.model.*;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class DatabaseService {

    public Connection connect() throws SQLException {
        return DriverManager.getConnection(DatabaseConfig.URL, DatabaseConfig.USER, DatabaseConfig.PASSWORD);
    }

    public void getData() {
        String query = "SELECT * FROM sua_tabela";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                System.out.println(rs.getString("coluna"));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Usuario getUsuarioPorEmailSenha(String email, String senha) {
        String query = "SELECT * FROM usuario WHERE email = ? AND senha = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, email);
            pstmt.setString(2, senha);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Integer codUsuario = rs.getInt("codusuario");
                String nome = rs.getString("nome");
                String userEmail = rs.getString("email");
                String userSenha = rs.getString("senha");

                return new Usuario(codUsuario, nome, userEmail, userSenha);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    public boolean createUsuario(CamposLoginUsuario camposUsuario) {
        String query = "INSERT INTO usuario (nome, email, senha) VALUES (?, ?, ?)";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, camposUsuario.getNome());
            pstmt.setString(2, camposUsuario.getEmail());
            pstmt.setString(3, camposUsuario.getSenha());

            int affectedRows = pstmt.executeUpdate();
            System.out.println("Affected Rows: " + affectedRows);
            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Quiz> obterQuizPorUsuario(Integer codUsuario) {
        List<Quiz> quizzes = new ArrayList<>();
        String query = "SELECT * FROM quiz WHERE codusuario = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, codUsuario);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Quiz quiz = new Quiz();
                    quiz.setCodigoQuiz(rs.getInt("codigoQuiz"));
                    quiz.setCodUsuario(rs.getInt("codUsuario"));
                    quiz.setNomeQuiz(rs.getString("nomeQuiz"));
                    quizzes.add(quiz);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return quizzes;
    }

    public List<Pergunta> obterPerguntasPorQuiz(int codigoQuiz) {
        List<Pergunta> perguntas = new ArrayList<>();
        String query = "SELECT * FROM pergunta WHERE codigoquiz = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, codigoQuiz);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Pergunta pergunta = new Pergunta(
                            rs.getInt("codigoPergunta"),
                            rs.getInt("codigoQuiz"),
                            rs.getString("textoPergunta")
                    );
                    perguntas.add(pergunta);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return perguntas;
    }

    public List<Resposta> obterRespostasPorPergunta(Integer codigoPergunta) {
        List<Resposta> respostas = new ArrayList<>();
        String query = "SELECT * FROM resposta WHERE codigopergunta = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, codigoPergunta);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Resposta resposta = new Resposta(
                            rs.getInt("codigoResposta"),
                            rs.getInt("codigoPergunta"),
                            rs.getString("textoResposta"),
                            rs.getBoolean("flagRespostaCorreta")
                    );
                    respostas.add(resposta);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return respostas;
    }

    public Usuario findUserById(Integer userId) {
        String query = "SELECT * FROM usuario WHERE codusuario = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, userId); // Corrigido para usar setInt em vez de setString
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Integer codUsuario = rs.getInt("codusuario");
                String nome = rs.getString("nome");
                String userEmail = rs.getString("email");
                String userSenha = rs.getString("senha");

                return new Usuario(codUsuario, nome, userEmail, userSenha);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    public Quiz createQuiz(Quiz quiz) {
        String query = "INSERT INTO quiz (codusuario, nomequiz) VALUES (?, ?)";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, quiz.getCodUsuario());
            pstmt.setString(2, quiz.getNomeQuiz());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    quiz.setCodigoQuiz(rs.getInt(1));
                    return quiz; // Retorna o objeto Quiz com o código gerado
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Retorna null se a inserção falhar
    }

    public Pergunta createPergunta(Pergunta novaPergunta) {
        String query = "INSERT INTO pergunta (codigoquiz, textopergunta) VALUES (?, ?)";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, novaPergunta.getCodigoQuiz());
            pstmt.setString(2, novaPergunta.getTextoPergunta());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    novaPergunta.setCodigoPergunta(rs.getInt(1));
                    return novaPergunta; // Retorna o objeto Pergunta com o código gerado
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Retorna null se a inserção falhar
    }

    public Resposta createResposta(Resposta resposta) {
        String query = "INSERT INTO resposta (codigopergunta, textoresposta, flagrespostacorreta) VALUES (?, ?, ?)";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, resposta.getCodigoPergunta());
            pstmt.setString(2, resposta.getTextoResposta());
            pstmt.setBoolean(3, resposta.getFlagRespostaCorreta());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    resposta.setCodigoResposta(rs.getInt(1));
                    return resposta; // Retorna o objeto Resposta com o código gerado
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Retorna null se a inserção falhar
    }

    public boolean createRanking(Ranking ranking) {
        String query = "INSERT INTO ranking (codrespondedor, codigoquiz, nomecompleto, pontuacao, perguntaserradas) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, ranking.getCodRespondedor());
            pstmt.setInt(2, ranking.getCodigoQuiz());
            pstmt.setString(3, ranking.getNomeCompleto());
            pstmt.setInt(4, ranking.getPontuacao());
            pstmt.setString(5, ranking.getPerguntasErradas());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0; // Retorna true se pelo menos uma linha foi inserida
        } catch (SQLException e) {
            System.err.println("Erro ao criar o ranking: " + e.getMessage());
            return false;
        }
    }

    public int getMaxId(String nomeCampo, String tableName) {
        String query = "SELECT MAX(" + nomeCampo +") AS max_id FROM " + tableName;
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("max_id");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0; // Retornar -1 se houver um erro ou se a tabela estiver vazia
    }
    public Quiz getQuiz(Integer codQuiz) {
        String query = "SELECT * FROM quiz WHERE codigoquiz = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, codQuiz);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Integer codUsuario = rs.getInt("codusuario");
                Integer codigoquiz = rs.getInt("codigoquiz");
                String nomequiz = rs.getString("nomequiz");


                return new Quiz(codigoquiz, codUsuario, nomequiz);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    public Resposta findRespostaCorreta(Integer codPergunta) {
        String query = "SELECT * FROM resposta WHERE codigopergunta = ? AND flagrespostacorreta = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, codPergunta);
            pstmt.setBoolean(2, true);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Integer codigoresposta = rs.getInt("codigoresposta");
                    Integer codigoPergunta = rs.getInt("codigopergunta");
                    String textoresposta = rs.getString("textoresposta");
                    boolean flagrespostacorreta = rs.getBoolean("flagrespostacorreta");

                    return new Resposta(codigoresposta, codigoPergunta, textoresposta, flagrespostacorreta);
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar a resposta correta: " + e.getMessage());
        }

        return null;
    }

    public List<Ranking> getRankingByCodigoQuiz(Integer codigoQuiz) {
        List<Ranking> rankings = new ArrayList<>();
        String query = "SELECT * FROM ranking WHERE codigoquiz = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, codigoQuiz);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Ranking ranking = new Ranking(
                            rs.getInt("codrespondedor"),
                            rs.getInt("codigoquiz"),
                            rs.getString("nomecompleto"),
                            rs.getInt("pontuacao"),
                            rs.getString("perguntaserradas")
                    );
                    rankings.add(ranking);
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar o ranking: " + e.getMessage());
        }
        return rankings;
    }

    public void deleteResposta(Integer codigoResposta) {
        String query = "DELETE FROM resposta WHERE codigoresposta = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, codigoResposta);
            int rowsAffected = pstmt.executeUpdate();
            System.out.println("Resposta deletada. Linhas afetadas: " + rowsAffected);

        } catch (SQLException e) {
            System.err.println("Erro ao deletar resposta: " + e.getMessage());
        }
    }

    public void deletePergunta(Integer codigoPergunta) {
        String query = "DELETE FROM pergunta WHERE codigopergunta = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, codigoPergunta);
            int rowsAffected = pstmt.executeUpdate();
            System.out.println("Pergunta deletada. Linhas afetadas: " + rowsAffected);

        } catch (SQLException e) {
            System.err.println("Erro ao deletar pergunta: " + e.getMessage());
        }
    }

    public void deleteQuiz(Integer codigoQuiz) {
        String query = "DELETE FROM quiz WHERE codigoquiz = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, codigoQuiz);
            int rowsAffected = pstmt.executeUpdate();
            System.out.println("Quiz deletado. Linhas afetadas: " + rowsAffected);

        } catch (SQLException e) {
            System.err.println("Erro ao deletar quiz: " + e.getMessage());
        }
    }

    public void deleteRankingByCodQuiz(Integer codigoQuiz) {
        String query = "DELETE FROM ranking WHERE codigoquiz = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, codigoQuiz);
            int rowsAffected = pstmt.executeUpdate();
            System.out.println("Ranking deletado. Linhas afetadas: " + rowsAffected);

        } catch (SQLException e) {
            System.err.println("Erro ao deletar ranking: " + e.getMessage());
        }
    }
}