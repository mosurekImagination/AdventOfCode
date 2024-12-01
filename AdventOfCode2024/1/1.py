import re
from collections import Counter
import numpy as np

### SOLVE FUNCTION IMPORT
import sys
import os

# Add the grandparent directory to sys.path (two levels up)
sys.path.append(os.path.abspath(os.path.join(os.path.dirname(__file__), '../../')))
from solve import solve


### SOLVE FUNCTION IMPORT


def part1(file):
    pattern = r"(\d+)\s+(\d+)"
    first_column = []
    second_column = []
    for line in file:
        match = re.match(pattern, line)
        first_column.append(int(match.group(1)))
        second_column.append(int(match.group(2)))
    first_sorted = sorted(first_column)
    second_sorted = sorted(second_column)
    return sum(map(lambda x, y: abs(x - y), first_sorted, second_sorted))


def part2(file):
    pattern = r"(\d+)\s+(\d+)"
    first_column = []
    second_column = []
    for line in file:
        match = re.match(pattern, line)
        first_column.append(int(match.group(1)))
        second_column.append(int(match.group(2)))

    # Create a Counter object to store counts of second_numbers
    second_column_counts = Counter(second_column)
    # result = sum(map(lambda x: x * second_column_counts[x] , first_column))
    return sum(number * second_column_counts[number] for number in first_column)


def part1Short(file):
    input = list(map(lambda x: int(x), file.read().split()))
    firstColumn, secondColumn = sorted(input[0::2]), sorted(input[1::2])
    return sum(map(lambda a, b: abs(a - b), firstColumn, secondColumn))


def part2Short(file):
    input = list(map(lambda x: int(x), file.read().split()))
    firstColumn, secondColumn = sorted(input[0::2]), sorted(input[1::2])
    return sum(map(lambda a: a * secondColumn.count(a), firstColumn))


def part1Numpy(file):
    firstColumn, secondColumn = np.sort(np.loadtxt(file).T)
    return sum(abs(firstColumn - secondColumn))


def part2Numpy(file):
    firstColumn, secondColumn = np.loadtxt(file).T
    return sum(a * sum(a == secondColumn) for a in firstColumn)


solve(11, 2756096, part1, part1Short, part1Numpy)
solve(31, 23117829, part2, part2Short, part2Numpy)
