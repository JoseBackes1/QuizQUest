document.addEventListener('DOMContentLoaded', function() {
    const rankingForm = document.getElementById('ranking-form');
    const errorMessage = document.getElementById('error-message');

    if (rankingForm) {
        rankingForm.addEventListener('submit', function(event) {
            event.preventDefault();

            const codeInput = document.getElementById('ranking-code');
            const code = codeInput.value.trim();

            fetch('http://localhost:8084/quizQuestController/obterRanking', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(parseInt(code, 10))
            })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Erro ao obter o ranking.');
                }
                return response.json();
            })
            .then(data => {
                if (!data || !data.ranqueadosList) {
                    throw new Error('Dados de ranking inválidos.');
                }

                renderRankingTable(data.ranqueadosList);
            })
            .catch(error => {
                console.error('Erro ao obter o ranking:', error);
                errorMessage.textContent = 'Erro ao obter o ranking. Tente novamente mais tarde.';
                errorMessage.style.display = 'block';
            });
        });
    }
});

function renderRankingTable(students) {
    const tableHead = document.querySelector('#ranking-table thead');
    const tableBody = document.querySelector('#ranking-table tbody');
    tableHead.innerHTML = '';
    tableBody.innerHTML = '';

    // Definir cabeçalho dinâmico
    const firstStudent = students[0];
    const numQuestions = firstStudent.perguntasQueAcertouList.length;

    // Cabeçalho fixo para "Nome do Aluno" e "Pontuação"
    let headerRow = '<tr><th>Nome do Aluno</th>';
    for (let i = 0; i < numQuestions; i++) {
        headerRow += `<th>Pergunta ${i + 1}</th>`;
    }
    headerRow += '<th>Pontuação</th></tr>';

    tableHead.innerHTML = headerRow;

    // Preencher corpo da tabela com dados dos alunos
    students.sort((a, b) => b.pontuacao - a.pontuacao);

    students.forEach(student => {
        const row = document.createElement('tr');
        const nameCell = document.createElement('td');
        nameCell.textContent = student.nome;
        row.appendChild(nameCell);

        student.perguntasQueAcertouList.forEach(answer => {
            const cell = document.createElement('td');
            cell.innerHTML = answer ? '<span class="check-mark">✔</span>' : '<span class="cross-mark">✖</span>';
            row.appendChild(cell);
        });

        const scoreCell = document.createElement('td');
        scoreCell.textContent = student.pontuacao;
        row.appendChild(scoreCell);

        tableBody.appendChild(row);
    });

    document.getElementById('ranking-table').style.display = 'table';
}
