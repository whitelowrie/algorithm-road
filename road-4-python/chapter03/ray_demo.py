"""
Ray framework 入门 demo
=======================

本文件演示 Ray 最核心的 4 个概念：
  1. @ray.remote 装饰器：把普通函数变成「远程任务(Task)」
  2. 并行调度：.remote() 提交任务，ray.get() 取结果
  3. Actor：有状态的远程对象（类似一个跨进程的类实例）
  4. 单机串行 vs Ray 并行的性能对比

运行方式：
    pip install -r requirements.txt
    python chapter03/ray_demo.py
"""

import time

import ray


# ---------------------------------------------------------------------------
# 1. 普通函数 vs Ray 远程任务(Task)
# ---------------------------------------------------------------------------

def cpu_task(n: int) -> int:
    """模拟一个耗时的 CPU 计算：累加 0..n 。"""
    total = 0
    for i in range(n):
        total += i
    return total


# 用 @ray.remote 装饰后，函数就变成了一个「远程任务」。
# 调用时不会立即执行，而是返回一个 ObjectRef（ future / 未来对象）。
@ray.remote
def remote_cpu_task(n: int) -> int:
    return cpu_task(n)


# ---------------------------------------------------------------------------
# 2. Actor：有状态的远程对象
# ---------------------------------------------------------------------------

@ray.remote
class Counter:
    """一个简单的计数器 Actor：在独立的进程里维护状态。"""

    def __init__(self, init: int = 0):
        self.value = init

    def increment(self, step: int = 1) -> int:
        self.value += step
        return self.value

    def get(self) -> int:
        return self.value


# ---------------------------------------------------------------------------
# 演示函数
# ---------------------------------------------------------------------------

def demo_basic_task():
    print("\n=== 1. 基本远程任务 Task ===")
    # 提交任务，得到 ObjectRef（不是结果本身）
    ref = remote_cpu_task.remote(1_000_000)
    print("提交任务后拿到的是 ObjectRef:", ref)
    # ray.get 会阻塞直到任务完成，并取回真实结果
    result = ray.get(ref)
    print("ray.get(ref) 取回结果:", result)


def demo_parallel_tasks():
    print("\n=== 2. 并行提交多个 Task ===")
    # 一次性提交 4 个任务，它们会在 Ray 的多个 worker 上并行执行
    refs = [remote_cpu_task.remote(5_000_000) for _ in range(4)]
    print("已并行提交 4 个任务，等待全部完成...")
    results = ray.get(refs)  # 传入列表即可批量取结果
    print("4 个任务结果:", results)


def demo_actor():
    print("\n=== 3. Actor（有状态对象）===")
    counter = Counter.remote(init=10)
    # 调用 Actor 的方法同样返回 ObjectRef，需要 ray.get 取值
    print("increment ->", ray.get(counter.increment.remote(5)))
    print("increment ->", ray.get(counter.increment.remote(3)))
    print("get       ->", ray.get(counter.get.remote()))


def demo_performance_compare():
    print("\n=== 4. 串行 vs Ray 并行 性能对比 ===")
    workload = [3_000_000, 3_000_000, 3_000_000, 3_000_000]

    # 单机串行
    t0 = time.time()
    serial = [cpu_task(n) for n in workload]
    serial_cost = time.time() - t0
    print(f"串行耗时: {serial_cost:.3f}s")

    # Ray 并行
    t0 = time.time()
    parallel = ray.get([remote_cpu_task.remote(n) for n in workload])
    parallel_cost = time.time() - t0
    print(f"Ray 并行耗时: {parallel_cost:.3f}s")

    assert serial == parallel, "结果必须一致！"
    speedup = serial_cost / parallel_cost if parallel_cost > 0 else 0
    print(f"加速比约: {speedup:.2f}x  (结果校验一致: {serial == parallel})")


def main():
    # 初始化 Ray（默认会启动本地集群，分配 CPU 资源）
    ray.init(ignore_reinit_error=True, logging_level="ERROR")

    print("Ray 可用资源:", ray.cluster_resources())

    demo_basic_task()
    demo_parallel_tasks()
    demo_actor()
    demo_performance_compare()

    # 关闭 Ray 运行时
    ray.shutdown()
    print("\nDemo 结束，Ray 已关闭。")


if __name__ == "__main__":
    main()
