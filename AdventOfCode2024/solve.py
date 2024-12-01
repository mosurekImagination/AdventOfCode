def solve(filename, expected_result, fun):
    with open(filename, 'r') as file:
        result = fun(file)
    msg = f"Expected result is {expected_result} and calculated is: {result}"
    if expected_result != result:
        raise ValueError(msg)