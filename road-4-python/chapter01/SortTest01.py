from functools import reduce
from unittest import TestCase

from torch.nn.functional import fold


class SortTest(TestCase):

    items = [5, 3, 8, 1, 2, 7, 4, 6]

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
        print(self.items)

    from functools import reduce

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
        for i in range(len(self.items)):
            for j in range(i, 0, -1):
                if self.items[j] < self.items[j - 1]:
                    self.items[j], self.items[j - 1] = self.items[j - 1], self.items[j]
        print(self.items)


