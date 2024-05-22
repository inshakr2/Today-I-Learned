def cain(M, N, x, y):
    
    k = x
    # 마지막 해
#    if x==M and y==N: return LCM(x, y)

    while k <= (M * N):
        if (k-1) % N +1 == y:
            return k
        k += M
    
    return -1 

def LCM(x, y):
    a, b = max(x, y), min(x, y)
    R = 1
    while R != 0:
        R = a % b
        a, b = b, R
    return x*y // a

T = int(input())
for _ in range(T):
    M, N, x, y = map(int, input().split())
    print(cain(M, N, x, y))