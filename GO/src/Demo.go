package src

import (
	"fmt"
	"unsafe"
)

var a int = 32

func main() {
	fmt.Print("Hello World!\n")
	fmt.Print(unsafe.Sizeof(a))
}
