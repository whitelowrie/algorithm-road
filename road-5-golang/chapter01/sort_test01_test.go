package main

import (
	"fmt"
	"testing"
)

func TestSort(t *testing.T) {
	fmt.Println("Hello, World!")
}

/*
*
选择排序
*/
func TestSort01(t *testing.T) {
	arr := []int{64, 34, 25, 12, 22, 11, 90}
	for i := 0; i < len(arr); i++ {
		for j := i + 1; j < len(arr); j++ {
			if arr[i] > arr[j] {
				arr[i], arr[j] = arr[j], arr[i]
			}
		}
	}
	fmt.Println(arr)
}

/*
*
冒泡排序
*/
func TestSort02(t *testing.T) {
	arr := []int{64, 34, 25, 12, 22, 11, 90}
	for i := 0; i < len(arr); i++ {
		for j := 0; j < len(arr)-i-1; j++ {
			if arr[j] > arr[j+1] {
				arr[j], arr[j+1] = arr[j+1], arr[j]
			}
		}
	}
	fmt.Println(arr)
}

func TestSort03(t *testing.T) {
	arr := []int{17, 18, 19, 5, 18, 19, 17, 5, 19, 5}
	eor := 0
	for i := 0; i < len(arr); i++ {
		eor ^= arr[i]
	}
	rightOne := eor & (^eor + 1)
	eor1 := 0
	for i := 0; i < len(arr); i++ {
		if arr[i]&rightOne == 0 {
			eor1 ^= arr[i]
		}
	}
	eor2 := eor ^ eor1
	fmt.Println(eor1, eor2)
}

func TestSort04(t *testing.T) {
	arr := []int{64, 34, 25, 12, 22, 11, 90}
	for i := 1; i < len(arr); i++ {
		for j := i; j > 0; j-- {
			if arr[j] < arr[j-1] {
				arr[j], arr[j-1] = arr[j-1], arr[j]
			}
		}
	}
	fmt.Println(arr)
}
