import re
from collections import Counter


def solvePart1(fileName, expectedResult):
    pattern = r"(\d+)\s+(\d+)"
    first = []
    second = []
    with open(fileName, 'r') as file:
        for line in file:
            match = re.match(pattern, line)
            first.append(int(match.group(1)))
            second.append(int(match.group(2)))
    firstSorted = sorted(first)
    secondSorted = sorted(second)
    result = sum(map(lambda x,y: abs(x-y), firstSorted, secondSorted))
    print(f"Expected result is {expectedResult} and calculated is: {result}")

solvePart1("test.txt", 11)
solvePart1("input.txt", 2756096)


def solvePart2(fileName, expectedResult):
    pattern = r"(\d+)\s+(\d+)"
    first_column = []
    second_column = []
    with open(fileName, 'r') as file:
        for line in file:
            match = re.match(pattern, line)
            first_column.append(int(match.group(1)))
            second_column.append(int(match.group(2)))

    # Create a Counter object to store counts of second_numbers
    second_number_counts = Counter(second_column)
    # result = sum(map(lambda x: x * second_number_counts[x] , first_column))
    result = sum(number * second_number_counts[number] for number in first_column)
    msg = f"Expected result is {expectedResult} and calculated is: {result}"
    if expectedResult != result:
        raise ValueError(msg)


solvePart2("test.txt", 31)
solvePart2("input.txt", 23117829)


def solve(filename, expected_result, fun):
    with open(filename, 'r') as file:
        result = fun(file)
    msg = f"Expected result is {expected_result} and calculated is: {result}"
    if expected_result != result:
        raise ValueError(msg)
