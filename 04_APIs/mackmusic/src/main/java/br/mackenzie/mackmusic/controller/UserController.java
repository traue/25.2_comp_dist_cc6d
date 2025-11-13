// Define o pacote onde esta classe está localizada
package br.mackenzie.mackmusic.controller;

// Importa os DTOs utilizados nas requisições e respostas relacionadas à autenticação e usuário
import br.mackenzie.mackmusic.controller.dto.AuthRequest;
import br.mackenzie.mackmusic.controller.dto.AuthResponse;
import br.mackenzie.mackmusic.controller.dto.UserDTO;

// Importa o serviço que contém a lógica de negócio relacionada a usuários
import br.mackenzie.mackmusic.service.UserService;

// Importa a anotação para validação de dados da requisição
import jakarta.validation.Valid;

// Lombok: Gera automaticamente um construtor com todos os campos `final` como parâmetro
import lombok.RequiredArgsConstructor;

// Anotações para construir um Controller REST e mapear endpoints
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

// Define que esta classe é um controlador REST que retornará JSON
@RestController

// Define o caminho base para todos os endpoints desta classe: /user
@RequestMapping("/user")

// Lombok: Gera o construtor com argumentos obrigatórios (neste caso, o userService)
@RequiredArgsConstructor
public class UserController {

    // Injeta automaticamente a instância do serviço de usuário
    private final UserService userService;

    // Endpoint POST para autenticar um usuário
    // Recebe um corpo JSON com email e senha e retorna um token de autenticação
    @PostMapping("/auth")
    public ResponseEntity<AuthResponse> authenticate(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(userService.authenticate(request.email(), request.password()));
    }

    // Endpoint GET para retornar a lista de todos os usuários
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // Endpoint GET para buscar um usuário por ID (UUID)
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable UUID id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    // Endpoint POST para criar um novo usuário
    // Usa @Valid para garantir que os dados enviados estejam corretos
    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody @Valid UserDTO userDTO) {
        return ResponseEntity.ok(userService.createUser(userDTO));
    }

    // Endpoint PUT para atualizar um usuário existente com base no ID
    // Também usa @Valid para validar os dados enviados
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(
            @PathVariable UUID id,
            @RequestBody @Valid UserDTO userDTO) {
        return ResponseEntity.ok(userService.updateUser(id, userDTO));
    }

    // Endpoint DELETE para remover um usuário com base no ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build(); // Retorna 204 No Content
    }
}