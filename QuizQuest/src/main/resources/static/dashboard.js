document.addEventListener('DOMContentLoaded', function () {
    // Verificar se o usuário está logado

    const user = JSON.parse(localStorage.getItem('user'));
    console.log(user);
    if (!user) {
        window.location.href =  "login.html";;
        return;
    }

    const welcomeMessage = document.getElementById('welcome-message');
    const userName = user.nome || 'Professor';
    const confirmLogoutModal = document.getElementById('confirm-logout-modal');
    const confirmYesButton = document.getElementById('confirm-yes');
    const confirmNoButton = document.getElementById('confirm-no');
    const toggleContrastButton = document.getElementById('toggle-contrast');

    // Personalizar mensagem de boas-vindas
    if (welcomeMessage) {
        welcomeMessage.textContent = `Bem-vindo, ${userName}!`;
    }

    // Função para alternar o modo de contraste
    function toggleContrast() {
        document.body.classList.toggle('high-contrast');
        const isHighContrast = document.body.classList.contains('high-contrast');
        localStorage.setItem('highContrast', isHighContrast ? 'true' : 'false');
    }

    // Evento para o botão de alto contraste
    if (toggleContrastButton) {
        toggleContrastButton.addEventListener('click', toggleContrast);
    }

    // Manter a preferência do usuário ao recarregar a página
    if (localStorage.getItem('highContrast') === 'true') {
        document.body.classList.add('high-contrast');
    }

    const logoutButton = document.getElementById('logout-button');
    if (logoutButton) {
        logoutButton.addEventListener('click', function () {
            // Exibe o modal de confirmação
            confirmLogoutModal.style.display = 'block';
        });
    }

    if (confirmYesButton) {
        confirmYesButton.addEventListener('click', function () {
            // Se o usuário clicar em "Sim"
            localStorage.removeItem('user');  // Remove os dados do usuário do localStorage
            window.location.href =  "login.html";;  // Redireciona para a página de login
        });
    }

    if (confirmNoButton) {
        confirmNoButton.addEventListener('click', function () {
            // Se o usuário clicar em "Não", esconde o modal de confirmação
            confirmLogoutModal.style.display = 'none';
        });
    }

    // Opcional: Esconder o modal se o usuário clicar fora da caixa de diálogo
    window.addEventListener('click', function(event) {
        if (event.target === confirmLogoutModal) {
            confirmLogoutModal.style.display = 'none';
        }
    });

    /*ZOOM*/
    const zoomInButton = document.getElementById('zoom-in');
    const zoomOutButton = document.getElementById('zoom-out');

    if (zoomInButton) {
        zoomInButton.addEventListener('click', () => {
            adjustFontSize(1.1); // Aumentar em 10%
        });
    }

    if (zoomOutButton) {
        zoomOutButton.addEventListener('click', () => {
            adjustFontSize(0.9); // Diminuir em 10%
        });
    }

    function adjustFontSize(factor) {
        document.body.style.fontSize = (parseFloat(getComputedStyle(document.body).fontSize) * factor) + 'px';
    }
});
