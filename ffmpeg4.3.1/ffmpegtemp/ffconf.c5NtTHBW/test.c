#include <stdbool.h>
#include <stdint.h>
#include <camera/NdkCameraManager.h>
#include <stdint.h>
long check_ACameraManager_create(void) { return (long) ACameraManager_create; }
int main(void) { int ret = 0;
 ret |= ((intptr_t)check_ACameraManager_create) & 0xFFFF;
return ret; }
