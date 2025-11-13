package br.mackenzie.mackmusic.controller.dto;

import java.util.UUID;

// Importa a classe de domínio que será convertida para DTO e vice-versa
import br.mackenzie.mackmusic.model.User;

// Importa as anotações de validação que serão aplicadas nos campos do DTO
import jakarta.validation.constraints.*;
public record UserDTO(
        // Identificador único do usuário (pode ser nulo em uma criação de novo usuário)
        UUID id,

        // Validação: o nome não pode ser nulo ou vazio
        // Deve ter entre 3 e 30 caracteres
        @NotBlank(message = "Nome do usuário é obrigatório")
        @Size(min = 3, max = 30, message = "Nome deve ter entre 3 e 30 caracteres")
        String name,

        // Validação: o e-mail é obrigatório e deve seguir o formato válido
        @NotBlank(message = "E-mail é obrigatório")
        @Email(message = "E-mail inválido")
        String email,

        // Validação: a senha não pode ser vazia e deve ter entre 6 e 100 caracteres
        @NotBlank(message = "Senha é obrigatória")
        @Size(min = 6, max = 100, message = "A senha deve ter entre 6 e 100 caracteres")
        String password
) {

    // Método estático auxiliar para criar um UserDTO a partir de um objeto User (entidade do sistema)
    public static UserDTO fromEntity(User user) {
        return new UserDTO(
                user.getId(),      // Copia o ID da entidade
                user.getName(),    // Copia o nome da entidade
                user.getEmail(),   // Copia o e-mail da entidade
                null      // Não expõe a senha na resposta por segurança
        );
    }

    // Método para converter um UserDTO de volta em uma entidade User (para salvar no banco, por exemplo)
    public User toEntity() {
        User user = new User();
        user.setId(this.id());         // Define o ID
        user.setName(this.name());     // Define o nome
        user.setEmail(this.email());   // Define o e-mail
        user.setPassword(this.password()); // Define a senha (se presente)
        return user;
    }

}
