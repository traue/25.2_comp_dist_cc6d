#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>

int main() {

    char server_message[256] = "Parabéns, você chegou ao server! Congrats!";
    
    // criação do socket
    int server_socket = socket(AF_INET, SOCK_STREAM, 0);

    if (server_socket < 0) {
        perror("socket");
        return 1;
    }

    // definir o endereçamento
    struct sockaddr_in server_address;
    server_address.sin_family = AF_INET;
    server_address.sin_port = 9002;
    server_address.sin_addr.s_addr = INADDR_ANY;

    // bind
    bind(server_socket, (struct sockaddr*)&server_address, sizeof(server_address)); // << certo
    //bind(server_socket, &server_address, sizeof(server_address));
    
    listen(server_socket, 5);

    // aceitar as conexões
    int client_socket = accept(server_socket, NULL, NULL);

    // enviar a mensagem
    send(client_socket, server_message, sizeof(server_message), 0);


    // fecha o socket
    close(server_socket);

    return 0;
}