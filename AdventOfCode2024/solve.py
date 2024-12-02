def solveFun(filename, expected_result, fun):
    with open(filename, 'r') as file:
        result = fun(file)
    if expected_result is None:
        print(f"Result is {result}")
    elif expected_result != result:
            msg = f"Expected result is {expected_result} and calculated is: {result}"
            raise ValueError(msg)

def solve(test_result, final_result, *funcs):
    for func in funcs:
        print(f"Running {func.__name__} for test")
        solveFun("test.txt", test_result, func)
        print(f"Running {func.__name__} for final")
        solveFun("input.txt", final_result, func)