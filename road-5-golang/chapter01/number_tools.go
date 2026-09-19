package main

import (
	"math/rand"
	"strconv"
	"strings"
)

type Tools struct {
	items []int
}

func NewTools(numberSize int, maxNumber int) *Tools {
	items := make([]int, numberSize)
	for i := range items {
		items[i] = rand.Intn(maxNumber+1) - rand.Intn(maxNumber)
	}
	return &Tools{
		items: items,
	}
}

func (t *Tools) GetItems() []int {
	dst := make([]int, len(t.items))
	copy(dst, t.items)
	return dst
}

func (t *Tools) toString() string {
	strs := make([]string, len(t.items))
	for i, v := range t.items {
		strs[i] = strconv.Itoa(v)
	}
	return strings.Join(strs, ",")
}
