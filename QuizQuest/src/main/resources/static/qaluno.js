// Função para buscar e exibir as perguntas do quiz
function exibirQuiz() {
    // Extrair o código do URL
    const urlParams = new URLSearchParams(window.location.search);
    const code = urlParams.get('code');

    // Verificar se há um código na URL
    if (code) {
        // Configurar os dados para a requisição
        const requestData = {
            codQuiz: code
        };

        // Configurar as opções para a requisição
        const requestOptions = {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(requestData)
        };

        // Realizar a requisição para obter o quiz
        fetch('http://localhost:8084/quizQuestController/obterQuiz', requestOptions)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Erro ao obter o quiz: ' + response.statusText);
                }
                return response.json();
            })
            .then(data => {
                // Verificar se há perguntas no quiz
                if (data && data.length > 0) {
                    const quiz = data[0]; // Como é uma lista com um único elemento, pegamos o primeiro item
                    // Exibir as perguntas no HTML
                    const questionsFieldset = document.querySelector('fieldset#questions');
                    questionsFieldset.innerHTML = ''; // Limpar qualquer conteúdo existente
                    quiz.perguntasList.forEach((pergunta, index) => {
                        const questionDiv = document.createElement('div');
                        questionDiv.classList.add('question-block');
                        questionDiv.innerHTML = `
                            <p>Pergunta ${index + 1}: ${pergunta.textoPergunta}</p>
                        `;
                        pergunta.respostaList.forEach(resposta => {
                            questionDiv.innerHTML += `
                                <input type="radio" id="q${pergunta.codPergunta}-option${resposta.codResposta}" name="question-${pergunta.codPergunta}" value="${resposta.codResposta}">
                                <label for="q${pergunta.codPergunta}-option${resposta.codResposta}">${resposta.textoResposta}</label><br>
                            `;
                        });
                        questionsFieldset.appendChild(questionDiv);
                    });
                } else {
                    // Não há quiz com esse código, mostrar alerta e redirecionar para index.html ao fechar o alerta
                    alert('Não existe quiz com o código fornecido.');
                    window.location.href = 'index.html';
                }
            })
            .catch(error => {
                // Ocorreu um erro na requisição, mostrar alerta e redirecionar para index.html ao fechar o alerta
                alert('Ocorreu um erro ao obter o quiz. Por favor, tente novamente mais tarde.');
                window.location.href = 'index.html';
            });
    } else {
        console.error('Nenhum código de quiz encontrado na URL.');
    }
}

// Chamar a função para exibir o quiz quando o conteúdo do DOM estiver carregado
document.addEventListener('DOMContentLoaded', exibirQuiz);

// Evento para o formulário de submissão
document.getElementById('quiz-form').addEventListener('submit', function(event) {
    event.preventDefault();

    const urlParams = new URLSearchParams(window.location.search);
    const code = urlParams.get('code');
    const nomeUsuario = document.getElementById('student-name').value; // Usar o campo de entrada para o nome do usuário
    const respostas = [];

    // Coletar as respostas selecionadas
    document.querySelectorAll('.question-block').forEach(block => {
        const perguntaId = block.querySelector('input[type="radio"]').name.split('-')[1];
        const respostaId = block.querySelector('input[type="radio"]:checked')?.value;

        if (respostaId) {
            respostas.push({
                codPergunta: parseInt(perguntaId, 10),
                codResposta: parseInt(respostaId, 10)
            });
        }
    });

    // Construir o objeto de dados para envio
    const respostaData = {
        codQuiz: parseInt(code, 10),
        nomeUsuario: nomeUsuario,
        respostaList: respostas
    };

    // Configurar as opções para a requisição
    const requestOptions = {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(respostaData)
    };

    // Enviar os dados para o backend
    fetch('http://localhost:8084/quizQuestController/enviarRespostas', requestOptions)
        .then(response => {
            if (!response.ok) {
                throw new Error('Erro ao enviar as respostas: ' + response.statusText);
            }
            return response.json();
        })
        .then(data => {
            // Exibir o resultado no modal
            const resultDialog = document.getElementById('result-dialog');
            const resultMessage = document.getElementById('result-message');
            resultMessage.textContent = `Você acertou ${data.numRespostasCorretas} de ${data.numRespostas}`;
            resultDialog.style.display = 'block';
        })
        .catch(error => {
            console.error(error);
            alert('Ocorreu um erro ao enviar as respostas. Por favor, tente novamente.');
        });
});

// Evento para o botão de cancelar o quiz
document.getElementById('cancel-quiz').addEventListener('click', function() {
    document.getElementById('confirmation-dialog').style.display = 'block';
});

// Evento para o botão "Não" na tela de confirmação
document.getElementById('confirm-no').addEventListener('click', function() {
    document.getElementById('confirmation-dialog').style.display = 'none';
});

// Evento para o botão "Sim" na tela de confirmação
document.getElementById('confirm-yes').addEventListener('click', function() {
    window.location.href = 'index.html';
});

// Evento para fechar o modal de resultado
document.getElementById('close-result').addEventListener('click', function() {
    document.getElementById('result-dialog').style.display = 'none';
    window.location.href = 'index.html'; // Redirecionar para a página inicial após fechar o modal
});
