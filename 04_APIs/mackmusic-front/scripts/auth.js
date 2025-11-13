document.addEventListener('DOMContentLoaded', function() {
    // Verifica se estamos na página de login
    if (document.getElementById('loginForm')) {
        const loginForm = document.getElementById('loginForm');
        
        loginForm.addEventListener('submit', function(e) {
            e.preventDefault();
            
            const email = document.getElementById('email').value.trim();
            const password = document.getElementById('password').value;
            
            // Validação do email
            if (!validateEmail(email)) {
                document.getElementById('emailError').textContent = 'Por favor, insira um e-mail válido';
                return;
            } else {
                document.getElementById('emailError').textContent = '';
            }
            
            // Validação da senha
            if (password.length < 6) {
                document.getElementById('passwordError').textContent = 'A senha deve ter pelo menos 6 caracteres';
                return;
            } else {
                document.getElementById('passwordError').textContent = '';
            }
            
            // Hash MD5 da senha
            const hashedPassword = md5(password);
            
            // Dados para enviar
            const loginData = {
                email: email,
                password: hashedPassword
            };
            
            // Fazendo a requisição para a API
            fetch('http://localhost:8080/user/auth', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(loginData)
            })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Erro na autenticação');
                }
                return response.json();
            })
            .then(data => {
                if (data.authenticated) {
                    // Armazena os dados do usuário no localStorage
                    localStorage.setItem('user', JSON.stringify({
                        id: data.userId,
                        name: data.name,
                        email: email
                    }));
                    
                    // Redireciona para o dashboard (que criaremos depois)
                    window.location.href = 'dashboard.html';
                } else {
                    alert('E-mail ou senha incorretos');
                }
            })
            .catch(error => {
                console.error('Erro:', error);
                alert('Erro ao fazer login. Por favor, tente novamente.');
            });
        });
    }
    
    // Verifica se estamos na página de registro
if (document.getElementById('registerForm')) {
    const registerForm = document.getElementById('registerForm');
    
    registerForm.addEventListener('submit', function(e) {
        e.preventDefault();
        
        const name = document.getElementById('name').value.trim();
        const email = document.getElementById('email').value.trim();
        const password = document.getElementById('password').value;
        const confirmPassword = document.getElementById('confirmPassword').value;
        
        // Validações
        let isValid = true;
        
        if (name.length < 3) {
            document.getElementById('nameError').textContent = 'O nome deve ter pelo menos 3 caracteres';
            isValid = false;
        } else {
            document.getElementById('nameError').textContent = '';
        }
        
        if (!validateEmail(email)) {
            document.getElementById('emailError').textContent = 'Por favor, insira um e-mail válido';
            isValid = false;
        } else {
            document.getElementById('emailError').textContent = '';
        }
        
        if (password.length < 6) {
            document.getElementById('passwordError').textContent = 'A senha deve ter pelo menos 6 caracteres';
            isValid = false;
        } else {
            document.getElementById('passwordError').textContent = '';
        }
        
        if (password !== confirmPassword) {
            document.getElementById('confirmPasswordError').textContent = 'As senhas não coincidem';
            isValid = false;
        } else {
            document.getElementById('confirmPasswordError').textContent = '';
        }
        
        if (!isValid) return;
        
        // Hash MD5 da senha antes de enviar
        const hashedPassword = md5(password);
        
        // Dados para enviar (agora com a senha hasheada)
        const registerData = {
            name: name,
            email: email,
            password: hashedPassword // Envia o hash MD5 da senha
        };
        
        // Fazendo a requisição para a API
        fetch('http://localhost:8080/user', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(registerData)
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Erro no registro');
            }
            return response.json();
        })
        .then(data => {
            // Mostra mensagem de sucesso
            const successMessage = document.createElement('div');
            successMessage.className = 'success-message';
            successMessage.textContent = 'Registro realizado com sucesso! Redirecionando para login...';
            successMessage.style.display = 'block';
            
            const form = document.getElementById('registerForm');
            form.parentNode.insertBefore(successMessage, form);
            
            // Redireciona após 5 segundos
            setTimeout(() => {
                window.location.href = 'index.html';
            }, 5000);
        })
        .catch(error => {
            console.error('Erro:', error);
            alert('Erro ao registrar. Por favor, tente novamente.');
        });
    });
}
});

// Função para validar e-mail
function validateEmail(email) {
    const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return re.test(email);
}