#include <stdio.h>


int main() {


    int x = 42;
    int *p = &x;

    printf("\n x = %d", x);
    printf("\n &x = %p", &x);
    printf("\n p = %p", p);
    printf("\n *p = %d", *p);
    printf("\n &p = %p", &p);
    *p = 40;
    printf("\n x = %d \n", x);

    return 0;
}