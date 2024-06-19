document.getElementById('create-quiz').addEventListener('click', function() {
    window.location.href = 'dashboard.html';
});


document.getElementById('search-code').addEventListener('click', function() {
    const code = document.getElementById('quiz-code').value;
    if (code) {
        window.location.href = `qaluno.html?code=${code}`;
    } else {
        alert('Por favor, insira um código.');
    }
});

document.addEventListener("DOMContentLoaded", function () {
    const searchIcon = document.getElementById("search-icon");
    const quizCodeInput = document.getElementById("quiz-code");
    const errorMessage = document.getElementById("error-message");

    searchIcon.addEventListener("click", function () {
        if (!quizCodeInput.value.trim()) {
            errorMessage.textContent = "Código inválido. Tente novamente";
            errorMessage.style.display = "block";
            quizCodeInput.style.border = "1px solid red"; // Adiciona borda vermelha para destacar o campo inválido
        } else {
            errorMessage.textContent = ""; // Limpa a mensagem de erro
            errorMessage.style.display = "none"; // Oculta a mensagem de erro
            quizCodeInput.style.border = ""; // Remove a borda vermelha se o campo for válido
        }
    });
});

// Função para alternar o modo de contraste
function toggleContrast() {
    document.body.classList.toggle('high-contrast');
    // Armazenar a preferência do usuário
    const isHighContrast = document.body.classList.contains('high-contrast');
    localStorage.setItem('highContrast', isHighContrast ? 'true' : 'false');
}

// Evento para o botão de alternância de contraste
document.getElementById('toggle-contrast').addEventListener('click', toggleContrast);

// Manter a preferência do usuário ao recarregar a página
window.addEventListener('load', function() {
    if (localStorage.getItem('highContrast') === 'true') {
        document.body.classList.add('high-contrast');
    }
});

document.addEventListener("DOMContentLoaded", function () {
    const loginBtn = document.getElementById("login-btn");

    loginBtn.addEventListener("click", function () {
        window.location.href = 'login.html';
    });
});
