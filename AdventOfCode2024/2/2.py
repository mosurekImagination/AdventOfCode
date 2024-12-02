### SOLVE FUNCTION IMPORT
import sys
import os

# Add the grandparent directory to sys.path (two levels up)
sys.path.append(os.path.abspath(os.path.join(os.path.dirname(__file__), '../../')))
from solve import solve

### SOLVE FUNCTION IMPORT

def part1(file):
    count = 0
    for line in file:
        line = [*map(int, line.split())]
        r1 = all(1 <= line[i] - line[i-1] <= 3 for i in range(1, len(line)))
        r2 = all(1 <= line[i-1] - line[i] <= 3 for i in range(1, len(line)))
        if r1 or r2:
            count = count + 1
    return count


def part2(file):
    count = 0
    for line in file:
        line = [*map(int, line.split())]
        variations = {tuple(line[:i] + line[i + 1:]) for i in range(len(line))}  # Use tuple to store variations
        if any(
                all(1 <= variation[i] - variation[i - 1] <= 3 for i in range(1, len(variation))) or
                all(1 <= variation[i - 1] - variation[i] <= 3 for i in range(1, len(variation)))
                for variation in variations
        ):
            count += 1  # Increment count if any variation matches either r1 or r2
    return count


solve(2, 236, part1)
solve(4, 308, part2)
