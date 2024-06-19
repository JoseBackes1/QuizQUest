document.addEventListener('DOMContentLoaded', function () {
    // Verificar se o usuário está logado
    const user = JSON.parse(localStorage.getItem('user'));
    if (!user) {
        window.location.href =  "login.html";;
        return;
    }

    const quizList = document.getElementById('quiz-list');
    const quizModal = document.getElementById('quiz-modal');
    const quizDetails = document.getElementById('quiz-details');
    const confirmDeleteModal = document.getElementById('confirm-delete-modal');
    const closeModal = document.getElementsByClassName('close')[0];
    const backButton = document.getElementById('back-button');
    const deleteButton = document.getElementById('delete-button');
    const confirmYesButton = document.getElementById('confirm-yes');
    const confirmNoButton = document.getElementById('confirm-no');

    // Função para buscar quizzes do usuário
    async function fetchUserQuizzes(codUsuario) {
        try {
            const response = await fetch('http://localhost:8084/quizQuestController/obterQuizzesUsuario', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ codUsuario }) // Certifique-se de que o backend espera "codUsuario"
            });
            if (response.ok) {
                return response.json();
            } else {
                throw new Error('Erro ao buscar quizzes');
            }
        } catch (error) {
            console.error('Erro ao buscar quizzes:', error);
            return [];
        }
    }

    // Renderizar lista de quizzes
    async function renderQuizList() {
        const userQuizzes = await fetchUserQuizzes(user.codUsuario);
        quizList.innerHTML = '';

        userQuizzes.quizList
            .filter(quiz => quiz.nomeQuiz && quiz.perguntasList.length > 0) // Filtrar quizzes válidos
            .forEach((quiz) => {
                const quizItem = document.createElement('div');
                quizItem.className = 'quiz-item';
                quizItem.innerHTML = `<strong>${quiz.nomeQuiz}</strong> (Código: ${quiz.codQuiz})`;
                quizItem.addEventListener('click', () => viewQuiz(quiz));
                quizList.appendChild(quizItem);
            });
    }

    // Exibir detalhes do quiz
    function viewQuiz(quiz) {
        quizDetails.innerHTML = `<h3>${quiz.nomeQuiz} (Código: ${quiz.codQuiz})</h3>`;
        quiz.perguntasList.forEach((pergunta) => {
            quizDetails.innerHTML += `<p><strong>Pergunta:</strong> ${pergunta.textoPergunta}</p>`;
            pergunta.respostaList.forEach((resposta) => {
                const checkIcon = resposta.flRespCorreta ? '<i class="fas fa-check" style="color: green;"></i>' : '';
                quizDetails.innerHTML += `<p><strong>Resposta:</strong> ${resposta.textoResposta} ${checkIcon}</p>`;
            });
            quizDetails.innerHTML += `<hr>`;
        });
        quizModal.style.display = 'block';

        // Lógica de voltar
        backButton.onclick = function () {
            quizModal.style.display = 'none';
        };

        deleteButton.onclick = function () {
            confirmDeleteModal.style.display = 'block';
            confirmYesButton.onclick = function () {
                deleteQuiz(quiz.codQuiz);
            };
        };
    }

    // Função para excluir quiz
    async function deleteQuiz(quizCode) {
        try {
            const response = await fetch('http://localhost:8084/quizQuestController/deleteQuiz', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(quizCode)
            });
            if (response.ok) {
                confirmDeleteModal.style.display = 'none';
                quizModal.style.display = 'none';
                renderQuizList();
            } else {
                throw new Error('Erro ao excluir quiz');
            }
        } catch (error) {
            console.error('Erro ao excluir quiz:', error);
        }
    }

    closeModal.onclick = function () {
        quizModal.style.display = 'none';
    };

    window.onclick = function (event) {
        if (event.target === quizModal) {
            quizModal.style.display = 'none';
        } else if (event.target === confirmDeleteModal) {
            confirmDeleteModal.style.display = 'none';
        }
    };

    // Evento de logout
    document.getElementById('logout-button').addEventListener('click', function () {
        localStorage.removeItem('user');
        window.location.href =  "login.html";;
    });

    renderQuizList();
});
