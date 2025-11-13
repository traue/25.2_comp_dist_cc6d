package br.mackenzie.mackmusic.service;

// Importa os DTOs usados para transferência de dados e resposta de autenticação
import br.mackenzie.mackmusic.controller.dto.AuthResponse;
import br.mackenzie.mackmusic.controller.dto.UserDTO;

// Importa a entidade User (modelo de domínio)
import br.mackenzie.mackmusic.model.User;

// Importa o repositório responsável pela persistência da entidade User
import br.mackenzie.mackmusic.repository.UserRepository;

// Importa exceção padrão usada quando um recurso não é encontrado no banco
import jakarta.persistence.EntityNotFoundException;

// Lombok: Gera automaticamente o construtor com os campos `final`
import lombok.RequiredArgsConstructor;

// Indica que esta classe é um Service gerenciado pelo Spring
import org.springframework.stereotype.Service;

// Garante que os métodos anotados sejam executados em uma transação
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

// Declara que esta classe é um serviço do Spring
@Service

// Lombok: cria automaticamente um construtor com os campos obrigatórios (no caso, userRepository)
@RequiredArgsConstructor
public class UserService {

    // Injeção de dependência do repositório de usuários
    private final UserRepository userRepository;

    // Retorna todos os usuários do sistema, convertendo cada entidade para DTO
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserDTO::fromEntity) // converte User → UserDTO
                .toList(); // retorna uma lista imutável com os DTOs
    }

    // Realiza a autenticação do usuário com base em e-mail e senha
    public AuthResponse authenticate(String email, String password) {
        // Busca o usuário pelo e-mail ou lança exceção se não encontrar
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found with email: " + email));

        // Verifica se a senha fornecida é igual à cadastrada
        // OBS: em uma aplicação real, essa comparação deve ser feita com senha criptografada
        if (user.getPassword().equals(password)) {
            return new AuthResponse(true, user.getId(), user.getName()); // Login bem-sucedido
        }

        // Retorna resposta negativa se a senha estiver incorreta
        return new AuthResponse(false, null, null);
    }

    // Busca um usuário pelo ID e retorna como DTO
    public UserDTO getUserById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
        return UserDTO.fromEntity(user);
    }

    // Cria um novo usuário no banco de dados
    @Transactional // Garante que a operação seja atômica (tudo ou nada)
    public UserDTO createUser(UserDTO userDTO) {
        User user = userDTO.toEntity(); // Converte DTO para entidade
        User savedUser = userRepository.save(user); // Salva no banco
        return UserDTO.fromEntity(savedUser); // Retorna o resultado como DTO
    }

    // Atualiza os dados de um usuário existente
    @Transactional
    public UserDTO updateUser(UUID id, UserDTO userDTO) {
        // Busca o usuário existente
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));

        // Atualiza os dados do usuário com os valores recebidos
        existingUser.setName(userDTO.name());
        existingUser.setEmail(userDTO.email());
        existingUser.setPassword(userDTO.password());

        // Salva as alterações e retorna o resultado como DTO
        User updatedUser = userRepository.save(existingUser);
        return UserDTO.fromEntity(updatedUser);
    }

    // Remove um usuário do banco de dados
    @Transactional
    public void deleteUser(UUID id) {
        // Verifica se o ID existe antes de tentar deletar
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(id); // Remove o usuário
    }
}