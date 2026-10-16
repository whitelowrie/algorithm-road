package main

import (
	"fmt"
	"testing"
)

func Test_Sort01(t *testing.T) {
	number := 123
	result := ""
	for i := 0; i <= 31; i++ {
		if number&(1<<i) != 0 {
			result = "1" + result
		} else {
			result = "0" + result
		}
	}

	fmt.Println(result)
}
