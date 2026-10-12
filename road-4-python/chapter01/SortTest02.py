from unittest import TestCase


class SortTest02(TestCase):
    def test01(self):
        number = 123
        result = ""
        for i in range(31):
            result = ("0" if (number & ( 1 << i)) == 0 else "1") + result
        print(result)

