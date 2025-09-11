#include <stdio.h>

int main(int argc, char const *argv[]) {
    
    printf("Total de argumentos recebidos: %d\n", argc);
    printf("...Argumentos recebidos:\n");

    for (int i = 0; i < argc; i++) {
        printf("Argumento %d: %s\n", i, argv[i]);
    }

    return 0;
}
