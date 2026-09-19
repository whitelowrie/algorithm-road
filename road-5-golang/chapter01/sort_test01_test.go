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

func TestSort05(t *testing.T) {
	tools := NewTools(10, 100)
	items := tools.GetItems()
	fmt.Println("排序前:", tools.toString())
	process(items, 0, len(items)-1)

	fmt.Println("排序后:", items)

}

func process(items []int, min int, max int) {
	if min == max {
		return
	}
	mid := min + (max-min)>>1
	process(items, min, mid)
	process(items, mid+1, max)
	merge(items, min, mid, max)
}
func merge(items []int, min int, mid int, max int) {
	helper := make([]int, max-min+1)
	i := 0
	p1 := min
	p2 := mid + 1
	for p1 <= mid && p2 <= max {
		if items[p1] > items[p2] {
			helper[i] = items[p2]
			p2++
		} else {
			helper[i] = items[p1]
			p1++
		}
		i++
	}
	for ; p1 <= mid; p1++ {
		helper[i] = items[p1]
		i++
	}
	for ; p2 <= max; p2++ {
		helper[i] = items[p2]
		i++
	}
	for k := 0; k < len(helper); k++ {
		items[min+k] = helper[k]
	}
}
