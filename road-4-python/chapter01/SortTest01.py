from functools import reduce
from unittest import TestCase

from torch.nn.functional import fold


class SortTest(TestCase):

    def test01(self):
        print("test01")

    def test_01(self):
        """
        选择排序
        """
        def swap(arr: list[int], i: int, j: int):
            arr[i], arr[j] = arr[j], arr[i]

        for i in range(len(self.items)):
            min_index = i
            for j in range(i + 1, len(self.items)):
                if self.items[min_index] > self.items[j]:
                    min_index = j
            swap(self.items, i, min_index)
        print(self.items)

    def test_02(self):
        items = [18, 19]
        def swap(arr: list[int], i: int, j: int):
            arr[i] = arr[i] ^ arr[j]
            arr[j] = arr[i] ^ arr[j]
            arr[i] = arr[i] ^ arr[j]

        swap(items, 0 , 1)
        print(items)

    def test_03(self):
        for i in range(len(self.items)):
            for j in range(len(self.items) - i - 1):
                if self.items[j] > self.items[j + 1]:
                    self.items[j], self.items[j + 1] = self.items[j + 1], self.items[j]

    def test_04(self):
        items = [17, 18, 19, 5, 18, 19, 17, 5, 19, 5]
        eor = reduce(lambda x, y: x ^ y, items)
        rightOne = eor & (~eor + 1)
        mid = filter(lambda x: x & rightOne == 0, items)
        eor1 = reduce(lambda x, y: x ^ y, mid)
        print(f"eor: {eor}, rightOne: {eor1}, eor1: {eor1 ^ eor}")

    """
     插入排序
    """
    def test_05(self):
        items = [5, 3, 8, 1, 2, 7, 4, 6]
        for i in range(len(items)):
            for j in range(i, 0, -1):
                if items[j] < items[j - 1]:
                    items[j], items[j - 1] = items[j - 1], items[j]
        print(items)

    def test_06(self):
        items = [5, 3, 8, 1, 2, 7, 4, 6]
        print(items)
        self.process(items, 0, len(items) - 1)
        print(items)

    def process(self, items: list[int], min: int, max: int):
        if min == max:
            return
        mid = min + ((max - min) >> 1)
        self.process(items, min, mid)
        self.process(items, mid + 1, max)
        self.merge(items, min, mid, max)

    def merge(self, items: list[int], m: int, mid: int, max: int):
        helper = [0 for _ in range(max - m + 1)]
        i = 0
        p1 = m
        p2 = mid + 1
        while p1 <= mid and p2 <= max:
            if items[p1] > items[p2]:
                helper[i] = items[p2]
                p2 += 1
            else:
                helper[i] = items[p1]
                p1 += 1
            i += 1
        while p1 <= mid:
            helper[i] = items[p1]
            p1 += 1
            i += 1
        while p2 <= max:
            helper[i] = items[p2]
            p2 += 1
            i += 1
        for i, k in enumerate(helper):
            items[m + i] = k