def solveFun(filename, expected_result, fun):
    with open(filename, 'r') as file:
        result = fun(file)
    msg = f"Expected result is {expected_result} and calculated is: {result}"
    if expected_result != result:
        raise ValueError(msg)

def solve(test_result, final_result, *funcs):
    for func in funcs:
        print(f"Running {func.__name__} for test")
        solveFun("test.txt", test_result, func)
        print(f"Running {func.__name__} for final")
        solveFun("input.txt", final_result, func)