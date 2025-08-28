#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>


int main() {

    // criação do socket
    int client_socket = socket(AF_INET, SOCK_STREAM, 0);
    
    if (client_socket < 0) {
        perror("socket");
        return -1;
    }

    //preenchimento do endereço do servidor
    struct sockaddr_in server_address;
    server_address.sin_family = AF_INET;
    server_address.sin_port = 9002;
    server_address.sin_addr.s_addr = inet_addr("127.0.0.1"); // localhost

    // conectar ao servidor
    if (connect(client_socket, (struct sockaddr*)&server_address, sizeof(server_address)) < 0) {
        perror("connect");
        return 1;
    }

    // receber a mensagem do server
    char mensagem[256]; 
    memset(mensagem, 0, sizeof(mensagem));
    ssize_t bytes = recv(client_socket, mensagem, sizeof(mensagem) - 1, 0);
    if (bytes < 0) {
        perror("recv");
        close(client_socket);
        return -1;
    }

    // mostrando a mensgem
    printf("Mensagem recebida: %s\n\n", mensagem);

    // fechar o socket
    close(client_socket);

    return 0;
}
