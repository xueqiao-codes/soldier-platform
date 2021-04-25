/*
 * gethostbyname_test.cpp
 *
 *  Created on: 2018年10月19日
 *      Author: wangli
 */

#include <stdio.h>
#include <netdb.h>
#include <sys/socket.h>
#include <arpa/inet.h>
#include "base/code_defense.h"

int main(int argc, char* argv[]) {
    if (argc < 2) {
        printf("please input hostname\n");
        return 1;
    }

    char* hostname = argv[1];

    char   str[128] = {0};

    struct hostent he, *hptr;
    int herr, ret, bufsz = 512;
    char *buff = NULL;
    do {
        char *new_buff = (char *)realloc(buff, bufsz);
        if (new_buff == NULL) {
            if (buff != NULL) {
                free(buff);
            }
            return ENOMEM;
        }
        buff = new_buff;
        ret = gethostbyname_r(hostname, &he, buff, bufsz, &hptr, &herr);
        bufsz *= 2;
    } while (ret == ERANGE);

    if (ret != 0) {
        free(buff);
        printf("get hostname failed, ret=%d\n", ret);
        return -1;
    }

    if(hptr != &he) {
        free(buff);
        printf("hptr != &he, means not host?");
        return -1;
    }

    if (hptr->h_name == NULL || hptr->h_addr_list == NULL) {
        free(buff);
        printf("no host found!!!");
        return 0;
    }

    printf("official hostname:%s\n", hptr->h_name);
    printf("h_length:%d\n", hptr->h_length);
    printf("h_addrtype:%d\n", hptr->h_addrtype);

    for(char** pptr = hptr->h_aliases; *pptr != NULL; pptr++)
        printf(" alias:%s\n",*pptr);

    switch(hptr->h_addrtype) {
        case AF_INET:
        case AF_INET6:
            for(char** pptr=hptr->h_addr_list; *pptr!=NULL; pptr++) {
                printf(" address:%s\n",
                        inet_ntop(hptr->h_addrtype, *pptr, str, sizeof(str)));
            }
            printf(" first address: %s\n",
                    inet_ntop(hptr->h_addrtype, hptr->h_addr, str, sizeof(str)));
           break;
        default:
            printf("unknown address type\n");
            break;
    }
    return 0;
}


