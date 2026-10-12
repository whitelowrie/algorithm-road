"""
用 Ray 实现「并行归并排序」
============================

呼应 chapter01/SortTest01.py 里的归并排序，把递归的 merge 过程
通过 @ray.remote 分发到多个 worker 上并行执行，演示 Ray 在
算法分治场景中的实际用法。

运行方式：
    python chapter03/ray_sort.py
"""

import random

import ray


# 本地辅助函数：合并两个有序数组
def _merge(left: list[int], right: list[int]) -> list[int]:
    result: list[int] = []
    i = j = 0
    while i < len(left) and j < len(right):
        if left[i] <= right[j]:
            result.append(left[i])
            i += 1
        else:
            result.append(right[j])
            j += 1
    result.extend(left[i:])
    result.extend(right[j:])
    return result


@ray.remote
def parallel_merge_sort(arr: list[int]) -> list[int]:
    """
    递归的并行归并排序。
    - 当数组较小时直接本地排序（避免远程调度开销得不偿失）；
    - 否则把左右两半分别作为远程任务提交，并行排序后再合并。
    """
    if len(arr) <= 1:
        return arr
    # 小数据量直接用内置 sorted，减少任务调度开销
    if len(arr) <= 10_000:
        return sorted(arr)

    mid = len(arr) // 2
    # 提交两个远程任务，Ray 会把它们调度到不同 worker 并行执行
    left_ref = parallel_merge_sort.remote(arr[:mid])
    right_ref = parallel_merge_sort.remote(arr[mid:])
    left, right = ray.get([left_ref, right_ref])
    return _merge(left, right)


def run_demo():
    ray.init(ignore_reinit_error=True, logging_level="ERROR")

    size = 200_000
    data = [random.randint(0, 1_000_000) for _ in range(size)]
    print(f"生成随机数组，长度 = {size}")

    sorted_data = ray.get(parallel_merge_sort.remote(data))

    # 简单校验
    assert sorted_data == sorted(data), "排序结果不正确！"
    print("排序完成，前 10 个元素:", sorted_data[:10])
    print("校验通过：结果等价于内置 sorted()。")

    ray.shutdown()


if __name__ == "__main__":
    run_demo()
