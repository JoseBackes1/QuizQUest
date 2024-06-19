document.addEventListener('DOMContentLoaded', function () {
    const user = JSON.parse(localStorage.getItem('user'));
    if (!user) {
        window.location.href = "login.html";
        return;
    }

    const quizNameInput = document.getElementById('quiz-name');
    const errorMessage = document.getElementById('quiz-name-error');
    const saveQuizButton = document.getElementById('save-quiz');
    const cancelButton = document.getElementById('cancel');
    const confirmationDialog = document.getElementById('confirmation-dialog');
    const confirmYesButton = document.getElementById('confirm-yes');
    const confirmNoButton = document.getElementById('confirm-no');

    // Abrir modal de confirmação ao clicar no botão cancelar
    cancelButton.addEventListener('click', function() {
        confirmationDialog.style.display = 'block';
    });

    // Fechar modal de confirmação ao clicar no botão "Não"
    confirmNoButton.addEventListener('click', function() {
        confirmationDialog.style.display = 'none';
    });

    // Redirecionar para o dashboard ao clicar no botão "Sim"
    confirmYesButton.addEventListener('click', function() {
        window.location.href = 'dashboard.html';
    });

    saveQuizButton.addEventListener('click', async () => {
        const quizName = quizNameInput.value.trim();

        if (quizName === "") {
            errorMessage.textContent = 'Por favor, preencha o nome do quiz.';
            errorMessage.style.color = 'red';
            errorMessage.style.display = 'block';
            quizNameInput.style.border = '1px solid red';
            quizNameInput.focus();
            quizNameInput.scrollIntoView({ behavior: 'smooth', block: 'center' });
            return; // Impede o envio do formulário se o nome do quiz estiver em branco
        }

        const camposQuizUsuario = {
            codQuiz: null,
            codUsuario: user.codUsuario,
            nomeQuiz: quizName,
            perguntasList: []
        };

        const questionBlocks = document.querySelectorAll('.question-block');
        questionBlocks.forEach(questionBlock => {
            const questionInput = questionBlock.querySelector('input[id^="question-"]');
            const questionText = questionInput.value.trim();

            if (questionText !== "") {
                const question = {
                    codPergunta: null,
                    textoPergunta: questionText,
                    respostaList: []
                };

                const answerInputs = questionBlock.querySelectorAll('input[id^="option-"]');
                const correctOptionSelect = questionBlock.querySelector('select[id^="correct-option-"]');
                answerInputs.forEach((answerInput, index) => {
                    const answerText = answerInput.value.trim();
                    const isCorrect = index === parseInt(correctOptionSelect.value) - 1;

                    if (answerText !== "") {
                        question.respostaList.push({
                            codResposta: null,
                            textoResposta: answerText,
                            flRespCorreta: isCorrect
                        });
                    }
                });

                camposQuizUsuario.perguntasList.push(question);
            }
        });

        try {
            const response = await fetch('http://localhost:8084/quizQuestController/saveQuiz', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(camposQuizUsuario)
            });

            if (response.ok) {
                alert('Quiz salvo com sucesso!');
                window.location.href = 'dashboard.html';
            } else {
                const errorText = await response.text();
                console.error('Erro ao salvar o quiz:', errorText);
                throw new Error('Erro ao salvar o quiz.');
            }
        } catch (error) {
            console.error('Erro ao salvar o quiz:', error);
            alert('Ocorreu um erro ao salvar o quiz. Por favor, tente novamente.');
        }
    });
});
