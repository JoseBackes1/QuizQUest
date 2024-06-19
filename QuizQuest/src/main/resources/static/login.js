document.addEventListener('DOMContentLoaded', function() {
    // Função para alternar o modo de contraste
    function toggleContrast() {
        document.body.classList.toggle('high-contrast');
        const isHighContrast = document.body.classList.contains('high-contrast');
        localStorage.setItem('highContrast', isHighContrast ? 'true' : 'false');
    }

    // Evento para o botão de alternância de contraste
    document.getElementById('toggle-contrast').addEventListener('click', function(event) {
        toggleContrast();
        // Evitar recarregamento da página
        event.preventDefault();
    });

    // Função para login do usuário
    async function loginUser(email, senha) {
        const response = await fetch('http://localhost:8084/quizQuestController/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ email, senha })
        });
        if (response.ok) {
            const userData = await response.json();
            // Armazenar informações do usuário no localStorage
            localStorage.setItem('user', JSON.stringify(userData));
            return true;
        } else {
            throw new Error('Erro ao fazer login');
        }
    }

    // Evento para o formulário de login
    document.getElementById('login-form').addEventListener('submit', async function(event) {
        event.preventDefault();

        const email = document.getElementById('email').value;
        const senha = document.getElementById('password').value;

        try {
            const loginSuccess = await loginUser(email, senha);
            if (loginSuccess) {
                window.location.href = 'dashboard.html';
            } else {
                alert('Email ou senha incorretos');
            }
        } catch (error) {
            console.error('Erro ao efetuar login:', error);
        }
    });

    // Função para verificar se o usuário está logado
    function checkLoginStatus() {
        const user = JSON.parse(localStorage.getItem('user'));
        console.log('User check:', user); // Debugging log
        if (!user) {
            window.location.href = 'login.html';
        }
        return user;
    }

    // Evento ao carregar a página
    window.addEventListener('load', function() {
        // Manter a preferência do usuário ao recarregar a página
        if (localStorage.getItem('highContrast') === 'true') {
            document.body.classList.add('high-contrast');
        }

        // Verificar o status do login se estiver na página do dashboard
        if (document.getElementById('welcome-message')) {
            const user = checkLoginStatus();
            if (user) {
                document.getElementById('welcome-message').textContent = `Bem-vindo, ${user.nome}`;
            }
        }
    });

    // Função para exibir o modal de cadastro
    function showModal() {
        document.getElementById('signup-modal').style.display = 'block';
    }

    // Função para fechar o modal de cadastro
    function closeModal() {
        document.getElementById('signup-modal').style.display = 'none';
    }

    // Evento para abrir o modal de cadastro
    document.getElementById('signup-link').addEventListener('click', function(event) {
        event.preventDefault();
        showModal();
    });

    // Evento para fechar o modal de cadastro ao clicar no botão de fechar
    document.querySelector('.modal .close').addEventListener('click', function() {
        closeModal();
    });

    // Evento para fechar o modal de cadastro ao clicar no botão de cancelar
    document.getElementById('cancel-signup').addEventListener('click', function() {
        closeModal();
    });

    // Evento para o formulário de cadastro
    document.getElementById('signup-form').addEventListener('submit', async function(event) {
        event.preventDefault();

        const nome = document.getElementById('signup-name').value;
        const email = document.getElementById('signup-email').value;
        const senha = document.getElementById('signup-password').value;
        const confirmPassword = document.getElementById('confirm-password').value;
        const errorMessage = document.getElementById('error-message');

        if (senha.length >= 6 && senha === confirmPassword) {
            try {
                const registerResponse = await registerUser(nome, email, senha);
                if (registerResponse) {
                    window.location.href = 'dashboard.html';
                } else {
                    errorMessage.textContent = 'Erro ao cadastrar usuário.';
                }
            } catch (error) {
                console.error('Erro ao cadastrar usuário:', error);
            }
        } else {
            errorMessage.textContent = 'Verifique os campos e tente novamente.';
        }
    });

    // Função para validar os campos de senha e atualizar o botão de submissão
    function validatePasswords() {
        const password = document.getElementById('signup-password').value;
        const confirmPassword = document.getElementById('confirm-password').value;
        const errorMessage = document.getElementById('error-message');
        const submitButton = document.querySelector('#signup-form button[type="submit"]');

        if (password.length < 6) {
            errorMessage.textContent = 'A senha deve ter pelo menos 6 caracteres.';
            submitButton.disabled = true;
            errorMessage.style.display = "block";
            errorMessage.style.color = "red";
            document.getElementById('signup-password').style.border = "1px solid red";
        } else if (confirmPassword && password !== confirmPassword) {
            errorMessage.textContent = 'As senhas não coincidem.';
            errorMessage.style.color = "red";
            document.getElementById('confirm-password').style.border = "1px solid red";
            submitButton.disabled = true;
        } else if (confirmPassword && password === confirmPassword) {
            errorMessage.textContent = '';
            submitButton.disabled = false;
            document.getElementById('signup-password').style.border = "";
            document.getElementById('confirm-password').style.border = "";
        } else {
            errorMessage.textContent = '';
            submitButton.disabled = true;
        }
    }

    // Validação de senha no evento blur do campo de senha
    document.getElementById('signup-password').addEventListener('blur', validatePasswords);

    // Validação de confirmação de senha no evento blur do campo de confirmação de senha
    document.getElementById('confirm-password').addEventListener('blur', validatePasswords);

    // Inicializar o botão de submissão como desabilitado
    document.addEventListener('DOMContentLoaded', function() {
        const submitButton = document.querySelector('#signup-form button[type="submit"]');
        submitButton.disabled = true;
    });

    // Função para registrar usuário
    async function registerUser(nome, email, senha) {
        const response = await fetch('http://localhost:8084/quizQuestController/createUsuario', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ nome, email, senha })
        });
        if (response.ok) {
            return response.json();
        } else {
            throw new Error('Erro ao registrar usuário');
        }
    }
});
